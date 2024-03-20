package ca.mcgill.ecse321.fitnessplusplus.controller;

import ca.mcgill.ecse321.fitnessplusplus.dto.AccountRoleDto;
import ca.mcgill.ecse321.fitnessplusplus.model.Client;
import ca.mcgill.ecse321.fitnessplusplus.service.AccountService;
import ca.mcgill.ecse321.fitnessplusplus.model.AccountRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(value = { "/accounts/{ID}", "/accounts/{ID}/"})
    public AccountRoleDto getAccountDTO(@RequestParam int ID) throws Exception{
        Client accountRoleDto = accountService.getClientById(ID);
        return convertToDto(accountRoleDto);
    }


    private AccountRoleDto convertToDto(AccountRole o) {
        if (o == null) {
            throw new IllegalArgumentException("Scheduled Class does not exist.");
        }
        return new AccountRoleDto(o.getRoleId());

    }

}
