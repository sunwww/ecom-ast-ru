package ru.ecom.expert2.form.financeplan;

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
public class VmpFinancePlanForm extends FinancePlanForm {

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

    /** Профиль коек V020 */
    @Comment("Профиль коек V020")
    @Persist
    public Long getBedProfile() {return theBedProfile;}
    public void setBedProfile(Long aBedProfile) {theBedProfile = aBedProfile;}
    /** Профиль коек V020 */
    private Long theBedProfile ;

}
