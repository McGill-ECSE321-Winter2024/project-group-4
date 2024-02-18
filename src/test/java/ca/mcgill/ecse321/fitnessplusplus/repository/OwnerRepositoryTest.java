package ca.mcgill.ecse321.fitnessplusplus.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import ca.mcgill.ecse321.fitnessplusplus.model.Owner;
import ca.mcgill.ecse321.fitnessplusplus.model.RegisteredUser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class OwnerRepositoryTest {

  @Autowired private OwnerRepository ownerRepository;
  @Autowired private RegisteredUserRepository registeredUserRepository;

  /**
   * Method to clear the database after each test run
   *
   * @author Mathieu Pestel
   */
  @AfterEach
  public void clearDatabase() {
    registeredUserRepository.deleteAll();
    ownerRepository.deleteAll();
  }

  /**
   * Test 1 for persistence of the Owner class<br>
   * Gets the Owner from its roleId, which is generated by Spring boot when it is saved to the
   * database
   *
   * @author Mathieu Pestel
   */
  @Test
  public void testPersistAndLoadOwner1() {
    // Create and persist owner
    Owner owner = new Owner();
    ownerRepository.save(owner);
    int roleId = owner.getRoleId();

    owner = ownerRepository.findInstructorByroleId(roleId);

    assertNotNull(owner);
    assertEquals(roleId, owner.getRoleId());
  }

  /**
   * Test 2 for persistence of the Owner class<br>
   * Gets the Owner from the RegisteredUser (while this also tests the persistence of
   * RegisteredUser, this test makes sure the owner is also appropriately saved).
   *
   * @author Mathieu Pestel
   */
  @Test
  public void testPersistAndLoadOwner2() {
    // Create and persist owner
    Owner owner = new Owner();
    ownerRepository.save(owner);
    int roleId = owner.getRoleId();

    String username = "mike";
    String password = "admin";
    String email = "mike@fitnessplusplus.com";
    RegisteredUser registeredUser = new RegisteredUser(username, password, email);
    registeredUser.setAccountRole(owner);
    registeredUserRepository.save(registeredUser);

    registeredUser = registeredUserRepository.findRegisteredUserByUsername(username);
    owner = (Owner) registeredUser.getAccountRole();

    assertNotNull(owner);
    assertEquals(roleId, owner.getRoleId());
  }
}
