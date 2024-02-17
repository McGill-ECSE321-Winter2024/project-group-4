package ca.mcgill.ecse321.fitnessplusplus.model;

import jakarta.persistence.Entity;

@Entity
public class Instructor extends Staff {

  // ------------------------
  // MEMBER VARIABLES
  // ------------------------

  // ------------------------
  // CONSTRUCTOR
  // ------------------------

  public Instructor(int aRoleId) {
    super(aRoleId);
  }

  public Instructor() {
    super();
  }

  // ------------------------
  // INTERFACE
  // ------------------------

  public void delete() {
    super.delete();
  }

}