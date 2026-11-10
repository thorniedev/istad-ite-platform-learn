package kh.edu.istad.platform.iam.restapi.dto;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public record UserAuthoritiesResponse(
        List<RoleAssignmentDto> roles,
        List<DirectPermissionDto> directPermissions,
        Set<String> effectivePermissions
) {
    public record RoleAssignmentDto(UUID uuid, String name, Instant expiresAt) {}
    public record DirectPermissionDto(String name, String effect) {}
}