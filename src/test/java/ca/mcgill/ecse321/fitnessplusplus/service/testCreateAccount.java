package ca.mcgill.ecse321.fitnessplusplus.service;
import java.util.*;

import ca.mcgill.ecse321.fitnessplusplus.model.*;
import ca.mcgill.ecse321.fitnessplusplus.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class testCreateAccount {
    @Mock
    private RegisteredUserRepository registeredUserRepository;
    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private RegisteredUserService registeredUserService;

    @BeforeEach
    public void setMockOutput() {
        lenient().when(registeredUserRepository.findAll())
                .thenAnswer((InvocationOnMock invocation) -> {
                    RegisteredUser user1 = new RegisteredUser("Lello", "Lello", "Lello");
                    List<RegisteredUser> users = new ArrayList<RegisteredUser>();
                    users.add(user1);
                    return users;
                });
        lenient().when(registeredUserRepository.findRegisteredUserByUsername(any(String.class)))
                .thenAnswer((InvocationOnMock invocation) -> {
                    RegisteredUser user1 = registeredUserService.createUser("Lello", "Lello", "Lello");
                    return user1;
                });
    }

    @Test
    public void testCreateAnAccount() {
        assertEquals(0, registeredUserRepository.count());

        String name = "Lello1";
        RegisteredUser account = null;
        Client customer = new Client();
        try {
            account = registeredUserService.createUser(name, name, name, customer);
        } catch (IllegalArgumentException e) {
            fail();
        }
        assertNotNull(account);
        assertEquals(name, account.getUsername());
    }
    @Test
    public void testNullAccount() {
        assertEquals(0, registeredUserRepository.count());

        String name = null;
        RegisteredUser account = null;
        String error = null;
        Client customer = new Client();
        try {
            account = registeredUserService.createUser(name, name, name, customer);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(account);
        assertEquals(error, "Illegal arguments");
    }
    @Test
    public void testExistingAccount() {
        assertEquals(0, registeredUserRepository.count());

        String name = "Lello";
        @SuppressWarnings("unused")
        RegisteredUser account = null;
        Client customer = new Client();
        String error = null;
        try {
            account = registeredUserService.createUser(name, name, name, customer);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertEquals("Account Exists", error);
    }
}
