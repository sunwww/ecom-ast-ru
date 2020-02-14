package ru.ecom.mis.ejb.form.disability;

import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.interceptors.AParentEntityFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.AParentPrepareCreateInterceptors;
import ru.ecom.mis.ejb.domain.disability.DisabilityDocument;
import ru.ecom.mis.ejb.form.disability.interceptors.DocumentByPatientPreCreate;
import ru.ecom.mis.ejb.form.patient.PatientForm;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;

/**
 * Документ нетрудоспособности по пациенту
 * @author stkacheva
 */
@EntityForm
@EntityFormPersistance (clazz = DisabilityDocument.class)
@Comment("Документ нетрудоспособности по пациенту")
@WebTrail(comment = "Документ нетрудоспособности по пациенту", nameProperties= "id", view="entityParentView-dis_documentByParent.do"
	,list="entityParentList-dis_case.do",listComment="СНТ по пациенту")
@Parent(property="patient", parentForm=PatientForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Disability/Case/Document")
@AParentPrepareCreateInterceptors(
        @AParentEntityFormInterceptor(DocumentByPatientPreCreate.class)
)
public class DisabilityDocumentByPatientForm extends DisabilityDocumentForm {

	private Boolean isELN;
	@Comment("Электронный")
	@Persist
	public Boolean getELN() {
		return isELN;
	}
	public void setELN(Boolean ELN) {
		isELN = ELN;
	}

	/** Дата начала нетрудоспособности */
	@Comment("Дата начала нетрудоспособности")
	@Persist @DateString @DoDateString @Required
	public String getDateFrom() {return theDateFrom;}
	public void setDateFrom(String aDateFrom) {theDateFrom = aDateFrom;}
	
	/** Дата окончания нетрудоспособности */
	@Comment("Дата окончания нетрудоспособности")
	@Persist @DateString @DoDateString 
	public String getDateTo() {return theDateTo;}
	public void setDateTo(String aDateTo) {theDateTo = aDateTo;	}
	
	/** Режим нетрудоспособности */
	@Comment("Режим нетрудоспособности") 
	public Long getRegime() {return theRegime;}
	public void setRegime(Long aRegime) {theRegime = aRegime;}
	
	/** Пациент*/
	@Comment("Пациент")
	@Persist @Required
	public Long getPatient() {return thePatient;}
	public void setPatient(Long aCreateMedCase) {thePatient = aCreateMedCase;}

	/** Специалист */
	@Comment("Специалист")
	public Long getWorkFunction() {return theWorkFunction;}
	public void setWorkFunction(Long aWorkFunction) {theWorkFunction = aWorkFunction;}

	/** Информация о записи */
	@Comment("Информация о записи")
	@Persist
	public String getInfo() {return theInfo;}
	public void setInfo(String aInfo) {theInfo = aInfo;}


	/** Медико-социальная экспертная комиссия */
	@Comment("Медико-социальная экспертная комиссия")
	@Persist
	public Long getMedSocCommission() {return theMedSocCommission;}
	public void setMedSocCommission(Long aMedSocCommission) {theMedSocCommission = aMedSocCommission;}

	/** Случай нетрудоспособности */
	@Comment("Случай нетрудоспособности")
	@Persist
	public Long getDisabilityCase() {return theDisabilityCase;}
	public void setDisabilityCase(Long aDisabilityCase) {theDisabilityCase = aDisabilityCase;}

	/** Серия */
	@Comment("Серия")
	@Persist
	public String getSeries() {return theSeries;}
	public void setSeries (String aCloseSeries) {theSeries = aCloseSeries;}
	
	/** Номер */
	@Comment("Номер")
	@Persist 
	public String getNumber() {return theNumber;}
	public void setNumber(String aNumber) {theNumber = aNumber;}
	
	/** Причина нетрудоспособности */
	@Comment("Причина нетрудоспособности")
	@Persist @Required
	public Long getDisabilityReason() {return theDisabilityReason;}
	public void setDisabilityReason(Long aDisabilityReason) {theDisabilityReason = aDisabilityReason;}

	/** Причина закрытия */
	@Comment("Причина закрытия")
	@Persist
	public Long getCloseReason() {return theCloseReason;}
	public void setCloseReason(Long aCloseReason) {theCloseReason = aCloseReason;}

	/** Тип документа нетрудоспособности */
	@Comment("Тип документа нетрудоспособности")
	@Persist @Required
	public Long getDocumentType() {return theDocumentType;}
	public void setDocumentType(Long aDocumentType) {theDocumentType = aDocumentType;}

	/** Режим нетрудоспособности */
	@Comment("Режим нетрудоспособности")
	@Persist
	public Long getDisabilityRegime() {return theDisabilityRegime;}
	public void setDisabilityRegime(Long aDisabilityRegime) {theDisabilityRegime = aDisabilityRegime;}

	/** Первичность */
	@Comment("Первичность")
	@Persist @Required
	public Long getPrimarity() {return thePrimarity;}
	public void setPrimarity(Long aPrimarity) {thePrimarity = aPrimarity;}

	/** Дата выдачи */
	@Comment("Дата выдачи")
	@DateString @DoDateString @Persist @Required
	public String getIssueDate() {return theIssueDate;}
	public void setIssueDate(String aIssueDate) {theIssueDate = aIssueDate;}

	/** Серия документа нетрудоспособности по основному месту работы */
	@Comment("Серия документа нетрудоспособности по основному месту работы")
	@Persist
	public String getMainWorkDocumentSeries() {return theMainWorkDocumentSeries;}
	public void setMainWorkDocumentSeries(String aSeries) {theMainWorkDocumentSeries = aSeries;}
	/** Серия документа нетрудоспособности по основному месту работы */
	private String theMainWorkDocumentSeries;
	
	/** Номер документа нетрудоспособности по основному месту работы */
	@Comment("Номер документа нетрудоспособности по основному месту работы")
	@Persist
	public String getMainWorkDocumentNumber() {return theMainWorkDocumentNumber;}
	public void setMainWorkDocumentNumber(String aMainWorkDisabilityDocument) {theMainWorkDocumentNumber = aMainWorkDisabilityDocument;}

	/** Предполагаемая дата родов */
	@Comment("Предполагаемая дата родов")
	@DateString @DoDateString @Persist
	public String getSupposeBirthDate() {return theSupposeBirthDate;}
	public void setSupposeBirthDate(String aSupposeBirthDate) {theSupposeBirthDate = aSupposeBirthDate;}

	/** Больной по уходу */
	@Comment("Больной по уходу")
	@Persist
	public Long getNursedPatient() {return theNursedPatient;}
	public void setNursedPatient(Long aNursingPatientAge) {theNursedPatient = aNursingPatientAge;}
	
	/** Дата начала санаторного лечения */
	@Comment("Дата начала санаторного лечения")
	@DateString @DoDateString @Persist
	public String getSanatoriumDateFrom() {return theSanatoriumDateFrom;}
	public void setSanatoriumDateFrom(String aSanatoriumDateFrom) {theSanatoriumDateFrom = aSanatoriumDateFrom;}

	/** Дата окончания санаторного лечения */
	@Comment("Дата окончания санаторного лечения")
	@DateString @DoDateString @Persist
	public String getSanatoriumDateTo() {return theSanatoriumDateTo;}
	public void setSanatoriumDateTo(String aSanatoriumDateTo) {theSanatoriumDateTo = aSanatoriumDateTo;}

	/** Номер санаторной путевки */
	@Comment("Номер санаторной путевки")
	@Persist
	public String getSanatoriumTicketNumber() {return theSanatoriumTicketNumber;}
	public void setSanatoriumTicketNumber(String aSanatoriumTicketNumber) {theSanatoriumTicketNumber = aSanatoriumTicketNumber;}

	/** Место нахождения санатория */
	@Comment("Место нахождения санатория")
	@Persist
	public String getSanatoriumPlace() {return theSanatoriumPlace;}
	public void setSanatoriumPlace(String aSanatoriumPlace) {theSanatoriumPlace = aSanatoriumPlace;}
	
	/** МКБ10*/
	@Comment("МКБ10")
	@Persist 
	public Long getIdc10() {return theIdc10;}
	public void setIdc10(Long aNewProperty) {theIdc10 = aNewProperty;	}
	
	/** Справочник видов совмещения работ */
	@Comment("Справочник видов совмещения работ")
	@Persist 
	public Long getWorkComboType() {return theWorkComboType;}
	public void setWorkComboType(Long aCombo) {theWorkComboType = aCombo;}

	/** Диагноз текст */
	@Comment("Диагноз текст")
	@Persist 
	public String getDiagnosis() {return theDiagnosis;}
	public void setDiagnosis(String aDiagnosis) {theDiagnosis = aDiagnosis;	}


	/** Лечебно-профилактическое учреждение */
	@Comment("Лечебно-профилактическое учреждение")
	@Persist
	public Long getAnotherLpu() {return theAnotherLpu;}
	public void setAnotherLpu(Long aAnotherLpu) {theAnotherLpu = aAnotherLpu;}

	/** Предыдущий документ */
	@Comment("Предыдущий документ")
	@Persist
	public Long getPrevDocument() {return thePrevDocument;}
	public void setPrevDocument(Long aPrevDocument) {thePrevDocument = aPrevDocument;}

	/** Доп. рабочая функция */
	@Comment("Доп. рабочая функция")
	public Long getWorkFunctionAdd() {return theWorkFunctionAdd;}
	public void setWorkFunctionAdd(Long aWorkFunctionAdd) {theWorkFunctionAdd = aWorkFunctionAdd;}

	/** Доп. рабочая функция */
	private Long theWorkFunctionAdd;
	/** Предыдущий документ */
	private Long thePrevDocument;
	/** Лечебно-профилактическое учреждение */
	private Long theAnotherLpu;
	/** Дата окончания */
	private String theDateTo;
	/** Дата начала */
	private String theDateFrom;
	/** Информация о документе */
	private String theInfo;
	/** Пациент */
	private Long thePatient;
	/** Диагноз текст */
	private String theDiagnosis;
	/** Больной по уходу */
	private Long theNursedPatient;
	/** Дата начала санаторного лечения */
	private String theSanatoriumDateFrom;
	/** Дата окончания санаторного лечения */
	private String theSanatoriumDateTo;
	/** Место нахождения санатория */
	private String theSanatoriumPlace;
	/** МКБ10 */
	private Long theIdc10;
	/** Справочник видов совмещения работ */
	private Long theWorkComboType;
	/** Номер санаторной путевки */
	private String theSanatoriumTicketNumber;
	/** Медико-социальная экспертная комиссия */
	private Long theMedSocCommission;
	/** Случай нетрудоспособности */
	private Long theDisabilityCase;
	/** Серия */
	private String theSeries; 
	/** Номер */
	private String theNumber;
	/** Причина нетрудоспособности */
	private Long theDisabilityReason;
	/** Причина закрытия */
	private Long theCloseReason;
	/** Тип документа нетрудоспособности */
	private Long theDocumentType;
	/** Режим нетрудоспособности */
	private Long theDisabilityRegime;
	/** Первичность */
	private Long thePrimarity;
	/** Дата выдачи */
	private String theIssueDate;
	/** Номер документа нетрудоспособности по основному месту работы */
	private String theMainWorkDocumentNumber;
	/** Предполагаемая дата родов */
	private String theSupposeBirthDate;
	/** Специалист */
	private Long theWorkFunction;
	/** Режим нетрудоспособности */
	private Long theRegime;

	
	/** Поставлена на учет в ранние сроки беременности (до 12 недель) */
	@Comment("Поставлена на учет в ранние сроки беременности (до 12 недель)")
	public Boolean getEarlyPregnancyRegistration() {return theEarlyPregnancyRegistration;}
	public void setEarlyPregnancyRegistration(Boolean aEarlyPregnancyRegistration) {theEarlyPregnancyRegistration = aEarlyPregnancyRegistration;}
	/**
	 * Поставлена на учет в ранние сроки беременности (до 12 недель)
	 */
	private Boolean theEarlyPregnancyRegistration;

	/** Лицо по уходу 1*/
	@Comment("Лицо по уходу 1")
	public Long getNursingPerson1() {return theNursingPerson1;}
	public void setNursingPerson1(Long aNursingPerson1) {theNursingPerson1 = aNursingPerson1;}

	/** Лицо по уходу 2*/
	@Comment("Лицо по уходу 2")
	public Long getNursingPerson2() {return theNursingPerson2;}
	public void setNursingPerson2(Long aNursingPerson2) {theNursingPerson2 = aNursingPerson2;}

	/** Лицо по уходу 2*/
	private Long theNursingPerson2;
	/** Лицо по уходу 1*/
	private Long theNursingPerson1;
	/**
	 * Состоит на учете в службе занятости
	 */
	@Comment("Состоит на учете в службе занятости")
	public Boolean getPlacementService() {return thePlacementService;}
	public void setPlacementService(Boolean aPlacementService) {thePlacementService = aPlacementService;}
	/**
	 * Состоит на учете в службе занятости
	 */
	private Boolean thePlacementService;
}
