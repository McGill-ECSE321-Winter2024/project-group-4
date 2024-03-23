package ca.mcgill.ecse321.fitnessplusplus.integration;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ca.mcgill.ecse321.fitnessplusplus.dto.*;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class IntegrationTests {

    @Autowired
    private TestRestTemplate client;

    private final String OWNER_NAME = "Bob";
    private final String OWNER_PASS = "BobIsGreat";
    private final String OWNER_EMAIL = "BobLivLaffLof@gmail.com";

    private final String INSTRUCTOR_NAME = "Bub";
    private final String INSTRUCTOR_PASS = "SoundsALotLikeSmthElse";
    private final String INSTRUCTOR_EMAIL = "UgandanKnuckles@mySpace.com";

    private final String CLIENT_NAME = "Bib";
    private final String CLIENT_PASS = "BibIsGreatAlso";
    private final String CLIENT_EMAIL = "yahooShallLiveOn@yahoo.com";

    private final String INVALID_NAME = null;
    private final String INVALID_PASS = null;
    private final String INVALID_EMAIL = null;

    public void clearDatabase(){
        
    }

    @Test
    @Order(1)
    public void createUser(){

        RegisteredUserRequestDto request = new RegisteredUserRequestDto(CLIENT_NAME, CLIENT_PASS, CLIENT_EMAIL);
        
        ResponseEntity<RegisteredUserResponseDto> response = client.postForEntity("/register-user", request, RegisteredUserResponseDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        RegisteredUserResponseDto createdUser = response.getBody(); 
        assertEquals(CLIENT_NAME, createdUser.getUsername());     
    }



}