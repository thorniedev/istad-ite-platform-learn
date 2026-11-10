package kh.edu.istad.platform.iam.restapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;
import java.util.UUID;

public record GrantPermissionsToUserRequest(
        @NotEmpty List<UUID> permissionUuids,
        @NotBlank String effect, // GRANT or DENY
        String reason
) {}