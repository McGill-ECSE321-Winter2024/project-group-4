package ca.mcgill.ecse321.fitnessplusplus.controller;

import ca.mcgill.ecse321.fitnessplusplus.dto.AccountRoleDto;
import ca.mcgill.ecse321.fitnessplusplus.dto.RegisteredUserDto;
import ca.mcgill.ecse321.fitnessplusplus.model.Client;
import ca.mcgill.ecse321.fitnessplusplus.model.RegisteredUser;
import ca.mcgill.ecse321.fitnessplusplus.service.AccountService;
import ca.mcgill.ecse321.fitnessplusplus.model.AccountRole;
import ca.mcgill.ecse321.fitnessplusplus.service.RegisteredUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
public class AccountController {
    @Autowired
    RegisteredUserService registeredUserService;


    @PostMapping(value = { "/accounts", "/accounts/"})
    public RegisteredUserDto createAccount(@RequestParam String aUsername, String aPassword, String aEmail) throws IllegalArgumentException {
        RegisteredUser newClient = registeredUserService.createUser(aUsername, aPassword, aEmail);
        return convertToDto(newClient);
    }

    @GetMapping(value = { "/accounts/{ID}", "/accounts/{ID}/"})
    public RegisteredUserDto getAccountDTO(@RequestParam String aEmail) throws Exception{
        RegisteredUser accountRoleDto = registeredUserService.getUserByEmail(aEmail);
        return convertToDto(accountRoleDto);
    }


    private RegisteredUserDto convertToDto(RegisteredUser o) {
        if (o == null) {
            throw new IllegalArgumentException("Scheduled Class does not exist.");
        }
        if (o.getAccountRole() != null)
            return new RegisteredUserDto(o.getUsername(), o.getPassword(), o.getEmail(), o.getAccountRole());
        return new RegisteredUserDto(o.getUsername(), o.getPassword(), o.getEmail());

    }

}
