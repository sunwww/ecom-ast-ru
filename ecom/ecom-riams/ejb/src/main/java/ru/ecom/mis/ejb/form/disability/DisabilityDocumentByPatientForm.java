package ru.ecom.mis.ejb.form.disability;

import lombok.Setter;
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
@Setter
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
	public String getDateFrom() {return dateFrom;}

	/** Дата окончания нетрудоспособности */
	@Comment("Дата окончания нетрудоспособности")
	@Persist @DateString @DoDateString 
	public String getDateTo() {return dateTo;}

	/** Режим нетрудоспособности */
	@Comment("Режим нетрудоспособности") 
	public Long getRegime() {return regime;}

	/** Пациент*/
	@Comment("Пациент")
	@Persist @Required
	public Long getPatient() {return patient;}

	/** Специалист */
	@Comment("Специалист")
	public Long getWorkFunction() {return workFunction;}

	/** Информация о записи */
	@Comment("Информация о записи")
	@Persist
	public String getInfo() {return info;}


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

	/** Причина нетрудоспособности */
	@Comment("Причина нетрудоспособности")
	@Persist @Required
	public Long getDisabilityReason() {return disabilityReason;}

	/** Причина закрытия */
	@Comment("Причина закрытия")
	@Persist
	public Long getCloseReason() {return closeReason;}

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

	/** Справочник видов совмещения работ */
	@Comment("Справочник видов совмещения работ")
	@Persist 
	public Long getWorkComboType() {return workComboType;}

	/** Диагноз текст */
	@Comment("Диагноз текст")
	@Persist 
	public String getDiagnosis() {return diagnosis;}


	/** Лечебно-профилактическое учреждение */
	@Comment("Лечебно-профилактическое учреждение")
	@Persist
	public Long getAnotherLpu() {return anotherLpu;}

	/** Предыдущий документ */
	@Comment("Предыдущий документ")
	@Persist
	public Long getPrevDocument() {return prevDocument;}

	/** Доп. рабочая функция */
	@Comment("Доп. рабочая функция")
	public Long getWorkFunctionAdd() {return workFunctionAdd;}

	/** Доп. рабочая функция */
	private Long workFunctionAdd;
	/** Предыдущий документ */
	private Long prevDocument;
	/** Лечебно-профилактическое учреждение */
	private Long anotherLpu;
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
	/** Специалист */
	private Long workFunction;
	/** Режим нетрудоспособности */
	private Long regime;

	
	/** Поставлена на учет в ранние сроки беременности (до 12 недель) */
	@Comment("Поставлена на учет в ранние сроки беременности (до 12 недель)")
	public Boolean getEarlyPregnancyRegistration() {return earlyPregnancyRegistration;}
	/**
	 * Поставлена на учет в ранние сроки беременности (до 12 недель)
	 */
	private Boolean earlyPregnancyRegistration;

	/** Лицо по уходу 1*/
	@Comment("Лицо по уходу 1")
	public Long getNursingPerson1() {return nursingPerson1;}

	/** Лицо по уходу 2*/
	@Comment("Лицо по уходу 2")
	public Long getNursingPerson2() {return nursingPerson2;}

	/** Лицо по уходу 2*/
	private Long nursingPerson2;
	/** Лицо по уходу 1*/
	private Long nursingPerson1;
	/**
	 * Состоит на учете в службе занятости
	 */
	@Comment("Состоит на учете в службе занятости")
	public Boolean getPlacementService() {return placementService;}
	/**
	 * Состоит на учете в службе занятости
	 */
	private Boolean placementService;
}
