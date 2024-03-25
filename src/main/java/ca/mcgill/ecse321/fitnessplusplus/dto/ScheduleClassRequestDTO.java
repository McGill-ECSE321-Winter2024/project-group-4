package ca.mcgill.ecse321.fitnessplusplus.dto;

import java.sql.Time;
import java.time.LocalDate;

public class ScheduleClassRequestDTO {
    private Time startTime;
    private Time endTime;
    private LocalDate date;
    private int offeredClassID;
    private int instructorID;

    @SuppressWarnings("unused")
    public ScheduleClassRequestDTO() {}

    public ScheduleClassRequestDTO(Time aStartTime, Time aEndTime, LocalDate aDate, Integer aOfferedClassId,
                                   Integer aInstructorId) {
        this.startTime = aStartTime;
        this.endTime = aEndTime;
        this.date = aDate;
        this.offeredClassID = aOfferedClassId;
        this.instructorID = aInstructorId;
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

    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate aDate) {
        this.date = aDate;
    }

    public  int getOfferedClassID(){ return offeredClassID; }
    public  void setOfferedClassID(int aOfferedClassID){ this.offeredClassID = aOfferedClassID; }

    public  int getInstructorID(){ return instructorID; }
    public  void setInstructorID(Integer aInstructorId){ this.instructorID = aInstructorId; }


}
