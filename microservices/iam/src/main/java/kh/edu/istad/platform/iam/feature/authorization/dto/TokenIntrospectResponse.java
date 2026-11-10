package kh.edu.istad.platform.iam.feature.authorization.dto;

import java.time.Instant;
import java.util.Set;

public record TokenIntrospectResponse(
        boolean active,
        String principalName,
        Set<String> scopes,
        Instant expiresAt
) {
}
