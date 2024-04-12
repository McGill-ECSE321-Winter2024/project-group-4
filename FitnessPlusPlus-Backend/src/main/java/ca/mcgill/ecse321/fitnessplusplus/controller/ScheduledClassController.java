package ca.mcgill.ecse321.fitnessplusplus.controller;

import ca.mcgill.ecse321.fitnessplusplus.dto.ScheduleClassRequestDTO;
import ca.mcgill.ecse321.fitnessplusplus.dto.ScheduleClassResponseDTO;
import ca.mcgill.ecse321.fitnessplusplus.model.ScheduledClass;
import ca.mcgill.ecse321.fitnessplusplus.service.OfferedClassService;
import ca.mcgill.ecse321.fitnessplusplus.service.ScheduledClassService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/** RESTful API Controller for ScheduledClass */
@CrossOrigin(origins = "*")
@RestController
public class ScheduledClassController {
  @Autowired ScheduledClassService scheduledClassService;
  @Autowired OfferedClassService offeredClassService;

  /**
   * API GET: endpoint to get a list of all scheduled classes
   *
   * @return List<ScheduledClassDto>
   * @author Isbat-ul Islam
   */
  @GetMapping(value = {"/scheduled-classes", "/scheduled-classes/"})
  public List<ScheduleClassResponseDTO> getAllSCheduledClasses() {
    List<ScheduleClassResponseDTO> dto = new ArrayList<>();
    for (ScheduledClass scheduledClass : scheduledClassService.getAllScheduledClass()) {
      dto.add(convertToDto(scheduledClass));
    }
    return dto;
  }

  /**
   * API POST: Creates a scheduled class.
   *
   * @param dto
   * @return ScheduleClassDto
   * @throws Exception
   * @author Isbat-ul Islam
   */
  @PostMapping(value = {"/scheduled-class", "/scheduled-class/"})
  public ScheduleClassResponseDTO createScheduledClass(@RequestBody ScheduleClassRequestDTO dto)
      throws Exception {
    ScheduledClass scheduledClass =
        scheduledClassService.createScheduledClass(
            dto.getStartTime(),
            dto.getEndTime(),
            dto.getDate(),
            dto.getOfferedClassID(),
            dto.getInstructorID());
    return new ScheduleClassResponseDTO(
            scheduledClass.getScheduledClassId(),
            scheduledClass.getStartTime(),
            scheduledClass.getEndTime(),
            scheduledClass.getDate(),
            scheduledClass.getOfferedClass().getId(),
            scheduledClass.getInstructor().getRoleId(),
            scheduledClass.getOfferedClass().getClassType(),
            scheduledClass.getOfferedClass().getDescription());
  }

  /**
   * API GET: returns a scheduled class by ID.
   *
   * @param scheduledClassId
   * @return ScheduleClassResponseDTO
   * @throws Exception
   * @author Yasmine Drissi
   */
  @GetMapping(value = {"/scheduled-classes/{id}", "/scheduled-classes/{id}/"})
  public ScheduleClassResponseDTO getScheduledClass(@PathVariable("id") int scheduledClassId)
      throws Exception {
    return convertToDto(scheduledClassService.getScheduledClass(scheduledClassId));
  }

  /**
   * API DELETE: Delete scheduled class by ID.
   *
   * @param scheduledClassId
   * @throws Exception
   * @author Yasmine Drissi
   */
  @DeleteMapping(value = {"/scheduled-classes/{id}", "/scheduled-classes/{id}/"})
  public void cancelScheduledClass(@PathVariable("id") int scheduledClassId) throws Exception {
    scheduledClassService.deleteScheduledClass(
        scheduledClassService.getScheduledClass(scheduledClassId));
  }

  /**
   * API GET: return a list of all classes in the current week
   *
   * @return
   * @throws Exception
   * @author Yasmine Drissi
   */
  @GetMapping(value = {"/week-class", "/week-class/"})
  public List<ScheduleClassResponseDTO> getWeeklyClassSchedule() throws Exception {
    List<ScheduleClassResponseDTO> dto = new ArrayList<>();
    for (ScheduledClass scheduledClass :
        scheduledClassService.getWeeklyScheduledClasses(LocalDate.now())) {
      dto.add(convertToDto(scheduledClass));
    }
    return dto;
  }

  /**
   * Converts ScheduledClass to scheduledClass response DTO
   *
   * @param o
   * @return
   * @author Isbat-ul Islam
   */
  private ScheduleClassResponseDTO convertToDto(ScheduledClass o) {
    if (o == null) {
      throw new IllegalArgumentException("Scheduled Class does not exist.");
    }
    return new ScheduleClassResponseDTO(
        o.getScheduledClassId(),
        o.getStartTime(),
        o.getEndTime(),
        o.getDate(),
        o.getOfferedClass().getId(),
        o.getInstructor().getRoleId(),
        o.getOfferedClass().getDescription(),
        o.getOfferedClass().getClassType());
  }
}
