package kh.edu.istad.platform.iam.application.dto.result;

import kh.edu.istad.platform.iam.domain.model.User;

import java.time.Instant;
import java.util.UUID;


public record UserResult(
        UUID uuid,
        String username,
        String email,
        boolean emailVerified,
        String givenName,
        String familyName,
        String locale,
        boolean enabled,
        Instant createdAt
) {
    public static UserResult from(User user) {
        return new UserResult(
                user.getUuid(),
                user.getUsername(),
                user.getEmail(),
                user.isEmailVerified(),
                user.getGivenName(),
                user.getFamilyName(),
                user.getLocale(),
                user.isEnabled(),
                user.getCreatedAt()
        );
    }
}
