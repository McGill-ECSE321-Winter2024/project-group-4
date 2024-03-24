package ca.mcgill.ecse321.fitnessplusplus.dto;

import java.sql.Date;
import java.sql.Time;

public class ScheduleClassResponseDTO {
    private int scheduledClassID;
    private Time startTime;
    private Time endTime;
    private Date date;
    private int offeredClassID;
    private int intrusctorID;

    public ScheduleClassResponseDTO(int aScheduleClassID, Time aStartTime, Time aEndTime,
                                    Date aDate, int aofferedClassID, int aInstructorID) {
        this.scheduledClassID = aScheduleClassID;
        this.startTime = aStartTime;
        this.endTime = aEndTime;
        this.date = aDate;
        this.offeredClassID = aofferedClassID;
        this.intrusctorID = aInstructorID;
    }

    public Time getStartTime() {
        return startTime;
    }
    public void setStartTime(Time aStartTime) { this.startTime = aStartTime; }

    public Time getEndTime() {
        return endTime;
    }
    public void setEndTime(Time aEndTime) {
        this.endTime = aEndTime;
    }

    public Date getDate() {
        return date;
    }
    public void setDate(Date aDate) {
        this.date = aDate;
    }

    public  int getOfferedClassID(){ return offeredClassID; }
    public  void setOfferedClassID(int aOfferedClassID){ this.offeredClassID = aOfferedClassID; }

    public  int getInstructorID(){ return intrusctorID; }
    public  void setInstructorID(Integer aInstructorID){ this.intrusctorID = aInstructorID; }
    public  int getScheduledClassID(){ return scheduledClassID; }
    public  void setScheduledClassID(Integer aScheduleID){ this.scheduledClassID = aScheduleID; }
}
