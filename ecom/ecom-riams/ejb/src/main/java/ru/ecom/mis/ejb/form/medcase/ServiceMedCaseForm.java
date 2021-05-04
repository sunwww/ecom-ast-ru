package ru.ecom.mis.ejb.form.medcase;

import lombok.Setter;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.interceptors.AParentEntityFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.AParentPrepareCreateInterceptors;
import ru.ecom.mis.ejb.domain.medcase.ServiceMedCase;
import ru.ecom.mis.ejb.form.medcase.hospital.interceptors.ServiceMedCasePreCreateInterceptor;
import ru.ecom.mis.ejb.form.medcase.ticket.TicketMedCaseForm;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.transforms.DoIntegerString;
import ru.nuzmsh.forms.validator.transforms.DoTimeString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.IntegerString;
import ru.nuzmsh.forms.validator.validators.MaxDateCurrent;
import ru.nuzmsh.forms.validator.validators.Mkb;
import ru.nuzmsh.forms.validator.validators.Required;
import ru.nuzmsh.forms.validator.validators.TimeString;

@EntityForm
@EntityFormPersistance(clazz = ServiceMedCase.class)
@Comment("Мед.услуга")
@WebTrail(comment = "Мед.услуга", nameProperties= "id"
	, view="entityParentView-smo_medService.do"
	, list = "entityParentList-smo_medService.do")
@Parent(property="parent", parentForm=MedCaseForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/MedCase/MedService")
@AParentPrepareCreateInterceptors(
        @AParentEntityFormInterceptor(ServiceMedCasePreCreateInterceptor.class)
)
@Setter
public class ServiceMedCaseForm extends TicketMedCaseForm {
	/** Мед. услуга */
	@Comment("Мед. услуга")
	@Persist @Required
	public Long getMedService() {return medService;}

	/** Количество мед. услуг */
	@Comment("Количество мед. услуг")
	@Persist @Required @DoIntegerString @IntegerString
	public Integer getMedServiceAmount() {return medServiceAmount;}

	/** Штамп времени исполнения */
	@Comment("Время исполнения")
	@Persist @TimeString @DoTimeString @Required
	public String getTimeExecute() {return timeExecute;	}

	/** Дата выполнения */
	@Comment("Дата выполнения")
	@Persist @DateString @DoDateString @Required @MaxDateCurrent
	public String getDateStart() {return dateStart;}

	/**
	 * Рабочая функция исполнения
	 */
	@Comment("Рабочая функция исполнения")
	@Persist @Required
	public Long getWorkFunctionExecute() {return workFunctionExecute;	}


	/** Поток обслуживания */
	@Comment("Поток обслуживания")
	@Persist 
	public Long getServiceStream() {return serviceStream;}

	/** Информация о мед.услуге */
	@Comment("Информация о мед.услуге")
	@Persist
	public String getMedServiceInfo() {return medServiceInfo;}

	/** Категория */
	@Comment("Категория")
	@Persist
	public Long getCategoryMedService() {return categoryMedService;}

	/** Категория информация */
	@Comment("Категория информация")
	@Persist
	public String getCategoryMedServiceInfo() {return categoryMedServiceInfo;}

	/** Протокол */
	@Comment("Протокол")
	public String getRecord() {return record;}

	/** Протокол */
	private String record;
	/** Категория информация */
	private String categoryMedServiceInfo;
	/** Категория */
	private Long categoryMedService;
	/** Информация о мед.услуге */
	private String medServiceInfo;
	/** Поток обслуживания */
	private Long serviceStream;
	/** Рабочая функция исполнения */
	private Long workFunctionExecute;
	/** Дата выполнения */
	private String dateStart;
	/** Время исполнения */
	private String timeExecute;
	/** Количество мед. услуг */
	private Integer medServiceAmount;
	/** Мед. услуга */
	private Long medService;
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
	@Persist
	public Long getWorkPlaceType() {return workPlaceType;}

    /** Тип рабочего места обслуживания */
	private Long workPlaceType;	
	/** Цель визита */
	private Long visitReason;
	/** Результат визита */
	private Long visitResult;


	/** Комментарий по услуге */
	@Comment("Комментарий по услуге")
	@Persist
	public String getServiceComment() {
		return serviceComment;
	}

	/** Комментарий по услуге */
	private String serviceComment;
}
