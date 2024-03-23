package ca.mcgill.ecse321.fitnessplusplus.repository;

import ca.mcgill.ecse321.fitnessplusplus.model.OfferedClass;
import org.springframework.data.repository.CrudRepository;

public interface OfferedClassRepository extends CrudRepository<OfferedClass, String> {
  public OfferedClass findOfferedClassByOfferedClassType(String classType);
}
