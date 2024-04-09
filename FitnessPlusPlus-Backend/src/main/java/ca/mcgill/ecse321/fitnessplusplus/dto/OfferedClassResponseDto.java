package ca.mcgill.ecse321.fitnessplusplus.dto;

public class OfferedClassResponseDto {
  private String classType;
  private String description;
  private int offeredClassId;
  private boolean approved;

  @SuppressWarnings("unused")
  public OfferedClassResponseDto() {}

  public OfferedClassResponseDto(String classType, String description, int offeredClassId, boolean approved) {
    this.classType = classType;
    this.description = description;
    this.offeredClassId = offeredClassId;
    this.approved = approved;
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

  public boolean isApproved() {
    return approved;
  }

  public void setApproved(boolean approved) {
    this.approved = approved;
  }
}
