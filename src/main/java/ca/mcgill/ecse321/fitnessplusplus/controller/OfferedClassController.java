package ca.mcgill.ecse321.fitnessplusplus.controller;

import ca.mcgill.ecse321.fitnessplusplus.dto.OfferedClassDto;
import ca.mcgill.ecse321.fitnessplusplus.dto.ScheduledClassDto;
import ca.mcgill.ecse321.fitnessplusplus.model.OfferedClass;
import ca.mcgill.ecse321.fitnessplusplus.model.ScheduledClass;
import ca.mcgill.ecse321.fitnessplusplus.service.OfferedClassService;
import ca.mcgill.ecse321.fitnessplusplus.service.ScheduledClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.sql.Time;

public class OfferedClassController {
    @Autowired
    OfferedClassService offeredClassService;

    @PostMapping(value = { "/offered-class", "/offered-class/" })
    public OfferedClassDto requestClass(@RequestParam String aRequestedClassType, @RequestParam String aRequestedClassDescription)
            throws Exception {

        return convertToDto(offeredClassService.requestClass(aRequestedClassType, aRequestedClassDescription));
    }

    private OfferedClassDto convertToDto(OfferedClass o) {
        if (o == null) {
            throw new IllegalArgumentException("Scheduled Class does not exist.");
        }
        return new OfferedClassDto(o.getClassType(), o.getDescription());

    }


}
