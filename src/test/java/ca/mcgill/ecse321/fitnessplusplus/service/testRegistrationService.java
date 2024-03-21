package ca.mcgill.ecse321.fitnessplusplus.service;

import ca.mcgill.ecse321.fitnessplusplus.model.*;
import ca.mcgill.ecse321.fitnessplusplus.repository.ClientRepository;
import ca.mcgill.ecse321.fitnessplusplus.repository.RegistrationRepository;
import ca.mcgill.ecse321.fitnessplusplus.repository.ScheduledClassRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class testRegistrationService {
    @Mock
    private RegistrationRepository registrationRepository;
    @Mock
    private ScheduledClassRepository scheduledClassRepository;
    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private RegistrationService registrationService;
    @InjectMocks
    private ScheduledClassService scheduledClassService;
    @InjectMocks
    private AccountService accountService;

    public static final Integer SCHEDULED_KEY = 0;
    public static final Integer CLIENT_KEY = 0;

    @BeforeEach
    public void setMockOutput() {
        lenient().when(scheduledClassRepository.findScheduledClassByscheduledClassId(any(Integer.class)))
                .thenAnswer((InvocationOnMock invocation) -> {
                    if (invocation.getArgument(0).equals(SCHEDULED_KEY)) {
                        Time startTime = Time.valueOf(LocalTime.of(23, 0));
                        Time endTime = Time.valueOf(LocalTime.of(23, 59));
                        Date aDate = Date.valueOf(LocalDate.now());

                        ScheduledClass scheduledClass = new ScheduledClass(startTime, endTime, aDate,
                                new OfferedClass("Cooking", "How to not burn down the kitchen: A critical guide"),
                                new Instructor(1));

                        scheduledClass.setScheduledClassId(SCHEDULED_KEY);

                        return scheduledClass;
                    } else {
                        return null;
                    }
                });

        lenient().when(clientRepository.findClientByroleId(any(Integer.class)))
                .thenAnswer((InvocationOnMock invocation) -> {
                    if (invocation.getArgument(0).equals(CLIENT_KEY)) {
                        return new Client(CLIENT_KEY);
                    } else {
                        return null;
                    }
                });

        lenient().when(registrationRepository.findAll())
                .thenAnswer((InvocationOnMock invocation) -> {
                    Date aRegistrationDate = Date.valueOf(LocalDate.now());

                    Time startTime = Time.valueOf(LocalTime.of(23, 0));
                    Time endTime = Time.valueOf(LocalTime.of(23, 59));
                    Date aScheduledDate = Date.valueOf(LocalDate.now());
                    ScheduledClass aScheduledClass = new ScheduledClass(startTime, endTime, aScheduledDate,
                            new OfferedClass("Swimming","A very needed class for some engineers"),
                            new Instructor(1));

                    Client aClient = new Client(1);

                    Registration r1 = new Registration(aRegistrationDate, aClient, aScheduledClass);
                    ArrayList<Registration> list = new ArrayList<>();

                    list.add(r1);
                    return r1;
                });
    }

    @Test
    public void testSuccesfulCreateRegistration() {
        Date aDate = Date.valueOf(LocalDate.now());
        Registration registration = null;

        try {
            registration = registrationService.createRegistration(aDate, CLIENT_KEY, SCHEDULED_KEY);
        } catch (Exception e) {
            fail();
        }

        assertEquals(registration.getDateOfRegistration(), aDate);
        assertEquals(registration.getClient().getRoleId(), CLIENT_KEY);
        assertEquals(registration.getScheduledClass().getScheduledClassId(), SCHEDULED_KEY);
    }

    @Test
    public void testWrongClientAndScheduledClassUnsuccesfullCreateRegistration() {
        Date aDate = Date.valueOf(LocalDate.now());
        Integer WRONG_CLIENT_ID = 666;
        Integer WRONG_SCHEDULED_CLASS_ID = 666;

        assertThrows(Exception.class, () -> {
           registrationService.createRegistration(aDate, WRONG_CLIENT_ID, SCHEDULED_KEY);
        });

        assertThrows(Exception.class, () -> {
            registrationService.createRegistration(aDate, CLIENT_KEY, WRONG_SCHEDULED_CLASS_ID);
        });
    }
}
