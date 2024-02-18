package ca.mcgill.ecse321.fitnessplusplus.repository;

/*
PersistanceLayer Testing Registration

Read test cases exist for each class
Write test cases exist for each class
Test suite demonstrates that application can read and write - objects
Test suite demonstrates that application can read and write - attributes
Test suite demonstrates that application can read and write - references
Database contents cleared / reverted after test method
 */

import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ca.mcgill.ecse321.fitnessplusplus.model.Registration;

@SpringBootTest
public class RegistrationRepositoryTest {
    @Autowired private RegistrationRepository registrationRepositoryTest;

    @AfterEach
    public void clearDatabase() {
        registrationRepositoryTest.deleteAll();
    }
}
