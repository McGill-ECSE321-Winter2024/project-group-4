package ca.mcgill.ecse321.fitnessplusplus.service;

import ca.mcgill.ecse321.fitnessplusplus.model.OfferedClass;
import ca.mcgill.ecse321.fitnessplusplus.model.RegisteredUser;
import ca.mcgill.ecse321.fitnessplusplus.model.ScheduledClass;
import ca.mcgill.ecse321.fitnessplusplus.repository.OfferedClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class OfferedClassService {

    @Autowired
    OfferedClassRepository offeredClassRepository;
    @Autowired
    ScheduledClassService scheduledClassService;

    
    @Transactional
    public OfferedClass requestClass(String classType, String classDescription) {
        if (classType == null || classDescription == null)
            throw new IllegalArgumentException("Illegal arguments");

        OfferedClass requestedClass = new OfferedClass(classType, classDescription);
        offeredClassRepository.save(requestedClass);
        return requestedClass;
    }

    @Transactional
    public OfferedClass removeOfferedClass(OfferedClass offeredClass) {

        if (offeredClass == null) {
            throw new IllegalArgumentException("You cannot remove an offered class that does not exist");
        }

        List<ScheduledClass> scheduledClassList = scheduledClassService.getScheduledClassesByOfferedClass(offeredClass);

        for (ScheduledClass currentClass : scheduledClassList) {
            scheduledClassService.deleteScheduledClass(currentClass.getScheduledClassId(),
                    currentClass.getInstructor().getRoleId());
        }

        offeredClassRepository.delete(offeredClass);
        return offeredClass;
    }

    @Transactional
    public List<OfferedClass> getAllOfferedClasses() {
        List<OfferedClass> list = new ArrayList<OfferedClass>();

        for (OfferedClass c : offeredClassRepository.findAll()) {
            list.add(c);
        }

        return list;
    }

    @Transactional
    public OfferedClass getOfferedClassById(int id){return offeredClassRepository.findOfferedClassByOfferedClassId(id);}

}
