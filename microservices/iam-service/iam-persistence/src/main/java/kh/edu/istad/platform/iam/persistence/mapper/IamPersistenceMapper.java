package kh.edu.istad.platform.iam.persistence.mapper;

import kh.edu.istad.platform.iam.domain.model.*;
import kh.edu.istad.platform.iam.domain.valueobject.Address;
import kh.edu.istad.platform.iam.persistence.entity.*;

import java.util.stream.Collectors;

public class IamPersistenceMapper {

    // --- User Mapping ---
    public static User toDomain(UserJpaEntity entity) {
        if (entity == null) return null;

        Address address = null;
        if (entity.getAddress() != null) {
            address = new Address(
                    entity.getAddress().getStreetAddress(),
                    entity.getAddress().getLocality(),
                    entity.getAddress().getRegion(),
                    entity.getAddress().getPostalCode(),
                    entity.getAddress().getCountry()
            );
        }

        User user = User.builder()
                .id(entity.getId())
                .uuid(entity.getUuid())
                .username(entity.getUsername())
                .email(entity.getEmail())
                .emailVerified(entity.isEmailVerified())
                .passwordHash(entity.getPasswordHash())
                .givenName(entity.getGivenName())
                .familyName(entity.getFamilyName())
                .middleName(entity.getMiddleName())
                .nickname(entity.getNickname())
                .gender(entity.getGender())
                .birthdate(entity.getBirthdate())
                .profileUrl(entity.getProfileUrl())
                .pictureUrl(entity.getPictureUrl())
                .websiteUrl(entity.getWebsiteUrl())
                .zoneinfo(entity.getZoneinfo())
                .locale(entity.getLocale())
                .phoneNumber(entity.getPhoneNumber())
                .phoneNumberVerified(entity.isPhoneNumberVerified())
                .address(address)
                .enabled(entity.isEnabled())
                .accountNonExpired(entity.isAccountNonExpired())
                .accountNonLocked(entity.isAccountNonLocked())
                .credentialsNonExpired(entity.isCredentialsNonExpired())
                .failedLoginAttempts(entity.getFailedLoginAttempts())
                .lastLoginAt(entity.getLastLoginAt())
                .passwordChangedAt(entity.getPasswordChangedAt())
                .isDeleted(entity.isDeleted())
                .deletedAt(entity.getDeletedAt())
                .deletedBy(entity.getDeletedBy())
                .createdAt(entity.getCreatedAt())
                .createdBy(entity.getCreatedBy())
                .updatedAt(entity.getUpdatedAt())
                .updatedBy(entity.getUpdatedBy())
                .version(entity.getVersion())
                .build();

        if (entity.getRoles() != null) {
            user.setRoles(entity.getRoles().stream()
                    .map(IamPersistenceMapper::toDomain)
                    .collect(Collectors.toSet()));
        }

        if (entity.getPermissions() != null) {
            user.setPermissions(entity.getPermissions().stream()
                    .map(IamPersistenceMapper::toDomain)
                    .collect(Collectors.toSet()));
        }

        return user;
    }

    public static UserJpaEntity toEntity(User domain) {
        if (domain == null) return null;

        AddressEmbeddable address = null;
        if (domain.getAddress() != null) {
            address = AddressEmbeddable.builder()
                    .streetAddress(domain.getAddress().streetAddress())
                    .locality(domain.getAddress().locality())
                    .region(domain.getAddress().region())
                    .postalCode(domain.getAddress().postalCode())
                    .country(domain.getAddress().country())
                    .build();
        }

        UserJpaEntity entity = UserJpaEntity.builder()
                .id(domain.getId())
                .uuid(domain.getUuid())
                .username(domain.getUsername())
                .email(domain.getEmail())
                .emailVerified(domain.isEmailVerified())
                .passwordHash(domain.getPasswordHash())
                .givenName(domain.getGivenName())
                .familyName(domain.getFamilyName())
                .middleName(domain.getMiddleName())
                .nickname(domain.getNickname())
                .gender(domain.getGender())
                .birthdate(domain.getBirthdate())
                .profileUrl(domain.getProfileUrl())
                .pictureUrl(domain.getPictureUrl())
                .websiteUrl(domain.getWebsiteUrl())
                .zoneinfo(domain.getZoneinfo())
                .locale(domain.getLocale())
                .phoneNumber(domain.getPhoneNumber())
                .phoneNumberVerified(domain.isPhoneNumberVerified())
                .address(address)
                .enabled(domain.isEnabled())
                .accountNonExpired(domain.isAccountNonExpired())
                .accountNonLocked(domain.isAccountNonLocked())
                .credentialsNonExpired(domain.isCredentialsNonExpired())
                .failedLoginAttempts(domain.getFailedLoginAttempts())
                .lastLoginAt(domain.getLastLoginAt())
                .passwordChangedAt(domain.getPasswordChangedAt())
                .isDeleted(domain.isDeleted())
                .deletedAt(domain.getDeletedAt())
                .deletedBy(domain.getDeletedBy())
                .createdAt(domain.getCreatedAt())
                .createdBy(domain.getCreatedBy())
                .updatedAt(domain.getUpdatedAt())
                .updatedBy(domain.getUpdatedBy())
                .version(domain.getVersion())
                .build();

        if (domain.getRoles() != null) {
            entity.setRoles(domain.getRoles().stream()
                    .map(r -> toEntity(r, entity))
                    .collect(Collectors.toSet()));
        }

        if (domain.getPermissions() != null) {
            entity.setPermissions(domain.getPermissions().stream()
                    .map(p -> toEntity(p, entity))
                    .collect(Collectors.toSet()));
        }

        return entity;
    }

