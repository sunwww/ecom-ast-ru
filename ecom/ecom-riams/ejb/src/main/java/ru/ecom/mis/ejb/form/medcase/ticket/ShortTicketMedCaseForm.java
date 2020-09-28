package ru.ecom.mis.ejb.form.medcase.ticket;

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
public class ShortTicketMedCaseForm extends ChildMedCaseForm {
	/** Внешний идентификатор */
	@Comment("Внешний идентификатор")
	@Persist
	public String getExternalId() {return theExternalId;}
	public void setExternalId(String aNewProperty) {theExternalId = aNewProperty;}
	private String theExternalId;

	/** Дата направления */
	@Comment("Дата направления")
	@DateString @DoDateString
	@Persist @Required
	public String getDateFinish() {return theDateFinish;}
	public void setDateFinish(String aDateFinish) {theDateFinish = aDateFinish;}
	private String theDateFinish;

	/**
	 * Рабочая функция исполнения
	 */
	@Comment("Рабочая функция исполнения")
	@Persist @Required
	public Long getWorkFunctionExecute() {return theWorkFunctionExecute;	}
	public void setWorkFunctionExecute(Long aNewProperty) {	theWorkFunctionExecute = aNewProperty;}
	private Long theWorkFunctionExecute;

	
    @Comment("Медицинская карта")
    @Persist 
    public Long getMedcard() {return theMedcard;}
    public void setMedcard(Long aMedcard) {theMedcard = aMedcard;}
    private Long theMedcard;

	/** Поток обслуживания */
	@Comment("Поток обслуживания")
	@Persist @Required
	public Long getServiceStream() {return theServiceStream;}
	public void setServiceStream(Long aServiceStream) {theServiceStream = aServiceStream;}
	private Long theServiceStream;

	/** Категория ребенка */
	@Comment("Категория ребенка")
	public Long getCategoryChild() {return theCategoryChild;}
	public void setCategoryChild(Long aCategoryChild) {theCategoryChild = aCategoryChild;}
	private Long theCategoryChild;
	
	/** Цель визита */
	@Comment("Цель визита")
	@Persist 
	public Long getVisitReason() {return theVisitReason;	}
	public void setVisitReason(Long aReason) {theVisitReason = aReason;	}
	private Long theVisitReason;

	/** Тип рабочего места обслуживания */
	@Comment("Тип рабочего места обслуживания")
	@Persist 
	public Long getWorkPlaceType() {return theWorkPlaceType;}
	public void setWorkPlaceType(Long aWorkPlaceType) {theWorkPlaceType = aWorkPlaceType;	}
	private Long theWorkPlaceType;

	@Comment("Планируемое время исполнения")
	@Persist
	public Long getTimePlan() {
		return theTimePlan;
	}
	public void setTimePlan(Long aNewProperty) {
		theTimePlan = aNewProperty;
	}
	private Long theTimePlan;

	/** Планируемая дата исполнения */
	@Comment("Планируемая дата исполнения")
	@Persist
	public Long getDatePlan() {return theDatePlan;	}
	public void setDatePlan(Long aNewProperty) {theDatePlan = aNewProperty;	}
	private Long theDatePlan;

	/** Планируемая рабочая функция исполнения */
	@Comment("Планируемая рабочая функция исполнения")
	@Persist
	public Long getWorkFunctionPlan() {return theWorkFunctionPlan;}
	public void setWorkFunctionPlan(Long aNewProperty) {theWorkFunctionPlan = aNewProperty;}
	private Long theWorkFunctionPlan;
}
