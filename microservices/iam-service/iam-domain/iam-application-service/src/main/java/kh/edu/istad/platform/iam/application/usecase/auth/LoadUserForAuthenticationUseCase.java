package kh.edu.istad.platform.iam.application.usecase.auth;

import kh.edu.istad.platform.iam.application.dto.result.AuthUserResult;
import kh.edu.istad.platform.iam.application.port.out.UserRepositoryPort;
import kh.edu.istad.platform.iam.application.usecase.user.GetUserAuthoritiesUseCase;
import kh.edu.istad.platform.iam.domain.model.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LoadUserForAuthenticationUseCase {

    private final UserRepositoryPort userRepository;
    private final GetUserAuthoritiesUseCase getUserAuthoritiesUseCase;

    public AuthUserResult execute(String usernameOrEmail) {
        User user = userRepository.findByUsernameOrEmail(usernameOrEmail)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + usernameOrEmail));
        var authorities = getUserAuthoritiesUseCase.execute(user.getUuid());
        return new AuthUserResult(
                user.getId(),
                user.getUuid(),
                user.getUsername(),
                user.getPasswordHash(),
                user.isEnabled(),
                user.isAccountNonExpired(),
                user.isAccountNonLocked(),
                user.isCredentialsNonExpired(),
                authorities.effectivePermissions()
        );
    }
}
