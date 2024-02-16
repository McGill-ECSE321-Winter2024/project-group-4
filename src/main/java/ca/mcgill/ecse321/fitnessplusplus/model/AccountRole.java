package ca.mcgill.ecse321.fitnessplusplus.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class AccountRole {

  // ------------------------
  // MEMBER VARIABLES
  // ------------------------

  // AccountRole Attributes
  @Id
  @GeneratedValue
  private int roleId;

  // ------------------------
  // CONSTRUCTOR
  // ------------------------

  public AccountRole(int aRoleId) {
    roleId = aRoleId;
  }

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

  public void delete() {
  }

  public String toString() {
    return super.toString() + "[" +
        "roleId" + ":" + getRoleId() + "]";
  }
}