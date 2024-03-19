package ca.mcgill.ecse321.fitnessplusplus.service;

import ca.mcgill.ecse321.fitnessplusplus.model.OfferedClass;
import ca.mcgill.ecse321.fitnessplusplus.model.ScheduledClass;
import ca.mcgill.ecse321.fitnessplusplus.repository.OfferedClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OfferedClassService {

    @Autowired
    OfferedClassRepository offeredClassRepository;
    @Autowired
    ScheduledClassService scheduledClassService;

    @Transactional
    public OfferedClass requestClass(String classType, String classDescription) throws Exception {
        if (classType == null || classDescription == null)
            throw new Exception("Illegal arguments");
        OfferedClass requestedClass = new OfferedClass(classType, classDescription);
        offeredClassRepository.save(requestedClass);
        return requestedClass;
    }

    @Transactional
    public void removeOfferedClass(int offeredClassId) throws Exception {
        OfferedClass offeredClass = offeredClassRepository.findOfferedClassByOfferedClassId(offeredClassId);

        if (offeredClass == null)
            throw new Exception("You cannot remove an offered class that does not exist");

        List<ScheduledClass> scheduledClassList = scheduledClassService.getScheduledClassesByOfferedClass(offeredClass);

        for (ScheduledClass currentClass : scheduledClassList) {
            scheduledClassService.deleteScheduledClass(currentClass.getScheduledClassId(),
                    currentClass.getInstructor().getRoleId());
        }

        offeredClassRepository.delete(offeredClass);
        offeredClass.delete();
    }

}
