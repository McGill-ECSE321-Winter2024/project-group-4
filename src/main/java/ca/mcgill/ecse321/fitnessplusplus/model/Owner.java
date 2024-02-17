package ca.mcgill.ecse321.fitnessplusplus.model;

import jakarta.persistence.Entity;

@Entity
public class Owner extends Staff {

  // ------------------------
  // MEMBER VARIABLES
  // ------------------------

  // ------------------------
  // CONSTRUCTOR
  // ------------------------

  public Owner(int aRoleId) {
    super(aRoleId);
  }

  // ------------------------
  // INTERFACE
  // ------------------------

  public void delete() {
    super.delete();
  }

}