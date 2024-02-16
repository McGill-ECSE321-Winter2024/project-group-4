package ca.mcgill.ecse321.fitnessplusplus.model;

import jakarta.persistence.Entity;

@Entity
public class Client extends AccountRole {

  // ------------------------
  // MEMBER VARIABLES
  // ------------------------

  // ------------------------
  // CONSTRUCTOR
  // ------------------------

  public Client(int aRoleId) {
    super(aRoleId);
  }

  // ------------------------
  // INTERFACE
  // ------------------------

  public void delete() {
    super.delete();
  }

}