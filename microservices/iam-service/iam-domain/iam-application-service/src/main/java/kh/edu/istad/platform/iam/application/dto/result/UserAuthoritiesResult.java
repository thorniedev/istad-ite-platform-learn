package kh.edu.istad.platform.iam.application.dto.result;

import java.util.List;
import java.util.Set;

public record UserAuthoritiesResult(
        List<RoleAssignmentResult> roles,
        List<String> directPermissions,
        Set<String> effectivePermissions
) {}
