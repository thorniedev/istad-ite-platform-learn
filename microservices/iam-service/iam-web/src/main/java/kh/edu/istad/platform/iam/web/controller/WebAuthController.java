package kh.edu.istad.platform.iam.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import kh.edu.istad.platform.iam.application.dto.command.RegisterUserCommand;
import kh.edu.istad.platform.iam.application.usecase.user.RegisterUserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.LocaleResolver;

import java.util.Locale;

@Controller
@RequiredArgsConstructor
public class WebAuthController {

    private final RegisterUserUseCase registerUserUseCase;
    private final MessageSource messageSource;
    private final LocaleResolver localeResolver;

    @GetMapping("/login")
    public String loginPage(
            HttpServletRequest request,
            @RequestParam(value = "error", required = false) String error,
            Model model
    ) {
        Locale locale = localeResolver.resolveLocale(request);
        model.addAttribute("lang", locale.getLanguage());
        model.addAttribute("error", error != null ? "Invalid credentials" : null);
        model.addAttribute("msg", (java.util.function.Function<String, String>) key -> messageSource.getMessage(key, null, locale));
        return "login";
    }

    @GetMapping("/register")
    public String registerPage(HttpServletRequest request, Model model) {
        Locale locale = localeResolver.resolveLocale(request);
        model.addAttribute("lang", locale.getLanguage());
        model.addAttribute("msg", (java.util.function.Function<String, String>) key -> messageSource.getMessage(key, null, locale));
        return "register";
    }

    @PostMapping("/register")
    public String handleRegister(
            HttpServletRequest request,
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String confirmPassword,
            @RequestParam String givenName,
            @RequestParam String familyName,
            Model model
    ) {
        Locale locale = localeResolver.resolveLocale(request);
        try {
            RegisterUserCommand command = new RegisterUserCommand(
                    username, email, password, confirmPassword, givenName, familyName, locale.getLanguage()
            );
            registerUserUseCase.execute(command);
            return "redirect:/login";
        } catch (Exception ex) {
            model.addAttribute("error", ex.getMessage());
            model.addAttribute("lang", locale.getLanguage());
            model.addAttribute("msg", (java.util.function.Function<String, String>) key -> messageSource.getMessage(key, null, locale));
            return "register";
        }
    }

    @GetMapping("/oauth2/consent")
    public String consentPage(
            HttpServletRequest request,
            @RequestParam(value = "client_id", required = false, defaultValue = "Client App") String clientId,
            @RequestParam(value = "scope", required = false, defaultValue = "openid profile email") String scope,
            Model model
    ) {
        Locale locale = localeResolver.resolveLocale(request);
        model.addAttribute("lang", locale.getLanguage());
        model.addAttribute("clientId", clientId);
        model.addAttribute("scopes", java.util.List.of(scope.split(" ")));
        model.addAttribute("msg", (java.util.function.Function<String, String>) key -> messageSource.getMessage(key, null, locale));
        return "consent";
    }
}