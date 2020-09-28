package ru.ecom.mis.ejb.form.medcase.poly;

import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.interceptors.*;
import ru.ecom.mis.ejb.domain.medcase.Visit;
import ru.ecom.mis.ejb.form.medcase.interceptor.DirectionPreCreateInterceptor;
import ru.ecom.mis.ejb.form.medcase.interceptor.DirectionSaveInterceptor;
import ru.ecom.mis.ejb.form.medcase.interceptor.DirectionViewInterceptor;
import ru.ecom.mis.ejb.form.medcase.ticket.TicketMedCaseForm;
import ru.ecom.mis.ejb.form.patient.PatientForm;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.MaxDateCurrent;
import ru.nuzmsh.forms.validator.validators.Mkb;
import ru.nuzmsh.forms.validator.validators.Required;

@Comment("Направление")
@EntityForm
@EntityFormPersistance(clazz= Visit.class)
@WebTrail(comment = "Направление", nameProperties= "id", view="entityParentView-smo_direction.do")
@Parent(property="patient", parentForm= PatientForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/MedCase/Direction")
@AParentPrepareCreateInterceptors(
		@AParentEntityFormInterceptor(DirectionPreCreateInterceptor.class)
)
@ACreateInterceptors(
		@AEntityFormInterceptor(DirectionSaveInterceptor.class)
)
@ASaveInterceptors(
		@AEntityFormInterceptor(DirectionSaveInterceptor.class)
)
@AViewInterceptors(
		@AEntityFormInterceptor(DirectionViewInterceptor.class)
)

public class DirectionMedCaseForm extends TicketMedCaseForm {
	
	/** Количество записей  */
	@Comment("Количество записей")
	@DateString @DoDateString
	public String getAnyDatePlan() {return theAnyDatePlan; }
	public void setAnyDatePlan(String aAnyDatePlan) {theAnyDatePlan = aAnyDatePlan;	}
	
	private String theAnyDatePlan;
	/** Количество записей  */
	@Comment("Количество записей")
	public Long getCountDays() {return theCountDays; }
	public void setCountDays(Long aCountDays) {theCountDays = aCountDays;	}

	private Long theCountDays;

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
	private Long theOrderLpu;



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
    
    private Long theDispRegistration;
	/** Рабочая функция направителя */
	private Long theOrderWorkFunction;
	/** Тип рабочего места обслуживания */
	private Long theWorkPlaceType;
	/** Цель визита */
	private Long theVisitReason;
	/** Результат визита */
	private Long theVisitResult;

	/** Количество выписанных льготных рецептов */
	private Integer thePrivilegeRecipeAmount;
	
	/** Планируемая дата исполнения */
	@Comment("Планируемая дата исполнения")
	@Persist @Required
	public Long getDatePlan() {return theDatePlan;	}
	public void setDatePlan(Long aNewProperty) {theDatePlan = aNewProperty;	}

	/** Планируемая рабочая функция исполнения */
	@Comment("Планируемая рабочая функция исполнения")
	@Persist @Required
	public Long getWorkFunctionPlan() {return theWorkFunctionPlan;}
	public void setWorkFunctionPlan(Long aNewProperty) {theWorkFunctionPlan = aNewProperty;}


	/** Планируемое время исполнения */
	@Comment("Планируемое время исполнения")
	@Persist @Required
	public Long getTimePlan() {return theTimePlan;}
	public void setTimePlan(Long aNewProperty) {	theTimePlan = aNewProperty;	}

	/** Планируемое время исполнения */
	private Long theTimePlan;
	/** Планируемая рабочая функция исполнения */
	private Long theWorkFunctionPlan;
	/** Планируемая дата исполнения */
	private Long theDatePlan;
	/** Поток обслуживания */
	@Comment("Поток обслуживания")
	@Persist @Required
	public Long getServiceStream() {return theServiceStream;}
	public void setServiceStream(Long aServiceStream) {theServiceStream = aServiceStream;}
	/** Поток обслуживания */
	private Long theServiceStream;	
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
	
	/** Дата начала */
	@Comment("Дата начала")
	@Persist @DateString @DoDateString @MaxDateCurrent
	public String getDateStart() {return theDateStart;}
	public void setDateStart(String aNewProperty) {theDateStart = aNewProperty;}
	/** Дата начала */
	private String theDateStart;
}
