package ca.mcgill.ecse321.fitnessplusplus.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
     * @param dto
     * @return ScheduleClassDto
     * @throws Exception
     * 
     * @author Isbat-ul Islam
     */
    @PostMapping(value = { "/scheduled-class", "/scheduled-class/" })
    public ScheduleClassResponseDTO createScheduledClass(@RequestBody ScheduleClassRequestDTO dto)
            throws Exception {
        ScheduledClass scheduledClass = scheduledClassService.createScheduledClass(dto.getStartTime(), dto.getEndTime(),
                dto.getDate(), dto.getOfferedClassID(), dto.getInstructorID());
        return new ScheduleClassResponseDTO(scheduledClass.getScheduledClassId(), scheduledClass.getStartTime(),
                scheduledClass.getEndTime(), scheduledClass.getDate(), scheduledClass.getOfferedClass().getId(),
                scheduledClass.getInstructor().getRoleId());
    }

    @GetMapping(value = { "/scheduled-classes/{id}", "/scheduled-classes/{id}/" })
    public ScheduleClassResponseDTO getScheduledClass(@PathVariable("id") int scheduledClassId) throws Exception {
        return convertToDto(scheduledClassService.getScheduledClass(scheduledClassId));
    }

    @DeleteMapping(value = { "/scheduled-classes/{id}", "/scheduled-classes/{id}/" })
    public void cancelScheduledClass(@PathVariable("id") int scheduledClassId) throws Exception {
        scheduledClassService.deleteScheduledClass(scheduledClassService.getScheduledClass(scheduledClassId));
    }

    @GetMapping(value = { "/scheduled-class/{date}", "/scheduled-class/{date}/" })
    public List<ScheduleClassResponseDTO> getWeeklyClassSchedule(@PathVariable("date") String aDay) throws Exception {
        List<ScheduleClassResponseDTO> dto = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate convertedDay = LocalDate.parse(aDay, formatter);
        for (ScheduledClass scheduledClass : scheduledClassService.getWeeklyScheduledClasses(convertedDay)) {
            dto.add(convertToDto(scheduledClass));
        }
        return dto;
    }

    private ScheduleClassResponseDTO convertToDto(ScheduledClass o) {
        if (o == null) {
            throw new IllegalArgumentException("Scheduled Class does not exist.");
        }
        return new ScheduleClassResponseDTO(o.getScheduledClassId(), o.getStartTime(), o.getEndTime(), o.getDate(),
                o.getOfferedClass().getId(), o.getInstructor().getRoleId());

    }

}
