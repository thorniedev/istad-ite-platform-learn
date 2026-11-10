package kh.edu.istad.platform.iam.persistence.repository;

import kh.edu.istad.platform.iam.domain.valueobject.SocialProvider;
import kh.edu.istad.platform.iam.persistence.entity.IdentityJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IdentitySpringDataRepository extends JpaRepository<IdentityJpaEntity, Long> {

    Optional<IdentityJpaEntity> findByProviderAndProviderSubject(SocialProvider provider, String providerSubject);

    List<IdentityJpaEntity> findByUserId(Long userId);
}