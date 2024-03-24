package ca.mcgill.ecse321.fitnessplusplus.service;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import ca.mcgill.ecse321.fitnessplusplus.repository.RegistrationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import ca.mcgill.ecse321.fitnessplusplus.repository.InstructorRepository;
import ca.mcgill.ecse321.fitnessplusplus.repository.OfferedClassRepository;
import ca.mcgill.ecse321.fitnessplusplus.repository.ScheduledClassRepository;
import ca.mcgill.ecse321.fitnessplusplus.model.Instructor;
import ca.mcgill.ecse321.fitnessplusplus.model.OfferedClass;
import ca.mcgill.ecse321.fitnessplusplus.model.ScheduledClass;

@ExtendWith(MockitoExtension.class)
public class TestScheduledClassService {

    @Mock
    private ScheduledClassRepository scheduledClassRepository;
    @Mock
    private OfferedClassRepository offeredClassRepository;
    @Mock
    private InstructorRepository instructorRepository;
    @Mock
    private RegistrationRepository registrationRepository;

    @InjectMocks
    private ScheduledClassService scheduledClassService;
    @InjectMocks
    private RegisteredUserService registeredUserService;
    @InjectMocks
    private OfferedClassService offeredClassService;

    public static final Integer INSTRUCTOR_KEY = 0;
    public static final Integer OFFERED_CLASS_KEY = 0;

    @BeforeEach
    public void setMockOutput() {
        lenient().when(offeredClassRepository.findOfferedClassByOfferedClassId(any(Integer.class)))
                .thenAnswer((InvocationOnMock invocation) -> {
                    if (invocation.getArgument(0).equals(OFFERED_CLASS_KEY)) {
                        OfferedClass offeredClass = new OfferedClass("Boxing", "Make em bleed");
                        offeredClass.setOfferedClassId(OFFERED_CLASS_KEY);
                        return offeredClass;
                    } else {
                        return null;
                    }
                });

        lenient().when(instructorRepository.findInstructorByroleId(any(Integer.class)))
                .thenAnswer((InvocationOnMock invocation) -> {
                    if (invocation.getArgument(0).equals(INSTRUCTOR_KEY)) {
                        return new Instructor(INSTRUCTOR_KEY);
                    } else {
                        return null;
                    }

                });

        lenient().when(scheduledClassRepository.findAll())
                .thenAnswer((InvocationOnMock invocation) -> {
                    Time startTime = Time.valueOf(LocalTime.of(15, 0));
                    Time endTime = Time.valueOf(LocalTime.of(17, 0));
                    LocalDate localDate = (LocalDate.now());
                    
                    LocalDate aDate = LocalDate.of(2024,3, 22);
                    ArrayList<ScheduledClass> list = new ArrayList<>();

                    ScheduledClass s1 = new ScheduledClass(startTime, endTime, localDate,
                            new OfferedClass("FireBending", "Fireball"));
                    s1.setInstructor(new Instructor(1));
                    list.add(s1);
                    ScheduledClass s2 = new ScheduledClass(startTime, endTime, (aDate.plusDays(2)),
                            new OfferedClass("WaterBending", "Save the ocean"));
                    s2.setInstructor(new Instructor(2));
                    list.add(s2);
                    ScheduledClass s3 = new ScheduledClass(startTime, endTime, (aDate.plusDays(20)),
                            new OfferedClass("AirBending", "Just keep breathing"));
                    s3.setInstructor(new Instructor(2));
                    list.add(s3);
                    ScheduledClass s4 = new ScheduledClass(startTime, endTime, (aDate.plusDays(5)),
                            new OfferedClass("EarthBending", "Rock'n Roll"));
                    s4.setInstructor(new Instructor(2)); 
                    list.add(s4);
                    return list;
                });

    }

    @Test
    public void testSuccesfulCreateScheduledClass() {

        Time startTime = Time.valueOf(LocalTime.of(10, 0));
        Time endTime = Time.valueOf(LocalTime.of(12, 0));
        LocalDate localDate = (LocalDate.now());
        ScheduledClass scheduledClass = null;
        try {
            scheduledClass = scheduledClassService.createScheduledClass(startTime, endTime, localDate,
                    OFFERED_CLASS_KEY, INSTRUCTOR_KEY);
        } catch (Exception e) {
            fail();
        }

        assertEquals(scheduledClass.getStartTime(), startTime);
        assertEquals(scheduledClass.getEndTime(), endTime);
        assertEquals(scheduledClass.getDate(), localDate);
        assertEquals(scheduledClass.getOfferedClass().getId(), OFFERED_CLASS_KEY);
        assertEquals(scheduledClass.getInstructor().getRoleId(), INSTRUCTOR_KEY);

    }

