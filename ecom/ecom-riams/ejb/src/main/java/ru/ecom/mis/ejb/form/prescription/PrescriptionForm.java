package ru.ecom.mis.ejb.form.prescription;

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
public class PrescriptionForm extends IdEntityForm {

	/** Тип случая СМО */
	@Comment("Тип случая СМО")
	public String getMedcaseType() {return theMedcaseType;}
	public void setMedcaseType(String aMedcaseType) {theMedcaseType = aMedcaseType;}
	/** Тип случая СМО */
	private String theMedcaseType ;

	/** ИД случая СМО */
	@Comment("ИД случая СМО")
	public Long getMedcaseId() {return theMedcaseId;}
	public void setMedcaseId(Long aMedcaseId) {theMedcaseId = aMedcaseId;}
	/** ИД случая СМО */
	private Long theMedcaseId ;

	/** Поток обслуживания */
	@Comment("Поток обслуживания")
	@Persist
	public Long getServiceStream() {return theServiceStream;}
	public void setServiceStream(Long aServiceStream) {theServiceStream = aServiceStream;}
	private Long theServiceStream ;

	/** Лист назначений */
	@Comment("Лист назначений")
	@Persist
	public Long getPrescriptionList() {return thePrescriptionList;}
	public void setPrescriptionList(Long aPrescriptionList) {thePrescriptionList = aPrescriptionList;}

	/** Регистратор */
	@Comment("Регистратор")
	@Persist
	public Long getRegistrator() {return theRegistrator;}
	public void setRegistrator(Long aRegistrator) {theRegistrator = aRegistrator;}
	
	/** Плановая дата начала */
	@Comment("Плановая дата начала")
	@Persist @Required
	@DateString @DoDateString
	public String getPlanStartDate() {return thePlanStartDate;}
	public void setPlanStartDate(String aPlanStartDate) {thePlanStartDate = aPlanStartDate;}
	
	/** Плановое время начала */
	@Comment("Плановое время начала")
	@Persist @TimeString @DoTimeString @Required
	public String getPlanStartTime() {return thePlanStartTime;}
	public void setPlanStartTime(String aPlanStartTime) {thePlanStartTime = aPlanStartTime;}

	/** Плановая дата окончания */
	@Comment("Плановая дата окончания")
	@Persist @DateString @DoDateString
	public String getPlanEndDate() {return thePlanEndDate;}
	public void setPlanEndDate(String aPlanEndDate) {thePlanEndDate = aPlanEndDate;}

	/** Плановое время окончания */
	@Comment("Плановое время окончания")
	@Persist @TimeString @DoTimeString
	public String getPlanEndTime() {return thePlanEndTime;}
	public void setPlanEndTime(String aPlanEndTime) {thePlanEndTime = aPlanEndTime;}

	/** Дата отмены */
	@Comment("Дата отмены")
	@Persist @DateString @DoDateString
	public String getCancelDate() {return theCancelDate;}
	public void setCancelDate(String aCancelDate) {theCancelDate = aCancelDate;}

	/** Время отмены */
	@Comment("Время отмены")
	@Persist @TimeString @DoTimeString
	public String getCancelTime() {return theCancelTime;}
	public void setCancelTime(String aCancelTime) {theCancelTime = aCancelTime;}

	/** Причина отмены */
	@Comment("Причина отмены")
	@Persist
	public Long getCancelReason() {return theCancelReason;}
	public void setCancelReason(Long aCancelReason) {theCancelReason = aCancelReason;}

	/** Отменивший */
	@Comment("Отменивший")
	@Persist
	public Long getCancelSpecial() {return theCancelSpecial;}
	public void setCancelSpecial(Long aCancelWorker) {theCancelSpecial = aCancelWorker;}

	/** Отменивший (text) */
	@Comment("Отменивший (text)")
	@Persist
	public String getCancelWorkerInfo() {return theCancelWorkerInfo;}
	public void setCancelWorkerInfo (String aCancelWorkerInfo) {}

	/** Назначивший */
	@Comment("Назначивший")
	@Persist
	public Long getPrescriptSpecial() {return thePrescriptSpecial;}
	public void setPrescriptSpecial(Long aPrescriptor) {thePrescriptSpecial = aPrescriptor;	}

	/** Назначивший (text) */
	@Comment("Назначивший (text)")
	@Persist
	public String getPrescriptorInfo() {return thePrescriptorInfo;}
	public void setPrescriptorInfo(String aPrescriptorInfo) {}
	
