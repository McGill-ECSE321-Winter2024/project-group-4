package ca.mcgill.ecse321.fitnessplusplus.dto;

public class OfferedClassResponseDto {
  private String classType;
  private String description;
  private int offeredClassId;

  @SuppressWarnings("unused")
  public OfferedClassResponseDto() {}

  public OfferedClassResponseDto(String classType, String description, int offeredClassId) {
    this.classType = classType;
    this.description = description;
    this.offeredClassId = offeredClassId;
  }

  public String getClassType() {
    return classType;
  }

  public void setClassType(String classType) {
    this.classType = classType;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public int getOfferedClassId() {
    return offeredClassId;
  }

  public void setOfferedClassId(int offeredClassId) {
    this.offeredClassId = offeredClassId;
  }
}
