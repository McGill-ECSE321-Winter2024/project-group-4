package ca.mcgill.ecse321.fitnessplusplus.dto;

import java.sql.Date;
import java.sql.Time;

public class ScheduledClassDto {
    private int scheduledClassId;
    private Time startTime;
    private Time endTime;
    private Date date;

    public ScheduledClassDto(int aId, Time aStartTime, Time aEndTime, Date aDate) {
        this.scheduledClassId = aId;
        this.startTime = aStartTime;
        this.endTime = aEndTime;
        this.date = aDate;
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
}
