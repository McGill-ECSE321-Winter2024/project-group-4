package ca.mcgill.ecse321.fitnessplusplus.service;

import ca.mcgill.ecse321.fitnessplusplus.model.*;

import ca.mcgill.ecse321.fitnessplusplus.repository.ClientRepository;
import ca.mcgill.ecse321.fitnessplusplus.repository.InstructorRepository;
import ca.mcgill.ecse321.fitnessplusplus.repository.OwnerRepository;
import ca.mcgill.ecse321.fitnessplusplus.repository.RegisteredUserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class RegisteredUserService {

    @Autowired
    private RegisteredUserRepository registeredUserRepository;

    @Autowired
    private InstructorRepository instructorRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @Transactional
    public RegisteredUser createUser(String aUsername, String aPassword, String aEmail) {
        // Input validation
        if (aUsername == null || aPassword == null || aEmail == null)
            throw new IllegalArgumentException("Illegal arguments");

        RegisteredUser newUser = new RegisteredUser(aUsername, aPassword, aEmail);
        // When new user is created, set as client.
        newUser.setAccountRole(new Client());
        // Save both inside of AccountRole & RegisteredUser Repositories.
        Client client = (Client) newUser.getAccountRole();
        clientRepository.save(client);
        registeredUserRepository.save(newUser);
        return newUser;
    }

    @Transactional
    public RegisteredUser promoteUser(RegisteredUser registeredUser) {

        if (registeredUser == null) {
            throw new IllegalArgumentException("You cannot promote a user that does not exist");
        }

        if (registeredUser.getAccountRole().getClass() == Client.class) {
            // Delete client account role -> change to instructor
            clientRepository.delete((Client) registeredUser.getAccountRole());
            registeredUser.setAccountRole(new Instructor());
            // Re-save (Update) again (double check if we need to delete then save, or happens
            // automatically)
            registeredUserRepository.save(registeredUser);
            instructorRepository.save((Instructor) registeredUser.getAccountRole());

        } else if (registeredUser.getAccountRole().getClass() == Instructor.class) {
            instructorRepository.delete((Instructor) registeredUser.getAccountRole());
            registeredUser.setAccountRole(new Owner());

            registeredUserRepository.save(registeredUser);
            ownerRepository.save((Owner) registeredUser.getAccountRole());

        } else if (registeredUser.getAccountRole().getClass() == Owner.class) {
            throw new IllegalArgumentException("Cannot promote a owner");

        } else {
            // If for some reason the account doesn't have a role
            registeredUser.setAccountRole(new Client());
            registeredUserRepository.save(registeredUser);
            clientRepository.save((Client) registeredUser.getAccountRole());
        }

        return registeredUser;
    }

    @Transactional
    public List<RegisteredUser> getAllRegisteredUser() {
        List<RegisteredUser> list = new ArrayList<RegisteredUser>();

        for (RegisteredUser r : registeredUserRepository.findAll()) {
            list.add(r);
        }

        return list;
    }

    @Transactional
    public RegisteredUser getRegisteredUserById(int id){return registeredUserRepository.findRegisteredUserByUserId(id);}

    @Transactional
    public Client getClientById(int id){
        return clientRepository.findClientByroleId(id);
    }

    @Transactional
    public Instructor getInstructorById(int id){
        return instructorRepository.findInstructorByroleId(id);
    }

    @Transactional
    public Owner getOwnerById(int id){
        return ownerRepository.findInstructorByroleId(id);
    }

}
