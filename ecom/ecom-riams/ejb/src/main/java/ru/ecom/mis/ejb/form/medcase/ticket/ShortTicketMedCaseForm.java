package ru.ecom.mis.ejb.form.medcase.ticket;

import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.interceptors.AParentEntityFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.AParentPrepareCreateInterceptors;
import ru.ecom.mis.ejb.domain.medcase.ShortMedCase;
import ru.ecom.mis.ejb.form.medcase.ChildMedCaseForm;
import ru.ecom.mis.ejb.form.medcase.hospital.interceptors.AdmissionPreCreateInterceptor;
import ru.ecom.mis.ejb.form.medcase.ticket.interceptors.TicketPreCreateInterceptor;
import ru.ecom.poly.ejb.form.MedcardForm;
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
	
	/** Внешний идентификатор */
	private String theExternalId;

	/** Дата направления */
	@Comment("Дата направления")
	@DateString @DoDateString
	@Persist @Required
	public String getDateFinish() {return theDateFinish;}
	public void setDateFinish(String aOrderDate) {theDateFinish = aOrderDate;}

	/**
	 * Рабочая функция исполнения
	 */
	@Comment("Рабочая функция исполнения")
	@Persist @Required
	public Long getWorkFunctionExecute() {return theWorkFunctionExecute;	}
	/** Рабочая функция исполнения */
	public void setWorkFunctionExecute(Long aNewProperty) {	theWorkFunctionExecute = aNewProperty;}

	/** Рабочая функция исполнения */
	private Long theWorkFunctionExecute;
	/** Дата направления */
	private String theDateFinish;
	
    @Comment("Медицинская карта")
    @Persist 
    public Long getMedcard() {return theMedcard;}
    public void setMedcard(Long aMedcard) {theMedcard = aMedcard;}
    /** Медицинская карта */
    private Long theMedcard;
	/** Поток обслуживания */
	@Comment("Поток обслуживания")
	@Persist @Required
	public Long getServiceStream() {return theServiceStream;}
	public void setServiceStream(Long aServiceStream) {theServiceStream = aServiceStream;}
	/** Поток обслуживания */
	private Long theServiceStream;	

	/** Категория ребенка */
	@Comment("Категория ребенка")
	public Long getCategoryChild() {return theCategoryChild;}
	public void setCategoryChild(Long aCategoryChild) {theCategoryChild = aCategoryChild;}

	/** Категория ребенка */
	private Long theCategoryChild;
	
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

	@Comment("Планируемое время исполнения")
	@Persist
	public Long getTimePlan() {
		return theTimePlan;
	}
	/*** Планируемое время исполнения*/
	public void setTimePlan(Long aNewProperty) {
		theTimePlan = aNewProperty;
	}
	/*** Планируемое время исполнения*/
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
	/** Планируемая рабочая функция исполнения */
	private Long theWorkFunctionPlan;

}
