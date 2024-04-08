package ca.mcgill.ecse321.fitnessplusplus.controller;

import ca.mcgill.ecse321.fitnessplusplus.dto.*;
import ca.mcgill.ecse321.fitnessplusplus.model.*;
import ca.mcgill.ecse321.fitnessplusplus.service.RegisteredUserService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/** RESTful API Controller for RegisteredUser */
@CrossOrigin(origins = "*")
@RestController
public class RegisteredUserController {
  @Autowired RegisteredUserService registeredUserService;

  /**
   * API POST: Register a User
   *
   * @param dto RegisteredUserRequest you wish to register
   * @return RegisteredUser response DTO of registered user
   * @author Neil Joe George
   */
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
        registeredUser.getAccountRole().getRoleId(),
        registeredUser.getAccountRole().getClass().getSimpleName());
  }

  /**
   * API POST: Promote registered user
   *
   * @param dto RegisteredUser you wish to promote
   * @return RegisteredUser response DTO of promoted registered user
   * @author Mathieu Pestel
   */
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
        user.getAccountRole().getRoleId(),
        user.getAccountRole().getClass().getSimpleName());
  }

  @PostMapping(value = {"/login", "/login/"})
  @ResponseStatus(HttpStatus.OK)
  public RegisteredUserResponseDto loginRegisteredUser(@RequestBody RegisteredUserRequestDto dto) {
    RegisteredUser user = registeredUserService.login(dto.getUsername(), dto.getPassword());
    return new RegisteredUserResponseDto(
            user.getUserId(),
            user.getUsername(),
            user.getPassword(),
            user.getEmail(),
            user.getAccountRole().getRoleId(),
            user.getAccountRole().getClass().getSimpleName());
  }

  /**
   * API GET: Get all registered users
   *
   * @return List of all registeredUsers Response DTO
   * @author Mathieu Pestel
   */
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
              r.getAccountRole().getRoleId(),
              r.getAccountRole().getClass().getSimpleName()));
    }
    return dto;
  }

  /**
   * API GET: Get a registered user by ID
   *
   * @param id Id of registered user
   * @return RegisteredUser Response DTO
   * @author Mathieu Pestel
   */
  @GetMapping(value = {"/registered-user/{id}", "/registered-user/{id}/"})
  @ResponseStatus(HttpStatus.OK)
  public RegisteredUserResponseDto getRegisteredUserByID(@PathVariable int id) {
    RegisteredUser registeredUser = registeredUserService.getRegisteredUserById(id);

    return new RegisteredUserResponseDto(
        registeredUser.getUserId(),
        registeredUser.getUsername(),
        registeredUser.getPassword(),
        registeredUser.getEmail(),
        registeredUser.getAccountRole().getRoleId(),
        registeredUser.getAccountRole().getClass().getSimpleName());
  }
}
