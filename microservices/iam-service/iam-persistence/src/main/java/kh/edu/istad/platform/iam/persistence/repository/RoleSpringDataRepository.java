package kh.edu.istad.platform.iam.persistence.repository;

import kh.edu.istad.platform.iam.persistence.entity.RoleJpaEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleSpringDataRepository extends JpaRepository<RoleJpaEntity, Long> {

    @EntityGraph(attributePaths = {"permissions.permission"})
    Optional<RoleJpaEntity> findByUuid(UUID uuid);

    @EntityGraph(attributePaths = {"permissions.permission"})
    Optional<RoleJpaEntity> findByName(String name);
}