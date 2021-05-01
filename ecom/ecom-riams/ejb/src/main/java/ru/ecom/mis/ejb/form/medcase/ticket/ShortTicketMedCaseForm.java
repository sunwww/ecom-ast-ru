package ru.ecom.mis.ejb.form.medcase.ticket;

import lombok.Setter;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.interceptors.AParentEntityFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.AParentPrepareCreateInterceptors;
import ru.ecom.mis.ejb.domain.medcase.ShortMedCase;
import ru.ecom.mis.ejb.form.medcase.ChildMedCaseForm;
import ru.ecom.mis.ejb.form.medcase.ticket.interceptors.TicketPreCreateInterceptor;
import ru.ecom.poly.ejb.form.MedcardForm;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@EntityFormPersistance(clazz = ShortMedCase.class)
@Comment("Талон на прием")
@Parent(property = "medcard", parentForm = MedcardForm.class)
@WebTrail(comment = "Талон", nameProperties = "id", view = "entityView-smo_short_ticket.do")
@EntityFormSecurityPrefix("/Policy/Poly/ShortTicket")
@AParentPrepareCreateInterceptors(
        @AParentEntityFormInterceptor(TicketPreCreateInterceptor.class)
)
@Setter
public class ShortTicketMedCaseForm extends ChildMedCaseForm {
	/** Внешний идентификатор */
	@Comment("Внешний идентификатор")
	@Persist
	public String getExternalId() {return externalId;}
	private String externalId;

	/** Дата направления */
	@Comment("Дата направления")
	@DateString @DoDateString
	@Persist @Required
	public String getDateFinish() {return dateFinish;}
	private String dateFinish;

	/**
	 * Рабочая функция исполнения
	 */
	@Comment("Рабочая функция исполнения")
	@Persist @Required
	public Long getWorkFunctionExecute() {return workFunctionExecute;	}
	private Long workFunctionExecute;

	
    @Comment("Медицинская карта")
    @Persist 
    public Long getMedcard() {return medcard;}
    private Long medcard;

	/** Поток обслуживания */
	@Comment("Поток обслуживания")
	@Persist @Required
	public Long getServiceStream() {return serviceStream;}
	private Long serviceStream;

	/** Категория ребенка */
	@Comment("Категория ребенка")
	public Long getCategoryChild() {return categoryChild;}
	private Long categoryChild;
	
	/** Цель визита */
	@Comment("Цель визита")
	@Persist 
	public Long getVisitReason() {return visitReason;	}
	private Long visitReason;

	/** Тип рабочего места обслуживания */
	@Comment("Тип рабочего места обслуживания")
	@Persist 
	public Long getWorkPlaceType() {return workPlaceType;}
	private Long workPlaceType;

	@Comment("Планируемое время исполнения")
	@Persist
	public Long getTimePlan() {
		return timePlan;
	}
	private Long timePlan;

	/** Планируемая дата исполнения */
	@Comment("Планируемая дата исполнения")
	@Persist
	public Long getDatePlan() {return datePlan;	}
	private Long datePlan;

	/** Планируемая рабочая функция исполнения */
	@Comment("Планируемая рабочая функция исполнения")
	@Persist
	public Long getWorkFunctionPlan() {return workFunctionPlan;}
	private Long workFunctionPlan;
}
