package ru.ecom.mis.ejb.domain.expert;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.ejb.services.util.ColumnConstants;
import ru.ecom.expomc.ejb.domain.med.VocIdc10;
import ru.ecom.mis.ejb.domain.disability.DisabilityDocument;
import ru.ecom.mis.ejb.domain.expert.voc.VocExpertComposition;
import ru.ecom.mis.ejb.domain.expert.voc.VocExpertConclusion;
import ru.ecom.mis.ejb.domain.expert.voc.VocExpertConclusionSent;
import ru.ecom.mis.ejb.domain.expert.voc.VocExpertDeviationStandards;
import ru.ecom.mis.ejb.domain.expert.voc.VocExpertModeCase;
import ru.ecom.mis.ejb.domain.expert.voc.VocExpertOrderConclusion;
import ru.ecom.mis.ejb.domain.expert.voc.VocExpertPatientStatus;
import ru.ecom.mis.ejb.domain.expert.voc.VocExpertPatternCase;
import ru.ecom.mis.ejb.domain.expert.voc.VocExpertReason;
import ru.ecom.mis.ejb.domain.expert.voc.VocExpertSubject;
import ru.ecom.mis.ejb.domain.expert.voc.VocExpertType;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.ecom.mis.ejb.domain.medcase.MedCase;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Table(schema="SQLUser")
@AIndexes(value = { @AIndex(properties = { "medCase" })
,@AIndex(properties={"patient"})
,@AIndex(properties={"expertDate"})
})
public class ClinicExpertCard extends BaseEntity {

	/** Описание состояния здоровья пациента */
	@Comment("Описание состояния здоровья пациента")
	@Column(length= ColumnConstants.TEXT_MAXLENGHT)
	public String getPatientHealthInfo() {return thePatientHealthInfo;}
	public void setPatientHealthInfo(String aPatientHealthInfo) {thePatientHealthInfo = aPatientHealthInfo;}
	/** Описание состояния здоровья пациента */
	private String thePatientHealthInfo ;

	/** Лист нетрудоспособности выданный другим ЛПУ */
	@Comment("Лист нетрудоспособности выданный другим ЛПУ")
	public String getAnotherDisabilityNumber() {return theAnotherDisabilityNumber;}
	public void setAnotherDisabilityNumber(String aAnotherDisabilityNumber) {theAnotherDisabilityNumber = aAnotherDisabilityNumber;}
	/** Лист нетрудоспособности выданный другим ЛПУ */
	private String theAnotherDisabilityNumber ;

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
	
	/** Дополнительная информация по МСЭ */
	@Comment("Дополнительная информация по МСЭ")
	public String getAdditionInfoHA() {return theAdditionInfoHA;}
	public void setAdditionInfoHA(String aAdditionInfoHA) {theAdditionInfoHA = aAdditionInfoHA;}

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

	/** Причина задержки */
	@Comment("Причина задержки")
	public String getDelayReason() {return theDelayReason;}
	public void setDelayReason(String aDelayReason) {theDelayReason = aDelayReason;}

	/** Причина направления */
	@Comment("Причина направления")
	@OneToOne
	public VocExpertReason getReasonDirect() {return theReasonDirect;}
	public void setReasonDirect(VocExpertReason aReasonDirect) {theReasonDirect = aReasonDirect;}

	/** Лечение на момент подачи */
	@Comment("Лечение на момент подачи")
	public String getTreatmentCurrent() {return theTreatmentCurrent;}
	public void setTreatmentCurrent(String aTreatmentCurrent) {theTreatmentCurrent = aTreatmentCurrent;}

	/** Срок предполагаемого лечения */
	@Comment("Срок предполагаемого лечения")
	public Date getPreFinishDate() {return thePreFinishDate;}
	public void setPreFinishDate(Date aPreFinishDate) {thePreFinishDate = aPreFinishDate;}

	/** Заключение. Направляется... */
	@Comment("Заключение. Направляется...")
	@OneToOne
	public VocExpertConclusionSent getConclusionSent() {return theConclusionSent;}
	public void setConclusionSent(VocExpertConclusionSent aConclusionSent) {theConclusionSent = aConclusionSent;}

	/** Заключение дополнение */
	@Comment("Заключение дополнение")
	public String getConclusionSentAdd() {return theConclusionSentAdd;}
	public void setConclusionSentAdd(String aConclusionSentAdd) {theConclusionSentAdd = aConclusionSentAdd;}

	/** Причина дополнение */
	@Comment("Причина дополнение")
	public String getReasonAdd() {return theReasonAdd;}
	public void setReasonAdd(String aReasonAdd) {theReasonAdd = aReasonAdd;}

	/** Причина дополнение */
	private String theReasonAdd;
	/** Заключение дополнение */
	private String theConclusionSentAdd;
	/** Заключение. Направляется... */
	private VocExpertConclusionSent theConclusionSent;
	/** Срок предполагаемого лечения */
	private Date thePreFinishDate;
	/** Лечение на момент подачи */
	private String theTreatmentCurrent;
	/** Причина направления */
	private VocExpertReason theReasonDirect;
	/** Причина задержки */
	private String theDelayReason;
	/** Статус пациента */
	private VocExpertPatientStatus thePatientStatus;
	/** Состав экспертов */
	private VocExpertComposition theExpComposition;
	/** Дополнительная информация по МСЭ */
	private String theAdditionInfoHA;
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

