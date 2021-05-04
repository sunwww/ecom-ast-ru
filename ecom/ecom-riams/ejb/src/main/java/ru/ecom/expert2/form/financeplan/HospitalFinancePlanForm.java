package ru.ecom.expert2.form.financeplan;

import lombok.Setter;
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
@Setter
public class HospitalFinancePlanForm extends FinancePlanForm {

    /** Отделение */
    @Comment("Отделение")
    @Persist @Required
    public Long getDepartment() {return department;}
    /** Отделение */
    private Long department ;

    /** КСГ */
    @Comment("КСГ")
    @Persist
    public Long getKsg() {return ksg;}
    /** КСГ */
    private Long ksg ;

    /** Группа КСГ */
    @Comment("Группа КСГ")
    @Persist
    public Long getKsgGroup() {return ksgGroup;}
    /** Группа КСГ */
    private Long ksgGroup ;

    /** Подтип коек */
    @Comment("Подтип коек")
    @Persist @Required
    public Long getBedSubType() {return bedSubType;}
    /** Подтип коек */
    private Long bedSubType ;

    /** Профиль коек V020 */
    @Comment("Профиль коек V020")
    @Persist
    public Long getBedProfile() {return bedProfile;}
    /** Профиль коек V020 */
    private Long bedProfile ;


}
