package ca.mcgill.ecse321.fitnessplusplus.repository;

import ca.mcgill.ecse321.fitnessplusplus.model.Client;
import ca.mcgill.ecse321.fitnessplusplus.model.Registration;
import ca.mcgill.ecse321.fitnessplusplus.model.ScheduledClass;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RegistrationRepository extends CrudRepository<Registration, Integer> {
  public Registration findRegistrationByregistrationId(int id);

  public Registration findByClientAndScheduledClass(Client client, ScheduledClass scheduledClass);

  public List<Registration> findAllByClient(Client client);
}
