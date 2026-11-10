package kh.edu.istad.platform.iam.feature.consent.dto;

import java.util.Set;
public record ConsentResponse(
        String clientId,
        String principalName,
        Set<String> authorities // Granted scopes, e.g. ["customer.read", "profile"]
) {}
