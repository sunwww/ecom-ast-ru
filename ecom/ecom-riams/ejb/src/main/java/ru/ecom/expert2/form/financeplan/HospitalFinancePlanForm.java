package ru.ecom.expert2.form.financeplan;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.expert2.domain.financeplan.HospitalFinancePlan;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@EntityFormPersistance(clazz = HospitalFinancePlan.class)
@Comment("Финансовый план в стационаре")
@WebTrail(comment = "Финансовый план в стационаре", nameProperties = "id", view = "entityView-e2_stacFinancePlan.do")
@EntityFormSecurityPrefix("/Policy/E2")
public class HospitalFinancePlanForm extends IdEntityForm {
    /** Профиль медицинской помощи */
    @Comment("Профиль медицинской помощи")
    @Persist @Required
    public Long getProfile() {return theProfile;}
    public void setProfile(Long aProfile) {theProfile = aProfile;}
    /** Профиль медицинской помощи */
    private Long theProfile ;

    /** Отделение */
    @Comment("Отделение")
    @Persist @Required
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

    /** КСГ */
    @Comment("КСГ")
    @Persist @Required
    public Long getKsg() {return theKsg;}
    public void setKsg(Long aKsg) {theKsg = aKsg;}
    /** КСГ */
    private Long theKsg ;

    /** Подтип коек */
    @Comment("Подтип коек")
    @Persist @Required
    public Long getBedSubType() {return theBedSubType;}
    public void setBedSubType(Long aBedSubType) {theBedSubType = aBedSubType;}
    /** Подтип коек */
    private Long theBedSubType ;

    /** Цена случая */
    @Comment("Цена случая")
    @Persist
    public String getCost() {return theCost;}
    public void setCost(String aCost) {theCost = aCost;}
    /** Цена случая */
    private String theCost ;


}
