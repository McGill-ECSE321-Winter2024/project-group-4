package ca.mcgill.ecse321.fitnessplusplus.controller;

import ca.mcgill.ecse321.fitnessplusplus.dto.OfferedClassDto;
import ca.mcgill.ecse321.fitnessplusplus.dto.RegisteredUserDto;
import ca.mcgill.ecse321.fitnessplusplus.model.OfferedClass;
import ca.mcgill.ecse321.fitnessplusplus.model.RegisteredUser;
import ca.mcgill.ecse321.fitnessplusplus.service.OfferedClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@CrossOrigin(origins = "*")
@RestController
public class OfferedClassController {
    @Autowired
    OfferedClassService offeredClassService;

    @PostMapping(value = { "/offer-class", "/offer-class/" })
    public OfferedClassDto requestClass(@RequestParam String aRequestedClassType, @RequestParam String aRequestedClassDescription)
            throws Exception {

        return convertToDto(offeredClassService.requestClass(aRequestedClassType, aRequestedClassDescription));
    }

    @GetMapping(value={"/offered-classes", "/offered-classes/" })
    public List<OfferedClassDto> getAllRegisteredUser() {
        List<OfferedClassDto> dto = new ArrayList<>();
        for (OfferedClass c: offeredClassService.getAllOfferedClasses()) {
            dto.add(convertToDto(c));
        }
        return dto;
    }

    @GetMapping(value={"/offered-class", "/offered-class/"})
    public OfferedClassDto getOfferedClassByID(@RequestParam(name = "id") int offeredClassId) {
        return convertToDto(offeredClassService.getOfferedClassById(offeredClassId));
    }

    @DeleteMapping(value = { "/offered-classes/{id}", "/offered-classes/{id}/" })
    public void removeOfferedClass(@PathVariable("id") int offeredClassId) {
        OfferedClass offeredClass = offeredClassService.getOfferedClassById(offeredClassId);
        offeredClassService.removeOfferedClass(offeredClass);
    }

    private OfferedClassDto convertToDto(OfferedClass o) {
        if (o == null) {
            throw new IllegalArgumentException("Offered class does not exist.");
        }
        return new OfferedClassDto(o.getClassType(), o.getDescription(), o.getId());
    }


}
