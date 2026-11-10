package kh.edu.istad.platform.iam.feature.client.dto;

import java.time.Instant;
import java.util.Set;
public record ClientResponse(
        String id,
        String clientId,
        String clientName,
        Set<String> grantTypes,
        Set<String> authMethods,
        Set<String> scopes,
        Set<String> redirectUris,
        Instant clientIdIssuedAt
) {}