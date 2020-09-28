package ru.ecom.expert2.form.financeplan;

import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.expert2.domain.financeplan.HospitalFinancePlan;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@EntityFormPersistance(clazz = HospitalFinancePlan.class)
@Comment("Финансовый план в стационаре")
@WebTrail(comment = "Финансовый план в стационаре", nameProperties = "id", view = "entityView-e2_stacFinancePlan.do")
@EntityFormSecurityPrefix("/Policy/E2")
public class HospitalFinancePlanForm extends FinancePlanForm {

    /** Отделение */
    @Comment("Отделение")
    @Persist @Required
    public Long getDepartment() {return theDepartment;}
    public void setDepartment(Long aDepartment) {theDepartment = aDepartment;}
    /** Отделение */
    private Long theDepartment ;

    /** КСГ */
    @Comment("КСГ")
    @Persist
    public Long getKsg() {return theKsg;}
    public void setKsg(Long aKsg) {theKsg = aKsg;}
    /** КСГ */
    private Long theKsg ;

    /** Группа КСГ */
    @Comment("Группа КСГ")
    @Persist
    public Long getKsgGroup() {return theKsgGroup;}
    public void setKsgGroup(Long aKsgGroup) {theKsgGroup = aKsgGroup;}
    /** Группа КСГ */
    private Long theKsgGroup ;

    /** Подтип коек */
    @Comment("Подтип коек")
    @Persist @Required
    public Long getBedSubType() {return theBedSubType;}
    public void setBedSubType(Long aBedSubType) {theBedSubType = aBedSubType;}
    /** Подтип коек */
    private Long theBedSubType ;

    /** Профиль коек V020 */
    @Comment("Профиль коек V020")
    @Persist
    public Long getBedProfile() {return theBedProfile;}
    public void setBedProfile(Long aBedProfile) {theBedProfile = aBedProfile;}
    /** Профиль коек V020 */
    private Long theBedProfile ;


}
