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
import java.util.ArrayList;
import java.util.List;

@Service
public class RegistrationService {
    @Autowired
    RegistrationRepository registrationRepository;

    /**
     * Method to create a registration, while checking the validity of the parameters
     *
     * @param aDateOfRegistration Date of registration
     * @param aClient Registering Client
     * @param aScheduledClass Class being registered for
     *
     * @return Registration
     * @throws Exception
     *
     * @author Yonatan Bensimon
     */
    @Transactional
    public Registration createRegistration(Date aDateOfRegistration, Client aClient, ScheduledClass aScheduledClass) throws Exception {
        //We verify if the registration date is before today
        if (aDateOfRegistration.before(Date.valueOf(LocalDate.now()))) {
            throw new Exception("It is not possible to register for a class in the past");
        }

        //There can only be one registration per client and scheduledClass pair
        if (registrationRepository.findByClientAndScheduledClass(aClient, aScheduledClass) != null) {
            throw new Exception("IT is not possible for a client to register for the same scheduledCLass once again");
        }

        Registration registration = new Registration(aDateOfRegistration, aClient, aScheduledClass);
        registrationRepository.save(registration);
        return  registration;
    }

    /**
     * Returns a list of all registrations in the repository
     *
     * @return List<Registration>
     *
     * @author Yonatan Bensimon
     */
    @Transactional
    public List<Registration> getAllRegistrations() {
        List<Registration> list = new ArrayList<Registration>();

        for (Registration r: registrationRepository.findAll()) {
            list.add(r);
        }

        return list;
    }

    /**
     * Returns a Registration by its ID
     *
     * @param registrationID ID of the desired Registration
     * @return Registration
     *
     * @author Yonatan Bensimon
     */
    @Transactional
    public Registration getRegistrationByID(int registrationID) {
        return registrationRepository.findRegistrationByregistrationId(registrationID);
    }

    //TODO Do i really need this function?
    /*
    @Transactional
    public Registration getRegistrationByClientAndScheduledClass(Client aClient, ScheduledClass aScheduledClass) {
        return registrationRepository.findByClientAndScheduledClass(aClient, aScheduledClass);
    }*/

    /**
     * Removes a Registration from the repository by its ID
     *
     * @param scheduledClassID Registration ID to be removed
     * @throws Exception
     *
     * @author Yonatan Bensimon
     */
    @Transactional
    public void removeRegistration(int scheduledClassID) throws Exception {
        Registration registration = registrationRepository.findRegistrationByregistrationId(scheduledClassID);

        if (registration == null) {
            throw new Exception("You cannot remove a registration that does not exist");
        }

        if (registration.getDateOfRegistration().before(Date.valueOf(LocalDate.now()))) {
            throw new Exception("YOu cannot remove a registration that has already passed");
        }

        registrationRepository.delete(registration);
        registration.delete();
    }
}
