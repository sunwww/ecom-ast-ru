package ru.ecom.mis.ejb.form.extdispplan;

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

public class ExtDispPlanPopulationRecordForm extends IdEntityForm {

    /** План диспансеризации */
    @Comment("План диспансеризации")
    @Persist
    public Long getPlan() {return thePlan;}
    public void setPlan(Long aPlan) {thePlan = aPlan;}
    /** План диспансеризации */
    private Long thePlan ;

    /** Дата планируемой диспансеризации */
    @Comment("Дата планируемой диспансеризации")
    @Persist @DateString @DoDateString
    public String getPlanDispDate() {return thePlanDispDate;}
    public void setPlanDispDate(String aPlanDispDate) {thePlanDispDate = aPlanDispDate;}
    /** Дата планируемой диспансеризации */
    private String thePlanDispDate ;

    /** Пациент */
    @Comment("Пациент")
    @Persist
    public Long getPatient() {return thePatient;}
    public void setPatient(Long aPatient) {thePatient = aPatient;}
    /** Пациент */
    private Long thePatient ;

    /** Удалено */
    @Comment("Удалено")
    @Persist
    public Boolean getIsDeleted() {return theIsDeleted;}
    public void setIsDeleted(Boolean aIsDeleted) {theIsDeleted = aIsDeleted;}
    /** Удалено */
    private Boolean theIsDeleted ;

}
