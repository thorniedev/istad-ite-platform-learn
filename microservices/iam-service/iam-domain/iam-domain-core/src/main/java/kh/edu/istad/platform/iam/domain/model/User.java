package kh.edu.istad.platform.iam.domain.model;

import kh.edu.istad.platform.iam.domain.valueobject.Address;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
public class User {
    private Long id;
    private UUID uuid;
    private String username;
    private String email;
    private boolean emailVerified;
    private String passwordHash; // null for social-only users
    private String givenName;
    private String familyName;
    private String middleName;
    private String nickname;
    private String gender;
    private LocalDate birthdate;
    private String profileUrl;
    private String pictureUrl;
    private String websiteUrl;
    private String zoneinfo;
    private String locale; // "en", "km"
    private String phoneNumber;
    private boolean phoneNumberVerified;
    private Address address;

    // Security flags
    private boolean enabled;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private int failedLoginAttempts;
    private Instant lastLoginAt;
    private Instant passwordChangedAt;

    // Soft delete & audit
    private boolean isDeleted;
    private Instant deletedAt;
    private String deletedBy;
    private Instant createdAt;
    private String createdBy;
    private Instant updatedAt;
    private String updatedBy;
    private Long version;

    // Audited assignments
    @Builder.Default
    private Set<UserRole> roles = new HashSet<>();

    @Builder.Default
    private Set<UserPermission> permissions = new HashSet<>();

    @Builder.Default
    private Set<Identity> identities = new HashSet<>();

    public String getFullName() {
        if (givenName == null && familyName == null) return username;
        if (givenName == null) return familyName;
        if (familyName == null) return givenName;
        return givenName + " " + familyName;
    }
}
