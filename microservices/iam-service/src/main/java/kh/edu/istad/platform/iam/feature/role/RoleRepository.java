package kh.edu.istad.platform.iam.feature.role;

import kh.edu.istad.platform.iam.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {

}
