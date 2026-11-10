package kh.edu.istad.platform.iam.persistence.adapter;

import kh.edu.istad.platform.iam.application.port.out.PermissionRepositoryPort;
import kh.edu.istad.platform.iam.domain.model.Permission;
import kh.edu.istad.platform.iam.persistence.entity.PermissionJpaEntity;
import kh.edu.istad.platform.iam.persistence.mapper.PermissionPersistenceMapper;
import kh.edu.istad.platform.iam.persistence.repository.PermissionSpringDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PermissionPersistenceAdapter implements PermissionRepositoryPort {

    private final PermissionSpringDataRepository permissionRepository;
    private final PermissionPersistenceMapper permissionMapper;

    @Override
    @Transactional(readOnly = true)
    public Optional<Permission> findById(Long id) {
        return permissionRepository.findById(id).map(permissionMapper::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Permission> findByUuid(UUID uuid) {
        return permissionRepository.findByUuid(uuid).map(permissionMapper::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Permission> findByName(String name) {
        return permissionRepository.findByName(name).map(permissionMapper::toDomain);
    }

    @Override
    @Transactional
    public Permission save(Permission permission) {
        PermissionJpaEntity entity = permissionMapper.toEntity(permission);
        PermissionJpaEntity saved = permissionRepository.save(entity);
        return permissionMapper.toDomain(saved);
    }
}