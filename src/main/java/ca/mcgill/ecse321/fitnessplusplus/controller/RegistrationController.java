package ca.mcgill.ecse321.fitnessplusplus.controller;

import ca.mcgill.ecse321.fitnessplusplus.dto.ClientDto;
import ca.mcgill.ecse321.fitnessplusplus.dto.RegistrationDto;
import ca.mcgill.ecse321.fitnessplusplus.dto.ScheduleClassRequestDTO;
import ca.mcgill.ecse321.fitnessplusplus.dto.ScheduleClassResponseDTO;
import ca.mcgill.ecse321.fitnessplusplus.model.Client;
import ca.mcgill.ecse321.fitnessplusplus.model.Instructor;
import ca.mcgill.ecse321.fitnessplusplus.model.Registration;
import ca.mcgill.ecse321.fitnessplusplus.model.ScheduledClass;
import ca.mcgill.ecse321.fitnessplusplus.service.RegisteredUserService;
import ca.mcgill.ecse321.fitnessplusplus.service.RegistrationService;
import ca.mcgill.ecse321.fitnessplusplus.service.ScheduledClassService;
import org.springframework.beans.factory.annotation.Autowired;
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
     * @param date Date of the registration
     * @param clientDto Client Data Transfer Object
     * @param scheduledClassDto ScheduledClass Data Transfer Object
     *
     * @return RegistrationDto
     * @throws Exception
     *
     * @author Yonatan Bensimon
     */
    @PostMapping(value = {"/register", "/register/"})
    public RegistrationDto createRegistration(@RequestParam(name = "date") Date date,
                                              @RequestParam(name = "client") ClientDto clientDto, ScheduleClassResponseDTO scheduledClassDto) throws Exception {
        ScheduledClass scheduledClass = scheduledClassService.getScheduledClass(scheduledClassDto.getScheduledClassID());
        Client client = registeredUserService.getClientById(clientDto.getRoleId());

        Registration r = registrationService.createRegistration(date,client.getRoleId() ,scheduledClass.getScheduledClassId());
        return convertToDto(r);
    }



    /**
     * API Get Endpoint to get all registrations
     * @return List<RegistrationDto>
     *
     * @author Yonatan Bensimon
     */
    @GetMapping(value={"/registrations", "/registrations/" })
    public List<RegistrationDto> getAllRegistrations() {
        List<RegistrationDto> dto = new ArrayList<>();
        for (Registration r: registrationService.getAllRegistrations()) {
            dto.add(convertToDto(r));
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
    public RegistrationDto getRegistrationByID(@PathVariable("id") int registrationID) {
        return convertToDto(registrationService.getRegistrationByID(registrationID));
    }

    /**
     * API Post Endpoint to remove a registration by its ID
     *
     * @param registrationID
     *
     * @author Yonatan Bensimon
     */
    @DeleteMapping(value={"registrations/{id}", "/registrations/{id}"})
    public void removeRegistration(@PathVariable("id") int registrationID) throws Exception {
        try {
            registrationService.removeRegistration(registrationID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Private method in order to convert a registration object to a registration data transfer object
     *
     * @param r Registration object to be converted
     * @return RegistrationDto
     *
     * @author Yonatan Bensimon
     */
    private RegistrationDto convertToDto(Registration r) {
         if (r == null) {
             throw new IllegalArgumentException("There is no such registration");
         }
         ClientDto clientDto = new ClientDto(r.getClient().getRoleId());
         int schID = r.getScheduledClass().getScheduledClassId();
         Time schStart = r.getScheduledClass().getStartTime();
         Time schEnd = r.getScheduledClass().getEndTime();
         Date schDate = r.getScheduledClass().getDate();

         return new RegistrationDto(r.getDateOfRegistration(), clientDto, schID, r.getRegistrationId());
    }
}
