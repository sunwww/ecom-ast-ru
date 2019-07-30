package ru.ecom.mis.ejb.form.medcase.poly;

import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.interceptors.AEntityFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.ASaveInterceptors;
import ru.ecom.ejb.services.entityform.interceptors.AViewInterceptors;
import ru.ecom.mis.ejb.domain.medcase.Visit;
import ru.ecom.mis.ejb.form.medcase.interceptor.DirectionViewInterceptor;
import ru.ecom.mis.ejb.form.medcase.interceptor.VisitSaveInterceptor;
import ru.ecom.mis.ejb.form.medcase.ticket.TicketMedCaseForm;
import ru.ecom.mis.ejb.form.patient.PatientForm;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.MaxDateCurrent;
import ru.nuzmsh.forms.validator.validators.Mkb;
import ru.nuzmsh.forms.validator.validators.Required;

/**
 * Форма визита
 * @author STkacheva
 */
@Comment("Визит")
@EntityForm
@EntityFormPersistance(clazz=Visit.class)
@WebTrail(comment = "Визит", nameProperties= "id", view="entityParentView-smo_visit.do", shortView="entityShortView-smo_visit.do")
@Parent(property="patient", parentForm= PatientForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/MedCase/Visit")
@AViewInterceptors(
		@AEntityFormInterceptor(DirectionViewInterceptor.class)
)
@ASaveInterceptors(
		@AEntityFormInterceptor(VisitSaveInterceptor.class)
)
public class VisitMedCaseForm extends TicketMedCaseForm {
	/** Количество выписанных льготных рецептов */
	@Comment("Количество выписанных льготных рецептов")
	@Persist
	public Integer getPrivilegeRecipeAmount() {return thePrivilegeRecipeAmount;	}
	public void setPrivilegeRecipeAmount(Integer aPrivilegeRecipeAmount) {thePrivilegeRecipeAmount = aPrivilegeRecipeAmount;	}

	/** Внешний направитель (ЛПУ) */
	@Comment("Внешний направитель (ЛПУ)")
	@Persist
	public Long getOrderLpu() {return theOrderLpu;}
	public void setOrderLpu(Long aOrderLpu) {theOrderLpu = aOrderLpu;}

	/** Результат визита */
	@Comment("Результат визита")
	@Persist @Required
	public Long getVisitResult() {return theVisitResult;	}
	public void setVisitResult(Long aResult) {theVisitResult = aResult;}

	/** Цель визита */
	@Comment("Цель визита")
	@Persist @Required
	public Long getVisitReason() {return theVisitReason;	}
	public void setVisitReason(Long aReason) {theVisitReason = aReason;	}

	/** Тип рабочего места обслуживания */
	@Comment("Тип рабочего места обслуживания")
	@Persist @Required
	public Long getWorkPlaceType() {return theWorkPlaceType;}
	public void setWorkPlaceType(Long aWorkPlaceType) {theWorkPlaceType = aWorkPlaceType;	}

	/** Рабочая функция направителя */
	@Comment("Рабочая функция направителя")
	@Persist
	public Long getOrderWorkFunction() {return theOrderWorkFunction;}
	public void setOrderWorkFunction(Long aNewProperty) {theOrderWorkFunction = aNewProperty;}

	 /** Диспансерный учет * */
    @Persist
    public Long getDispRegistration() {return theDispRegistration;}
    public void setDispRegistration(Long aVocDispanseryRegistration) {theDispRegistration = aVocDispanseryRegistration;}
    
    /** Дата следующего визита */
	@Comment("Дата следующего визита")
	@DateString @DoDateString @Persist @MaxDateCurrent
	public String getNextVisitDate() {
		return theNextVisitDate;
	}

	public void setNextVisitDate(String aNextVisitDate) {
		theNextVisitDate = aNextVisitDate;
	}
	/** Госпитализация (впервые, повторно) */
	@Comment("Госпитализация (впервые, повторно)")
	@Persist @Required
	public Long getHospitalization() {return theHospitalization;}
	public void setHospitalization(Long aHospitalization) {theHospitalization = aHospitalization;}
	/** Дата начала */
	@Comment("Дата начала")
	@Persist @DateString @DoDateString @MaxDateCurrent
	public String getDateStart() {return theDateStart;}
	public void setDateStart(String aNewProperty) {theDateStart = aNewProperty;}
	/** Дата начала */
	private String theDateStart;
	/** Госпитализация (впервые, повторно) */
	private Long theHospitalization;

	/** Дата следующего визита */
	private String theNextVisitDate;
    private Long theDispRegistration;
	/** Рабочая функция направителя */
	private Long theOrderWorkFunction;
	/** Тип рабочего места обслуживания */
	private Long theWorkPlaceType;
	/** Цель визита */
	private Long theVisitReason;
	/** Результат визита */
	private Long theVisitResult;
	/** Внешний направитель (ЛПУ) */
	private Long theOrderLpu;
	/** Количество выписанных льготных рецептов */
	private Integer thePrivilegeRecipeAmount;
	
	/** Количество оказанных услуг - AOI, 09.08.2016*/
	private String theMedserviceAmounts;
	public String getMedserviceAmounts() { return theMedserviceAmounts; }
	public void setMedserviceAmounts(String aMedserviceAmounts) { this.theMedserviceAmounts = aMedserviceAmounts; }
	
	/** Поток обслуживания */
	@Comment("Поток обслуживания")
	@Persist @Required
	public Long getServiceStream() {return theServiceStream;}
	public void setServiceStream(Long aServiceStream) {theServiceStream = aServiceStream;}

	/** Поток обслуживания */
	private Long theServiceStream;
	/** Отделение (текст) */
	@Comment("Отделение (текст)")
	@Persist
	public String getDepartmentInfo() {return theDepartmentInfo;}
	public void setDepartmentInfo(String aDepartmentInfo) {theDepartmentInfo = aDepartmentInfo;}
	/** Отделение */
	@Comment("Отделение")
	@Persist
	public Long getDepartment() {return theDepartment;}
	public void setDepartment(Long aDepartment) {theDepartment = aDepartment;}
	/** Отделение */
	private Long theDepartment;
	/** Отделение (текст) */
	private String theDepartmentInfo;	
	
	/** medServices */
	@Comment("medServices")
	public String getMedServices() {return theMedServices;}
	public void setMedServices(String aMedServices) {theMedServices = aMedServices;}

	/** medServices */
	private String theMedServices;
	/** Предварительный прием */
	@Comment("Предварительный прием")
	public Boolean getIsPreRecord() {return theIsPreRecord;}
	public void setIsPreRecord(Boolean aIsPreRecord) {theIsPreRecord = aIsPreRecord;}

	/** Предварительный прием */
	private Boolean theIsPreRecord;

	
	/**
	 * Рабочая функция исполнения
	 */
	@Comment("Рабочая функция исполнения")
	@Persist 
	public Long getWorkFunctionExecute() {return theWorkFunctionExecute;	}
	/** Рабочая функция исполнения */
	public void setWorkFunctionExecute(Long aNewProperty) {	theWorkFunctionExecute = aNewProperty;}

	/** Рабочая функция исполнения */
	private Long theWorkFunctionExecute;
	
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
	
}
