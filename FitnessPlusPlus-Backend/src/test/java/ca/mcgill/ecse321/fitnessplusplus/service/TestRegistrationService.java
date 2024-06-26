package ca.mcgill.ecse321.fitnessplusplus.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.lenient;

import ca.mcgill.ecse321.fitnessplusplus.model.*;
import ca.mcgill.ecse321.fitnessplusplus.repository.ClientRepository;
import ca.mcgill.ecse321.fitnessplusplus.repository.RegisteredUserRepository;
import ca.mcgill.ecse321.fitnessplusplus.repository.RegistrationRepository;
import ca.mcgill.ecse321.fitnessplusplus.repository.ScheduledClassRepository;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

@ExtendWith(MockitoExtension.class)
public class TestRegistrationService {
  @Mock private RegistrationRepository registrationRepository;
  @Mock private ScheduledClassRepository scheduledClassRepository;
  @Mock private ClientRepository clientRepository;
  @Mock private RegisteredUserRepository registeredUserRepository;

  @InjectMocks private RegistrationService registrationService;
  @InjectMocks private ScheduledClassService scheduledClassService;
  @InjectMocks private RegisteredUserService registeredUserService;

  public static final Integer SCHEDULED_CLASS_KEY = 0;
  public static final Integer CLIENT_KEY = 3;

  public static final LocalDate REGISTRATION_DATE = (LocalDate.now());

  @BeforeEach
  public void setMockOutput() {
    lenient()
        .when(scheduledClassRepository.findScheduledClassByscheduledClassId(any(Integer.class)))
        .thenAnswer(
            (InvocationOnMock invocation) -> {
              if (invocation.getArgument(0).equals(SCHEDULED_CLASS_KEY)) {
                Time startTime = Time.valueOf(LocalTime.of(13, 0));
                Time endTime = Time.valueOf(LocalTime.of(15, 59));
                LocalDate aDate = (LocalDate.now().plusDays(1));

                ScheduledClass scheduledClass =
                    new ScheduledClass(
                        startTime,
                        endTime,
                        aDate,
                        new OfferedClass(
                            "Cooking", "How to not burn down the kitchen: A critical guide"));

                scheduledClass.setScheduledClassId(SCHEDULED_CLASS_KEY);

                return scheduledClass;
              } else {
                return null;
              }
            });

    lenient()
        .when(clientRepository.findClientByroleId(any(Integer.class)))
        .thenAnswer(
            (InvocationOnMock invocation) -> {
              if (invocation.getArgument(0).equals(CLIENT_KEY)) {
                return new Client(CLIENT_KEY);
              } else {
                return null;
              }
            });

    lenient()
        .when(registrationRepository.findAll())
        .thenAnswer(
            (InvocationOnMock invocation) -> {
              LocalDate aRegistrationDate = (LocalDate.now());
              Time startTime = Time.valueOf(LocalTime.of(13, 0));
              Time endTime = Time.valueOf(LocalTime.of(15, 59));
              LocalDate aScheduledDate = (LocalDate.now());

              ScheduledClass aScheduledClass =
                  new ScheduledClass(
                      startTime,
                      endTime,
                      aScheduledDate,
                      new OfferedClass("Swimming", "A very needed class for some engineers"));
              aScheduledClass.setInstructor(new Instructor(1));

              List<Registration> registrations = new ArrayList<Registration>();

              RegisteredUser user1 =
                  registeredUserService.createUser("Bob", "Bob Again", "BobLivesOn@gmail.com");
              Registration r1 =
                  new Registration(
                      aRegistrationDate, (Client) user1.getAccountRole(), aScheduledClass);
              r1.setRegistrationId(1);
              registrations.add(r1);

              RegisteredUser user2 =
                  registeredUserService.createUser(
                      "Alice", "Alice Again", "AliceLivesOn@gmail.com");
              Registration r2 =
                  new Registration(
                      aRegistrationDate, (Client) user2.getAccountRole(), aScheduledClass);
              r2.setRegistrationId(2);
              registrations.add(r2);

              return registrations;
            });
    Answer<?> returnParameterAsAnswer =
        (InvocationOnMock invocation) -> {
          return invocation.getArgument(0);
        };
    lenient().when(clientRepository.save(any(Client.class))).thenAnswer(returnParameterAsAnswer);
    lenient()
        .when(registeredUserRepository.save(any(RegisteredUser.class)))
        .thenAnswer(returnParameterAsAnswer);

    lenient()
        .when(registrationRepository.findRegistrationByregistrationId(anyInt()))
        .thenAnswer(
            (InvocationOnMock invocation) -> {
              if (invocation.getArgument(0).equals(0)) {
                Registration registration =
                    new Registration(
                        REGISTRATION_DATE,
                        clientRepository.findClientByroleId(CLIENT_KEY),
                        scheduledClassRepository.findScheduledClassByscheduledClassId(
                            SCHEDULED_CLASS_KEY));
                return registration;
              } else {
                return null;
              }
            });
  }

