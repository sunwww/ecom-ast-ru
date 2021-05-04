package ru.ecom.mis.ejb.domain.expert;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
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
@Getter
@Setter
public class ClinicExpertCard extends BaseEntity {

	/** Описание состояния здоровья пациента */
	@Comment("Описание состояния здоровья пациента")
	@Column(length= ColumnConstants.TEXT_MAXLENGHT)
	public String getPatientHealthInfo() {return patientHealthInfo;}
	/** Описание состояния здоровья пациента */
	private String patientHealthInfo ;

	/** Лист нетрудоспособности выданный другим ЛПУ */
	private String anotherDisabilityNumber ;

	/** СМО */
	@Comment("СМО")
	@OneToOne
	public MedCase getMedCase() {return medCase;}


	/** Характеристика случая экспертизы */
	@Comment("Характеристика случая экспертизы")
	@OneToOne
	public VocExpertPatternCase getPatternCase() {return patternCase;}

	/** Листок нетрудоспособности */
	@Comment("Листок нетрудоспособности")
	@OneToOne
	public DisabilityDocument getDisabilityDocument() {return disabilityDocument;}

	/** Вид экспертизы */
	@Comment("Вид экспертизы")
	@OneToOne
	public VocExpertModeCase getModeCase() {return modeCase;}

	/** Предмет экспертизы */
	@Comment("Предмет экспертизы")
	@OneToOne
	public VocExpertSubject getSubjectCase() {return subjectCase;}

	/** Отклонение от стандартов */
	@Comment("Отклонение от стандартов")
	@OneToOne
	public VocExpertDeviationStandards getDeviationStandards() {return deviationStandards;}


	/** Обоснование заключения */
	@Comment("Обоснование заключения")
	@OneToOne
	public VocExpertConclusion getConclusion() {return conclusion;}

	/** Состав экспертов */
	@Comment("Состав экспертов")
	@OneToOne
	public VocExpertComposition getExpComposition() {return expComposition;}

	/** Статус пациента */
	@Comment("Статус пациента")
	@OneToOne
	public VocExpertPatientStatus getPatientStatus() {return patientStatus;}

	/** Причина направления */
	@Comment("Причина направления")
	@OneToOne
	public VocExpertReason getReasonDirect() {return reasonDirect;}

	/** Заключение. Направляется... */
	@Comment("Заключение. Направляется...")
	@OneToOne
	public VocExpertConclusionSent getConclusionSent() {return conclusionSent;}

	/** Причина дополнение */
	private String reasonAdd;
	/** Заключение дополнение */
	private String conclusionSentAdd;
	/** Заключение. Направляется... */
	private VocExpertConclusionSent conclusionSent;
	/** Срок предполагаемого лечения */
	private Date preFinishDate;
	/** Лечение на момент подачи */
	private String treatmentCurrent;
	/** Причина направления */
	private VocExpertReason reasonDirect;
	/** Причина задержки */
	private String delayReason;
	/** Статус пациента */
	private VocExpertPatientStatus patientStatus;
	/** Состав экспертов */
	private VocExpertComposition expComposition;
	/** Дополнительная информация по МСЭ */
	private String additionInfoHA;
	/** Дополнительная информация по заключению */
	private String additionInfo;
	/** Дата получения заключения МСЭ */
	private Date receiveHADate;
	/** Заключение МСЭ */
	private String conclusionHA;
	/** Дата направления в бюро МСЭ */
	private Date orderHADate;
	/** Продление */
	private Date conclusionDate;
	/** Обоснование заключения */
	private VocExpertConclusion conclusion;
	/** Достижение результата этапа */
	private String resultStep;
	/** Дефекты */
	private String defects;
	/** Текст оклонения от стандартов */
	private String deviationStandardsText;
	/** Отклонение от стандартов */
	private VocExpertDeviationStandards deviationStandards;
	/** Предмет экспертизы */
	private VocExpertSubject subjectCase;
	/** Вид экспертизы */
	private VocExpertModeCase modeCase;
	/** Листок нетрудоспособности */
	private DisabilityDocument disabilityDocument;
	/** Характеристика случая экспертизы */
	private VocExpertPatternCase patternCase;
	/** Профессия */
	private String profession;
	/** СМО */
	private MedCase medCase;
	
	/** Пациент */
	@Comment("Пациент")
	@OneToOne
	public Patient getPatient() {return patient;}

	/** Пациент */
	private Patient patient;
	
	/** Диагноз осн. */
	@Comment("Диагноз осн.")
	@OneToOne
	public VocIdc10 getMainDiagnosis() {return mainDiagnosis;}

	/** Обоснование направления */
	@Comment("Обоснование направления")
	@OneToOne
	public VocExpertOrderConclusion getOrderConclusion() {return orderConclusion;}

	/** Направивший специалист */
	@Comment("Направивший специалист")
	@OneToOne
	public WorkFunction getOrderFunction() {return orderFunction;}

	/** Направившее ЛПУ */
	@Comment("Направившее ЛПУ")
	@OneToOne
	public MisLpu getOrderLpu() {return orderLpu;}

	/** Направившее ЛПУ */
	private MisLpu orderLpu;
	/** Направивший специалист */
	private WorkFunction orderFunction;
	/** Диагноз осложнение */
	private String complicationDiagnosis;
	/** Диагноз сопутствующий */
	private String concomitantDiagnosis;
	/** Обоснование направления */
	private VocExpertOrderConclusion orderConclusion;
	/** Диагноз осн. */
	private VocIdc10 mainDiagnosis;
	/** Дата выхода на нетрудосп. */
	private Date disabilityDate;
	/** Дата направления */
	private Date orderDate;
	/** Дата экспертизы */
	private Date expertDate;
	/** Пользователь, который последний редактировал запись */
	private String editUsername;
	/** Пользователь, который создал запись */
	private String createUsername;
	/** Время редактрования */
	private Time editTime;
	/** Время создания */
	private Time createTime;
	/** Дата редактирования */
	private Date editDate;
	/** Дата создания */
	private Date createDate;
	/** Замечания */
	private String notes;
	/** ТИп ВК */
	@Comment("ТИп ВК")
	@OneToOne
	public VocExpertType getType() {return type;}
	/** ТИп ВК */
	private VocExpertType type;
	/** Порядковый номер в журнале */
	private String numberInJournal;
}