    @Test
    public void testFakeInstructorAndOfferedClassUnsuccesfulCreateScheduledClass() {
        Time startTime = Time.valueOf(LocalTime.of(10, 0));
        Time endTime = Time.valueOf(LocalTime.of(12, 0));
        LocalDate localDate = (LocalDate.of(2024, 12, 12));
        Integer FAKE_OFFERED_CLASS_KEY = 100;
        Integer FAKE_INSTRUCTOR_KEY = 100;

        assertThrows(Exception.class, () -> {
            scheduledClassService.createScheduledClass(startTime, endTime, localDate,
                    FAKE_OFFERED_CLASS_KEY, INSTRUCTOR_KEY);
        });

        assertThrows(Exception.class, () -> {
            scheduledClassService.createScheduledClass(startTime, endTime, localDate,
                    OFFERED_CLASS_KEY, FAKE_INSTRUCTOR_KEY);
        });
    }

    @Test
    public void testWrongTimeAndDateUnsuccesfulCreateScheduledClass() {

        Time goodStartTime = Time.valueOf(LocalTime.of(8, 0));
        Time badStartTime = Time.valueOf(LocalTime.of(7, 59));

        Time goodEndTime = Time.valueOf(LocalTime.of(20, 0));
        Time badEndTime = Time.valueOf(LocalTime.of(20, 1));

        LocalDate currentDate = LocalDate.now();
        LocalDate goodDate = (currentDate);
        LocalDate badDate = (currentDate.minusDays(1));

        // before opening
        assertThrows(Exception.class, () -> {
            scheduledClassService.createScheduledClass(badStartTime, goodEndTime, goodDate,
                    OFFERED_CLASS_KEY, INSTRUCTOR_KEY);
        });

        // after closing
        assertThrows(Exception.class, () -> {
            scheduledClassService.createScheduledClass(goodStartTime, badEndTime, goodDate,
                    OFFERED_CLASS_KEY, INSTRUCTOR_KEY);
        });

        // before current date
        assertThrows(Exception.class, () -> {
            scheduledClassService.createScheduledClass(goodStartTime, goodEndTime, badDate,
                    OFFERED_CLASS_KEY, INSTRUCTOR_KEY);
        });
    }

    @Test
    public void testExistingClassAtWantedTimeUnsuccesfulCreateScheduledClass() {
        // at the moment, there is a class from 15 - 17. Therefore, it starts in the
        // middle of ongoing class.
        Time startTime = Time.valueOf(LocalTime.of(16, 0));
        Time endTime = Time.valueOf(LocalTime.of(18, 0));
        LocalDate aDate = (LocalDate.now());
        assertThrows(Exception.class, () -> {
            scheduledClassService.createScheduledClass(startTime, endTime, aDate,
                    OFFERED_CLASS_KEY, INSTRUCTOR_KEY);
        });
    }

    @Test
    public void testGetWeeklyScheduledClasses() {
        List<ScheduledClass> scheduledClasses = scheduledClassService.getAllScheduledClass();
        ScheduledClass firstClass = scheduledClasses.get(0);
        int numberOfClassesBeforeWeeklyFilter = scheduledClasses.size();
        int numberOfClassesAfterWeeklyFilter = scheduledClassService.getWeeklyScheduledClasses(firstClass.getDate()).size();
        // Currently march 22nd is Friday. Only acceptable dates are 23 (index 2). The other two go beyond Sunday => bad. 
        assertEquals(4, numberOfClassesBeforeWeeklyFilter);
        assertEquals(2, numberOfClassesAfterWeeklyFilter);
    }

    //tests for the delete scheduled Class use case
    @Test
    public void testDeleteScheduledClass() throws Exception {
        LocalDate aDate = (LocalDate.now().plusDays(30));
        LocalTime startLocalTime = LocalTime.of(9, 0); // 9:00 AM
        LocalTime endLocalTime = LocalTime.of(17, 0); // 5:00 PM

        Time startTime = Time.valueOf(startLocalTime);
        Time endTime = Time.valueOf(endLocalTime);

        String classType = "some class type";
        String description = "some description";
        OfferedClass offeredclass = new OfferedClass(classType, description);

        Instructor instructor = new Instructor();
        ScheduledClass scheduledClass = scheduledClassService.createScheduledClass(startTime, endTime, aDate, offeredclass.getId(), instructor.getRoleId());
        int id = scheduledClass.getScheduledClassId();
        ScheduledClass deletedScheduledClass = scheduledClassService.deleteScheduledClass(scheduledClass);
        assertNotNull(deletedScheduledClass);
        assertEquals(id, deletedScheduledClass.getScheduledClassId());
    }

    @Test
    public void testDeleteUnexistingScheduledClass() {
        int scheduledClassId = -1; //negative class ID

        assertThrows(Exception.class, () -> {
            scheduledClassService.deleteScheduledClass(scheduledClassService.getScheduledClass(scheduledClassId));
        });
    }

}
