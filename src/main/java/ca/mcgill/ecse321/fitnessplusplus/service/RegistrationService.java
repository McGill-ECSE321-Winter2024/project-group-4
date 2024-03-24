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
    private RegistrationRepository registrationRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ScheduledClassRepository scheduledClassRepository;

    /**
     * Method to create a registration, while checking the validity of the
     * parameters
     *
     * @param aDateOfRegistration Date of registration
     * @param aClient             Registering Client
     * @param aScheduledClass     Class being registered for
     *
     * @return Registration
     * @throws Exception
     *
     * @author Yonatan Bensimon
     */
    @Transactional
    public Registration createRegistration(Date aDateOfRegistration, int clientId, int classId) {
        Client aClient = clientRepository.findClientByroleId(clientId);
        ScheduledClass aScheduledClass = scheduledClassRepository.findScheduledClassByscheduledClassId(classId);

        // We verify if the registration date is before today
        if (aScheduledClass.getDate().before(aDateOfRegistration)) {
            throw new IllegalArgumentException("It is not possible to register for a class in the past");
        }

        // check for repeated registration
        ArrayList<Registration> registrations = (ArrayList<Registration>) getAllRegistrations();
        for (Registration e : registrations) {
            if (e.getClient().getRoleId() == clientId && e.getDateOfRegistration().equals(aDateOfRegistration)
                    && e.getScheduledClass().getScheduledClassId() == classId) {
                throw new IllegalArgumentException("A Client cannot register for a class more than once.");
            }
        }

        Registration registration = new Registration(aDateOfRegistration, aClient, aScheduledClass);
        registrationRepository.save(registration);
        return registration;
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

        for (Registration r : registrationRepository.findAll()) {
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

    /**
     * Removes a Registration from the repository by its ID
     *
     * @param registrationID Registration ID to be removed
     * @throws Exception
     *
     * @author Yonatan Bensimon
     */
    @Transactional
    public void removeRegistration(int registrationID) throws Exception {
        Registration registration = registrationRepository.findRegistrationByregistrationId(registrationID);

        if (registration == null) {
            throw new Exception("You cannot remove a registration that does not exist");
        }

        if (registration.getScheduledClass().getDate().before(Date.valueOf(LocalDate.now()))) {
            throw new Exception("You cannot remove a registration that has already passed");
        }

        registrationRepository.delete(registration);
        registration.delete();
    }
}
