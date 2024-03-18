package ca.mcgill.ecse321.fitnessplusplus.controller;

import ca.mcgill.ecse321.fitnessplusplus.dto.AccountRoleDto;
import ca.mcgill.ecse321.fitnessplusplus.model.Client;
import ca.mcgill.ecse321.fitnessplusplus.model.ScheduledClass;
import ca.mcgill.ecse321.fitnessplusplus.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ca.mcgill.ecse321.fitnessplusplus.dto.ClientDto;
import ca.mcgill.ecse321.fitnessplusplus.dto.RegistrationDto;
import ca.mcgill.ecse321.fitnessplusplus.dto.ScheduledClassDto;
import ca.mcgill.ecse321.fitnessplusplus.model.AccountRole;
import ca.mcgill.ecse321.fitnessplusplus.model.Registration;
import ca.mcgill.ecse321.fitnessplusplus.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class AccountController {
    @Autowired
    AccountService accountService;

    @PostMapping(value = { "/accounts", "/accounts/"})
    public AccountRoleDto createAccount(@RequestParam int ID) throws IllegalArgumentException {
        Client newClient = accountService.createClient(ID);
        return convertToDto(newClient);
    }

    private AccountRoleDto convertToDto(AccountRole o) {
        if (o == null) {
            throw new IllegalArgumentException("Scheduled Class does not exist.");
        }
        return new AccountRoleDto(o.getRoleId());

    }

}
