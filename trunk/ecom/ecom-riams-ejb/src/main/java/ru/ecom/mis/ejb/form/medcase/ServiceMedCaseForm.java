package ru.ecom.mis.ejb.form.medcase;

import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.interceptors.AParentEntityFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.AParentPrepareCreateInterceptors;
import ru.ecom.mis.ejb.domain.medcase.ServiceMedCase;
import ru.ecom.mis.ejb.form.medcase.hospital.interceptors.ServiceMedCasePreCreateInterceptor;
import ru.ecom.mis.ejb.form.medcase.ticket.TicketMedCaseForm;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.transforms.DoIntegerString;
import ru.nuzmsh.forms.validator.transforms.DoTimeString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.IntegerString;
import ru.nuzmsh.forms.validator.validators.MaxDateCurrent;
import ru.nuzmsh.forms.validator.validators.Required;
import ru.nuzmsh.forms.validator.validators.TimeString;

@EntityForm
@EntityFormPersistance(clazz = ServiceMedCase.class)
@Comment("Мед.услуга")
@WebTrail(comment = "Мед.услуга", nameProperties= "id"
	, view="entityParentView-smo_medService.do"
	, list = "entityParentList-smo_medService.do")
@Parent(property="parent", parentForm=MedCaseForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/MedCase/MedService")
@AParentPrepareCreateInterceptors(
        @AParentEntityFormInterceptor(ServiceMedCasePreCreateInterceptor.class)
)
public class ServiceMedCaseForm extends TicketMedCaseForm {
	/** Мед. услуга */
	@Comment("Мед. услуга")
	@Persist @Required
	public Long getMedService() {return theMedService;}
	public void setMedService(Long aMedService) {theMedService = aMedService;}
	
	/** Количество мед. услуг */
	@Comment("Количество мед. услуг")
	@Persist @Required @DoIntegerString @IntegerString
	public Integer getMedServiceAmount() {return theMedServiceAmount;}
	public void setMedServiceAmount(Integer aMedServiceAmount) {theMedServiceAmount = aMedServiceAmount;}

	/** Штамп времени исполнения */
	@Comment("Время исполнения")
	@Persist @TimeString @DoTimeString @Required
	public String getTimeExecute() {return theTimeExecute;	}
	public void setTimeExecute(String aNewProperty) {theTimeExecute = aNewProperty;}

	/** Дата выполнения */
	@Comment("Дата выполнения")
	@Persist @DateString @DoDateString @Required @MaxDateCurrent
	public String getDateExecute() {return theDateExecute;}
	public void setDateExecute(String aDateExecute) {theDateExecute = aDateExecute;}
	
	/**
	 * Рабочая функция исполнения
	 */
	@Comment("Рабочая функция исполнения")
	@Persist @Required
	public Long getWorkFunctionExecute() {return theWorkFunctionExecute;	}
	public void setWorkFunctionExecute(Long aNewProperty) {	theWorkFunctionExecute = aNewProperty;}
	

	/** Поток обслуживания */
	@Comment("Поток обслуживания")
	@Persist 
	public Long getServiceStream() {return theServiceStream;}
	public void setServiceStream(Long aServiceStream) {theServiceStream = aServiceStream;}

	/** Информация о мед.услуге */
	@Comment("Информация о мед.услуге")
	@Persist
	public String getMedServiceInfo() {return theMedServiceInfo;}
	public void setMedServiceInfo(String aMedServiceInfo) {theMedServiceInfo = aMedServiceInfo;}

	/** Категория */
	@Comment("Категория")
	@Persist
	public Long getCategoryMedService() {return theCategoryMedService;}
	public void setCategoryMedService(Long aCategory) {theCategoryMedService = aCategory;}

	/** Категория информация */
	@Comment("Категория информация")
	@Persist
	public String getCategoryMedServiceInfo() {return theCategoryMedServiceInfo;}
	public void setCategoryMedServiceInfo(String aCategoryInfo) {theCategoryMedServiceInfo = aCategoryInfo;}

	/** Протокол */
	@Comment("Протокол")
	public String getRecord() {return theRecord;}
	public void setRecord(String aRecord) {theRecord = aRecord;}

	/** Протокол */
	private String theRecord;
	/** Категория информация */
	private String theCategoryMedServiceInfo;
	/** Категория */
	private Long theCategoryMedService;
	/** Информация о мед.услуге */
	private String theMedServiceInfo;
	/** Поток обслуживания */
	private Long theServiceStream;
	/** Рабочая функция исполнения */
	private Long theWorkFunctionExecute;
	/** Дата выполнения */
	private String theDateExecute;
	/** Время исполнения */
	private String theTimeExecute;
	/** Количество мед. услуг */
	private Integer theMedServiceAmount;
	/** Мед. услуга */
	private Long theMedService;

}
