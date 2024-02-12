package ca.mcgill.ecse321.fitnessplusplus.Model;


import java.sql.Time;
import java.sql.Date;


public class ScheduledClass
{


  private int scheduledClassId;
  private Time startTime;
  private Time endTime;
  private Date date;


  private Staff staff;
  private OfferedClass offeredClass;



  public ScheduledClass(int aScheduledClassId, Time aStartTime, Time aEndTime, Date aDate, Staff aStaff, OfferedClass aOfferedClass)
  {
    scheduledClassId = aScheduledClassId;
    startTime = aStartTime;
    endTime = aEndTime;
    date = aDate;
    boolean didAddStaff = setStaff(aStaff);
    if (!didAddStaff)
    {
      throw new RuntimeException("Unable to create scheduledClass due to staff. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddOfferedClass = setOfferedClass(aOfferedClass);
    if (!didAddOfferedClass)
    {
      throw new RuntimeException("Unable to create scheduledClass due to offeredClass. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }


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
  /* Code from template association_SetOneToMany */
  public boolean setStaff(Staff aStaff)
  {
    boolean wasSet = false;
    if (aStaff == null)
    {
      return wasSet;
    }

    Staff existingStaff = staff;
    staff = aStaff;
    if (existingStaff != null && !existingStaff.equals(aStaff))
    {
      existingStaff.removeScheduledClass(this);
    }
    staff.addScheduledClass(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setOfferedClass(OfferedClass aOfferedClass)
  {
    boolean wasSet = false;
    if (aOfferedClass == null)
    {
      return wasSet;
    }

    OfferedClass existingOfferedClass = offeredClass;
    offeredClass = aOfferedClass;
    if (existingOfferedClass != null && !existingOfferedClass.equals(aOfferedClass))
    {
      existingOfferedClass.removeScheduledClass(this);
    }
    offeredClass.addScheduledClass(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Staff placeholderStaff = staff;
    this.staff = null;
    if(placeholderStaff != null)
    {
      placeholderStaff.removeScheduledClass(this);
    }
    OfferedClass placeholderOfferedClass = offeredClass;
    this.offeredClass = null;
    if(placeholderOfferedClass != null)
    {
      placeholderOfferedClass.removeScheduledClass(this);
    }
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