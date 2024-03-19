package ca.mcgill.ecse321.fitnessplusplus.service;

import ca.mcgill.ecse321.fitnessplusplus.model.*;
import ca.mcgill.ecse321.fitnessplusplus.repository.RegisteredUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegisteredUserService {

    @Autowired
    RegisteredUserRepository registeredUserRepository;

    @Transactional
    public RegisteredUser createUser(String aUsername, String aPassword, String aEmail) throws Exception {
        if (aUsername == null || aPassword == null || aEmail == null)
            throw new Exception("Illegal arguments");
        RegisteredUser newUser = new RegisteredUser(aUsername, aPassword, aEmail);
        AccountRole newRole = new Client();
        newUser.setAccountRole(newRole);
        registeredUserRepository.save(newUser);
        return newUser;
    }

    @Transactional
    public RegisteredUser promoteUser(RegisteredUser registeredUser) throws Exception {
        if (registeredUser.getAccountRole().getClass() == Client.class) {
            registeredUser.setAccountRole(new Instructor());
        }
        else if (registeredUser.getAccountRole().getClass() == Instructor.class) {
            registeredUser.setAccountRole(new Owner());
        }
        else if (registeredUser.getAccountRole().getClass() == Owner.class) {
            throw new Exception("Cannot promote owner");
        }
        else {
            // if for some reason the account doesn't have a role
            registeredUser.setAccountRole(new Client());
        }

        return registeredUser;
    }

}
