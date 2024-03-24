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
import org.mockito.stubbing.Answer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class TestRegisteredUserService {

    @Mock
    private RegisteredUserRepository registeredUserRepository;
    @Mock
    private ClientRepository clientRepository;
    @Mock
    private InstructorRepository instructorRepository;
    @Mock
    private OwnerRepository ownerRepository;

    @InjectMocks
    private RegisteredUserService registeredUserService;

    private static final int USER_ID1 = 0;
    private static final int USER_ID2 = 1;
    private static final int USER_ID3 = 2;

    @BeforeEach
    public void setMockOutput() {
        lenient().when(registeredUserRepository.findRegisteredUserByUserId(anyInt()))
                .thenAnswer((InvocationOnMock invocation) -> {
                    if (invocation.getArgument(0).equals(USER_ID1)) {
                        RegisteredUser registeredUser = new RegisteredUser();
                        registeredUser.setUserId(USER_ID1);
                        registeredUser.setUsername("johndoe");
                        registeredUser.setPassword("test@gmail.com");
                        registeredUser.setAccountRole(new Client(USER_ID1));
                        return registeredUser;
                    } else if (invocation.getArgument(0).equals(USER_ID2)) {
                        RegisteredUser registeredUser = new RegisteredUser();
                        registeredUser.setUserId(USER_ID2);
                        registeredUser.setUsername("johndoe");
                        registeredUser.setPassword("test@gmail.com");
                        registeredUser.setAccountRole(new Instructor(USER_ID2));
                        return registeredUser;
                    } else if (invocation.getArgument(0).equals(USER_ID3)) {
                        RegisteredUser registeredUser = new RegisteredUser();
                        registeredUser.setUserId(USER_ID3);
                        registeredUser.setUsername("johndoe");
                        registeredUser.setPassword("test@gmail.com");
                        registeredUser.setAccountRole(new Owner(USER_ID3));
                        return registeredUser;
                    } else {
                        return null;
                    }
                });

        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
            return invocation.getArgument(0);
        };
        lenient().when(instructorRepository.save(any(Instructor.class))).thenAnswer(returnParameterAsAnswer);
        lenient().when(ownerRepository.save(any(Owner.class))).thenAnswer(returnParameterAsAnswer);
        lenient().when(clientRepository.save(any(Client.class))).thenAnswer(returnParameterAsAnswer);
        lenient().when(registeredUserRepository.save(any(RegisteredUser.class))).thenAnswer(returnParameterAsAnswer);
        // For a method that returns void
        lenient().doNothing().when(clientRepository).delete(any(Client.class));
        lenient().doNothing().when(instructorRepository).delete(any(Instructor.class));
        
        
    }

    @Test
    public void testPromoteClientToInstructor() {
        RegisteredUser user = registeredUserService.promoteUser(USER_ID1);
        assertEquals(Instructor.class, user.getAccountRole().getClass());
    }

    @Test
    public void testPromoteInstructorToOwner() {
        RegisteredUser user = registeredUserService.promoteUser(USER_ID2);
        assertEquals(Owner.class, user.getAccountRole().getClass());
    }

    @Test
    public void testPromoteOwner() {
        String message = null;
        try {
            registeredUserService.promoteUser(USER_ID3);
        } catch (Exception e) {
            message = e.getMessage();
        }
        assertEquals("Cannot promote a owner", message);

    }

    @Test
    public void testPromoteInvalidUser() {
        String message = null;
        try {
            registeredUserService.promoteUser(18);
        } catch (Exception e) {
            message = e.getMessage();
        }
        assertEquals("Cannot promote a user that does not exist", message);

    }

}
