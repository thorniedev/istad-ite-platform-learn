package kh.edu.istad.platform.iam.application.usecase.user;


import kh.edu.istad.platform.iam.application.dto.command.RegisterUserCommand;
import kh.edu.istad.platform.iam.application.dto.result.UserResult;
import kh.edu.istad.platform.iam.application.port.out.PasswordHasherPort;
import kh.edu.istad.platform.iam.application.port.out.RoleRepositoryPort;
import kh.edu.istad.platform.iam.application.port.out.UserRepositoryPort;
import kh.edu.istad.platform.iam.domain.model.Role;
import kh.edu.istad.platform.iam.domain.model.User;
import kh.edu.istad.platform.iam.domain.model.UserRole;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@RequiredArgsConstructor
public class RegisterUserUseCase {
    private final UserRepositoryPort userRepository;
    private final RoleRepositoryPort roleRepository;
    private final PasswordHasherPort passwordHasher;

    public UserResult execute(RegisterUserCommand command) {
        if (!command.password().equals(command.confirmPassword())) {
            throw new IllegalArgumentException("Passwords do not match");
        }
        if (userRepository.existsByUsername(command.username())) {
            throw new IllegalArgumentException("Username already exists");
        }
        if (userRepository.existsByEmail(command.email().toLowerCase())) {
            throw new IllegalArgumentException("Email already exists");
        }
        Role defaultRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new IllegalStateException("Default USER role not configured"));
        Instant now = Instant.now();
        User user = User.builder()
                .uuid(UUID.randomUUID())
                .username(command.username())
                .email(command.email().toLowerCase())
                .emailVerified(false)
                .passwordHash(passwordHasher.encode(command.password()))
                .givenName(command.givenName())
                .familyName(command.familyName())
                .locale(command.locale() != null ? command.locale() : "en")
                .enabled(true)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .createdAt(now)
                .createdBy("SELF_REGISTER")
                .updatedAt(now)
                .updatedBy("SELF_REGISTER")
                .build();
        UserRole userRole = UserRole.builder()
                .role(defaultRole)
                .assignedAt(now)
                .assignedBy("SYSTEM")
                .build();
        user.getRoles().add(userRole);
        User savedUser = userRepository.save(user);
        return UserResult.from(savedUser);
    }

}
