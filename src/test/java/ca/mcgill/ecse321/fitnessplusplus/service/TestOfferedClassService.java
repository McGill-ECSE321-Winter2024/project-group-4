package ca.mcgill.ecse321.fitnessplusplus.service;

import ca.mcgill.ecse321.fitnessplusplus.model.OfferedClass;
import ca.mcgill.ecse321.fitnessplusplus.repository.OfferedClassRepository;
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
public class TestOfferedClassService {

    @Mock
    private OfferedClassRepository offeredClassDao;
    @Mock
    private ScheduledClassService scheduledClassService;

    @InjectMocks
    private OfferedClassService offeredClassService;

    private static final int OfferedClass_KEY = 123;
    private static final String OfferedClass_Type = "Running";
    private static final String OfferedClass_Description = "Learn to run with us!";

    @BeforeEach
    public void setMockOutput() {

        lenient().when(offeredClassDao.findOfferedClassByOfferedClassId(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(OfferedClass_KEY)) {
                OfferedClass offeredClass = new OfferedClass();
                offeredClass.setClassType(OfferedClass_Type);
                offeredClass.setDescription(OfferedClass_Description);
                offeredClass.setOfferedClassId(OfferedClass_KEY);
                return offeredClass;
            }
            else {
                return null;
            }
        });

    }

    @Test
    public void testRemoveOfferedClass() {
        OfferedClass offeredClass = offeredClassService.requestClass(OfferedClass_Type, OfferedClass_Description);
        int id = offeredClass.getId();
        OfferedClass deletedOfferedClass = offeredClassService.removeOfferedClass(offeredClass);
        assertNotNull(deletedOfferedClass);
        assertEquals(id, deletedOfferedClass.getId());
    }

    @Test
    public void testRemoveOfferedClassInvalidOfferedClass() {
        OfferedClass deletedOfferedClass = null;
        String error = null;
        try {
            deletedOfferedClass = offeredClassService.removeOfferedClass(null);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertEquals("You cannot remove an offered class that does not exist", error);
        assertNull(deletedOfferedClass);
    }

}
