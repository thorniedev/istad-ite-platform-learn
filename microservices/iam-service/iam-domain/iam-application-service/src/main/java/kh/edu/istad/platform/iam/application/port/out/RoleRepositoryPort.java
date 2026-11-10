package kh.edu.istad.platform.iam.application.port.out;

import kh.edu.istad.platform.iam.domain.model.Role;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepositoryPort {
    Optional<Role> findById(Long id);
    Optional<Role> findByUuid(UUID uuid);
    Optional<Role> findByName(String name);
    Role save(Role role);
}