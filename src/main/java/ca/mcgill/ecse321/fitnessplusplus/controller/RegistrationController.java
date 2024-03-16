package ca.mcgill.ecse321.fitnessplusplus.controller;

import ca.mcgill.ecse321.fitnessplusplus.dto.ClientDto;
import ca.mcgill.ecse321.fitnessplusplus.dto.RegistrationDto;
import ca.mcgill.ecse321.fitnessplusplus.dto.ScheduledClassDto;
import ca.mcgill.ecse321.fitnessplusplus.model.Client;
import ca.mcgill.ecse321.fitnessplusplus.model.Registration;
import ca.mcgill.ecse321.fitnessplusplus.model.ScheduledClass;
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
    @Autowired
    private ScheduledClassService scheduledClassService;

    /* Waiting for Mathieu's function
    @PostMapping(value = {"/register", "/register/"})
    public RegistrationDto createRegistration(@RequestParam(name = "date") Date date,
                                              @RequestParam(name = "client") ClientDto clientDto,
                                              @RequestParam(name = "scheduledClass") ScheduledClassDto scheduledClassDto) throws Exception {
        ScheduledClass s = scheduledClassService.getScheduledClass(scheduledClassDto.getScheduledClassId());
        Client c = clientClassService.getClient(clientDto.getRoleId());

        Registration r = registrationService.createRegistration(date, c, s);
        return convertToDto(r);
    }

     */

    @GetMapping(value={"/registrations", "/registrations/" })
    public List<RegistrationDto> getAllRegistrations() {
        List<RegistrationDto> dto = new ArrayList<>();
        for (Registration r: registrationService.getAllRegistrations()) {
            dto.add(convertToDto(r));
        }
        return dto;
    }

    @GetMapping(value={"/registrations/{id}", "/registrations/{id}/"})
    public RegistrationDto getRegistrationByID(@PathVariable("id") int registrationID) {
        return convertToDto(registrationService.getRegistrationByID(registrationID));
    }

    @PostMapping(value={"deregister/{id}", "/deregister/{id}"})
    public void removeRegistration(@PathVariable("id") int registrationID) throws Exception {
        registrationService.removeRegistration(registrationID);
    }
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
