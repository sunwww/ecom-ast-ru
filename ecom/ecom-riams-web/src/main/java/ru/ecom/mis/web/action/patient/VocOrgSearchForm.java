package ru.ecom.mis.web.action.patient;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.forms.validator.BaseValidatorForm;

public class VocOrgSearchForm extends BaseValidatorForm{
    /** Старый код ФОНДА */
    @Comment("Старый код ФОНДА")
    public String getOldFondNumber() { return theOldFondNumber ; }
    public void setOldFondNumber(String aOldFondNumber) { theOldFondNumber = aOldFondNumber ; }

    /** Код ФОНДА */
    @Comment("Новый код ФОНДА")
    public String getFondNumber() { return theFondNumber ; }
    public void setFondNumber(String aFondNumber) { theFondNumber = aFondNumber ; }

    /** Название */
    @Comment("Наименование")
    public String getName() { return theName ; }
    public void setName(String aName) { theName = aName ; }

    /** Название */
    private String theName ;
    /** Код ФОНДА */
    private String theFondNumber ;
    /** Старый код ФОНДА */
    private String theOldFondNumber ;	

}
