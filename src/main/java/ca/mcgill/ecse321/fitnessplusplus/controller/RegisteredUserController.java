package ca.mcgill.ecse321.fitnessplusplus.controller;

import ca.mcgill.ecse321.fitnessplusplus.dto.*;
import ca.mcgill.ecse321.fitnessplusplus.model.*;
import ca.mcgill.ecse321.fitnessplusplus.service.RegisteredUserService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
public class RegisteredUserController {
  @Autowired RegisteredUserService registeredUserService;

  @PostMapping(value = {"/register-user", "/register-user/"})
  @ResponseStatus(HttpStatus.CREATED)
  public RegisteredUserResponseDto createRegisteredUser(@RequestBody RegisteredUserRequestDto dto)
      throws Exception {
    RegisteredUser registeredUser =
        registeredUserService.createUser(dto.getUsername(), dto.getPassword(), dto.getEmail());
    return new RegisteredUserResponseDto(
        registeredUser.getUserId(),
        registeredUser.getUsername(),
        registeredUser.getPassword(),
        registeredUser.getEmail(),
        registeredUser.getAccountRole().getRoleId());
  }

  @PostMapping(value = {"/promote", "/promote/"})
  @ResponseStatus(HttpStatus.OK)
  public RegisteredUserResponseDto promoteRegisteredUser(@RequestBody RegisteredUserResponseDto dto)
      throws Exception {
    RegisteredUser user = registeredUserService.promoteUser(dto.getUserId());
    return new RegisteredUserResponseDto(
        user.getUserId(),
        user.getUsername(),
        user.getPassword(),
        user.getEmail(),
        user.getAccountRole().getRoleId());
  }

  @GetMapping(value = {"/registered-users", "/registered-users/"})
  @ResponseStatus(HttpStatus.OK)
  public List<RegisteredUserResponseDto> getAllRegisteredUsers() {
    List<RegisteredUserResponseDto> dto = new ArrayList<>();
    for (RegisteredUser r : registeredUserService.getAllRegisteredUser()) {
      dto.add(
          new RegisteredUserResponseDto(
              r.getUserId(),
              r.getUsername(),
              r.getPassword(),
              r.getEmail(),
              r.getAccountRole().getRoleId()));
    }
    return dto;
  }

  @GetMapping(value = {"/registered-user/{id}", "/registered-user/{id}/"})
  @ResponseStatus(HttpStatus.OK)
  public RegisteredUserResponseDto getRegisteredUserByID(@PathVariable int id) {
    RegisteredUser registeredUser = registeredUserService.getRegisteredUserById(id);

    return new RegisteredUserResponseDto(
        registeredUser.getUserId(),
        registeredUser.getUsername(),
        registeredUser.getPassword(),
        registeredUser.getEmail(),
        registeredUser.getAccountRole().getRoleId());
  }
}
