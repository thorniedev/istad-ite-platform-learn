package kh.edu.istad.platform.iam.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
public class Role {
    private Long id;
    private UUID uuid;
    private String name;
    private String description;
    private boolean isSystem;
    private boolean isActive;
    private boolean isDeleted;
    private Instant deletedAt;
    private String deletedBy;
    private Instant createdAt;
    private String createdBy;
    private Instant updatedAt;
    private String updatedBy;
    private Long version;

    @Builder.Default
    private Set<RolePermission> permissions = new HashSet<>();
}
