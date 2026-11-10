package kh.edu.istad.platform.iam.domain.model;

import kh.edu.istad.platform.iam.domain.valueobject.SocialProvider;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@Builder
public class Identity {
    private Long id;
    private UUID uuid;
    private Long userId;
    private SocialProvider provider;
    private String providerSubject; // Unique user ID from social provider
    private String email;
    private boolean emailVerified;
    private String displayName;
    private String username;
    private String pictureUrl;
    private Map<String, Object> attributes; // JSONB attributes
    private Instant linkedAt;
    private Instant lastLoginAt;
    private boolean isDeleted;
    private Instant deletedAt;
    private String deletedBy;
    private Instant createdAt;
    private String createdBy;
    private Instant updatedAt;
    private String updatedBy;
    private Long version;
}
