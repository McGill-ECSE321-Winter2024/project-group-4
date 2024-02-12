package ca.mcgill.ecse321.fitnessplusplus.Model;


import java.util.*;
import java.sql.Time;
import java.sql.Date;


public abstract class Staff extends AccountRole
{


  private List<ScheduledClass> scheduledClasses;


  public Staff(RegisteredUser aRegisteredUser)
  {
    super(aRegisteredUser);
    scheduledClasses = new ArrayList<ScheduledClass>();
  }


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
  public ScheduledClass addScheduledClass(int aScheduledClassId, Time aStartTime, Time aEndTime, Date aDate, OfferedClass aOfferedClass)
  {
    return new ScheduledClass(aScheduledClassId, aStartTime, aEndTime, aDate, this, aOfferedClass);
  }

  public boolean addScheduledClass(ScheduledClass aScheduledClass)
  {
    boolean wasAdded = false;
    if (scheduledClasses.contains(aScheduledClass)) { return false; }
    Staff existingStaff = aScheduledClass.getStaff();
    boolean isNewStaff = existingStaff != null && !this.equals(existingStaff);
    if (isNewStaff)
    {
      aScheduledClass.setStaff(this);
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
    //Unable to remove aScheduledClass, as it must always have a staff
    if (!this.equals(aScheduledClass.getStaff()))
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
    super.delete();
  }

}