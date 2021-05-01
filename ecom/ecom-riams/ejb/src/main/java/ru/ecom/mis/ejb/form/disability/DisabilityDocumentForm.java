package ru.ecom.mis.ejb.form.disability;

import lombok.Setter;
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
@Setter
public class DisabilityDocumentForm extends IdEntityForm{


	private Boolean eln;
	@Comment("Электронный")
	@Persist
	public Boolean getEln() {
		return eln;
	}

	/** Первичность предыдущего листа нетрудоспособности  */
	@Comment("Первичность предыдущего листа нетрудоспособности ")
	public String getAnotherPrevPrimarity() {return anotherPrevPrimarity;}
	/** Первичность предыдущего листа нетрудоспособности  */
	private String anotherPrevPrimarity ;

	/** Номер первичного листа нетрудоспособности другого ЛПУ*/
	@Comment("Номер первичного листа нетрудоспособности")
	public String getAnotherPrevNumber() {return anotherPrevNumber;}
	/** Номер первичного листа нетрудоспособности */
	private String anotherPrevNumber;

	/** Дата экспорта */
	@Comment("Дата экспорта")
	@DateString @DoDateString @Persist
	public String getExportDate() {return exportDate;}
	/** Дата экспорта */
	private String exportDate;
	
	/** Дефект экспорта */
	@Comment("Дефект экспорта")
	@Persist
	public String getExportDefect() {return exportDefect;}
	/** Дефект экспорта */
	private String exportDefect;
	
	/** Медико-социальная экспертная комиссия */
	@Comment("Медико-социальная экспертная комиссия")
	@Persist
	public Long getMedSocCommission() {return medSocCommission;}

	/** Случай нетрудоспособности */
	@Comment("Случай нетрудоспособности")
	@Persist
	public Long getDisabilityCase() {return disabilityCase;}

	/** Серия */
	@Comment("Серия")
	@Persist
	public String getSeries() {return series;}

	/** Номер */
	@Comment("Номер")
	@Persist
	public String getNumber() {return number;}

	/** Дата начала работы */
	@Comment("Дата начала работы")
	@DateString @DoDateString @Persist
	public String getBeginWorkDate() { return beginWorkDate;}
	private String beginWorkDate;
	
	/** Причина нетрудоспособности */
	@Comment("Причина нетрудоспособности")
	@Persist @Required
	public Long getDisabilityReason() {return disabilityReason;}

	/** Причина закрытия */
	@Comment("Причина закрытия")
	@Persist
	public Long getCloseReason() {return closeReason;}

	/** Признак недействительности документа */
	@Comment("Признак недействительности документа")
	@Persist
	public Boolean getNoActuality() {return noActuality;}

	/** Тип документа нетрудоспособности */
	@Comment("Тип документа нетрудоспособности")
	@Persist @Required
	public Long getDocumentType() {return documentType;}

	/** Режим нетрудоспособности */
	@Comment("Режим нетрудоспособности")
	@Persist
	public Long getDisabilityRegime() {return disabilityRegime;}

	/** Первичность */
	@Comment("Первичность")
	@Persist @Required
	public Long getPrimarity() {return primarity;}

	/** Дата выдачи */
	@Comment("Дата выдачи")
	@DateString @DoDateString @Persist @Required
	public String getIssueDate() {return issueDate;}

	/** Серия документа нетрудоспособности по основному месту работы */
	@Comment("Серия документа нетрудоспособности по основному месту работы")
	@Persist
	public String getMainWorkDocumentSeries() {return mainWorkDocumentSeries;}
	/** Серия документа нетрудоспособности по основному месту работы */
	private String mainWorkDocumentSeries;
	
	/** Номер документа нетрудоспособности по основному месту работы */
	@Comment("Номер документа нетрудоспособности по основному месту работы")
	@Persist
	public String getMainWorkDocumentNumber() {return mainWorkDocumentNumber;}

	/** Предполагаемая дата родов */
	@Comment("Предполагаемая дата родов")
	@DateString @DoDateString @Persist
	public String getSupposeBirthDate() {return supposeBirthDate;}

	/** Больной по уходу */
	@Comment("Больной по уходу")
	@Persist
	public Long getNursedPatient() {return nursedPatient;}

	/** Дата начала санаторного лечения */
	@Comment("Дата начала санаторного лечения")
	@DateString @DoDateString @Persist
	public String getSanatoriumDateFrom() {return sanatoriumDateFrom;}

	/** Дата окончания санаторного лечения */
	@Comment("Дата окончания санаторного лечения")
	@DateString @DoDateString @Persist
	public String getSanatoriumDateTo() {return sanatoriumDateTo;}

	/** Номер санаторной путевки */
	@Comment("Номер санаторной путевки")
	@Persist
	public String getSanatoriumTicketNumber() {return sanatoriumTicketNumber;}

	/** Место нахождения санатория */
	@Comment("Место нахождения санатория")
	@Persist
	public String getSanatoriumPlace() {return sanatoriumPlace;}

