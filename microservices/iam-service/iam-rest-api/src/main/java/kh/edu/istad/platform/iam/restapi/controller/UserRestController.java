package kh.edu.istad.platform.iam.restapi.controller;

import kh.edu.istad.platform.iam.application.dto.result.UserAuthoritiesResult;
import kh.edu.istad.platform.iam.application.dto.result.UserResult;
import kh.edu.istad.platform.iam.application.usecase.user.*;
import kh.edu.istad.platform.iam.restapi.dto.UserAuthoritiesResponse;
import kh.edu.istad.platform.iam.restapi.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserRestController {

    private final GetUserUseCase getUserUseCase;
    private final DeleteUserUseCase deleteUserUseCase;
    private final EnableUserUseCase enableUserUseCase;
    private final DisableUserUseCase disableUserUseCase;
    private final LockUserUseCase lockUserUseCase;
    private final UnlockUserUseCase unlockUserUseCase;
    private final GetUserAuthoritiesUseCase getUserAuthoritiesUseCase;

    @GetMapping("/{uuid}")
    @PreAuthorize("hasAuthority('user:read')")
    public UserResponse getUser(@PathVariable UUID uuid) {
        UserResult result = getUserUseCase.execute(uuid);
        return new UserResponse(
                result.uuid(),
                result.username(),
                result.email(),
                result.emailVerified(),
                result.givenName() + " " + result.familyName(),
                result.locale(),
                result.enabled(),
                Collections.emptyList(),
                result.createdAt(),
                result.createdAt()
        );
    }

    @DeleteMapping("/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('user:delete')")
    public void deleteUser(@PathVariable UUID uuid) {
        deleteUserUseCase.execute(uuid);
    }

    @PostMapping("/{uuid}/enable")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('user:update')")
    public void enableUser(@PathVariable UUID uuid) {
        enableUserUseCase.execute(uuid);
    }

    @PostMapping("/{uuid}/disable")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('user:update')")
    public void disableUser(@PathVariable UUID uuid) {
        disableUserUseCase.execute(uuid);
    }

    @PostMapping("/{uuid}/lock")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('user:update')")
    public void lockUser(@PathVariable UUID uuid) {
        lockUserUseCase.execute(uuid);
    }

    @PostMapping("/{uuid}/unlock")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('user:update')")
    public void unlockUser(@PathVariable UUID uuid) {
        unlockUserUseCase.execute(uuid);
    }

    @GetMapping("/{uuid}/authorities")
    @PreAuthorize("hasAuthority('user:read')")
    public UserAuthoritiesResponse getUserAuthorities(@PathVariable UUID uuid) {
        UserAuthoritiesResult result = getUserAuthoritiesUseCase.execute(uuid);

        var roles = result.roles().stream()
                .map(r -> new UserAuthoritiesResponse.RoleAssignmentDto(r.uuid(), r.name(), r.expiresAt()))
                .toList();

        var directPermissions = result.directPermissions().stream()
                .map(p -> new UserAuthoritiesResponse.DirectPermissionDto(p, "GRANT"))
                .toList();

        return new UserAuthoritiesResponse(roles, directPermissions, result.effectivePermissions());
    }
}