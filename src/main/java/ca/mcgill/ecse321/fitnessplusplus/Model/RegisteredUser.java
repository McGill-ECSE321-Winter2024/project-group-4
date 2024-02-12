package ca.mcgill.ecse321.fitnessplusplus.Model;


import java.util.*;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class RegisteredUser
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //RegisteredUser Attributes
  @Id
  @GeneratedValue
  private int userId;
  private String username;
  private String password;
  private String email;

  //RegisteredUser Associations
  private List<AccountRole> accountRoles;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public RegisteredUser(int aUserId, String aUsername, String aPassword, String aEmail)
  {
    userId = aUserId;
    username = aUsername;
    password = aPassword;
    email = aEmail;
    accountRoles = new ArrayList<AccountRole>();
  }

  //------------------------
  // INTERFACE
  //------------------------

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
  /* Code from template association_GetMany */
  public AccountRole getAccountRole(int index)
  {
    AccountRole aAccountRole = accountRoles.get(index);
    return aAccountRole;
  }

  public List<AccountRole> getAccountRoles()
  {
    List<AccountRole> newAccountRoles = Collections.unmodifiableList(accountRoles);
    return newAccountRoles;
  }

  public int numberOfAccountRoles()
  {
    int number = accountRoles.size();
    return number;
  }

  public boolean hasAccountRoles()
  {
    boolean has = accountRoles.size() > 0;
    return has;
  }

  public int indexOfAccountRole(AccountRole aAccountRole)
  {
    int index = accountRoles.indexOf(aAccountRole);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfAccountRoles()
  {
    return 0;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfAccountRoles()
  {
    return 2;
  }
  /* Code from template association_AddOptionalNToOne */


  public boolean addAccountRole(AccountRole aAccountRole)
  {
    boolean wasAdded = false;
    if (accountRoles.contains(aAccountRole)) { return false; }
    if (numberOfAccountRoles() >= maximumNumberOfAccountRoles())
    {
      return wasAdded;
    }

    RegisteredUser existingRegisteredUser = aAccountRole.getRegisteredUser();
    boolean isNewRegisteredUser = existingRegisteredUser != null && !this.equals(existingRegisteredUser);
    if (isNewRegisteredUser)
    {
      aAccountRole.setRegisteredUser(this);
    }
    else
    {
      accountRoles.add(aAccountRole);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeAccountRole(AccountRole aAccountRole)
  {
    boolean wasRemoved = false;
    //Unable to remove aAccountRole, as it must always have a registeredUser
    if (!this.equals(aAccountRole.getRegisteredUser()))
    {
      accountRoles.remove(aAccountRole);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addAccountRoleAt(AccountRole aAccountRole, int index)
  {  
    boolean wasAdded = false;
    if(addAccountRole(aAccountRole))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfAccountRoles()) { index = numberOfAccountRoles() - 1; }
      accountRoles.remove(aAccountRole);
      accountRoles.add(index, aAccountRole);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveAccountRoleAt(AccountRole aAccountRole, int index)
  {
    boolean wasAdded = false;
    if(accountRoles.contains(aAccountRole))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfAccountRoles()) { index = numberOfAccountRoles() - 1; }
      accountRoles.remove(aAccountRole);
      accountRoles.add(index, aAccountRole);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addAccountRoleAt(aAccountRole, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    for(int i=accountRoles.size(); i > 0; i--)
    {
      AccountRole aAccountRole = accountRoles.get(i - 1);
      aAccountRole.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "userId" + ":" + getUserId()+ "," +
            "username" + ":" + getUsername()+ "," +
            "password" + ":" + getPassword()+ "," +
            "email" + ":" + getEmail()+ "]";
  }
}