package kh.edu.istad.platform.iam.infrastructure.oauth2;

import kh.edu.istad.platform.iam.application.port.out.UserRepositoryPort;
import kh.edu.istad.platform.iam.domain.model.User;
import kh.edu.istad.platform.iam.infrastructure.security.IamUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.stereotype.Component;
import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class IamOAuth2TokenCustomizer implements OAuth2TokenCustomizer<JwtEncodingContext> {

    private final UserRepositoryPort userRepository;

    @Override
    public void customize(JwtEncodingContext context) {
        Authentication principal = context.getPrincipal();
        if (principal.getPrincipal() instanceof IamUserDetails userDetails) {
            UUID userUuid = userDetails.getUuid();
            // 1. Customize Access Token with Authorities
            if (OAuth2TokenType.ACCESS_TOKEN.equals(context.getTokenType())) {
                Set<String> authorities = userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toSet());
                Set<String> roles = authorities.stream()
                        .filter(a -> a.startsWith("ROLE_"))
                        .map(a -> a.substring(5))
                        .collect(Collectors.toSet());
                Set<String> permissions = authorities.stream()
                        .filter(a -> !a.startsWith("ROLE_"))
                        .collect(Collectors.toSet());
                context.getClaims().claim("sub", userUuid.toString());
                context.getClaims().claim("roles", roles);
                context.getClaims().claim("permissions", permissions);
            }
            // 2. Customize ID Token / UserInfo according to Section E.2 Scopes
            if ("id_token".equals(context.getTokenType().getValue())) {
                context.getClaims().claim("sub", userUuid.toString());
                Optional<User> userOpt = userRepository.findByUuid(userUuid);
                if (userOpt.isPresent()) {
                    User user = userOpt.get();
                    Set<String> authorizedScopes = context.getAuthorizedScopes();
                    if (authorizedScopes.contains("profile")) {
                        context.getClaims().claim("name", user.getFullName());
                        context.getClaims().claim("given_name", user.getGivenName());
                        context.getClaims().claim("family_name", user.getFamilyName());
                        if (user.getMiddleName() != null) context.getClaims().claim("middle_name", user.getMiddleName());
                        if (user.getNickname() != null) context.getClaims().claim("nickname", user.getNickname());
                        context.getClaims().claim("preferred_username", user.getUsername());
                        if (user.getProfileUrl() != null) context.getClaims().claim("profile", user.getProfileUrl());
                        if (user.getPictureUrl() != null) context.getClaims().claim("picture", user.getPictureUrl());
                        if (user.getWebsiteUrl() != null) context.getClaims().claim("website", user.getWebsiteUrl());
                        if (user.getGender() != null) context.getClaims().claim("gender", user.getGender());
                        if (user.getBirthdate() != null) context.getClaims().claim("birthdate", user.getBirthdate().toString());
                        if (user.getZoneinfo() != null) context.getClaims().claim("zoneinfo", user.getZoneinfo());
                        context.getClaims().claim("locale", user.getLocale());
                        if (user.getUpdatedAt() != null) context.getClaims().claim("updated_at", user.getUpdatedAt().getEpochSecond());
                    }
                    if (authorizedScopes.contains("email")) {
                        context.getClaims().claim("email", user.getEmail());
                        context.getClaims().claim("email_verified", user.isEmailVerified());
                    }
                    if (authorizedScopes.contains("phone") && user.getPhoneNumber() != null) {
                        context.getClaims().claim("phone_number", user.getPhoneNumber());
                        context.getClaims().claim("phone_number_verified", user.isPhoneNumberVerified());
                    }
                    if (authorizedScopes.contains("address") && user.getAddress() != null) {
                        Map<String, Object> addr = new HashMap<>();
                        addr.put("street_address", user.getAddress().streetAddress());
                        addr.put("locality", user.getAddress().locality());
                        addr.put("region", user.getAddress().region());
                        addr.put("postal_code", user.getAddress().postalCode());
                        addr.put("country", user.getAddress().country());
                        context.getClaims().claim("address", addr);
                    }
                }
            }
        }
    }
}
