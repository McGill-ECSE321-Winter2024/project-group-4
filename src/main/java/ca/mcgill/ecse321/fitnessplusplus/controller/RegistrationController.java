package ca.mcgill.ecse321.fitnessplusplus.controller;

import ca.mcgill.ecse321.fitnessplusplus.dto.ClientDto;
import ca.mcgill.ecse321.fitnessplusplus.dto.RegistrationDto;
import ca.mcgill.ecse321.fitnessplusplus.dto.ScheduledClassDto;
import ca.mcgill.ecse321.fitnessplusplus.model.Registration;
import ca.mcgill.ecse321.fitnessplusplus.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class RegistrationController {
    @Autowired
    private RegistrationService registrationService;

    @GetMapping(value={"/registrations", "/registrations/" })
    public List<RegistrationDto> getAllRegistrations() {
        List<RegistrationDto> dto = new ArrayList<>();
        for (Registration r: registrationService.getAllRegistrations()) {
            dto.add(convertToDto(r));
        }
        return dto;
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
