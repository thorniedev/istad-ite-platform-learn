package kh.edu.istad.platform.iam.restapi.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateUserProfileRequest(
        @NotBlank String givenName,
        String nickname,
        String phoneNumber,
        String locale
) {}