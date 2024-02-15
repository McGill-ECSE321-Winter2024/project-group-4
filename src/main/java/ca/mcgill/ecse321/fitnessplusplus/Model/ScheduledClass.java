package ca.mcgill.ecse321.fitnessplusplus.Model;


import java.sql.Time;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.sql.Date;

@Entity
public class ScheduledClass
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //ScheduledClass Attributes
  @Id
  private int scheduledClassId;
  private Time startTime;
  private Time endTime;
  private Date date;

  //ScheduledClass Associations
  private Staff staff;
  private OfferedClass offeredClass;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public ScheduledClass(int aScheduledClassId, Time aStartTime, Time aEndTime, Date aDate, Staff aStaff, OfferedClass aOfferedClass)
  {
    scheduledClassId = aScheduledClassId;
    startTime = aStartTime;
    endTime = aEndTime;
    date = aDate;
    if (!setStaff(aStaff))
    {
      throw new RuntimeException("Unable to create ScheduledClass due to aStaff. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    if (!setOfferedClass(aOfferedClass))
    {
      throw new RuntimeException("Unable to create ScheduledClass due to aOfferedClass. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setScheduledClassId(int aScheduledClassId)
  {
    boolean wasSet = false;
    scheduledClassId = aScheduledClassId;
    wasSet = true;
    return wasSet;
  }

  public boolean setStartTime(Time aStartTime)
  {
    boolean wasSet = false;
    startTime = aStartTime;
    wasSet = true;
    return wasSet;
  }

  public boolean setEndTime(Time aEndTime)
  {
    boolean wasSet = false;
    endTime = aEndTime;
    wasSet = true;
    return wasSet;
  }

  public boolean setDate(Date aDate)
  {
    boolean wasSet = false;
    date = aDate;
    wasSet = true;
    return wasSet;
  }

  public int getScheduledClassId()
  {
    return scheduledClassId;
  }

  public Time getStartTime()
  {
    return startTime;
  }

  public Time getEndTime()
  {
    return endTime;
  }

  public Date getDate()
  {
    return date;
  }
  /* Code from template association_GetOne */
  public Staff getStaff()
  {
    return staff;
  }
  /* Code from template association_GetOne */
  public OfferedClass getOfferedClass()
  {
    return offeredClass;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setStaff(Staff aNewStaff)
  {
    boolean wasSet = false;
    if (aNewStaff != null)
    {
      staff = aNewStaff;
      wasSet = true;
    }
    return wasSet;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setOfferedClass(OfferedClass aNewOfferedClass)
  {
    boolean wasSet = false;
    if (aNewOfferedClass != null)
    {
      offeredClass = aNewOfferedClass;
      wasSet = true;
    }
    return wasSet;
  }

  public void delete()
  {
    staff = null;
    offeredClass = null;
  }


  public String toString()
  {
    return super.toString() + "["+
            "scheduledClassId" + ":" + getScheduledClassId()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "startTime" + "=" + (getStartTime() != null ? !getStartTime().equals(this)  ? getStartTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "endTime" + "=" + (getEndTime() != null ? !getEndTime().equals(this)  ? getEndTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "date" + "=" + (getDate() != null ? !getDate().equals(this)  ? getDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "staff = "+(getStaff()!=null?Integer.toHexString(System.identityHashCode(getStaff())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "offeredClass = "+(getOfferedClass()!=null?Integer.toHexString(System.identityHashCode(getOfferedClass())):"null");
  }
}