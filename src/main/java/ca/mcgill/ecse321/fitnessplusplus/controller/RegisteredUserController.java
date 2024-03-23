package ca.mcgill.ecse321.fitnessplusplus.controller;

import ca.mcgill.ecse321.fitnessplusplus.dto.*;
import ca.mcgill.ecse321.fitnessplusplus.model.*;
import ca.mcgill.ecse321.fitnessplusplus.service.RegisteredUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@CrossOrigin(origins = "*")
@RestController
public class RegisteredUserController {
    @Autowired
    RegisteredUserService registeredUserService;

    @PostMapping(value = { "/register-user", "/register-user/" })
    public RegisteredUserDto createRegisteredUser(
            @RequestParam String aUsername, @RequestParam String aPassword, @RequestParam String aEmail)
            throws Exception {
        return convertToDto(registeredUserService.createUser(aUsername, aPassword, aEmail));
    }

    @GetMapping(value={"/registered-users", "/registered-users/" })
    public List<RegisteredUserDto> getAllRegisteredUsers() {
        List<RegisteredUserDto> dto = new ArrayList<>();
        for (RegisteredUser r: registeredUserService.getAllRegisteredUser()) {
            dto.add(convertToDto(r));
        }
        return dto;
    }

    @GetMapping(value={"/registered-user", "/registered-user/"})
    public RegisteredUserDto getRegisteredUserByID(@RequestParam(name = "id") int registeredUserId) {
        return convertToDto(registeredUserService.getRegisteredUserById(registeredUserId));
    }

    @PostMapping(value = {"/promote", "/promote/"})
    public RegisteredUserDto promoteRegisteredUser(@RequestParam(name = "id") int registeredUserId) throws Exception {
        RegisteredUser registeredUser = registeredUserService.getRegisteredUserById(registeredUserId);
        return convertToDto(registeredUserService.promoteUser(registeredUser));
    }

    private RegisteredUserDto convertToDto(RegisteredUser o) {
        if (o == null) {
            throw new IllegalArgumentException("Registered user does not exist.");
        }

        RegisteredUserDto registeredUserDto = new RegisteredUserDto(o.getUsername(), o.getPassword(), o.getEmail(), o.getUserId());
        if (o.getAccountRole().getClass() == Client.class) {registeredUserDto.setAccountRole(new ClientDto(o.getAccountRole().getRoleId()));}
        if (o.getAccountRole().getClass() == Instructor.class) {registeredUserDto.setAccountRole(new InstructorDto(o.getAccountRole().getRoleId()));}
        if (o.getAccountRole().getClass() == Owner.class) {registeredUserDto.setAccountRole(new OwnerDto(o.getAccountRole().getRoleId()));}
        return registeredUserDto;

    }

}
