package ru.ecom.mis.ejb.form.medcase.ticket;

import lombok.Setter;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.interceptors.*;
import ru.ecom.mis.ejb.domain.medcase.ShortMedCase;
import ru.ecom.mis.ejb.form.medcase.ticket.interceptors.TicketMedCaseSaveInterceptor;
import ru.ecom.mis.ejb.form.medcase.ticket.interceptors.TicketMedCaseViewInterceptor;
import ru.ecom.mis.ejb.form.medcase.ticket.interceptors.TicketPreCreateInterceptor;
import ru.ecom.poly.ejb.form.MedcardForm;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.transforms.DoTimeString;
import ru.nuzmsh.forms.validator.validators.*;

/**
 * Форма короткого СМО
 * @author STkacheva
 *
 */
@EntityForm
@EntityFormPersistance(clazz = ShortMedCase.class)
@Comment("Талон")
@Parent(property = "medcard", parentForm = MedcardForm.class,orderBy="dateStart")
@WebTrail(comment = "Талон", nameProperties = "id"
	, view = "entityView-smo_ticket.do"
	, shortView = "entityShortView-smo_ticket.do"
	, list = "js-smo_ticket-list.do"
	, shortList = "js-smo_ticket-list.do?short=Short" 
)
@EntityFormSecurityPrefix("/Policy/Poly/Ticket")
@ASaveInterceptors(
        @AEntityFormInterceptor(TicketMedCaseSaveInterceptor.class)
)
@ACreateInterceptors(
        @AEntityFormInterceptor(TicketMedCaseSaveInterceptor.class)
)
@AViewInterceptors(
        @AEntityFormInterceptor(TicketMedCaseViewInterceptor.class)
)
@AParentPrepareCreateInterceptors(
        @AParentEntityFormInterceptor(TicketPreCreateInterceptor.class)
)
@Setter
public class TicketMedCaseForm extends ShortTicketMedCaseForm {
	/** Признак консультативно-диагностического обращения */
	@Comment("Признак консультативно-диагностического обращения")
	@Persist @Deprecated
	public Boolean getIsDiagnosticSpo() {return isDiagnosticSpo;}
	private Boolean isDiagnosticSpo ;

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

	/**
	 * Рабочая функция исполнения
	 */
	@Comment("Рабочая функция исполнения")
	@Persist @Required
	public Long getWorkFunctionExecute() {return workFunctionExecute;	}
	private Long workFunctionExecute;

	/** Планируемое время исполнения */
	@Comment("Планируемое время исполнения")
	@Persist
	public Long getTimePlan() {return timePlan;}
	private Long timePlan;

	/** Штамп времени исполнения */
	@Comment("Время исполнения")
	@Persist @TimeString @DoTimeString
	public String getTimeExecute() {return timeExecute;	}
	private String timeExecute;

	/** Инфо по полису */
	@Comment("Инфо по полису")
	public String getInfoByPolicy() {return infoByPolicy;}
	private String infoByPolicy;
	
	/** Дата направления */
	@Comment("Дата направления")
	@DateString @DoDateString
	@Persist
	public String getDateFinish() {return dateFinish;}
	private String dateFinish;

    /** Разговор с родственником */
	@Comment("Разговор с родственником")
	@Persist
	public Boolean getIsTalk() {return isTalk;}
	private Boolean isTalk;

	/** Направление на госпитализацию */
	@Comment("Направление на госпитализацию")
	@Persist
	public Boolean getIsDirectHospital() {return isDirectHospital;}
	private Boolean isDirectHospital;

	/** Услуги */
	@Comment("Услуги")
	public String getMedServices() {return medServices;	}
	private String medServices;

	/** Сопутствующие диагнозы */
	@Comment("Сопутствующие диагнозы")
	public String getConcomitantDiseases() {return concomitantDiseases;}
	private String concomitantDiseases;
	
	/** Заключительный диагноз */
	@Comment("Заключительный диагноз")
	@Required
	public String getConcludingDiagnos() {return concludingDiagnos;}
	private String concludingDiagnos;

