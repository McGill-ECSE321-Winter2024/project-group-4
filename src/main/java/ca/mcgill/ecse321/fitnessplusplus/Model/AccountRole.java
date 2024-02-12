package ca.mcgill.ecse321.fitnessplusplus.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

@Entity
@Inheritance(strategy=InheritanceType.JOINED)
public abstract class AccountRole
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //AccountRole Associations
  private RegisteredUser registeredUser;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public AccountRole(RegisteredUser aRegisteredUser)
  {
    boolean didAddRegisteredUser = setRegisteredUser(aRegisteredUser);
    if (!didAddRegisteredUser)
    {
      throw new RuntimeException("Unable to create accountRole due to registeredUser. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
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
  /* Code from template association_SetOneToAtMostN */
  public boolean setRegisteredUser(RegisteredUser aRegisteredUser)
  {
    boolean wasSet = false;
    //Must provide registeredUser to accountRole
    if (aRegisteredUser == null)
    {
      return wasSet;
    }

    //registeredUser already at maximum (2)
    if (aRegisteredUser.numberOfAccountRoles() >= RegisteredUser.maximumNumberOfAccountRoles())
    {
      return wasSet;
    }
    
    RegisteredUser existingRegisteredUser = registeredUser;
    registeredUser = aRegisteredUser;
    if (existingRegisteredUser != null && !existingRegisteredUser.equals(aRegisteredUser))
    {
      boolean didRemove = existingRegisteredUser.removeAccountRole(this);
      if (!didRemove)
      {
        registeredUser = existingRegisteredUser;
        return wasSet;
      }
    }
    registeredUser.addAccountRole(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    RegisteredUser placeholderRegisteredUser = registeredUser;
    this.registeredUser = null;
    if(placeholderRegisteredUser != null)
    {
      placeholderRegisteredUser.removeAccountRole(this);
    }
  }

}