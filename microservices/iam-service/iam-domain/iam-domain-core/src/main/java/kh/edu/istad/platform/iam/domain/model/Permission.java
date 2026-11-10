package kh.edu.istad.platform.iam.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Builder
@Getter
@Setter
public class Permission {
    private Long id;
    private UUID uuid;
    private String name; // resource:action e.g. "user:read"
    private String resource;
    private String action;
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

}
