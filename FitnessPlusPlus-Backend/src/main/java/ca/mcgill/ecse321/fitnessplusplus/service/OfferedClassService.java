package ca.mcgill.ecse321.fitnessplusplus.service;

import ca.mcgill.ecse321.fitnessplusplus.model.OfferedClass;
import ca.mcgill.ecse321.fitnessplusplus.model.ScheduledClass;
import ca.mcgill.ecse321.fitnessplusplus.repository.OfferedClassRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OfferedClassService {

  @Autowired OfferedClassRepository offeredClassRepository;
  @Autowired ScheduledClassService scheduledClassService;

  /**
   * Method to create an Offered Class
   *
   * @param classType
   * @param classDescription
   * @return created OfferedClass
   * @author Neil Joe George
   */
  @Transactional
  public OfferedClass requestClass(String classType, String classDescription) {
    if (classType == null || classDescription == null)
      throw new IllegalArgumentException("Illegal arguments");

    OfferedClass requestedClass = new OfferedClass(classType, classDescription);
    offeredClassRepository.save(requestedClass);
    return requestedClass;
  }

  /**
   * Method to delete an Offered Class
   *
   * @param offeredClassId
   * @return deleted OfferedClass
   * @throws Exception
   * @author Mathieu Pestel
   */
  @Transactional
  public OfferedClass removeOfferedClass(int offeredClassId) throws Exception {
    OfferedClass offeredClass =
        offeredClassRepository.findOfferedClassByOfferedClassId(offeredClassId);

    if (offeredClass == null) {
      throw new IllegalArgumentException("You cannot remove an offered class that does not exist");
    }

    List<ScheduledClass> scheduledClassList =
        scheduledClassService.getScheduledClassesByOfferedClass(offeredClass);

    for (ScheduledClass currentClass : scheduledClassList) {
      scheduledClassService.deleteScheduledClass(currentClass);
    }

    offeredClassRepository.delete(offeredClass);
    return offeredClass;
  }

  /**
   * Method that returns all Offered Classes
   *
   * @return List<OfferedClass>
   * @author Mathieu Pestel
   */
  @Transactional
  public List<OfferedClass> getAllOfferedClasses() {
    List<OfferedClass> list = new ArrayList<OfferedClass>();

    for (OfferedClass c : offeredClassRepository.findAll()) {
      list.add(c);
    }

    return list;
  }

  /**
   * Get a certain Offered Class By ID
   *
   * @param id
   * @return OfferedClass that was requested
   * @author Mathieu Pestel
   */
  @Transactional
  public OfferedClass getOfferedClassById(int id) {
    OfferedClass o = offeredClassRepository.findOfferedClassByOfferedClassId(id);
    if (o == null) {
      throw new IllegalArgumentException("OfferedClass with id " + id + " not found.");
    }
    return o;
  }

  @Transactional
  public OfferedClass approveOfferedClassById(int id) {
    OfferedClass offeredClass =
            offeredClassRepository.findOfferedClassByOfferedClassId(id);

    if (offeredClass == null) {
      throw new IllegalArgumentException("You cannot approve an offered class that does not exist");
    }

    offeredClass.setApproval(true);
    offeredClassRepository.save(offeredClass);
    return offeredClass;
  }

  @Transactional
  public List<OfferedClass> getAllOfferedClassesByApproval(boolean approval) {

      return new ArrayList<>(offeredClassRepository.findOfferedClassesByApproval(approval));
  }
}
