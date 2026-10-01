package com.phuoc.model;

/** Model tuong ung bang UserRoles. */
public class UserRoles_24162100 {
    private int roleId;
    private String roleName;

    public UserRoles_24162100() {
    }

    public UserRoles_24162100(int roleId, String roleName) {
        this.roleId = roleId;
        this.roleName = roleName;
    }

    public int getRoleId() { return roleId; }
    public void setRoleId(int roleId) { this.roleId = roleId; }

    public String getRoleName() { return roleName; }
    public void setRoleName(String roleName) { this.roleName = roleName; }
}
