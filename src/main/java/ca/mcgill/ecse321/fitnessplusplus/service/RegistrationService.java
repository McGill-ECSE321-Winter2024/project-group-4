package ca.mcgill.ecse321.fitnessplusplus.service;

import ca.mcgill.ecse321.fitnessplusplus.model.Client;
import ca.mcgill.ecse321.fitnessplusplus.model.Registration;
import ca.mcgill.ecse321.fitnessplusplus.model.ScheduledClass;
import ca.mcgill.ecse321.fitnessplusplus.repository.ClientRepository;
import ca.mcgill.ecse321.fitnessplusplus.repository.RegistrationRepository;
import ca.mcgill.ecse321.fitnessplusplus.repository.ScheduledClassRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;

@Service
public class RegistrationService {
    @Autowired
    RegistrationRepository registrationRepository;

    @Transactional
    public Registration createRegistration(Date aDateOfRegistration, Client aClient, ScheduledClass aScheduledClass) throws Exception {
        //We verify if the registration date is before today
        if (aDateOfRegistration.before(Date.valueOf(LocalDate.now()))) {
            throw new Exception("It is not possible to register for a class in the past");
        }

        //There can only be one registration per client and scheduledClass pair
        if (registrationRepository.findByClientAndScheduledClass(aClient, aScheduledClass) != null) {
            throw new Exception("IT is not possible for a client to register for the same scheduledCLass once again")
        }

        Registration registration = new Registration(aDateOfRegistration, aClient, aScheduledClass);
        registrationRepository.save(registration);
        return  registration;
    }
}
