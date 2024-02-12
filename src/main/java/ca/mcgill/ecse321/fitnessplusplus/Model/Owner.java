package ca.mcgill.ecse321.fitnessplusplus.Model;

import jakarta.persistence.Entity;


@Entity
public class Owner extends Staff
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Owner(RegisteredUser aRegisteredUser)
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