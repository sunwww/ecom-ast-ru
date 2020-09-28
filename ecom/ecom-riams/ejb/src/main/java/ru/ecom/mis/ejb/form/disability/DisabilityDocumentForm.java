package ru.ecom.mis.ejb.form.disability;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.interceptors.AParentEntityFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.AParentPrepareCreateInterceptors;
import ru.ecom.mis.ejb.domain.disability.DisabilityDocument;
import ru.ecom.mis.ejb.form.disability.interceptors.DocumentPreCreate;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;

/**
 * Документ нетрудоспособности
 * @author oegorova,stkacheva
 */
@EntityForm
@EntityFormPersistance (clazz = DisabilityDocument.class)
@Comment("Документ нетрудоспособности")
@WebTrail(comment = "Документ нетрудоспособности", nameProperties= "info"
	, view="entityParentView-dis_document.do"
	, shortView="entityShortView-dis_document.do"
	)
@Parent(property="disabilityCase", parentForm=DisabilityCaseForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Disability/Case/Document")
@AParentPrepareCreateInterceptors(
        @AParentEntityFormInterceptor(DocumentPreCreate.class)
)
public class DisabilityDocumentForm extends IdEntityForm{


	private Boolean isELN;
	@Comment("Электронный")
	@Persist
	public Boolean getELN() {
		return isELN;
	}
	public void setELN(Boolean ELN) {
		isELN = ELN;
	}

	/** Первичность предыдущего листа нетрудоспособности  */
	@Comment("Первичность предыдущего листа нетрудоспособности ")
	public String getAnotherPrevPrimarity() {return theAnotherPrevPrimarity;}
	public void setAnotherPrevPrimarity(String aAnotherPrevPrimarity) {theAnotherPrevPrimarity = aAnotherPrevPrimarity;}
	/** Первичность предыдущего листа нетрудоспособности  */
	private String theAnotherPrevPrimarity ;

	/** Номер первичного листа нетрудоспособности другого ЛПУ*/
	@Comment("Номер первичного листа нетрудоспособности")
	public String getAnotherPrevNumber() {return theAnotherPrevNumber;}
	public void setAnotherPrevNumber(String aAnotherPrevNumber) {theAnotherPrevNumber= aAnotherPrevNumber;}
	/** Номер первичного листа нетрудоспособности */
	private String theAnotherPrevNumber;

	/** Дата экспорта */
	@Comment("Дата экспорта")
	@DateString @DoDateString @Persist
	public String getExportDate() {return theExportDate;}
	public void setExportDate(String aExportDate) {theExportDate = aExportDate;}
	/** Дата экспорта */
	private String theExportDate;
	
	/** Дефект экспорта */
	@Comment("Дефект экспорта")
	@Persist
	public String getExportDefect() {return theExportDefect;}
	public void setExportDefect(String aExportDefect) {theExportDefect = aExportDefect;	}
	/** Дефект экспорта */
	private String theExportDefect;
	
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

	/** Дата начала работы */
	@Comment("Дата начала работы")
	@DateString @DoDateString @Persist
	public String getBeginWorkDate() { return theBeginWorkDate;}
	public void setBeginWorkDate(String aBeginWorkDate) { this.theBeginWorkDate = aBeginWorkDate;}
	private String theBeginWorkDate;
	
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

	/** Признак недействительности документа */
	@Comment("Признак недействительности документа")
	@Persist
	public Boolean getNoActuality() {return theNoActuality;}
	public void setNoActuality(Boolean aNoActuality) {theNoActuality = aNoActuality;}

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

	/** Первичность (текст) */
	@Comment("Первичность (текст)")
	@Persist
	public String getPrimarityInfo() {return thePrimarityInfo;}
	public void setPrimarityInfo(String aPrimarityText) {thePrimarityInfo = aPrimarityText;}

	
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

	/** Пациент */
	@Comment("Пациент")
	@Persist
	public Long getPatient() {return thePatient;}
	public void setPatient(Long aPatient) {thePatient = aPatient;}

	/** Информация о документе */
	@Comment("Информация о документе")
	@Persist
	public String getInfo() {return theInfo;}
	public void setInfo(String aInfo) {theInfo = aInfo;}

	/** Дата начала */
	@Comment("Дата начала")
	@Persist @DateString @DoDateString
	public String getDateFrom() {return theDateFrom;}
	public void setDateFrom(String aDateFrom) {theDateFrom = aDateFrom;}

	/** Дата окончания */
	@Comment("Дата окончания")
	@Persist @DateString @DoDateString 
	public String getDateTo() {return theDateTo;}
	public void setDateTo(String aDateTo) {theDateTo = aDateTo;}

	/** Тип документа инфо */
	@Comment("Тип документа инфо")
	@Persist
	public String getDocumentTypeInfo() {return theDocumentTypeInfo;}
	public void setDocumentTypeInfo(String aDocumentTypeInfo) {theDocumentTypeInfo = aDocumentTypeInfo;}

	/** Режим нетрудоспособности */
	@Comment("Режим нетрудоспособности")
	public Long getRegime() {return theRegime;}
	public void setRegime(Long aRegime) {theRegime = aRegime;}

	/** Рабочая функция */
	@Comment("Рабочая функция")
	public Long getWorkFunction() {return theWorkFunction;}
	public void setWorkFunction(Long aWorkFunction) {theWorkFunction = aWorkFunction;}

	/** Пациент ФИО */
	@Comment("Пациент ФИО")
	@Persist
	public String getPatientFio() {return thePatientFio;}
	public void setPatientFio(String aPatientFio) {thePatientFio = aPatientFio;}

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

	/** Закрыт случай */
	@Comment("Закрыт случай")
	@Persist
	public Boolean getIsClose() {return theIsClose;}
	public void setIsClose(Boolean aIsClose) {theIsClose = aIsClose;}

	/** Статус */
	@Comment("Статус")
	@Persist @Required
	public Long getStatus() {return theStatus;}
	public void setStatus(Long aStatus) {theStatus = aStatus;}

	/** Статус */
	private Long theStatus;
	/** Закрыт случай */
	private Boolean theIsClose;
	/** Предыдущий документ */
	private Long thePrevDocument;
	/** Лечебно-профилактическое учреждение */
	private Long theAnotherLpu;
	/** Пациент ФИО */
	private String thePatientFio;
	/** Рабочая функция */
	private Long theWorkFunction;
	/** Режим нетрудоспособности */
	private Long theRegime;
	/** Тип документа инфо */
	private String theDocumentTypeInfo;
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
	/** Первичность (текст) */
	private String thePrimarityInfo;
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
	/** Признак недействительности документа */
	private Boolean theNoActuality;
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

	/**
	 * Дополнительная причина нетрудоспособности
	 */
	@Comment("Дополнительная причина нетрудоспособности")
	@Persist
	public Long getDisabilityReason2() {return theDisabilityReason2;}
	public void setDisabilityReason2(Long aDisabilityReason2) {theDisabilityReason2 = aDisabilityReason2;}
	/**
	 * Дополнительная причина нетрудоспособности
	 */
	private Long theDisabilityReason2;

	/**
	 * Дата начала госпитализации
	 */
	@Comment("Дата начала госпитализации")
	@Persist @DateString @DoDateString
	public String getHospitalizedFrom() {return theHospitalizedFrom;}
	public void setHospitalizedFrom(String aHospitalizedFrom) {theHospitalizedFrom = aHospitalizedFrom;}
	/**
	 * Дата начала госпитализации
	 */
	private String theHospitalizedFrom;
	/**
	 * Дата окончания госпитализации
	 */
	@Comment("Дата окончания госпитализации")	
	@Persist @DateString @DoDateString
	public String getHospitalizedTo() {return theHospitalizedTo;}
	public void setHospitalizedTo(String aHospitalizedTo) {theHospitalizedTo = aHospitalizedTo;}
	/**
	 * Дата окончания госпитализации
	 */
	private String theHospitalizedTo;
	/**
	 * ОГРН санатория или клиники НИИ
	 */
	@Comment("ОГРН санатория или клиники НИИ")	
	@Persist
	public String getSanatoriumOgrn() {return theSanatoriumOgrn;}
	public void setSanatoriumOgrn(String aSanatoriumOgrn) {theSanatoriumOgrn = aSanatoriumOgrn;}
	/**
	 * ОГРН санатория или клиники НИИ
	 */
	private String theSanatoriumOgrn;
	/**
	 * Код изменения причины нетрудоспособности
	 */
	@Comment("Код изменения причины нетрудоспособности")
	@Persist
	public Long getDisabilityReasonChange() {return theDisabilityReasonChange;}
	public void setDisabilityReasonChange(Long aDisabilityReasonChange) {theDisabilityReasonChange = aDisabilityReasonChange;}
	/**
	 * Код изменения причины нетрудоспособности
	 */
	private Long theDisabilityReasonChange;	
	/** № истории болезни */
	@Comment("№ истории болезни")
	@Persist
	public String getHospitalizedNumber() {return theHospitalizedNumber;}
	public void setHospitalizedNumber(String aHospitalizedNumber) {theHospitalizedNumber = aHospitalizedNumber;}

	/** № истории болезни */
	private String theHospitalizedNumber;
	
	/** Пользователь, создавший документ */
	@Comment("Пользователь, создавший документ")
	@Persist
	public String getCreateUsername() {return theCreateUsername;}
	public void setCreateUsername(String aUsernameCreate) {theCreateUsername = aUsernameCreate;}

	/** Дата создания */
	@Comment("Дата создания")
	@Persist @DateString @DoDateString
	public String getCreateDate() {return theCreateDate;}
	public void setCreateDate(String aDateCreate) {theCreateDate = aDateCreate;}

	/** Пользователь, редактировавший документ */
	@Comment("Пользователь, редактировавший документ")
	@Persist
	public String getEditUsername() {return theEditUsername;}
	public void setEditUsername(String aUsernameEdit) {theEditUsername = aUsernameEdit;}

	/** Дата редактирования */
	@Comment("Дата редактирования")
	@Persist @DateString @DoDateString
	public String getEditDate() {return theEditDate;}
	public void setEditDate(String aDateEdit) {theEditDate = aDateEdit;}

	/** Дата редактирования */
	private String theEditDate;
	/** Пользователь, редактировавший документ */
	private String theEditUsername;
	/** Дата создания */
	private String theCreateDate;
	/** Пользователь, создавший документ */
	private String theCreateUsername;
	
	/** Место работы */
	@Comment("Место работы")
	@Persist
	public String getJob() {return theJob;}
	public void setJob(String aJob) {theJob = aJob;}

	/** Место работы */
	private String theJob;
	
	/** Обновить место работы */
	@Comment("Обновить место работы")
	public Boolean getIsUpdateWork() {return theIsUpdateWork;}
	public void setIsUpdateWork(Boolean aIsUpdateWork) {theIsUpdateWork = aIsUpdateWork;}

	/** Обновить место работы */
	private Boolean theIsUpdateWork;
	
	/** Специалист 2 */
	@Comment("Специалист 2")
	public Long getWorkFunctionAdd() {return theWorkFunctionAdd;}
	public void setWorkFunctionAdd(Long aWorkFunctionAdd) {theWorkFunctionAdd = aWorkFunctionAdd;}

	/** Специалист 2 */
	private Long theWorkFunctionAdd;
	/** Заключительный диагноз */
	@Comment("Заключительный диагноз")
	@Persist
	public Long getIdc10Final() {return theIdc10Final;}
	public void setIdc10Final(Long aIdc10Final) {theIdc10Final = aIdc10Final;}

	/** Заключительный диагноз */
	private Long theIdc10Final;	
	/** Диагноз заключительный */
	@Comment("Диагноз заключительный")
	public String getDiagnosisFinal() {return theDiagnosisFinal;}
	public void setDiagnosisFinal(String aDiagnosisFinal) {theDiagnosisFinal = aDiagnosisFinal;}

	/** Диагноз заключительный */
	private String theDiagnosisFinal;
	/** Продолжительность */
	@Comment("Продолжительность")
	public String getDuration() {return theDuration;}
	public void setDuration(String aDuration) {theDuration = aDuration;}
	/** Продолжительность */
	private String theDuration;
	
	/** Причина закрытия инфо */
	@Comment("Причина закрытия инфо")
	public String getCloseReasonInfo() {return theCloseReasonInfo;}
	public void setCloseReasonInfo(String aCloseReasonInfo) {theCloseReasonInfo = aCloseReasonInfo;}

	/** Причина закрытия инфо */
	private String theCloseReasonInfo;
	
	/** Адрес пациента */
	@Comment("Адрес пациента")
	//@Persist
	public String getPatientAddress() {
		return thePatientAddress;
	}

	public void setPatientAddress(String aPatientAddress) {
		thePatientAddress = aPatientAddress;
	}

	/** Адрес пациента */
	private String thePatientAddress;
	
	/** Номер БЛ выданный др. ЛПУ */
	@Comment("Номер БЛ выданный др. ЛПУ")
	public String getOtherNumber() {
		return theOtherNumber;
	}

	public void setOtherNumber(String aOtherNumber) {
		theOtherNumber = aOtherNumber;
	}

	/** Номер БЛ выданный др. ЛПУ */
	private String theOtherNumber;
	
	/** Дубликат */
	@Comment("Дубликат")
	@Persist
	public Long getDuplicate() {return theDuplicate;}
	public void setDuplicate(Long aDuplicate) {theDuplicate = aDuplicate;}

	/** Дубликат */
	private Long theDuplicate;
	
	/** Статус информация */
	@Comment("Статус информация")
	@Persist
	public String getStatusInfo() {return theStatusInfo;}
	public void setStatusInfo(String aStatusInfo) {theStatusInfo = aStatusInfo;}

	/** Статус информация */
	private String theStatusInfo;
	
	/** Дата иное */
	@Comment("Дата иное")
	@Persist @DateString @DoDateString
	public String getOtherCloseDate() {return theOtherCloseDate;}
	public void setOtherCloseDate(String aOtherCloseDate) {theOtherCloseDate = aOtherCloseDate;}

	/** Дата иное */
	private String theOtherCloseDate;

	/** Статус экспорта в ФСС */
	@Comment("Статус экспорта в ФСС")
	public String getExportStatus() {return theExportStatus;}
	public void setExportStatus(String aExportStatus) {theExportStatus = aExportStatus;}
	/** Статус экспорта в ФСС */
	private String theExportStatus ;

	/** Возраст пациента на дату начала случая */
	@Comment("Возраст пациента на дату начала случая")
	public String getPatientAgeYear() {return thePatientAgeYear;}
	public void setPatientAgeYear(String aPatientAgeYear) {thePatientAgeYear = aPatientAgeYear;}
	/** Возраст пациента на дату начала случая */
	private String thePatientAgeYear ;


}
