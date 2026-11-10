package kh.edu.istad.platform.iam.infrastructure.adapter;

import kh.edu.istad.platform.iam.application.port.out.CurrentActorProviderPort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class SecurityCurrentActorProviderAdapter implements CurrentActorProviderPort {
    @Override
    public String getCurrentActorSub() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return "SYSTEM";
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof Jwt jwt) {
            return jwt.getSubject();
        }
        return authentication.getName();
    }
}
