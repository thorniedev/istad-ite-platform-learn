package kh.edu.istad.platform.iam.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import kh.edu.istad.platform.iam.application.dto.result.UserAuthoritiesResult;
import kh.edu.istad.platform.iam.application.dto.result.UserResult;
import kh.edu.istad.platform.iam.application.usecase.user.GetUserAuthoritiesUseCase;
import kh.edu.istad.platform.iam.application.usecase.user.GetUserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.LocaleResolver;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class WebProfileController {

    private final GetUserUseCase getUserUseCase;
    private final GetUserAuthoritiesUseCase authoritiesUseCase;
    private final MessageSource messageSource;
    private final LocaleResolver localeResolver;

    @GetMapping("/")
    public String home(HttpServletRequest request, @AuthenticationPrincipal Object principal, Model model) {
        Locale locale = localeResolver.resolveLocale(request);
        model.addAttribute("lang", locale.getLanguage());
        model.addAttribute("msg", (java.util.function.Function<String, String>) key -> messageSource.getMessage(key, null, locale));

        String username = "user";
        String fullName = "Standard User";
        List<String> roles = List.of("USER");
        List<String> perms = List.of("user:read");

        if (principal instanceof Jwt jwt) {
            UUID uuid = UUID.fromString(jwt.getSubject());
            UserResult user = getUserUseCase.execute(uuid);
            UserAuthoritiesResult auth = authoritiesUseCase.execute(uuid);
            username = user.username();
            fullName = user.givenName() + " " + user.familyName();
            roles = auth.roles().stream().map(r -> r.name()).toList();
            perms = auth.effectivePermissions().stream().toList();
        }

        model.addAttribute("username", username);
        model.addAttribute("fullName", fullName);
        model.addAttribute("roles", roles);
        model.addAttribute("permissions", perms);

        return "index";
    }

    @GetMapping("/profile/edit")
    public String editProfile(HttpServletRequest request, Model model) {
        Locale locale = localeResolver.resolveLocale(request);
        model.addAttribute("lang", locale.getLanguage());
        model.addAttribute("givenName", "");
        model.addAttribute("familyName", "");
        model.addAttribute("msg", (java.util.function.Function<String, String>) key -> messageSource.getMessage(key, null, locale));
        return "profile-edit";
    }

    @GetMapping("/profile/password")
    public String changePassword(HttpServletRequest request, Model model) {
        Locale locale = localeResolver.resolveLocale(request);
        model.addAttribute("lang", locale.getLanguage());
        model.addAttribute("msg", (java.util.function.Function<String, String>) key -> messageSource.getMessage(key, null, locale));
        return "change-password";
    }

    @GetMapping("/profile/accounts")
    public String linkedAccounts(HttpServletRequest request, Model model) {
        Locale locale = localeResolver.resolveLocale(request);
        model.addAttribute("lang", locale.getLanguage());
        model.addAttribute("providers", List.of("Google", "GitHub"));
        model.addAttribute("msg", (java.util.function.Function<String, String>) key -> messageSource.getMessage(key, null, locale));
        return "linked-accounts";
    }

    @GetMapping("/profile/apps")
    public String connectedApps(HttpServletRequest request, Model model) {
        Locale locale = localeResolver.resolveLocale(request);
        model.addAttribute("lang", locale.getLanguage());
        model.addAttribute("apps", Collections.emptyList());
        model.addAttribute("msg", (java.util.function.Function<String, String>) key -> messageSource.getMessage(key, null, locale));
        return "connected-apps";
    }

    @PostMapping("/profile/edit")
    public String handleEditProfile(
            HttpServletRequest request,
            @RequestParam String givenName,
            @RequestParam String familyName
    ) {
        // Handle update
        return "redirect:/";
    }
    @PostMapping("/profile/password")
    public String handleChangePassword(
            HttpServletRequest request,
            @RequestParam String currentPassword,
            @RequestParam String newPassword,
            @RequestParam String confirmPassword
    ) {
        // Handle password change
        return "redirect:/";
    }

}