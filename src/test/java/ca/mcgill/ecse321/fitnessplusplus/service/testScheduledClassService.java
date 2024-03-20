package ca.mcgill.ecse321.fitnessplusplus.service;

import ca.mcgill.ecse321.fitnessplusplus.repository.ClientRepository;
import ca.mcgill.ecse321.fitnessplusplus.repository.InstructorRepository;
import ca.mcgill.ecse321.fitnessplusplus.repository.OfferedClassRepository;
import ca.mcgill.ecse321.fitnessplusplus.repository.OwnerRepository;
import ca.mcgill.ecse321.fitnessplusplus.repository.RegisteredUserRepository;
import ca.mcgill.ecse321.fitnessplusplus.repository.ScheduledClassRepository;
import ca.mcgill.ecse321.fitnessplusplus.model.Instructor;
import ca.mcgill.ecse321.fitnessplusplus.model.OfferedClass;
import ca.mcgill.ecse321.fitnessplusplus.model.RegisteredUser;
import ca.mcgill.ecse321.fitnessplusplus.model.ScheduledClass;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

@SpringBootTest
public class testScheduledClassService {

    @Mock
    private ScheduledClassRepository scheduledClassRepository;
    @Mock
    private RegisteredUserRepository registeredUserRepository;
    @Mock
    private OfferedClassRepository offeredClassRepository;
    @Mock
    private InstructorRepository instructorRepository;
    @Mock 
    private ClientRepository clientRepository;
    @Mock
    private OwnerRepository ownerRepository;

    @InjectMocks
    private ScheduledClassService scheduledClassService;
    @InjectMocks
    private RegisteredUserService registeredUserService;
    @InjectMocks
    private OfferedClassService offeredClassService;

    @Test
    public void testSuccesfullyCreateScheduledClass() {
        // set up
        LocalTime startTime = LocalTime.of(10, 0);
        LocalTime endTime = LocalTime.of(12, 0);
        LocalDate localDate = LocalDate.of(2024, 12, 12);

        RegisteredUser user = registeredUserService.createUser("bob", "bobAgain", "bobThrice@gmail.com");
        registeredUserService.promoteUser(user);

        OfferedClass offeredClass = offeredClassService.requestClass("Boxing", "Make em bleed");

        // Act
        ScheduledClass scheduledClass = null;
        try {
            scheduledClassService.createScheduledClass(Time.valueOf(startTime), Time.valueOf(endTime), Date.valueOf(localDate), offeredClass.getId(), user.getUserId());
        } catch (Exception e) {
            System.out.println("Creating ScheduledClass failed ln 62");
            fail();
        }

        // Assert
        ScheduledClass testScheduledClass = scheduledClassService
                .getScheduledClass(scheduledClass.getScheduledClassId());

        assertEquals(testScheduledClass.getDate(), localDate);
        assertEquals(testScheduledClass.getInstructor(), user);
        assertEquals(testScheduledClass.getStartTime(), startTime);
        assertEquals(testScheduledClass.getEndTime(), endTime);
        assertEquals(testScheduledClass.getOfferedClass(), offeredClass);

    }

}
