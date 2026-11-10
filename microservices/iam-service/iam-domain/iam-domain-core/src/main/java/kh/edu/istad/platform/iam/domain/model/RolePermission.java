package kh.edu.istad.platform.iam.domain.model;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class RolePermission extends AuditedAssignment{
    private Long roleId;
    private Permission permission;
}
