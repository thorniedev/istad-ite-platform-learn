package kh.edu.istad.platform.iam.application.dto.result;

import java.util.Set;
import java.util.UUID;

public record AuthUserResult(
        Long id,
        UUID uuid,
        String username,
        String passwordHash,
        boolean enabled,
        boolean accountNonExpired,
        boolean accountNonLocked,
        boolean credentialsNonExpired,
        Set<String> authorities // ROLE_name + permissions
) {
}