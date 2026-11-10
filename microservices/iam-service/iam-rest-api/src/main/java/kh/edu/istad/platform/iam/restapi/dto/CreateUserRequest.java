package kh.edu.istad.platform.iam.restapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record CreateUserRequest(
        @NotBlank @Size(min = 3, max = 50) String username,
        @NotBlank @Email String email,
        @NotBlank @Size(min = 8) String password,
        @NotBlank String givenName,
        @NotBlank String familyName,
        String gender,
        LocalDate birthdate,
        String phoneNumber,
        String zoneinfo,
        String locale,
        AddressDto address,
        List<UUID> roleUuids
) {
    public record AddressDto(
            String streetAddress,
            String locality,
            String region,
            String postalCode,
            String country
    ) {}
}