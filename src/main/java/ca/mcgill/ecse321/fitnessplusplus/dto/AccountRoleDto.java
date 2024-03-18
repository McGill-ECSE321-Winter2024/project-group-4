package ca.mcgill.ecse321.fitnessplusplus.dto;

import ca.mcgill.ecse321.fitnessplusplus.model.AccountRole;

public class AccountRoleDto {

    private int roleId;

    public AccountRoleDto(int aRoleId) {
        this.roleId = aRoleId;
    }

    public int getRoleId() {
        return roleId;
    }
}
