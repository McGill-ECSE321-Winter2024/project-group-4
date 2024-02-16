package ca.mcgill.ecse321.fitnessplusplus.model;

import jakarta.persistence.Entity;

@Entity
public abstract class Staff extends AccountRole {

  // ------------------------
  // MEMBER VARIABLES
  // ------------------------

  // ------------------------
  // CONSTRUCTOR
  // ------------------------

  public Staff(int aRoleId) {
    super(aRoleId);
  }

  // ------------------------
  // INTERFACE
  // ------------------------

  public void delete() {
    super.delete();
  }

}