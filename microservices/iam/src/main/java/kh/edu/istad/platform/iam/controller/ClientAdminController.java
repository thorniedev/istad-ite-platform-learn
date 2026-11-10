package kh.edu.istad.platform.iam.controller;

import kh.edu.istad.platform.iam.feature.client.dto.ClientResponse;
import kh.edu.istad.platform.iam.feature.client.dto.CreateClientRequest;
import kh.edu.istad.platform.iam.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/admin/clients")
public class ClientAdminController {

    private final ClientService clientService;
    public ClientAdminController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<RegisteredClient> createClient(@RequestBody CreateClientRequest request) {
        return ResponseEntity.ok(clientService.createClient(request));
    }

    @GetMapping("/{clientId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<RegisteredClient> getClientById(@PathVariable String clientId) {
        return ResponseEntity.ok(clientService.getClientById(clientId));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<String>> getAllClients() {
        return ResponseEntity.ok(clientService.getAllClients());
    }



    /* Old Implementation dosen't have service.
    private final RegisteredClientRepository registeredClientRepository;
    private final PasswordEncoder passwordEncoder;
    private final PathPatternRequestMatcher.Builder builder;

    public ClientAdminController(RegisteredClientRepository registeredClientRepository, PasswordEncoder passwordEncoder, PathPatternRequestMatcher.Builder builder) {
        this.registeredClientRepository = registeredClientRepository;
        this.passwordEncoder = passwordEncoder;
        this.builder = builder;
    }

    // Create Client
    @PostMapping
    public ResponseEntity<ClientResponse> createClient(@RequestBody CreateClientRequest  request) {
        // verify clientId is unique
        if (registeredClientRepository.findByClientId(request.clientId()) != null ) {
            throw new RuntimeException("Client ID already exists" + request.clientId());
        }
        String id = UUID.randomUUID().toString();

        //  Build RegisteredClient
        RegisteredClient.Builder builder = RegisteredClient.withId(id)
                .clientId(request.clientId())
                .clientName(request.clientName() != null ? request.clientName() : request.clientId())
                .clientIdIssuedAt(Instant.now());

        // Client Secret (encode if provided)
        if (request.clientSecret() != null && !request.clientSecret().isBlank()) {
            builder.clientSecret(passwordEncoder.encode(request.clientSecret()));
        }

        // Grant types (default to client_credentials if omitted)
        if (request.grantTypes() != null && !request.grantTypes().isEmpty()) {
            request.grantTypes().forEach(gt -> builder.authorizationGrantType(new AuthorizationGrantType(gt)));
        } else {
            builder.authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS);
        }

        // Scopes
        if (request.scopes() != null) {
            request.scopes().forEach(builder::scope);
        }

        // Redirect URIs (for authorization_code)
        if (request.redirectUris() != null) {
            request.redirectUris().forEach(builder::redirectUri);
        }
        if (request.postLogoutRedirectUris() != null) {
            request.postLogoutRedirectUris().forEach(builder::postLogoutRedirectUri);
        }

        // Client & Token settings
        long ttl = request.accessTokenTtlMinutes() > 0 ? request.accessTokenTtlMinutes() : 60;
        builder.clientSettings(ClientSettings.builder()
                .requireAuthorizationConsent(request.requireAuthorizationConsent())
                .requireProofKey(request.grantTypes() != null && request.grantTypes().contains("authorization_code"))
                .build());
        builder.tokenSettings(TokenSettings.builder()
                .accessTokenFormat(OAuth2TokenFormat.SELF_CONTAINED) // JWT
                .accessTokenTimeToLive(Duration.ofMinutes(ttl))
                .build());

        // Save to JDBC repository
        RegisteredClient registeredClient = builder.build();
        registeredClientRepository.save(registeredClient);

        // return safe DTO
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(registeredClient));
    }

    @GetMapping("/{clientId}")
    public ResponseEntity<ClientResponse> getClient(@PathVariable String clientId) {
        RegisteredClient registeredClient = registeredClientRepository.findByClientId(clientId);

        RegisteredClient client = registeredClientRepository.findByClientId(clientId);

        if (client != null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Client not found with clientId: " + clientId
            );
        }
        return ResponseEntity.ok(toResponse(client));
    }

    // helper map
    private ClientResponse toResponse(RegisteredClient client) {
        return new ClientResponse(
                client.getId(),
                client.getClientId(),
                client.getClientName(),
                client.getAuthorizationGrantTypes().stream().map(AuthorizationGrantType::getValue).collect(Collectors.toSet()),
                client.getClientAuthenticationMethods().stream().map(ClientAuthenticationMethod::getValue).collect(Collectors.toSet()),
                client.getScopes(),
                client.getRedirectUris(),
                client.getClientIdIssuedAt()
        );
    } */

}

