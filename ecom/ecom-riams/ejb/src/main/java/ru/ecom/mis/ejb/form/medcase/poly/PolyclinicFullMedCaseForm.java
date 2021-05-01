package ru.ecom.mis.ejb.form.medcase.poly;

import lombok.Setter;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.medcase.PolyclinicMedCase;
import ru.ecom.poly.ejb.form.MedcardForm;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.transforms.DoTimeString;
import ru.nuzmsh.forms.validator.validators.*;

//import ru.nuzmsh.commons.formpersistence.annotation.Persist;

@Comment("Случай поликлинического обслуживания")
@EntityForm
@EntityFormPersistance(clazz= PolyclinicMedCase.class)
@WebTrail(comment = "Случай поликлинического обслуживания", nameProperties= "id"
, view="entityParentView-smo_short_spo.do",shortList="entityParentList-smo_short_spo.do?short=Short",list="entityParentList-smo_short_spo.do")
@Parent(property="medcard", parentForm= MedcardForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/MedCase/Spo")
@Setter
public class PolyclinicFullMedCaseForm extends PolyclinicMedCaseForm {
	/** Дата окончания */
	@Comment("Дата окончания")
	@Persist @DateString @DoDateString
	public String getDateFinish() {return dateFinish;}

	/** Поток обслуживания */
	@Comment("Поток обслуживания")
	@Persist @Required
	public Long getServiceStream() {return serviceStream;}
	private Long serviceStream ;
	/** Код МКБ-10 */
	@Comment("Код МКБ-10")
	@Persist @Mkb  @Required
	public Long getIdc10() {return idc10;}

	/** Кто завершил */
	@Comment("Кто завершил")
	@Persist
	public Long getFinishFunction() {return finishFunction;}

	/** Владелец */
	@Comment("Владелец")
	@Persist @Required
	public Long getOwnerFunction() {return ownerFunction;}

	/** Владелец */
	private Long ownerFunction;
	/** Кто завершил */
	private Long finishFunction;
	/** Код МКБ-10 */
	private Long idc10;
	
	/** Дата окончания */
	private String dateFinish;
	
	/** Даты визитов */
	@Comment("Даты визитов")
	public String getOtherTicketDates() {return otherTicketDates;}
	/** Даты визитов */
	private String otherTicketDates;

	/** Дата другого визита */
	@Comment("Дата другого визита")
	@DoDateString @DateString
	public String getOtherTicketDate() {return otherTicketDate;}
	/** Дата другого визита */
	private String otherTicketDate;

	/** Штамп времени исполнения */
	@Comment("Время исполнения")
	@TimeString @DoTimeString
	public String getTimeExecute() {return timeExecute;	}

	/** Время исполнения */
	private String timeExecute;
	
	/** Инфо по полису */
	@Comment("Инфо по полису")
	public String getInfoByPolicy() {return infoByPolicy;}

	/** Инфо по полису */
	private String infoByPolicy;
	
    /** Разговор с родственником */
	@Comment("Разговор с родственником")
	
	public Boolean getIsTalk() {return isTalk;}

	/** Разговор с родственником */
	private Boolean isTalk;
	
	/** Услуги */
	@Comment("Услуги")
	public String getMedServices() {return medServices;	}

	/** Услуги */
	private String medServices;
	/** Сопутствующие диагнозы */
	@Comment("Сопутствующие диагнозы")
	public String getConcomitantDiseases() {return concomitantDiseases;}

	/** Сопутствующие диагнозы */
	private String concomitantDiseases;
	
	/** Заключительный диагноз */
	@Comment("Заключительный диагноз")
	@Required
	public String getConcludingDiagnos() {return concludingDiagnos;}

	
	/** Заключительный диагноз */
	@Comment("Заключительный диагноз")
	public Long getConcludingTrauma() {return concludingTrauma;}

	/** Острота диагноза заключительного */
	@Comment("Острота диагноза клинического")
	@Required
	public Long getConcludingActuity() {return concludingActuity;}

	/** Результат визита */
	@Comment("Результат визита")
	 @Required
	public Long getVisitResult() {return visitResult;	}

	/** Цель визита */
	@Comment("Цель визита")
	 @Required
	public Long getVisitReason() {return visitReason;	}

	/** Тип рабочего места обслуживания */
	@Comment("Тип рабочего места обслуживания")
	 @Required
	public Long getWorkPlaceType() {return workPlaceType;}

	 /** Диспансерный учет * */
    
    public Long getDispRegistration() {return dispRegistration;}

    /** Скорая помощь */
	@Comment("Скорая помощь")
	
	public Long getAmbulance() {return ambulance;}

	/** Исход визита */
	@Comment("Исход визита")
	
	public Long getVisitOutcome() {return visitOutcome;}

	/** Дата начала */
	@Comment("Дата начала")
	@Persist @DateString @DoDateString @MaxDateCurrent @Required
	public String getDateStart() {return dateStart;}
	/** Дата начала */
	private String dateStart;
	/** Исход визита */
	private Long visitOutcome;
	/** Скорая помощь */
	private Long ambulance;
    private Long dispRegistration;
    /** Тип рабочего места обслуживания */
	private Long workPlaceType;
	/** Цель визита */
	private Long visitReason;
	/** Результат визита */
	private Long visitResult;
	/** Острота диагноза заключительного */
	private Long concludingActuity;
	/** Заключительный диагноз */
	private Long concludingTrauma;
	
	/** Заключительный диагноз */
	private String concludingDiagnos;
	
	
	/** Внешний направитель (ЛПУ) */
	@Comment("Внешний направитель (ЛПУ)")
	
	public Long getOrderLpu() {return orderLpu;}
	/** Внешний направитель (ЛПУ) */
	private Long orderLpu;	
	/** МКБ */
	@Comment("МКБ")
	public String getMkb() {return mkb;}

	/** МКБ */
	private String mkb;
	
	/** Доп.код мкб */
	@Comment("Доп.код мкб")
	public String getMkbAdc() {return mkbAdc;}

	/** Доп.код мкб */
	private String mkbAdc;
	
	/** Карта скорой помощи */
	@Comment("Карта скорой помощи")
	public String getAmbulanceCard() {return ambulanceCard;}

	/** Карта скорой помощи */
	private String ambulanceCard;
	@Comment("Медицинская карта")
    
    public Long getMedcard() {return medcard;}
    /** Медицинская карта */
    private Long medcard;
}
