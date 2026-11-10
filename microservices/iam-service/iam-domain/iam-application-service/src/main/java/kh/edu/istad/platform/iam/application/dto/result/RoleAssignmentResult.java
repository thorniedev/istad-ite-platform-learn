package kh.edu.istad.platform.iam.application.dto.result;

import java.time.Instant;
import java.util.UUID;

public record RoleAssignmentResult(
        UUID uuid,
        String name,
        Instant expiresAt
) {}
