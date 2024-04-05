package ca.mcgill.ecse321.fitnessplusplus.controller;

import ca.mcgill.ecse321.fitnessplusplus.dto.OfferedClassRequestDto;
import ca.mcgill.ecse321.fitnessplusplus.dto.OfferedClassResponseDto;
import ca.mcgill.ecse321.fitnessplusplus.model.OfferedClass;
import ca.mcgill.ecse321.fitnessplusplus.service.OfferedClassService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/** RESTful API Controller for OfferedClass */
@CrossOrigin(origins = "*")
@RestController
public class OfferedClassController {
  @Autowired OfferedClassService offeredClassService;

  /**
   * API POST: Creates an offeredClass and returns a ResponseDto
   *
   * @param dto Request Body DTO
   * @return OfferedClassResponseDto Response DTO
   * @author Neil Joe George
   */
  @PostMapping(value = {"/offer-class", "/offer-class/"})
  @ResponseStatus(HttpStatus.CREATED)
  public OfferedClassResponseDto requestClass(@RequestBody OfferedClassRequestDto dto)
      throws Exception {
    OfferedClass offeredClass =
        offeredClassService.requestClass(dto.getClassType(), dto.getDescription());
    return new OfferedClassResponseDto(
        offeredClass.getClassType(), offeredClass.getDescription(), offeredClass.getId());
  }

  /**
   * API GET: Return list of all OfferedClasses
   *
   * @return List of OfferedClass response DTO
   * @author Neil Joe George, Mathieu Pestel
   */
  @GetMapping(value = {"/offered-classes", "/offered-classes/"})
  @ResponseStatus(HttpStatus.OK)
  public List<OfferedClassResponseDto> getAllRegisteredUser() throws Exception {
    List<OfferedClassResponseDto> dto = new ArrayList<>();
    for (OfferedClass c : offeredClassService.getAllOfferedClasses()) {
      dto.add(new OfferedClassResponseDto(c.getClassType(), c.getDescription(), c.getId()));
    }
    return dto;
  }

  /**
   * API GET: Returns an offeredClass with passed ID.
   *
   * @param id ID of offeredClass you want to find
   * @return An OfferedClass response DTO with corresponding ID
   * @author Mathieu Pestel
   */
  @GetMapping(value = {"/offered-class/{id}", "/offered-class/{id}/"})
  @ResponseStatus(HttpStatus.OK)
  public OfferedClassResponseDto getOfferedClassByID(@PathVariable int id) {
    OfferedClass offeredClass = offeredClassService.getOfferedClassById(id);

    return new OfferedClassResponseDto(
        offeredClass.getClassType(), offeredClass.getDescription(), offeredClass.getId());
  }

  /**
   * API DELETE: Deletes offeredClass with passed ID.
   *
   * @param offeredClassId Id of offeredClass you wish to delete.
   * @return An OfferedClass response DTO of deleted offered class.
   * @author Mathieu Pestel
   */
  @DeleteMapping(value = {"/offered-classes/{id}", "/offered-classes/{id}/"})
  @ResponseStatus(HttpStatus.OK)
  public OfferedClassResponseDto removeOfferedClass(@PathVariable("id") int offeredClassId)
      throws Exception {

    OfferedClass offeredClass = offeredClassService.removeOfferedClass(offeredClassId);

    return new OfferedClassResponseDto(
        offeredClass.getClassType(), offeredClass.getDescription(), offeredClass.getId());
  }
}