    // --- Role & Permission Mapping ---
    public static Role toDomain(RoleJpaEntity entity) {
        if (entity == null) return null;

        Role role = Role.builder()
                .id(entity.getId())
                .uuid(entity.getUuid())
                .name(entity.getName())
                .description(entity.getDescription())
                .isSystem(entity.isSystem())
                .isActive(entity.isActive())
                .isDeleted(entity.isDeleted())
                .deletedAt(entity.getDeletedAt())
                .deletedBy(entity.getDeletedBy())
                .createdAt(entity.getCreatedAt())
                .createdBy(entity.getCreatedBy())
                .updatedAt(entity.getUpdatedAt())
                .updatedBy(entity.getUpdatedBy())
                .version(entity.getVersion())
                .build();

        if (entity.getPermissions() != null) {
            role.setPermissions(entity.getPermissions().stream()
                    .map(IamPersistenceMapper::toDomain)
                    .collect(Collectors.toSet()));
        }

        return role;
    }

    public static RoleJpaEntity toEntity(Role domain) {
        if (domain == null) return null;

        return RoleJpaEntity.builder()
                .id(domain.getId())
                .uuid(domain.getUuid())
                .name(domain.getName())
                .description(domain.getDescription())
                .isSystem(domain.isSystem())
                .isActive(domain.isActive())
                .isDeleted(domain.isDeleted())
                .deletedAt(domain.getDeletedAt())
                .deletedBy(domain.getDeletedBy())
                .createdAt(domain.getCreatedAt())
                .createdBy(domain.getCreatedBy())
                .updatedAt(domain.getUpdatedAt())
                .updatedBy(domain.getUpdatedBy())
                .version(domain.getVersion())
                .build();
    }

    public static Permission toDomain(PermissionJpaEntity entity) {
        if (entity == null) return null;

        return Permission.builder()
                .id(entity.getId())
                .uuid(entity.getUuid())
                .name(entity.getName())
                .resource(entity.getResource())
                .action(entity.getAction())
                .description(entity.getDescription())
                .isSystem(entity.isSystem())
                .isActive(entity.isActive())
                .isDeleted(entity.isDeleted())
                .deletedAt(entity.getDeletedAt())
                .deletedBy(entity.getDeletedBy())
                .createdAt(entity.getCreatedAt())
                .createdBy(entity.getCreatedBy())
                .updatedAt(entity.getUpdatedAt())
                .updatedBy(entity.getUpdatedBy())
                .version(entity.getVersion())
                .build();
    }

    public static PermissionJpaEntity toEntity(Permission domain) {
        if (domain == null) return null;

        return PermissionJpaEntity.builder()
                .id(domain.getId())
                .uuid(domain.getUuid())
                .name(domain.getName())
                .resource(domain.getResource())
                .action(domain.getAction())
                .description(domain.getDescription())
                .isSystem(domain.isSystem())
                .isActive(domain.isActive())
                .isDeleted(domain.isDeleted())
                .deletedAt(domain.getDeletedAt())
                .deletedBy(domain.getDeletedBy())
                .createdAt(domain.getCreatedAt())
                .createdBy(domain.getCreatedBy())
                .updatedAt(domain.getUpdatedAt())
                .updatedBy(domain.getUpdatedBy())
                .version(domain.getVersion())
                .build();
    }

    // --- Audited Assignments Mapping ---
    public static UserRole toDomain(UserRoleJpaEntity entity) {
        if (entity == null) return null;

        return UserRole.builder()
                .id(entity.getId())
                .userId(entity.getUser() != null ? entity.getUser().getId() : null)
                .role(toDomain(entity.getRole()))
                .assignedAt(entity.getAssignedAt())
                .assignedBy(entity.getAssignedBy())
                .expiresAt(entity.getExpiresAt())
                .revokedAt(entity.getRevokedAt())
                .revokedBy(entity.getRevokedBy())
                .reason(entity.getReason())
                .build();
    }

