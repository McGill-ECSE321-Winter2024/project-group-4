package ca.mcgill.ecse321.fitnessplusplus.model;

import java.sql.Time;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.sql.Date;

// line 31 "model.ump"
// line 89 "model.ump"
@Entity
public class ScheduledClass {

  // ------------------------
  // MEMBER VARIABLES
  // ------------------------

  // ScheduledClass Attributes
  @Id
  @GeneratedValue
  private int scheduledClassId;
  private Time startTime;
  private Time endTime;
  private Date date;

  // ScheduledClass Associations
  @ManyToOne
  private OfferedClass offeredClass;
  @ManyToOne
  private Instructor instructor;

  // ------------------------
  // CONSTRUCTOR
  // ------------------------

  public ScheduledClass(int aScheduledClassId, Time aStartTime, Time aEndTime, Date aDate, OfferedClass aOfferedClass,
      Instructor aInstructor) {
    scheduledClassId = aScheduledClassId;
    startTime = aStartTime;
    endTime = aEndTime;
    date = aDate;
    if (!setOfferedClass(aOfferedClass)) {
      throw new RuntimeException(
          "Unable to create ScheduledClass due to aOfferedClass. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    if (!setInstructor(aInstructor)) {
      throw new RuntimeException(
          "Unable to create ScheduledClass due to aInstructor. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  public ScheduledClass() {

  }

  // ------------------------
  // INTERFACE
  // ------------------------

  public boolean setScheduledClassId(int aScheduledClassId) {
    boolean wasSet = false;
    scheduledClassId = aScheduledClassId;
    wasSet = true;
    return wasSet;
  }

  public boolean setStartTime(Time aStartTime) {
    boolean wasSet = false;
    startTime = aStartTime;
    wasSet = true;
    return wasSet;
  }

  public boolean setEndTime(Time aEndTime) {
    boolean wasSet = false;
    endTime = aEndTime;
    wasSet = true;
    return wasSet;
  }

  public boolean setDate(Date aDate) {
    boolean wasSet = false;
    date = aDate;
    wasSet = true;
    return wasSet;
  }

  public int getScheduledClassId() {
    return scheduledClassId;
  }

  public Time getStartTime() {
    return startTime;
  }

  public Time getEndTime() {
    return endTime;
  }

  public Date getDate() {
    return date;
  }

  /* Code from template association_GetOne */
  public OfferedClass getOfferedClass() {
    return offeredClass;
  }

  /* Code from template association_GetOne */
  public Instructor getInstructor() {
    return instructor;
  }

  /* Code from template association_SetUnidirectionalOne */
  public boolean setOfferedClass(OfferedClass aNewOfferedClass) {
    boolean wasSet = false;
    if (aNewOfferedClass != null) {
      offeredClass = aNewOfferedClass;
      wasSet = true;
    }
    return wasSet;
  }

  /* Code from template association_SetUnidirectionalOne */
  public boolean setInstructor(Instructor aNewInstructor) {
    boolean wasSet = false;
    if (aNewInstructor != null) {
      instructor = aNewInstructor;
      wasSet = true;
    }
    return wasSet;
  }

  public void delete() {
    offeredClass = null;
    instructor = null;
  }

  public String toString() {
    return super.toString() + "[" +
        "scheduledClassId" + ":" + getScheduledClassId() + "]" + System.getProperties().getProperty("line.separator") +
        "  " + "startTime" + "="
        + (getStartTime() != null
            ? !getStartTime().equals(this) ? getStartTime().toString().replaceAll("  ", "    ") : "this"
            : "null")
        + System.getProperties().getProperty("line.separator") +
        "  " + "endTime" + "="
        + (getEndTime() != null ? !getEndTime().equals(this) ? getEndTime().toString().replaceAll("  ", "    ") : "this"
            : "null")
        + System.getProperties().getProperty("line.separator") +
        "  " + "date" + "="
        + (getDate() != null ? !getDate().equals(this) ? getDate().toString().replaceAll("  ", "    ") : "this"
            : "null")
        + System.getProperties().getProperty("line.separator") +
        "  " + "offeredClass = "
        + (getOfferedClass() != null ? Integer.toHexString(System.identityHashCode(getOfferedClass())) : "null")
        + System.getProperties().getProperty("line.separator") +
        "  " + "instructor = "
        + (getInstructor() != null ? Integer.toHexString(System.identityHashCode(getInstructor())) : "null");
  }
}