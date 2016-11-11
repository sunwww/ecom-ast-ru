package ru.ecom.mis.ejb.form.medcase.hospital;


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
public class DepartmentMedCaseForm extends HospitalMedCaseForm {

	/** Пациент */
	@Comment("Пациент")
	@Persist
	public Long getPatient() {return thePatient;}
	public void setPatient(Long aPatient) {thePatient = aPatient;}
	
	/** Случай стационарного лечения */
	@Comment("Случай стационарного лечения")
	@Persist
	public Long getParent() {return theParent;	}
	public void setParent(Long aParent) {theParent = aParent;}

	/** Дата поступления */
	@Comment("Дата поступления")
	@DateString @DoDateString
	@Persist @MaxDateCurrent @Required
	public String getDateStart() {return theDateStart;	}
	public void setDateStart(String aDateStart) {theDateStart = aDateStart;}

	/** Время поступления */
	@Comment("Время поступления")
	@TimeString @DoTimeString
	@Persist @Required
	public String getEntranceTime() {return theEntranceTime;}
	public void setEntranceTime(String aEntranceTime) {theEntranceTime = aEntranceTime;}


	/** Отделение перевода */
	@Comment("Отделение перевода")
	@Persist
	public Long getTransferDepartment() {return theTransferDepartment;}
	public void setTransferDepartment(Long aTransferDepartment) {theTransferDepartment = aTransferDepartment;}

	/** Отделение */
	@Comment("Отделение")
	@Persist @Required
	public Long getDepartment() {return theDepartment;	}
	public void setDepartment(Long aDepartment) {theDepartment = aDepartment;	}

	/** Дата выписки */
	@Comment("Дата выписки")
	@DateString @DoDateString
	@Persist @MaxDateCurrent
	public String getDateFinish() {	return theDateFinish;}
	public void setDateFinish(String aDateFinish) {theDateFinish = aDateFinish;}

	/** Время выписки */
	@Comment("Время выписки")
	@TimeString @DoTimeString
	@Persist
	public String getDischargeTime() {return theDischargeTime;}
	public void setDischargeTime(String aDischargeTime) {theDischargeTime = aDischargeTime;	}
	
	
	/** Лечебное учреждение */
	@Comment("Лечебное учреждение")
	@Persist
	public Long getLpu() {return theLpu;}
	public void setLpu(Long aLpu) {theLpu = aLpu;}

	/** Недействительность */
	@Comment("Недействительность")
	@Persist
	public Boolean getNoActuality() {return theNoActuality;}
	public void setNoActuality(Boolean aNoActuality) {theNoActuality = aNoActuality;}

	/** Количество дней */
	@Comment("Количество дней")
	@Persist
	public String getDaysCount() {return theDaysCount;}
	public void setDaysCount(String aDaysCount) {theDaysCount = aDaysCount;}

	/** Отделение поступления. Инфо */
	@Comment("Отделение поступления. Инфо")
	@Persist
	public String getDepartmentInfo() {return theDepartmentInfo;}
	public void setDepartmentInfo(String aDepartmentInfo) {theDepartmentInfo = aDepartmentInfo;}
	
	/** Отделение перевода. Инфо */
	@Comment("Отделение перевода. Инфо")
	@Persist
	public String getTransferDepartmentInfo() {return theTransferDepartmentInfo;}
	public void setTransferDepartmentInfo(String aTransferDepartmentInfo) {theTransferDepartmentInfo = aTransferDepartmentInfo;}

	/** Куда переведен */
	@Comment("Куда переведен")
	@Persist
	public Long getTargetHospType() {return theTargetHospType;}
	public void setTargetHospType(Long aTargetHospType) {theTargetHospType = aTargetHospType;	}

	
	/** Тип текущего стационара */
	@Comment("Тип текущего стационара")
	@Persist
	public Long getHospType() {return theHospType;}
	public void setHospType(Long aHospType) {theHospType = aHospType;}

	/** Откуда поступил */
	@Comment("Откуда поступил")
	@Persist
	public Long getSourceHospType() {return theSourceHospType;	}
	public void setSourceHospType(Long aSourceHospType) {theSourceHospType = aSourceHospType;}

	

	/** Рабочая функция лечащего врача */
	@Comment("Рабочая функция лечащего врача")
	@Persist @Required
	public Long getOwnerFunction() {return theOwnerFunction;}
	public void setOwnerFunction(Long aOwnerFunction) {theOwnerFunction = aOwnerFunction;}

	/** Предыдущий случай лечения в отделении */
	@Comment("Предыдущий случай лечения в отделении")
	@Persist
	public Long getPrevMedCase() {return thePrevMedCase;}
	public void setPrevMedCase(Long aPrevMedCase) {thePrevMedCase = aPrevMedCase;}
	
	/** Коечный фонд */
	@Comment("Коечный фонд")
	@Persist @Required
	public Long getBedFund() {return theBedFund;	}
	public void setBedFund(Long aBedFund) {theBedFund = aBedFund;}

	/** № палаты */
	@Comment("№ палаты")
	@Persist
	public Long getRoomNumber() {return theRoomNumber;	}
	public void setRoomNumber(Long aRoomNumber) {theRoomNumber = aRoomNumber;}

	/** Поток обслуживания */
	@Comment("Поток обслуживания")
	@Persist @Required
	public Long getServiceStream() {return theServiceStream;}
	public void setServiceStream(Long aServiceStream) {theServiceStream = aServiceStream;}
	
