package ru.ecom.mis.ejb.form.prescription;

import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.Subclasses;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.prescription.Prescription;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.transforms.DoTimeString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;
import ru.nuzmsh.forms.validator.validators.TimeString;

/**
 * Назначение
 * @author oegorova
 *
 */

@EntityForm
@EntityFormPersistance(clazz = Prescription.class)
@Comment("Назначение")
@WebTrail(comment = "Назначение", nameProperties= "descriptionInfo",list="entityParentList-pres_prescription.do", view="entitySubclassView-pres_prescription.do")
@Parent(property="prescriptionList", parentForm=AbstractPrescriptionListForm.class)
@Subclasses({DrugPrescriptionForm.class, DietPrescriptionForm.class
	, ServicePrescriptionForm.class, ModePrescriptionForm.class})
@EntityFormSecurityPrefix("/Policy/Mis/Prescription")
@Setter
public class PrescriptionForm extends IdEntityForm {

	/** Тип случая СМО */
	@Comment("Тип случая СМО")
	public String getMedcaseType() {return medcaseType;}
	/** Тип случая СМО */
	private String medcaseType ;

	/** ИД случая СМО */
	@Comment("ИД случая СМО")
	public Long getMedcaseId() {return medcaseId;}
	/** ИД случая СМО */
	private Long medcaseId ;

	/** Поток обслуживания */
	@Comment("Поток обслуживания")
	@Persist
	public Long getServiceStream() {return serviceStream;}
	private Long serviceStream ;

	/** Лист назначений */
	@Comment("Лист назначений")
	@Persist
	public Long getPrescriptionList() {return prescriptionList;}

	/** Регистратор */
	@Comment("Регистратор")
	@Persist
	public Long getRegistrator() {return registrator;}

	/** Плановая дата начала */
	@Comment("Плановая дата начала")
	@Persist @Required
	@DateString @DoDateString
	public String getPlanStartDate() {return planStartDate;}

	/** Плановое время начала */
	@Comment("Плановое время начала")
	@Persist @TimeString @DoTimeString @Required
	public String getPlanStartTime() {return planStartTime;}

	/** Плановая дата окончания */
	@Comment("Плановая дата окончания")
	@Persist @DateString @DoDateString
	public String getPlanEndDate() {return planEndDate;}

	/** Плановое время окончания */
	@Comment("Плановое время окончания")
	@Persist @TimeString @DoTimeString
	public String getPlanEndTime() {return planEndTime;}

	/** Дата отмены */
	@Comment("Дата отмены")
	@Persist @DateString @DoDateString
	public String getCancelDate() {return cancelDate;}

	/** Время отмены */
	@Comment("Время отмены")
	@Persist @TimeString @DoTimeString
	public String getCancelTime() {return cancelTime;}

	/** Причина отмены */
	@Comment("Причина отмены")
	@Persist
	public Long getCancelReason() {return cancelReason;}

	/** Отменивший */
	@Comment("Отменивший")
	@Persist
	public Long getCancelSpecial() {return cancelSpecial;}

	/** Отменивший (text) */
	@Comment("Отменивший (text)")
	@Persist
	public String getCancelWorkerInfo() {return cancelWorkerInfo;}

	/** Назначивший */
	@Comment("Назначивший")
	@Persist
	public Long getPrescriptSpecial() {return prescriptSpecial;}

	/** Назначивший (text) */
	@Comment("Назначивший (text)")
	@Persist
	public String getPrescriptorInfo() {return prescriptorInfo;}

	/** Комментарии */
	@Comment("Комментарии")
	@Persist
	public String getComments() {return comments;}

	/** Описание */
	@Comment("Описание")
	@Persist
	public String getDescription() {return "";}

	/** Состояние исполнения */
	@Comment("Состояние исполнения")
	@Persist
	public Long getFulfilmentState() {return fulfilmentState;}

	/** Регистратор (text) */
	@Comment("Регистратор (text)")
	@Persist
	public String getRegistratorInfo() {return registratorInfo;}

	/** Описание назначения */
	@Comment("Описание назначения")
	@Persist
	public String getDescriptionInfo() {return descriptionInfo;}

	/** Дата и время назначения */
	@Comment("Дата и время назначения")
	@Persist
	public String getPrescriptTimeStamp() {return prescriptTimeStamp;}

