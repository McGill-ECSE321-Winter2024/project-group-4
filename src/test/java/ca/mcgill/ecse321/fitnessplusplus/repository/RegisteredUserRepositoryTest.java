package ca.mcgill.ecse321.fitnessplusplus.repository;

import ca.mcgill.ecse321.fitnessplusplus.model.RegisteredUser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class RegisteredUserRepositoryTest {
    @Autowired
    private RegisteredUserRepository registeredUserRepository;

    /**
     * Method to clear each the registered user repo after each test has been completed
     *
     * @author Neil Joe George
     */
    @AfterEach
    public void clearDatabase() {
        registeredUserRepository.deleteAll();
    }

    /**
     * Testing of reading and writing of RegisteredUser
     *
     * @author Neil Joe George
     */
    @Test
    public void testReadWriteRegisteredUser() {
        String username = "John_Doe";
        String password = "1234567890";
        String email = "john.doe@gmail.com";
        RegisteredUser registeredUser = new RegisteredUser(username, password, email);

        registeredUserRepository.save(registeredUser);

        RegisteredUser retrievedUser = registeredUserRepository.findRegisteredUserByUsername("john_doe");

        // Assert there exists a user with that username
        assertNotNull(retrievedUser);

        // Assert attributes of the retrieved user match the original user
        assertEquals(username, retrievedUser.getUsername());
        assertEquals(password, retrievedUser.getPassword());
        assertEquals(email, retrievedUser.getEmail());
    }

    /**
     * Test writing and reading attributes of RegisteredUser
     * Edits the attribute values and checks if the values have been updated in the repository
     *
     * @author Neil Joe George
     */
    @Test
    public void testReadWriteRegisteredUserAttributes() {
        String username = "John_Doe";
        String password = "1234567890";
        String email = "john.doe@gmail.com";
        RegisteredUser registeredUser = new RegisteredUser(username, password, email);

        registeredUserRepository.save(registeredUser);

        RegisteredUser retrievedUser = registeredUserRepository.findRegisteredUserByUsername("john_doe");

        // Assert there exists a user with that username
        assertNotNull(retrievedUser);


        String newEmail = "john.doe2@gmail.com";
        String newPassword = "0987654321";
        // Setting new password and email to the user
        retrievedUser.setEmail(newEmail);
        retrievedUser.setPassword(newPassword);

        RegisteredUser retrievedUpdatedUser = registeredUserRepository.findRegisteredUserByUsername("john_doe");

        // Assert attributes of the retrieved user match the original user
        assertEquals(newEmail, retrievedUpdatedUser.getEmail());
        assertEquals(newPassword, retrievedUpdatedUser.getPassword());
        assertEquals(email, retrievedUser.getEmail());
    }


}
