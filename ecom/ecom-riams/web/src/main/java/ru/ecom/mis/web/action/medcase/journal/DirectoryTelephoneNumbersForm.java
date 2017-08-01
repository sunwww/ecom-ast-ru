package ru.ecom.mis.web.action.medcase.journal;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.forms.validator.BaseValidatorForm;



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
    
}