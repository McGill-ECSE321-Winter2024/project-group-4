package ca.mcgill.ecse321.fitnessplusplus.repository;
import ca.mcgill.ecse321.fitnessplusplus.model.Owner;
import org.springframework.data.repository.CrudRepository;
public interface OwnerRepository extends CrudRepository<Owner, Integer> {
    public Owner findInstructorByroleId(int roleId);
}
