package ru.ecom.mis.ejb.form.expert;

import lombok.Setter;
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
@Setter
public class ClinicExpertCardForm extends DirectOfMedicalCommissionForm {

	/** Описание состояния здоровья пациента */
	@Comment("Описание состояния здоровья пациента")
	@Persist @Required
	public String getPatientHealthInfo() {return patientHealthInfo;}
	/** Описание состояния здоровья пациента */
	private String patientHealthInfo ;

	/** Лист нетрудоспособности выданный другим ЛПУ */
	@Comment("Лист нетрудоспособности выданный другим ЛПУ")
	@Persist
	public String getAnotherDisabilityNumber() {return anotherDisabilityNumber;}
	/** Лист нетрудоспособности выданный другим ЛПУ */
	private String anotherDisabilityNumber ;

	/** Характеристика случая экспертизы */
	@Comment("Характеристика случая экспертизы")
	@Persist @Required
	public Long getPatternCase() {return patternCase;}

	/** Вид экспертизы */
	@Comment("Вид экспертизы")
	@Persist @Required
	public Long getModeCase() {return modeCase;}

	/** Предмет экспертизы */
	@Comment("Предмет экспертизы")
	@Persist @Required
	public Long getSubjectCase() {return subjectCase;}

	/** Отклонение от стандартов */
	@Comment("Отклонение от стандартов")
	@Persist @Required
	public Long getDeviationStandards() {return deviationStandards;}

	/** Текст оклонения от стандартов */
	@Comment("Текст оклонения от стандартов")
	@Persist 
	public String getDeviationStandardsText() {return deviationStandardsText;}

	/** Дефекты */
	@Comment("Дефекты")
	@Persist
	public String getDefects() {return defects;}

	/** Достижение результата этапа */
	@Comment("Достижение результата этапа")
	@Persist
	public String getResultStep() {return resultStep;}

	/** Обоснование заключения */
	@Comment("Обоснование заключения")
	@Persist @Required
	public Long getConclusion() {return conclusion;}

	/** Продление */
	@Comment("Продление")
	@Persist @DateString @DoDateString
	public String getConclusionDate() {return conclusionDate;}

	/** Дата направления в бюро МСЭ */
	@Comment("Дата направления в бюро МСЭ")
	@Persist @DateString @DoDateString
	public String getOrderHADate() {return orderHADate;}

	/** Заключение МСЭ */
	@Comment("Заключение МСЭ")
	@Persist
	public String getConclusionHA() {return conclusionHA;}

	/** Дата получения заключения МСЭ */
	@Comment("Дата получения заключения МСЭ")
	@Persist  @DateString @DoDateString
	public String getReceiveHADate() {return receiveHADate;}

	/** Дополнительная информация по заключению */
	@Comment("Дополнительная информация по заключению")
	@Persist
	public String getAdditionInfo() {return additionInfo;}

	/** Состав экспертов */
	@Comment("Состав экспертов")
	@Persist @Required
	public Long getExpComposition() {return expComposition;}


	/** Причина задержки */
	@Comment("Причина задержки")
	@Persist
	public String getDelayReason() {return delayReason;}

	/** Причина направления */
	@Comment("Причина направления")
	@Persist 
	public Long getReasonDirect() {return reasonDirect;}

	/** Лечение на момент подачи */
	@Comment("Лечение на момент подачи")
	@Persist @MaxLength(value = 250)
	public String getTreatmentCurrent() {return treatmentCurrent;}

	/** Срок предполагаемого лечения */
	@Comment("Срок предполагаемого лечения")
	@Persist @DateString @DoDateString
	public String getPreFinishDate() {return preFinishDate;}

	/** Срок предполагаемого лечения */
	private String preFinishDate;
	/** Лечение на момент подачи */
	private String treatmentCurrent;
	/** Причина направления */
	private Long reasonDirect;
	/** Причина задержки */
	private String delayReason;
	/** Состав экспертов */
	private Long expComposition;
	/** Дополнительная информация по заключению */
	private String additionInfo;
	/** Дата получения заключения МСЭ */
	private String receiveHADate;
	/** Заключение МСЭ */
	private String conclusionHA;
	/** Дата направления в бюро МСЭ */
	private String orderHADate;
	/** Продление */
	private String conclusionDate;
	/** Обоснование заключения */
	private Long conclusion;
	/** Достижение результата этапа */
	private String resultStep;
	/** Дефекты */
	private String defects;
	/** Текст оклонения от стандартов */
	private String deviationStandardsText;
	/** Отклонение от стандартов */
	private Long deviationStandards;
	/** Предмет экспертизы */
	private Long subjectCase;
	/** Вид экспертизы */
	private Long modeCase;
	/** Характеристика случая экспертизы */
	private Long patternCase;

	/** Дата экспертизы */
	@Comment("Дата экспертизы")
	@Persist @DateString @DoDateString @Required
	public String getExpertDate() {return expertDate;}

	/** Дата экспертизы */
	private String expertDate;
	
	/** Дополнительная информация по МСЭ */
	@Comment("Дополнительная информация по МСЭ")
	@Persist
	public String getAdditionInfoHA() {return additionInfoHA;}
	/** Дополнительная информация по МСЭ */
	private String additionInfoHA;
	
	/** Заключение. Направляется... */
	@Comment("Заключение. Направляется...")
	@Persist
	public Long getConclusionSent() {return conclusionSent;}

	/** Заключение. Направляется... */
	private Long conclusionSent;
	
	/** Замечания */
	@Comment("Замечания")
	@Persist
	public String getNotes() {return notes;}

	/** Замечания */
	private String notes;
	
	/** Порядковый номер в журнале */
	@Comment("Порядковый номер в журнале")
	@Persist 
	public String getNumberInJournal() {return numberInJournal;}

	/** Порядковый номер в журнале */
	private String numberInJournal;
}
