package ca.mcgill.ecse321.fitnessplusplus.dto;

public class OfferedClassDto {
    private String classType;
    private String description;
    private int offeredClassId;

    public OfferedClassDto(String classType, String description, int offeredClassId) {
        this.classType = classType;
        this.description = description;
        this.offeredClassId = offeredClassId;
    }

    public String getClassType() {
        return classType;
    }

    public String getDescription() {
        return description;
    }
    public int getOfferedClassId() {return offeredClassId;}
}
