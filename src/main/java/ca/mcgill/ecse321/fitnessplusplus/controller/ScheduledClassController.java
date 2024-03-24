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
import org.springframework.web.bind.annotation.*;

import ca.mcgill.ecse321.fitnessplusplus.dto.ScheduleClassRequestDTO;
import ca.mcgill.ecse321.fitnessplusplus.dto.ScheduleClassResponseDTO;
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
    public List<ScheduleClassResponseDTO> getAllSCheduledClasses() {
        List<ScheduleClassResponseDTO> dto = new ArrayList<>();
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
    public ScheduleClassResponseDTO createScheduledClass(@RequestParam ScheduleClassRequestDTO scheduleClassRequestDTO)
            throws Exception {
        return convertToDto(scheduledClassService.createScheduledClass(scheduleClassRequestDTO.getStartTime(),
                scheduleClassRequestDTO.getEndTime(),
                scheduleClassRequestDTO.getDate(),
                scheduleClassRequestDTO.getOfferedClassID(),
                scheduleClassRequestDTO.getInstructorID()));
    }

    @GetMapping(value = { "/scheduled-classes/{id}", "/scheduled-classes/{id}/" })
    public ScheduleClassResponseDTO getScheduledClass(@PathVariable("id") int scheduledClassId) {
        return convertToDto(scheduledClassService.getScheduledClass(scheduledClassId));
    }

    @DeleteMapping(value = { "/scheduled-classes/{id}", "/scheduled-classes/{id}/" })
    public void cancelScheduledClass(@PathVariable("id") int scheduledClassId) throws Exception {
        scheduledClassService.deleteScheduledClass(scheduledClassService.getScheduledClass(scheduledClassId));
    }

    @GetMapping(value = { "/scheduled-class", "/scheduled-class/" })
    public List<ScheduleClassResponseDTO> getWeeklyClassSchedule(@RequestParam Date aday) {
        List<ScheduleClassResponseDTO> dto = new ArrayList<>();
        for (ScheduledClass scheduledClass : scheduledClassService.getWeeklyScheduledClasses(aday)) {
            dto.add(convertToDto(scheduledClass));
        }
        return dto;
    }

    private ScheduleClassResponseDTO convertToDto(ScheduledClass o) {
        if (o == null) {
            throw new IllegalArgumentException("Scheduled Class does not exist.");
        }
        return new
                ScheduleClassResponseDTO(o.getScheduledClassId(), o.getStartTime(), o.getEndTime(), o.getDate(),
                o.getInstructor().getRoleId(), o.getScheduledClassId());

    }

}
