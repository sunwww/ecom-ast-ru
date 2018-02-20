package ru.ecom.mis.ejb.form.extdispplan;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.interceptors.AEntityFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.AViewInterceptors;
import ru.ecom.mis.ejb.domain.extdisp.ExtDispCard;
import ru.ecom.mis.ejb.domain.extdispplan.ExtDispPlanPopulationRecord;
import ru.ecom.mis.ejb.domain.patient.IdentityCard;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.ecom.mis.ejb.form.extdisp.interceptor.ExtDispCardViewInterceptor;
import ru.ecom.mis.ejb.form.patient.PatientForm;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.sql.Date;

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
