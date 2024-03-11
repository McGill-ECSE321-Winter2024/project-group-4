package ca.mcgill.ecse321.fitnessplusplus.dto;

import java.sql.Date;

public class RegistrationDto {

    private Date dateOfRegistration;
    private ClientDto client;
    private ScheduledClassDto scheduledClass;

    public RegistrationDto(Date aDateOfRegistration, ClientDto aClient, ScheduledClassDto aScheduledClass) {
        this.dateOfRegistration = aDateOfRegistration;
        this.client = aClient;
        this.scheduledClass = aScheduledClass;
    }

    public Date getDateOfRegistration() {
        return dateOfRegistration;
    }

    public ClientDto getClient() {
        return client;
    }

    public ScheduledClassDto getScheduledClass() {
        return scheduledClass;
    }
}
