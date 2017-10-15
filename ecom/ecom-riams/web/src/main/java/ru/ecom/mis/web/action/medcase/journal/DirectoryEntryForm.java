package ru.ecom.mis.web.action.medcase.journal;


import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.forms.validator.BaseValidatorForm;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;


public class DirectoryEntryForm extends BaseValidatorForm{
    
    /** Здание */
    @Comment("Здание")
    public Long getBuilding() {	return theBuilding; }
    public void setBuilding(Long aBuilding) { theBuilding = aBuilding; }
    private Long theBuilding;
    
    /** Этаж */
    @Comment("Этаж")
    public Long getBuildingLevel() { return theBuildingLevel;}
    public void setBuildingLevel(Long aBuildingLevel) {	theBuildingLevel = aBuildingLevel;}
    private Long theBuildingLevel;
    
    /** Отделение */
    @Comment("Отделение")
    public Long getDepartment() { return theDepartment; }
    public void setDepartment(Long aDepartment) { theDepartment = aDepartment; }
    private Long theDepartment;

    /** Персона */
    @Comment("Персона")
    public Long getPerson() { return thePerson;}
    public void setPerson(Long aPerson) { thePerson = aPerson; }
    private Long thePerson;
    
    /** Комментарий */
    @Comment("Комментарий")
    public String getComment() { return theComment; }
    public void setComment(String aComment) { theComment = aComment;}
    private String theComment;
    
    /** Внутренний номер */
    @Comment("Внутренний номер")
    public String getInternalNumber() {	return theInternalNumber; }
    public void setInternalNumber(String aInternalNumber) { theInternalNumber = aInternalNumber;}
    private String theInternalNumber;

}