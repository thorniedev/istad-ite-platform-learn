package kh.edu.istad.platform.iam.application.usecase.user;

import kh.edu.istad.platform.iam.application.dto.result.RoleAssignmentResult;
import kh.edu.istad.platform.iam.application.dto.result.UserAuthoritiesResult;
import kh.edu.istad.platform.iam.application.port.out.UserRepositoryPort;
import kh.edu.istad.platform.iam.domain.model.User;
import kh.edu.istad.platform.iam.domain.model.UserPermission;
import kh.edu.istad.platform.iam.domain.model.UserRole;
import kh.edu.istad.platform.iam.domain.service.EffectivePermissionCalculator;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor
public class GetUserAuthoritiesUseCase {

    private final UserRepositoryPort userRepository;
    public UserAuthoritiesResult execute(UUID userUuid) {
        User user = userRepository.findByUuid(userUuid)
                .orElseThrow(() -> new IllegalArgumentException("User not found with uuid: " + userUuid));
        // 1. Map active roles
        List<RoleAssignmentResult> activeRoles = user.getRoles().stream()
                .filter(UserRole::isActive)
                .map(ur -> new RoleAssignmentResult(ur.getRole().getUuid(), ur.getRole().getName(), ur.getExpiresAt()))
                .toList();
        // 2. Direct permissions
        List<String> directPermissions = user.getPermissions().stream()
                .filter(UserPermission::isActive)
                .map(up -> up.getPermission().getName())
                .toList();
        // 3. Compute effective permissions via domain service
        Set<String> effectivePermissions = EffectivePermissionCalculator.calculate(user);
        // 4. Combine: Authorities = ROLE_<name> + effective permissions
        Set<String> allAuthorities = new HashSet<>(effectivePermissions);
        activeRoles.forEach(r -> allAuthorities.add("ROLE_" + r.name()));
        return new UserAuthoritiesResult(activeRoles, directPermissions, allAuthorities);
    }

}
