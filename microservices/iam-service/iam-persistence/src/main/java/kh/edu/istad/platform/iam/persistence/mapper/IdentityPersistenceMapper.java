package kh.edu.istad.platform.iam.persistence.mapper;

import kh.edu.istad.platform.iam.domain.model.Identity;
import kh.edu.istad.platform.iam.persistence.entity.IdentityJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface IdentityPersistenceMapper {
    @Mapping(target = "userId", source = "user.id")
    Identity toDomain(IdentityJpaEntity entity);
    @Mapping(target = "user", ignore = true)
    IdentityJpaEntity toEntity(Identity domain);
}