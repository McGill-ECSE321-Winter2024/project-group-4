package ca.mcgill.ecse321.fitnessplusplus.dto;

import java.sql.Date;
import java.time.LocalDate;

public class RegistrationRequestDto {

    private LocalDate dateOfRegistration;
    private int clientId;
    private int scheduledClassID;

    @SuppressWarnings("unused")
    public RegistrationRequestDto() {
    }

    public RegistrationRequestDto(LocalDate aDateOfRegistration, int clientId, int scheduledClassID, int registrationId) {
        this.dateOfRegistration = aDateOfRegistration;
        this.clientId = clientId;
        this.scheduledClassID = scheduledClassID;
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
}