	/** Дата и время отмены назначения */
	@Comment("Дата и время отмены назначения")
	public String getPrescriptCancelTimeStamp() {return prescriptCancelTimeStamp;}

	/** Подпись */
	@Comment("Подпись")
	@Persist
	public String getSignature() {return signature;}

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

	/** Лабораторные исследования */
	@Comment("Лабораторные исследования")
	public String getLabServicies() {
		return labServicies;
	}

	/** Лабораторные исследования */
	private String labServicies;
	
	/** Дата по лаб. исследованию */
	@Comment("Дата по лаб. исследованию")
	@DateString @DoDateString
	public String getLabDate() {return labDate;}

	/** Дата по лаб. исследованию */
	private String labDate;
	
	/** Список услуг по лаборатории */
	@Comment("Список услуг по лаборатории")
	public String getLabList() {return labList;}

	/** Список услуг по лаборатории */
	private String labList;
	
	/** Функциональные исследования */
	@Comment("Функциональные исследования")
	public String getFuncServicies() {
		return funcServicies;
	}
	/** Кабинет назначения */
	@Comment("Кабинет назначения")
	@Persist
	public Long getPrescriptCabinet() {return prescriptCabinet;}

	/** Кабинет назначения */
	private Long prescriptCabinet;
	/** Функциональные исследования */
	private String funcServicies;
	
	/** Дата функционального исследования */
	@Comment("Дата функционального исследования")
	@DateString @DoDateString
	public String getFuncDate() {
		return funcDate;
	}

	/** Дата функционального исследования */
	private String funcDate;
	
	/** Кабинет для лабораторного исследования */
	@Comment("Кабинет для лабораторного исследования")
	public String getLabCabinet() {
		return labCabinet;
	}

	/** Кабинет для лабораторного исследования */
	private String labCabinet;
	
	/** Кабинет для функ. исследования */
	@Comment("Кабинет для функ. исследования")
	public String getFuncCabinet() {
		return funcCabinet;
	}

	/** Кабинет для функ. исследования */
	private String funcCabinet;
	
	/** Список функциональных исследований */
	@Comment("Список функциональных исследований")
	public String getFuncList() {
		return funcList;
	}
	/** Список функциональных исследований */
	private String funcList;
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
	/** Подпись */
	private String signature;
	/** Дата и время отмены назначения */
	private String prescriptCancelTimeStamp;
	/** Дата и время назначения */
	private String prescriptTimeStamp;
	/** Лист назначений */
	private Long prescriptionList;
	/** Регистратор */
	private Long registrator;
	/** Плановая дата начала */
	private String planStartDate;
	/** Плановое время начала */
	private String planStartTime;
	/** Плановая дата окончания */
	private String planEndDate;
	/** Плановое время окончания */
	private String planEndTime;
	/** Дата отмены */
	private String cancelDate;
	/** Время отмены */
	private String cancelTime;
	/** Причина отмены */
	private Long cancelReason;
	/** Отменивший */
	private Long cancelSpecial;
	/** Отменивший (text) */
	private String cancelWorkerInfo;
	/** Назначивший */
	private Long prescriptSpecial;
	/** Назначивший (text) */
	private String prescriptorInfo;
	/** Комментарии */
	private String comments;
	/** Состояние исполнения */
	private Long fulfilmentState;
	/** Регистратор (text) */
	private String registratorInfo;
	/** Описание назначения */
	private String descriptionInfo;

	/** Тип назначения */
	@Comment("Тип назначения")
	@Persist 
	public Long getPrescriptType() {return prescriptType;}
	private Long prescriptType;
	
	/** Метка назначения */
	@Comment("Метка назначения")
	public String getLabelPrescript() {return labelPrescript;}
	private String labelPrescript;
	
	/** Дата забора */
	@Comment("Дата забора")
	@Persist @DateString @DoDateString
	public String getIntakeDate() {return intakeDate;}
    private String intakeDate;

	/** Время забора */
	@Comment("Время забора")
	@Persist @DoTimeString @TimeString 
	public String getIntakeTime() {return intakeTime;}
    private String intakeTime;

	/** Пользователь, осуществившей забор */
	@Comment("Пользователь, осуществившей забор")
	public String getIntakeUsername() {return intakeUsername;}
    private String intakeUsername;

