package kh.edu.istad.platform.iam.restapi.controller;

import jakarta.validation.Valid;
import kh.edu.istad.platform.iam.application.dto.command.RegisterUserCommand;
import kh.edu.istad.platform.iam.application.dto.result.UserResult;
import kh.edu.istad.platform.iam.application.usecase.user.RegisterUserUseCase;
import kh.edu.istad.platform.iam.restapi.dto.RegisterUserRequest;
import kh.edu.istad.platform.iam.restapi.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthRestController {

    private final RegisterUserUseCase registerUserUseCase;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse register(@Valid @RequestBody RegisterUserRequest request) {
        RegisterUserCommand command = new RegisterUserCommand(
                request.username(),
                request.email(),
                request.password(),
                request.confirmPassword(),
                request.givenName(),
                request.familyName(),
                request.locale()
        );

        UserResult result = registerUserUseCase.execute(command);

        return new UserResponse(
                result.uuid(),
                result.username(),
                result.email(),
                result.emailVerified(),
                result.givenName() + " " + result.familyName(),
                result.locale(),
                result.enabled(),
                List.of(new UserResponse.RoleSummaryDto(null, "USER")),
                result.createdAt(),
                result.createdAt()
        );
    }
}