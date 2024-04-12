package ca.mcgill.ecse321.fitnessplusplus.dto;

import java.sql.Time;
import java.time.LocalDate;

public class ScheduleClassResponseDTO {
  private int scheduledClassID;
  private Time startTime;
  private Time endTime;
  private LocalDate date;
  private int offeredClassID;
  private int instructorID;
  private String instructorUsername;
  private String type;
  private String description;

  public ScheduleClassResponseDTO(
      int aScheduleClassID,
      Time aStartTime,
      Time aEndTime,
      LocalDate aDate,
      int aOfferedClassID,
      int aInstructorID,
      String aType,
      String aDescription,
      String aInstructorUsername) {
    this.scheduledClassID = aScheduleClassID;
    this.startTime = aStartTime;
    this.endTime = aEndTime;
    this.date = aDate;
    this.offeredClassID = aOfferedClassID;
    this.instructorID = aInstructorID;
    this.type = aType;
    this.description = aDescription;
    this.instructorUsername = aInstructorUsername;
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
    return instructorID;
  }

  public void setInstructorID(Integer aInstructorID) {
    this.instructorID = aInstructorID;
  }

  public int getScheduledClassID() {
    return scheduledClassID;
  }
  

  public String getDescription(){return description;}
  public String getType(){return type;}
  public void setDescription(String aDescription){ this.description = aDescription;}
  public void setType(String aType){ this.type = aType;}

  public void setScheduledClassID(int scheduledClassID) {
    this.scheduledClassID = scheduledClassID;
  }

  public String getInstructorUsername() {
    return instructorUsername;
  }

  public void setInstructorUsername(String instructorUsername) {
    this.instructorUsername = instructorUsername;
  }
}
