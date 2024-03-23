package ca.mcgill.ecse321.fitnessplusplus.dto;

public class OwnerDto extends StaffDto{

    private int roleId;
    public OwnerDto(int aRoleId) {
        super(aRoleId, "Owner");
        this.roleId = aRoleId;
    }

    public int getRoleId() {
        return roleId;
    }

}
