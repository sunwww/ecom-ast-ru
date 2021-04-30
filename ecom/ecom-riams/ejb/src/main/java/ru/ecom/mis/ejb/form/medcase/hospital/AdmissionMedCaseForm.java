package ru.ecom.mis.ejb.form.medcase.hospital;

import lombok.Setter;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.interceptors.*;
import ru.ecom.mis.ejb.domain.medcase.HospitalMedCase;
import ru.ecom.mis.ejb.form.medcase.hospital.interceptors.AdmissionPreCreateInterceptor;
import ru.ecom.mis.ejb.form.medcase.hospital.interceptors.AdmissionSaveInterceptor;
import ru.ecom.mis.ejb.form.medcase.hospital.interceptors.AdmissionViewInterceptor;
import ru.ecom.mis.ejb.form.patient.PatientForm;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.transforms.DoTimeString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.MaxDateCurrent;
import ru.nuzmsh.forms.validator.validators.Required;
import ru.nuzmsh.forms.validator.validators.TimeString;

@Comment("Случай стационарного лечения. Поступление в приемном отделении")
@EntityForm
@EntityFormPersistance(clazz= HospitalMedCase.class)
@WebTrail(comment = "Случай стационарного лечения. Поступление в приемном отделении", nameProperties= "id", view="entityParentView-stac_sslAdmission.do")
@Parent(property="patient", parentForm= PatientForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/MedCase/Stac/Ssl/Admission")
@AParentPrepareCreateInterceptors(
        @AParentEntityFormInterceptor(AdmissionPreCreateInterceptor.class)
)
@ASaveInterceptors(
        @AEntityFormInterceptor(AdmissionSaveInterceptor.class)
)
@AViewInterceptors(
        @AEntityFormInterceptor(AdmissionViewInterceptor.class)
)
@ACreateInterceptors( {
	@AEntityFormInterceptor(AdmissionSaveInterceptor.class)
})
@Setter
public class AdmissionMedCaseForm extends HospitalMedCaseForm {

	/** Предварительная госпитализация */
	@Comment("Предварительная госпитализация")
	public Long getPreHosp() {return preHosp;}
	/** Предварительная госпитализация */
	private Long preHosp ;

	/** Дата начала */
	@Comment("Дата начала") 
	@DateString @DoDateString 
	@Persist @MaxDateCurrent @Required
	public String getDateStart() {return dateStart;	}

	/** Недействительность */
	@Comment("Недействительность")
	@Persist
	public Boolean getNoActuality() {return noActuality;}

	/** Внешний направитель (ЛПУ) */
	@Comment("Внешний направитель (ЛПУ)")
	@Persist
	public Long getOrderLpu() {return orderLpu;}

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

	/** Дата направления */
	@Comment("Дата направления")
	@DateString @DoDateString
	@Persist 
	public String getOrderDate() {return orderDate;}

	/** Время заболевания до госпитализации */
	@Comment("Время заболевания до госпитализации")
	@Persist 
	public Long getPreAdmissionTime() {	return preAdmissionTime;	}

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
	public Long getPatient() {	return patient;	}

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
	//@Required
	public String getEntranceDiagnos() {return entranceDiagnos;	}

	/** Код МКБ приемного отделения */
	@Comment("Код МКБ приемного отделения")
	//@Required
	public Long getEntranceMkb() {return entranceMkb;}

	/** Диагноз направившего учреждения */
	@Comment("Диагноз направившего учреждения")
	public String getOrderDiagnos() {return orderDiagnos;}

	/** Код МКБ направившего учреждения */
	@Comment("Код МКБ направившего учреждения")
	public Long getOrderMkb() {return orderMkb;	}

	/** Дата диагноза при поступлении */
	@Comment("Дата диагноза при поступлении")
	@DoDateString @DateString 
	public String getEntranceDiagDate() {return entranceDiagDate;}

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
	@Persist @Required
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

	/** Время поступления */
	@Comment("Время поступления")
	@Persist @Required
	@DoTimeString @TimeString
	public String getEntranceTime() {return entranceTime;}

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
	@Required
	public Long getLpu() {return lpu;}

	/** Откуда поступил */
	@Comment("Откуда поступил")
	@Persist
	public Long getSourceHospType() {
		return sourceHospType;
	}

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
	public Double getIMT() {
		return iMT;
	}

	/** Откуда поступил */
	private Long sourceHospType;
	
	/** Тип текущего стационара */
	@Comment("Тип текущего стационара")
	@Persist
	public Long getHospType() {return hospType;}

	/** Рабочая функция лечащего врача */
	@Comment("Рабочая функция лечащего врача")
	@Persist
	public Long getOwnerFunction() {return ownerFunction;}

	/** Госпитализация (впервые, повторно) */
	@Comment("Госпитализация (впервые, повторно)")
	@Persist 
	public Long getHospitalization() {return hospitalization;}

	/** Прикрепленные полисы */
	@Comment("Прикрепленные полисы")
	public Long getAttachedPolicies() {return attachedPolicies;}

	/** Прикрепленный полис ДМС */
	@Comment("Прикрепленный полис ДМС")
	public Long getAttachedPolicyDmc() {return attachedPolicyDmc;}

	/** Была ли проведена идентификация пациента? */
	@Comment("Была ли проведена идентификация пациента?")
	@Persist
	public Boolean getIsIdentified() { return isIdentified; }

	/** Дата идентификации */
	@Comment("Дата идентификации")
	@DateString @DoDateString
	@Persist @MaxDateCurrent
	public String getIdentDate() {return identDate;	}

	/** Время идентификации */
	@Comment("Время идентификации")
	@Persist
	@DoTimeString @TimeString
	public String getIdentTime() {return identTime;	}

	/** Кто провёл идентификацию */
	@Comment("Кто провёл идентификацию")
	@Persist
	public String getIdentUsername() {return identUsername;}

	/** Прикрепленный полис ДМС */
	private Long attachedPolicyDmc;
	/** Прикрепленные полисы */
	private Long attachedPolicies;
	
	/** Госпитализация (впервые, повторно) */
	private Long hospitalization;
	/** Рабочая функция лечащего врача */
	private Long ownerFunction;
/** Тип текущего стационара */
	private Long hospType;
	/** Лечебное учреждение */
	private Long lpu;
	/** Оказана мед. помощь в приемном отделении */
	private Boolean medicalAid;
	/** Сообщение родственникам */
	private Boolean relativeMessage;
	/** Время поступления */
	private String entranceTime;
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
	/** Дата диагноза при поступлении */
	private String entranceDiagDate;
	/** Отказ от госпитализации */
	private Long deniedHospitalizating;
	/** Кем доставлен (тип доставки) */
	private Long orderType;
	/** Вид травмы */
	private Long trauma;
	/** Состояние опьянения */
	private Long intoxication;

	/** Пациент */
	private Long patient;
	/** Результат госпитализации */
	private Long result;
	/** Исход госпитализации */
	private Long outcome;
	/** Время заболевания до госпитализации */
	private Long preAdmissionTime;
	/** Дата направления */
	private String orderDate;
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
	/** Внешний направитель (ЛПУ) */
	private Long orderLpu;

	/** Недействительность */
	private Boolean noActuality;
	/** Дата начала */
	private String dateStart;

	/** Вес */
	private Integer weight;
	/** Рост */
	private Integer height;
	/** Индекс массы тела */
	private Double iMT;

	/** Была ли проведена идентификация пациента */
	private Boolean isIdentified;
	/** Дата идентификации */
	private String identDate;
	/** Время идентификации */
	private String identTime;
	/** Кто провёл идентификацию */
	private String identUsername;
}

