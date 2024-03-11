package ca.mcgill.ecse321.fitnessplusplus.dto;

public class InstructorDto {

    private int roleId;

    public InstructorDto(int aRoleId) {
        this.roleId = aRoleId;
    }

    public int getRoleId() {
        return roleId;
    }
}
