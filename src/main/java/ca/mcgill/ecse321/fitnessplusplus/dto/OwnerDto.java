package ca.mcgill.ecse321.fitnessplusplus.dto;

public class OwnerDto {

    private int roleId;

    public OwnerDto(int aRoleId) {
        this.roleId = aRoleId;
    }

    public int getRoleId() {
        return roleId;
    }

}
