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

import ca.mcgill.ecse321.fitnessplusplus.model.ScheduledClass;
import ca.mcgill.ecse321.fitnessplusplus.model.Instructor;
import ca.mcgill.ecse321.fitnessplusplus.model.OfferedClass;
import ca.mcgill.ecse321.fitnessplusplus.model.Registration;
import ca.mcgill.ecse321.fitnessplusplus.model.Client;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.sql.Time;

@SpringBootTest
public class RegistrationRepositoryTest {
    @Autowired private RegistrationRepository registrationRepositoryTest;
    @Autowired private  ClientRepository clientRepositoryTest;
    @Autowired private ScheduledClassRepository scheduledClassRepositoryTest;
    @AfterEach
    public void clearDatabase() {
        registrationRepositoryTest.deleteAll();
        clientRepositoryTest.deleteAll();
        scheduledClassRepositoryTest.deleteAll();
    }

    @Test
    public void testReadWriteRegistration() {
        //Create and persist a Client
        Client client = new Client();
        clientRepositoryTest.save(client);

        //Create and persist a ScheduledClass
        OfferedClass offeredClass = new OfferedClass();
        offeredClass.setClassType("Strength");
        offeredClass.setDescription("A super duper crazy fun class!");

        Instructor instructor = new Instructor();
        Date classDate = Date.valueOf("2024-02-29");
        Time classStart = Time.valueOf("22:11:23");
        Time classEnd = Time.valueOf("23:59:59");

        ScheduledClass scheduledClass = new ScheduledClass();
        scheduledClass.setOfferedClass(offeredClass);
        scheduledClass.setInstructor(instructor);
        scheduledClass.setDate(classDate);
        scheduledClass.setStartTime(classStart);
        scheduledClass.setEndTime(classEnd);
        scheduledClassRepositoryTest.save(scheduledClass);

        //Create Registration

        Registration registration = new Registration();
        Date registrationDate = Date.valueOf("2024-02-18");
        registration.setClient(client);
        registration.setScheduledClass(scheduledClass);
        registration.setDateOfRegistration(registrationDate);

        //Save Registration
        registrationRepositoryTest.save(registration);

        //Read registration
        registration = registrationRepositoryTest.findByClientAndCLass(client, scheduledClass);
    }
}
