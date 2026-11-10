package kh.edu.istad.platform.iam.application.usecase.identity;

import kh.edu.istad.platform.iam.application.dto.command.FederatedIdentityCommand;
import kh.edu.istad.platform.iam.application.dto.result.AuthUserResult;
import kh.edu.istad.platform.iam.application.port.out.IdentityRepositoryPort;
import kh.edu.istad.platform.iam.application.port.out.RoleRepositoryPort;
import kh.edu.istad.platform.iam.application.port.out.UserRepositoryPort;
import kh.edu.istad.platform.iam.application.usecase.user.GetUserAuthoritiesUseCase;
import kh.edu.istad.platform.iam.domain.model.Identity;
import kh.edu.istad.platform.iam.domain.model.Role;
import kh.edu.istad.platform.iam.domain.model.User;
import kh.edu.istad.platform.iam.domain.model.UserRole;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@RequiredArgsConstructor
public class FindOrProvisionFederatedUserUseCase {

    private final IdentityRepositoryPort identityRepository;
    private final UserRepositoryPort userRepository;
    private final RoleRepositoryPort roleRepository;
    private final GetUserAuthoritiesUseCase getUserAuthoritiesUseCase;

    public AuthUserResult execute(FederatedIdentityCommand command) {
        Instant now = Instant.now();
        // 1. Check if this social provider identity already exists
        Identity identity = identityRepository.findByProviderAndProviderSubject(
                command.provider(), command.providerSubject()
        ).orElseGet(() -> {
            // 2. Not linked yet -> Find or create the user by verified email
            User user = userRepository.findByEmail(command.email().toLowerCase())
                    .orElseGet(() -> createFederatedUser(command, now));
            // 3. Link this social provider identity to the user
            Identity newIdentity = Identity.builder()
                    .uuid(UUID.randomUUID())
                    .userId(user.getId())
                    .provider(command.provider())
                    .providerSubject(command.providerSubject())
                    .email(command.email())
                    .emailVerified(command.emailVerified())
                    .displayName(command.displayName())
                    .username(command.username())
                    .pictureUrl(command.pictureUrl())
                    .attributes(command.attributes())
                    .linkedAt(now)
                    .lastLoginAt(now)
                    .createdAt(now)
                    .createdBy("FEDERATION")
                    .updatedAt(now)
                    .updatedBy("FEDERATION")
                    .build();
            return identityRepository.save(newIdentity);
        });
        // 4. Update last login timestamp
        identity.setLastLoginAt(now);
        identityRepository.save(identity);
        User user = userRepository.findById(identity.getUserId())
                .orElseThrow(() -> new IllegalStateException("User not found for identity"));
        // 5. Build full authentication authorities (roles + effective permissions)
        var authoritiesResult = getUserAuthoritiesUseCase.execute(user.getUuid());
        return new AuthUserResult(
                user.getId(),
                user.getUuid(),
                user.getUsername(),
                user.getPasswordHash(),
                user.isEnabled(),
                user.isAccountNonExpired(),
                user.isAccountNonLocked(),
                user.isCredentialsNonExpired(),
                authoritiesResult.effectivePermissions()
        );
    }
    private User createFederatedUser(FederatedIdentityCommand cmd, Instant now) {
        Role defaultRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new IllegalStateException("Default USER role not found"));
        String baseUsername = (cmd.username() != null && !cmd.username().isBlank())
                ? cmd.username()
                : cmd.email().split("@")[0];
        User newUser = User.builder()
                .uuid(UUID.randomUUID())
                .username(baseUsername)
                .email(cmd.email().toLowerCase())
                .emailVerified(cmd.emailVerified())
                .passwordHash(null) // Social-only user has no password
                .givenName(cmd.displayName())
                .pictureUrl(cmd.pictureUrl())
                .locale("en")
                .enabled(true)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .createdAt(now)
                .createdBy("FEDERATION")
                .updatedAt(now)
                .updatedBy("FEDERATION")
                .build();
        UserRole userRole = UserRole.builder()
                .role(defaultRole)
                .assignedAt(now)
                .assignedBy("SYSTEM")
                .build();
        newUser.getRoles().add(userRole);
        return userRepository.save(newUser);
    }
}
