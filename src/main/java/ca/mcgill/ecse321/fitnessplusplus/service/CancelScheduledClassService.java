package ca.mcgill.ecse321.fitnessplusplus.service;

import ca.mcgill.ecse321.fitnessplusplus.model.Instructor;
import ca.mcgill.ecse321.fitnessplusplus.model.OfferedClass;
import ca.mcgill.ecse321.fitnessplusplus.model.ScheduledClass;
import ca.mcgill.ecse321.fitnessplusplus.repository.ClientRepository;
import ca.mcgill.ecse321.fitnessplusplus.repository.ScheduledClassRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ca.mcgill.ecse321.fitnessplusplus.model.Client;

@Service
public class CancelScheduledClassService {

    @Autowired
    private ScheduledClassRepository scheduledClassRepo;

    @Autowired
    private ClientRepository ClientRepo;

    @Transactional
    public ScheduledClass getScheduledClass(int scheduledClassId) {
        return scheduledClassRepo.findScheduledClassByscheduledClassId(scheduledClassId);

    }


    @Transactional
    public void removeScheduledClass(int scheduledClassId, Instructor aInstructor) throws Exception {
        //we get the scheduled class we want to remove
        ScheduledClass scheduledClass = getScheduledClass(scheduledClassId);

        //we first remove the instructor
        if (scheduledClass != null && scheduledClass.getInstructor().equals(aInstructor)) {
            scheduledClassRepo.delete(scheduledClass);
            scheduledClass.delete();
        }



    }

}
