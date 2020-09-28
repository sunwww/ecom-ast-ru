package ru.ecom.expert2.form;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;
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

    /** Группа КСГ */
    public Long getKsgGroup() {return theKsgGroup;}
    public void setKsgGroup(Long aKsgGroup) {theKsgGroup = aKsgGroup;}
    /** Группа КСГ */
    private Long theKsgGroup ;

    /** Тип коек */
    public Long getBedSubType() {return theBedSubType;}
    public void setBedSubType(Long aBedSubType) {theBedSubType = aBedSubType;}
    /** Тип коек */
    private Long theBedSubType ;

    /** Вид случая */
    public Long getVidSluch() {return theVidSluch;}
    public void setVidSluch(Long aVidSluch) {theVidSluch = aVidSluch;}
    /** Вид случая */
    private Long theVidSluch ;

    @Comment("Профиль коек V020")
    public Long getBedProfile() {return theBedProfile;}
    public void setBedProfile(Long aBedProfile) {theBedProfile = aBedProfile;}
    /** Профиль коек V020 */
    private Long theBedProfile ;


}
