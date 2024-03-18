package ca.mcgill.ecse321.fitnessplusplus.service;

import ca.mcgill.ecse321.fitnessplusplus.model.Client;
import ca.mcgill.ecse321.fitnessplusplus.model.Registration;
import ca.mcgill.ecse321.fitnessplusplus.model.ScheduledClass;
import ca.mcgill.ecse321.fitnessplusplus.repository.ClientRepository;
import ca.mcgill.ecse321.fitnessplusplus.repository.RegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;

@Service
public class AccountService {
    @Autowired
    ClientRepository clientRepository;

    @Transactional
    public Client createClient(int ID) throws IllegalArgumentException {
        //We verify if the registration date is before today
        if (clientRepository.existsById(ID)) {
            throw new IllegalArgumentException("Client already exists");
        }

        Client client = new Client(ID);
        clientRepository.save(client);
        return  client;
    }


}
