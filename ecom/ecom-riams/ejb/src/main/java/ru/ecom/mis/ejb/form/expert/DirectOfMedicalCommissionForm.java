package ru.ecom.mis.ejb.form.expert;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.interceptors.AParentEntityFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.AParentPrepareCreateInterceptors;
import ru.ecom.mis.ejb.domain.expert.ClinicExpertCard;
import ru.ecom.mis.ejb.form.expert.interceptor.ClinicExpertCardPreCreateInterceptor;
import ru.ecom.mis.ejb.form.medcase.MedCaseForm;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.transforms.DoTimeString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;
import ru.nuzmsh.forms.validator.validators.TimeString;

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

	/** Описание состояния здоровья пациента */
	@Comment("Описание состояния здоровья пациента")
	@Persist @Required
	public String getPatientHealthInfo() {return thePatientHealthInfo;}
	public void setPatientHealthInfo(String aPatientHealthInfo) {thePatientHealthInfo = aPatientHealthInfo;}
	/** Описание состояния здоровья пациента */
	private String thePatientHealthInfo ;

	/** Лист нетрудоспособности выданный другим ЛПУ */
	@Comment("Лист нетрудоспособности выданный другим ЛПУ")
	@Persist
	public String getAnotherDisabilityNumber() {return theAnotherDisabilityNumber;}
	public void setAnotherDisabilityNumber(String aAnotherDisabilityNumber) {theAnotherDisabilityNumber = aAnotherDisabilityNumber;}
	/** Лист нетрудоспособности выданный другим ЛПУ */
	private String theAnotherDisabilityNumber ;

	/** Тип ВК */
	@Comment("Тип ВК")
	@Persist @Required
	public Long getType() {return theType;}
	public void setType(Long aType) {theType = aType;}

	/** Тип ВК */
	private Long theType;
	/** СМО */
	@Comment("СМО")
	@Persist
	public Long getMedCase() {return theMedCase;}
	public void setMedCase(Long aMedCase) {theMedCase = aMedCase;}
	
	/** Профессия */
	@Comment("Профессия")
	@Persist @Required
	public String getProfession() {return theProfession;}
	public void setProfession(String aProfession) {theProfession = aProfession;}
	
	/** Статус пациента */
	@Comment("Статус пациента")
	@Persist @Required
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
	@Persist 
	public String getDelayReason() {return theDelayReason;}
	public void setDelayReason(String aDelayReason) {theDelayReason = aDelayReason;}

	/** Направившее ЛПУ */
	@Comment("Направившее ЛПУ")
	@Persist
	public Long getOrderLpu() {return theOrderLpu;}
	public void setOrderLpu(Long aOrderLpu) {theOrderLpu = aOrderLpu;}

	/** Причина направления */
	@Comment("Причина направления")
	@Persist @Required
	public Long getReasonDirect() {return theReasonDirect;}
	public void setReasonDirect(Long aReasonDirect) {theReasonDirect = aReasonDirect;}

	/** Лечение на момент подачи */
	@Comment("Лечение на момент подачи")
	@Persist @Required
	public String getTreatmentCurrent() {return theTreatmentCurrent;}
	public void setTreatmentCurrent(String aTreatmentCurrent) {theTreatmentCurrent = aTreatmentCurrent;}

	/** Срок предполагаемого лечения */
	@Comment("Срок предполагаемого лечения")
	@Persist @DateString @DoDateString 
	public String getPreFinishDate() {return thePreFinishDate;}
	public void setPreFinishDate(String aPreFinishDate) {thePreFinishDate = aPreFinishDate;}

	/** Срок предполагаемого лечения */
	private String thePreFinishDate;
	/** Лечение на момент подачи */
	private String theTreatmentCurrent;
	/** Причина направления */
	private Long theReasonDirect;
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
	
	
	/** Дата создания */
	@Comment("Дата создания")
	@DateString @DoDateString @Persist
	public String getCreateDate() {return theCreateDate;}
	public void setCreateDate(String aCreateDate) {theCreateDate = aCreateDate;}
	
	/** Дата редактирования */
	@Comment("Дата редактирования")
	@DateString @DoDateString @Persist
	public String getEditDate() {return theEditDate;}
	public void setEditDate(String aEditDate) {theEditDate = aEditDate;}
	
	/** Время создания */
	@Comment("Время создания")
	@TimeString @DoTimeString @Persist
	public String getCreateTime() {return theCreateTime;}
	public void setCreateTime(String aCreateTime) {theCreateTime = aCreateTime;}
	/** Время редактрования */
	@Comment("Время редактрования")
	@TimeString @DoTimeString @Persist
	public String getEditTime() {return theEditTime;}
	public void setEditTime(String aEditTime) {theEditTime = aEditTime;}
	/** Пользователь, который создал запись */
	@Comment("Пользователь, который создал запись")
	@Persist
	public String getCreateUsername() {return theCreateUsername;}
	public void setCreateUsername(String aCreateUsername) {theCreateUsername = aCreateUsername;}
	/** Пользователь, который последний редактировал запись */
	@Comment("Пользователь, который последний редактировал запись")
	@Persist
	public String getEditUsername() {return theEditUsername;}
	public void setEditUsername(String aEditUsername) {theEditUsername = aEditUsername;}

	/** Пользователь, который последний редактировал запись */
	private String theEditUsername;
	/** Пользователь, который создал запись */
	private String theCreateUsername;
	/** Время редактрования */
	private String theEditTime;
	/** Время создания */
	private String theCreateTime;
	/** Дата редактирования */
	private String theEditDate;
	/** Дата создания */
	private String theCreateDate;
	
	/** Заключение дополнение */
	@Comment("Заключение дополнение")
	@Persist
	public String getConclusionSentAdd() {return theConclusionSentAdd;}
	public void setConclusionSentAdd(String aConclusionSentAdd) {theConclusionSentAdd = aConclusionSentAdd;}

	/** Причина дополнение */
	@Comment("Причина дополнение")
	@Persist
	public String getReasonAdd() {return theReasonAdd;}
	public void setReasonAdd(String aReasonAdd) {theReasonAdd = aReasonAdd;}

	/** Причина дополнение */
	private String theReasonAdd;
	/** Заключение дополнение */
	private String theConclusionSentAdd;
}
