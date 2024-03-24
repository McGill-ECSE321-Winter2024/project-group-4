package ca.mcgill.ecse321.fitnessplusplus.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import ca.mcgill.ecse321.fitnessplusplus.model.OfferedClass;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ca.mcgill.ecse321.fitnessplusplus.dto.*;
import ca.mcgill.ecse321.fitnessplusplus.model.RegisteredUser;
import ca.mcgill.ecse321.fitnessplusplus.repository.AccountRoleRepository;
import ca.mcgill.ecse321.fitnessplusplus.repository.ClientRepository;
import ca.mcgill.ecse321.fitnessplusplus.repository.InstructorRepository;
import ca.mcgill.ecse321.fitnessplusplus.repository.OfferedClassRepository;
import ca.mcgill.ecse321.fitnessplusplus.repository.OwnerRepository;
import ca.mcgill.ecse321.fitnessplusplus.repository.RegisteredUserRepository;
import ca.mcgill.ecse321.fitnessplusplus.repository.RegistrationRepository;
import ca.mcgill.ecse321.fitnessplusplus.repository.ScheduledClassRepository;
import ca.mcgill.ecse321.fitnessplusplus.repository.StaffRepository;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class IntegrationTests {

        @Autowired
        private TestRestTemplate client;
        @Autowired
        private ScheduledClassRepository scheduledClassRepository;
        @Autowired
        private RegistrationRepository registrationRepository;
        @Autowired
        private ClientRepository clientRepository;
        @Autowired
        private InstructorRepository instructorRepository;
        @Autowired
        private OfferedClassRepository offeredClassRepository;
        @Autowired
        private OwnerRepository ownerRepository;
        @Autowired
        private StaffRepository staffRepository;
        @Autowired
        private AccountRoleRepository accountRoleRepository;
        @Autowired
        private RegisteredUserRepository registeredUserRepository;

        private final String OWNER_NAME = "Bob";
        private final String OWNER_PASS = "BobIsGreat";
        private final String OWNER_EMAIL = "BobLivLaffLof@gmail.com";

        private final String INSTRUCTOR_NAME = "Bub";
        private final String INSTRUCTOR_PASS = "SoundsALotLikeSmthElse";
        private final String INSTRUCTOR_EMAIL = "UgandanKnuckles@mySpace.com";

        private final String CLIENT_NAME = "Bib";
        private final String CLIENT_PASS = "BibIsGreatAlso";
        private final String CLIENT_EMAIL = "yahooShallLiveOn@yahoo.com";
        private int CLIENT_ID = 0;
        private int ROLE_ID = 0;

        private final String OFFERED_CLASS_TYPE = "Cardio";
        private final String OFFERED_CLASS_DESCRIPTION = "Come exercise with us!";

        private final String INVALID_NAME = null;
        private final String INVALID_PASS = null;
        private final String INVALID_EMAIL = null;
        private final String INVALID_CLASS_TYPE = null;
        private final String INVALID_CLASS_DESCRIPTION = null;

        // TODO Get instructor ID and offered class ID
        private final int VALID_OFFERED_CLASS_ID = 0;
        private final int VALID_INSTRUCTOR_ID = 0;
        private final Time SCHEDULE_CLASS_START = Time.valueOf(LocalTime.of(10, 0));
        private final Time SCHEDULE_CLASS_END = Time.valueOf(LocalTime.of(20, 0));
        private final Date SCHEDULE_CLASS_DATE = Date.valueOf(LocalDate.of(2024, 12, 12));
        private final Time INVALID_SCHEDULE_CLASS_START = null;
        private final Time INVALID_SCHEDULE_CLASS_END = null;
        private final Date INVALID_SCHEDULE_CLASS_DATE = null;
        @BeforeAll
        public void clearDatabase() {
                registrationRepository.deleteAll();
                scheduledClassRepository.deleteAll();
                clientRepository.deleteAll();
                instructorRepository.deleteAll();
                ownerRepository.deleteAll();
                offeredClassRepository.deleteAll();
                staffRepository.deleteAll();
                accountRoleRepository.deleteAll();
                registeredUserRepository.deleteAll();
        }

        @Test
        @Order(1)
        public void createUser() {
                RegisteredUserRequestDto request = new RegisteredUserRequestDto(CLIENT_NAME, CLIENT_PASS, CLIENT_EMAIL);
                ResponseEntity<RegisteredUserResponseDto> response = client.postForEntity("/register-user", request,
                                RegisteredUserResponseDto.class);

                assertNotNull(response);
                assertEquals(HttpStatus.CREATED, response.getStatusCode());
                RegisteredUserResponseDto createdUser = response.getBody();
                CLIENT_ID = createdUser.getUserId();
                ROLE_ID = createdUser.getAccountRole();
                assertEquals(CLIENT_NAME, createdUser.getUsername());
        }

        /*/
        @Test
        public void invalidCreateUser(){
                RegisteredUserRequestDto request = new RegisteredUserRequestDto(CLIENT_NAME, CLIENT_PASS, CLIENT_EMAIL);
                ResponseEntity<RegisteredUserResponseDto> response = client.postForEntity("/register-user", request,
                                RegisteredUserResponseDto.class);

                assertNotNull(response);
                assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        }*/              

        @Test
        @Order(2)
        public void promoteUser() {
                RegisteredUserResponseDto request = new RegisteredUserResponseDto(CLIENT_ID, CLIENT_PASS, CLIENT_NAME,
                                CLIENT_EMAIL, ROLE_ID);
                ResponseEntity<RegisteredUserResponseDto> response = client.postForEntity("/promote", request,
                                RegisteredUserResponseDto.class);
                assertNotNull(response);
                assertNull(clientRepository.findClientByroleId(ROLE_ID));
                assertNotNull(instructorRepository.findInstructorByroleId(response.getBody().getUserId()));
        }

        @Test
        @Order(3)
        public void offerClass() {
                OfferedClassRequestDto request = new OfferedClassRequestDto(OFFERED_CLASS_TYPE,
                                OFFERED_CLASS_DESCRIPTION);
                ResponseEntity<OfferedClassResponseDto> response = client.postForEntity("/offer-class", request,
                                OfferedClassResponseDto.class);

                assertNotNull(response);
                assertEquals(HttpStatus.CREATED, response.getStatusCode(), "Response has correct status");
                OfferedClassResponseDto createdOfferedClass = response.getBody();
                assertEquals(OFFERED_CLASS_TYPE, createdOfferedClass.getClassType(), "Response has correct class type");
                assertEquals(OFFERED_CLASS_DESCRIPTION, createdOfferedClass.getDescription(),
                                "Response has correct description");
        }

        @Test
        @Order(4)
        public void offerInvalidClass() {
                OfferedClassRequestDto request = new OfferedClassRequestDto(INVALID_CLASS_TYPE,
                                INVALID_CLASS_DESCRIPTION);
                String error = null;

                ResponseEntity<ErrorDto> response = client.postForEntity("/offer-class", request,
                                ErrorDto.class);

                assertNotNull(response);
                assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
                ErrorDto body = response.getBody();
                assertNotNull(body);
                assertEquals(1, body.getErrors().size());
                assertEquals("Illegal arguments", body.getErrors().get(0));

        }

        @Test
        @Order(5)
        public void listOfferedClasses() {
                ResponseEntity<List<OfferedClassResponseDto>> response = client.exchange("/offered-classes",
                                HttpMethod.GET, null, new ParameterizedTypeReference<List<OfferedClassResponseDto>>() {
                                });

                assertNotNull(response);
                assertEquals(HttpStatus.OK, response.getStatusCode(), "Response has correct status");
                List<OfferedClassResponseDto> offeredClasses = response.getBody();
                assertNotNull(offeredClasses);
                assertEquals(1, offeredClasses.size());

                for (OfferedClassResponseDto c : offeredClasses) {
                        assertEquals(OFFERED_CLASS_TYPE, c.getClassType(), "Response has correct class type");
                        assertEquals(OFFERED_CLASS_DESCRIPTION, c.getDescription(),
                                        "Response has correct class description");
                }

        }





    @Test
    @Order(100)
    public void createScheduleClass1() {
        ScheduleClassRequestDTO requestDTO = new
                ScheduleClassRequestDTO(SCHEDULE_CLASS_START, SCHEDULE_CLASS_END, SCHEDULE_CLASS_DATE,
                VALID_OFFERED_CLASS_ID, VALID_INSTRUCTOR_ID);

        ResponseEntity<ScheduleClassResponseDTO> response = client.postForEntity("/scheduled-class",
                requestDTO, ScheduleClassResponseDTO.class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Response doesn't have correct status");
        ScheduleClassResponseDTO offeredClasses = response.getBody();
        assertNotNull(offeredClasses);
        assertEquals(VALID_OFFERED_CLASS_ID, response.getBody().getOfferedClassID());
        assertEquals(VALID_INSTRUCTOR_ID, response.getBody().getInstructorID());
        assertEquals(SCHEDULE_CLASS_START, response.getBody().getStartTime());
        assertEquals(SCHEDULE_CLASS_END, response.getBody().getEndTime());
        assertEquals(SCHEDULE_CLASS_DATE, response.getBody().getDate());
    }
    @Test
    @Order(101)
    public void createScheduleClass2() {
        ScheduleClassRequestDTO requestDTO = new
                ScheduleClassRequestDTO(SCHEDULE_CLASS_START, SCHEDULE_CLASS_END, SCHEDULE_CLASS_DATE,
                VALID_OFFERED_CLASS_ID, VALID_INSTRUCTOR_ID);

        ResponseEntity<ScheduleClassResponseDTO> response = client.postForEntity("/scheduled-class/",
                requestDTO, ScheduleClassResponseDTO.class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Response doesn't have correct status");
        ScheduleClassResponseDTO offeredClasses = response.getBody();
        assertNotNull(offeredClasses);
        assertEquals(VALID_OFFERED_CLASS_ID, response.getBody().getOfferedClassID());
        assertEquals(VALID_INSTRUCTOR_ID, response.getBody().getInstructorID());
        assertEquals(SCHEDULE_CLASS_START, response.getBody().getStartTime());
        assertEquals(SCHEDULE_CLASS_END, response.getBody().getEndTime());
        assertEquals(SCHEDULE_CLASS_DATE, response.getBody().getDate());
    }
    @Test
    @Order(102)
    public void createInvalidScheduleClass() {
        ScheduleClassRequestDTO requestDTO = new
                ScheduleClassRequestDTO(SCHEDULE_CLASS_END, SCHEDULE_CLASS_START, SCHEDULE_CLASS_DATE,
                VALID_OFFERED_CLASS_ID, VALID_INSTRUCTOR_ID);

        String error = null;
        ResponseEntity<ErrorDto> response = client.postForEntity("/scheduled-class/",
                requestDTO, ErrorDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ErrorDto body = response.getBody();
        assertNotNull(body);
        assertEquals(1, body.getErrors().size());
        assertEquals("Cannot schedule class before and after closing hours", body.getErrors().get(0));
    }
    @Test
    @Order(103)
    public void createDuplicateScheduleClass() {
        ScheduleClassRequestDTO requestDTO = new
                ScheduleClassRequestDTO(SCHEDULE_CLASS_START, SCHEDULE_CLASS_END, SCHEDULE_CLASS_DATE,
                VALID_OFFERED_CLASS_ID, VALID_INSTRUCTOR_ID);

        ResponseEntity<ScheduleClassResponseDTO> response = client.postForEntity("/scheduled-class",
                requestDTO, ScheduleClassResponseDTO.class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Response doesn't have correct status");
        ScheduleClassResponseDTO offeredClasses = response.getBody();
        assertNotNull(offeredClasses);
        assertEquals(VALID_OFFERED_CLASS_ID, response.getBody().getOfferedClassID());
        assertEquals(VALID_INSTRUCTOR_ID, response.getBody().getInstructorID());
        assertEquals(SCHEDULE_CLASS_START, response.getBody().getStartTime());
        assertEquals(SCHEDULE_CLASS_END, response.getBody().getEndTime());
        assertEquals(SCHEDULE_CLASS_DATE, response.getBody().getDate());

        ResponseEntity<ErrorDto> responseError = client.postForEntity("/scheduled-class",
                requestDTO, ErrorDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ErrorDto body = responseError.getBody();
        assertNotNull(body);
        assertEquals(1, body.getErrors().size());
        assertEquals("There already exists a class scheduled at those times.", body.getErrors().get(0));
    }



}