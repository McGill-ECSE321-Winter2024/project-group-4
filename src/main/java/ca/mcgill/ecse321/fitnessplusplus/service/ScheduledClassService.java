package ca.mcgill.ecse321.fitnessplusplus.service;

import java.sql.Time;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse321.fitnessplusplus.model.Registration;
import ca.mcgill.ecse321.fitnessplusplus.repository.RegistrationRepository;
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
    private RegistrationRepository registrationRepository;
    @Autowired
    private InstructorRepository instructorRepository;

    public static LocalTime OPENING_TIME = LocalTime.of(8, 0);
    public static LocalTime CLOSING_TIME = LocalTime.of(20, 0);

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
    public ScheduledClass createScheduledClass(Time aStartTime, Time aEndTime, LocalDate aDate, Integer aOfferedClassId,
            Integer aInstructorId) throws IllegalArgumentException {

        // add checks for if instructor and offered class exist.
        if (aStartTime == null || aEndTime == null || aDate == null || aOfferedClassId == null || aInstructorId == null){
            throw new IllegalArgumentException("Parameters cannot be empty");
        }

        // Check if date selected is before.
        if (aDate.isBefore((LocalDate.now()))) {
            throw new IllegalArgumentException("Impossible to schedule a class in the past.");
        }

        if (aStartTime.before(Time.valueOf(OPENING_TIME)) || aEndTime.after(Time.valueOf(CLOSING_TIME))) {
            throw new IllegalArgumentException("Cannot schedule class before and after closing hours");
        }

        // Check if any scheduled class is conflicting
        for (ScheduledClass e : scheduledClassRepo.findAll()) {
            // if the dates are same, check if times are same => avoid schedule conflicts
            if (e.getDate().isEqual(aDate)) {
                if ((aStartTime.compareTo(e.getStartTime()) >= 0 && aStartTime.compareTo(e.getEndTime()) <= 0)
                        || (aEndTime.compareTo(e.getStartTime()) >= 0 && aEndTime.compareTo(e.getEndTime()) <= 0)) {
                    throw new IllegalArgumentException("There already exists a class scheduled at those times.");
                }
            }
        }

        OfferedClass offeredClass = offeredClassRepo.findOfferedClassByOfferedClassId(aOfferedClassId);
        // Must be Instructor despite getting regisered user because checks above make
        // sure of it.
        Instructor instructor = instructorRepository.findInstructorByroleId(aInstructorId);
        if (instructor == null){
            throw new IllegalArgumentException("Not a valid Intructor ID");
        }
        ScheduledClass scheduledClass = new ScheduledClass(aStartTime, aEndTime, aDate, offeredClass);
        scheduledClass.setInstructor(instructor);
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
    public ScheduledClass getScheduledClass (int scheduledClassId) {
        return scheduledClassRepo.findScheduledClassByscheduledClassId(scheduledClassId);

    }

    @Transactional
    public ScheduledClass deleteScheduledClass(ScheduledClass scheduledClass) throws IllegalArgumentException {
        if (scheduledClass  == null) {
            throw new IllegalArgumentException("The scheduled class does not exist");
        }

        if (scheduledClass.getDate().isBefore((LocalDate.now()))) {
            throw new IllegalArgumentException("You cannot remove a scheduled class that has already passed");
        }

        // find the associated registration
        // loop through registrations and add all registration to a list
        ArrayList<Registration> registrations = new ArrayList<>();
        for (Registration currentRegistration : registrationRepository.findAll()) {
            if (currentRegistration.getScheduledClass().equals(scheduledClass)) {
                registrations.add(currentRegistration);
            }
        }
        // the associated registration has been found
        // delete the registration
        if (!(registrations.isEmpty())) {
            //remove all registration associated with the scheduled class
            for (Registration currentRegistration: registrations) {
                registrationRepository.delete(currentRegistration);
                currentRegistration.delete();
            }
            //remove the scheduled class
            scheduledClassRepo.delete(scheduledClass);
            scheduledClass.delete();
        }
        return scheduledClass;
    }


    @Transactional
    public ArrayList<ScheduledClass> getWeeklyScheduledClasses(LocalDate aDate) {
        LocalDate startOfWeek = (aDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)));
        LocalDate endOfWeek = (startOfWeek.plusDays(6));
        ArrayList<ScheduledClass> scheduledClasses = new ArrayList<>();

        for (ScheduledClass scheduledClass : scheduledClassRepo.findAll()) {
            LocalDate date = scheduledClass.getDate();
            if (!date.isBefore(startOfWeek) && !date.isAfter(endOfWeek)) {
                scheduledClasses.add(scheduledClass);
            }
        }

        return scheduledClasses;
    }

    @Transactional
    public List<ScheduledClass> getScheduledClassesByOfferedClass(OfferedClass offeredClass) {
        List<ScheduledClass> scheduledClassesToReturn = new ArrayList<>();
        List<ScheduledClass> scheduledClasses = (List<ScheduledClass>) scheduledClassRepo.findAll();

        for (ScheduledClass currentClass : scheduledClasses) {
            if (currentClass.getOfferedClass().equals(offeredClass)) {
                scheduledClassesToReturn.add(currentClass);
            }
        }
        return scheduledClassesToReturn;

    }
}
