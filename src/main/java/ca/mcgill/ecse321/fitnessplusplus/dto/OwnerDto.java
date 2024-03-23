package ca.mcgill.ecse321.fitnessplusplus.dto;

public class OwnerDto extends StaffDto{

    private int roleId;
    private String roleType;

    public OwnerDto(int aRoleId) {
        super(aRoleId, "Owner");
        this.roleId = aRoleId;
        this.roleType = "Owner";
    }

    public int getRoleId() {
        return roleId;
    }

}
