package ru.ecom.mis.ejb.form.expert;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.interceptors.AParentEntityFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.AParentPrepareCreateInterceptors;
import ru.ecom.mis.ejb.domain.expert.ClinicExpertCard;
import ru.ecom.mis.ejb.form.expert.interceptor.ClinicExpertCardPreCreateInterceptor;
import ru.ecom.mis.ejb.form.medcase.MedCaseForm;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@EntityFormPersistance(clazz = ClinicExpertCard.class)
@Comment("Направление на ВК")
@WebTrail(comment = "Направление на ВК", nameProperties = "id", view = "entityView-expert_ker.do")
@Parent(property = "medCase", parentForm=MedCaseForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/MedCase/ClinicExpertCard/Direct")
@AParentPrepareCreateInterceptors(
        @AParentEntityFormInterceptor(ClinicExpertCardPreCreateInterceptor.class)
)
public class DirectOfMedicalCommissionForm extends IdEntityForm {
	/** СМО */
	@Comment("СМО")
	@Persist
	public Long getMedCase() {return theMedCase;}
	public void setMedCase(Long aMedCase) {theMedCase = aMedCase;}
	
	/** Профессия */
	@Comment("Профессия")
	@Persist
	public String getProfession() {return theProfession;}
	public void setProfession(String aProfession) {theProfession = aProfession;}
	
	/** Статус пациента */
	@Comment("Статус пациента")
	@Persist
	public Long getPatientStatus() {return thePatientStatus;}
	public void setPatientStatus(Long aPatientStatus) {thePatientStatus = aPatientStatus;}

	/** Пациент */
	@Comment("Пациент")
	@Persist
	public Long getPatient() {return thePatient;}
	public void setPatient(Long aPatient) {thePatient = aPatient;}

	/** Дата направления */
	@Comment("Дата направления")
	@Persist @DateString @DoDateString @Required
	public String getOrderDate() {return theOrderDate;}
	public void setOrderDate(String aOrderDate) {theOrderDate = aOrderDate;}

	/** Дата выхода на нетрудосп. */
	@Comment("Дата выхода на нетрудосп.")
	@Persist @DateString @DoDateString @Required
	public String getDisabilityDate() {return theDisabilityDate;}
	public void setDisabilityDate(String aDisabilityDate) {theDisabilityDate = aDisabilityDate;}

	/** Диагноз осн. */
	@Comment("Диагноз осн.")
	@Persist @Required
	public Long getMainDiagnosis() {return theMainDiagnosis;}
	public void setMainDiagnosis(Long aMainDiagnosis) {theMainDiagnosis = aMainDiagnosis;}

	/** Обоснование направления */
	@Comment("Обоснование направления")
	@Persist @Required
	public Long getOrderConclusion() {return theOrderConclusion;}
	public void setOrderConclusion(Long aOrderConclusion) {theOrderConclusion = aOrderConclusion;}

	/** Диагноз сопутствующий */
	@Comment("Диагноз сопутствующий")
	@Persist
	public String getConcomitantDiagnosis() {return theConcomitantDiagnosis;}
	public void setConcomitantDiagnosis(String aConcomitantDiagnosis) {theConcomitantDiagnosis = aConcomitantDiagnosis;}

	/** Диагноз осложнение */
	@Comment("Диагноз осложнение")
	@Persist
	public String getComplicationDiagnosis() {return theComplicationDiagnosis;}
	public void setComplicationDiagnosis(String aComplicationDiagnosis) {theComplicationDiagnosis = aComplicationDiagnosis;}

	/** Направивший специалист */
	@Comment("Направивший специалист")
	@Persist @Required
	public Long getOrderFunction() {return theOrderFunction;}
	public void setOrderFunction(Long aOrderFunction) {theOrderFunction = aOrderFunction;}

	/** Листок нетрудоспособности */
	@Comment("Листок нетрудоспособности")
	@Persist
	public Long getDisabilityDocument() {return theDisabilityDocument;}
	public void setDisabilityDocument(Long aDisabilityDocument) {theDisabilityDocument = aDisabilityDocument;}

	/** Причина задержки */
	@Comment("Причина задержки")
	@Persist @Required
	public String getDelayReason() {return theDelayReason;}
	public void setDelayReason(String aDelayReason) {theDelayReason = aDelayReason;}

	/** Направившее ЛПУ */
	@Comment("Направившее ЛПУ")
	@Persist
	public Long getOrderLpu() {return theOrderLpu;}
	public void setOrderLpu(Long aOrderLpu) {theOrderLpu = aOrderLpu;}

	/** Направившее ЛПУ */
	private Long theOrderLpu;
	/** Причина задержки */
	private String theDelayReason;
	/** Листок нетрудоспособности */
	private Long theDisabilityDocument;
    /** Направивший специалист */
	private Long theOrderFunction;
	/** Диагноз осложнение */
	private String theComplicationDiagnosis;
	/** Диагноз сопутствующий */
	private String theConcomitantDiagnosis;
	/** Обоснование направления */
	private Long theOrderConclusion;
	/** Диагноз осн. */
	private Long theMainDiagnosis;
	/** Дата выхода на нетрудосп. */
	private String theDisabilityDate;
	/** Дата направления */
	private String theOrderDate;
	/** Пациент */
	private Long thePatient;
	/** Статус пациента */
	private Long thePatientStatus;
	/** Профессия */
	private String theProfession;
	/** СМО */
	private Long theMedCase;
}
