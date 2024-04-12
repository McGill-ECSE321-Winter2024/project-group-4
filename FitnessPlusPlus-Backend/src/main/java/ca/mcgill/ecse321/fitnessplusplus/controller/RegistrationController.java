package ca.mcgill.ecse321.fitnessplusplus.controller;

import ca.mcgill.ecse321.fitnessplusplus.dto.*;
import ca.mcgill.ecse321.fitnessplusplus.model.Registration;
import ca.mcgill.ecse321.fitnessplusplus.model.ScheduledClass;
import ca.mcgill.ecse321.fitnessplusplus.service.RegisteredUserService;
import ca.mcgill.ecse321.fitnessplusplus.service.RegistrationService;
import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse321.fitnessplusplus.service.ScheduledClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/** RESTful API Controller for Registration */
@CrossOrigin(origins = "*")
@RestController
public class RegistrationController {
  @Autowired private RegistrationService registrationService;
  @Autowired private ScheduledClassService scheduledClassService;
  @Autowired private RegisteredUserService registeredUserService;


  /**
   * API Post Endpoint to create a registration
   *
   * @param dto Request body of Registration to create
   * @return RegistrationDto
   * @throws Exception
   * @author Yonatan Bensimon
   */
  @PostMapping(value = {"/register", "/register/"})
  @ResponseStatus(HttpStatus.CREATED)
  public RegistrationResponseDto createRegistration(@RequestBody RegistrationRequestDto dto)
      throws Exception {


    ScheduledClass scheduledClass = scheduledClassService.getScheduledClass(dto.getScheduledClassID());

    String instructorName =
            registeredUserService
                    .getRegisteredUserByRoleId(scheduledClass.getInstructor().getRoleId())
                    .getUsername();

    ScheduleClassResponseDTO scheduledClassDto = new ScheduleClassResponseDTO(
            scheduledClass.getScheduledClassId(),
            scheduledClass.getStartTime(),
            scheduledClass.getEndTime(),
            scheduledClass.getDate(),
            scheduledClass.getOfferedClass().getId(),
            scheduledClass.getInstructor().getRoleId(),
            scheduledClass.getOfferedClass().getClassType(),
            scheduledClass.getOfferedClass().getDescription(),
            instructorName
    );

    Registration r =
        registrationService.createRegistration(
            dto.getDateOfRegistration(), dto.getClientId(), dto.getScheduledClassID());
    return new RegistrationResponseDto(
        r.getDateOfRegistration(),
        r.getClient().getRoleId(),
        r.getScheduledClass().getScheduledClassId(),
        r.getRegistrationId(),
            scheduledClassDto);
  }

  /**
   * API Get Endpoint to get all registrations
   *
   * @return List<RegistrationDto>
   * @author Yonatan Bensimon
   */
  @GetMapping(value = {"/registrations", "/registrations/"})
  @ResponseStatus(HttpStatus.OK)
  public List<RegistrationResponseDto> getAllRegistrations() {
    List<RegistrationResponseDto> dto = new ArrayList<>();

    for (Registration r : registrationService.getAllRegistrations()) {

      ScheduledClass scheduledClass = scheduledClassService.getScheduledClass(r.getScheduledClass().getScheduledClassId());

      String instructorName =
              registeredUserService
                      .getRegisteredUserByRoleId(scheduledClass.getInstructor().getRoleId())
                      .getUsername();

      ScheduleClassResponseDTO scheduledClassDto = new ScheduleClassResponseDTO(
              scheduledClass.getScheduledClassId(),
              scheduledClass.getStartTime(),
              scheduledClass.getEndTime(),
              scheduledClass.getDate(),
              scheduledClass.getOfferedClass().getId(),
              scheduledClass.getInstructor().getRoleId(),
              scheduledClass.getOfferedClass().getClassType(),
              scheduledClass.getOfferedClass().getDescription(),
              instructorName
      );

      dto.add(
          new RegistrationResponseDto(
              r.getDateOfRegistration(),
              r.getClient().getRoleId(),
              r.getScheduledClass().getScheduledClassId(),
              r.getRegistrationId(),
                  scheduledClassDto));
    }
    return dto;
  }

