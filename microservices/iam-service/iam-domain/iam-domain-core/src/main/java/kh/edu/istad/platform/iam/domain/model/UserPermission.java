package kh.edu.istad.platform.iam.domain.model;


import kh.edu.istad.platform.iam.domain.valueobject.PermissionEffect;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class UserPermission extends AuditedAssignment {
    private Long userId;
    private Permission permission;
    private PermissionEffect effect;
}
