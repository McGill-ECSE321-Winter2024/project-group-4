package ca.mcgill.ecse321.fitnessplusplus.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import ca.mcgill.ecse321.fitnessplusplus.dto.*;
import ca.mcgill.ecse321.fitnessplusplus.repository.ClientRepository;
import ca.mcgill.ecse321.fitnessplusplus.repository.InstructorRepository;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class IntegrationTests {

        @Autowired
        private TestRestTemplate client;
        @Autowired
        private JdbcTemplate jdbcTemplate;
        @Autowired
        private ClientRepository clientRepository;
        @Autowired
        private InstructorRepository instructorRepository;

        private final String CLIENT_NAME = "Bib";
        private final String CLIENT_PASS = "BibIsGreatAlso";
        private final String CLIENT_EMAIL = "yahooShallLiveOn@yahoo.com";
        private int USER_ID = 0;
        private int ROLE_ID = 0;
        private int OFFERED_CLASS_ID = 0;

        private final String OFFERED_CLASS_TYPE = "Cardio";
        private final String OFFERED_CLASS_DESCRIPTION = "Come exercise with us!";

        private final String INVALID_NAME = null;
        private final String INVALID_PASS = null;
        private final String INVALID_EMAIL = null;
        private final int INVALID_USER_ID = -1;
        private final String INVALID_OFFERED_CLASS_TYPE = null;
        private final String INVALID_OFFERED_CLASS_DESCRIPTION = null;
        private final int INVALID_OFFERED_CLASS_ID = -1;

        private int VALID_INSTRUCTOR_ID;
        private final Time SCHEDULE_CLASS_START = Time.valueOf(LocalTime.of(10, 0));
        private final Time SCHEDULE_CLASS_END = Time.valueOf(LocalTime.of(20, 0));
        private final LocalDate SCHEDULE_CLASS_DATE = (LocalDate.of(2024, 12, 12));
        private final Time INVALID_SCHEDULE_CLASS_START = null;
        private final Time INVALID_SCHEDULE_CLASS_END = null;
        private final LocalDate INVALID_SCHEDULE_CLASS_DATE = (LocalDate.of(2024, 10, 12));
        private int VALID_SCHEDULE_CLASS_ID;
        private final int INVALID_SCHEDULE_CLASS_ID = 0;

//        @BeforeAll
//        @AfterAll
//        public void clearDatabase() {
//                List<String> tableNames = jdbcTemplate.queryForList(
//                                "SELECT table_name FROM information_schema.tables WHERE table_schema='public'",
//                                String.class);
//                for (String tableName : tableNames) {
//                        jdbcTemplate.execute("ALTER TABLE " + tableName + " DISABLE TRIGGER ALL");
//                }
//                for (String tableName : tableNames) {
//                        jdbcTemplate.execute("DELETE FROM " + tableName);
//                }
//                for (String tableName : tableNames) {
//                        jdbcTemplate.execute("ALTER TABLE " + tableName + " ENABLE TRIGGER ALL");
//                }
//        }

        @Test
        @Order(1)
        public void createUser() {
                RegisteredUserRequestDto request = new RegisteredUserRequestDto(CLIENT_NAME, CLIENT_PASS, CLIENT_EMAIL);
                ResponseEntity<RegisteredUserResponseDto> response = client.postForEntity("/register-user", request,
                                RegisteredUserResponseDto.class);

                assertNotNull(response);
                assertEquals(HttpStatus.CREATED, response.getStatusCode());
                RegisteredUserResponseDto createdUser = response.getBody();
                USER_ID = createdUser.getUserId();
                ROLE_ID = createdUser.getAccountRole();
                assertEquals(CLIENT_NAME, createdUser.getUsername());
        }

        @Test
        @Order(2)
        public void createInvalidUser() {
                RegisteredUserRequestDto request = new RegisteredUserRequestDto(INVALID_NAME, INVALID_PASS,
                                INVALID_EMAIL);
                ResponseEntity<ErrorDto> response = client.postForEntity("/register-user", request,
                                ErrorDto.class);

                assertNotNull(response);
                assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
                ErrorDto error = response.getBody();
                assertNotNull(error);
                assertEquals(1, error.getErrors().size());
                assertEquals("Illegal arguments", error.getErrors().get(0));
        }

        @Test
        @Order(3)
        public void createDuplicateUser() {
                // request a user with same name, etc than already existing user
                RegisteredUserRequestDto request = new RegisteredUserRequestDto(CLIENT_NAME, CLIENT_PASS, CLIENT_EMAIL);
                ResponseEntity<ErrorDto> response = client.postForEntity("/register-user", request, ErrorDto.class);

                assertNotNull(response);
                assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
                ErrorDto error = response.getBody();
                assertNotNull(error);
                assertEquals(1, error.getErrors().size());
                assertEquals("Account Exists", error.getErrors().get(0));
        }

        @Test
        @Order(4)
        public void promoteUser() {
                RegisteredUserResponseDto request = new RegisteredUserResponseDto(USER_ID, CLIENT_PASS, CLIENT_NAME,
                                CLIENT_EMAIL, ROLE_ID);
                ResponseEntity<RegisteredUserResponseDto> response = client.postForEntity("/promote", request,
                                RegisteredUserResponseDto.class);
                assertNotNull(response);
                assertEquals(HttpStatus.OK, response.getStatusCode());
                RegisteredUserResponseDto promotedUser = response.getBody();
                assertNull(clientRepository.findClientByroleId(ROLE_ID));
                VALID_INSTRUCTOR_ID = promotedUser.getAccountRole();
                assertNotNull(instructorRepository.findInstructorByroleId(VALID_INSTRUCTOR_ID));

        }

        @Test
        @Order(5)
        public void listRegisteredUsers() {
                ResponseEntity<List<RegisteredUserResponseDto>> response = client.exchange("/registered-users",
                                HttpMethod.GET, null,
                                new ParameterizedTypeReference<List<RegisteredUserResponseDto>>() {
                                });

                assertNotNull(response);
                assertEquals(HttpStatus.OK, response.getStatusCode());
                List<RegisteredUserResponseDto> registeredUsers = response.getBody();
                assertNotNull(registeredUsers);
                assertEquals(1, registeredUsers.size());

                for (RegisteredUserResponseDto r : registeredUsers) {
                        assertEquals(CLIENT_NAME, r.getUsername());
                        assertEquals(CLIENT_PASS, r.getPassword());
                        assertEquals(CLIENT_EMAIL, r.getEmail());
                        assertEquals(VALID_INSTRUCTOR_ID, r.getAccountRole());
                }
        }

        @Test
        @Order(6)
        public void findRegisteredUserById() {
                ResponseEntity<RegisteredUserResponseDto> response = client.getForEntity("/registered-user/" + USER_ID,
                                RegisteredUserResponseDto.class);

                assertNotNull(response);
                assertEquals(HttpStatus.OK, response.getStatusCode());
                RegisteredUserResponseDto registeredUser = response.getBody();
                assertNotNull(registeredUser);
                assertEquals(CLIENT_NAME, registeredUser.getUsername());
                assertEquals(CLIENT_PASS, registeredUser.getPassword());
                assertEquals(CLIENT_EMAIL, registeredUser.getEmail());
                assertEquals(USER_ID, registeredUser.getUserId());
                assertEquals(VALID_INSTRUCTOR_ID, registeredUser.getAccountRole());
        }

        @Test
        @Order(7)
        public void findRegisteredUserByInvalidId() {
                ResponseEntity<ErrorDto> response = client.getForEntity("/registered-user/" + INVALID_USER_ID,
                                ErrorDto.class);

                assertNotNull(response);
                assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
                ErrorDto error = response.getBody();
                assertNotNull(error);
                assertEquals(1, error.getErrors().size());
                assertEquals("RegisteredUser with id " + INVALID_USER_ID + " not found.", error.getErrors().get(0));
        }

        @Test
        @Order(8)
        public void offerClass() {
                OfferedClassRequestDto request = new OfferedClassRequestDto(OFFERED_CLASS_TYPE,
                                OFFERED_CLASS_DESCRIPTION);
                ResponseEntity<OfferedClassResponseDto> response = client.postForEntity("/offer-class", request,
                                OfferedClassResponseDto.class);

                assertNotNull(response);
                assertEquals(HttpStatus.CREATED, response.getStatusCode(), "Response has correct status");
                OfferedClassResponseDto createdOfferedClass = response.getBody();
                assertNotNull(createdOfferedClass);
                this.OFFERED_CLASS_ID = createdOfferedClass.getOfferedClassId();
                assertEquals(OFFERED_CLASS_TYPE, createdOfferedClass.getClassType(), "Response has correct class type");
                assertEquals(OFFERED_CLASS_DESCRIPTION, createdOfferedClass.getDescription(),
                                "Response has correct description");
        }

        @Test
        @Order(9)
        public void offerInvalidClass() {
                OfferedClassRequestDto request = new OfferedClassRequestDto(INVALID_OFFERED_CLASS_TYPE,
                                INVALID_OFFERED_CLASS_DESCRIPTION);

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
        @Order(10)
        public void listOfferedClasses() {
                ResponseEntity<List<OfferedClassResponseDto>> response = client.exchange("/offered-classes",
                                HttpMethod.GET, null, new ParameterizedTypeReference<List<OfferedClassResponseDto>>() {
                                });

                assertNotNull(response);
                assertEquals(HttpStatus.OK, response.getStatusCode());
                List<OfferedClassResponseDto> offeredClasses = response.getBody();
                assertNotNull(offeredClasses);
                assertEquals(1, offeredClasses.size());

                for (OfferedClassResponseDto c : offeredClasses) {
                        assertEquals(OFFERED_CLASS_TYPE, c.getClassType());
                        assertEquals(OFFERED_CLASS_DESCRIPTION, c.getDescription());
                }

        }

        @Test
        @Order(11)
        public void findOfferedClassById() {
                ResponseEntity<OfferedClassResponseDto> response = client
                                .getForEntity("/offered-class/" + OFFERED_CLASS_ID, OfferedClassResponseDto.class);

                assertNotNull(response);
                assertEquals(HttpStatus.OK, response.getStatusCode());
                OfferedClassResponseDto offeredClass = response.getBody();
                assertNotNull(offeredClass);
                assertEquals(OFFERED_CLASS_TYPE, offeredClass.getClassType());
                assertEquals(OFFERED_CLASS_DESCRIPTION, offeredClass.getDescription());
                assertEquals(OFFERED_CLASS_ID, offeredClass.getOfferedClassId());
        }

        @Test
        @Order(12)
        public void findOfferedClassByInvalidId() {
                ResponseEntity<ErrorDto> response = client.getForEntity("/offered-class/" + INVALID_OFFERED_CLASS_ID,
                                ErrorDto.class);

                assertNotNull(response);
                assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
                ErrorDto body = response.getBody();
                assertNotNull(body);
                assertEquals(1, body.getErrors().size());
                assertEquals("OfferedClass with id " + INVALID_OFFERED_CLASS_ID + " not found.",
                                body.getErrors().get(0));
        }

        @Test
        @Order(13)
        public void removeOfferedClass() {
                // create offeredclass
                ResponseEntity<OfferedClassResponseDto> createdOfferedClass = client.postForEntity("/offer-class",
                                new OfferedClassRequestDto(OFFERED_CLASS_TYPE,
                                                OFFERED_CLASS_DESCRIPTION),
                                OfferedClassResponseDto.class);
                int id = createdOfferedClass.getBody().getOfferedClassId();

                // delete offeredclass
                ResponseEntity<OfferedClassResponseDto> response = client.exchange("/offered-classes/" + id,
                                HttpMethod.DELETE, null, OfferedClassResponseDto.class);

                // check attributes of deleted offereclass
                assertNotNull(response);
                assertEquals(HttpStatus.OK, response.getStatusCode());
                OfferedClassResponseDto deletedOfferedClass = response.getBody();
                assertEquals(OFFERED_CLASS_TYPE, deletedOfferedClass.getClassType());
                assertEquals(OFFERED_CLASS_DESCRIPTION, deletedOfferedClass.getDescription());
                assertEquals(id, deletedOfferedClass.getOfferedClassId());

                // try to find deleted offeredclass
                ResponseEntity<ErrorDto> searchResponse = client.getForEntity("/offered-class/" + id, ErrorDto.class);

                // check error message
                assertNotNull(searchResponse);
                assertEquals(HttpStatus.BAD_REQUEST, searchResponse.getStatusCode());
                ErrorDto body = searchResponse.getBody();
                assertNotNull(body);
                assertEquals(1, body.getErrors().size());
                assertEquals("OfferedClass with id " + id + " not found.", body.getErrors().get(0));
        }

        @Test
        @Order(14)
        public void removeOfferedClassInvalidId() {
                ResponseEntity<ErrorDto> response = client.exchange("/offered-classes/" + INVALID_OFFERED_CLASS_ID,
                                HttpMethod.DELETE, null, ErrorDto.class);

                assertNotNull(response);
                assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
                ErrorDto body = response.getBody();
                assertNotNull(body);
                assertEquals(1, body.getErrors().size());
                assertEquals("You cannot remove an offered class that does not exist", body.getErrors().get(0));
        }
      @Test
    @Order(15)
    public void offerClass1() {
        OfferedClassRequestDto request = new OfferedClassRequestDto(OFFERED_CLASS_TYPE,
                OFFERED_CLASS_DESCRIPTION);
        ResponseEntity<OfferedClassResponseDto> response = client.postForEntity("/offer-class", request,
                OfferedClassResponseDto.class);

                assertNotNull(response);
                assertEquals(HttpStatus.CREATED, response.getStatusCode(), "Response has correct status");
                OfferedClassResponseDto createdOfferedClass = response.getBody();
                assertNotNull(createdOfferedClass);
                this.OFFERED_CLASS_ID = createdOfferedClass.getOfferedClassId();
                assertEquals(OFFERED_CLASS_TYPE, createdOfferedClass.getClassType(), "Response has correct class type");
                assertEquals(OFFERED_CLASS_DESCRIPTION, createdOfferedClass.getDescription(),
                                "Response has correct description");
    }

        @Test
        @Order(16)
        public void createScheduleClass1() {
                ScheduleClassRequestDTO requestDTO = new ScheduleClassRequestDTO(SCHEDULE_CLASS_START,
                                SCHEDULE_CLASS_END, SCHEDULE_CLASS_DATE,
                                OFFERED_CLASS_ID, VALID_INSTRUCTOR_ID);

                ResponseEntity<ScheduleClassResponseDTO> response = client.postForEntity("/scheduled-class",
                                requestDTO, ScheduleClassResponseDTO.class);

                assertNotNull(response);
                assertEquals(HttpStatus.OK, response.getStatusCode(), "Response has correct status");
                ScheduleClassResponseDTO scheduleClassResponseDTO = response.getBody();
                assertNotNull(scheduleClassResponseDTO);
                assertEquals(OFFERED_CLASS_ID, scheduleClassResponseDTO.getOfferedClassID());
                assertEquals(VALID_INSTRUCTOR_ID, scheduleClassResponseDTO.getInstructorID());
                assertEquals(SCHEDULE_CLASS_START, scheduleClassResponseDTO.getStartTime());
                assertEquals(SCHEDULE_CLASS_END, scheduleClassResponseDTO.getEndTime());
                this.VALID_SCHEDULE_CLASS_ID = scheduleClassResponseDTO.getScheduledClassID();
        }

        @Test
        @Order(17)
        public void createInvalidScheduleClass() {
                ScheduleClassRequestDTO requestDTO = new ScheduleClassRequestDTO(SCHEDULE_CLASS_END,
                                SCHEDULE_CLASS_START, SCHEDULE_CLASS_DATE,
                                OFFERED_CLASS_ID, 123);

                String error = null;
                ResponseEntity<ErrorDto> response = client.postForEntity("/scheduled-class/",
                                requestDTO, ErrorDto.class);

                assertNotNull(response);
                assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
                ErrorDto body = response.getBody();
                assertNotNull(body);
                assertEquals(1, body.getErrors().size());
                assertEquals("There already exists a class scheduled at those times.", body.getErrors().get(0));
        }

        /*
        @Test
        @Order(18)
        public void createDuplicateScheduleClass() {
                // DUE to deprecated Date library, each time the DB is accesses, date is
                // decremented by 1. in this case, the DB is accessed twice. For this reason, we will test this
                // method after.
                ScheduleClassRequestDTO requestDTO = new ScheduleClassRequestDTO(SCHEDULE_CLASS_START,
                                SCHEDULE_CLASS_END, SCHEDULE_CLASS_DATE,
                                OFFERED_CLASS_ID, VALID_INSTRUCTOR_ID);

                ResponseEntity<ScheduleClassResponseDTO> responseError = client.postForEntity("/scheduled-class",
                                requestDTO, ScheduleClassResponseDTO.class);

                assertNotNull(responseError);
                assertEquals(HttpStatus.BAD_REQUEST, responseError.getStatusCode());
        }*/

        @Test
        @Order(19)
        public void readValidScheduleClass() {
                // Set up
                String url = "/scheduled-classes/" + this.VALID_SCHEDULE_CLASS_ID;

                // Act
                ResponseEntity<ScheduleClassResponseDTO> response = client.getForEntity(url,
                                ScheduleClassResponseDTO.class);

                // Assert
                ScheduleClassResponseDTO scheduleClassResponseDTO = response.getBody();
                assertNotNull(scheduleClassResponseDTO);
                assertEquals(OFFERED_CLASS_ID, scheduleClassResponseDTO.getOfferedClassID());
                assertEquals(VALID_INSTRUCTOR_ID, scheduleClassResponseDTO.getInstructorID());
                assertEquals(SCHEDULE_CLASS_START, scheduleClassResponseDTO.getStartTime());
                assertEquals(SCHEDULE_CLASS_END, scheduleClassResponseDTO.getEndTime());
                assertEquals(this.VALID_SCHEDULE_CLASS_ID, scheduleClassResponseDTO.getScheduledClassID());
        }

        @Test
        @Order(20)
        public void readInvalidScheduleClass() {
                // Set up
                String url = "/scheduled-classes/" + this.INVALID_SCHEDULE_CLASS_ID;

                // Act
                ResponseEntity<ErrorDto> response = client.getForEntity(url, ErrorDto.class);

                // Assert
                assertNotNull(response);
                assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
                assertEquals("Scheduled Class does not exist.", response.getBody().getErrors().get(0));
        }

        @Test
        @Order(21)
        public void testGetAllScheduledClass() {
                // Set up
                String url = "/scheduled-classes";

                // Act
                ResponseEntity<List<ScheduleClassResponseDTO>> response = client.exchange(url,
                                HttpMethod.GET, null, new ParameterizedTypeReference<List<ScheduleClassResponseDTO>>() {
                                });

                // Assert
                assertNotNull(response);
                assertEquals(HttpStatus.OK, response.getStatusCode(), "Response doesn't have correct status");
                List<ScheduleClassResponseDTO> body = response.getBody();
                assertNotNull(body);
                assertEquals(1, body.size());
                ScheduleClassResponseDTO bodyDTO = body.get(0);
                assertEquals(OFFERED_CLASS_ID, bodyDTO.getOfferedClassID());
                assertEquals(VALID_INSTRUCTOR_ID, bodyDTO.getInstructorID());
                assertEquals(SCHEDULE_CLASS_START, bodyDTO.getStartTime());
                assertEquals(SCHEDULE_CLASS_END, bodyDTO.getEndTime());
                assertEquals(this.VALID_SCHEDULE_CLASS_ID, bodyDTO.getScheduledClassID());

        }

        @Test
        @Order(22)
        public void testGetWeekly() {
                // Set up
                String url = "/scheduled-classes/" + this.SCHEDULE_CLASS_DATE;

                // Act
                List<ScheduleClassResponseDTO> response = client.exchange(url, HttpMethod.GET, null,
                                new ParameterizedTypeReference<List<ScheduleClassResponseDTO>>() {
                                }).getBody();
                assertNotNull(response);
                assertEquals(1, response.size());
                ScheduleClassResponseDTO responseDTO = response.get(0);
                assertEquals(OFFERED_CLASS_ID, responseDTO.getOfferedClassID());
                assertEquals(VALID_INSTRUCTOR_ID, responseDTO.getInstructorID());
                assertEquals(SCHEDULE_CLASS_START, responseDTO.getStartTime());
                assertEquals(SCHEDULE_CLASS_END, responseDTO.getEndTime());
                assertEquals(this.VALID_SCHEDULE_CLASS_ID, responseDTO.getScheduledClassID());
        };

        @Test
        @Order(23)
        public void testGetEmptyWeekly() {
                // Set up
                String url = "/scheduled-classes/" + this.INVALID_SCHEDULE_CLASS_DATE;

                // Act
                List<ScheduleClassResponseDTO> response = client.exchange(url, HttpMethod.GET, null,
                                new ParameterizedTypeReference<List<ScheduleClassResponseDTO>>() {
                                }).getBody();
                assertNotNull(response);
                assertEquals(0, response.size());
        }

        @Test
        @Order(24)
        public void testCancelClassInvalid() {
                // Set up
                String url = "/scheduled-classes/" + this.INVALID_SCHEDULE_CLASS_ID;

                // Act
                ResponseEntity<ErrorDto> response = client.exchange(url,
                                HttpMethod.DELETE, null, ErrorDto.class);

                // Assert
                assertNotNull(response);
                assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
                ErrorDto body = response.getBody();
                assertNotNull(body);
                assertEquals(1, body.getErrors().size());
                assertEquals("The scheduled class does not exist",
                                body.getErrors().get(0));

        }

        @Test
        @Order(25)
        public void testCancelClassValid() {
                // Set up
                String url = "/scheduled-classes/" + this.VALID_SCHEDULE_CLASS_ID;

                // Act
                ResponseEntity<ScheduleClassResponseDTO> response = client.exchange(url,
                                HttpMethod.DELETE, null, ScheduleClassResponseDTO.class);

                // Assert
                assertNotNull(response);
                assertEquals(HttpStatus.OK, response.getStatusCode());
                ScheduleClassResponseDTO body = response.getBody();
                assertNotNull(body);
                assertEquals(OFFERED_CLASS_ID, body.getOfferedClassID());
                assertEquals(VALID_INSTRUCTOR_ID, body.getInstructorID());
                assertEquals(SCHEDULE_CLASS_START, body.getStartTime());
                assertEquals(SCHEDULE_CLASS_END, body.getEndTime());
                assertEquals(SCHEDULE_CLASS_DATE, body.getDate());
                assertEquals(this.VALID_SCHEDULE_CLASS_ID, body.getScheduledClassID());

                // Set up
                url = "/scheduled-classes/" + this.INVALID_SCHEDULE_CLASS_ID;

                // Act
                ResponseEntity<ErrorDto> responseError = client.getForEntity(url, ErrorDto.class);

                // Assert
                assertNotNull(responseError);
                assertEquals(HttpStatus.NOT_FOUND, responseError.getStatusCode());
                ErrorDto bodyError = responseError.getBody();
                assertNotNull(bodyError);
                assertEquals(1, bodyError.getErrors().size());
                assertEquals("There is no person with ID " + this.INVALID_SCHEDULE_CLASS_ID + ".",
                                bodyError.getErrors().get(0));

        }
}