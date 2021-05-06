package ru.ecom.mis.ejb.form.medcase.hospital;

import lombok.Setter;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.interceptors.AEntityFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.AViewInterceptors;
import ru.ecom.mis.ejb.domain.medcase.HospitalMedCase;
import ru.ecom.mis.ejb.form.medcase.MedCaseForm;
import ru.ecom.mis.ejb.form.medcase.hospital.interceptors.HospitalMedCaseViewInterceptor;
import ru.ecom.mis.ejb.form.patient.PatientForm;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.transforms.DoTimeString;
import ru.nuzmsh.forms.validator.validators.*;


@Comment("Госпитализация (СЛС общ. инф.)")
@EntityForm
@EntityFormPersistance(clazz= HospitalMedCase.class)
@WebTrail(comment = "Госпитализация", nameProperties= "statCardNumber", view="entityParentView-stac_ssl.do"
	,shortView="entityShortView-stac_ssl.do")
@Parent(property="patient", parentForm= PatientForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/MedCase/Stac/Ssl")
@AViewInterceptors(
        @AEntityFormInterceptor(HospitalMedCaseViewInterceptor.class)
)
@Setter
public class HospitalMedCaseForm extends MedCaseForm {
	/** Дата начала */
	@Comment("Дата начала") 
	@DateString @DoDateString 
	@Persist 
	public String getDateStart() {return dateStart;	}

	/** Рабочая функция направителя */
	@Comment("Рабочая функция направителя")
	public Long getOrderWorkFunction() {return orderWorkFunction;}

	
	/** Экстренность */
	@Comment("Экстренность")
	@Persist
	public Boolean getEmergency() {return emergency;	}

	/** Внешний идентификатор */
	@Comment("Внешний идентификатор")
	@Persist
	public String getExternalId() {return externalId;}

	/** Дефекты догоспитального этапа */
	@Comment("Дефекты догоспитального этапа")
	@Persist
	public Long getPreAdmissionDefect() { return preAdmissionDefect;}

	/** Тип доставки */
	@Comment("Тип доставки")
	@Persist
	public String getSupplyType() {return supplyType;	}

	/** Номер направления */
	@Comment("Номер направления")
	@Persist
	public String getOrderNumber() {return orderNumber;	}

	/** Номер наряда доставки */
	@Comment("Номер наряда доставки")
	@Persist
	public String getSupplyOrderNumber() {return supplyOrderNumber;	}

	/** Время заболевания до госпитализации */
	@Comment("Время заболевания до госпитализации")
	@Persist
	public Long getPreAdmissionTime() {	return preAdmissionTime;	}

	/** Время поступления */
	@Comment("Время поступления")
	@TimeString @DoTimeString
	@Persist 
	public String getEntranceTime() {	return entranceTime;	}

	/** Время выписки */
	@Comment("Время выписки")
	@TimeString @DoTimeString
	@Persist
	public String getDischargeTime() {return dischargeTime;}

	/** Исход госпитализации */
	@Comment("Исход госпитализации")
	@Persist
	public Long getOutcome() {	return outcome;}

	/** Результат госпитализации */
	@Comment("Результат госпитализации")
	@Persist
	public Long getResult() {return result;	}

	/** Пациент */
	@Comment("Пациент")
	@Persist
	@Required
	public Long getPatient() {	return patient;	}

	/** Дата окончания */
	@Comment("Дата окончания")
	@Persist @DateString @DoDateString
	public String getDateFinish() {return dateFinish;}

	/** Количество дней */
	@Comment("Количество дней")
	@Persist
	public String getDaysCount() {	return daysCount;}

	/** Состояние опьянения */
	@Comment("Состояние опьянения")
	@Persist
	public Long getIntoxication() {	return intoxication;	}

	@Comment("Вид травмы")
	public Long getTrauma() {return trauma;	}

	/** Кем доставлен (тип доставки) */
	@Comment("Кем доставлен (тип доставки)")
	@Persist
	public Long getOrderType() {return orderType;}

	/** Отказ от госпитализации */
	@Comment("Отказ от госпитализации")
	@Persist
	public Long getDeniedHospitalizating() {return deniedHospitalizating;}

	/** Диагноз при поступлении */
	@Comment("Диагноз при поступлении")
	public String getEntranceDiagnos() {return entranceDiagnos;	}

	/** Код МКБ приемного отделения */
	@Comment("Код МКБ приемного отделения")
	public Long getEntranceMkb() {return entranceMkb;}

	/** Диагноз направившего учреждения */
	@Comment("Диагноз направившего учреждения")
	public String getOrderDiagnos() {return orderDiagnos;}

	/** Код МКБ направившего учреждения */
	@Comment("Код МКБ направившего учреждения")
	public Long getOrderMkb() {return orderMkb;	}

	/** Профиль коек */
	@Comment("Профиль коек")
	@Persist
	public Long getBedType() {return bedType;}

	/** Оператор */
	@Comment("Оператор")
	@Persist
	public String getUsername() {return username;}

	/** Отделение */
	@Comment("Отделение")
	@Persist
	public Long getDepartment() {return department;}

	/** Случай является отказом от госпитализации? */
	@Comment("Случай является отказом от госпитализации?")
	@Persist
	public Boolean getIsDeniedHospitalizating() {return isDeniedHospitalizating;}

	/** Амбулаторное лечение */
	@Comment("Амбулаторное лечение")
	@Persist
	public Boolean getAmbulanceTreatment() {return ambulanceTreatment;}

	/** Номер стат. карты */
	@Comment("Номер стат. карты")
	@Persist
	public String getStatCardNumber() {return statCardNumber;	}

	/** Сообщение родственникам */
	@Comment("Сообщение родственникам")
	@Persist
	public Boolean getRelativeMessage() {return relativeMessage;	}

	/** Оказана мед. помощь в приемном отделении */
	@Comment("Оказана мед. помощь в приемном отделении")
	@Persist
	public Boolean getMedicalAid() {return medicalAid;}

	/** Лечебное учреждение */
	@Comment("Лечебное учреждение")
	@Persist 
	public Long getLpu() {return lpu;}

	@Comment("Провизорность")
	@Persist @Deprecated
	public Boolean getProvisional() {return provisional;}

	/** Перевод в другое ЛПУ */
	@Comment("Перевод в другое ЛПУ")
	@Persist
	public Long getMoveToAnotherLPU() {return moveToAnotherLPU;}

	/** Тип текущего стационара */
	@Comment("Тип текущего стационара")
	@Persist
	public Long getHospType() {return hospType;}

	/** Госпитализация (впервые, повторно) */
	@Comment("Госпитализация (впервые, повторно)")
	@Persist
	public Long getHospitalization() {return hospitalization;}

	/** Рабочая функция лечащего врача */
	@Comment("Рабочая функция лечащего врача")
	@Persist
	public Long getOwnerFunction() {return ownerFunction;}

	/** Выписной эпикриз */
	@Comment("Выписной эпикриз")
	public String getDischargeEpicrisis() {return dischargeEpicrisis;}

	/** Клинический диагноз */
	@Comment("Клинический диагноз")
	public String getClinicalDiagnos() {return clinicalDiagnos;}

	/** Код МКБ клинического диагноза */
	@Comment("Код МКБ клинического диагноза")
	@Mkb
	public Long getClinicalMkb() {return clinicalMkb;}

	/** Патанатомический диагноз */
	@Comment("Патанатомический диагноз")
	public String getPathanatomicalDiagnos() {return pathanatomicalDiagnos;}

	/** Патанатомический диагноз по МКБ-10 */
	@Comment("Патанатомический диагноз по МКБ-10")
	@Mkb
	public Long getPathanatomicalMkb() {return pathanatomicalMkb;}

	/** Заключительный диагноз */
	@Comment("Заключительный диагноз")
	public String getConcludingDiagnos() {return concludingDiagnos;}

	/** Заключительный диагноз по МКБ-10 */
	@Comment("Заключительный диагноз по МКБ-10")
	@Mkb
	public Long getConcludingMkb() {return concludingMkb;}

	/** Сопровождающее лицо */
	@Comment("Сопровождающее лицо")
	@Persist
	public String getAttendant() {return attendant;}

	/** Отделение (текст) */
	@Comment("Отделение (текст)")
	@Persist
	public String getDepartmentInfo() {return departmentInfo;}

	/** Педикулез */
	@Comment("Педикулез")
	@Persist
	public Long getPediculosis() {return pediculosis;}

	/** Педикулез */
	private Long pediculosis;

	/** Редкий случай */
	@Comment("Редкий случай")
	@Persist
	public Boolean getRareCase() {return rareCase;}

	/** Диагноз клин. сопутствующий */
	@Comment("Диагноз клин. сопутствующий")
	public String getConcomitantDiagnos() {return concomitantDiagnos;}

	/** Диагноз клин. сопутствующий МКБ */
	@Comment("Диагноз клин. сопутствующий МКБ")
	@Mkb
	public Long getConcomitantMkb() {return concomitantMkb;}

	/** Острота диагноза клинического */
	@Comment("Острота диагноза клинического")
	public Long getClinicalActuity() {return clinicalActuity;}

	 /**
	  * Причина госпитализации в психиатрический стационар
	  */
	 @Comment("Причина госпитализации в психиатрический стационар")
	 @Persist
	 public Long getPsychReason() {return psychReason;}
		/** Дата решения суда */
		@Comment("Дата решения суда")
		@DateString @DoDateString @Persist
		public String getLawCourtDesicionDate() {return lawCourtDesicionDate;}

		/** Острота диагноза заключительного */
		@Comment("Острота диагноза клинического")
		public Long getConcludingActuity() {return concludingActuity;}

		/** Острота диагноза заключительного */
		private Long concludingActuity;
		/** Дата решения суда */
		private String lawCourtDesicionDate;
	 /**
	  * Причина госпитализации в психиатрический стационар
	  */
	 private Long psychReason;
	/** Острота диагноза клинического */
	private Long clinicalActuity;
	/** Диагноз клин. сопутствующий МКБ */
	private Long concomitantMkb;
	/** Диагноз клин. сопутствующий */
	private String concomitantDiagnos;
	/** Редкий случай */
	private Boolean rareCase;

	/** Отделение (текст) */
	private String departmentInfo;
	/** Сопровождающее лицо */
	private String attendant;
	/** Заключительный диагноз по МКБ-10 */
	private Long concludingMkb;
	/** Заключительный диагноз */
	private String concludingDiagnos;
	/** Патанатомический диагноз по МКБ-10 */
	private Long pathanatomicalMkb;
	/** Патанатомический диагноз */
	private String pathanatomicalDiagnos;
	/** Код МКБ клинического диагноза */
	private Long clinicalMkb;
	/** Клинический диагноз */
	private String clinicalDiagnos;
	/** Выписной эпикриз */
	private String dischargeEpicrisis;
	/** Рабочая функция лечащего врача */
	private Long ownerFunction;
	/** Госпитализация (впервые, повторно) */
	private Long hospitalization;
	/** Тип текущего стационара */
	private Long hospType;	
	/** Перевод в другое ЛПУ */
	private Long moveToAnotherLPU;
	/** Провизорность */
	private Boolean provisional;
	/** Лечебное учреждение */
	private Long lpu;
	/** Оказана мед. помощь в приемном отделении */
	private Boolean medicalAid;
	/** Сообщение родственникам */
	private Boolean relativeMessage;
	///** Номер RW */
	//private String rwNumber;
	/** Номер стат. карты */
	private String statCardNumber;
	/** Амбулаторное лечение */
	private Boolean ambulanceTreatment;
	/** Случай является отказом от госпитализации? */
	private Boolean isDeniedHospitalizating;
	/** Отделение */
	private Long department;
	/** Оператор */
	private String username;
	/** Профиль коек */
	private Long bedType;
	/** Код МКБ направившего учреждения */
	private Long orderMkb;
	/** Диагноз направившего учреждения */
	private String orderDiagnos;
	/** Код МКБ приемного отделения */
	private Long entranceMkb;
	/** Диагноз при поступлении */
	private String entranceDiagnos;
	///** Дата RW */
	//private String rwDate;
	/** Отказ от госпитализации */
	private Long deniedHospitalizating;
	/** Кем доставлен (тип доставки) */
	private Long orderType;
	/** Вид травмы */
	private Long trauma;
	/** Состояние опьянения */
	private Long intoxication;
	/** Количество дней */
	private String daysCount;
	/** Дата окончания */
	private String dateFinish;
	/** Пациент */
	private Long patient;
	/** Результат госпитализации */
	private Long result;
	/** Исход госпитализации */
	private Long outcome;
	/** Время выписки */
	private String dischargeTime;
	/** Время поступления */
	private String entranceTime;
	/** Время заболевания до госпитализации */
	private Long preAdmissionTime;

	/** Номер наряда доставки */
	private String supplyOrderNumber;
	/** Номер направления */
	private String orderNumber;
	/** Тип доставки */
	private String supplyType;
	/** Дефекты догоспитального этапа */
	private Long preAdmissionDefect;
	/** Внешний идентификатор */
	private String externalId;
	/** Экстренность */
	private Boolean emergency;
	/** Дата начала */
	private String dateStart;
	/** Рабочая функция направителя */
	private Long orderWorkFunction;

	/** Дата направления */
	@Comment("Дата направления")
	@DateString @DoDateString
	@Persist
	public String getOrderDate() {return orderDate;}

	/** Дата направления */
	private String orderDate;
	
	/** Направитель */
	@Comment("Направитель")
	@Persist
	public Long getOrderLpu() {return orderLpu;}

	/** Направитель */
	private Long orderLpu;
	
	/** Порядок поступления */
	@Comment("Порядок поступления")
	@Persist
	public Long getAdmissionOrder() {return admissionOrder;}

	/** Решение суда по 35 статье */
	@Comment("Решение суда по 35 статье")
	@Persist
	public Long getJudgment35() {return judgment35;}
	/** Решение суда по 35 статье */
	private Long judgment35;
	/** Порядок поступления */
	private Long admissionOrder;
	
	/** Осложнение текст диагноза */
	@Comment("Осложнение текст диагноза")
	public String getComplicationDiagnos() {return complicationDiagnos;}

	/** Осложнение МКБ диагноза */
	@Comment("Осложнение МКБ диагноза")
	public Long getComplicationMkb() {return complicationMkb;}

	/** Осложнение МКБ диагноза */
	private Long complicationMkb;
	/** Осложнение текст диагноза */
	private String complicationDiagnos;
	
	/** Диагнозы осложнение */
	@Comment("Диагнозы осложнение")
	public String getComplicationDiags() { return complicationDiags; }

	/** Диагнозы осложнение */
	private String complicationDiags;
	
	/** Диагнозы сопутствующие */
	@Comment("Диагнозы сопутствующие")
	public String getConcomitantDiags() { return concomitantDiags; }

	/** Диагнозы сопутствующие */
	private String concomitantDiags;


	/** Поступление в данный стационар */
	@Comment("Поступление в данный стационар")
	@Persist
	public Long getAdmissionInHospital() {return admissionInHospital;}

	/** Откуда поступил */
	@Comment("Откуда поступил")
	@Persist
	public Long getWhereAdmission() {return whereAdmission;}

	/** Откуда поступил */
	private Long whereAdmission;
	/** Поступление в данный стационар */
	private Long admissionInHospital;

	/** Дата перевода */
	@Comment("Дата перевода")
	@DateString @DoDateString
	@Persist @MaxDateCurrent
	public String getTransferDate() {return transferDate;	}

	/** Время перевода */
	@Comment("Время перевода")
	@TimeString @DoTimeString
	@Persist
	public String getTransferTime() {return transferTime;	}

	/** Время перевода */
	private String transferTime;
	/** Дата перевода */
	private String transferDate;
	
	/** Причина выписки */
	@Comment("Причина выписки")
	@Persist
	public Long getReasonDischarge() {return reasonDischarge;}

	/** Причина выписки */
	private Long reasonDischarge;
	/** Итог выписки */
	@Comment("Итог выписки")
	@Persist
	public Long getResultDischarge() {return resultDischarge;}

	/** Причина выписки */
	private Long resultDischarge;
	/* Рост */
	@Comment("Рост")
	@Persist
	public Integer getHeight() { return height; }
	/* Вес */
	@Comment("Вес")
	@Persist
	public Integer getWeight() {
		return weight;
	}
	/* Индекс массы тела */
	@Comment("Индекс массы тела")
	@Persist
	public Double getImt() {
		return imt;
	}
	/** Вес */
	private Integer weight;
	/** Рост */
	private Integer height;
	/** Индекс массы тела */
	private Double imt;

	/** Дата идентификации */
	@Comment("Дата идентификации")
	@DateString @DoDateString
	@MaxDateCurrent
	public String getIdentDate() {return identDate;	}

	/** Время идентификации */
	@Comment("Время идентификации")
	@DoTimeString @TimeString
	public String getIdentTime() {return identTime;	}

	/** Кто провёл идентификацию */
	@Comment("Кто провёл идентификацию")
	public String getIdentUsername() {return identUsername;}

	/** Была ли проведена идентификация пациента? */
	@Comment("Была ли проведена идентификация пациента?")
	public Boolean getIsIdentified() { return isIdentified; }

	/** Дата идентификации */
	private String identDate;
	/** Время идентификации */
	private String identTime;
	/** Кто провёл идентификацию */
	private String identUsername;
	/** Была ли проведена идентификация пациента */
	private Boolean isIdentified;
}
