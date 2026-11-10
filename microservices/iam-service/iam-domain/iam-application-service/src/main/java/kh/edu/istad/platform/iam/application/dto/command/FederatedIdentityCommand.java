package kh.edu.istad.platform.iam.application.dto.command;

import kh.edu.istad.platform.iam.domain.valueobject.SocialProvider;

import java.util.Map;

public record FederatedIdentityCommand(
        SocialProvider provider,
        String providerSubject,
        String email,
        boolean emailVerified,
        String displayName,
        String username,
        String pictureUrl,
        Map<String, Object> attributes
) {
}
