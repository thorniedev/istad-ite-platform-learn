package kh.edu.istad.platform.iam.infrastructure.security;

import kh.edu.istad.platform.iam.application.dto.result.AuthUserResult;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.UUID;

@Getter
public class IamUserDetails implements UserDetails {

    private final Long id;
    private final UUID uuid;
    private final String username;
    private final String password;
    private final boolean enabled;
    private final boolean accountNonExpired;
    private final boolean accountNonLocked;
    private final boolean credentialsNonExpired;
    private final Collection<? extends GrantedAuthority> authorities;

    public IamUserDetails(AuthUserResult user) {
        this.id = user.id();
        this.uuid = user.uuid();
        this.username = user.username();
        this.password = user.passwordHash() != null ? user.passwordHash() : "";
        this.enabled = user.enabled();
        this.accountNonExpired = user.accountNonExpired();
        this.accountNonLocked = user.accountNonLocked();
        this.credentialsNonExpired = user.credentialsNonExpired();
        this.authorities = user.authorities().stream()
                .map(SimpleGrantedAuthority::new)
                .toList();
    }
}