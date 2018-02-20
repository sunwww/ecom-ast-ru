package ru.ecom.mis.ejb.form.extdispplan;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.extdisp.ExtDispPlan;
import ru.ecom.mis.ejb.domain.extdispplan.ExtDispPlanPopulation;
import ru.ecom.mis.ejb.form.extdisp.voc.VocExtDispForm;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

import javax.persistence.Entity;

/**
 * План доп. диспансеризации для населения
  */
@EntityForm
@EntityFormPersistance(clazz = ExtDispPlanPopulation.class)
@Comment("План дополнительной диспансеризации")
@WebTrail(comment = "План дополнительной диспансеризации", nameProperties= "id"
        , view="entityView-extDispPlan_plan.do")
@EntityFormSecurityPrefix("/Policy/Mis/ExtDisp/Card")
public class ExtDispPlanPopulationForm extends IdEntityForm{

    /** Год плана*/
    @Comment("Год плана")
    @Persist
    public Long getYear() {return theYear;}
    public void setYear(Long aYear) {theYear = aYear;}
    /** Год плана */
    private Long theYear ;

}
