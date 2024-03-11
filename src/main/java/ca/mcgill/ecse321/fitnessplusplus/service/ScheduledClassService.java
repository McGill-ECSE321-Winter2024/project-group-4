package ca.mcgill.ecse321.fitnessplusplus.service;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.fitnessplusplus.model.Instructor;
import ca.mcgill.ecse321.fitnessplusplus.model.OfferedClass;
import ca.mcgill.ecse321.fitnessplusplus.model.ScheduledClass;
import ca.mcgill.ecse321.fitnessplusplus.repository.ScheduledClassRepository;
import jakarta.transaction.Transactional;

@Service
public class ScheduledClassService {
    @Autowired
    private ScheduledClassRepository scheduledClassRepo;

    @Transactional
    public ScheduledClass createScheduledClass(Time aStartTime, Time aEndTime, Date aDate, OfferedClass aOfferedClass,
            Instructor aInstrcutor) throws Exception {

        // Check if date selected is before.
        if (aDate.before(Date.valueOf(LocalDate.now()))) {
            throw new Exception("Impossible to schedule a class during past dates.");
        }

        // Check if the scheduled classes are conflicting
        for (ScheduledClass e : getAllScheduledClass()) {
            // if the dates are same, check if times are same => avoid schedule conflicts
            if (e.getDate().compareTo(aDate) == 0) {
                if (aStartTime.compareTo(e.getEndTime()) >= 0 || aEndTime.compareTo(e.getStartTime()) <= 0) {
                    throw new Exception("There already exists a class scheduled at those times.");
                }
            }
        }

        ScheduledClass scheduledClass = new ScheduledClass(aStartTime, aEndTime, aDate, aOfferedClass, aInstrcutor);
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

}
