package ca.mcgill.ecse321.fitnessplusplus.dto;

public class RegisteredUserDto {


    private int registeredUserId;
    private String aUsername;
    private String aPassword;
    private String aEmail;

    private AccountRoleDto accountRole;

    public RegisteredUserDto(String aUsername, String aPassword, String aEmail, int registeredUserId) {
        this.aUsername = aUsername;
        this.aPassword = aPassword;
        this.aEmail = aEmail;
        this.registeredUserId = registeredUserId;
    }

    public RegisteredUserDto(String aUsername, String aPassword, String aEmail, int id, AccountRoleDto accountRole) {
        this.aUsername = aUsername;
        this.aPassword = aPassword;
        this.aEmail = aEmail;
        this.accountRole = accountRole;
        this.registeredUserId = id;
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

    public AccountRoleDto getAccountRole() {
        return accountRole;
    }
    public int getRegisteredUserId() {return registeredUserId;}

    public void setAccountRole(AccountRoleDto accountRole) {
        this.accountRole = accountRole;
    }
}
