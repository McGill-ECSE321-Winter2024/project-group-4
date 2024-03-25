package ca.mcgill.ecse321.fitnessplusplus.service;

import ca.mcgill.ecse321.fitnessplusplus.model.*;
import ca.mcgill.ecse321.fitnessplusplus.repository.*;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegisteredUserService {

  @Autowired private RegisteredUserRepository registeredUserRepository;

  @Autowired private InstructorRepository instructorRepository;
  @Autowired private ClientRepository clientRepository;
  @Autowired private OwnerRepository ownerRepository;
  @Autowired OfferedClassRepository offeredClassRepo;

  /**
   * Create a RegisteredUser
   *
   * @param aUsername
   * @param aPassword
   * @param aEmail
   * @return created RegisteredUser
   * @author Isbat-ul Islam
   */
  @Transactional
  public RegisteredUser createUser(String aUsername, String aPassword, String aEmail) {
    // Input validation
    if (aUsername == null || aPassword == null || aEmail == null)
      throw new IllegalArgumentException("Illegal arguments");
    if (accountExists(aEmail, aUsername) != null)
      throw new IllegalArgumentException("Account Exists");

    RegisteredUser newUser = new RegisteredUser(aUsername, aPassword, aEmail);
    // When new user is created, set as client.
    newUser.setAccountRole(new Client());
    // Save both inside of AccountRole & RegisteredUser Repositories.
    Client client = (Client) newUser.getAccountRole();
    clientRepository.save(client);
    registeredUserRepository.save(newUser);

    return newUser;
  }

  /**
   * Create a registered User
   *
   * @param aUsername
   * @param aPassword
   * @param aEmail
   * @param aClient
   * @return created RegisteredUser
   * @author Neil Joe George
   */
  @Transactional
  public RegisteredUser createUser(
      String aUsername, String aPassword, String aEmail, Client aClient) {
    // Input validation
    if (aUsername == null || aPassword == null || aEmail == null)
      throw new IllegalArgumentException("Illegal arguments");
    if (accountExists(aEmail, aUsername) != null)
      throw new IllegalArgumentException("Account Exists");
    RegisteredUser newUser = new RegisteredUser(aUsername, aPassword, aEmail);
    newUser.setAccountRole(aClient);
    // When new user is created, set as client.
    clientRepository.save(aClient);
    registeredUserRepository.save(newUser);

    return newUser;
  }

  /**
   * Promote a given user
   *
   * @param id
   * @return promoted RegisteredUser
   * @author Mathieu Pestel
   */
  @Transactional
  public RegisteredUser promoteUser(int id) {
    RegisteredUser registeredUser = registeredUserRepository.findRegisteredUserByUserId(id);

    if (registeredUser == null) {
      throw new IllegalArgumentException("Cannot promote a user that does not exist");
    }

    if (registeredUser.getAccountRole().getClass() == Client.class) {
      // Delete client account role -> change to instructor
      clientRepository.delete((Client) registeredUser.getAccountRole());
      registeredUser.setAccountRole(new Instructor());
      // Re-save (Update) again (double check if we need to delete then save, or happens
      // automatically)
      registeredUserRepository.save(registeredUser);
      instructorRepository.save((Instructor) registeredUser.getAccountRole());

    } else if (registeredUser.getAccountRole().getClass() == Instructor.class) {
      instructorRepository.delete((Instructor) registeredUser.getAccountRole());
      registeredUser.setAccountRole(new Owner());

      registeredUserRepository.save(registeredUser);
      ownerRepository.save((Owner) registeredUser.getAccountRole());

    } else if (registeredUser.getAccountRole().getClass() == Owner.class) {
      throw new IllegalArgumentException("Cannot promote a owner");

    } else {
      // If for some reason the account doesn't have a role
      registeredUser.setAccountRole(new Client());
      registeredUserRepository.save(registeredUser);
      clientRepository.save((Client) registeredUser.getAccountRole());
    }

    return registeredUser;
  }

  /**
   * Return all registered Users
   *
   * @return List<RegisteredUser>
   * @author Mathieu Pestel
   */
  @Transactional
  public List<RegisteredUser> getAllRegisteredUser() {
    List<RegisteredUser> list = new ArrayList<RegisteredUser>();

    for (RegisteredUser r : registeredUserRepository.findAll()) {
      list.add(r);
    }

    return list;
  }

  /**
   * Return a certain user
   *
   * @param id
   * @return RegisteredUser
   * @author Mathieu Pestel
   */
  @Transactional
  public RegisteredUser getRegisteredUserById(int id) {
    RegisteredUser r = registeredUserRepository.findRegisteredUserByUserId(id);
    if (r == null) {
      throw new IllegalArgumentException("RegisteredUser with id " + id + " not found.");
    }
    return r;
  }

  /**
   * Return a certain user
   *
   * @param aEmail
   * @return RegisteredUser
   * @author Neil Joe George
   */
  @Transactional
  public RegisteredUser getUserByEmail(String aEmail) {
    for (RegisteredUser user : registeredUserRepository.findAll()) {
      if (user.getEmail().equals(aEmail)) {
        return user;
      }
    }
    return null;
  }

  /**
   * Checks if an account exists
   *
   * @param aEmail
   * @param aUsername
   * @return RegisteredUser
   * @author Neil Joe George
   */
  @Transactional
  public RegisteredUser accountExists(String aEmail, String aUsername) {
    for (RegisteredUser user : registeredUserRepository.findAll()) {
      if (user.getEmail().equals(aEmail) || user.getUsername().equals(aUsername)) {
        return user;
      }
    }

    return null;
  }

  @Transactional
  public Client getClientById(int id) {
    return clientRepository.findClientByroleId(id);
  }

  @Transactional
  public Instructor getInstructorById(int id) {
    return instructorRepository.findInstructorByroleId(id);
  }

  @Transactional
  public Owner getOwnerById(int id) {
    return ownerRepository.findInstructorByroleId(id);
  }
}
