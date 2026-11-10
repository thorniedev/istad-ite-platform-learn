package kh.edu.istad.platform.iam.restapi.dto;

import java.time.Instant;
import java.util.UUID;

public record IdentityResponse(
        UUID uuid,
        String provider,
        String email,
        String displayName,
        Instant linkedAt
) {}