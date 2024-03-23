package ca.mcgill.ecse321.fitnessplusplus.dto;

public class StaffDto extends AccountRoleDto{

    private int roleId;
    public StaffDto(int aRoleId, String roleType) {
        super(aRoleId, roleType);
        this.roleId = aRoleId;
    }

    public int getRoleId() {
        return roleId;
    }

}
