package ca.mcgill.ecse321.fitnessplusplus.Model;




public class RegisteredUser
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //RegisteredUser Attributes
  private int userId;
  private String username;
  private String password;
  private String email;

  //RegisteredUser Associations
  private AccountRole accountRole;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public RegisteredUser(int aUserId, String aUsername, String aPassword, String aEmail, AccountRole aAccountRole)
  {
    userId = aUserId;
    username = aUsername;
    password = aPassword;
    email = aEmail;
    if (aAccountRole == null || aAccountRole.getRegisteredUser() != null)
    {
      throw new RuntimeException("Unable to create RegisteredUser due to aAccountRole. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    accountRole = aAccountRole;
  }

  public RegisteredUser(int aUserId, String aUsername, String aPassword, String aEmail)
  {
    userId = aUserId;
    username = aUsername;
    password = aPassword;
    email = aEmail;
    // default set to Client. 
    accountRole = new Client(this);
  }


  public boolean setUserId(int aUserId)
  {
    boolean wasSet = false;
    userId = aUserId;
    wasSet = true;
    return wasSet;
  }

  public boolean setUsername(String aUsername)
  {
    boolean wasSet = false;
    username = aUsername;
    wasSet = true;
    return wasSet;
  }

  public boolean setPassword(String aPassword)
  {
    boolean wasSet = false;
    password = aPassword;
    wasSet = true;
    return wasSet;
  }

  public boolean setEmail(String aEmail)
  {
    boolean wasSet = false;
    email = aEmail;
    wasSet = true;
    return wasSet;
  }

  public int getUserId()
  {
    return userId;
  }

  public String getUsername()
  {
    return username;
  }

  public String getPassword()
  {
    return password;
  }

  public String getEmail()
  {
    return email;
  }
  /* Code from template association_GetOne */
  public AccountRole getAccountRole()
  {
    return accountRole;
  }

  public void delete()
  {
    AccountRole existingAccountRole = accountRole;
    accountRole = null;
    if (existingAccountRole != null)
    {
      existingAccountRole.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "userId" + ":" + getUserId()+ "," +
            "username" + ":" + getUsername()+ "," +
            "password" + ":" + getPassword()+ "," +
            "email" + ":" + getEmail()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "accountRole = "+(getAccountRole()!=null?Integer.toHexString(System.identityHashCode(getAccountRole())):"null");
  }
}