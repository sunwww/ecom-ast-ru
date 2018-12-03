package ru.ecom.expert2.form.financeplan;

import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.expert2.domain.financeplan.VmpFinancePlan;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@EntityFormPersistance(clazz = VmpFinancePlan.class)
@Comment("Финансовый план по ВМП")
@WebTrail(comment = "Финансовый план по ВМП", nameProperties = "id", view = "entityView-e2_vmpFinancePlan.do")
@EntityFormSecurityPrefix("/Policy/E2")
public class VmpFinancePlanForm extends HospitalFinancePlanForm {
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

    /** КСГ */
    @Comment("КСГ")
    public Long getKsg() {return theKsg;}
    public void setKsg(Long aKsg) {theKsg = aKsg;}
    /** КСГ */
    private Long theKsg ;

    /** Подтип коек */
    @Comment("Подтип коек")
    public Long getBedSubType() {return theBedSubType;}
    public void setBedSubType(Long aBedSubType) {theBedSubType = aBedSubType;}
    /** Подтип коек */
    private Long theBedSubType ;

    /** Метод ВМП */
    @Comment("Метод ВМП")
    @Persist
    public Long getMethod() {return theMethod;}
    public void setMethod(Long aMethod) {theMethod = aMethod;}
    /** Метод ВМП */
    private Long theMethod ;
}
