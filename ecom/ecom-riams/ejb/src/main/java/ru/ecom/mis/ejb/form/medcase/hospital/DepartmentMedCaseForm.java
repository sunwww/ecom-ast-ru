package ru.ecom.mis.ejb.form.medcase.hospital;


import lombok.Setter;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.interceptors.ACreateInterceptors;
import ru.ecom.ejb.services.entityform.interceptors.AEntityFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.AParentEntityFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.AParentPrepareCreateInterceptors;
import ru.ecom.ejb.services.entityform.interceptors.ASaveInterceptors;
import ru.ecom.ejb.services.entityform.interceptors.AViewInterceptors;
import ru.ecom.mis.ejb.domain.medcase.DepartmentMedCase;
import ru.ecom.mis.ejb.form.medcase.hospital.interceptors.DepartmentMedCaseCreateInterceptor;
import ru.ecom.mis.ejb.form.medcase.hospital.interceptors.DepartmentSaveInterceptor;
import ru.ecom.mis.ejb.form.medcase.hospital.interceptors.DepartmentViewInterceptor;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.transforms.DoTimeString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.MaxDateCurrent;
import ru.nuzmsh.forms.validator.validators.Required;
import ru.nuzmsh.forms.validator.validators.TimeString;

@Comment("Лечение в отделении")
//@Comment("Случай стационарного лечения в отделении")
@EntityForm
@EntityFormPersistance(clazz=DepartmentMedCase.class)
@WebTrail(comment = "Лечение в отделении", nameProperties= "departmentInfo", view="entityParentView-stac_slo.do",shortView="entityShortView-stac_slo.do"
	,shortList="entityParentShortList-stac_slo.do",list="entityParentList-stac_slo.do")
