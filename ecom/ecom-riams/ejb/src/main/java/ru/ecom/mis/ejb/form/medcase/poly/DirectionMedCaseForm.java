package ru.ecom.mis.ejb.form.medcase.poly;

import lombok.Setter;
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
@Setter

public class DirectionMedCaseForm extends TicketMedCaseForm {
	
	/** Количество записей  */
	@Comment("Количество записей")
	@DateString @DoDateString
	public String getAnyDatePlan() {return anyDatePlan; }

	private String anyDatePlan;
	/** Количество записей  */
	@Comment("Количество записей")
	public Long getCountDays() {return countDays; }

	private Long countDays;

	/** Количество выписанных льготных рецептов */
	@Comment("Количество выписанных льготных рецептов")
	@Persist
	public Integer getPrivilegeRecipeAmount() {return privilegeRecipeAmount;	}

	/** Внешний направитель (ЛПУ) */
	@Comment("Внешний направитель (ЛПУ)")
	@Persist
	public Long getOrderLpu() {return orderLpu;}
	private Long orderLpu;

	/** Результат визита */
	@Comment("Результат визита")
	@Persist
	public Long getVisitResult() {return visitResult;	}

	/** Цель визита */
	@Comment("Цель визита")
	@Persist
	public Long getVisitReason() {return visitReason;	}

	/** Тип рабочего места обслуживания */
	@Comment("Тип рабочего места обслуживания")
	@Persist @Required
	public Long getWorkPlaceType() {return workPlaceType;}

	/** Рабочая функция направителя */
	@Comment("Рабочая функция направителя")
	@Persist
	public Long getOrderWorkFunction() {return orderWorkFunction;}

	 /** Диспансерный учет * */
    @Persist
    public Long getDispRegistration() {return dispRegistration;}

    private Long dispRegistration;
	/** Рабочая функция направителя */
	private Long orderWorkFunction;
	/** Тип рабочего места обслуживания */
	private Long workPlaceType;
	/** Цель визита */
	private Long visitReason;
	/** Результат визита */
	private Long visitResult;

	/** Количество выписанных льготных рецептов */
	private Integer privilegeRecipeAmount;
	
	/** Планируемая дата исполнения */
	@Comment("Планируемая дата исполнения")
	@Persist @Required
	public Long getDatePlan() {return datePlan;	}

	/** Планируемая рабочая функция исполнения */
	@Comment("Планируемая рабочая функция исполнения")
	@Persist @Required
	public Long getWorkFunctionPlan() {return workFunctionPlan;}

	/** Планируемое время исполнения */
	@Comment("Планируемое время исполнения")
	@Persist @Required
	public Long getTimePlan() {return timePlan;}

	/** Планируемое время исполнения */
	private Long timePlan;
	/** Планируемая рабочая функция исполнения */
	private Long workFunctionPlan;
	/** Планируемая дата исполнения */
	private Long datePlan;
	/** Поток обслуживания */
	@Comment("Поток обслуживания")
	@Persist @Required
	public Long getServiceStream() {return serviceStream;}
	/** Поток обслуживания */
	private Long serviceStream;	
	/**
	 * Рабочая функция исполнения
	 */
	@Comment("Рабочая функция исполнения")
	@Persist 
	public Long getWorkFunctionExecute() {return workFunctionExecute;	}
	/** Рабочая функция исполнения */
	private Long workFunctionExecute;
	/** Заключительный диагноз по МКБ-10 */
	@Comment("Заключительный диагноз по МКБ-10")
	@Mkb 
	public Long getConcludingMkb() {return concludingMkb;}
	/** Заключительный диагноз */
	@Comment("Заключительный диагноз")
	public String getConcludingDiagnos() {return concludingDiagnos;}

	/** Острота диагноза заключительного */
	@Comment("Острота диагноза клинического")
	public Long getConcludingActuity() {return concludingActuity;}
	/** Острота диагноза заключительного */
	private Long concludingActuity;
	/** Заключительный диагноз */
	private String concludingDiagnos;
	/** Заключительный диагноз по МКБ-10 */
	private Long concludingMkb;
	
	/** Дата начала */
	@Comment("Дата начала")
	@Persist @DateString @DoDateString @MaxDateCurrent
	public String getDateStart() {return dateStart;}
	/** Дата начала */
	private String dateStart;
}
