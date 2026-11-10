package kh.edu.istad.platform.iam.init;


import jakarta.annotation.PostConstruct;
import kh.edu.istad.platform.iam.application.Permission;
import kh.edu.istad.platform.iam.application.Role;
import kh.edu.istad.platform.iam.application.User;
import kh.edu.istad.platform.iam.feature.permission.PermissionRepostitory;
import kh.edu.istad.platform.iam.feature.role.RoleRepository;
import kh.edu.istad.platform.iam.feature.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@RequiredArgsConstructor
@Configuration
public class InitCore {

    private final PermissionRepostitory permissionRepostitory;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        Permission permission = Permission.builder()
                .permission("user:read")
                .description("Read all users")
                .build();

        Role role = Role.builder()
                .role("admin")
                .description("Admin CG")
                .build();

        User user = User.builder()
                .familyName("Kim")
                .givenName("Chanthorn")
                .email("kimchanthon46@gmail.com")
                .gender("Male")
                .emailVerified(true)
                .username("kimchanthorn")
                .password(passwordEncoder.encode("supersecret"))
                // if you want to enable user account because default is false
                .isEnabled(true)
                .isAccountNonExpired(true)
                .isAccountNonLocked(true)
                .isCredentialsNonExpired(true)
                .build();

        if(!userRepository.existsByUsername("kimchanthorn")){
            role = roleRepository.save(role);
            permission = permissionRepostitory.save(permission);
            user.setRoles(Set.of(role));
            user.setPermissions(Set.of(permission));
            userRepository.save(user);
        }

    }
}
