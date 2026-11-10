package kh.edu.istad.platform.iam.persistence.repository;

import kh.edu.istad.platform.iam.persistence.entity.UserJpaEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserSpringDataRepository extends JpaRepository<UserJpaEntity, Long> {

    @EntityGraph(attributePaths = {"roles.role.permissions.permission", "permissions.permission", "identities"})
    Optional<UserJpaEntity> findByUuid(UUID uuid);

    @EntityGraph(attributePaths = {"roles.role.permissions.permission", "permissions.permission", "identities"})
    Optional<UserJpaEntity> findByUsername(String username);

    @EntityGraph(attributePaths = {"roles.role.permissions.permission", "permissions.permission", "identities"})
    Optional<UserJpaEntity> findByEmail(String email);

    @EntityGraph(attributePaths = {"roles.role.permissions.permission", "permissions.permission", "identities"})
    Optional<UserJpaEntity> findByUsernameOrEmail(String username, String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}