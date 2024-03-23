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

    private static final String offeredClass_Type = "Running";
    private static final String offeredClass_Description = "Learn to run with us!";

    @BeforeEach
    public void setMockOutput() {

        lenient().when(offeredClassDao.findOfferedClassByOfferedClassId(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(0)) {
                OfferedClass offeredClass = new OfferedClass(offeredClass_Type,offeredClass_Description);
                return offeredClass;
            }
            else {
                return null;
            }
        });

    }

    @Test
    public void testRemoveOfferedClass() {
        OfferedClass offeredClass = offeredClassService.requestClass(offeredClass_Type, offeredClass_Description);
        int id = offeredClass.getId();
        String error = null;

        try {
            offeredClassService.removeOfferedClass(id);
        } catch(Exception e){
            error = e.getMessage();
        } 
        assertNull(error);
    }

    @Test
    public void testRemoveOfferedClassInvalidOfferedClass() {
        String error = null;
        try {
            offeredClassService.removeOfferedClass(4);
        } catch (Exception e) {
            error = e.getMessage();
        }
        assertNotNull(error);
    }

}
