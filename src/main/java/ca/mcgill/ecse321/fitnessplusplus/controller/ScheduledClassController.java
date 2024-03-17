package ca.mcgill.ecse321.fitnessplusplus.controller;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import ca.mcgill.ecse321.fitnessplusplus.dto.ScheduledClassDto;
import ca.mcgill.ecse321.fitnessplusplus.model.ScheduledClass;
import ca.mcgill.ecse321.fitnessplusplus.service.ScheduledClassService;

@CrossOrigin(origins = "*")
@RestController
public class ScheduledClassController {
    @Autowired
    ScheduledClassService scheduledClassService;

    /**
     * API endpoint to get a list of all scheduled classes
     * 
     * @return List<ScheduledClassDto>
     * 
     * @author Isbat-ul Islam
     */
    @GetMapping(value = { "/scheduled-classes", "/scheduled-classes/" })
    public List<ScheduledClassDto> getAllSCheduledClasses() {
        List<ScheduledClassDto> dto = new ArrayList<>();
        for (ScheduledClass scheduledClass : scheduledClassService.getAllScheduledClass()) {
            dto.add(convertToDto(scheduledClass));
        }
        return dto;
    }

    /**
     * Creates a scheduled class.
     * 
     * @param aStartTime
     * @param aEndTime
     * @param aDay
     * @param aOfferedClassId
     * @param anInstructorId
     * @return ScheduleClassDto
     * @throws Exception
     * 
     * @author Isbat-ul Islam
     */
    @PostMapping(value = { "/scheduled-class", "/scheduled-class/" })
    public ScheduledClassDto createScheduledClass(
            @RequestParam Time aStartTime,
            @RequestParam Time aEndTime,
            @RequestParam Date aDay, @RequestParam Integer aOfferedClassId, @RequestParam Integer anInstructorId)
            throws Exception {
        return convertToDto(scheduledClassService.createScheduledClass(aStartTime, aEndTime, aDay, aOfferedClassId, anInstructorId));
    }


    @GetMapping (value = {"/scheduled-classes/{id}", "/scheduled-classes/{id}/"})
    public ScheduledClassDto getScheduledClass(@PathVariable("id") int scheduledClassId) {
        return convertToDto(scheduledClassService.getScheduledClass(scheduledClassId));
    }

    @DeleteMapping (value = {"/scheduled-classes/{id}", "/scheduled-classes/{id}/"})
    public void cancelScheduledClass(@PathVariable("id") int scheduledClassId, @RequestParam Integer anInstructorId) {
        scheduledClassService.cancelScheduledClass(scheduledClassId, anInstructorId);
    }

    private ScheduledClassDto convertToDto(ScheduledClass o) {
        if (o == null) {
            throw new IllegalArgumentException("Scheduled Class does not exist.");
        }
        return new ScheduledClassDto(o.getScheduledClassId(), o.getStartTime(), o.getEndTime(), o.getDate());

    }



}
