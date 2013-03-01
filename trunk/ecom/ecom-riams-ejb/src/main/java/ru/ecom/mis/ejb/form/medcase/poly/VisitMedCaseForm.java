package ru.ecom.mis.ejb.form.medcase.poly;

import javax.persistence.OneToOne;

import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.interceptors.AEntityFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.AViewInterceptors;
import ru.ecom.mis.ejb.domain.medcase.Visit;
import ru.ecom.mis.ejb.domain.medcase.voc.VocAmbulance;
import ru.ecom.mis.ejb.domain.medcase.voc.VocVisitOutcome;
import ru.ecom.mis.ejb.form.medcase.ShortMedCaseForm;
import ru.ecom.mis.ejb.form.medcase.interceptor.DirectionViewInterceptor;
import ru.ecom.mis.ejb.form.patient.PatientForm;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.MaxDateCurrent;
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
public class VisitMedCaseForm extends ShortMedCaseForm {
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
    public void setDispRegistration(Long aVocDispanseryRegistration) {
        theDispRegistration = aVocDispanseryRegistration;
    }
    
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
	/** Бригада скорой помощи */
	@Comment("Бригада скорой помощи")
	@OneToOne
	public VocAmbulance getAmbulance() {return theAmbulance;}
	public void setAmbulance(VocAmbulance aAmbulance) {theAmbulance = aAmbulance;}

	/** Исход визита */
	@Comment("Исход визита")
	@OneToOne
	public VocVisitOutcome getVisitOutcome() {return theVisitOutcome;}
	public void setVisitOutcome(VocVisitOutcome aVisitOutcome) {theVisitOutcome = aVisitOutcome;}

	/** Исход визита */
	private VocVisitOutcome theVisitOutcome;
	/** Бригада скорой помощи */
	private VocAmbulance theAmbulance;
}
