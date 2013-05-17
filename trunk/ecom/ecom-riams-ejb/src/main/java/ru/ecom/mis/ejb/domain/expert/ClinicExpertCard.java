package ru.ecom.mis.ejb.domain.expert;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.mis.ejb.domain.disability.DisabilityDocument;
import ru.ecom.mis.ejb.domain.expert.voc.VocExpertComposition;
import ru.ecom.mis.ejb.domain.expert.voc.VocExpertConclusion;
import ru.ecom.mis.ejb.domain.expert.voc.VocExpertDeviationStandards;
import ru.ecom.mis.ejb.domain.expert.voc.VocExpertModeCase;
import ru.ecom.mis.ejb.domain.expert.voc.VocExpertPatientStatus;
import ru.ecom.mis.ejb.domain.expert.voc.VocExpertPatternCase;
import ru.ecom.mis.ejb.domain.expert.voc.VocExpertSubject;
import ru.ecom.mis.ejb.domain.medcase.MedCase;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Table(schema="SQLUser")
public class ClinicExpertCard extends BaseEntity {
	
	/** СМО */
	@Comment("СМО")
	@OneToOne
	public MedCase getMedCase() {return theMedCase;}
	public void setMedCase(MedCase aMedCase) {theMedCase = aMedCase;}

	/** Профессия */
	@Comment("Профессия")
	public String getProfession() {return theProfession;}
	public void setProfession(String aProfession) {theProfession = aProfession;}

	/** Характеристика случая экспертизы */
	@Comment("Характеристика случая экспертизы")
	@OneToOne
	public VocExpertPatternCase getPatternCase() {return thePatternCase;}
	public void setPatternCase(VocExpertPatternCase aPatternCase) {thePatternCase = aPatternCase;}

	/** Листок нетрудоспособности */
	@Comment("Листок нетрудоспособности")
	@OneToOne
	public DisabilityDocument getDisabilityDocument() {return theDisabilityDocument;}
	public void setDisabilityDocument(DisabilityDocument aDisabilityDocument) {theDisabilityDocument = aDisabilityDocument;}

	/** Вид экспертизы */
	@Comment("Вид экспертизы")
	@OneToOne
	public VocExpertModeCase getModeCase() {return theModeCase;}
	public void setModeCase(VocExpertModeCase aModeCase) {theModeCase = aModeCase;}

	/** Предмет экспертизы */
	@Comment("Предмет экспертизы")
	@OneToOne
	public VocExpertSubject getSubjectCase() {return theSubjectCase;}
	public void setSubjectCase(VocExpertSubject aSubjectCase) {theSubjectCase = aSubjectCase;}

	/** Отклонение от стандартов */
	@Comment("Отклонение от стандартов")
	@OneToOne
	public VocExpertDeviationStandards getDeviationStandards() {return theDeviationStandards;}
	public void setDeviationStandards(VocExpertDeviationStandards aDeviationStandards) {theDeviationStandards = aDeviationStandards;}

	/** Текст оклонения от стандартов */
	@Comment("Текст оклонения от стандартов")
	public String getDeviationStandardsText() {return theDeviationStandardsText;}
	public void setDeviationStandardsText(String aDeviationStandardsText) {theDeviationStandardsText = aDeviationStandardsText;}

	/** Дефекты */
	@Comment("Дефекты")
	public String getDefects() {return theDefects;}
	public void setDefects(String aDefects) {theDefects = aDefects;}

	/** Достижение результата этапа */
	@Comment("Достижение результата этапа")
	public String getResultStep() {return theResultStep;}
	public void setResultStep(String aResultStep) {theResultStep = aResultStep;}

	/** Обоснование заключения */
	@Comment("Обоснование заключения")
	@OneToOne
	public VocExpertConclusion getConclusion() {return theConclusion;}
	public void setConclusion(VocExpertConclusion aConclusion) {theConclusion = aConclusion;}

	/** Продление */
	@Comment("Продление")
	public Date getConclusionDate() {return theConclusionDate;}
	public void setConclusionDate(Date aConclusionDate) {theConclusionDate = aConclusionDate;}

	/** Дата направления в бюро МСЭ */
	@Comment("Дата направления в бюро МСЭ")
	public Date getOrderHADate() {return theOrderHADate;}
	public void setOrderHADate(Date aOrderDate) {theOrderHADate = aOrderDate;}

	/** Заключение МСЭ */
	@Comment("Заключение МСЭ")
	public String getConclusionHA() {return theConclusionHA;}
	public void setConclusionHA(String aConclusionHealthAssessment) {theConclusionHA = aConclusionHealthAssessment;}

	/** Дата получения заключения МСЭ */
	@Comment("Дата получения заключения МСЭ")
	public Date getReceiveHADate() {return theReceiveHADate;}
	public void setReceiveHADate(Date aReceiveHADate) {theReceiveHADate = aReceiveHADate;}

	/** Дополнительная информация по заключению */
	@Comment("Дополнительная информация по заключению")
	public String getAdditionInfo() {return theAdditionInfo;}
	public void setAdditionInfo(String aAdditionInfo) {theAdditionInfo = aAdditionInfo;}

	/** Состав экспертов */
	@Comment("Состав экспертов")
	@OneToOne
	public VocExpertComposition getExpComposition() {return theExpComposition;}
	public void setExpComposition(VocExpertComposition aExpComposition) {theExpComposition = aExpComposition;}

	/** Статус пациента */
	@Comment("Статус пациента")
	@OneToOne
	public VocExpertPatientStatus getPatientStatus() {return thePatientStatus;}
	public void setPatientStatus(VocExpertPatientStatus aPatientStatus) {thePatientStatus = aPatientStatus;}

	/** Статус пациента */
	private VocExpertPatientStatus thePatientStatus;
	/** Состав экспертов */
	private VocExpertComposition theExpComposition;
	/** Дополнительная информация по заключению */
	private String theAdditionInfo;
	/** Дата получения заключения МСЭ */
	private Date theReceiveHADate;
	/** Заключение МСЭ */
	private String theConclusionHA;
	/** Дата направления в бюро МСЭ */
	private Date theOrderHADate;
	/** Продление */
	private Date theConclusionDate;
	/** Обоснование заключения */
	private VocExpertConclusion theConclusion;
	/** Достижение результата этапа */
	private String theResultStep;
	/** Дефекты */
	private String theDefects;
	/** Текст оклонения от стандартов */
	private String theDeviationStandardsText;
	/** Отклонение от стандартов */
	private VocExpertDeviationStandards theDeviationStandards;
	/** Предмет экспертизы */
	private VocExpertSubject theSubjectCase;
	/** Вид экспертизы */
	private VocExpertModeCase theModeCase;
	/** Листок нетрудоспособности */
	private DisabilityDocument theDisabilityDocument;
	/** Характеристика случая экспертизы */
	private VocExpertPatternCase thePatternCase;
	/** Профессия */
	private String theProfession;
	/** СМО */
	private MedCase theMedCase;
	
	/** Пациент */
	@Comment("Пациент")
	@OneToOne
	public Patient getPatient() {return thePatient;}
	public void setPatient(Patient aPatient) {thePatient = aPatient;}

	/** Пациент */
	private Patient thePatient;
	
	/** Дата экспертизы */
	@Comment("Дата экспертизы")
	public Date getExpertDate() {return theExpertDate;}
	public void setExpertDate(Date aExpertDate) {theExpertDate = aExpertDate;}

	/** Дата экспертизы */
	private Date theExpertDate;

}
