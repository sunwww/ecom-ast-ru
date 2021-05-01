package ru.ecom.mis.ejb.form.expert;

import lombok.Setter;
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
@Setter
public class DirectOfMedicalCommissionForm extends IdEntityForm {

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

	/** Тип ВК */
	@Comment("Тип ВК")
	@Persist @Required
	public Long getType() {return type;}

	/** Тип ВК */
	private Long type;
	/** СМО */
	@Comment("СМО")
	@Persist
	public Long getMedCase() {return medCase;}

	/** Профессия */
	@Comment("Профессия")
	@Persist @Required
	public String getProfession() {return profession;}

	/** Статус пациента */
	@Comment("Статус пациента")
	@Persist @Required
	public Long getPatientStatus() {return patientStatus;}

	/** Пациент */
	@Comment("Пациент")
	@Persist
	public Long getPatient() {return patient;}

	/** Дата направления */
	@Comment("Дата направления")
	@Persist @DateString @DoDateString @Required
	public String getOrderDate() {return orderDate;}

	/** Дата выхода на нетрудосп. */
	@Comment("Дата выхода на нетрудосп.")
	@Persist @DateString @DoDateString @Required
	public String getDisabilityDate() {return disabilityDate;}

	/** Диагноз осн. */
	@Comment("Диагноз осн.")
	@Persist @Required
	public Long getMainDiagnosis() {return mainDiagnosis;}

	/** Обоснование направления */
	@Comment("Обоснование направления")
	@Persist @Required
	public Long getOrderConclusion() {return orderConclusion;}

	/** Диагноз сопутствующий */
	@Comment("Диагноз сопутствующий")
	@Persist
	public String getConcomitantDiagnosis() {return concomitantDiagnosis;}

	/** Диагноз осложнение */
	@Comment("Диагноз осложнение")
	@Persist
	public String getComplicationDiagnosis() {return complicationDiagnosis;}

	/** Направивший специалист */
	@Comment("Направивший специалист")
	@Persist @Required
	public Long getOrderFunction() {return orderFunction;}

	/** Листок нетрудоспособности */
	@Comment("Листок нетрудоспособности")
	@Persist
	public Long getDisabilityDocument() {return disabilityDocument;}

	/** Причина задержки */
	@Comment("Причина задержки")
	@Persist 
	public String getDelayReason() {return delayReason;}

	/** Направившее ЛПУ */
	@Comment("Направившее ЛПУ")
	@Persist
	public Long getOrderLpu() {return orderLpu;}

	/** Причина направления */
	@Comment("Причина направления")
	@Persist @Required
	public Long getReasonDirect() {return reasonDirect;}

	/** Лечение на момент подачи */
	@Comment("Лечение на момент подачи")
	@Persist @Required
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
	/** Направившее ЛПУ */
	private Long orderLpu;
	/** Причина задержки */
	private String delayReason;
	/** Листок нетрудоспособности */
	private Long disabilityDocument;
    /** Направивший специалист */
	private Long orderFunction;
	/** Диагноз осложнение */
	private String complicationDiagnosis;
	/** Диагноз сопутствующий */
	private String concomitantDiagnosis;
	/** Обоснование направления */
	private Long orderConclusion;
	/** Диагноз осн. */
	private Long mainDiagnosis;
	/** Дата выхода на нетрудосп. */
	private String disabilityDate;
	/** Дата направления */
	private String orderDate;
	/** Пациент */
	private Long patient;
	/** Статус пациента */
	private Long patientStatus;
	/** Профессия */
	private String profession;
	/** СМО */
	private Long medCase;
	
	
	/** Дата создания */
	@Comment("Дата создания")
	@DateString @DoDateString @Persist
	public String getCreateDate() {return createDate;}

	/** Дата редактирования */
	@Comment("Дата редактирования")
	@DateString @DoDateString @Persist
	public String getEditDate() {return editDate;}

	/** Время создания */
	@Comment("Время создания")
	@TimeString @DoTimeString @Persist
	public String getCreateTime() {return createTime;}
	/** Время редактрования */
	@Comment("Время редактрования")
	@TimeString @DoTimeString @Persist
	public String getEditTime() {return editTime;}
	/** Пользователь, который создал запись */
	@Comment("Пользователь, который создал запись")
	@Persist
	public String getCreateUsername() {return createUsername;}
	/** Пользователь, который последний редактировал запись */
	@Comment("Пользователь, который последний редактировал запись")
	@Persist
	public String getEditUsername() {return editUsername;}

	/** Пользователь, который последний редактировал запись */
	private String editUsername;
	/** Пользователь, который создал запись */
	private String createUsername;
	/** Время редактрования */
	private String editTime;
	/** Время создания */
	private String createTime;
	/** Дата редактирования */
	private String editDate;
	/** Дата создания */
	private String createDate;
	
	/** Заключение дополнение */
	@Comment("Заключение дополнение")
	@Persist
	public String getConclusionSentAdd() {return conclusionSentAdd;}

	/** Причина дополнение */
	@Comment("Причина дополнение")
	@Persist
	public String getReasonAdd() {return reasonAdd;}

	/** Причина дополнение */
	private String reasonAdd;
	/** Заключение дополнение */
	private String conclusionSentAdd;
}
