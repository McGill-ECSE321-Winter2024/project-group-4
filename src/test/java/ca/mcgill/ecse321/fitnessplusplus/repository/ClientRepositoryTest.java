package ca.mcgill.ecse321.fitnessplusplus.repository;

import ca.mcgill.ecse321.fitnessplusplus.model.Client;
import ca.mcgill.ecse321.fitnessplusplus.model.RegisteredUser;
import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class ClientRepositoryTest {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private RegisteredUserRepository registeredUserRepository;

    @AfterEach()
    public void clearDatabase() {
        registeredUserRepository.deleteAll();
        clientRepository.deleteAll();

    }

    @Test
    public void testPersistAndLoadClient1() {
        // Create and persist client
        Client client = new Client();
        int roleId = client.getRoleId();
        clientRepository.save(client);

        String username = "diviiniity";
        String password = "123456789";
        String email = "matpestel@gmail.com";
        RegisteredUser registeredUser = new RegisteredUser(username, password, email);
        registeredUser.setAccountRole(client);
        registeredUserRepository.save(registeredUser);

        registeredUser = registeredUserRepository.findRegisteredUserByUsername(username);
        client = (Client) registeredUser.getAccountRole();

        assertNotNull(client);
        assertNotNull(client.getRoleId());
    }
}