  /**
   * API Get Endpoint to get a registration by its ID
   *
   * @param registrationID
   * @return RegistrationDto
   * @author Yonatan Bensimon
   */
  @GetMapping(value = {"/registrations/{id}", "/registrations/{id}/"})
  @ResponseStatus(HttpStatus.OK)
  public RegistrationResponseDto getRegistrationByID(@PathVariable("id") int registrationID) {
    Registration registration = registrationService.getRegistrationByID(registrationID);

    ScheduledClass scheduledClass = scheduledClassService.getScheduledClass(registration.getScheduledClass().getScheduledClassId());

    String instructorName =
            registeredUserService
                    .getRegisteredUserByRoleId(scheduledClass.getInstructor().getRoleId())
                    .getUsername();

    ScheduleClassResponseDTO scheduledClassDto = new ScheduleClassResponseDTO(
            scheduledClass.getScheduledClassId(),
            scheduledClass.getStartTime(),
            scheduledClass.getEndTime(),
            scheduledClass.getDate(),
            scheduledClass.getOfferedClass().getId(),
            scheduledClass.getInstructor().getRoleId(),
            scheduledClass.getOfferedClass().getClassType(),
            scheduledClass.getOfferedClass().getDescription(),
            instructorName
    );

    return new RegistrationResponseDto(
        registration.getDateOfRegistration(),
        registration.getClient().getRoleId(),
        registration.getScheduledClass().getScheduledClassId(),
        registration.getRegistrationId(),
            scheduledClassDto);
  }

  @GetMapping(value = {"/client-registrations/{id}", "/client-registrations/{id}/"})
  @ResponseStatus(HttpStatus.OK)
  public List<RegistrationResponseDto> getRegistrationByClient(@PathVariable("id") int clientID) {
    List<RegistrationResponseDto> dto = new ArrayList<>();
    List<Registration> registrations = registrationService.getAllRegistrationByClientId(clientID);
    for (Registration r : registrations) {

      ScheduledClass scheduledClass = scheduledClassService.getScheduledClass(r.getScheduledClass().getScheduledClassId());

      String instructorName =
              registeredUserService
                      .getRegisteredUserByRoleId(scheduledClass.getInstructor().getRoleId())
                      .getUsername();

      ScheduleClassResponseDTO scheduledClassDto = new ScheduleClassResponseDTO(
              scheduledClass.getScheduledClassId(),
              scheduledClass.getStartTime(),
              scheduledClass.getEndTime(),
              scheduledClass.getDate(),
              scheduledClass.getOfferedClass().getId(),
              scheduledClass.getInstructor().getRoleId(),
              scheduledClass.getOfferedClass().getClassType(),
              scheduledClass.getOfferedClass().getDescription(),
              instructorName
      );

      dto.add(
              new RegistrationResponseDto(
                      r.getDateOfRegistration(),
                      r.getClient().getRoleId(),
                      r.getScheduledClass().getScheduledClassId(),
                      r.getRegistrationId(),
                      scheduledClassDto));
    }

    return dto;
  }

  /**
   * API Post Endpoint to remove a registration by its ID
   *
   * @param registrationID
   * @author Yonatan Bensimon
   */
  @DeleteMapping(value = {"registrations/{id}", "/registrations/{id}"})
  public RegistrationResponseDto removeRegistration(@PathVariable("id") int registrationID)
      throws Exception {
    Registration registration = registrationService.removeRegistration(registrationID);

    ScheduledClass scheduledClass = scheduledClassService.getScheduledClass(registration.getScheduledClass().getScheduledClassId());

    String instructorName =
            registeredUserService
                    .getRegisteredUserByRoleId(scheduledClass.getInstructor().getRoleId())
                    .getUsername();

    ScheduleClassResponseDTO scheduledClassDto = new ScheduleClassResponseDTO(
            scheduledClass.getScheduledClassId(),
            scheduledClass.getStartTime(),
            scheduledClass.getEndTime(),
            scheduledClass.getDate(),
            scheduledClass.getOfferedClass().getId(),
            scheduledClass.getInstructor().getRoleId(),
            scheduledClass.getOfferedClass().getClassType(),
            scheduledClass.getOfferedClass().getDescription(),
            instructorName
    );

    return new RegistrationResponseDto(
        registration.getDateOfRegistration(),
        registration.getClient().getRoleId(),
        registration.getScheduledClass().getScheduledClassId(),
        registration.getRegistrationId(),
            scheduledClassDto);
  }
}
