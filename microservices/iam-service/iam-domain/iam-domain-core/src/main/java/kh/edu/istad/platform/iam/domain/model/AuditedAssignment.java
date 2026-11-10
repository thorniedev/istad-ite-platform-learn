package kh.edu.istad.platform.iam.domain.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

@Getter
@Setter
@SuperBuilder
public abstract class AuditedAssignment {
    protected Long id;
    protected Instant assignedAt;
    protected String assignedBy;
    protected Instant expiresAt;
    protected Instant revokedAt;
    protected String revokedBy;
    protected String reason;

    public boolean isActive() {
        boolean notRevoked = (revokedAt == null);
        boolean notExpired = (expiresAt == null || expiresAt.isAfter(Instant.now()));
        return notRevoked && notExpired;
    }

    public void revoke(String actorSub, String reason) {
        this.revokedAt = Instant.now();
        this.revokedBy = actorSub;
        this.reason = reason;
    }
}
