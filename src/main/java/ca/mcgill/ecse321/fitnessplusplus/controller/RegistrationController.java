package ca.mcgill.ecse321.fitnessplusplus.controller;

import ca.mcgill.ecse321.fitnessplusplus.dto.ClientDto;
import ca.mcgill.ecse321.fitnessplusplus.dto.RegistrationDto;
import ca.mcgill.ecse321.fitnessplusplus.dto.ScheduledClassDto;
import ca.mcgill.ecse321.fitnessplusplus.model.Client;
import ca.mcgill.ecse321.fitnessplusplus.model.Registration;
import ca.mcgill.ecse321.fitnessplusplus.model.ScheduledClass;
import ca.mcgill.ecse321.fitnessplusplus.service.AccountService;
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
    private AccountService accountService;


    /**
     * API Post Endpoint to create a registration
     *
     * @param date Date of the registration
     * @param clientID Client ID
     * @param scheduledClassID ScheduledClass ID
     *
     * @return RegistrationDto
     * @throws Exception
     *
     * @author Yonatan Bensimon
     */
    @PostMapping(value = {"/register", "/register/"})
    public RegistrationDto createRegistration(@RequestParam(name = "date") Date date,
                                              @RequestParam(name = "clientID") int clientID,
                                              @RequestParam(name = "scheduledClassID") int scheduledClassID) throws Exception {
        ScheduledClass s = scheduledClassService.getScheduledClass(scheduledClassID);
        Client c = accountService.getClientById(clientID);

        Registration r = registrationService.createRegistration(date, c, s);
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
        registrationService.removeRegistration(registrationID);
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

         ScheduledClassDto scheduledClassDto = new ScheduledClassDto(schID, schStart, schEnd, schDate);
         return new RegistrationDto(r.getDateOfRegistration(), clientDto, scheduledClassDto);
    }
}
