package kh.edu.istad.platform.iam.persistence.mapper;

import kh.edu.istad.platform.iam.domain.model.Permission;
import kh.edu.istad.platform.iam.persistence.entity.PermissionJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PermissionPersistenceMapper {

    Permission toDomain(PermissionJpaEntity entity);

    PermissionJpaEntity toEntity(Permission domain);
}
