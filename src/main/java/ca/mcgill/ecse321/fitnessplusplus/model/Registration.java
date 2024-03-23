package ca.mcgill.ecse321.fitnessplusplus.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.sql.Date;

@Entity
public class Registration {

  // ------------------------
  // MEMBER VARIABLES
  // ------------------------

  // Registration Attributes
  @Id @GeneratedValue private int registrationId;
  private Date dateOfRegistration;

  // Registration Associations
  @ManyToOne private Client client;
  @ManyToOne private ScheduledClass scheduledClass;

  // ------------------------
  // CONSTRUCTOR
  // ------------------------

  public Registration(Date aDateOfRegistration, Client aClient, ScheduledClass aScheduledClass) {
    dateOfRegistration = aDateOfRegistration;
    if (!setClient(aClient)) {
      throw new RuntimeException(
          "Unable to create Registration due to aClient. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    if (!setScheduledClass(aScheduledClass)) {
      throw new RuntimeException(
          "Unable to create Registration due to aScheduledClass. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  public Registration() {}

  // ------------------------
  // INTERFACE
  // ------------------------

  public boolean setRegistrationId(int aRegistrationId) {
    boolean wasSet = false;
    registrationId = aRegistrationId;
    wasSet = true;
    return wasSet;
  }

  public boolean setDateOfRegistration(Date aDateOfRegistration) {
    boolean wasSet = false;
    dateOfRegistration = aDateOfRegistration;
    wasSet = true;
    return wasSet;
  }

  public int getRegistrationId() {
    return registrationId;
  }

  public Date getDateOfRegistration() {
    return dateOfRegistration;
  }

  /* Code from template association_GetOne */
  public Client getClient() {
    return client;
  }

  /* Code from template association_GetOne */
  public ScheduledClass getScheduledClass() {
    return scheduledClass;
  }

  /* Code from template association_SetUnidirectionalOne */
  public boolean setClient(Client aNewClient) {
    boolean wasSet = false;
    if (aNewClient != null) {
      client = aNewClient;
      wasSet = true;
    }
    return wasSet;
  }

  /* Code from template association_SetUnidirectionalOne */
  public boolean setScheduledClass(ScheduledClass aNewScheduledClass) {
    boolean wasSet = false;
    if (aNewScheduledClass != null) {
      scheduledClass = aNewScheduledClass;
      wasSet = true;
    }
    return wasSet;
  }

  public void delete() {
    client = null;
    scheduledClass = null;
  }

}
