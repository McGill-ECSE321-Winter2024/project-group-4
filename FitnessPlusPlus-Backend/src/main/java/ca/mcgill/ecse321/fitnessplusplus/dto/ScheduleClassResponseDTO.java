package ca.mcgill.ecse321.fitnessplusplus.dto;

import java.sql.Time;
import java.time.LocalDate;

public class ScheduleClassResponseDTO {
  private int scheduledClassID;
  private Time startTime;
  private Time endTime;
  private LocalDate date;
  private int offeredClassID;
  private int intrusctorID;
  private String type;
  private String description;

  public ScheduleClassResponseDTO(
      int aScheduleClassID,
      Time aStartTime,
      Time aEndTime,
      LocalDate aDate,
      int aofferedClassID,
      int aInstructorID,
      String aType,
      String aDescription) {
    this.scheduledClassID = aScheduleClassID;
    this.startTime = aStartTime;
    this.endTime = aEndTime;
    this.date = aDate;
    this.offeredClassID = aofferedClassID;
    this.intrusctorID = aInstructorID;
    this.type = aType;
    this.description = aDescription;
  }

  public Time getStartTime() {
    return startTime;
  }

  public void setStartTime(Time aStartTime) {
    this.startTime = aStartTime;
  }

  public Time getEndTime() {
    return endTime;
  }

  public void setEndTime(Time aEndTime) {
    this.endTime = aEndTime;
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate aDate) {
    this.date = aDate;
  }

  public int getOfferedClassID() {
    return offeredClassID;
  }

  public void setOfferedClassID(int aOfferedClassID) {
    this.offeredClassID = aOfferedClassID;
  }

  public int getInstructorID() {
    return intrusctorID;
  }

  public void setInstructorID(Integer aInstructorID) {
    this.intrusctorID = aInstructorID;
  }

  public int getScheduledClassID() {
    return scheduledClassID;
  }

  public void setScheduledClassID(Integer aScheduleID) {
    this.scheduledClassID = aScheduleID;
  }

  public String getDescription(){return description;}
  public String getType(){return type;}
  public void setDescription(String aDescription){ this.description = aDescription;}
  public void setType(String aType){ this.type = aType;}
}
