package kh.edu.istad.platform.iam.restapi.dto;

import jakarta.validation.constraints.NotEmpty;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record AssignRolesToUserRequest(
        @NotEmpty List<UUID> roleUuids,
        Instant expiresAt,
        String reason
) {}