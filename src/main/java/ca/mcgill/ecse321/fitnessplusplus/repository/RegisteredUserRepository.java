package ca.mcgill.ecse321.fitnessplusplus.repository;

import ca.mcgill.ecse321.fitnessplusplus.model.RegisteredUser;
import org.springframework.data.repository.CrudRepository;

public interface RegisteredUserRepository extends CrudRepository<RegisteredUser, Integer> {
    public RegisteredUser findRegisteredUserByUserId(int id);
}
