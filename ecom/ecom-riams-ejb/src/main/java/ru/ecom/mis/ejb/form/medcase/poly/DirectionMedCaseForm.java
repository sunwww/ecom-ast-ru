package ru.ecom.mis.ejb.form.medcase.poly;

import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.medcase.Visit;
import ru.ecom.mis.ejb.form.medcase.ShortMedCaseForm;
import ru.ecom.mis.ejb.form.patient.PatientForm;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.transforms.DoTimeString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;
import ru.nuzmsh.forms.validator.validators.TimeString;

@Comment("Направление")
@EntityForm
@EntityFormPersistance(clazz= Visit.class)
@WebTrail(comment = "Направление", nameProperties= "id", view="entityParentView-smo_direction.do")
@Parent(property="patient", parentForm= PatientForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/MedCase/Direction")
public class DirectionMedCaseForm extends ShortMedCaseForm {
	/** Количество выписанных льготных рецептов */
	@Comment("Количество выписанных льготных рецептов")
	@Persist
	public Integer getPrivilegeRecipeAmount() {return thePrivilegeRecipeAmount;	}
	public void setPrivilegeRecipeAmount(Integer aPrivilegeRecipeAmount) {thePrivilegeRecipeAmount = aPrivilegeRecipeAmount;	}

	/** Внутренний направитель (сотрудник) */
	@Comment("Внутренний направитель (сотрудник)")
	@Persist
	public Long getOrderWorker() {return theOrderWorker;	}
	public void setOrderWorker(Long aOrderWorker) {theOrderWorker = aOrderWorker;	}

	/** Внешний направитель (ЛПУ) */
	@Comment("Внешний направитель (ЛПУ)")
	@Persist
	public Long getOrderLpu() {return theOrderLpu;}
	public void setOrderLpu(Long aOrderLpu) {theOrderLpu = aOrderLpu;}



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
	
	/** Планируемая дата исполнения (текст) */
	@Comment("Планируемая дата исполнения (текст)")
	@Persist
	public String getDatePlanText() {return theDatePlanText;}
	public void setDatePlanText(String aDatePlanText) {theDatePlanText = aDatePlanText;	}

	/** Планируемое время исполнения (текст) */
	@Comment("Планируемое время исполнения (текст)")
	@Persist
	public String getTimePlanText() {return theTimePlanText;	}
	public void setTimePlanText(String aTimePlanText) {	theTimePlanText = aTimePlanText;	}

	/** Внутренний направитель (текст) */
	@Comment("Внутренний направитель (текст)")
	@Persist
	public String getOrderWorkerText() {return theOrderWorkerText;}
	public void setOrderWorkerText(String aOrderWorkerText) {theOrderWorkerText = aOrderWorkerText;}
	

	/** Цель визита (текст) */
	@Comment("Цель визита (текст)")
	@Persist
	public String getVisitReasonText() {return theVisitReasonText;}
	public void setVisitReasonText(String aVisitReasonText) {theVisitReasonText = aVisitReasonText;}

	/** Дата выполнения */
	@Comment("Дата выполнения")
	@Persist
	public String getDateExecuteText() {return theDateExecuteText;	}
	public void setDateExecuteText(String aDateExecuteText) {theDateExecuteText = aDateExecuteText;	}

	/** Рабочая функция исполнения (текст) */
	@Comment("Рабочая функция исполнения (текст)")
	@Persist
	public String getWorkFunctionExecuteText() {return theWorkFunctionExecuteText;}
	public void setWorkFunctionExecuteText(String aWorkFunctionExecuteText) {theWorkFunctionExecuteText = aWorkFunctionExecuteText;	}

	/** Рабочая функция направителя */
	@Comment("Рабочая функция направителя")
	@Persist
	public Long getOrderWorkFunction() {return theOrderWorkFunction;}
	public void setOrderWorkFunction(Long aNewProperty) {theOrderWorkFunction = aNewProperty;}

		/** Рабочая функция направителя (текст) */
	@Comment("Рабочая функция направителя (текст)")
	@Persist
	public String getOrderWorkFunctionText() {return theOrderWorkFunctionText;}
	public void setOrderWorkFunctionText(String aOrderWorkFunctionText) {theOrderWorkFunctionText = aOrderWorkFunctionText;}

	 /** Диспансерный учет * */
    @Persist
    public Long getDispRegistration() {return theDispRegistration;}
    public void setDispRegistration(Long aVocDispanseryRegistration) {
        theDispRegistration = aVocDispanseryRegistration;
    }
    
    /** Дата следующего визита */
	@Comment("Дата следующего визита")
	@DateString @DoDateString @Persist
	public String getNextVisitDate() {
		return theNextVisitDate;
	}

	public void setNextVisitDate(String aNextVisitDate) {
		theNextVisitDate = aNextVisitDate;
	}

	/** Дата следующего визита */
	private String theNextVisitDate;
    private Long theDispRegistration;
	/** Рабочая функция направителя (текст) */
	private String theOrderWorkFunctionText;
	/** Рабочая функция направителя */
	private Long theOrderWorkFunction;
	/** Рабочая функция исполнения (текст) */
	private String theWorkFunctionExecuteText;
	/** Дата выполнения */
	private String theDateExecuteText;
	/** Цель визита (текст) */
	private String theVisitReasonText;
	/** Внутренний направитель (текст) */
	private String theOrderWorkerText;
	/** Планируемое время исполнения (текст) */
	private String theTimePlanText;
	/** Планируемая дата исполнения (текст) */
	private String theDatePlanText;
	/** Тип рабочего места обслуживания */
	private Long theWorkPlaceType;
	/** Цель визита */
	private Long theVisitReason;
	/** Результат визита */
	private Long theVisitResult;
	/** Внешний направитель (ЛПУ) */
	private Long theOrderLpu;
	/** Внутренний направитель (сотрудник) */
	private Long theOrderWorker;
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
}