	/** Заключительный диагноз по МКБ-10 */
	@Comment("Заключительный диагноз по МКБ-10")
	@Mkb @Required
	public Long getConcludingMkb() {return concludingMkb;}
	private Long concludingMkb;

	/** Заключительный диагноз */
	@Comment("Заключительный диагноз")
	public Long getConcludingTrauma() {return concludingTrauma;}
	private Long concludingTrauma;

	/** Острота диагноза заключительного */
	@Comment("Острота диагноза клинического")
	@Required
	public Long getConcludingActuity() {return concludingActuity;}
	private Long concludingActuity;

	/** Результат визита */
	@Comment("Результат визита")
	@Persist @Required
	public Long getVisitResult() {return visitResult;	}
	private Long visitResult;

	/** Цель визита */
	@Comment("Цель визита")
	@Persist @Required
	public Long getVisitReason() {return visitReason;	}
	private Long visitReason;

	/** Тип рабочего места обслуживания */
	@Comment("Тип рабочего места обслуживания")
	@Persist @Required
	public Long getWorkPlaceType() {return workPlaceType;}
	private Long workPlaceType;

	 /** Диспансерный учет * */
    @Persist
    public Long getDispRegistration() {return dispRegistration;}
	private Long dispRegistration;

	/** Дата следующего визита */
	@Comment("Дата следующего визита")
	@DateString @DoDateString @Persist
	public String getNextVisitDate() {
		return nextVisitDate;
	}
	private String nextVisitDate;

    /** Скорая помощь */
	@Comment("Скорая помощь")
	@Persist
	public Long getAmbulance() {return ambulance;}
	private Long ambulance;

	/** Исход визита */
	@Comment("Исход визита")
	@Persist
	public Long getVisitOutcome() {return visitOutcome;}
	private Long visitOutcome;

	/** Дата начала */
	@Comment("Дата начала")
	@Persist @DateString @DoDateString @MaxDateCurrent @Required
	public String getDateStart() {return dateStart;}
	private String dateStart;

	/** Дата направления */
	@Comment("Дата направления")
	@Persist @DoDateString @DateString
	public String getOrderDate() {return orderDate;}
	private String orderDate;

	/** Внешний направитель (ЛПУ) */
	@Comment("Внешний направитель (ЛПУ)")
	@Persist
	public Long getOrderLpu() {return orderLpu;}
	private Long orderLpu;

	/** МКБ */
	@Comment("МКБ")
	public String getMkb() {return mkb;}
	private String mkb;
	
	/** Доп.код мкб */
	@Comment("Доп.код мкб")
	public String getMkbAdc() {return mkbAdc;}
	private String mkbAdc;
	
	/** Карта скорой помощи */
	@Comment("Карта скорой помощи")
	public String getAmbulanceCard() {return ambulanceCard;}
	private String ambulanceCard;
	
	/** Номер зуба */
	@Comment("Номер зуба")
	public String getOrderNumber() {return orderNumber;}
	private String orderNumber;
	
	/** Кол-во услуг */
	@Comment("Кол-во услуг")
	public Integer getMedServiceAmount() {
		return medServiceAmount;
	}
	private Integer medServiceAmount;
	
	/** услуга */
	@Comment("услуга")
	public Long getMedService() {
		return medService;
	}
	private Long medService;
	
	/** УЕТ */
	@Comment("УЕТ")
	public String getUetT() {
		return uetT;
	}
	private String uetT;

	@Comment("Исполнитель услуги")
	public Long getMedserviceExecutor() {return medserviceExecutor;}
	private Long medserviceExecutor;
	
	/** Время получения вызова СМП */
	@Comment("Время получения вызова СМП")
	@TimeString @DoTimeString
	public String getCallReceiveTime() {return callReceiveTime;}
	private String callReceiveTime;

	/** Время прибытия бригады СМП */
	@Comment("Время прибытия бригады СМП")
	@TimeString @DoTimeString
	public String getArrivalTime() {return arrivalTime;}
	private String arrivalTime;

	/** Примечания пациента при записи */
	@Comment("Примечания пациента при записи")
	public String getPatientComment() {return patientComment;}
	private String patientComment ;
}