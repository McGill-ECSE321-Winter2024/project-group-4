package ca.mcgill.ecse321.fitnessplusplus.dto;

public class StaffDto extends AccountRoleDto{

    private int roleId;
    private String roleType;

    public StaffDto(int aRoleId, String roleType) {
        super(aRoleId, roleType);
        this.roleId = aRoleId;
        this.roleType = roleType;
    }

    public int getRoleId() {
        return roleId;
    }

}
