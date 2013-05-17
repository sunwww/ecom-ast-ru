package ru.ecom.mis.ejb.form.expert;

import java.sql.Date;

import javax.persistence.OneToOne;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.expert.ClinicExpertCard;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.ecom.mis.ejb.form.medcase.MedCaseForm;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;

@EntityForm
@EntityFormPersistance(clazz = ClinicExpertCard.class)
@Comment("КЭР")
@WebTrail(comment = "КЭР", nameProperties = "id", view = "entityView-expert_ker.do")
@Parent(property = "medCase", parentForm=MedCaseForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/MedCase/ClinicExpertCard")
public class ClinicExpertCardForm extends IdEntityForm {
	
	
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

	/** Характеристика случая экспертизы */
	@Comment("Характеристика случая экспертизы")
	@Persist
	public Long getPatternCase() {return thePatternCase;}
	public void setPatternCase(Long aPatternCase) {thePatternCase = aPatternCase;}

	/** Листок нетрудоспособности */
	@Comment("Листок нетрудоспособности")
	@Persist
	public Long getDisabilityDocument() {return theDisabilityDocument;}
	public void setDisabilityDocument(Long aDisabilityDocument) {theDisabilityDocument = aDisabilityDocument;}

	/** Вид экспертизы */
	@Comment("Вид экспертизы")
	@Persist
	public Long getModeCase() {return theModeCase;}
	public void setModeCase(Long aModeCase) {theModeCase = aModeCase;}

	/** Предмет экспертизы */
	@Comment("Предмет экспертизы")
	@Persist
	public Long getSubjectCase() {return theSubjectCase;}
	public void setSubjectCase(Long aSubjectCase) {theSubjectCase = aSubjectCase;}

	/** Отклонение от стандартов */
	@Comment("Отклонение от стандартов")
	@Persist
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
	@Persist
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
	@Persist
	public Long getExpComposition() {return theExpComposition;}
	public void setExpComposition(Long aExpComposition) {theExpComposition = aExpComposition;}

	/** Статус пациента */
	@Comment("Статус пациента")
	@Persist
	public Long getPatientStatus() {return thePatientStatus;}
	public void setPatientStatus(Long aPatientStatus) {thePatientStatus = aPatientStatus;}

	/** Статус пациента */
	private Long thePatientStatus;
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
	/** Листок нетрудоспособности */
	private Long theDisabilityDocument;
	/** Характеристика случая экспертизы */
	private Long thePatternCase;
	/** Профессия */
	private String theProfession;
	/** СМО */
	private Long theMedCase;

	/** Пациент */
	@Comment("Пациент")
	@Persist
	public Long getPatient() {return thePatient;}
	public void setPatient(Long aPatient) {thePatient = aPatient;}

	/** Пациент */
	private Long thePatient;
	
	/** Дата экспертизы */
	@Comment("Дата экспертизы")
	@Persist @DateString @DoDateString
	public String getExpertDate() {return theExpertDate;}
	public void setExpertDate(String aExpertDate) {theExpertDate = aExpertDate;}

	/** Дата экспертизы */
	private String theExpertDate;
	
}
