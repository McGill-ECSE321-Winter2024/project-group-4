package ca.mcgill.ecse321.fitnessplusplus.dto;

import ca.mcgill.ecse321.fitnessplusplus.model.AccountRole;

public class RegisteredUserDto {

    private String aUsername;
    private String aPassword;
    private String aEmail;

    private AccountRoleDto accountRole;

    public RegisteredUserDto(String aUsername, String aPassword, String aEmail) {
        this.aUsername = aUsername;
        this.aPassword = aPassword;
        this.aEmail = aEmail;
    }

    public RegisteredUserDto(String aUsername, String aPassword, String aEmail, AccountRoleDto accountRole) {
        this.aUsername = aUsername;
        this.aPassword = aPassword;
        this.aEmail = aEmail;
        this.accountRole = accountRole;
    }

    public String getaUsername() {
        return aUsername;
    }

    public String getaPassword() {
        return aPassword;
    }

    public String getaEmail() {
        return aEmail;
    }

    public AccountRoleDto getAccountRole(){
        return accountRole;
    }

    public void setAccountRole(AccountRoleDto accountRole){
        this.accountRole = accountRole;
    }
}
