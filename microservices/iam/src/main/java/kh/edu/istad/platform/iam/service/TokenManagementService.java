package kh.edu.istad.platform.iam.service;

import kh.edu.istad.platform.iam.feature.authorization.dto.TokenIntrospectResponse;
import kh.edu.istad.platform.iam.feature.consent.dto.ConsentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class TokenManagementService {

    private final OAuth2AuthorizationService authorizationService;


    public void revokeToken(String tokenValue) {
        OAuth2Authorization authorization = authorizationService.findByToken(tokenValue, OAuth2TokenType.ACCESS_TOKEN);
        if (authorization == null) {
            authorization = authorizationService.findByToken(tokenValue, OAuth2TokenType.REFRESH_TOKEN);
        }
        if (authorization != null) {
            authorizationService.remove(authorization);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Token not found or already expired");
        }
    }

    public TokenIntrospectResponse introspectToken(String tokenValue) {
        OAuth2Authorization authorization = authorizationService.findByToken(tokenValue, OAuth2TokenType.ACCESS_TOKEN);
        if (authorization == null) {
            return new TokenIntrospectResponse(false, null, null, null);
        }
        OAuth2Authorization.Token<OAuth2AccessToken> accessToken = authorization.getAccessToken();
        boolean isActive = accessToken != null && accessToken.isActive();
        if (!isActive) {
            return new TokenIntrospectResponse(false, null, null, null);
        }
        return new TokenIntrospectResponse(
                true,
                authorization.getPrincipalName(),
                authorization.getAuthorizedScopes(),
                accessToken.getToken().getExpiresAt()
        );
    }

}
