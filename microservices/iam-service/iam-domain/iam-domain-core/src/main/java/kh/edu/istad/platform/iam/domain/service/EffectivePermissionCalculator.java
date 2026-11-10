package kh.edu.istad.platform.iam.domain.service;

import kh.edu.istad.platform.iam.domain.model.RolePermission;
import kh.edu.istad.platform.iam.domain.model.User;
import kh.edu.istad.platform.iam.domain.model.UserPermission;
import kh.edu.istad.platform.iam.domain.model.UserRole;
import kh.edu.istad.platform.iam.domain.valueobject.PermissionEffect;

import java.util.HashSet;
import java.util.Set;

public class EffectivePermissionCalculator {

    public static Set<String> calculate(User user) {
        Set<String> effectivePermissions = new HashSet<>();

        // Gather all active permissions from active roles
        user.getRoles().stream()
                .filter(UserRole::isActive)
                .map(UserRole::getRole)
                .flatMap(role -> role.getPermissions().stream())
                .filter(RolePermission::isActive)
                .map(rp -> rp.getPermission().getName())
                .forEach(effectivePermissions::add);

        // Add Direct GRANTs
        user.getPermissions().stream()
                .filter(UserPermission::isActive)
                .filter(up -> up.getEffect() == PermissionEffect.GRANT)
                .map(up -> up.getPermission().getName())
                .forEach(effectivePermissions::add);

        // Remove Direct DENYs
        user.getPermissions().stream()
                .filter(UserPermission::isActive)
                .filter(up -> up.getEffect() == PermissionEffect.DENY)
                .map(up -> up.getPermission().getName())
                .forEach(effectivePermissions::remove);
        return effectivePermissions;
    }
}
