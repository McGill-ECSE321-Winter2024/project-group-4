package ca.mcgill.ecse321.fitnessplusplus.service;

import ca.mcgill.ecse321.fitnessplusplus.model.Client;
import ca.mcgill.ecse321.fitnessplusplus.model.OfferedClass;
import ca.mcgill.ecse321.fitnessplusplus.model.Registration;
import ca.mcgill.ecse321.fitnessplusplus.model.ScheduledClass;
import ca.mcgill.ecse321.fitnessplusplus.repository.OfferedClassRepository;
import ca.mcgill.ecse321.fitnessplusplus.repository.RegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;

public class OfferedClassService {

    @Autowired
    OfferedClassRepository offeredClassRepository;

    @Transactional
    public OfferedClass requestClass(String classType, String classDescription) throws Exception {
        if (classType == null || classDescription == null) throw new Exception("Illegal arguments");
        OfferedClass requestedClass = new OfferedClass(classType,classDescription);
        offeredClassRepository.save(requestedClass);
        return  requestedClass;
    }

}
