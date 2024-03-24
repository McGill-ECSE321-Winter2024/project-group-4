package ca.mcgill.ecse321.fitnessplusplus.service;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
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
import org.mockito.stubbing.Answer;

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
    private static final String newName = "Neil Joe George";
    private static final String newPassword = "123";
    private static final String newEmail = "Neil.George@mcgill.ca";

    private static final String managerName = "manager";
    private static final String managerPwd = "manager";
    private static final String managerEmil = "manager@mcgill.ca";

    private static final String PERSON_KEY = "TestPerson";
    private static final String NONEXISTING_KEY = "NotAPerson";

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
        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
            return invocation.getArgument(0);
        };
    }

    @Test
    public void testCreateAccount() {
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
