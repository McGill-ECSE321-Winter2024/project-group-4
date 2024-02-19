package ca.mcgill.ecse321.fitnessplusplus.repository;
import java.sql.Date;
import java.sql.Time;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import ca.mcgill.ecse321.fitnessplusplus.model.OfferedClass;
import ca.mcgill.ecse321.fitnessplusplus.model.ScheduledClass;
import ca.mcgill.ecse321.fitnessplusplus.model.Instructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ScheduledClassRepositoryTest {
    @Autowired
    private OfferedClassRepository offeredClassRepository;
    @Autowired
    private ScheduledClassRepository scheduledClassRepository;
    @Autowired
    private InstructorRepository instructorRepository;

    @AfterEach
    public void clearDatabase() {
        scheduledClassRepository.deleteAll();
        offeredClassRepository.deleteAll();
        instructorRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadScheduledClass1() {
        ScheduledClass scheduledClass = new ScheduledClass();
        scheduledClassRepository.save(scheduledClass);
        int classId = scheduledClass.getScheduledClassId();

        scheduledClass = scheduledClassRepository.findScheduledClassByscheduledClassId(classId);

        assertNotNull(scheduledClass);
        assertEquals(classId, scheduledClass.getScheduledClassId());
    }

    @Test
    public void testPersistAndLoadScheduledClass2() {
        ScheduledClass scheduledClass = new ScheduledClass();
        scheduledClassRepository.save(scheduledClass);
        int classId = scheduledClass.getScheduledClassId();

        OfferedClass offeredClass = new OfferedClass();
        Instructor instructor = new Instructor();
        offeredClassRepository.save(offeredClass);
        instructorRepository.save(instructor);
        int offeredClassId = offeredClass.getId();
        int instructorId = instructor.getRoleId();

        scheduledClass = scheduledClassRepository.findScheduledClassByscheduledClassId(classId);

        scheduledClass.setOfferedClass(offeredClass);
        scheduledClass.setInstructor(instructor);
        Date date = new Date(0);
        scheduledClass.setDate(date);
        Time startTime = new Time(0);
        scheduledClass.setStartTime(startTime);
        Time endTime = new Time(1);
        scheduledClass.setEndTime(endTime);

        scheduledClassRepository.save(scheduledClass);
        scheduledClass = scheduledClassRepository.findScheduledClassByscheduledClassId(classId);

        assertEquals(classId, scheduledClass.getScheduledClassId());
        assertEquals(instructorId, scheduledClass.getInstructor().getRoleId());
        assertEquals(offeredClassId, scheduledClass.getOfferedClass().getId());
        assertEquals(date.toString(), scheduledClass.getDate().toString());
        assertEquals(startTime.toString(), scheduledClass.getStartTime().toString());
        assertEquals(endTime.toString(), scheduledClass.getEndTime().toString());
    }
}
