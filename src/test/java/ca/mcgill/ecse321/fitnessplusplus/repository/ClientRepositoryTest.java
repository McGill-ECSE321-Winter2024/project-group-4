package ca.mcgill.ecse321.fitnessplusplus.repository;

import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;

public class ClientRepositoryTest {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private RegisteredUserRepository registeredUserRepository;

    @AfterEach
    public void clearDatabase() {
        clientRepository.deleteAll();
        registeredUserRepository.deleteAll();
    }
}
