package kh.edu.istad.platform.iam.domain.model;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class UserRole extends AuditedAssignment{
    private Long userId;
    private Role role;
}
