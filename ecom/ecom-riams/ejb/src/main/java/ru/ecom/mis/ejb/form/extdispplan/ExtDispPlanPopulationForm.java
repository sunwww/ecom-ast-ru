package ru.ecom.mis.ejb.form.extdispplan;

import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.extdispplan.ExtDispPlanPopulation;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

/**
 * План доп. диспансеризации для населения
  */
@EntityForm
@EntityFormPersistance(clazz = ExtDispPlanPopulation.class)
@Comment("План дополнительной диспансеризации")
@WebTrail(comment = "План дополнительной диспансеризации", nameProperties= "id"
        , view="entityView-extDispPlan_plan.do")
@EntityFormSecurityPrefix("/Policy/Mis/ExtDisp/Card")
@Setter
public class ExtDispPlanPopulationForm extends IdEntityForm{

    /** Год плана*/
    @Comment("Год плана")
    @Persist
    public Long getYear() {return year;}
    /** Год плана */
    private Long year ;

}
