package kh.edu.istad.platform.iam.service;

import kh.edu.istad.platform.iam.feature.client.dto.CreateClientRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final RegisteredClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;
    private final JdbcTemplate jdbcTemplate;

    // Create Client
    public RegisteredClient createClient(CreateClientRequest request){
        if (clientRepository.findByClientId(request.clientId()) != null) {
            throw new RuntimeException("Client ID already exists" + request.clientId());
        }

        RegisteredClient.Builder builder = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId(request.clientId())
                .clientName(request.clientName())
                .clientIdIssuedAt(Instant.now());

        // Client Secret
        if (request.clientSecret() != null && !request.clientSecret().isBlank()) {
            builder.clientSecret(passwordEncoder.encode(request.clientSecret()));
            builder.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC);
        } else {
            builder.clientAuthenticationMethod(ClientAuthenticationMethod.NONE); // PKCE public app
        }

        // Grant Types
        if (request.grantTypes() != null && !request.grantTypes().isEmpty()) {
            request.grantTypes().forEach(grantype -> builder.authorizationGrantType(new AuthorizationGrantType(grantype)));
        } else {
            builder.authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS);
        }

        // Scopes
        if (request.scopes() != null) {
            request.scopes().forEach(builder::scope);
        }

        if (request.redirectUris() != null) {
            request.redirectUris().forEach(builder::redirectUri);
        }

        if (request.postLogoutRedirectUris() != null) {
            request.postLogoutRedirectUris().forEach(builder::postLogoutRedirectUri);
        }

        // Settings
        long ttl = request.accessTokenTtlMinutes() > 0 ? request.accessTokenTtlMinutes() : 60;
        builder.clientSettings(ClientSettings.builder()
                .requireAuthorizationConsent(request.requireAuthorizationConsent())
                .build());

        builder.tokenSettings(TokenSettings.builder()
                        .accessTokenFormat(OAuth2TokenFormat.SELF_CONTAINED)
                        .accessTokenTimeToLive(Duration.ofMinutes(ttl))
                        .build());
        RegisteredClient registeredClient = builder.build();
        clientRepository.save(registeredClient);

        return registeredClient;

    }

    // READ Client
    public RegisteredClient getClientById(String clientId){
        RegisteredClient client = clientRepository.findByClientId(clientId);
        if (client != null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Client not found with clientId: " + clientId);
        }
        return client;
    }

    // List All Clients
    // Handled via JdbcTemplate because RegisteredClientRepository has no findAll() method
    public List<String> getAllClients(){
        return jdbcTemplate.queryForList(
                "SELECT client_id FROM oauth2_registered_client",
                String.class
        );
    }

    // DELETE
    public void deleteClient(String clientId){

        RegisteredClient client = clientRepository.findByClientId(clientId);

        // Clean up any existing tokens for this client first
        jdbcTemplate.update("DELETE FROM oauth2_authorization WHERE registered_client_id = ?", client.getId());
        jdbcTemplate.update("DELETE FROM oauth2_authorization_consent WHERE registered_client_id = ?", client.getId());

        // Delete client
        jdbcTemplate.update("DELETE FROM oauth2_registered_client WHERE id = ?", client.getId());

    }
}
