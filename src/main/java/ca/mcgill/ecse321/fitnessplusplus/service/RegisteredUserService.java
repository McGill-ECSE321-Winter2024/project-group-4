package ca.mcgill.ecse321.fitnessplusplus.service;

import ca.mcgill.ecse321.fitnessplusplus.dto.AccountRoleDto;
import ca.mcgill.ecse321.fitnessplusplus.model.AccountRole;
import ca.mcgill.ecse321.fitnessplusplus.model.Client;
import ca.mcgill.ecse321.fitnessplusplus.model.OfferedClass;
import ca.mcgill.ecse321.fitnessplusplus.model.RegisteredUser;
import ca.mcgill.ecse321.fitnessplusplus.repository.RegisteredUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class RegisteredUserService {

    @Autowired
    RegisteredUserRepository registeredUserRepository;

    @Transactional
    public RegisteredUser createUser(String aUsername, String aPassword, String aEmail) throws Exception {
        if (aUsername == null || aPassword == null || aEmail == null) throw new Exception("Illegal arguments");
        RegisteredUser newUser = new RegisteredUser(aUsername, aPassword, aEmail);
        AccountRole newRole = new Client();
        AccountRoleDto newRoleDTO = new AccountRoleDto(newRole.getRoleId());
        newUser.setAccountRole(newRole);
        registeredUserRepository.save(newUser);
        return newUser;
    }


}
