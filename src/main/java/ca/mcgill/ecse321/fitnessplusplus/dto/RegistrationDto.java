package ca.mcgill.ecse321.fitnessplusplus.dto;

import java.sql.Date;

public class RegistrationDto {

    private Date dateOfRegistration;
    private ClientDto client;
    private int scheduledClassID;
    private int registrationId;

    public RegistrationDto(Date aDateOfRegistration, ClientDto aClient, int scheduledClassID, int registrationId) {
        this.dateOfRegistration = aDateOfRegistration;
        this.client = aClient;
        this.scheduledClassID = scheduledClassID;
        this.registrationId = registrationId;
    }

    public Date getDateOfRegistration() {
        return dateOfRegistration;
    }

    public ClientDto getClient() {
        return client;
    }

    public int getScheduledClass() {
        return scheduledClassID;
    }
    public int getRegistrationId() {return registrationId;}
}
