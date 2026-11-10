package kh.edu.istad.platform.iam.infrastructure.security;

import kh.edu.istad.platform.iam.application.usecase.auth.LoadUserForAuthenticationUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final LoadUserForAuthenticationUseCase loadUserForAuthenticationUseCase;
    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        try {
            var authUser = loadUserForAuthenticationUseCase.execute(usernameOrEmail);
            return new IamUserDetails(authUser);
        } catch (IllegalArgumentException ex) {
            throw new UsernameNotFoundException("User not found: " + usernameOrEmail, ex);
        }
    }
}
