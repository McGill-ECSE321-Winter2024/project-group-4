package ca.mcgill.ecse321.fitnessplusplus.dto;

public class InstructorDto extends StaffDto{

    private int roleId;
    public InstructorDto(int aRoleId) {
        super(aRoleId, "Instructor");
        this.roleId = aRoleId;
    }

    public int getRoleId() {
        return roleId;
    }
}
