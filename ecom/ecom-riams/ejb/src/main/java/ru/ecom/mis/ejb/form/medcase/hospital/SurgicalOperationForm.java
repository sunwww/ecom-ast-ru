package ru.ecom.mis.ejb.form.medcase.hospital;

import lombok.Setter;
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
@Setter
public class SurgicalOperationForm extends IdEntityForm{
	/** Дата операции */
	@Comment("Дата операции")
	@Persist @Required
	@DateString @DoDateString @MaxDateCurrent
	public String getOperationDate() {return operationDate;}

	/** Время операции */
	@Comment("Время операции")
	@Persist @Required
	@TimeString @DoTimeString
	public String getOperationTime() {return operationTime;}

	/** Хирург */
	@Comment("Хирург")
	@Persist @Required
	public Long getSurgeon() {return surgeon;	}

	/** Отделение */
	@Comment("Отделение")
	@Persist @Required
	public Long getDepartment() {return department;}

	/** Операция */
	@Comment("Операция")
	@Persist 
	public Long getOperation() {return operation;}

	/** Анестизия */
	@Comment("Анестезия")
	public Long getAnesthesia() {return anesthesia;}

	/** Флаг основной операции */
	@Comment("Флаг основной операции")
	@Persist 
	public boolean isBase() {return base;	}

	/** Флаг использования эндоскопии */
	@Comment("Флаг использования эндоскопии")
	@Persist 
	public boolean isEndoscopyUse() {return endoscopyUse;	}

	/** Флаг использования лазерной аппаратуры */
	@Comment("Флаг использования лазерной аппаратуры")
	@Persist 
	public boolean isLaserUse() {	return laserUse;	}

	/** Флаг использования криогенной аппаратуры */
	@Comment("Флан использования криогенной аппаратуры")
	@Persist 
	public boolean isCryogenicUse() {return cryogenicUse;	}

	/** Случай медицинского обслуживания */
	@Comment("Случай медицинского обслуживания")
	@Persist
	public Long getMedCase() {return medCase;}

	/** Пациент */
	@Comment("Пациент")
	@Persist
	public Long getPatient() {return patient;}

	/** Лечебное учреждение */
	@Comment("Лечебное учреждение")
	@Persist
	public Long getLpu() {return lpu;}

	/** Кол-во  анастезии */
	@Comment("Кол-во  анастезии")
	@IntegerString @DoIntegerString
	public String getAnesthesiaAmount() {return anesthesiaAmount;}

	/** Дата операции по */
	@Comment("Дата операции по")
	@DateString @DoDateString @MaxDateCurrent
	@Persist
	public String getOperationDateTo() {return operationDateTo;}

	
	/** Анестезиолог */
	@Comment("Анестезиолог")
	public Long getAnaesthetist() {return anaesthetist;}

	
	/** Операционная медсестра */
	@Comment("Операционная медсестра")
	@Persist
	public Long getOperatingNurse() {return operatingNurse;}

	
	/** Предоперационный эпикриз */
	@Comment("Предоперационный эпикриз")
	@Persist
	public String getPreoperativeEpicrisis() {return preoperativeEpicrisis;}

	
	/** Описание операции */
	@Comment("Описание операции")
	@Persist
	public String getOperationText() {return operationText;}

	
	/** МКБ до операции */
	@Comment("МКБ до операции")
	@Persist
	public Long getIdc10Before() {return idc10Before;}

	
	/** МКБ после операции */
	@Comment("МКБ после операции")
	@Persist 
	public Long getIdc10After() {return idc10After;}

	
	/** Гистологическое исследование */
	@Comment("Гистологическое исследование")
	@Persist
	public String getHistologicalStudy() {return histologicalStudy;}

	
	/** Хирурги */
	@Comment("Хирурги")
	@Persist @PersistManyToManyOneProperty(collectionGenericType=WorkFunction.class)
	public String getSurgeonFunctions() {return surgeonFunctions;}

	/** Хирурги */
	private String surgeonFunctions;
	/** Время операции по */
	@Comment("Время операции по")
	@Persist
	@TimeString @DoTimeString
	public String getOperationTimeTo() {return operationTimeTo;}

