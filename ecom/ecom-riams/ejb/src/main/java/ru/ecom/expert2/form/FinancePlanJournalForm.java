package ru.ecom.expert2.form;

import ru.nuzmsh.forms.validator.BaseValidatorForm;

public class FinancePlanJournalForm extends BaseValidatorForm {

    /** Профиль мед. помощи */
    public Long getProfile() {return theProfile;}
    public void setProfile(Long aProfile) {theProfile = aProfile;}
    /** Профиль мед. помощи */
    private Long theProfile ;

    /** Отделение */
    public Long getDepartment() {return theDepartment;}
    public void setDepartment(Long aDepartment) {theDepartment = aDepartment;}
    /** Отделение */
    private Long theDepartment ;

    /** КСГ */
    public Long getKsg() {return theKsg;}
    public void setKsg(Long aKsg) {theKsg = aKsg;}
    /** КСГ */
    private Long theKsg ;

    /** Тип коек */
    public Long getBedSubType() {return theBedSubType;}
    public void setBedSubType(Long aBedSubType) {theBedSubType = aBedSubType;}
    /** Тип коек */
    private Long theBedSubType ;

}
