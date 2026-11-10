package kh.edu.istad.platform.iam.config;


import kh.edu.istad.platform.iam.application.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;

@Configuration
public class TokenConfig {

    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> tokenCustomizer(){
        return context -> {

            Authentication authentication = context.getPrincipal();

            if(authentication == null){
                return;
            }

            var principal = authentication.getPrincipal();

            if (principal instanceof CustomuserDetails customuserDetails) {

                User user = customuserDetails.getUser(); // User from iam application not from spring security "user details"

                if (OAuth2ParameterNames.ACCESS_TOKEN.equals(context.getTokenType().getValue())) {
                    context.getClaims().claims(claims -> {
                        claims.put("preferred_username", user.getUsername());
                        claims.put("email", user.getEmail());
                        claims.put("given_name", user.getGivenName());
                        claims.put("family_name", user.getFamilyName());
                        claims.put("authorities", customuserDetails.getAuthorities().stream()
                                .map(GrantedAuthority::getAuthority)
                                .toList());
                    });
                }

            }

            /** Old
             * if(principal instanceof UserDetails user){
             *                 if(OAuth2ParameterNames.ACCESS_TOKEN.equals(context.getTokenType().getValue())){
             *                     context.getClaims()
             *                             .claims(claim -> {
             *                                 claim.put("preferred_username", user.getUsername());
             *                                 claim.put("authorities", user.getAuthorities());
             *                             });
             *                 }
             *             }
             * */
        };
    }

}
