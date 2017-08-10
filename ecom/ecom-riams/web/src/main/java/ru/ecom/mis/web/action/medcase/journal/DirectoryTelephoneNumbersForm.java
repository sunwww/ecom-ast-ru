package ru.ecom.mis.web.action.medcase.journal;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.forms.validator.BaseValidatorForm;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;



public class DirectoryTelephoneNumbersForm extends BaseValidatorForm{
    
    /** Корпус */
    @Comment("Корпус")
    public Long getBuilding() {	return theBuilding;    }
    public void setBuilding(Long aBuilding) {	theBuilding = aBuilding;    }
    /** Корпус */
    private Long theBuilding;
    
    /** Этаж */
    @Comment("Этаж")
    public Long getBuildingLevel() {	return theBuildingLevel;    }
    public void setBuildingLevel(Long aBuildingLevel) {	theBuildingLevel = aBuildingLevel;    }
    /** Этаж */
    private Long theBuildingLevel;
    
    /** ФИО */
    @Comment("ФИО")
    public String getLastname() {	return theLastname;    }
    public void setLastname(String aLastname) {	theLastname = aLastname;    }
    /** ФИО */
    private String theLastname;
    
    /** Дата с */
    @Comment("Дата с") @DateString @DoDateString
    public String getBeginDate() {return theBeginDate;}
    public void setBeginDate(String aBeginDate) {theBeginDate = aBeginDate;}
    private String theBeginDate;
    
    /** Дата по */
    @Comment("Дата по") @DateString @DoDateString
    public String getEndDate() {return theEndDate;}
    public void setEndDate(String aEndDate) {theEndDate = aEndDate;}
    private String theEndDate;
    
}