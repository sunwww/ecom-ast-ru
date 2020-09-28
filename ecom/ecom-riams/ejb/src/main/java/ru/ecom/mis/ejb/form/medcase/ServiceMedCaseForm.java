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
import ru.nuzmsh.forms.validator.validators.Mkb;
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
	public String getDateStart() {return theDateStart;}
	public void setDateStart(String aDateStart) {theDateStart = aDateStart;}
	
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
	private String theDateStart;
	/** Время исполнения */
	private String theTimeExecute;
	/** Количество мед. услуг */
	private Integer theMedServiceAmount;
	/** Мед. услуга */
	private Long theMedService;
	/** Заключительный диагноз по МКБ-10 */
	@Comment("Заключительный диагноз по МКБ-10")
	@Mkb 
	public Long getConcludingMkb() {return theConcludingMkb;}
	public void setConcludingMkb(Long aConcludingMkb) {theConcludingMkb = aConcludingMkb;}
	/** Заключительный диагноз */
	@Comment("Заключительный диагноз")
	public String getConcludingDiagnos() {return theConcludingDiagnos;}
	public void setConcludingDiagnos(String aConcludingDiagnos) {theConcludingDiagnos = aConcludingDiagnos;}

	/** Острота диагноза заключительного */
	@Comment("Острота диагноза клинического")
	public Long getConcludingActuity() {return theConcludingActuity;}
	public void setConcludingActuity(Long aClinicalActuity) {theConcludingActuity = aClinicalActuity;}
	/** Острота диагноза заключительного */
	private Long theConcludingActuity;
	/** Заключительный диагноз */
	private String theConcludingDiagnos;
	/** Заключительный диагноз по МКБ-10 */
	private Long theConcludingMkb;
	
	/** Результат визита */
	@Comment("Результат визита")
	@Persist
	public Long getVisitResult() {return theVisitResult;	}
	public void setVisitResult(Long aResult) {theVisitResult = aResult;}

	/** Цель визита */
	@Comment("Цель визита")
	@Persist
	public Long getVisitReason() {return theVisitReason;	}
	public void setVisitReason(Long aReason) {theVisitReason = aReason;	}
	/** Тип рабочего места обслуживания */
	@Comment("Тип рабочего места обслуживания")
	@Persist
	public Long getWorkPlaceType() {return theWorkPlaceType;}
	public void setWorkPlaceType(Long aWorkPlaceType) {theWorkPlaceType = aWorkPlaceType;	}
	
    /** Тип рабочего места обслуживания */
	private Long theWorkPlaceType;	
	/** Цель визита */
	private Long theVisitReason;
	/** Результат визита */
	private Long theVisitResult;


	/** Комментарий по услуге */
	@Comment("Комментарий по услуге")
	@Persist
	public String getServiceComment() {
		return theServiceComment;
	}
	public void setServiceComment(String aServiceComment) {
		theServiceComment = aServiceComment;
	}

	/** Комментарий по услуге */
	private String theServiceComment;
}
