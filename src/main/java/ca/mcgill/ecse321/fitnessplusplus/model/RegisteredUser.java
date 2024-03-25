package ca.mcgill.ecse321.fitnessplusplus.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class RegisteredUser {

  // ------------------------
  // MEMBER VARIABLES
  // ------------------------

  // RegisteredUser Attributes
  @Id @GeneratedValue private int userId;
  private String username;
  private String password;
  private String email;

  // RegisteredUser Associations
  @OneToOne private AccountRole accountRole;

  // ------------------------
  // CONSTRUCTOR
  // ------------------------

  public RegisteredUser(String aUsername, String aPassword, String aEmail) {
    username = aUsername;
    password = aPassword;
    email = aEmail;
  }

  public RegisteredUser() {}

  // ------------------------
  // INTERFACE
  // ------------------------

  public boolean setUserId(int aUserId) {
    boolean wasSet = false;
    userId = aUserId;
    wasSet = true;
    return wasSet;
  }

  public boolean setUsername(String aUsername) {
    boolean wasSet = false;
    username = aUsername;
    wasSet = true;
    return wasSet;
  }

  public boolean setPassword(String aPassword) {
    boolean wasSet = false;
    password = aPassword;
    wasSet = true;
    return wasSet;
  }

  public boolean setEmail(String aEmail) {
    boolean wasSet = false;
    email = aEmail;
    wasSet = true;
    return wasSet;
  }

  public int getUserId() {
    return userId;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public String getEmail() {
    return email;
  }

  /* Code from template association_GetOne */
  public AccountRole getAccountRole() {
    return accountRole;
  }

  public boolean hasAccountRole() {
    boolean has = accountRole != null;
    return has;
  }

  /* Code from template association_SetUnidirectionalOptionalOne */
  public boolean setAccountRole(AccountRole aNewAccountRole) {
    boolean wasSet = false;
    accountRole = aNewAccountRole;
    wasSet = true;
    return wasSet;
  }

  public void delete() {
    accountRole = null;
  }
}
