package kh.edu.istad.platform.iam.feature.permission;

import kh.edu.istad.platform.iam.application.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepostitory extends JpaRepository<Permission, Long> {

}
