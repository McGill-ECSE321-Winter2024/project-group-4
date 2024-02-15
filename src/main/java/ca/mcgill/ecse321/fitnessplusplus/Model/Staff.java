package ca.mcgill.ecse321.fitnessplusplus.Model;

import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Staff extends AccountRole
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Staff(RegisteredUser aRegisteredUser)
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