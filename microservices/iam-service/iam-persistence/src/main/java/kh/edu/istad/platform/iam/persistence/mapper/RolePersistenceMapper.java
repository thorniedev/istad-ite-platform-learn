package kh.edu.istad.platform.iam.persistence.mapper;

import kh.edu.istad.platform.iam.domain.model.Role;
import kh.edu.istad.platform.iam.domain.model.RolePermission;
import kh.edu.istad.platform.iam.persistence.entity.RoleJpaEntity;
import kh.edu.istad.platform.iam.persistence.entity.RolePermissionJpaEntity;
import org.mapstruct.*;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {PermissionPersistenceMapper.class}
)
public interface RolePersistenceMapper {
    Role toDomain(RoleJpaEntity entity);
    RoleJpaEntity toEntity(Role domain);
    @Mapping(target = "roleId", source = "role.id")
    RolePermission toDomain(RolePermissionJpaEntity entity);
    @Mapping(target = "role", ignore = true)
    RolePermissionJpaEntity toEntity(RolePermission domain);
    @AfterMapping
    default void linkRoleToPermissions(@MappingTarget RoleJpaEntity entity) {
        if (entity.getPermissions() != null) {
            entity.getPermissions().forEach(rp -> rp.setRole(entity));
        }
    }
}
