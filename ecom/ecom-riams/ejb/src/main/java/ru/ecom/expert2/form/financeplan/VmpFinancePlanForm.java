package ru.ecom.expert2.form.financeplan;

import lombok.Setter;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.expert2.domain.financeplan.VmpFinancePlan;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

@EntityForm
@EntityFormPersistance(clazz = VmpFinancePlan.class)
@Comment("Финансовый план по ВМП")
@WebTrail(comment = "Финансовый план по ВМП", nameProperties = "id", view = "entityView-e2_vmpFinancePlan.do")
@EntityFormSecurityPrefix("/Policy/E2")
@Setter
public class VmpFinancePlanForm extends FinancePlanForm {

    /** Подтип коек */
    @Comment("Подтип коек")
    public Long getBedSubType() {return bedSubType;}
    /** Подтип коек */
    private Long bedSubType ;

    /** Метод ВМП */
    @Comment("Метод ВМП")
    @Persist
    public Long getMethod() {return method;}
    /** Метод ВМП */
    private Long method ;

    /** Профиль коек V020 */
    @Comment("Профиль коек V020")
    @Persist
    public Long getBedProfile() {return bedProfile;}
    /** Профиль коек V020 */
    private Long bedProfile ;

}
