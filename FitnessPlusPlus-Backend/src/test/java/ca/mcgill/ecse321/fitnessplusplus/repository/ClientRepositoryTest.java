package ca.mcgill.ecse321.fitnessplusplus.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import ca.mcgill.ecse321.fitnessplusplus.model.Client;
import ca.mcgill.ecse321.fitnessplusplus.model.RegisteredUser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ClientRepositoryTest {
  @Autowired private ClientRepository clientRepository;
  @Autowired private RegisteredUserRepository registeredUserRepository;

  /**
   * Method to clear the database after each test run
   *
   * @author Mathieu Pestel
   */
  @AfterEach
  public void clearDatabase() {
    registeredUserRepository.deleteAll();
    clientRepository.deleteAll();
  }

  /**
   * Test 1 for persistence of the Client class<br>
   * Gets the Client from its roleId, which is generated by Spring boot when it is saved to the
   * database
   *
   * @author Mathieu Pestel
   */
  @Test
  public void testPersistAndLoadClient1() {
    // Create and persist client
    Client client = new Client();
    clientRepository.save(client);
    int roleId = client.getRoleId();

    client = clientRepository.findClientByroleId(roleId);

    assertNotNull(client);
    assertEquals(roleId, client.getRoleId());
  }

  /**
   * Test 2 for persistence of the Client class<br>
   * Gets the Client from the RegisteredUser (while this also tests the persistence of
   * RegisteredUser, this test makes sure the client is also appropriately saved).
   *
   * @author Mathieu Pestel
   */
  @Test
  public void testPersistAndLoadClient2() {
    // Create and persist client
    Client client = new Client();
    clientRepository.save(client);
    int roleId = client.getRoleId();

    String username = "matpes";
    String password = "123456789";
    String email = "matpestel@gmail.com";
    RegisteredUser registeredUser = new RegisteredUser(username, password, email);
    registeredUser.setAccountRole(client);
    registeredUserRepository.save(registeredUser);

    registeredUser = registeredUserRepository.findRegisteredUserByUsername(username);
    client = (Client) registeredUser.getAccountRole();

    assertNotNull(client);
    assertEquals(roleId, client.getRoleId());
  }
}