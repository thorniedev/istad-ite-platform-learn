package kh.edu.istad.platform.iam.application.port.out;

import kh.edu.istad.platform.iam.domain.model.Permission;

import java.util.Optional;
import java.util.UUID;

public interface PermissionRepositoryPort {
    Optional<Permission> findById(Long id);
    Optional<Permission> findByUuid(UUID uuid);
    Optional<Permission> findByName(String name);
    Permission save(Permission permission);
}
