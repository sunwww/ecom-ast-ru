package ru.ecom.expert2.form.financeplan;

import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.expert2.domain.financeplan.PolyclinicFinancePlan;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

@EntityForm
@EntityFormPersistance(clazz = PolyclinicFinancePlan.class)
@Comment("Финансовый план в поликлинике")
@WebTrail(comment = "Финансовый план в поликлинике", nameProperties = "id", view = "entityView-e2_polFinancePlan.do")
@EntityFormSecurityPrefix("/Policy/E2")
public class PolyclinicFinancePlanForm extends FinancePlanForm {

}
