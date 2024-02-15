package ca.mcgill.ecse321.fitnessplusplus.Model;

import jakarta.persistence.Entity;

@Entity
public class Owner extends Staff
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Owner Associations
  private RegisteredUser registeredUser;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Owner(RegisteredUser aRegisteredUser)
  {
    super();
    if (!setRegisteredUser(aRegisteredUser))
    {
      throw new RuntimeException("Unable to create Owner due to aRegisteredUser. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public RegisteredUser getRegisteredUser()
  {
    return registeredUser;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setRegisteredUser(RegisteredUser aNewRegisteredUser)
  {
    boolean wasSet = false;
    if (aNewRegisteredUser != null)
    {
      registeredUser = aNewRegisteredUser;
      wasSet = true;
    }
    return wasSet;
  }

  public void delete()
  {
    registeredUser = null;
    super.delete();
  }

}