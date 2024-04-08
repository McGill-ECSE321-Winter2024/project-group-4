package ca.mcgill.ecse321.fitnessplusplus.dto;

public class RegisteredUserResponseDto {
  private int userId;
  private String username;
  private String password;
  private String email;
  private int accountRole;
  private String roleType;

  @SuppressWarnings("unused")
  public RegisteredUserResponseDto() {}

  public RegisteredUserResponseDto(
      int userId, String username, String password, String email, int accountRole, String roleType) {
    this.userId = userId;
    this.username = username;
    this.password = password;
    this.email = email;
    this.accountRole = accountRole;
    this.roleType = roleType;
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public int getAccountRole() {
    return accountRole;
  }

  public void setAccountRole(int accountRole) {
    this.accountRole = accountRole;
  }

  public String getRoleType() {
    return roleType;
  }

  public void setRoleType(String roleType) {
    this.roleType = roleType;
  }
}
