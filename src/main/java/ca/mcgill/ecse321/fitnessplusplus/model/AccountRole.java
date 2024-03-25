package ca.mcgill.ecse321.fitnessplusplus.model;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class AccountRole {

  // ------------------------
  // MEMBER VARIABLES
  // ------------------------

  // AccountRole Attributes
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int roleId;

  // ------------------------
  // CONSTRUCTOR
  // ------------------------

  public AccountRole(int aRoleId) {
    roleId = aRoleId;
  }

  public AccountRole() {}

  // ------------------------
  // INTERFACE
  // ------------------------

  public boolean setRoleId(int aRoleId) {
    boolean wasSet = false;
    roleId = aRoleId;
    wasSet = true;
    return wasSet;
  }

  public int getRoleId() {
    return roleId;
  }

  public void delete() {}
}
