package kh.edu.istad.platform.iam.feature.client.dto;

import java.util.Set;

public record CreateClientRequest(
        String clientId,
        String clientSecret,
        String clientName,
        Set<String> grantTypes,          // ["client_credentials"] or ["authorization_code", "refresh_token"]
        Set<String> authMethods,         // ["client_secret_basic", "client_secret_post", "none"]
        Set<String> scopes,              // ["customer.read", "customer.write", "openid"]
        Set<String> redirectUris,        // required for authorization_code
        Set<String> postLogoutRedirectUris,
        long accessTokenTtlMinutes,      // 60
        boolean requireAuthorizationConsent // true for user consent screen, false for M2M
) {
}
