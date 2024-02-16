package ca.mcgill.ecse321.fitnessplusplus.model;

import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Staff extends AccountRole {

  // ------------------------
  // MEMBER VARIABLES
  // ------------------------

  // ------------------------
  // CONSTRUCTOR
  // ------------------------

  public Staff() {
    super();
  }

  // ------------------------
  // INTERFACE
  // ------------------------

  public void delete() {
    super.delete();
  }

}