@Parent(property="parent", parentForm= HospitalMedCaseForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/MedCase/Stac/Ssl/Slo")
@AParentPrepareCreateInterceptors(
        @AParentEntityFormInterceptor(DepartmentMedCaseCreateInterceptor.class)
)
@ASaveInterceptors(
        @AEntityFormInterceptor(DepartmentSaveInterceptor.class)
)
@AViewInterceptors(
        @AEntityFormInterceptor(DepartmentViewInterceptor.class)
)
@ACreateInterceptors( {
	@AEntityFormInterceptor(DepartmentSaveInterceptor.class)
})
@Setter
public class DepartmentMedCaseForm extends HospitalMedCaseForm {

	/** Пациент */
	@Comment("Пациент")
	@Persist
	public Long getPatient() {return patient;}

	/** Случай стационарного лечения */
	@Comment("Случай стационарного лечения")
	@Persist
	public Long getParent() {return parent;	}

	/** Дата поступления */
	@Comment("Дата поступления")
	@DateString @DoDateString
	@Persist @MaxDateCurrent @Required
	public String getDateStart() {return dateStart;	}

	/** Время поступления */
	@Comment("Время поступления")
	@TimeString @DoTimeString
	@Persist @Required
	public String getEntranceTime() {return entranceTime;}

	/** Отделение перевода */
	@Comment("Отделение перевода")
	@Persist
	public Long getTransferDepartment() {return transferDepartment;}

	/** Отделение */
	@Comment("Отделение")
	@Persist @Required
	public Long getDepartment() {return department;	}

	/** Дата выписки */
	@Comment("Дата выписки")
	@DateString @DoDateString
	@Persist @MaxDateCurrent
	public String getDateFinish() {	return dateFinish;}

	/** Время выписки */
	@Comment("Время выписки")
	@TimeString @DoTimeString
	@Persist
	public String getDischargeTime() {return dischargeTime;}

	
	/** Лечебное учреждение */
	@Comment("Лечебное учреждение")
	@Persist
	public Long getLpu() {return lpu;}

	/** Количество дней */
	@Comment("Количество дней")
	@Persist
	public String getDaysCount() {return daysCount;}

	/** Отделение поступления. Инфо */
	@Comment("Отделение поступления. Инфо")
	@Persist
	public String getDepartmentInfo() {return departmentInfo;}

	/** Отделение перевода. Инфо */
	@Comment("Отделение перевода. Инфо")
	@Persist
	public String getTransferDepartmentInfo() {return transferDepartmentInfo;}

	/** Куда переведен */
	@Comment("Куда переведен")
	@Persist
	public Long getTargetHospType() {return targetHospType;}

	
	/** Тип текущего стационара */
	@Comment("Тип текущего стационара")
	@Persist
	public Long getHospType() {return hospType;}

	/** Откуда поступил */
	@Comment("Откуда поступил")
	@Persist
	public Long getSourceHospType() {return sourceHospType;	}

	/** Рабочая функция лечащего врача */
	@Comment("Рабочая функция лечащего врача")
	@Persist @Required
	public Long getOwnerFunction() {return ownerFunction;}

	/** Предыдущий случай лечения в отделении */
	@Comment("Предыдущий случай лечения в отделении")
	@Persist
	public Long getPrevMedCase() {return prevMedCase;}

	/** Коечный фонд */
	@Comment("Коечный фонд")
	@Persist @Required
	public Long getBedFund() {return bedFund;	}

	/** № палаты */
	@Comment("№ палаты")
	@Persist
	public Long getRoomNumber() {return roomNumber;	}

	/** Поток обслуживания */
	@Comment("Поток обслуживания")
	@Persist @Required
	public Long getServiceStream() {return serviceStream;}

	/** ЛПУ и дата начала (необходима для вычисления профиля коек из фонда) */
	@Comment("ЛПУ и дата начала (необходима для вычисления профиля коек из фонда)")
	public String getlpuAndDate() {return lpuAndDate;}
	public void setlpuAndDate(String alpuAndDate) {
		lpuAndDate =	new StringBuilder().append(department)
			.append(":")
			.append(serviceStream)
			.append(":").append(dateStart).toString();
	}

	/** № койки */
	@Comment("№ койки")
	@Persist
	public Long getBedNumber() {return bedNumber;}

	/** Информация о номере стат.карты СЛС */
	@Comment("Информация о номере стат.карты СЛС")
	@Persist
	public String getStatCardBySLS() {return statCardBySLS;}

	/** Тип палаты */
	@Comment("Тип палаты")
	@Persist
	public Long getRoomType() {return roomType;}

	/** Острота диагноза клинического */
	@Comment("Острота диагноза клинического")
	public Long getClinicalActuity() {return clinicalActuity;}

	/** Острота диагноза клинического */
	private Long clinicalActuity;
	/** Тип палаты */
	private Long roomType;
	/** Информация о номере стат.карты СЛС */
	private String statCardBySLS;
	/** № койки */
	private Long bedNumber;
	/** Поток обслуживания*/
	private Long serviceStream;
	/** ЛПУ и дата начала (необходима для вычисления профиля коек из фонда) */
	private String lpuAndDate;
	/** № палаты */
	private Long roomNumber;
	/** Коечный фонд */
	private Long bedFund;
	/** Предыдущий случай лечения в отделении */
	private Long prevMedCase;	/** Рабочая функция лечащего врача */
	private Long ownerFunction;
	/** Откуда поступил */
	private Long sourceHospType;
	/** Тип текущего стационара */
	private Long hospType;
	
	/** Куда переведен */
	private Long targetHospType; 
	/** Отделение перевода. Инфо */
	private String transferDepartmentInfo;
	/** Отделение поступления. Инфо */
	private String departmentInfo;
	/** Количество дней */
	private String daysCount;
	/** Лечебное учреждение */
	private Long lpu;
	/** Время выписки */
	private String dischargeTime;
	/** Дата выписки */
	private String dateFinish;

	/** Отделение */
	private Long department;
	/** Отделение перевода */
	private Long transferDepartment;
	/** Время поступления */
	private String entranceTime;
	/** Дата поступления */
	private String dateStart;
	/** Случай стационарного лечения */
	private Long parent;
	/** Пациент */
	private Long patient;
	/** Стандарт */
	@Comment("Стандарт")
	@Persist
	public Long getOmcStandart() {
		return omcStandart;
	}

	/** Стандарт */
	private Long omcStandart;
	/** Омс стандарт, установленный экспертом */
	@Comment("Омс стандарт, установленный экспертом")
	@Persist
	public Long getOmcStandartExpert() {
		return omcStandartExpert;
	}

	/** Омс стандарт, установленный экспертом */
	private Long omcStandartExpert;
	
	/** Вид ВМП */
	@Comment("Вид ВМП")
	@Persist
	public Long getKindHighCare() {return kindHighCare;}

	/** Метод ВМП */
	@Comment("Метод ВМП")
	@Persist
	public Long getMethodHighCare() {return methodHighCare;}

	/** Метод ВМП */
	private Long methodHighCare;
	/** Вид ВМП */
	private Long kindHighCare;

	/** Доп.код мкб */
	@Comment("Доп.код мкб")
	public String getMkbAdc() {return mkbAdc;}

	/** Доп.код мкб */
	private String mkbAdc;
	
	/** Диета */
	@Comment("Диета")
	public Long getDiet() {return diet;}
	/** Диета */
	private Long diet;
	
	/** Режим */
	@Comment("Режим")
	public Long getMode() {return mode;}
	/** Режим */
	private Long mode;

}
