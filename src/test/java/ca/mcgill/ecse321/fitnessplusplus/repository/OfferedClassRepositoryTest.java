package ca.mcgill.ecse321.fitnessplusplus.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import ca.mcgill.ecse321.fitnessplusplus.model.OfferedClass;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class OfferedClassRepositoryTest {
    @Autowired private OfferedClassRepository offeredClassRepository;

    @AfterEach
    public void clearDatabase() {
        offeredClassRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadOfferedClass1() {
        /*
        OfferedClass offeredClass = new OfferedClass();
        offeredClassRepository.save(offeredClass);
        int classId = offeredClass.getId();

        offeredClass = offeredClassRepository.findOfferedClassByclassType(classId);

        assertNotNull(offeredClass);
        assertEquals(classType, offeredClass.getClassType());
         */
    }
}
