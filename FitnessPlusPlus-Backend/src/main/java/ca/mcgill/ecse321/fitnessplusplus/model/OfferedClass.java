package ca.mcgill.ecse321.fitnessplusplus.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class OfferedClass {

  // ------------------------
  // MEMBER VARIABLES
  // ------------------------

  // OfferedClass Attributes
  @Id @GeneratedValue private int offeredClassId;
  private String classType;
  private String description;
  private boolean approval;

  // ------------------------
  // CONSTRUCTOR
  // ------------------------

  public OfferedClass(String aClassType, String aDescription) {
    classType = aClassType;
    description = aDescription;
    approval = false;
  }

  public OfferedClass() {}

  // ------------------------
  // INTERFACE
  // ------------------------

  public boolean setOfferedClassId(int anOfferedClassId) {
    boolean wasSet = false;
    offeredClassId = anOfferedClassId;
    wasSet = true;
    return wasSet;
  }

  public boolean setClassType(String aClassType) {
    boolean wasSet = false;
    classType = aClassType;
    wasSet = true;
    return wasSet;
  }

  public boolean setDescription(String aDescription) {
    boolean wasSet = false;
    description = aDescription;
    wasSet = true;
    return wasSet;
  }

  public boolean setApproval(boolean aApproval) {
    boolean wasSet = false;
    approval = aApproval;
    wasSet = true;
    return wasSet;
  }

  public String getClassType() {
    return this.classType;
  }

  public String getDescription() {
    return this.description;
  }

  public int getId() {
    return this.offeredClassId;
  }

  public boolean getApproval() {return this.approval;}

  public void delete() {}
}
