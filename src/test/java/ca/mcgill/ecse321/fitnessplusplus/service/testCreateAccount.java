package ca.mcgill.ecse321.fitnessplusplus.service;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import ca.mcgill.ecse321.fitnessplusplus.model.*;
import ca.mcgill.ecse321.fitnessplusplus.repository.RegisteredUserRepository;
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

import ca.mcgill.ecse321.fitnessplusplus.repository.InstructorRepository;
import ca.mcgill.ecse321.fitnessplusplus.repository.OfferedClassRepository;
import ca.mcgill.ecse321.fitnessplusplus.repository.ScheduledClassRepository;

public class testCreateAccount {

    @Mock
    private RegisteredUserRepository registeredUserRepository;


    @InjectMocks
    private RegisteredUserService userService;
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
        lenient().when(registeredUserRepository.findRegisteredUserByUserId(any(Integer.class)))
                .thenAnswer((InvocationOnMock invocation) -> {
                    if (invocation.getArgument(0).equals(PERSON_KEY)) {
                        RegisteredUser registeredUser = new RegisteredUser(PERSON_KEY, PERSON_KEY, PERSON_KEY);
                        return registeredUser;
                    } else {
                        return null;
                    }
                });
    }

    @Test
    public void testCreateAccount() {
        assertEquals(0, registeredUserRepository.count());

        String name = "Lello";
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

        String name = "Test1";
        String pwd = "Password";
        String email = "a@a.com";
        Client customer = new Client();
        RegisteredUser account = null;
        String error = null;
        try {
            account = registeredUserService.createUser(name, pwd, email, customer);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNotNull(account);
        try {
            account = registeredUserService.createUser(name, pwd, email, customer);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertEquals(error, "Account Exists");
    }
}
