package ca.mcgill.ecse321.fitnessplusplus.controller;

import ca.mcgill.ecse321.fitnessplusplus.dto.OfferedClassRequestDto;
import ca.mcgill.ecse321.fitnessplusplus.dto.OfferedClassResponseDto;
import ca.mcgill.ecse321.fitnessplusplus.dto.RegisteredUserRequestDto;
import ca.mcgill.ecse321.fitnessplusplus.dto.RegisteredUserResponseDto;
import ca.mcgill.ecse321.fitnessplusplus.model.OfferedClass;
import ca.mcgill.ecse321.fitnessplusplus.model.RegisteredUser;
import ca.mcgill.ecse321.fitnessplusplus.service.OfferedClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@CrossOrigin(origins = "*")
@RestController
public class OfferedClassController {
    @Autowired
    OfferedClassService offeredClassService;

    @PostMapping(value = { "/offer-class", "/offer-class/" })
    @ResponseStatus(HttpStatus.CREATED)
    public OfferedClassResponseDto requestClass(@RequestBody OfferedClassRequestDto dto)
            throws Exception {
        OfferedClass offeredClass = offeredClassService.requestClass(dto.getClassType(), dto.getDescription());
    return new OfferedClassResponseDto(
        offeredClass.getClassType(), offeredClass.getDescription(), offeredClass.getId());
    }

    @GetMapping(value={"/offered-classes", "/offered-classes/" })
    @ResponseStatus(HttpStatus.OK)
    public List<OfferedClassResponseDto> getAllRegisteredUser() throws Exception{
        List<OfferedClassResponseDto> dto = new ArrayList<>();
        for (OfferedClass c: offeredClassService.getAllOfferedClasses()) {
            dto.add(new OfferedClassResponseDto(c.getClassType(), c.getDescription(), c.getId()));
        }
        return dto;
    }

    @GetMapping(value={"/offered-class", "/offered-class/"})
    public OfferedClassResponseDto getOfferedClassByID(@RequestParam(name = "id") int offeredClassId) {
        return convertToDto(offeredClassService.getOfferedClassById(offeredClassId));
    }

    @DeleteMapping(value = { "/offered-classes/{id}", "/offered-classes/{id}/" })
    public void removeOfferedClass(@PathVariable("id") int offeredClassId) {
        try {
            offeredClassService.removeOfferedClass(offeredClassId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private OfferedClassResponseDto convertToDto(OfferedClass o) {
        if (o == null) {
            throw new IllegalArgumentException("Offered class does not exist.");
        }
        return new OfferedClassResponseDto(o.getClassType(), o.getDescription(), o.getId());
    }


}
