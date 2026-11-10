package kh.edu.istad.platform.iam.persistence.mapper;

import kh.edu.istad.platform.iam.domain.model.User;
import kh.edu.istad.platform.iam.domain.model.UserPermission;
import kh.edu.istad.platform.iam.domain.model.UserRole;
import kh.edu.istad.platform.iam.domain.valueobject.Address;
import kh.edu.istad.platform.iam.persistence.entity.*;
import org.mapstruct.*;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {RolePersistenceMapper.class, PermissionPersistenceMapper.class, IdentityPersistenceMapper.class}
)
public interface UserPersistenceMapper {
    User toDomain(UserJpaEntity entity);
    UserJpaEntity toEntity(User domain);
    // Embeddable Address <-> Domain Record Address
    Address toDomain(AddressEmbeddable embeddable);
    AddressEmbeddable toEmbeddable(Address domain);
    // UserRole Mapping
    @Mapping(target = "userId", source = "user.id")
    UserRole toDomain(UserRoleJpaEntity entity);
    @Mapping(target = "user", ignore = true)
    UserRoleJpaEntity toEntity(UserRole domain);
    // UserPermission Mapping
    @Mapping(target = "userId", source = "user.id")
    UserPermission toDomain(UserPermissionJpaEntity entity);
    @Mapping(target = "user", ignore = true)
    UserPermissionJpaEntity toEntity(UserPermission domain);
    // Set bidirectional parent references for JPA cascade
    @AfterMapping
    default void linkParentToAssignments(@MappingTarget UserJpaEntity entity) {
        if (entity.getRoles() != null) {
            entity.getRoles().forEach(ur -> ur.setUser(entity));
        }
        if (entity.getPermissions() != null) {
            entity.getPermissions().forEach(up -> up.setUser(entity));
        }
        if (entity.getIdentities() != null) {
            entity.getIdentities().forEach(id -> id.setUser(entity));
        }
    }
}