	/** Информация о хир.операции */
	@Comment("Информация о хир.операции")
	@Persist
	public String getInformation() {return information;}

	/** Период */
	@Comment("Период")
	@Persist
	public String getPeriod() {return period;}

	/** Информация о пациенте */
	@Comment("Информация о пациенте")
	@Persist
	public String getPatientInfo() {return patientInfo;}

	 /** Экстренность */
	@Comment("Экстренность")
	@Persist
	public Boolean getEmergency() {
		return emergency;
	}

	/** Экстренность */
	private Boolean emergency;
	
	/** Малая */
	@Comment("Малая")
	@Persist
	public Boolean getMinor() {
		return minor;
	}

	/** Номер в журнале */
	@Comment("Номер в журнале")
	@Persist
	public String getNumberInJournal() {return numberInJournal;}

	/** Профиль */
	@Comment("Профиль")
	@Persist
	public Long getProfile() {return profile;}

	/** Операция с использованием высоких медицинских технологий */
	@Comment("Операция с использованием высоких медицинских технологий")
	@Persist 
	public Long getTechnology() {return technology;}

	/** Показания для операции */
	@Comment("Показания для операции")
	@Persist @Required
	public Long getAspect() {return aspect;}

	/** Исход операции */
	@Comment("Исход операции")
	@Persist 
	public Long getOutcome() {return outcome;}

	
	/** Метод операции */
	@Comment("Метод операции")
	@Persist
	public Long getMethod() {return method;}

	/** Метод операции */
	private Long method;
	/** Исход операции */
	private Long outcome;
	/** Показания для операции */
	private Long aspect;
	/** Операция с использованием высоких медицинских технологий */
	private Long technology;
	/** Профиль */
	private Long profile;
	/** Номер в журнале */
	private String numberInJournal;
	/** Малая */
	private Boolean minor;

	/** Информация о пациенте */
	private String patientInfo;
	/** Период */
	private String period;
	/** Информация о хир.операции */
	private String information;
	/** Время операции по */
	private String operationTimeTo;
	/** Гистологическое исследование */
	private String histologicalStudy;
	/** МКБ после операции */
	private Long idc10After;
	/** МКБ до операции */
	private Long idc10Before;
	/** Описание операции */
	private String operationText;
	/** Предоперационный эпикриз */
	private String preoperativeEpicrisis;
	/** Операционная медсестра */
	private Long operatingNurse;
	/** Анестезиолог */
	private Long anaesthetist;
	/** Дата операции по */
	private String operationDateTo;

	/** Рабочая функция врача, проводившего операцию */
	private Long surgeon;
	/** Кол-во  анастезии */
	private String anesthesiaAmount;
	
	/** Лечебное учреждение */
	private Long lpu;
	/** Пациент */
	private Long patient;
	/** Случай медицинского обслуживания */
	private Long medCase;
	/** Флаг использования криогенной аппаратуры */
	private boolean cryogenicUse;
	/** Флаг использования лазерной аппаратуры */
	private boolean laserUse;
	/** Флаг использования эндоскопии */
	private boolean endoscopyUse;
	/** Флаг основной операции */
	private boolean base;
	/** Анестезия */
	private Long anesthesia;
	/** Операция */
	private Long operation;
	/** Отделение */
	private Long department;
	/** Время операции */
	private String operationTime;
	/** Дата операции */
	private String operationDate;

	/** Дата создания */
	@Comment("Дата создания")
	@Persist @DoDateString @DateString
	public String getCreateDate() {return createDate;}

	/** Пользователь, создавший запись */
	@Comment("Пользователь, создавший запись")
	@Persist
	public String getCreateUsername() {return createUsername;}

	/** Дата редактирования */
	@Comment("Дата редактирования")
	@Persist @DoDateString @DateString
	public String getEditDate() {return editDate;}

	/** Пользователь, последний изменявший запись */
	@Comment("Пользователь, последний изменявший запись")
	@Persist
	public String getEditUsername() {return editUsername;}

	/** Поток обслуживания */
	@Comment("Поток обслуживания")
	@Persist @Required
	public Long getServiceStream() {return serviceStream;}

	/** Поток обслуживания */
	private Long serviceStream;

