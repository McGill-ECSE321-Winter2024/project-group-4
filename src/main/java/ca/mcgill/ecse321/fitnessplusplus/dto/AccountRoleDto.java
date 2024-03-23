package ca.mcgill.ecse321.fitnessplusplus.dto;

public abstract class AccountRoleDto {

    private int roleId;
    private String roleType;

    public AccountRoleDto(int aRoleId, String roleType) {
        this.roleId = aRoleId;
        this.roleType = roleType;
    }

    public int getRoleId() {
        return roleId;
    }

    public String getRoleType() {
        return roleType;
    }
}
