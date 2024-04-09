package ca.mcgill.ecse321.fitnessplusplus.repository;

import ca.mcgill.ecse321.fitnessplusplus.model.OfferedClass;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OfferedClassRepository extends CrudRepository<OfferedClass, String> {
  public OfferedClass findOfferedClassByOfferedClassId(int classId);

  public List<OfferedClass> findOfferedClassesByApproval(boolean approval);
}
