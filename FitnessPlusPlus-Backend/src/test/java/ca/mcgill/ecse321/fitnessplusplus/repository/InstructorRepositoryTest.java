package ca.mcgill.ecse321.fitnessplusplus.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import ca.mcgill.ecse321.fitnessplusplus.model.Instructor;
import ca.mcgill.ecse321.fitnessplusplus.model.RegisteredUser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class InstructorRepositoryTest {

  @Autowired private InstructorRepository instructorRepository;
  @Autowired private RegisteredUserRepository registeredUserRepository;

  /**
   * Method to clear the database after each test run
   *
   * @author Mathieu Pestel
   */
  @AfterEach
  public void clearDatabase() {
    registeredUserRepository.deleteAll();
    instructorRepository.deleteAll();
  }

  /**
   * Test 1 for persistence of the Instructor class<br>
   * Gets the Instructor from its roleId, which is generated by Spring boot when it is saved to the
   * database
   *
   * @author Mathieu Pestel
   */
  @Test
  public void testPersistAndLoadInstructor1() {
    // Create and persist instructor
    Instructor instructor = new Instructor();
    instructorRepository.save(instructor);
    int roleId = instructor.getRoleId();

    instructor = instructorRepository.findInstructorByroleId(roleId);

    assertNotNull(instructor);
    assertEquals(roleId, instructor.getRoleId());
  }

  /**
   * Test 2 for persistence of the Instructor class<br>
   * Gets the Instructor from the RegisteredUser (while this also tests the persistence of
   * RegisteredUser, this test makes sure the instructor is also appropriately saved).
   *
   * @author Mathieu Pestel
   */
  @Test
  public void testPersistAndLoadInstructor2() {
    // Create and persist instructor
    Instructor instructor = new Instructor();
    instructorRepository.save(instructor);
    int roleId = instructor.getRoleId();

    String username = "johndoe";
    String password = "000000000";
    String email = "john.doe@gmail.com";
    RegisteredUser registeredUser = new RegisteredUser(username, password, email);
    registeredUser.setAccountRole(instructor);
    registeredUserRepository.save(registeredUser);

    registeredUser = registeredUserRepository.findRegisteredUserByUsername(username);
    instructor = (Instructor) registeredUser.getAccountRole();

    assertNotNull(instructor);
    assertEquals(roleId, instructor.getRoleId());
  }
}