	/** МКБ10*/
	@Comment("МКБ10")
	@Persist 
	public Long getIdc10() {return idc10;}

	/** Первичность (текст) */
	@Comment("Первичность (текст)")
	@Persist
	public String getPrimarityInfo() {return primarityInfo;}

	
	/** Справочник видов совмещения работ */
	@Comment("Справочник видов совмещения работ")
	@Persist 
	public Long getWorkComboType() {return workComboType;}

	/** Диагноз текст */
	@Comment("Диагноз текст")
	@Persist 
	public String getDiagnosis() {return diagnosis;}

	/** Пациент */
	@Comment("Пациент")
	@Persist
	public Long getPatient() {return patient;}

	/** Информация о документе */
	@Comment("Информация о документе")
	@Persist
	public String getInfo() {return info;}

	/** Дата начала */
	@Comment("Дата начала")
	@Persist @DateString @DoDateString
	public String getDateFrom() {return dateFrom;}

	/** Дата окончания */
	@Comment("Дата окончания")
	@Persist @DateString @DoDateString 
	public String getDateTo() {return dateTo;}

	/** Тип документа инфо */
	@Comment("Тип документа инфо")
	@Persist
	public String getDocumentTypeInfo() {return documentTypeInfo;}

	/** Режим нетрудоспособности */
	@Comment("Режим нетрудоспособности")
	public Long getRegime() {return regime;}

	/** Рабочая функция */
	@Comment("Рабочая функция")
	public Long getWorkFunction() {return workFunction;}

	/** Пациент ФИО */
	@Comment("Пациент ФИО")
	@Persist
	public String getPatientFio() {return patientFio;}

	/** Лечебно-профилактическое учреждение */
	@Comment("Лечебно-профилактическое учреждение")
	@Persist
	public Long getAnotherLpu() {return anotherLpu;}

	/** Предыдущий документ */
	@Comment("Предыдущий документ")
	@Persist
	public Long getPrevDocument() {return prevDocument;}

	/** Закрыт случай */
	@Comment("Закрыт случай")
	@Persist
	public Boolean getIsClose() {return isClose;}

	/** Статус */
	@Comment("Статус")
	@Persist @Required
	public Long getStatus() {return status;}

	/** Статус */
	private Long status;
	/** Закрыт случай */
	private Boolean isClose;
	/** Предыдущий документ */
	private Long prevDocument;
	/** Лечебно-профилактическое учреждение */
	private Long anotherLpu;
	/** Пациент ФИО */
	private String patientFio;
	/** Рабочая функция */
	private Long workFunction;
	/** Режим нетрудоспособности */
	private Long regime;
	/** Тип документа инфо */
	private String documentTypeInfo;
	/** Дата окончания */
	private String dateTo;
	/** Дата начала */
	private String dateFrom;
	/** Информация о документе */
	private String info;
	/** Пациент */
	private Long patient;
	/** Диагноз текст */
	private String diagnosis;
	/** Больной по уходу */
	private Long nursedPatient;
	/** Дата начала санаторного лечения */
	private String sanatoriumDateFrom;
	/** Дата окончания санаторного лечения */
	private String sanatoriumDateTo;
	/** Место нахождения санатория */
	private String sanatoriumPlace;
	/** МКБ10 */
	private Long idc10;
	/** Первичность (текст) */
	private String primarityInfo;
	/** Справочник видов совмещения работ */
	private Long workComboType;
	/** Номер санаторной путевки */
	private String sanatoriumTicketNumber;
	/** Медико-социальная экспертная комиссия */
	private Long medSocCommission;
	/** Случай нетрудоспособности */
	private Long disabilityCase;
	/** Серия */
	private String series; 
	/** Номер */
	private String number;
	/** Причина нетрудоспособности */
	private Long disabilityReason;
	/** Причина закрытия */
	private Long closeReason;
	/** Признак недействительности документа */
	private Boolean noActuality;
	/** Тип документа нетрудоспособности */
	private Long documentType;
	/** Режим нетрудоспособности */
	private Long disabilityRegime;
	/** Первичность */
	private Long primarity;
	/** Дата выдачи */
	private String issueDate;
	/** Номер документа нетрудоспособности по основному месту работы */
	private String mainWorkDocumentNumber;
	/** Предполагаемая дата родов */
	private String supposeBirthDate;

	/**
	 * Дополнительная причина нетрудоспособности
	 */
	@Comment("Дополнительная причина нетрудоспособности")
	@Persist
	public Long getDisabilityReason2() {return disabilityReason2;}
	/**
	 * Дополнительная причина нетрудоспособности
	 */
	private Long disabilityReason2;

