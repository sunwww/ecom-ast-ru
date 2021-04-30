package ru.ecom.mis.ejb.form.extdispplan;

import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.extdispplan.ExtDispPlanPopulationRecord;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;

@EntityForm
@EntityFormPersistance(clazz =  ExtDispPlanPopulationRecord.class)
@Comment("Карта учета дополнительной диспансеризации (профосмотров) (УФ N 131/у)")
@WebTrail(comment = "Карта учета дополнительной диспансеризации (профосмотров) (УФ N 131/у)"
        , nameProperties= "id", list="entityParentList-extDispPlan_record.do", view="entityParentView-extDispPlan_record.do")
@Parent(property="plan", parentForm=ExtDispPlanPopulationForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/ExtDisp/Card")

@Setter
public class ExtDispPlanPopulationRecordForm extends IdEntityForm {

    /** План диспансеризации */
    @Comment("План диспансеризации")
    @Persist
    public Long getPlan() {return plan;}
    /** План диспансеризации */
    private Long plan ;

    /** Дата планируемой диспансеризации */
    @Comment("Дата планируемой диспансеризации")
    @Persist @DateString @DoDateString
    public String getPlanDispDate() {return planDispDate;}
    /** Дата планируемой диспансеризации */
    private String planDispDate ;

    /** Пациент */
    @Comment("Пациент")
    @Persist
    public Long getPatient() {return patient;}
    /** Пациент */
    private Long patient ;

    /** Удалено */
    @Comment("Удалено")
    @Persist
    public Boolean getIsDeleted() {return isDeleted;}
    /** Удалено */
    private Boolean isDeleted ;

}