	/** Дата направления */
	@Comment("Дата направления")
	public Date getOrderDate() {return theOrderDate;}
	public void setOrderDate(Date aOrderDate) {theOrderDate = aOrderDate;}

	/** Дата выхода на нетрудосп. */
	@Comment("Дата выхода на нетрудосп.")
	public Date getDisabilityDate() {return theDisabilityDate;}
	public void setDisabilityDate(Date aDisabilityDate) {theDisabilityDate = aDisabilityDate;}

	/** Диагноз осн. */
	@Comment("Диагноз осн.")
	@OneToOne
	public VocIdc10 getMainDiagnosis() {return theMainDiagnosis;}
	public void setMainDiagnosis(VocIdc10 aMainDiagnosis) {theMainDiagnosis = aMainDiagnosis;}

	/** Обоснование направления */
	@Comment("Обоснование направления")
	@OneToOne
	public VocExpertOrderConclusion getOrderConclusion() {return theOrderConclusion;}
	public void setOrderConclusion(VocExpertOrderConclusion aOrderConclusion) {theOrderConclusion = aOrderConclusion;}

	/** Диагноз сопутствующий */
	@Comment("Диагноз сопутствующий")
	public String getConcomitantDiagnosis() {return theConcomitantDiagnosis;}
	public void setConcomitantDiagnosis(String aConcomitantDiagnosis) {theConcomitantDiagnosis = aConcomitantDiagnosis;}

	/** Диагноз осложнение */
	@Comment("Диагноз осложнение")
	public String getComplicationDiagnosis() {return theComplicationDiagnosis;}
	public void setComplicationDiagnosis(String aComplicationDiagnosis) {theComplicationDiagnosis = aComplicationDiagnosis;}

	/** Направивший специалист */
	@Comment("Направивший специалист")
	@OneToOne
	public WorkFunction getOrderFunction() {return theOrderFunction;}
	public void setOrderFunction(WorkFunction aOrderFunction) {theOrderFunction = aOrderFunction;}

	/** Направившее ЛПУ */
	@Comment("Направившее ЛПУ")
	@OneToOne
	public MisLpu getOrderLpu() {return theOrderLpu;}
	public void setOrderLpu(MisLpu aOrderLpu) {theOrderLpu = aOrderLpu;}

	/** Направившее ЛПУ */
	private MisLpu theOrderLpu;
	/** Направивший специалист */
	private WorkFunction theOrderFunction;
	/** Диагноз осложнение */
	private String theComplicationDiagnosis;
	/** Диагноз сопутствующий */
	private String theConcomitantDiagnosis;
	/** Обоснование направления */
	private VocExpertOrderConclusion theOrderConclusion;
	/** Диагноз осн. */
	private VocIdc10 theMainDiagnosis;
	/** Дата выхода на нетрудосп. */
	private Date theDisabilityDate;
	/** Дата направления */
	private Date theOrderDate;
	/** Дата экспертизы */
	private Date theExpertDate;

	
	/** Дата создания */
	@Comment("Дата создания")
	public Date getCreateDate() {return theCreateDate;}
	public void setCreateDate(Date aCreateDate) {theCreateDate = aCreateDate;}
	
	/** Дата редактирования */
	@Comment("Дата редактирования")
	public Date getEditDate() {return theEditDate;}
	public void setEditDate(Date aEditDate) {theEditDate = aEditDate;}
	
	/** Время создания */
	@Comment("Время создания")
	public Time getCreateTime() {return theCreateTime;}
	public void setCreateTime(Time aCreateTime) {theCreateTime = aCreateTime;}
	/** Время редактрования */
	@Comment("Время редактрования")
	public Time getEditTime() {return theEditTime;}
	public void setEditTime(Time aEditTime) {theEditTime = aEditTime;}
	/** Пользователь, который создал запись */
	@Comment("Пользователь, который создал запись")
	public String getCreateUsername() {return theCreateUsername;}
	public void setCreateUsername(String aCreateUsername) {theCreateUsername = aCreateUsername;}
	/** Пользователь, который последний редактировал запись */
	@Comment("Пользователь, который последний редактировал запись")
	public String getEditUsername() {return theEditUsername;}
	public void setEditUsername(String aEditUsername) {theEditUsername = aEditUsername;}

	/** Пользователь, который последний редактировал запись */
	private String theEditUsername;
	/** Пользователь, который создал запись */
	private String theCreateUsername;
	/** Время редактрования */
	private Time theEditTime;
	/** Время создания */
	private Time theCreateTime;
	/** Дата редактирования */
	private Date theEditDate;
	/** Дата создания */
	private Date theCreateDate;
	
	/** Замечания */
	@Comment("Замечания")
	public String getNotes() {return theNotes;}
	public void setNotes(String aNotes) {theNotes = aNotes;}

	/** Замечания */
	private String theNotes;
	
	/** ТИп ВК */
	@Comment("ТИп ВК")
	@OneToOne
	public VocExpertType getType() {return theType;}
	public void setType(VocExpertType aType) {theType = aType;}

	/** ТИп ВК */
	private VocExpertType theType;
	/** Порядковый номер в журнале */
	@Comment("Порядковый номер в журнале")
	public String getNumberInJournal() {return theNumberInJournal;}
	public void setNumberInJournal(String aNumberInJournal) {theNumberInJournal = aNumberInJournal;}

	/** Порядковый номер в журнале */
	private String theNumberInJournal;
}
