package ca.mcgill.ecse321.fitnessplusplus.dto;

public class ClientDto extends AccountRoleDto{

    private int roleId;
    public ClientDto(int aRoleId) {
        super(aRoleId, "Client");
        this.roleId = aRoleId;
    }

    public int getRoleId() {
        return roleId;
    }

}
