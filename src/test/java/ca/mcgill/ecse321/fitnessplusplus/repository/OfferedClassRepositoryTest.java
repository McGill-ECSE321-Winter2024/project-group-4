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
    OfferedClass offeredClass = new OfferedClass();
    offeredClassRepository.save(offeredClass);
    String classType = offeredClass.getClassType();

    offeredClass = offeredClassRepository.findOfferedClassByOfferedClassType(classType);

    assertNotNull(offeredClass);
    assertEquals(classType, offeredClass.getClassType());
  }

  @Test
  public void testPersistAndLoadOfferedClass2() {
    OfferedClass offeredClass = new OfferedClass();
    offeredClassRepository.save(offeredClass);
    String classType = offeredClass.getClassType();

    offeredClass = offeredClassRepository.findOfferedClassByOfferedClassType(classType);

    classType = "Strength";
    offeredClass.setClassType(classType);
    String description = "Strength class";
    offeredClass.setDescription(description);

    offeredClassRepository.save(offeredClass);

    OfferedClass updatedOfferedClass =
        offeredClassRepository.findOfferedClassByOfferedClassType(classType);

    assertEquals(classType, updatedOfferedClass.getClassType());
    assertEquals(description, updatedOfferedClass.getDescription());
  }
}
