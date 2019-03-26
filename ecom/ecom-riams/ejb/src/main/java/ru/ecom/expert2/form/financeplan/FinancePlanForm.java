package ru.ecom.expert2.form.financeplan;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.expert2.domain.financeplan.FinancePlan;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@EntityFormPersistance(clazz = FinancePlan.class)
@Comment("Финансовый план в стационаре")
@WebTrail(comment = "Финансовый план ", nameProperties = "id", view = "entityView-e2_stacFinancePlan.do")
@EntityFormSecurityPrefix("/Policy/E2")
public class FinancePlanForm extends IdEntityForm {

    /** Профиль медицинской помощи */
    @Comment("Профиль медицинской помощи")
    @Persist @Required
    public Long getProfile() {return theProfile;}
    public void setProfile(Long aProfile) {theProfile = aProfile;}
    /** Профиль медицинской помощи */
    private Long theProfile ;

    /** Отделение */
    @Comment("Отделение")
    @Persist
    public Long getDepartment() {return theDepartment;}
    public void setDepartment(Long aDepartment) {theDepartment = aDepartment;}
    /** Отделение */
    private Long theDepartment ;

    /** Количество случаев */
    @Comment("Количество случаев")
    @Persist @Required
    public Long getCount() {return theCount;}
    public void setCount(Long aCount) {theCount = aCount;}
    /** Количество случаев */
    private Long theCount ;

    /** Дата начала периода */
    @Comment("Дата начала периода")
    @Persist @Required
    @DateString @DoDateString
    public String getStartDate() {return theStartDate;}
    public void setStartDate(String aStartDate) {theStartDate = aStartDate;}
    /** Дата начала периода */
    private String theStartDate ;

    /** Дата окончания периода */
    @Comment("Дата окончания периода")
    @Persist @Required
    @DateString @DoDateString
    public String getFinishDate() {return theFinishDate;}
    public void setFinishDate(String aFinishDate) {theFinishDate = aFinishDate;}
    /** Дата окончания периода */
    private String theFinishDate ;

    /** Цена случая */
    @Comment("Цена случая")
    @Persist
    public String getCost() {return theCost;}
    public void setCost(String aCost) {theCost = aCost;}
    /** Цена случая */
    private String theCost ;

    /** Вид случай */
    @Comment("Вид случай")
    @Persist @Required
    public Long getVidSluch() {return theVidSluch;}
    public void setVidSluch(Long aVidSluch) {theVidSluch = aVidSluch;}
    /** Вид случай */
    private Long theVidSluch ;


}
