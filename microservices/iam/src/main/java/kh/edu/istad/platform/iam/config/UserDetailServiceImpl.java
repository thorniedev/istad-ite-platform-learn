package kh.edu.istad.platform.iam.config;

import kh.edu.istad.platform.iam.feature.user.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var existing = userRepository.findByUsernameOrEmail(username, username).orElseThrow(
                () -> new UsernameNotFoundException("Username not found")
        );

        Set<GrantedAuthority> authorities = new HashSet<>();

        // Add permissions
        if (existing.getPermissions() != null) {
            existing.getPermissions().forEach(permission -> {
                        authorities.add(
                                new SimpleGrantedAuthority(permission.getPermission())
                        );
                    }
            );
        }

        // Add roles with "ROLE_" prefix
        if (existing.getRoles() == null) {
            existing.getRoles().forEach(role -> {
                authorities.add(
                        new SimpleGrantedAuthority("ROLE_" + role.getRole())
                );
                }
            );
        }

        // change return customUserDetails
        return new CustomuserDetails(existing, authorities);

        // this is default spring security user details
        /*
        return new User(
                existing.getUsername(),
                existing.getPassword(),
                existing.isEnabled(),
                existing.isAccountNonExpired(),
                existing.isCredentialsNonExpired(),
                existing.isAccountNonLocked(),
                authorities
        ); */
    }
}
