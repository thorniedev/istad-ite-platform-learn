package kh.edu.istad.platform.iam.persistence.adapter;

import kh.edu.istad.platform.iam.application.port.out.IdentityRepositoryPort;
import kh.edu.istad.platform.iam.domain.model.Identity;
import kh.edu.istad.platform.iam.domain.valueobject.SocialProvider;
import kh.edu.istad.platform.iam.persistence.entity.IdentityJpaEntity;
import kh.edu.istad.platform.iam.persistence.entity.UserJpaEntity;
import kh.edu.istad.platform.iam.persistence.mapper.IamPersistenceMapper;
import kh.edu.istad.platform.iam.persistence.mapper.IdentityPersistenceMapper;
import kh.edu.istad.platform.iam.persistence.repository.IdentitySpringDataRepository;
import kh.edu.istad.platform.iam.persistence.repository.UserSpringDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class IdentityPersistenceAdapter implements IdentityRepositoryPort {

    private final IdentitySpringDataRepository identityRepository;
    private final UserSpringDataRepository userRepository;
    private final IdentityPersistenceMapper identityMapper;

    @Override
    @Transactional(readOnly = true)
    public Optional<Identity> findByProviderAndProviderSubject(SocialProvider provider, String providerSubject) {
        return identityRepository.findByProviderAndProviderSubject(provider, providerSubject)
                .map(identityMapper::toDomain);
    }
    @Override
    @Transactional(readOnly = true)
    public List<Identity> findByUserId(Long userId) {
        return identityRepository.findByUserId(userId).stream()
                .map(identityMapper::toDomain)
                .toList();
    }
    @Override
    @Transactional
    public Identity save(Identity identity) {
        UserJpaEntity userEntity = userRepository.findById(identity.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found for id: " + identity.getUserId()));
        IdentityJpaEntity entity = identityMapper.toEntity(identity);
        entity.setUser(userEntity);
        IdentityJpaEntity saved = identityRepository.save(entity);
        return identityMapper.toDomain(saved);
    }
    @Override
    @Transactional
    public void delete(Identity identity) {
        identityRepository.deleteById(identity.getId());
    }

}