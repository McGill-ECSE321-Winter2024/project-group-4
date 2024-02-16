package ca.mcgill.ecse321.fitnessplusplus.repository;
import ca.mcgill.ecse321.fitnessplusplus.model.Instructor;
import org.springframework.data.repository.CrudRepository;
public interface InstructorRepository extends CrudRepository<Instructor, Integer> {
    public Instructor findInstructorById(int id);
}
