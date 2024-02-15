package ca.mcgill.ecse321.fitnessplusplus.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class RegisteredUser
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //RegisteredUser Attributes
  @Id
  @GeneratedValue (strategy = GenerationType.IDENTITY)
  private int userId;
  private String username;
  private String password;
  private String email;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public RegisteredUser(int aUserId, String aUsername, String aPassword, String aEmail)
  {
    userId = aUserId;
    username = aUsername;
    password = aPassword;
    email = aEmail;
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

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "userId" + ":" + getUserId()+ "," +
            "username" + ":" + getUsername()+ "," +
            "password" + ":" + getPassword()+ "," +
            "email" + ":" + getEmail()+ "]";
  }
}