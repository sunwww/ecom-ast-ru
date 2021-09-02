package ru.ecom.mis.ejb.form.medcase.poly;

import lombok.Setter;
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
@Setter
public class VisitMedCaseForm extends TicketMedCaseForm {
	/** Количество выписанных льготных рецептов */
	@Comment("Количество выписанных льготных рецептов")
	@Persist
	public Integer getPrivilegeRecipeAmount() {return privilegeRecipeAmount;	}

	/** Внешний направитель (ЛПУ) */
	@Comment("Внешний направитель (ЛПУ)")
	@Persist
	public Long getOrderLpu() {return orderLpu;}

	/** Результат визита */
	@Comment("Результат визита")
	@Persist @Required
	public Long getVisitResult() {return visitResult;	}

	/** Цель визита */
	@Comment("Цель визита")
	@Persist @Required
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

    /** Дата следующего визита */
	@Comment("Дата следующего визита")
	@DateString @DoDateString @Persist @MaxDateCurrent
	public String getNextVisitDate() {
		return nextVisitDate;
	}

	/** Госпитализация (впервые, повторно) */
	@Comment("Госпитализация (впервые, повторно)")
	@Persist @Required
	public Long getHospitalization() {return hospitalization;}
	/** Дата начала */
	@Comment("Дата начала")
	@Persist @DateString @DoDateString @MaxDateCurrent
	public String getDateStart() {return dateStart;}
	/** Дата начала */
	private String dateStart;
	/** Госпитализация (впервые, повторно) */
	private Long hospitalization;

	/** Дата следующего визита */
	private String nextVisitDate;
    private Long dispRegistration;
	/** Рабочая функция направителя */
	private Long orderWorkFunction;
	/** Тип рабочего места обслуживания */
	private Long workPlaceType;
	/** Цель визита */
	private Long visitReason;
	/** Результат визита */
	private Long visitResult;
	/** Внешний направитель (ЛПУ) */
	private Long orderLpu;
	/** Количество выписанных льготных рецептов */
	private Integer privilegeRecipeAmount;
	
	/** Количество оказанных услуг - AOI, 09.08.2016*/
	private String medserviceAmounts;
	public String getMedserviceAmounts() { return medserviceAmounts; }

	/** Поток обслуживания */
	@Comment("Поток обслуживания")
	@Persist @Required
	public Long getServiceStream() {return serviceStream;}

	/** Поток обслуживания */
	private Long serviceStream;
	/** Отделение (текст) */
	@Comment("Отделение (текст)")
	@Persist
	public String getDepartmentInfo() {return departmentInfo;}
	/** Отделение */
	@Comment("Отделение")
	@Persist
	public Long getDepartment() {return department;}
	/** Отделение */
	private Long department;
	/** Отделение (текст) */
	private String departmentInfo;	
	
	/** medServices */
	@Comment("medServices")
	public String getMedServices() {return medServices;}

	/** medServices */
	private String medServices;
	/** Предварительный прием */
	@Comment("Предварительный прием")
	public Boolean getIsPreRecord() {return isPreRecord;}

	/** Предварительный прием */
	private Boolean isPreRecord;

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

	private String promedCode;

	@Comment("Код в промеде")
	@Persist
	public String getPromedCode() {
		return promedCode;
	}
	
}
