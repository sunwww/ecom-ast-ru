package ru.ecom.mis.ejb.form.medcase.hospital;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.annotation.PersistManyToManyOneProperty;
import ru.ecom.ejb.services.entityform.interceptors.*;
import ru.ecom.mis.ejb.domain.medcase.SurgicalOperation;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.ecom.mis.ejb.form.medcase.MedCaseForm;
import ru.ecom.mis.ejb.form.medcase.hospital.interceptors.SurgicalOperationCreateInterceptor;
import ru.ecom.mis.ejb.form.medcase.hospital.interceptors.SurgicalOperationSaveInterceptor;
import ru.ecom.mis.ejb.form.medcase.hospital.interceptors.SurgicalOperationViewInterceptor;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.transforms.DoIntegerString;
import ru.nuzmsh.forms.validator.transforms.DoTimeString;
import ru.nuzmsh.forms.validator.validators.*;

import javax.persistence.Id;

@Comment("Хирургическая операция")
@EntityForm
@EntityFormPersistance(clazz= SurgicalOperation.class)
@WebTrail(comment = "Хирургическая операция", nameProperties= "id", view="entityParentView-stac_surOperation.do")
@Parent(property="medCase", parentForm= MedCaseForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/MedCase/Stac/Ssl/SurOper")
@AParentPrepareCreateInterceptors(
        @AParentEntityFormInterceptor(SurgicalOperationCreateInterceptor.class)
)
@ASaveInterceptors(@AEntityFormInterceptor(SurgicalOperationSaveInterceptor.class))
@ACreateInterceptors(@AEntityFormInterceptor(SurgicalOperationSaveInterceptor.class))
@AViewInterceptors(
		@AEntityFormInterceptor(SurgicalOperationViewInterceptor.class)
)
public class SurgicalOperationForm extends IdEntityForm{
	/** Дата операции */
	@Comment("Дата операции")
	@Persist @Required
	@DateString @DoDateString @MaxDateCurrent
	public String getOperationDate() {return theOperationDate;}
	public void setOperationDate(String aOperationDate) {theOperationDate = aOperationDate;	}

	/** Время операции */
	@Comment("Время операции")
	@Persist @Required
	@TimeString @DoTimeString
	public String getOperationTime() {return theOperationTime;}
	public void setOperationTime(String aOperationTime) {theOperationTime = aOperationTime;	}

	/** Хирург */
	@Comment("Хирург")
	@Persist @Required
	public Long getSurgeon() {return theSurgeon;	}
	public void setSurgeon(Long aSurgeon) {theSurgeon = aSurgeon;}
	
	/** Отделение */
	@Comment("Отделение")
	@Persist @Required
	public Long getDepartment() {return theDepartment;}
	public void setDepartment(Long aDepartment) {theDepartment = aDepartment;}

	/** Операция */
	@Comment("Операция")
	@Persist 
	public Long getOperation() {return theOperation;}
	public void setOperation(Long aOperation) {theOperation = aOperation;}

	/** Анестизия */
	@Comment("Анестезия")
	public Long getAnesthesia() {return theAnesthesia;}
	public void setAnesthesia(Long aAnesthesia) {theAnesthesia = aAnesthesia;	}

	/** Флаг основной операции */
	@Comment("Флаг основной операции")
	@Persist 
	public Boolean getBase() {return theBase;	}
	public void setBase(Boolean aBase) {theBase = aBase;}

	/** Флаг использования эндоскопии */
	@Comment("Флаг использования эндоскопии")
	@Persist 
	public Boolean getEndoscopyUse() {return theEndoscopyUse;	}
	public void setEndoscopyUse(Boolean aEndoscopyUse) {theEndoscopyUse = aEndoscopyUse;	}

	/** Флаг использования лазерной аппаратуры */
	@Comment("Флаг использования лазерной аппаратуры")
	@Persist 
	public Boolean getLaserUse() {	return theLaserUse;	}
	public void setLaserUse(Boolean aLaserUse) {theLaserUse = aLaserUse;}

	/** Флаг использования криогенной аппаратуры */
	@Comment("Флан использования криогенной аппаратуры")
	@Persist 
	public Boolean getCryogenicUse() {return theCryogenicUse;	}
	public void setCryogenicUse(Boolean aCryogenicUse) {theCryogenicUse = aCryogenicUse;	}

	/** Случай медицинского обслуживания */
	@Comment("Случай медицинского обслуживания")
	@Persist
	public Long getMedCase() {return theMedCase;}
	public void setMedCase(Long aMedCase) {theMedCase = aMedCase;}

	/** Пациент */
	@Comment("Пациент")
	@Persist
	public Long getPatient() {return thePatient;}
	public void setPatient(Long aPatient) {thePatient = aPatient;}

	/** Лечебное учреждение */
	@Comment("Лечебное учреждение")
	@Persist
	public Long getLpu() {return theLpu;}
	public void setLpu(Long aLpu) {theLpu = aLpu;}

	/** Кол-во  анастезии */
	@Comment("Кол-во  анастезии")
	@IntegerString @DoIntegerString
	public String getAnesthesiaAmount() {return theAnesthesiaAmount;}
	public void setAnesthesiaAmount(String aAnesthesiaAmount) {theAnesthesiaAmount = aAnesthesiaAmount;}

	/** Дата операции по */
	@Comment("Дата операции по")
	@DateString @DoDateString @MaxDateCurrent
	@Persist
	public String getOperationDateTo() {return theOperationDateTo;}
	public void setOperationDateTo(String aOperationDateTo) {theOperationDateTo = aOperationDateTo;}

	
	/** Анестезиолог */
	@Comment("Анестезиолог")
	public Long getAnaesthetist() {return theAnaesthetist;}
	public void setAnaesthetist(Long aAnaesthetist) {theAnaesthetist = aAnaesthetist;}

	
	/** Операционная медсестра */
	@Comment("Операционная медсестра")
	@Persist
	public Long getOperatingNurse() {return theOperatingNurse;}
	public void setOperatingNurse(Long aOperatingNurse) {theOperatingNurse = aOperatingNurse;}

	
	/** Предоперационный эпикриз */
	@Comment("Предоперационный эпикриз")
	@Persist
	public String getPreoperativeEpicrisis() {return thePreoperativeEpicrisis;}
	public void setPreoperativeEpicrisis(String aPreoperativeEpicrisis) {thePreoperativeEpicrisis = aPreoperativeEpicrisis;}

	
	/** Описание операции */
	@Comment("Описание операции")
	@Persist
	public String getOperationText() {return theOperationText;}
	public void setOperationText(String aOperationText) {theOperationText = aOperationText;}

	
	/** МКБ до операции */
	@Comment("МКБ до операции")
	@Persist
	public Long getIdc10Before() {return theIdc10Before;}
	public void setIdc10Before(Long aIdc10Before) {theIdc10Before = aIdc10Before;}

	
	/** МКБ после операции */
	@Comment("МКБ после операции")
	@Persist 
	public Long getIdc10After() {return theIdc10After;}
	public void setIdc10After(Long aIdc10After) {theIdc10After = aIdc10After;}

	
	/** Гистологическое исследование */
	@Comment("Гистологическое исследование")
	@Persist
	public String getHistologicalStudy() {return theHistologicalStudy;}
	public void setHistologicalStudy(String aHistologicalStudy) {theHistologicalStudy = aHistologicalStudy;	}

	
	/** Хирурги */
	@Comment("Хирурги")
	@Persist @PersistManyToManyOneProperty(collectionGenericType=WorkFunction.class)
	public String getSurgeonFunctions() {return theSurgeonFunctions;}
	public void setSurgeonFunctions(String aSurgeonFunctions) {theSurgeonFunctions = aSurgeonFunctions;}

	/** Хирурги */
	private String theSurgeonFunctions;
	/** Время операции по */
	@Comment("Время операции по")
	@Persist
	@TimeString @DoTimeString
	public String getOperationTimeTo() {return theOperationTimeTo;}
	public void setOperationTimeTo(String aOperationTimeTo) {theOperationTimeTo = aOperationTimeTo;}

	/** Информация о хир.операции */
	@Comment("Информация о хир.операции")
	@Persist
	public String getInformation() {return theInformation;}
	public void setInformation(String aInformation) {theInformation = aInformation;}
	
	/** Период */
	@Comment("Период")
	@Persist
	public String getPeriod() {return thePeriod;}
	public void setPeriod(String aPeriod) {thePeriod = aPeriod;}

	/** Информация о пациенте */
	@Comment("Информация о пациенте")
	@Persist
	public String getPatientInfo() {return thePatientInfo;}
	public void setPatientInfo(String aPatientInfo) {thePatientInfo = aPatientInfo;}

	 /** Экстренность */
	@Comment("Экстренность")
	@Persist
	public Boolean getEmergency() {
		return theEmergency;
	}

	public void setEmergency(Boolean aEmergency) {
		theEmergency = aEmergency;
	}

	/** Экстренность */
	private Boolean theEmergency;
	
	/** Малая */
	@Comment("Малая")
	@Persist
	public Boolean getMinor() {
		return theMinor;
	}

	public void setMinor(Boolean aMinor) {
		theMinor = aMinor;
	}

	/** Номер в журнале */
	@Comment("Номер в журнале")
	@Persist
	public String getNumberInJournal() {return theNumberInJournal;}
	public void setNumberInJournal(String aNumberInJournal) {theNumberInJournal = aNumberInJournal;}

	/** Профиль */
	@Comment("Профиль")
	@Persist
	public Long getProfile() {return theProfile;}
	public void setProfile(Long aProfile) {theProfile = aProfile;}

	/** Операция с использованием высоких медицинских технологий */
	@Comment("Операция с использованием высоких медицинских технологий")
	@Persist 
	public Long getTechnology() {return theTechnology;}
	public void setTechnology(Long aTechnology) {theTechnology = aTechnology;}

	/** Показания для операции */
	@Comment("Показания для операции")
	@Persist @Required
	public Long getAspect() {return theAspect;}
	public void setAspect(Long aAspect) {theAspect = aAspect;}

	/** Исход операции */
	@Comment("Исход операции")
	@Persist 
	public Long getOutcome() {return theOutcome;}
	public void setOutcome(Long aOutcome) {theOutcome = aOutcome;}

	
	/** Метод операции */
	@Comment("Метод операции")
	@Persist
	public Long getMethod() {return theMethod;}
	public void setMethod(Long aMethod) {theMethod = aMethod;}

	/** Метод операции */
	private Long theMethod;
	/** Исход операции */
	private Long theOutcome;
	/** Показания для операции */
	private Long theAspect;
	/** Операция с использованием высоких медицинских технологий */
	private Long theTechnology;
	/** Профиль */
	private Long theProfile;
	/** Номер в журнале */
	private String theNumberInJournal;
	/** Малая */
	private Boolean theMinor;

	/** Информация о пациенте */
	private String thePatientInfo;
	/** Период */
	private String thePeriod;
	/** Информация о хир.операции */
	private String theInformation;
	/** Время операции по */
	private String theOperationTimeTo;
	/** Гистологическое исследование */
	private String theHistologicalStudy;
	/** МКБ после операции */
	private Long theIdc10After;
	/** МКБ до операции */
	private Long theIdc10Before;
	/** Описание операции */
	private String theOperationText;
	/** Предоперационный эпикриз */
	private String thePreoperativeEpicrisis;
	/** Операционная медсестра */
	private Long theOperatingNurse;
	/** Анестезиолог */
	private Long theAnaesthetist;
	/** Дата операции по */
	private String theOperationDateTo;

	/** Рабочая функция врача, проводившего операцию */
	private Long theSurgeon;
	/** Кол-во  анастезии */
	private String theAnesthesiaAmount;
	
	/** Лечебное учреждение */
	private Long theLpu;
	/** Пациент */
	private Long thePatient;
	/** Случай медицинского обслуживания */
	private Long theMedCase;
	/** Флаг использования криогенной аппаратуры */
	private Boolean theCryogenicUse;
	/** Флаг использования лазерной аппаратуры */
	private Boolean theLaserUse;
	/** Флаг использования эндоскопии */
	private Boolean theEndoscopyUse;
	/** Флаг основной операции */
	private Boolean theBase;
	/** Анестезия */
	private Long theAnesthesia;
	/** Операция */
	private Long theOperation;
	/** Отделение */
	private Long theDepartment;
	/** Время операции */
	private String theOperationTime;
	/** Дата операции */
	private String theOperationDate;

	/** Дата создания */
	@Comment("Дата создания")
	@Persist @DoDateString @DateString
	public String getCreateDate() {return theCreateDate;}
	public void setCreateDate(String aCreateDate) {theCreateDate = aCreateDate;}
	
	/** Пользователь, создавший запись */
	@Comment("Пользователь, создавший запись")
	@Persist
	public String getCreateUsername() {return theCreateUsername;}
	public void setCreateUsername(String aUsername) {theCreateUsername = aUsername;}
	
	/** Дата редактирования */
	@Comment("Дата редактирования")
	@Persist @DoDateString @DateString
	public String getEditDate() {return theEditDate;}
	public void setEditDate(String aEditDate) {theEditDate = aEditDate;}
	
	/** Пользователь, последний изменявший запись */
	@Comment("Пользователь, последний изменявший запись")
	@Persist
	public String getEditUsername() {return theEditUsername;}
	public void setEditUsername(String aEditUsername) {theEditUsername = aEditUsername;}
	
	/** Поток обслуживания */
	@Comment("Поток обслуживания")
	@Persist @Required
	public Long getServiceStream() {return theServiceStream;}
	public void setServiceStream(Long aServiceStream) {theServiceStream = aServiceStream;}

	/** Поток обслуживания */
	private Long theServiceStream;

	/** Пользователь, последний изменявший запись */
	private String theEditUsername;
	/** Дата редактирования */
	private String theEditDate;
	/** Пользователь, создавший запись */
	private String theCreateUsername;
	/** Дата создания */
	private String theCreateDate;
	
	/** Мед. услуга */
	@Comment("Мед. услуга")
	@Persist @Required
	public Long getMedService() {return theMedService;}
	public void setMedService(Long aMedService) {theMedService = aMedService;}


	/** Мед. услуга */
	private Long theMedService;
	/** Аборта */
	@Comment("Аборта")
	@Persist
	public Long getAbortion() {return theAbortion;}
	public void setAbortion(Long aAbortion) {theAbortion = aAbortion;}

	/** Аборта */
	private Long theAbortion;
	
	/** Дата печати */
	@Comment("Дата печати")
	@Persist @DateString @DoDateString
	public String getPrintDate() {return thePrintDate;}
	public void setPrintDate(String aPrintDate) {thePrintDate = aPrintDate;}

	/** Дата печати */
	private String thePrintDate;
	
	/** Время печати */
	@Comment("Время печати")
	@Persist @TimeString @DoTimeString
	public String getPrintTime() {return thePrintTime;}
	public void setPrintTime(String aPrintTime) {thePrintTime = aPrintTime;}

	/** Время печати */
	private String thePrintTime;
	
	/** Пользователь, посл. распечат. документ */
	@Comment("Пользователь, посл. распечат. документ")
	@Persist
	public String getPrintUsername() {return thePrintUsername;}
	public void setPrintUsername(String aPrintUsername) {thePrintUsername = aPrintUsername;}

	/** Пользователь, посл. распечат. документ */
	private String thePrintUsername;
	
	/** Проводилась анестезия */
	@Comment("Проводилась анестезия")
	public Long getIsAnesthesia() {return theIsAnesthesia;}
	public void setIsAnesthesia(Long aIsAnesthesia) {theIsAnesthesia = aIsAnesthesia;}

	/** Проводилась анестезия */
	private Long theIsAnesthesia;
	
	/** Анестезия вида */
	@Comment("Анестезия вида")
	public Long getAnesthesiaType() {return theAnesthesiaType;}
	public void setAnesthesiaType(Long aAnesthesiaType) {theAnesthesiaType = aAnesthesiaType;}

	/** Анестезия вида */
	private Long theAnesthesiaType;
	
	/** Анестезия услуга */
	@Comment("Анестезия услуга")
	public Long getAnesthesiaService() {
		return theAnesthesiaService;
	}

	public void setAnesthesiaService(Long aAnesthesiaService) {
		theAnesthesiaService = aAnesthesiaService;
	}

	/** Анестезия услуга */
	private Long theAnesthesiaService;
	
	/** Длительность */
	@Comment("Длительность")
	public Integer getAnesthesiaDuration() {
		return theAnesthesiaDuration;
	}

	public void setAnesthesiaDuration(Integer aAnesthesiaDuration) {
		theAnesthesiaDuration = aAnesthesiaDuration;
	}

	/** Длительность */
	private Integer theAnesthesiaDuration;

	/** На какой конечности была сделана операция */
	@Comment("На какой конечности была сделана операция")
	@Persist
	public Long getLeftRight() {return theLeftRight;}
	public void setLeftRight(Long aLeftRight) {theLeftRight = aLeftRight;}
	/** На какой конечности была сделана операция */
	private Long theLeftRight ;

	/** Идентификатор */
	@Id
	@Comment("Идентификатор")
	public long getId() { return theId ; }
	public void setId(long aId) { theId = aId ; }
	/** Идентификатор */
	private long theId ;


	/** Класс раны */
	@Comment("Класс раны")
	@Persist
	public Long getClassWound() {return theClassWound;}
	public void setClassWound(Long aClassWound) {theClassWound = aClassWound;}
	/** Класс раны */
	private Long theClassWound ;

	/** Препарат периоперационной антибиотикопрофилактики */
	@Comment("Препарат периоперационной антибиотикопрофилактики")
	@Persist
	public Long getAntibioticDrug() {return theAntibioticDrug;}
	public void setAntibioticDrug(Long aAntibioticDrug) {theAntibioticDrug = aAntibioticDrug;}
	/** Препарат периоперационной антибиотикопрофилактики */
	private Long theAntibioticDrug ;

	/** Доза (мл) */
	@Comment("Доза (мл)")
	@Persist
	public Double getDose() {return theDose;}
	public void setDose(Double aDose) {theDose = aDose;}
	/** Доза (мл) */
	private Double theDose;

	/** Способы введения препаратов при периоперационной антибиотикопрофилактике */
	@Comment("Способы введения препаратов при периоперационной антибиотикопрофилактике")
	@Persist
	public Long getMethodsDrugAdm() {return theMethodsDrugAdm;}
	public void setMethodsDrugAdm(Long aMethodsDrugAdm) {theMethodsDrugAdm = aMethodsDrugAdm;}
	/** Способы введения препаратов при периоперационной антибиотикопрофилактике */
	private Long theMethodsDrugAdm ;

	/** Время первой дозы */
	@Comment("Время первой дозы")
	@TimeString @DoTimeString
	@Persist
	public String getFirstDoseTime() {return theFirstDoseTime;}
	public void setFirstDoseTime(String aFirstDoseTime) {theFirstDoseTime = aFirstDoseTime;	}
	/** Время первой дозы */
	private String theFirstDoseTime;

	/** Время повторной (при необходимости) дозы */
	@Comment("Время повторной (при необходимости) дозы")
	@TimeString @DoTimeString
	@Persist
	public String getSecondDoseTime() {return theSecondDoseTime;}
	public void setSecondDoseTime(String aSecondDoseTime) {theSecondDoseTime = aSecondDoseTime;	}
	/** Время повторной (при необходимости) дозы */
	private String theSecondDoseTime;

	/** данные для сохранения осложнений*/
	public String getAllComps() {
		return theAllComps;
	}
	public void setAllComps(String aAllComps) { theAllComps = aAllComps; }
	/** данные для сохранения осложнений*/
	private String theAllComps;
}
