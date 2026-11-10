package kh.edu.istad.platform.iam.restapi.dto;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record UserResponse(
        UUID uuid,
        String username,
        String email,
        boolean emailVerified,
        String name,
        String locale,
        boolean enabled,
        List<RoleSummaryDto> roles,
        Instant createdAt,
        Instant updatedAt
) {
    public record RoleSummaryDto(UUID uuid, String name) {}
}