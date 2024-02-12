package ca.mcgill.ecse321.fitnessplusplus.Model;


import java.util.*;
import java.sql.Time;
import java.sql.Date;


public class OfferedClass
{


  //OfferedClass Attributes
  private int classId;
  private String classType;
  private String description;

  //OfferedClass Associations
  private List<ScheduledClass> scheduledClasses;



  public OfferedClass(int aClassId, String aClassType, String aDescription)
  {
    classId = aClassId;
    classType = aClassType;
    description = aDescription;
    scheduledClasses = new ArrayList<ScheduledClass>();
  }


  public boolean setClassId(int aClassId)
  {
    boolean wasSet = false;
    classId = aClassId;
    wasSet = true;
    return wasSet;
  }

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

  public int getClassId()
  {
    return classId;
  }

  public String getClassType()
  {
    return classType;
  }

  public String getDescription()
  {
    return description;
  }
  /* Code from template association_GetMany */
  public ScheduledClass getScheduledClass(int index)
  {
    ScheduledClass aScheduledClass = scheduledClasses.get(index);
    return aScheduledClass;
  }

  public List<ScheduledClass> getScheduledClasses()
  {
    List<ScheduledClass> newScheduledClasses = Collections.unmodifiableList(scheduledClasses);
    return newScheduledClasses;
  }

  public int numberOfScheduledClasses()
  {
    int number = scheduledClasses.size();
    return number;
  }

  public boolean hasScheduledClasses()
  {
    boolean has = scheduledClasses.size() > 0;
    return has;
  }

  public int indexOfScheduledClass(ScheduledClass aScheduledClass)
  {
    int index = scheduledClasses.indexOf(aScheduledClass);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfScheduledClasses()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public ScheduledClass addScheduledClass(int aScheduledClassId, Time aStartTime, Time aEndTime, Date aDate)
  {
    return new ScheduledClass(aScheduledClassId, aStartTime, aEndTime, aDate, this);
  }

  public boolean addScheduledClass(ScheduledClass aScheduledClass)
  {
    boolean wasAdded = false;
    if (scheduledClasses.contains(aScheduledClass)) { return false; }
    OfferedClass existingOfferedClass = aScheduledClass.getOfferedClass();
    boolean isNewOfferedClass = existingOfferedClass != null && !this.equals(existingOfferedClass);
    if (isNewOfferedClass)
    {
      aScheduledClass.setOfferedClass(this);
    }
    else
    {
      scheduledClasses.add(aScheduledClass);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeScheduledClass(ScheduledClass aScheduledClass)
  {
    boolean wasRemoved = false;
    //Unable to remove aScheduledClass, as it must always have a offeredClass
    if (!this.equals(aScheduledClass.getOfferedClass()))
    {
      scheduledClasses.remove(aScheduledClass);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addScheduledClassAt(ScheduledClass aScheduledClass, int index)
  {  
    boolean wasAdded = false;
    if(addScheduledClass(aScheduledClass))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfScheduledClasses()) { index = numberOfScheduledClasses() - 1; }
      scheduledClasses.remove(aScheduledClass);
      scheduledClasses.add(index, aScheduledClass);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveScheduledClassAt(ScheduledClass aScheduledClass, int index)
  {
    boolean wasAdded = false;
    if(scheduledClasses.contains(aScheduledClass))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfScheduledClasses()) { index = numberOfScheduledClasses() - 1; }
      scheduledClasses.remove(aScheduledClass);
      scheduledClasses.add(index, aScheduledClass);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addScheduledClassAt(aScheduledClass, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    for(int i=scheduledClasses.size(); i > 0; i--)
    {
      ScheduledClass aScheduledClass = scheduledClasses.get(i - 1);
      aScheduledClass.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "classId" + ":" + getClassId()+ "," +
            "classType" + ":" + getClassType()+ "," +
            "description" + ":" + getDescription()+ "]";
  }
}