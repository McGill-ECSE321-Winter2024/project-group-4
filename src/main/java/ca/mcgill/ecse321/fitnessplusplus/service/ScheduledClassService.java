package ca.mcgill.ecse321.fitnessplusplus.service;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

import java.util.ArrayList;
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

    @Transactional
    public ScheduledClass createScheduledClass(Time aStartTime, Time aEndTime, Date aDate, Integer aOfferedClassId,
            Integer aInstructorId) throws Exception {

        // add checks for if instructor and offered class exist.

        // Check if date selected is before.
        if (aDate.before(Date.valueOf(LocalDate.now())) && aStartTime.compareTo(Time.valueOf(LocalTime.now())) <= 0) {
            throw new Exception("Impossible to schedule a class in the past.");
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

    @Transactional
    public List<ScheduledClass> getAllScheduledClass() {
        List<ScheduledClass> list = new ArrayList<>();
        for (ScheduledClass e : scheduledClassRepo.findAll()) {
            list.add(e);
        }
        return list;
    }

    @Transactional
    public ScheduledClass getScheduledClass(int scheduledClassId) {
        return scheduledClassRepo.findScheduledClassByscheduledClassId(scheduledClassId);

    }

    @Transactional
    public void removeScheduledClass(int scheduledClassId, Instructor aInstructor) throws Exception {
        // we get the scheduled class we want to remove
        ScheduledClass scheduledClass = getScheduledClass(scheduledClassId);

        // we first remove the instructor
        if (scheduledClass != null && scheduledClass.getInstructor().equals(aInstructor)) {
            scheduledClassRepo.delete(scheduledClass);
            scheduledClass.delete();
        }
    }
}
