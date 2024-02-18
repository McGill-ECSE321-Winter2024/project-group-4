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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class RegistrationRepositoryTest {
    @Autowired private RegistrationRepository registrationRepositoryTest;
    @Autowired private  ClientRepository clientRepositoryTest;
    @Autowired private ScheduledClassRepository scheduledClassRepositoryTest;
    @Autowired private InstructorRepository instructorRepositoryTest;
    @Autowired private  OfferedClassRepository offeredClassRepositorTest;

    /**
     * Method to clear the repos used after each test
     *
     * @author: Yonatan Bensimon
     */
    @AfterEach
    public void clearDatabase() {
        registrationRepositoryTest.deleteAll();
        clientRepositoryTest.deleteAll();
        scheduledClassRepositoryTest.deleteAll();
        instructorRepositoryTest.deleteAll();
        offeredClassRepositorTest.deleteAll();
    }

    /**
     * Testing of reading and writing of Registration
     *
     * @author Yonatan Bensimon
     */
    @Test
    public void testReadWriteRegistration() {
        //Create and persist a Client
        Client client = new Client();
        clientRepositoryTest.save(client);

        //Create and persist a ScheduledClass
        OfferedClass offeredClass = new OfferedClass();
        offeredClass.setClassType("Strength");
        offeredClass.setDescription("A super duper crazy fun class!");
        offeredClassRepositorTest.save(offeredClass);

        Instructor instructor = new Instructor();
        instructorRepositoryTest.save(instructor);

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
        registration = registrationRepositoryTest.findByClientAndScheduledClass(client, scheduledClass);

        //Asserts a registration exists
        assertNotNull(registration);
        assertNotNull(registration.getRegistrationId());
        assertEquals(client.getRoleId(), registration.getClient().getRoleId());
        assertEquals(registrationDate, registration.getDateOfRegistration());
        assertEquals(scheduledClass.getScheduledClassId(), registration.getScheduledClass().getScheduledClassId());
        assertEquals(scheduledClass.getOfferedClass().getId(), registration.getScheduledClass().getOfferedClass().getId());
        assertEquals(scheduledClass.getDate(), registration.getScheduledClass().getDate());
        assertEquals(scheduledClass.getInstructor().getRoleId(), registration.getScheduledClass().getInstructor().getRoleId());
        assertEquals(scheduledClass.getStartTime(), registration.getScheduledClass().getStartTime());
        assertEquals(scheduledClass.getEndTime(), registration.getScheduledClass().getEndTime());
    }

    /**
     * Test writing and reading attributes of Registration
     * Edits an attribute value and checks if the value has been updated in the repository
     *
     * @author Yonatan Bensimon
     */
    @Test
    public void testReadWriteRegistrationAttributes() {
        //Create and persist a Client
        Client client = new Client();
        clientRepositoryTest.save(client);

        //Create and persist a ScheduledClass
        OfferedClass offeredClass = new OfferedClass();
        offeredClass.setClassType("Cardio");
        offeredClass.setDescription("A new crazy fun class!");
        offeredClassRepositorTest.save(offeredClass);

        Instructor instructor = new Instructor();
        instructorRepositoryTest.save(instructor);

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
        registration = registrationRepositoryTest.findByClientAndScheduledClass(client, scheduledClass);

        //Asserts a registration exists
        assertNotNull(registration);

        //Set new attributes
        Date newRegistrationDate = Date.valueOf("2023-02-18");
        registration.setDateOfRegistration(newRegistrationDate);
        registrationRepositoryTest.save(registration);

        Registration retrievedRegistration = registrationRepositoryTest.findByClientAndScheduledClass(client, scheduledClass);
        assertEquals(newRegistrationDate, retrievedRegistration.getDateOfRegistration());
    }
}
