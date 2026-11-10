package kh.edu.istad.platform.iam.application.usecase.user;

import kh.edu.istad.platform.iam.application.dto.result.UserResult;
import kh.edu.istad.platform.iam.application.port.out.UserRepositoryPort;
import kh.edu.istad.platform.iam.domain.model.User;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class GetUserUseCase {

    private final UserRepositoryPort userRepository;

    public UserResult execute(UUID userUuid) {
        User user = userRepository.findByUuid(userUuid)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userUuid));
        return UserResult.from(user);
    }
}