package kh.edu.istad.platform.iam.restapi.controller;

import kh.edu.istad.platform.iam.application.dto.result.UserResult;
import kh.edu.istad.platform.iam.application.usecase.user.GetUserUseCase;
import kh.edu.istad.platform.iam.restapi.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.UUID;

@RestController
@RequestMapping("/api/me")
@RequiredArgsConstructor
public class MeRestController {

    private final GetUserUseCase getUserUseCase;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public UserResponse getMe(@AuthenticationPrincipal Jwt jwt) {
        UUID userUuid = UUID.fromString(jwt.getSubject());
        UserResult result = getUserUseCase.execute(userUuid);

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
}