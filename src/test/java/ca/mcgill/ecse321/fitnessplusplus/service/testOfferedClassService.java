package ca.mcgill.ecse321.fitnessplusplus.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.fitnessplusplus.repository.InstructorRepository;
import ca.mcgill.ecse321.fitnessplusplus.repository.OfferedClassRepository;
import ca.mcgill.ecse321.fitnessplusplus.repository.ScheduledClassRepository;
import ca.mcgill.ecse321.fitnessplusplus.model.Instructor;
import ca.mcgill.ecse321.fitnessplusplus.model.OfferedClass;
import ca.mcgill.ecse321.fitnessplusplus.model.ScheduledClass;

@ExtendWith(MockitoExtension.class)
public class testOfferedClassService {
    @Mock
    private OfferedClassRepository offeredClassRepository;
    @InjectMocks
    private OfferedClassService offeredClassService;
    public static final Integer OFFERED_CLASS_KEY = 0;


    @BeforeEach
    public void setMockOutput() {
        lenient().when(offeredClassRepository.findOfferedClassByOfferedClassId(any(Integer.class)))
                .thenAnswer((InvocationOnMock invocation) -> {
                    if (invocation.getArgument(0).equals(OFFERED_CLASS_KEY)) {
                        OfferedClass offeredClass = new OfferedClass("Boxing", "Make em bleed");
                        offeredClass.setOfferedClassId(OFFERED_CLASS_KEY);
                        return offeredClass;
                    } else {
                        return null;
                    }
                });
    }

    @Test
    public void testSuccessfulRequestClass() {
        String classType = "strength";
        String description = "Strength training class";
        int classId = OFFERED_CLASS_KEY;

        OfferedClass offeredClass = null;
        OfferedClassService offeredClassService1 = new OfferedClassService();

        try{
            offeredClass = offeredClassService1.requestClass(classType, description, OFFERED_CLASS_KEY);
        } catch (Exception e) {
            fail();
        }

        assertEquals(offeredClass.getClassType(), classType);
        assertEquals(offeredClass.getDescription(), description);
        assertEquals(offeredClass.getId(), classId);
    }
}
