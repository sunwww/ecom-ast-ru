package ru.ecom.mis.ejb.form.medcase;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.transforms.DoTimeString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.MaxDateCurrent;
import ru.nuzmsh.forms.validator.validators.TimeString;

/**
 * Форма короткого СМО
 * @author STkacheva
 *
 */
public class ShortMedCaseForm extends ChildMedCaseForm{
	
	/** Планируемая дата исполнения */
	@Comment("Планируемая дата исполнения")
	@Persist
	public Long getDatePlan() {return theDatePlan;	}
	public void setDatePlan(Long aNewProperty) {theDatePlan = aNewProperty;	}

	/** Планируемая рабочая функция исполнения */
	@Comment("Планируемая рабочая функция исполнения")
	@Persist
	public Long getWorkFunctionPlan() {return theWorkFunctionPlan;}
	public void setWorkFunctionPlan(Long aNewProperty) {theWorkFunctionPlan = aNewProperty;}

	/**
	 * Рабочая функция исполнения
	 */
	@Comment("Рабочая функция исполнения")
	@Persist
	public Long getWorkFunctionExecute() {return theWorkFunctionExecute;	}
	/** Рабочая функция исполнения */
	public void setWorkFunctionExecute(Long aNewProperty) {	theWorkFunctionExecute = aNewProperty;}

	/** Планируемое время исполнения */
	@Comment("Планируемое время исполнения")
	@Persist
	public Long getTimePlan() {return theTimePlan;}
	public void setTimePlan(Long aNewProperty) {	theTimePlan = aNewProperty;	}

	/** Штамп времени исполнения */
	@Comment("Время исполнения")
	@Persist @TimeString @DoTimeString
	public String getTimeExecute() {return theTimeExecute;	}
	public void setTimeExecute(String aNewProperty) {theTimeExecute = aNewProperty;}

	/** Дата выполнения */
	@Comment("Дата выполнения")
	@Persist @DateString @DoDateString @MaxDateCurrent
	public String getDateExecute() {return theDateExecute;}
	public void setDateExecute(String aDateExecute) {theDateExecute = aDateExecute;}
	
	/** Дата выполнения */
	private String theDateExecute;
	/** Время исполнения */
	private String theTimeExecute;
	/** Планируемое время исполнения */
	private Long theTimePlan;
	/** Рабочая функция исполнения */
	private Long theWorkFunctionExecute;
	/** Планируемая рабочая функция исполнения */
	private Long theWorkFunctionPlan;
	/** Планируемая дата исполнения */
	private Long theDatePlan;
	/** Инфо по полису */
	@Comment("Инфо по полису")
	public String getInfoByPolicy() {
		return theInfoByPolicy;
	}

	public void setInfoByPolicy(String aInfoByPolicy) {
		theInfoByPolicy = aInfoByPolicy;
	}

	/** Инфо по полису */
	private String theInfoByPolicy;
	
	/** Дата направления */
	@Comment("Дата направления")
	@DateString @DoDateString
	@Persist
	public String getOrderDate() {return theOrderDate;}
	public void setOrderDate(String aOrderDate) {theOrderDate = aOrderDate;}

	/** Дата направления */
	private String theOrderDate;
}

