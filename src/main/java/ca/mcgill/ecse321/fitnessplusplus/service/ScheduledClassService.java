package ca.mcgill.ecse321.fitnessplusplus.service;

import java.sql.Date;
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
    public ScheduledClass createScheduledClass(Time aStartTime, Time aEndTime, Date aDate, Integer aOfferedClassId,
            Integer aInstructorId) throws Exception {

        // add checks for if instructor and offered class exist.

        // Check if date selected is before.
        if (aDate.compareTo(Date.valueOf(LocalDate.now())) < 0 ) {
            throw new Exception("Impossible to schedule a class in the past.");
        }

        if (aStartTime.before(Time.valueOf(OPENING_TIME)) || aEndTime.after(Time.valueOf(CLOSING_TIME))) {
            throw new Exception("Cannot schedule class before and after closing hours");
        }

        // Check if any scheduled class is conflicting
        for (ScheduledClass e : scheduledClassRepo.findAll()) {
            // if the dates are same, check if times are same => avoid schedule conflicts
            if (e.getDate().compareTo(aDate) == 0) {
                if ((aStartTime.compareTo(e.getStartTime()) >= 0 && aStartTime.compareTo(e.getEndTime()) <= 0)
                        || (aEndTime.compareTo(e.getStartTime()) >= 0 && aEndTime.compareTo(e.getEndTime()) <= 0)) {
                    throw new Exception("There already exists a class scheduled at those times.");
                }
            }
        }

        OfferedClass offeredClass = offeredClassRepo.findOfferedClassByOfferedClassId(aOfferedClassId);
        // Must be Instructor despite getting regisered user because checks above make
        // sure of it.
        Instructor instructor = instructorRepository.findInstructorByroleId(aInstructorId);
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
        // find the associated registration
        // loop through registrations
        Registration registration = null;
        for (Registration currentRegistration : registrationRepository.findAll()) {
            if (currentRegistration.getScheduledClass().equals(scheduledClass)) {
                registration = currentRegistration;
                break;
            }
        }
        // the associated registration has been found
        // delete the registration
        if (registration != null) {
            scheduledClassRepo.delete(scheduledClass);
            registrationRepository.delete(registration);
        }
    }


    @Transactional
    public ArrayList<ScheduledClass> getWeeklyScheduledClasses(Date aDate) {
        Date startOfWeek = Date.valueOf(aDate.toLocalDate().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)));
        Date endOfWeek = Date.valueOf(startOfWeek.toLocalDate().plusDays(6));
        ArrayList<ScheduledClass> scheduledClasses = new ArrayList<>();

        for (ScheduledClass scheduledClass : scheduledClassRepo.findAll()) {
            Date date = scheduledClass.getDate();
            if (date.compareTo(startOfWeek) >= 0 && date.compareTo(endOfWeek) <= 0) {
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
