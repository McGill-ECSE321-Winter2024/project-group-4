package ca.mcgill.ecse321.fitnessplusplus.service;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.fitnessplusplus.model.Instructor;
import ca.mcgill.ecse321.fitnessplusplus.model.OfferedClass;
import ca.mcgill.ecse321.fitnessplusplus.model.ScheduledClass;
import ca.mcgill.ecse321.fitnessplusplus.repository.InstructorRepository;
import ca.mcgill.ecse321.fitnessplusplus.repository.OfferedClassRepository;
import ca.mcgill.ecse321.fitnessplusplus.repository.ScheduledClassRepository;

import jakarta.transaction.Transactional;

@Service
public class ScheduledClassService {
    @Autowired
    private ScheduledClassRepository scheduledClassRepo;
    @Autowired
    OfferedClassRepository offeredClassRepo;
    @Autowired
    InstructorRepository instructorRepo;

    /**
     * Creates a schedules class
     * 
     * @param aStartTime
     * @param aEndTime
     * @param aDate
     * @param aOfferedClassId
     * @param aInstructorId
     * @return ScheduledClass
     * @throws Exception
     * 
     * @author Isbat-ul Islam
     */
    @Transactional
    public ScheduledClass createScheduledClass(Time aStartTime, Time aEndTime, Date aDate, Integer aOfferedClassId,
            Integer aInstructorId) throws Exception {

        // add checks for if instructor and offered class exist.

        // Check if date selected is before.
        if (aDate.before(Date.valueOf(LocalDate.now())) && aStartTime.compareTo(Time.valueOf(LocalTime.now())) <= 0) {
            throw new Exception("Impossible to schedule a class in the past.");
        }

        // checks for opening and closing hours
        LocalTime openingTime = LocalTime.of(8, 0);
        LocalTime closingTime = LocalTime.of(20, 0);
        if (aStartTime.before(Time.valueOf(openingTime)) || aEndTime.after(Time.valueOf(closingTime))) {
            throw new Exception("Cannot schedule class before and after closing hours");
        }

        // Check if any scheduled class is conflicting
        for (ScheduledClass e : getAllScheduledClass()) {
            // if the dates are same, check if times are same => avoid schedule conflicts
            if (e.getDate().compareTo(aDate) == 0) {
                if (aStartTime.compareTo(e.getEndTime()) >= 0 || aEndTime.compareTo(e.getStartTime()) <= 0) {
                    throw new Exception("There already exists a class scheduled at those times.");
                }
            }
        }

        OfferedClass offeredClass = offeredClassRepo.findOfferedClassByOfferedClassId(aOfferedClassId);
        Instructor instructor = instructorRepo.findInstructorByroleId(aInstructorId);
        ScheduledClass scheduledClass = new ScheduledClass(aStartTime, aEndTime, aDate, offeredClass, instructor);
        scheduledClassRepo.save(scheduledClass);
        return scheduledClass;

    }

    /**
     * Returns a list of schedules classes
     * 
     * @return List of all ScheduledClass
     * 
     * @author Isbat-ul Islam
     */
    @Transactional
    public List<ScheduledClass> getAllScheduledClass() {
        List<ScheduledClass> list = new ArrayList<>();
        for (ScheduledClass e : scheduledClassRepo.findAll()) {
            list.add(e);
        }
        return list;
    }

    /**
     * Returns ScheduledClass with Id scheduledClassId
     * 
     * @param scheduledClassId
     * @return ScheduledClass
     * 
     * @author Isbat-ul Islam
     */
    @Transactional
    public ScheduledClass getScheduledClass(int scheduledClassId) {
        return scheduledClassRepo.findScheduledClassByscheduledClassId(scheduledClassId);

    }

    @Transactional
    public void deleteScheduledClass(int scheduledClassId, Integer aInstructorId) {
        // we get the scheduled class we want to remove
        ScheduledClass scheduledClass = getScheduledClass(scheduledClassId);
        //get the Instructor
        Instructor instructor = instructorRepo.findInstructorByroleId(aInstructorId);

        // we first remove the instructor
        if (scheduledClass != null && scheduledClass.getInstructor().equals(instructor)) {
            scheduledClassRepo.delete(scheduledClass);
            scheduledClass.delete();
        }
    }

    @Transactional
    public List<ScheduledClass> getWeeklyClassSchedule(Date date) {
        //first we get all the scheduled classes
        //we display from that day to the next seven days
        //Take the date given if it is a sunday, display from sunday to saturday,
        //if it is not a sunday go to that week's sunday and display from that sunday to saturday
        List<ScheduledClass> listToDisplay = new ArrayList<>();
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
            calendar.add(Calendar.DATE, -1);
        }
        Date startDate = (Date) calendar.getTime();
        calendar.add(Calendar.DATE, 6);
        Date endDate = (Date) calendar.getTime();

        List<ScheduledClass> list = getAllScheduledClass();
        for (ScheduledClass currentClass : list) {
            //check the date
            if (currentClass.getDate().compareTo(startDate) >= 0 && currentClass.getDate().compareTo(endDate) <= 0 ) {
                listToDisplay.add(currentClass);
            }
        }
        return listToDisplay;
    }

    @Transactional
    public List<ScheduledClass> getScheduledClassesByOfferedClass(OfferedClass offeredClass) {
        List<ScheduledClass> scheduledClassesToReturn = new ArrayList<>();
        List<ScheduledClass> scheduledClasses = getAllScheduledClass();

        for (ScheduledClass currentClass : scheduledClasses) {
            if (currentClass.getOfferedClass().equals(offeredClass)) {
                scheduledClassesToReturn.add(currentClass);
            }
        }
        return scheduledClassesToReturn;

    }
}
