package ca.mcgill.ecse321.fitnessplusplus.dto;

import ca.mcgill.ecse321.fitnessplusplus.model.Client;

public class ClientDto extends AccountRoleDto{

    private int roleId;
    private String roleType;


    public ClientDto(int aRoleId) {
        super(aRoleId, "Client");
        this.roleId = aRoleId;
        this.roleType = "Client";
    }

    public int getRoleId() {
        return roleId;
    }

}
