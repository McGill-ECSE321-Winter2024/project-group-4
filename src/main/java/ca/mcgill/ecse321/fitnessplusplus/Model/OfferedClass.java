package ca.mcgill.ecse321.fitnessplusplus.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class OfferedClass
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //OfferedClass Attributesi
  @Id
  private String classType;
  private String description;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public OfferedClass(String aClassType, String aDescription)
  {
    classType = aClassType;
    description = aDescription;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setClassType(String aClassType)
  {
    boolean wasSet = false;
    classType = aClassType;
    wasSet = true;
    return wasSet;
  }

  public boolean setDescription(String aDescription)
  {
    boolean wasSet = false;
    description = aDescription;
    wasSet = true;
    return wasSet;
  }

  public String getClassType()
  {
    return classType;
  }

  public String getDescription()
  {
    return description;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "classType" + ":" + getClassType()+ "," +
            "description" + ":" + getDescription()+ "]";
  }
}