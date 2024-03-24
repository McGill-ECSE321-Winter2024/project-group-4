package ca.mcgill.ecse321.fitnessplusplus.dto;

import java.sql.Date;

public class RegistrationRequestDto {

    private Date dateOfRegistration;
    private int clientId;
    private int scheduledClassID;

    @SuppressWarnings("unused")
    public RegistrationRequestDto() {
    }

    public RegistrationRequestDto(Date aDateOfRegistration, int clientId, int scheduledClassID, int registrationId) {
        this.dateOfRegistration = aDateOfRegistration;
        this.clientId = clientId;
        this.scheduledClassID = scheduledClassID;
    }

    public Date getDateOfRegistration() {
        return dateOfRegistration;
    }

    public void setDateOfRegistration(Date dateOfRegistration) {
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
