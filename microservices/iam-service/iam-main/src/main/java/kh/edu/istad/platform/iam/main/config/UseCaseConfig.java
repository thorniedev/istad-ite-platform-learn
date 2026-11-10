package kh.edu.istad.platform.iam.main.config;

import kh.edu.istad.platform.iam.application.port.out.CurrentActorProviderPort;
import kh.edu.istad.platform.iam.application.port.out.IdentityRepositoryPort;
import kh.edu.istad.platform.iam.application.port.out.PasswordHasherPort;
import kh.edu.istad.platform.iam.application.port.out.RoleRepositoryPort;
import kh.edu.istad.platform.iam.application.port.out.UserRepositoryPort;
import kh.edu.istad.platform.iam.application.usecase.auth.LoadUserForAuthenticationUseCase;
import kh.edu.istad.platform.iam.application.usecase.identity.FindOrProvisionFederatedUserUseCase;
import kh.edu.istad.platform.iam.application.usecase.user.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Bean
    public GetUserAuthoritiesUseCase getUserAuthoritiesUseCase(UserRepositoryPort userRepository) {
        return new GetUserAuthoritiesUseCase(userRepository);
    }

    @Bean
    public RegisterUserUseCase registerUserUseCase(
            UserRepositoryPort userRepository,
            RoleRepositoryPort roleRepository,
            PasswordHasherPort passwordHasher
    ) {
        return new RegisterUserUseCase(userRepository, roleRepository, passwordHasher);
    }

    @Bean
    public GetUserUseCase getUserUseCase(UserRepositoryPort userRepository) {
        return new GetUserUseCase(userRepository);
    }

    @Bean
    public DeleteUserUseCase deleteUserUseCase(
            UserRepositoryPort userRepository,
            CurrentActorProviderPort actorProvider
    ) {
        return new DeleteUserUseCase(userRepository, actorProvider);
    }

    @Bean
    public EnableUserUseCase enableUserUseCase(UserRepositoryPort userRepository) {
        return new EnableUserUseCase(userRepository);
    }

    @Bean
    public DisableUserUseCase disableUserUseCase(UserRepositoryPort userRepository) {
        return new DisableUserUseCase(userRepository);
    }

    @Bean
    public LockUserUseCase lockUserUseCase(UserRepositoryPort userRepository) {
        return new LockUserUseCase(userRepository);
    }

    @Bean
    public UnlockUserUseCase unlockUserUseCase(UserRepositoryPort userRepository) {
        return new UnlockUserUseCase(userRepository);
    }

    @Bean
    public LoadUserForAuthenticationUseCase loadUserForAuthenticationUseCase(
            UserRepositoryPort userRepository,
            GetUserAuthoritiesUseCase getUserAuthoritiesUseCase
    ) {
        return new LoadUserForAuthenticationUseCase(userRepository, getUserAuthoritiesUseCase);
    }

    @Bean
    public FindOrProvisionFederatedUserUseCase findOrProvisionFederatedUserUseCase(
            IdentityRepositoryPort identityRepository,
            UserRepositoryPort userRepository,
            RoleRepositoryPort roleRepository,
            GetUserAuthoritiesUseCase getUserAuthoritiesUseCase
    ) {
        return new FindOrProvisionFederatedUserUseCase(
                identityRepository,
                userRepository,
                roleRepository,
                getUserAuthoritiesUseCase
        );
    }
}