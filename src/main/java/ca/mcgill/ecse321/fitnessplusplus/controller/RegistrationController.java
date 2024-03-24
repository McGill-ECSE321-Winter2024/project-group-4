package ca.mcgill.ecse321.fitnessplusplus.controller;

import ca.mcgill.ecse321.fitnessplusplus.dto.ClientDto;
import ca.mcgill.ecse321.fitnessplusplus.dto.ScheduleClassResponseDTO;
import ca.mcgill.ecse321.fitnessplusplus.dto.*;
import ca.mcgill.ecse321.fitnessplusplus.model.Client;
import ca.mcgill.ecse321.fitnessplusplus.model.Registration;
import ca.mcgill.ecse321.fitnessplusplus.model.ScheduledClass;
import ca.mcgill.ecse321.fitnessplusplus.service.RegisteredUserService;
import ca.mcgill.ecse321.fitnessplusplus.service.RegistrationService;
import ca.mcgill.ecse321.fitnessplusplus.service.ScheduledClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class RegistrationController {
    @Autowired
    private RegistrationService registrationService;
    private ScheduledClassService scheduledClassService;
    private RegisteredUserService registeredUserService;


    /**
     * API Post Endpoint to create a registration
     *
     * @param dto Request body of Registration to create
     *
     * @return RegistrationDto
     * @throws Exception
     *
     * @author Yonatan Bensimon
     */
    @PostMapping(value = {"/register", "/register/"})
    @ResponseStatus(HttpStatus.CREATED)
    public RegistrationResponseDto createRegistration(@RequestBody RegistrationRequestDto dto) throws Exception {
        Registration r = registrationService.createRegistration(dto.getDateOfRegistration(), dto.getClientId(), dto.getScheduledClassID());
    return new RegistrationResponseDto(r.getDateOfRegistration(), r.getClient().getRoleId(), r.getScheduledClass().getScheduledClassId(), r.getRegistrationId());
    }



    /**
     * API Get Endpoint to get all registrations
     * @return List<RegistrationDto>
     *
     * @author Yonatan Bensimon
     */
    @GetMapping(value={"/registrations", "/registrations/" })
    @ResponseStatus(HttpStatus.OK)
    public List<RegistrationResponseDto> getAllRegistrations() {
        List<RegistrationResponseDto> dto = new ArrayList<>();
        for (Registration r: registrationService.getAllRegistrations()) {
            dto.add(new RegistrationResponseDto(r.getDateOfRegistration(), r.getClient().getRoleId(), r.getScheduledClass().getScheduledClassId(), r.getRegistrationId()));
        }
        return dto;
    }

    /**
     * API Get Endpoint to get a registration by its ID
     *
     * @param registrationID
     * @return RegistrationDto
     *
     * @author Yonatan Bensimon
     */
    @GetMapping(value={"/registrations/{id}", "/registrations/{id}/"})
    @ResponseStatus(HttpStatus.OK)
    public RegistrationResponseDto getRegistrationByID(@PathVariable("id") int registrationID) {
        Registration registration = registrationService.getRegistrationByID(registrationID);
        return new RegistrationResponseDto(registration.getDateOfRegistration(), registration.getClient().getRoleId(), registration.getScheduledClass().getScheduledClassId(), registration.getRegistrationId());
    }

    /**
     * API Post Endpoint to remove a registration by its ID
     *
     * @param registrationID
     *
     * @author Yonatan Bensimon
     */
    @DeleteMapping(value={"registrations/{id}", "/registrations/{id}"})
    public RegistrationResponseDto removeRegistration(@PathVariable("id") int registrationID) throws Exception {
        Registration registration = registrationService.removeRegistration(registrationID);

        return new RegistrationResponseDto(registration.getDateOfRegistration(), registration.getClient().getRoleId(), registration.getScheduledClass().getScheduledClassId(), registration.getRegistrationId());
    }

}
