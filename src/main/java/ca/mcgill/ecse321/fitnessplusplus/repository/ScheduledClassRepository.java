package ca.mcgill.ecse321.fitnessplusplus.repository;

import ca.mcgill.ecse321.fitnessplusplus.model.ScheduledClass;
import org.springframework.data.repository.CrudRepository;

public interface ScheduledClassRepository extends CrudRepository<ScheduledClass, Integer> {
    public ScheduledClass findScheduledClassByscheduledClassId(int id);
}
