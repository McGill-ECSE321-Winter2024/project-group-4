package ca.mcgill.ecse321.fitnessplusplus.service;

import ca.mcgill.ecse321.fitnessplusplus.model.Client;
import ca.mcgill.ecse321.fitnessplusplus.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountService {
    @Autowired
    ClientRepository clientRepository;

    @Transactional
    public Client createClient() {
        // We verify if the registration date is before today
        Client client = new Client();
        clientRepository.save(client);
        return client;
    }

    @Transactional
    public Client getClientById(int ID) {
        return clientRepository.findClientByroleId(ID);
    }

}