	/** Пользователь, последний изменявший запись */
	private String editUsername;
	/** Дата редактирования */
	private String editDate;
	/** Пользователь, создавший запись */
	private String createUsername;
	/** Дата создания */
	private String createDate;
	
	/** Мед. услуга */
	@Comment("Мед. услуга")
	@Persist @Required
	public Long getMedService() {return medService;}

	/** Мед. услуга */
	private Long medService;
	/** Аборта */
	@Comment("Аборта")
	@Persist
	public Long getAbortion() {return abortion;}

	/** Аборта */
	private Long abortion;
	
	/** Дата печати */
	@Comment("Дата печати")
	@Persist @DateString @DoDateString
	public String getPrintDate() {return printDate;}

	/** Дата печати */
	private String printDate;
	
	/** Время печати */
	@Comment("Время печати")
	@Persist @TimeString @DoTimeString
	public String getPrintTime() {return printTime;}

	/** Время печати */
	private String printTime;
	
	/** Пользователь, посл. распечат. документ */
	@Comment("Пользователь, посл. распечат. документ")
	@Persist
	public String getPrintUsername() {return printUsername;}

	/** Пользователь, посл. распечат. документ */
	private String printUsername;
	
	/** Проводилась анестезия */
	@Comment("Проводилась анестезия")
	public Long getIsAnesthesia() {return isAnesthesia;}

	/** Проводилась анестезия */
	private Long isAnesthesia;
	
	/** Анестезия вида */
	@Comment("Анестезия вида")
	public Long getAnesthesiaType() {return anesthesiaType;}

	/** Анестезия вида */
	private Long anesthesiaType;
	
	/** Анестезия услуга */
	@Comment("Анестезия услуга")
	public Long getAnesthesiaService() {
		return anesthesiaService;
	}

	/** Анестезия услуга */
	private Long anesthesiaService;
	
	/** Длительность */
	@Comment("Длительность")
	public Integer getAnesthesiaDuration() {
		return anesthesiaDuration;
	}

	/** Длительность */
	private Integer anesthesiaDuration;

	/** На какой конечности была сделана операция */
	@Comment("На какой конечности была сделана операция")
	@Persist
	public Long getLeftRight() {return leftRight;}
	/** На какой конечности была сделана операция */
	private Long leftRight ;

	/** Идентификатор */
	@Id
	@Comment("Идентификатор")
	public long getId() { return id ; }
	/** Идентификатор */
	private long id ;


	/** Класс раны */
	@Comment("Класс раны")
	@Persist
	public Long getClassWound() {return classWound;}
	/** Класс раны */
	private Long classWound ;

	/** Препарат периоперационной антибиотикопрофилактики */
	@Comment("Препарат периоперационной антибиотикопрофилактики")
	@Persist
	public Long getAntibioticDrug() {return antibioticDrug;}
	/** Препарат периоперационной антибиотикопрофилактики */
	private Long antibioticDrug ;

	/** Доза (мл) */
	@Comment("Доза (мл)")
	@Persist
	public Double getDose() {return dose;}
	/** Доза (мл) */
	private Double dose;

	/** Способы введения препаратов при периоперационной антибиотикопрофилактике */
	@Comment("Способы введения препаратов при периоперационной антибиотикопрофилактике")
	@Persist
	public Long getMethodsDrugAdm() {return methodsDrugAdm;}
	/** Способы введения препаратов при периоперационной антибиотикопрофилактике */
	private Long methodsDrugAdm ;

	/** Время первой дозы */
	@Comment("Время первой дозы")
	@TimeString @DoTimeString
	@Persist
	public String getFirstDoseTime() {return firstDoseTime;}
	/** Время первой дозы */
	private String firstDoseTime;

	/** Время повторной (при необходимости) дозы */
	@Comment("Время повторной (при необходимости) дозы")
	@TimeString @DoTimeString
	@Persist
	public String getSecondDoseTime() {return secondDoseTime;}
	/** Время повторной (при необходимости) дозы */
	private String secondDoseTime;

	/** данные для сохранения осложнений*/
	public String getAllComps() {
		return allComps;
	}
	/** данные для сохранения осложнений*/
	private String allComps;
}