	/** Идентификатор материала */
	@Comment("Идентификатор материала")
	public String getMaterialId() {return materialId;}
	private String materialId;

	/** Номер пробирки ПЦР*/
	@Comment("Номер пробирки ПЦР")
	public String getMaterialPCRId() {return materialPCRId;}
	private String materialPCRId;

	/** Причина отмены текст */
	@Comment("Причина отмены текст")
	@Persist
	public String getCancelReasonText() {return cancelReasonText;}
    private String cancelReasonText;

	/** Пользователь отменивший */
	@Comment("Пользователь отменивший")
	@Persist
	public String getCancelUsername() {return cancelUsername;}
	private String cancelUsername;

	/** Дата передачи в лабораторию */
	@Comment("Дата передачи в лабораторию")
	@Persist @DoDateString @DateString
	public String getTransferDate() {return transferDate;}
    private String transferDate;

	/** Время передачи */
	@Comment("Время передачи")
	@Persist @DoTimeString @TimeString
	public String getTransferTime() {return transferTime;}
    private String transferTime;

	/** Пользователь, принявший биоматериал */
	@Comment("Пользователь, принявший биоматериал")
	@Persist
	public String getTransferUsername() {return transferUsername;}
	private String transferUsername;

	/** Время из wct */
	@Comment("Время из wct")
	@Persist
	public Long getCalendarTime() {return calendarTime;}
	private Long calendarTime;

	/** Отделение (забора) */
	@Comment("Отделение (забора)")
	@Persist
	public Long getDepartment() {return department;}
	private Long department;

	/** Отделение для забора крови */
	@Comment("Отделение для забора крови")
	public Long getLabDepartment() {return labDepartment;}
	private Long labDepartment;
	
	/** Хирургическая операция */
	@Comment("Хирургическая операция")
	@Persist
	public Long getSurgicalOperation() {return surgicalOperation;}
	private Long surgicalOperation;
	
	/** ИД пациента */
	@Comment("ИД пациента")
	public Long getPatient() {return patient;}
	private Long patient ;

	/** Разрешены только оплаченные услуги по потоку обслуживания */
	@Comment("Разрешены только оплаченные услуги по потоку обслуживания")
	public Boolean getAllowOnlyPaid() {return allowOnlyPaid;}
	private Boolean allowOnlyPaid ;

	/** Раб. функция, осущ. забор */
	@Comment("Раб. функция, осущ. забор")
	@Persist
	public Long getIntakeSpecial() {return intakeSpecial;}
	private Long intakeSpecial;

	/** Дата установки патологии */
	@Comment("Дата установки патологии")
	@Persist @DateString @DoDateString
	public String getSetPatologyDate() {return setPatologyDate;}
	private String setPatologyDate;

	/** Время установки патологии */
	@Comment("Время установки патологии")
	@Persist @TimeString @DoTimeString
	public String getSetPatologyTime() {return setPatologyTime;}
	private String setPatologyTime;

	/** Пользователь, установивший патологию*/
	@Comment("Пользователь, установивший патологию")
	@Persist
	public String getSetPatologyUsername() {return setPatologyUsername;}
	private String setPatologyUsername;

	/** Проставил патологию специалист */
	@Comment("Проставил патологию специалист")
	@Persist
	public Long getSetPatologySpecial() {return setPatologySpecial;}
	private Long setPatologySpecial;

	/** ИД гарантийного письма */
	@Comment("ИД гарантийного письма")
	public Long getGuaranteeId() {return guaranteeId;}
	private Long guaranteeId ;

	/** Примечание для лаборатории*/
	@Comment("Примечание для лаборатории")
	@Persist
	public String getNoteForLab() {return noteForLab;}
	/** Примечание для лаборатории*/
	private String noteForLab;

	/** Примечание1*/
	@Comment("Примечание1")
	public String getNoteForLab1() {return noteForLab1;}
	/** Примечание1*/
	private String noteForLab1;

	/** Примечание2*/
	@Comment("Примечание2")
	public String getNoteForLab2() {return noteForLab2;}
	/** Примечание2*/
	private String noteForLab2;

	/** Примечание3*/
	@Comment("Примечание3")
	public String getNoteForLab3() {return noteForLab3;}
	/** Примечание3*/
	private String noteForLab3;
}

