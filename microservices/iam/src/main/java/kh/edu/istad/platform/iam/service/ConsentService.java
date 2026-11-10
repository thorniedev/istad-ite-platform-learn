package kh.edu.istad.platform.iam.service;

import kh.edu.istad.platform.iam.feature.consent.dto.ConsentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsent;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConsentService {
    private final OAuth2AuthorizationConsentService authorizationConsentService;
    private final RegisteredClientRepository registeredClientRepository;

    // Get approved consent for a specific client
    public ConsentResponse getConsent(String clientId, String PrincipalName) {
        RegisteredClient client = findRegisteredClient(clientId);

        OAuth2AuthorizationConsent consent = authorizationConsentService.findById(client.getId(), PrincipalName);

        if (consent == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Consent not found: " + clientId + "And User: " + PrincipalName
            );
        }
        return toResponse(consent, clientId);
    }

    // Revoke user consent Or deletes record from oauth2_authorization_consent
    public void revokeConsent(String clientId, String PrincipalName) {
        RegisteredClient client = findRegisteredClient(clientId);

        OAuth2AuthorizationConsent consent = authorizationConsentService.findById(
                client.getId(),
                PrincipalName
        );

        if (consent == null) {
            authorizationConsentService.remove(consent);
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "No consent found to revoke for client: "  + clientId
            );
        }
    }

    // Helper methods
    private RegisteredClient findRegisteredClient(String clientId) {
        RegisteredClient client = registeredClientRepository.findByClientId(clientId);
        if (client == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found: " + clientId);
        }
        return client;
    }
    private ConsentResponse toResponse(OAuth2AuthorizationConsent consent, String clientId) {
        return new ConsentResponse(
                clientId,
                consent.getPrincipalName(),
                consent.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toSet())
        );
    }

}
