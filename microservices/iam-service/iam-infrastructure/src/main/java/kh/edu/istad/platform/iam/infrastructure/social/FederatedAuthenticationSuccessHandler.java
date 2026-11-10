package kh.edu.istad.platform.iam.infrastructure.social;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kh.edu.istad.platform.iam.application.dto.command.FederatedIdentityCommand;
import kh.edu.istad.platform.iam.application.dto.result.AuthUserResult;
import kh.edu.istad.platform.iam.application.usecase.identity.FindOrProvisionFederatedUserUseCase;
import kh.edu.istad.platform.iam.domain.valueobject.SocialProvider;
import kh.edu.istad.platform.iam.infrastructure.security.IamUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class FederatedAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final FindOrProvisionFederatedUserUseCase federatedUserUseCase;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, jakarta.servlet.ServletException {
        if (authentication instanceof OAuth2AuthenticationToken oauthToken) {
            String clientRegistrationId = oauthToken.getAuthorizedClientRegistrationId().toUpperCase();
            SocialProvider provider = SocialProvider.valueOf(clientRegistrationId);
            OAuth2User oauth2User = oauthToken.getPrincipal();

            String providerSubject = oauth2User.getName();
            String email = oauth2User.getAttribute("email");
            String displayName = oauth2User.getAttribute("name");
            String pictureUrl = oauth2User.getAttribute("avatar_url"); // GitHub
            if (pictureUrl == null) pictureUrl = oauth2User.getAttribute("picture"); // Google
            String username = oauth2User.getAttribute("login"); // GitHub username

            FederatedIdentityCommand command = new FederatedIdentityCommand(
                    provider,
                    providerSubject,
                    email != null ? email : providerSubject + "@" + provider.name().toLowerCase() + ".local",
                    email != null,
                    displayName,
                    username,
                    pictureUrl,
                    oauth2User.getAttributes()
            );

            AuthUserResult authUser = federatedUserUseCase.execute(command);
            IamUserDetails userDetails = new IamUserDetails(authUser);

            UsernamePasswordAuthenticationToken newAuth = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
            );
            SecurityContextHolder.getContext().setAuthentication(newAuth);
        }
        super.onAuthenticationSuccess(request, response, SecurityContextHolder.getContext().getAuthentication());
    }
}