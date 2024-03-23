package ca.mcgill.ecse321.fitnessplusplus.controller;

import ca.mcgill.ecse321.fitnessplusplus.dto.*;
import ca.mcgill.ecse321.fitnessplusplus.model.*;
import ca.mcgill.ecse321.fitnessplusplus.service.RegisteredUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class RegisteredUserController {
    @Autowired
    RegisteredUserService registeredUserService;

    @PostMapping(value = { "/register-user", "/register-user/" })
    @ResponseStatus(HttpStatus.CREATED)
    public RegisteredUserResponseDto createRegisteredUser(@RequestBody RegisteredUserRequestDto dto)
            throws Exception {
        RegisteredUser registeredUser = registeredUserService.createUser(dto.getUsername(), dto.getPassword(),
                dto.getEmail());
        return new RegisteredUserResponseDto(registeredUser.getUserId(), registeredUser.getUsername(),
                registeredUser.getPassword(), registeredUser.getEmail(), registeredUser.getAccountRole().getRoleId());
    }

    /* 
    @GetMapping(value = { "/registered-users", "/registered-users/" })
    public List<RegisteredUserDto> getAllRegisteredUsers() {
        List<RegisteredUserDto> dto = new ArrayList<>();
        for (RegisteredUser r : registeredUserService.getAllRegisteredUser()) {
            dto.add(convertToDto(r));
        }
        return dto;
    }

    @GetMapping(value = { "/registered-user", "/registered-user/" })
    public RegisteredUserDto getRegisteredUserByID(@RequestParam(name = "id") int registeredUserId) {
        return convertToDto(registeredUserService.getRegisteredUserById(registeredUserId));
    }

    @PostMapping(value = { "/promote", "/promote/" })
    public RegisteredUserDto promoteRegisteredUser(@RequestParam(name = "id") int registeredUserId) throws Exception {
        RegisteredUser registeredUser = registeredUserService.getRegisteredUserById(registeredUserId);
        return convertToDto(registeredUserService.promoteUser(registeredUser));
    }

    private RegisteredUserDto convertToDto(RegisteredUser o) {
        if (o == null) {
            throw new IllegalArgumentException("Registered user does not exist.");
        }

        RegisteredUserDto registeredUserDto = new RegisteredUserDto(o.getUsername(), o.getPassword(), o.getEmail());
        if (o.getAccountRole().getClass() == Client.class) {
            registeredUserDto.setAccountRole(new ClientDto(o.getAccountRole().getRoleId()));
        }
        if (o.getAccountRole().getClass() == Instructor.class) {
            registeredUserDto.setAccountRole(new InstructorDto(o.getAccountRole().getRoleId()));
        }
        if (o.getAccountRole().getClass() == Owner.class) {
            registeredUserDto.setAccountRole(new OwnerDto(o.getAccountRole().getRoleId()));
        }
        return registeredUserDto;

    }
*/
}
