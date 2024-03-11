package ca.mcgill.ecse321.fitnessplusplus.dto;

public class OfferedClassDto {
    private String classType;
    private String description;

    public OfferedClassDto(String classType, String description) {
        this.classType = classType;
        this.description = description;
    }

    public String getClassType() {
        return classType;
    }

    public String getDescription() {
        return description;
    }
}