  @Test
  public void testSuccessfulCreateRegistration() {
    LocalDate aDate = (LocalDate.now());
    Registration registration =
        registrationService.createRegistration(aDate, CLIENT_KEY, SCHEDULED_CLASS_KEY);

    assertEquals(registration.getDateOfRegistration(), aDate);
    assertEquals(registration.getClient().getRoleId(), CLIENT_KEY);
    assertEquals(registration.getScheduledClass().getScheduledClassId(), SCHEDULED_CLASS_KEY);
  }

  @Test
  public void testWrongClientAndScheduledClassUnsuccesfullCreateRegistration() {
    LocalDate aDate = (LocalDate.now());
    Integer WRONG_CLIENT_ID = 666;
    Integer WRONG_SCHEDULED_CLASS_ID = 666;

    assertThrows(
        Exception.class,
        () -> {
          registrationService.createRegistration(aDate, WRONG_CLIENT_ID, SCHEDULED_CLASS_KEY);
        });

    assertThrows(
        Exception.class,
        () -> {
          registrationService.createRegistration(aDate, CLIENT_KEY, WRONG_SCHEDULED_CLASS_ID);
        });
  }

  @Test
  public void testRegistrationDateAfterScheduledClassDate() {
    LocalDate aRegistrationDate = (LocalDate.now().plusDays(2));

    assertThrows(
        Exception.class,
        () -> {
          registrationService.createRegistration(
              aRegistrationDate, CLIENT_KEY, SCHEDULED_CLASS_KEY);
        });
  }

  @Test
  public void testRepeatedRegistrationUnsuccessfulCreateRegistration() {
    Client alreadyRegisteredClient = registrationService.getAllRegistrations().get(0).getClient();
    ScheduledClass existingClass =
        registrationService.getAllRegistrations().get(0).getScheduledClass();

    assertThrows(
        Exception.class,
        () -> {
          registrationService.createRegistration(
              (LocalDate.now()),
              alreadyRegisteredClient.getRoleId(),
              existingClass.getScheduledClassId());
        });
  }

  @Test
  public void testSuccessfulDeleteRegistration() {
    Registration registration =
        registrationService.createRegistration(REGISTRATION_DATE, CLIENT_KEY, SCHEDULED_CLASS_KEY);
    int id = registration.getRegistrationId();
    String error = null;

    try {
      registrationService.removeRegistration(id);
    } catch (Exception e) {
      error = e.getMessage();
    }
    assertNull(error);
  }

  @Test
  public void testUnsuccessfulDeleteRegistrationInvalidRegistration() {
    int invalid_id = -1;

    assertThrows(
        Exception.class,
        () -> {
          registrationService.removeRegistration(invalid_id);
        });
  }

  @Test
  public void testUnsuccessfulDeleteRegistrationPassedScheduledClass() {

    lenient()
        .when(scheduledClassRepository.findScheduledClassByscheduledClassId(any(Integer.class)))
        .thenAnswer(
            (InvocationOnMock invocation) -> {
              Time startTime = Time.valueOf(LocalTime.of(13, 0));
              Time endTime = Time.valueOf(LocalTime.of(15, 59));
              LocalDate aDate = (LocalDate.now().minusDays(1));

              ScheduledClass scheduledClass =
                  new ScheduledClass(
                      startTime,
                      endTime,
                      aDate,
                      new OfferedClass(
                          "Cooking", "How to not burn down the kitchen: A critical guide"));

              scheduledClass.setScheduledClassId(SCHEDULED_CLASS_KEY);

              return scheduledClass;
            });

    LocalDate aDate = (LocalDate.now().plusDays(5));
    Registration registration =
        registrationService.createRegistration(
            (LocalDate.now().minusDays(1)), CLIENT_KEY, SCHEDULED_CLASS_KEY);

    registration.setDateOfRegistration(aDate);

    assertThrows(
        Exception.class,
        () -> {
          registrationService.removeRegistration(registration.getRegistrationId());
        });
  }
}
