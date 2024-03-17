package ca.mcgill.ecse321.fitnessplusplus.controller;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.fitnessplusplus.dto.ScheduledClassDto;
import ca.mcgill.ecse321.fitnessplusplus.model.ScheduledClass;
import ca.mcgill.ecse321.fitnessplusplus.service.ScheduledClassService;

@CrossOrigin(origins = "*")
@RestController
public class ScheduledClassController {
    @Autowired
    ScheduledClassService scheduledClassService;

    @GetMapping(value = { "/scheduled-classes", "/scheduled-classes/" })
    public List<ScheduledClassDto> getAllSCheduledClasses() {
        List<ScheduledClassDto> dto = new ArrayList<>();
        for (ScheduledClass scheduledClass : scheduledClassService.getAllScheduledClass()) {
            dto.add(convertToDto(scheduledClass));
        }
        return dto;
    }

    @PostMapping(value = { "/scheduled-class", "/scheduled-class/" })
    public ScheduledClassDto createScheduledClass(
            @RequestParam Time aStartTime,
            @RequestParam Time aEndTime,
            @RequestParam Date aDay, @RequestParam Integer aOfferedClassId, @RequestParam Integer anInstructorId)
            throws Exception {
        return convertToDto(scheduledClassService.createScheduledClass(aStartTime, aEndTime, aDay, aOfferedClassId, anInstructorId));
    }

    @GetMapping("/greetings/hello")
    public String hello() {
        return "Hello there!";
    }

    private ScheduledClassDto convertToDto(ScheduledClass o) {
        if (o == null) {
            throw new IllegalArgumentException("Scheduled Class does not exist.");
        }
        return new ScheduledClassDto(o.getScheduledClassId(), o.getStartTime(), o.getEndTime(), o.getDate());

    }

}