	/** Комментарии */
	@Comment("Комментарии")
	@Persist
	public String getComments() {return theComments;}
	public void setComments(String aComments) {theComments = aComments;}

	/** Описание */
	@Comment("Описание")
	@Persist
	public String getDescription() {return "";}
	public void setDescription(String aDescription) {}
	
	/** Состояние исполнения */
	@Comment("Состояние исполнения")
	@Persist
	public Long getFulfilmentState() {return theFulfilmentState;}
	public void setFulfilmentState(Long aFulfilmentState) {theFulfilmentState = aFulfilmentState;}
	 
	/** Регистратор (text) */
	@Comment("Регистратор (text)")
	@Persist
	public String getRegistratorInfo() {return theRegistratorInfo;}
	public void setRegistratorInfo(String aRegistratorInfo) {}

	/** Описание назначения */
	@Comment("Описание назначения")
	@Persist
	public String getDescriptionInfo() {return theDescriptionInfo;}
	public void setDescriptionInfo(String aDescriptionInfo) {theDescriptionInfo = aDescriptionInfo;}
	
	/** Дата и время назначения */
	@Comment("Дата и время назначения")
	@Persist
	public String getPrescriptTimeStamp() {return thePrescriptTimeStamp;}
	public void setPrescriptTimeStamp(String aPrescriptTimeStamp) {thePrescriptTimeStamp = aPrescriptTimeStamp;}
	
	/** Дата и время отмены назначения */
	@Comment("Дата и время отмены назначения")
	public String getPrescriptCancelTimeStamp() {return thePrescriptCancelTimeStamp;}
	public void setPrescriptCancelTimeStamp(String aPrescriptCancelTimeStamp) {thePrescriptCancelTimeStamp = aPrescriptCancelTimeStamp;}

	/** Подпись */
	@Comment("Подпись")
	@Persist
	public String getSignature() {return theSignature;}
	public void setSignature(String aSignature) {theSignature = aSignature;}

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
	
	/** Лабораторные исследования */
	@Comment("Лабораторные исследования")
	public String getLabServicies() {
		return theLabServicies;
	}

	public void setLabServicies(String aLabServicies) {
		theLabServicies = aLabServicies;
	}
	
	/** Лабораторные исследования */
	private String theLabServicies;
	
	/** Дата по лаб. исследованию */
	@Comment("Дата по лаб. исследованию")
	@DateString @DoDateString
	public String getLabDate() {return theLabDate;}
	public void setLabDate(String aLabDate) {theLabDate = aLabDate;}

	/** Дата по лаб. исследованию */
	private String theLabDate;
	
	/** Список услуг по лаборатории */
	@Comment("Список услуг по лаборатории")
	public String getLabList() {return theLabList;}
	public void setLabList(String aLabList) {theLabList = aLabList;}

	/** Список услуг по лаборатории */
	private String theLabList;
	
	/** Функциональные исследования */
	@Comment("Функциональные исследования")
	public String getFuncServicies() {
		return theFuncServicies;
	}

	public void setFuncServicies(String aFuncServicies) {
		theFuncServicies = aFuncServicies;
	}
	/** Кабинет назначения */
	@Comment("Кабинет назначения")
	@Persist
	public Long getPrescriptCabinet() {return thePrescriptCabinet;}
	public void setPrescriptCabinet(Long aPrescriptCabinet) {
		thePrescriptCabinet = aPrescriptCabinet;
	}

	/** Кабинет назначения */
	private Long thePrescriptCabinet;
	/** Функциональные исследования */
	private String theFuncServicies;
	
	/** Дата функционального исследования */
	@Comment("Дата функционального исследования")
	@DateString @DoDateString
	public String getFuncDate() {
		return theFuncDate;
	}

	public void setFuncDate(String aFuncDate) {
		theFuncDate = aFuncDate;
	}

	/** Дата функционального исследования */
	private String theFuncDate;
	
	/** Кабинет для лабораторного исследования */
	@Comment("Кабинет для лабораторного исследования")
	public String getLabCabinet() {
		return theLabCabinet;
	}

	public void setLabCabinet(String aLabCabinet) {
		theLabCabinet = aLabCabinet;
	}

	/** Кабинет для лабораторного исследования */
	private String theLabCabinet;
	
