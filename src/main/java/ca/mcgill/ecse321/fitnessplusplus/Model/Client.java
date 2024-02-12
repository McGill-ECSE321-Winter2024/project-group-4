package ca.mcgill.ecse321.fitnessplusplus.Model;

import jakarta.persistence.Entity;


@Entity
public class Client extends AccountRole
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Client(RegisteredUser aRegisteredUser)
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