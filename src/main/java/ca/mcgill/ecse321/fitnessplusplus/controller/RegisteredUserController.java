package ca.mcgill.ecse321.fitnessplusplus.controller;

import ca.mcgill.ecse321.fitnessplusplus.dto.RegisteredUserDto;
import ca.mcgill.ecse321.fitnessplusplus.dto.ScheduledClassDto;
import ca.mcgill.ecse321.fitnessplusplus.model.RegisteredUser;
import ca.mcgill.ecse321.fitnessplusplus.model.ScheduledClass;
import ca.mcgill.ecse321.fitnessplusplus.service.AccountService;
import ca.mcgill.ecse321.fitnessplusplus.service.RegisteredUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

public class RegisteredUserController {
    @Autowired
    RegisteredUserService registeredUserService;
    @Autowired
    AccountService accountService;

    @PostMapping(value = { "/register-user", "/register-user/" })
    public RegisteredUserDto createScheduledClass(
            @RequestParam String aUsername, @RequestParam String aPassword, @RequestParam String aEmail)
            throws Exception {
        return convertToDto(registeredUserService.createUser(aUsername, aPassword, aEmail));
    }

    private RegisteredUserDto convertToDto(RegisteredUser o) {
        if (o == null) {
            throw new IllegalArgumentException("Scheduled Class does not exist.");
        }
        return new RegisteredUserDto(o.getUsername(), o.getPassword(), o.getEmail());

    }

}