	/** Кабинет для функ. исследования */
	@Comment("Кабинет для функ. исследования")
	public String getFuncCabinet() {
		return theFuncCabinet;
	}

	public void setFuncCabinet(String aFuncCabinet) {
		theFuncCabinet = aFuncCabinet;
	}

	/** Кабинет для функ. исследования */
	private String theFuncCabinet;
	
	/** Список функциональных исследований */
	@Comment("Список функциональных исследований")
	public String getFuncList() {
		return theFuncList;
	}

	public void setFuncList(String aFuncList) {
		theFuncList = aFuncList;
	}
	/** Список функциональных исследований */
	private String theFuncList;
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
	/** Подпись */
	private String theSignature;
	/** Дата и время отмены назначения */
	private String thePrescriptCancelTimeStamp;
	/** Дата и время назначения */
	private String thePrescriptTimeStamp;
	/** Лист назначений */
	private Long thePrescriptionList;
	/** Регистратор */
	private Long theRegistrator;
	/** Плановая дата начала */
	private String thePlanStartDate;
	/** Плановое время начала */
	private String thePlanStartTime;
	/** Плановая дата окончания */
	private String thePlanEndDate;
	/** Плановое время окончания */
	private String thePlanEndTime;
	/** Дата отмены */
	private String theCancelDate;
	/** Время отмены */
	private String theCancelTime;
	/** Причина отмены */
	private Long theCancelReason;
	/** Отменивший */
	private Long theCancelSpecial;
	/** Отменивший (text) */
	private String theCancelWorkerInfo;
	/** Назначивший */
	private Long thePrescriptSpecial;
	/** Назначивший (text) */
	private String thePrescriptorInfo;
	/** Комментарии */
	private String theComments;
	/** Состояние исполнения */
	private Long theFulfilmentState;
	/** Регистратор (text) */
	private String theRegistratorInfo;
	/** Описание назначения */
	private String theDescriptionInfo;

	/** Тип назначения */
	@Comment("Тип назначения")
	@Persist 
	public Long getPrescriptType() {return thePrescriptType;}
	public void setPrescriptType(Long aPrescriptType) {thePrescriptType = aPrescriptType;}
	private Long thePrescriptType;
	
	/** Метка назначения */
	@Comment("Метка назначения")
	public String getLabelPrescript() {return theLabelPrescript;}
	public void setLabelPrescript(String aLabelPrescript) {theLabelPrescript = aLabelPrescript;}
	private String theLabelPrescript;
	
	/** Дата забора */
	@Comment("Дата забора")
	@Persist @DateString @DoDateString
	public String getIntakeDate() {return theIntakeDate;}
	public void setIntakeDate(String aIntakeDate) {theIntakeDate = aIntakeDate;}
    private String theIntakeDate;

	/** Время забора */
	@Comment("Время забора")
	@Persist @DoTimeString @TimeString 
	public String getIntakeTime() {return theIntakeTime;}
	public void setIntakeTime(String aIntakeTime) {theIntakeTime = aIntakeTime;}
    private String theIntakeTime;

	/** Пользователь, осуществившей забор */
	@Comment("Пользователь, осуществившей забор")
	public String getIntakeUsername() {return theIntakeUsername;}
	public void setIntakeUsername(String aIntakeUsername) {theIntakeUsername = aIntakeUsername;}
    private String theIntakeUsername;

	/** Идентификатор материала */
	@Comment("Идентификатор материала")
	public String getMaterialId() {return theMaterialId;}
	public void setMaterialId(String aMaterialId) {theMaterialId = aMaterialId;}
	private String theMaterialId;

	/** Причина отмены текст */
	@Comment("Причина отмены текст")
	@Persist
	public String getCancelReasonText() {return theCancelReasonText;}
	public void setCancelReasonText(String aCancelReasonText) {theCancelReasonText = aCancelReasonText;}
    private String theCancelReasonText;

	/** Пользователь отменивший */
	@Comment("Пользователь отменивший")
	@Persist
	public String getCancelUsername() {return theCancelUsername;}
	public void setCancelUsername(String aCancelUsername) {theCancelUsername = aCancelUsername;}
	private String theCancelUsername;

	/** Дата передачи в лабораторию */
	@Comment("Дата передачи в лабораторию")
	@Persist @DoDateString @DateString
	public String getTransferDate() {return theTransferDate;}
	public void setTransferDate(String aTransferDate) {theTransferDate = aTransferDate;}
    private String theTransferDate;

