package ca.mcgill.ecse321.fitnessplusplus.service;

import ca.mcgill.ecse321.fitnessplusplus.model.*;
import ca.mcgill.ecse321.fitnessplusplus.repository.ClientRepository;
import ca.mcgill.ecse321.fitnessplusplus.repository.InstructorRepository;
import ca.mcgill.ecse321.fitnessplusplus.repository.OwnerRepository;
import ca.mcgill.ecse321.fitnessplusplus.repository.RegisteredUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class TestRegisteredUserService {

    @Mock
    private RegisteredUserRepository registeredUserDao;
    @Mock
    private ClientRepository clientDao;
    @Mock
    private InstructorRepository instructorDao;
    @Mock
    private OwnerRepository ownerDao;

    @InjectMocks
    private RegisteredUserService registeredUserService;

    private static final int RegisteredUser_KEY = 123;
    private static final String RegisteredUser_Username = "johndoe";
    private static final String RegisteredUser_Password = "123456789";
    private static final String RegisteredUser_Email = "test@gmail.com";

    @BeforeEach
    public void setMockOutput() {
        lenient().when(registeredUserDao.findRegisteredUserByUserId(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(RegisteredUser_KEY)) {
                RegisteredUser registeredUser = new RegisteredUser(RegisteredUser_Username, RegisteredUser_Password, RegisteredUser_Email);
                registeredUser.setUserId(RegisteredUser_KEY);
                return registeredUser;
            } else {
                return null;
            }
        });
    }

    @Test
    public void testPromoteClientToInstructor() {
        RegisteredUser registeredUser = registeredUserService.createUser(RegisteredUser_Username, RegisteredUser_Password, RegisteredUser_Email);
        int id = registeredUser.getUserId();

        RegisteredUser promotedUser = registeredUserService.promoteUser(registeredUser);

        assertNotNull(promotedUser);
        assertEquals(promotedUser.getAccountRole().getClass(), Instructor.class);
    }

    @Test
    public void testPromoteInstructorToOwner() {
        RegisteredUser registeredUser = registeredUserService.createUser(RegisteredUser_Username, RegisteredUser_Password, RegisteredUser_Email);
        int id = registeredUser.getUserId();
        registeredUser.setAccountRole(new Instructor());

        RegisteredUser promotedUser = registeredUserService.promoteUser(registeredUser);

        assertNotNull(promotedUser);
        assertEquals(promotedUser.getAccountRole().getClass(), Owner.class);
    }

    @Test
    public void testPromoteOwner() {
        RegisteredUser registeredUser = registeredUserService.createUser(RegisteredUser_Username, RegisteredUser_Password, RegisteredUser_Email);
        int id = registeredUser.getUserId();
        registeredUser.setAccountRole(new Owner());

        RegisteredUser promotedUser = null;
        String error = null;

        try {
            promotedUser = registeredUserService.promoteUser(registeredUser);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertNull(promotedUser);
        assertEquals("Cannot promote a owner", error);
        assertEquals(registeredUser.getAccountRole().getClass(), Owner.class);
    }

    @Test
    public void testPromoteInvalidUser() {
        RegisteredUser registeredUser = null;
        String error = null;

        try {
            registeredUser = registeredUserService.promoteUser(null);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("You cannot promote a user that does not exist", error);
        assertNull(registeredUser);
    }

}
