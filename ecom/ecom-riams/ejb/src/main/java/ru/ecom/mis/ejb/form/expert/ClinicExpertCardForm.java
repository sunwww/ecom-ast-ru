package ru.ecom.mis.ejb.form.expert;

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
import ru.nuzmsh.forms.validator.validators.MaxLength;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@EntityFormPersistance(clazz = ClinicExpertCard.class)
@Comment("Врачебная комиссия")
@WebTrail(comment = "Врачебная комиссия", nameProperties = "id", view = "entityView-expert_ker.do")
@Parent(property = "medCase", parentForm=MedCaseForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/MedCase/ClinicExpertCard")
@AParentPrepareCreateInterceptors(
        @AParentEntityFormInterceptor(ClinicExpertCardPreCreateInterceptor.class)
)
public class ClinicExpertCardForm extends DirectOfMedicalCommissionForm {

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

	/** Характеристика случая экспертизы */
	@Comment("Характеристика случая экспертизы")
	@Persist @Required
	public Long getPatternCase() {return thePatternCase;}
	public void setPatternCase(Long aPatternCase) {thePatternCase = aPatternCase;}


	/** Вид экспертизы */
	@Comment("Вид экспертизы")
	@Persist @Required
	public Long getModeCase() {return theModeCase;}
	public void setModeCase(Long aModeCase) {theModeCase = aModeCase;}

	/** Предмет экспертизы */
	@Comment("Предмет экспертизы")
	@Persist @Required
	public Long getSubjectCase() {return theSubjectCase;}
	public void setSubjectCase(Long aSubjectCase) {theSubjectCase = aSubjectCase;}

	/** Отклонение от стандартов */
	@Comment("Отклонение от стандартов")
	@Persist @Required
	public Long getDeviationStandards() {return theDeviationStandards;}
	public void setDeviationStandards(Long aDeviationStandards) {theDeviationStandards = aDeviationStandards;}

	/** Текст оклонения от стандартов */
	@Comment("Текст оклонения от стандартов")
	@Persist 
	public String getDeviationStandardsText() {return theDeviationStandardsText;}
	public void setDeviationStandardsText(String aDeviationStandardsText) {theDeviationStandardsText = aDeviationStandardsText;}

	/** Дефекты */
	@Comment("Дефекты")
	@Persist
	public String getDefects() {return theDefects;}
	public void setDefects(String aDefects) {theDefects = aDefects;}

	/** Достижение результата этапа */
	@Comment("Достижение результата этапа")
	@Persist
	public String getResultStep() {return theResultStep;}
	public void setResultStep(String aResultStep) {theResultStep = aResultStep;}

	/** Обоснование заключения */
	@Comment("Обоснование заключения")
	@Persist @Required
	public Long getConclusion() {return theConclusion;}
	public void setConclusion(Long aConclusion) {theConclusion = aConclusion;}

	/** Продление */
	@Comment("Продление")
	@Persist @DateString @DoDateString
	public String getConclusionDate() {return theConclusionDate;}
	public void setConclusionDate(String aConclusionDate) {theConclusionDate = aConclusionDate;}

	/** Дата направления в бюро МСЭ */
	@Comment("Дата направления в бюро МСЭ")
	@Persist @DateString @DoDateString
	public String getOrderHADate() {return theOrderHADate;}
	public void setOrderHADate(String aOrderDate) {theOrderHADate = aOrderDate;}

	/** Заключение МСЭ */
	@Comment("Заключение МСЭ")
	@Persist
	public String getConclusionHA() {return theConclusionHA;}
	public void setConclusionHA(String aConclusionHealthAssessment) {theConclusionHA = aConclusionHealthAssessment;}

	/** Дата получения заключения МСЭ */
	@Comment("Дата получения заключения МСЭ")
	@Persist  @DateString @DoDateString
	public String getReceiveHADate() {return theReceiveHADate;}
	public void setReceiveHADate(String aReceiveHADate) {theReceiveHADate = aReceiveHADate;}

	/** Дополнительная информация по заключению */
	@Comment("Дополнительная информация по заключению")
	@Persist
	public String getAdditionInfo() {return theAdditionInfo;}
	public void setAdditionInfo(String aAdditionInfo) {theAdditionInfo = aAdditionInfo;}

	/** Состав экспертов */
	@Comment("Состав экспертов")
	@Persist @Required
	public Long getExpComposition() {return theExpComposition;}
	public void setExpComposition(Long aExpComposition) {theExpComposition = aExpComposition;}


	/** Причина задержки */
	@Comment("Причина задержки")
	@Persist
	public String getDelayReason() {return theDelayReason;}
	public void setDelayReason(String aDelayReason) {theDelayReason = aDelayReason;}

	/** Причина направления */
	@Comment("Причина направления")
	@Persist 
	public Long getReasonDirect() {return theReasonDirect;}
	public void setReasonDirect(Long aReasonDirect) {theReasonDirect = aReasonDirect;}

	/** Лечение на момент подачи */
	@Comment("Лечение на момент подачи")
	@Persist @MaxLength(value = 250)
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
	/** Причина задержки */
	private String theDelayReason;
	/** Состав экспертов */
	private Long theExpComposition;
	/** Дополнительная информация по заключению */
	private String theAdditionInfo;
	/** Дата получения заключения МСЭ */
	private String theReceiveHADate;
	/** Заключение МСЭ */
	private String theConclusionHA;
	/** Дата направления в бюро МСЭ */
	private String theOrderHADate;
	/** Продление */
	private String theConclusionDate;
	/** Обоснование заключения */
	private Long theConclusion;
	/** Достижение результата этапа */
	private String theResultStep;
	/** Дефекты */
	private String theDefects;
	/** Текст оклонения от стандартов */
	private String theDeviationStandardsText;
	/** Отклонение от стандартов */
	private Long theDeviationStandards;
	/** Предмет экспертизы */
	private Long theSubjectCase;
	/** Вид экспертизы */
	private Long theModeCase;
	/** Характеристика случая экспертизы */
	private Long thePatternCase;



	
	/** Дата экспертизы */
	@Comment("Дата экспертизы")
	@Persist @DateString @DoDateString @Required
	public String getExpertDate() {return theExpertDate;}
	public void setExpertDate(String aExpertDate) {theExpertDate = aExpertDate;}

	/** Дата экспертизы */
	private String theExpertDate;
	
	/** Дополнительная информация по МСЭ */
	@Comment("Дополнительная информация по МСЭ")
	@Persist
	public String getAdditionInfoHA() {return theAdditionInfoHA;}
	public void setAdditionInfoHA(String aAdditionInfoHA) {theAdditionInfoHA = aAdditionInfoHA;}	
	/** Дополнительная информация по МСЭ */
	private String theAdditionInfoHA;
	
	/** Заключение. Направляется... */
	@Comment("Заключение. Направляется...")
	@Persist
	public Long getConclusionSent() {return theConclusionSent;}
	public void setConclusionSent(Long aConclusionSent) {theConclusionSent = aConclusionSent;}

	/** Заключение. Направляется... */
	private Long theConclusionSent;
	
	/** Замечания */
	@Comment("Замечания")
	@Persist
	public String getNotes() {return theNotes;}
	public void setNotes(String aNotes) {theNotes = aNotes;}

	/** Замечания */
	private String theNotes;
	
	/** Порядковый номер в журнале */
	@Comment("Порядковый номер в журнале")
	@Persist 
	public String getNumberInJournal() {return theNumberInJournal;}
	public void setNumberInJournal(String aNumberInJournal) {theNumberInJournal = aNumberInJournal;}

	/** Порядковый номер в журнале */
	private String theNumberInJournal;
}