	/** Время передачи */
	@Comment("Время передачи")
	@Persist @DoTimeString @TimeString
	public String getTransferTime() {return theTransferTime;}
	public void setTransferTime(String aTransferTime) {theTransferTime = aTransferTime;}
    private String theTransferTime;

	/** Пользователь, принявший биоматериал */
	@Comment("Пользователь, принявший биоматериал")
	@Persist
	public String getTransferUsername() {return theTransferUsername;}
	public void setTransferUsername(String aTransferUsername) {theTransferUsername = aTransferUsername;}
	private String theTransferUsername;

	/** Время из wct */
	@Comment("Время из wct")
	@Persist
	public Long getCalendarTime() {return theCalendarTime;}
	public void setCalendarTime(Long aCalendarTime) {theCalendarTime = aCalendarTime;}
	private Long theCalendarTime;

	/** Отделение (забора) */
	@Comment("Отделение (забора)")
	@Persist
	public Long getDepartment() {return theDepartment;}
	public void setDepartment(Long aDepartment) {theDepartment = aDepartment;}
	private Long theDepartment;

	/** Отделение для забора крови */
	@Comment("Отделение для забора крови")
	public Long getLabDepartment() {return theLabDepartment;}
	public void setLabDepartment(Long aLabDepartment) {theLabDepartment = aLabDepartment;}
	private Long theLabDepartment;
	
	/** Хирургическая операция */
	@Comment("Хирургическая операция")
	@Persist
	public Long getSurgicalOperation() {return theSurgicalOperation;}
	public void setSurgicalOperation(Long aSurgicalOperation) {theSurgicalOperation = aSurgicalOperation;}
	private Long theSurgicalOperation;
	
	/** ИД пациента */
	@Comment("ИД пациента")
	public Long getPatient() {return thePatient;}
	public void setPatient(Long aPatient) {thePatient = aPatient;}
	private Long thePatient ;

	/** Разрешены только оплаченные услуги по потоку обслуживания */
	@Comment("Разрешены только оплаченные услуги по потоку обслуживания")
	public Boolean getAllowOnlyPaid() {return theAllowOnlyPaid;}
	public void setAllowOnlyPaid(Boolean aAllowOnlyPaid) {theAllowOnlyPaid = aAllowOnlyPaid;}
	private Boolean theAllowOnlyPaid ;

	/** Раб. функция, осущ. забор */
	@Comment("Раб. функция, осущ. забор")
	@Persist
	public Long getIntakeSpecial() {return theIntakeSpecial;}
	public void setIntakeSpecial(Long aIntakeSpecial) {theIntakeSpecial = aIntakeSpecial;}
	private Long theIntakeSpecial;

	/** Дата установки патологии */
	@Comment("Дата установки патологии")
	@Persist @DateString @DoDateString
	public String getSetPatologyDate() {return theSetPatologyDate;}
	public void setSetPatologyDate(String aSetPatologyDate) {theSetPatologyDate = aSetPatologyDate;}
	private String theSetPatologyDate;

	/** Время установки патологии */
	@Comment("Время установки патологии")
	@Persist @TimeString @DoTimeString
	public String getSetPatologyTime() {return theSetPatologyTime;}
	public void setSetPatologyTime(String aSetPatologyTime) {theSetPatologyTime = aSetPatologyTime;}
	private String theSetPatologyTime;

	/** Пользователь, установивший патологию*/
	@Comment("Пользователь, установивший патологию")
	@Persist
	public String getSetPatologyUsername() {return theSetPatologyUsername;}
	public void setSetPatologyUsername(String aSetPatologyUsername) {theSetPatologyUsername = aSetPatologyUsername;}
	private String theSetPatologyUsername;

	/** Проставил патологию специалист */
	@Comment("Проставил патологию специалист")
	@Persist
	public Long getSetPatologySpecial() {return theSetPatologySpecial;}
	public void setSetPatologySpecial(Long aSetPatologySpecial) {theSetPatologySpecial = aSetPatologySpecial;}
	private Long theSetPatologySpecial;

	/** ИД гарантийного письма */
	@Comment("ИД гарантийного письма")
	public Long getGuaranteeId() {return theGuaranteeId;}
	public void setGuaranteeId(Long aGuaranteeId) {theGuaranteeId = aGuaranteeId;}
	private Long theGuaranteeId ;
}

