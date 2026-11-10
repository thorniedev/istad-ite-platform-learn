package kh.edu.istad.platform.iam.application.usecase.user;

import kh.edu.istad.platform.iam.application.port.out.CurrentActorProviderPort;
import kh.edu.istad.platform.iam.application.port.out.UserRepositoryPort;
import kh.edu.istad.platform.iam.domain.model.User;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@RequiredArgsConstructor
public class DeleteUserUseCase {

    private final UserRepositoryPort userRepository;
    private final CurrentActorProviderPort actorProvider;

    public void execute(UUID userUuid) {
        User user = userRepository.findByUuid(userUuid)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userUuid));

        user.setDeleted(true);
        user.setDeletedAt(Instant.now());
        user.setDeletedBy(actorProvider.getCurrentActorSub());
        userRepository.save(user);
    }
}