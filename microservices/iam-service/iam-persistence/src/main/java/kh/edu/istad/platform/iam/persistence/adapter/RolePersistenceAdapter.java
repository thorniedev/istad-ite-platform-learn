package kh.edu.istad.platform.iam.persistence.adapter;

import kh.edu.istad.platform.iam.application.port.out.RoleRepositoryPort;
import kh.edu.istad.platform.iam.domain.model.Role;
import kh.edu.istad.platform.iam.persistence.entity.RoleJpaEntity;
import kh.edu.istad.platform.iam.persistence.mapper.RolePersistenceMapper;
import kh.edu.istad.platform.iam.persistence.repository.RoleSpringDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RolePersistenceAdapter implements RoleRepositoryPort {

    private final RoleSpringDataRepository roleRepository;
    private final RolePersistenceMapper roleMapper;

    @Override
    @Transactional(readOnly = true)
    public Optional<Role> findById(Long id) {
        return roleRepository.findById(id).map(roleMapper::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Role> findByUuid(UUID uuid) {
        return roleRepository.findByUuid(uuid).map(roleMapper::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Role> findByName(String name) {
        return roleRepository.findByName(name).map(roleMapper::toDomain);
    }

    @Override
    @Transactional
    public Role save(Role role) {
        RoleJpaEntity entity = roleMapper.toEntity(role);
        RoleJpaEntity saved = roleRepository.save(entity);
        return roleMapper.toDomain(saved);
    }
}