	/**
	 * Дата начала госпитализации
	 */
	@Comment("Дата начала госпитализации")
	@Persist @DateString @DoDateString
	public String getHospitalizedFrom() {return hospitalizedFrom;}
	/**
	 * Дата начала госпитализации
	 */
	private String hospitalizedFrom;
	/**
	 * Дата окончания госпитализации
	 */
	@Comment("Дата окончания госпитализации")	
	@Persist @DateString @DoDateString
	public String getHospitalizedTo() {return hospitalizedTo;}
	/**
	 * Дата окончания госпитализации
	 */
	private String hospitalizedTo;
	/**
	 * ОГРН санатория или клиники НИИ
	 */
	@Comment("ОГРН санатория или клиники НИИ")	
	@Persist
	public String getSanatoriumOgrn() {return sanatoriumOgrn;}
	/**
	 * ОГРН санатория или клиники НИИ
	 */
	private String sanatoriumOgrn;
	/**
	 * Код изменения причины нетрудоспособности
	 */
	@Comment("Код изменения причины нетрудоспособности")
	@Persist
	public Long getDisabilityReasonChange() {return disabilityReasonChange;}
	/**
	 * Код изменения причины нетрудоспособности
	 */
	private Long disabilityReasonChange;	
	/** № истории болезни */
	@Comment("№ истории болезни")
	@Persist
	public String getHospitalizedNumber() {return hospitalizedNumber;}

	/** № истории болезни */
	private String hospitalizedNumber;
	
	/** Пользователь, создавший документ */
	@Comment("Пользователь, создавший документ")
	@Persist
	public String getCreateUsername() {return createUsername;}

	/** Дата создания */
	@Comment("Дата создания")
	@Persist @DateString @DoDateString
	public String getCreateDate() {return createDate;}

	/** Пользователь, редактировавший документ */
	@Comment("Пользователь, редактировавший документ")
	@Persist
	public String getEditUsername() {return editUsername;}

	/** Дата редактирования */
	@Comment("Дата редактирования")
	@Persist @DateString @DoDateString
	public String getEditDate() {return editDate;}

	/** Дата редактирования */
	private String editDate;
	/** Пользователь, редактировавший документ */
	private String editUsername;
	/** Дата создания */
	private String createDate;
	/** Пользователь, создавший документ */
	private String createUsername;
	
	/** Место работы */
	@Comment("Место работы")
	@Persist
	public String getJob() {return job;}

	/** Место работы */
	private String job;
	
	/** Обновить место работы */
	@Comment("Обновить место работы")
	public Boolean getIsUpdateWork() {return isUpdateWork;}

	/** Обновить место работы */
	private Boolean isUpdateWork;
	
	/** Специалист 2 */
	@Comment("Специалист 2")
	public Long getWorkFunctionAdd() {return workFunctionAdd;}

	/** Специалист 2 */
	private Long workFunctionAdd;
	/** Заключительный диагноз */
	@Comment("Заключительный диагноз")
	@Persist
	public Long getIdc10Final() {return idc10Final;}

	/** Заключительный диагноз */
	private Long idc10Final;	
	/** Диагноз заключительный */
	@Comment("Диагноз заключительный")
	public String getDiagnosisFinal() {return diagnosisFinal;}

	/** Диагноз заключительный */
	private String diagnosisFinal;
	/** Продолжительность */
	@Comment("Продолжительность")
	public String getDuration() {return duration;}
	/** Продолжительность */
	private String duration;
	
	/** Причина закрытия инфо */
	@Comment("Причина закрытия инфо")
	public String getCloseReasonInfo() {return closeReasonInfo;}

	/** Причина закрытия инфо */
	private String closeReasonInfo;
	
	/** Адрес пациента */
	@Comment("Адрес пациента")
	//@Persist
	public String getPatientAddress() {
		return patientAddress;
	}

	/** Адрес пациента */
	private String patientAddress;
	
	/** Номер БЛ выданный др. ЛПУ */
	@Comment("Номер БЛ выданный др. ЛПУ")
	public String getOtherNumber() {
		return otherNumber;
	}

	/** Номер БЛ выданный др. ЛПУ */
	private String otherNumber;
	
	/** Дубликат */
	@Comment("Дубликат")
	@Persist
	public Long getDuplicate() {return duplicate;}
	/** Дубликат */
	private Long duplicate;
	
	/** Статус информация */
	@Comment("Статус информация")
	@Persist
	public String getStatusInfo() {return statusInfo;}

	/** Статус информация */
	private String statusInfo;
	
	/** Дата иное */
	@Comment("Дата иное")
	@Persist @DateString @DoDateString
	public String getOtherCloseDate() {return otherCloseDate;}

	/** Дата иное */
	private String otherCloseDate;

	/** Статус экспорта в ФСС */
	@Comment("Статус экспорта в ФСС")
	public String getExportStatus() {return exportStatus;}
	/** Статус экспорта в ФСС */
	private String exportStatus ;

	/** Возраст пациента на дату начала случая */
	@Comment("Возраст пациента на дату начала случая")
	public String getPatientAgeYear() {return patientAgeYear;}
	/** Возраст пациента на дату начала случая */
	private String patientAgeYear ;


}