    public static UserRoleJpaEntity toEntity(UserRole domain, UserJpaEntity userEntity) {
        if (domain == null) return null;

        return UserRoleJpaEntity.builder()
                .id(domain.getId())
                .user(userEntity)
                .role(toEntity(domain.getRole()))
                .assignedAt(domain.getAssignedAt())
                .assignedBy(domain.getAssignedBy())
                .expiresAt(domain.getExpiresAt())
                .revokedAt(domain.getRevokedAt())
                .revokedBy(domain.getRevokedBy())
                .reason(domain.getReason())
                .build();
    }

    public static RolePermission toDomain(RolePermissionJpaEntity entity) {
        if (entity == null) return null;

        return RolePermission.builder()
                .id(entity.getId())
                .roleId(entity.getRole() != null ? entity.getRole().getId() : null)
                .permission(toDomain(entity.getPermission()))
                .assignedAt(entity.getAssignedAt())
                .assignedBy(entity.getAssignedBy())
                .expiresAt(entity.getExpiresAt())
                .revokedAt(entity.getRevokedAt())
                .revokedBy(entity.getRevokedBy())
                .reason(entity.getReason())
                .build();
    }

    public static UserPermission toDomain(UserPermissionJpaEntity entity) {
        if (entity == null) return null;

        return UserPermission.builder()
                .id(entity.getId())
                .userId(entity.getUser() != null ? entity.getUser().getId() : null)
                .permission(toDomain(entity.getPermission()))
                .effect(entity.getEffect())
                .assignedAt(entity.getAssignedAt())
                .assignedBy(entity.getAssignedBy())
                .expiresAt(entity.getExpiresAt())
                .revokedAt(entity.getRevokedAt())
                .revokedBy(entity.getRevokedBy())
                .reason(entity.getReason())
                .build();
    }

    public static UserPermissionJpaEntity toEntity(UserPermission domain, UserJpaEntity userEntity) {
        if (domain == null) return null;

        return UserPermissionJpaEntity.builder()
                .id(domain.getId())
                .user(userEntity)
                .permission(toEntity(domain.getPermission()))
                .effect(domain.getEffect())
                .assignedAt(domain.getAssignedAt())
                .assignedBy(domain.getAssignedBy())
                .expiresAt(domain.getExpiresAt())
                .revokedAt(domain.getRevokedAt())
                .revokedBy(domain.getRevokedBy())
                .reason(domain.getReason())
                .build();
    }

    // --- Identity Mapping ---
    public static Identity toDomain(IdentityJpaEntity entity) {
        if (entity == null) return null;

        return Identity.builder()
                .id(entity.getId())
                .uuid(entity.getUuid())
                .userId(entity.getUser() != null ? entity.getUser().getId() : null)
                .provider(entity.getProvider())
                .providerSubject(entity.getProviderSubject())
                .email(entity.getEmail())
                .emailVerified(entity.isEmailVerified())
                .displayName(entity.getDisplayName())
                .username(entity.getUsername())
                .pictureUrl(entity.getPictureUrl())
                .attributes(entity.getAttributes())
                .linkedAt(entity.getLinkedAt())
                .lastLoginAt(entity.getLastLoginAt())
                .isDeleted(entity.isDeleted())
                .deletedAt(entity.getDeletedAt())
                .deletedBy(entity.getDeletedBy())
                .createdAt(entity.getCreatedAt())
                .createdBy(entity.getCreatedBy())
                .updatedAt(entity.getUpdatedAt())
                .updatedBy(entity.getUpdatedBy())
                .version(entity.getVersion())
                .build();
    }

    public static IdentityJpaEntity toEntity(Identity domain, UserJpaEntity userEntity) {
        if (domain == null) return null;

        return IdentityJpaEntity.builder()
                .id(domain.getId())
                .uuid(domain.getUuid())
                .user(userEntity)
                .provider(domain.getProvider())
                .providerSubject(domain.getProviderSubject())
                .email(domain.getEmail())
                .emailVerified(domain.isEmailVerified())
                .displayName(domain.getDisplayName())
                .username(domain.getUsername())
                .pictureUrl(domain.getPictureUrl())
                .attributes(domain.getAttributes())
                .linkedAt(domain.getLinkedAt())
                .lastLoginAt(domain.getLastLoginAt())
                .isDeleted(domain.isDeleted())
                .deletedAt(domain.getDeletedAt())
                .deletedBy(domain.getDeletedBy())
                .createdAt(domain.getCreatedAt())
                .createdBy(domain.getCreatedBy())
                .updatedAt(domain.getUpdatedAt())
                .updatedBy(domain.getUpdatedBy())
                .version(domain.getVersion())
                .build();
    }
}