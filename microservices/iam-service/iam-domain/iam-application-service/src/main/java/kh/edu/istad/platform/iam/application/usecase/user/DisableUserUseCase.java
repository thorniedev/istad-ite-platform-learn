package kh.edu.istad.platform.iam.application.usecase.user;

import kh.edu.istad.platform.iam.application.port.out.UserRepositoryPort;
import kh.edu.istad.platform.iam.domain.model.User;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class DisableUserUseCase {

    private final UserRepositoryPort userRepository;

    public void execute(UUID userUuid) {
        User user = userRepository.findByUuid(userUuid)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userUuid));
        user.setEnabled(false);
        userRepository.save(user);
    }
}