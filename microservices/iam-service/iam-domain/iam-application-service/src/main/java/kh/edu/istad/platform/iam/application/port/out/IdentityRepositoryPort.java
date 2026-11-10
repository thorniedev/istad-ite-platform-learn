package kh.edu.istad.platform.iam.application.port.out;

import kh.edu.istad.platform.iam.domain.model.Identity;
import kh.edu.istad.platform.iam.domain.valueobject.SocialProvider;

import java.util.List;
import java.util.Optional;

public interface IdentityRepositoryPort {
    Optional<Identity> findByProviderAndProviderSubject(SocialProvider provider, String providerSubject);
    List<Identity> findByUserId(Long userId);
    Identity save(Identity identity);
    void delete(Identity identity);
}
