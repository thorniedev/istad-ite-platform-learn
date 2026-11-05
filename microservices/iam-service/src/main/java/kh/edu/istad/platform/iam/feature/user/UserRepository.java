package kh.edu.istad.platform.iam.feature.user;

import kh.edu.istad.platform.iam.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsername(String username);

    Optional<User> findByUsernameOrEmail(String username, String email);

}
