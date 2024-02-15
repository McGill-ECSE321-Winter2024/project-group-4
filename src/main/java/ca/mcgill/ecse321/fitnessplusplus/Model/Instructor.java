package ca.mcgill.ecse321.fitnessplusplus.Model;

import jakarta.persistence.Entity;

@Entity
public class Instructor extends Staff
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Instructor(RegisteredUser aRegisteredUser)
  {
    super(aRegisteredUser);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {
    super.delete();
  }

}