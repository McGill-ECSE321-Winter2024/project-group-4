package ca.mcgill.ecse321.fitnessplusplus.Model;


public abstract class AccountRole
{


  private RegisteredUser registeredUser;



  public AccountRole(RegisteredUser aRegisteredUser)
  {
    if (aRegisteredUser == null || aRegisteredUser.getAccountRole() != null)
    {
      throw new RuntimeException("Unable to create AccountRole due to aRegisteredUser. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    registeredUser = aRegisteredUser;
  }

  public AccountRole(int aUserIdForRegisteredUser, String aUsernameForRegisteredUser, String aPasswordForRegisteredUser, String aEmailForRegisteredUser)
  {
    registeredUser = new RegisteredUser(aUserIdForRegisteredUser, aUsernameForRegisteredUser, aPasswordForRegisteredUser, aEmailForRegisteredUser, this);
  }


  public RegisteredUser getRegisteredUser()
  {
    return registeredUser;
  }

  public void delete()
  {
    RegisteredUser existingRegisteredUser = registeredUser;
    registeredUser = null;
    if (existingRegisteredUser != null)
    {
      existingRegisteredUser.delete();
    }
  }

}