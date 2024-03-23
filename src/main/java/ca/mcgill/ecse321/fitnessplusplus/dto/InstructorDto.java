package ca.mcgill.ecse321.fitnessplusplus.dto;

public class InstructorDto extends StaffDto{

    private int roleId;
    private String roleType;

    public InstructorDto(int aRoleId) {
        super(aRoleId, "Instructor");
        this.roleId = aRoleId;
        this.roleType = "Instructor";
    }

    public int getRoleId() {
        return roleId;
    }
}
