package ca.mcgill.ecse321.fitnessplusplus.dto;

import java.time.LocalDate;

public class RegistrationResponseDto {

  private LocalDate dateOfRegistration;
  private int clientId;
  private int scheduledClassID;
  private int registrationId;

  @SuppressWarnings("unused")
  public RegistrationResponseDto() {}

  public RegistrationResponseDto(
      LocalDate aDateOfRegistration, int clientId, int scheduledClassID, int registrationId) {
    this.dateOfRegistration = aDateOfRegistration;
    this.clientId = clientId;
    this.scheduledClassID = scheduledClassID;
    this.registrationId = registrationId;
  }

  public LocalDate getDateOfRegistration() {
    return dateOfRegistration;
  }

  public void setDateOfRegistration(LocalDate dateOfRegistration) {
    this.dateOfRegistration = dateOfRegistration;
  }

  public int getClientId() {
    return clientId;
  }

  public void setClientId(int clientId) {
    this.clientId = clientId;
  }

  public int getScheduledClassID() {
    return scheduledClassID;
  }

  public void setScheduledClassID(int scheduledClassID) {
    this.scheduledClassID = scheduledClassID;
  }

  public int getRegistrationId() {
    return registrationId;
  }

  public void setRegistrationId(int registrationId) {
    this.registrationId = registrationId;
  }
}