	/** ЛПУ и дата начала (необходима для вычисления профиля коек из фонда) */
	@Comment("ЛПУ и дата начала (необходима для вычисления профиля коек из фонда)")
	public String getlpuAndDate() {return thelpuAndDate;}
	public void setlpuAndDate(String alpuAndDate) {
		thelpuAndDate =	new StringBuilder().append(theDepartment)
			.append(":")
			.append(theServiceStream)
			.append(":").append(theDateStart).toString();
	}

	/** № койки */
	@Comment("№ койки")
	@Persist
	public Long getBedNumber() {return theBedNumber;}
	public void setBedNumber(Long aBedNumber) {theBedNumber = aBedNumber;}

	/** Информация о номере стат.карты СЛС */
	@Comment("Информация о номере стат.карты СЛС")
	@Persist
	public String getStatCardBySLS() {return theStatCardBySLS;}
	public void setStatCardBySLS(String aStatCardBySLS) {theStatCardBySLS = aStatCardBySLS;}

	/** Тип палаты */
	@Comment("Тип палаты")
	@Persist
	public Long getRoomType() {return theRoomType;}
	public void setRoomType(Long aRoomType) {theRoomType = aRoomType;}

	/** Острота диагноза клинического */
	@Comment("Острота диагноза клинического")
	public Long getClinicalActuity() {return theClinicalActuity;}
	public void setClinicalActuity(Long aClinicalActuity) {theClinicalActuity = aClinicalActuity;}

	/** Острота диагноза клинического */
	private Long theClinicalActuity;
	/** Тип палаты */
	private Long theRoomType;
	/** Информация о номере стат.карты СЛС */
	private String theStatCardBySLS;
	/** № койки */
	private Long theBedNumber;
	/** Поток обслуживания*/
	private Long theServiceStream;
	/** ЛПУ и дата начала (необходима для вычисления профиля коек из фонда) */
	private String thelpuAndDate;
	/** № палаты */
	private Long theRoomNumber;
	/** Коечный фонд */
	private Long theBedFund;
	/** Предыдущий случай лечения в отделении */
	private Long thePrevMedCase;	/** Рабочая функция лечащего врача */
	private Long theOwnerFunction;
	/** Откуда поступил */
	private Long theSourceHospType;
	/** Тип текущего стационара */
	private Long theHospType;
	
	/** Куда переведен */
	private Long theTargetHospType; 
	/** Отделение перевода. Инфо */
	private String theTransferDepartmentInfo;
	/** Отделение поступления. Инфо */
	private String theDepartmentInfo;
	/** Количество дней */
	private String theDaysCount;
	/** Недействительность */
	private Boolean theNoActuality;
	/** Лечебное учреждение */
	private Long theLpu;
	/** Время выписки */
	private String theDischargeTime;
	/** Дата выписки */
	private String theDateFinish;

	/** Отделение */
	private Long theDepartment;
	/** Отделение перевода */
	private Long theTransferDepartment;
	/** Время поступления */
	private String theEntranceTime;
	/** Дата поступления */
	private String theDateStart;
	/** Случай стационарного лечения */
	private Long theParent;
	/** Пациент */
	private Long thePatient;
	/** Стандарт */
	@Comment("Стандарт")
	@Persist
	public Long getOmcStandart() {
		return theOmcStandart;
	}

	public void setOmcStandart(Long aOmcStandart) {
		theOmcStandart = aOmcStandart;
	}

	/** Стандарт */
	private Long theOmcStandart;
	/** Омс стандарт, установленный экспертом */
	@Comment("Омс стандарт, установленный экспертом")
	@Persist
	public Long getOmcStandartExpert() {
		return theOmcStandartExpert;
	}

	public void setOmcStandartExpert(Long aOmcStandartExpert) {
		theOmcStandartExpert = aOmcStandartExpert;
	}

	/** Омс стандарт, установленный экспертом */
	private Long theOmcStandartExpert;
	
	/** Вид ВМП */
	@Comment("Вид ВМП")
	@Persist
	public Long getKindHighCare() {return theKindHighCare;}
	public void setKindHighCare(Long aKindHighCare) {theKindHighCare = aKindHighCare;}

	/** Метод ВМП */
	@Comment("Метод ВМП")
	@Persist
	public Long getMethodHighCare() {return theMethodHighCare;}
	public void setMethodHighCare(Long aMethodHighCare) {theMethodHighCare = aMethodHighCare;}

	/** Метод ВМП */
	private Long theMethodHighCare;
	/** Вид ВМП */
	private Long theKindHighCare;

	/** Доп.код мкб */
	@Comment("Доп.код мкб")
	public String getMkbAdc() {return theMkbAdc;}
	public void setMkbAdc(String aMkbAdc) {theMkbAdc = aMkbAdc;}

	/** Доп.код мкб */
	private String theMkbAdc;
	
	/** Диета */
	@Comment("Диета")
	public Long getDiet() {return theDiet;}
	public void setDiet(Long aDiet) {theDiet = aDiet;}
	/** Диета */
	private Long theDiet;
	
	/** Режим */
	@Comment("Режим")
	public Long getMode() {return theMode;}
	public void setMode(Long aMode) {theMode = aMode;}
	/** Режим */
	private Long theMode;

}
