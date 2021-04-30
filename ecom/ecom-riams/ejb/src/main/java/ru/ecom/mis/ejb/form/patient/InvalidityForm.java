package ru.ecom.mis.ejb.form.patient;

import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.patient.Invalidity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.transforms.DoTimeString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;
import ru.nuzmsh.forms.validator.validators.TimeString;

/**
 * Инвалидность
 */
@EntityForm
@EntityFormPersistance(clazz = Invalidity.class)
@Comment("Инвалидность")
@WebTrail(comment = "Инвалидность", nameProperties = "id", view = "entityParentView-mis_invalidity.do")
@Parent(property = "patient", parentForm =PatientForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Patient/Invalidity")
@Setter
public class InvalidityForm extends IdEntityForm{
	 /**
	  * Инвалид ВОВ
	  */
	 @Comment("Инвалид ВОВ")
	 @Persist
	 public Boolean getGreatePatrioticWarInvalid() {
	  return greatePatrioticWarInvalid;
	 }
	 /**
	  * Инвалид ВОВ
	  */
	 private Boolean greatePatrioticWarInvalid;
	 /**
	  * Главное нарушение состояния здоровья
	  */
	 @Comment("Главное нарушение состояния здоровья")
	 @Persist
	 public Long getHealthViolation() {
	  return healthViolation;
	 }
	 /**
	  * Главное нарушение состояния здоровья
	  */
	 private Long healthViolation;
	 /**
	  * Ведущее ограничение жизнедеятельности
	  */
	 @Comment("Ведущее ограничение жизнедеятельности")
	 @Persist
	 public Long getVitalRestriction() {
	  return vitalRestriction;
	 }
	 /**
	  * Ведущее ограничение жизнедеятельности
	  */
	 private Long vitalRestriction;
	 /**
	  * Место работы
	  */
	 @Comment("Место работы")
	 @Persist
	 public Long getWorkPlace() {
	  return workPlace;
	 }
	 /**
	  * Место работы
	  */
	 private Long workPlace;
	 /**
	  * Дата начала
	  */
	 @Comment("Дата начала")
	 @Persist @DateString @DoDateString @Required
	 public String getDateFrom() {
	  return dateFrom;
	 }
	 /**
	  * Дата начала
	  */
	 private String dateFrom;
	 /**
	  * Дата окончания
	  */
	 @Comment("Дата окончания")
	 @Persist @DateString @DoDateString
	 public String getDateTo() {
	  return dateTo;
	 }
	 /**
	  * Дата окончания
	  */
	 private String dateTo;
	 /**
	  * Группа
	  */
	 @Comment("Группа")
	 @Persist @Required
	 public Long getGroup() {
	  return group;
	 }
	 /**
	  * Группа
	  */
	 private Long group;
	 /**
	  * Пациент
	  */
	 @Comment("Пациент")
	 @Persist @Required
	 public Long getPatient() {
	  return patient;
	 }
	 /**
	  * Пациент
	  */
	 private Long patient;
	 /**
	  * Мкб10
	  */
	 @Comment("Мкб10")
	 @Persist 
	 public Long getIdc10() {
	  return idc10;
	 }
	 /**
	  * Мкб10
	  */
	 private Long idc10;
	 /**
	  * Диагноз
	  */
	 @Comment("Диагноз")
	 @Persist
	 public String getDiagnosis() {
	  return diagnosis;
	 }
	 /**
	  * Диагноз
	  */
	 private String diagnosis;
	 /**
	  * Дата последнего пересмотра
	  */
	 @Comment("Дата последнего пересмотра")
	 @Persist @DateString @DoDateString
	 public String getLastRevisionDate() {
	  return lastRevisionDate;
	 }
	 /**
	  * Дата последнего пересмотра
	  */
	 private String lastRevisionDate;
	 /**
	  * Дата следующего пересмотра
	  */
	 @Comment("Дата следующего пересмотра")
	 @Persist @DateString @DoDateString
	 public String getNextRevisionDate() {
	  return nextRevisionDate;
	 }
	 /**
	  * Дата следующего пересмотра
	  */
	 private String nextRevisionDate;
	 /**
	  * Инвалид с детства
	  */
	 @Comment("Инвалид с детства")
	 @Persist
	 public Boolean getChildhoodInvalid() {
	  return childhoodInvalid;
	 }
	 /**
	  * Инвалид с детства
	  */
	 private Boolean childhoodInvalid;
	 
	 /** Дата постановки (впервые) */
	@Comment("Дата постановки (впервые)")
	@Persist @DateString @DoDateString
	public String getFirstDiscloseDate() {
		return firstDiscloseDate;
	}

	/** Дата постановки (впервые) */
	private String firstDiscloseDate;
	
	/** Дата освидетельствования */
	@Comment("Дата освидетельствования")
	@Persist @DateString @DoDateString
	public String getRevisionDate() {
		return revisionDate;
	}

	/** Дата освидетельствования */
	private String revisionDate;
	
	/** Категория работника */
	@Comment("Категория работника")
	@Persist
	public Long getCategoryWorker() {
		return categoryWorker;
	}

	/** Категория работника */
	private Long categoryWorker;
	
	/** Место регистрации */
	@Comment("Место регистрации")
	@Persist @Required
	public Long getRegistrationPlace() {
		return registrationPlace;
	}

	/** Место регистрации */
	private Long registrationPlace;
	
	/** Профиль заболевания */
	@Comment("Профиль заболевания")
	@Persist 
	public Long getProfileIllness() {
		return profileIllness;
	}

	/** Профиль заболевания */
	private Long profileIllness;
	
	/** Работа в условиях профвредности */
	@Comment("Работа в условиях профвредности")
	@Persist
	public Boolean getWorkProfDisutility() {
		return workProfDisutility;
	}

	/** Работа в условиях профвредности */
	private Boolean workProfDisutility;
	
	/** Стаж работы в отрасли */
	@Comment("Стаж работы в отрасли")
	@Persist
	public Integer getSeniority() {
		return seniority;
	}

	/** Стаж работы в отрасли */
	private Integer seniority;
	
	/** Стаж работы в нефтегазовой промышленности */
	@Comment("Стаж работы в нефтегазовой промышленности")
	@Persist
	public Integer getSeniorityNGP() {
		return seniorityNGP;
	}

	/** Стаж работы в нефтегазовой промышленности */
	private Integer seniorityNGP;
	
	/** Трудоспособен */
	@Comment("Трудоспособен")
	@Persist
	public Boolean getIsWorking() {
		return isWorking;
	}

	/** Трудоспособен */
	private Boolean isWorking;
	
	/** Первичность */
	@Comment("Первичность")
	@Persist
	public Boolean getInitial() {return initial;}

	/** Без переосвидетельствования */
	@Comment("Без переосвидетельствования")
	@Persist
	public Boolean getWithoutExam() {return withoutExam;}

	/** Без переосвидетельствования */
	private Boolean withoutExam;
	
	/** Недееспособный */
	@Comment("Недееспособный")
	@Persist
	public Boolean getIncapable() {return incapable;}

	/** Первичность */
	private Boolean initial;
	/** Недееспособный */
	private Boolean incapable;
	/** Суд */
	@Comment("Суд")
	@Persist
	public Long getLawCourt() {return lawCourt;}

	/** Дата суда */
	@Comment("Дата суда")
	@Persist @DoDateString @DateString
	public String getLawCourtDate() {return lawCourtDate;}

	/** Дата суда */
	private String lawCourtDate;
	/** Суд */
	private Long lawCourt;

	/** Дата создания */
	@Comment("Дата создания")
	@DateString @DoDateString @Persist
	public String getCreateDate() {return createDate;}

	/** Дата редактирования */
	@Comment("Дата редактирования")
	@DateString @DoDateString @Persist
	public String getEditDate() {return editDate;}

	/** Время создания */
	@Comment("Время создания")
	@TimeString @DoTimeString @Persist
	public String getCreateTime() {return createTime;}
	/** Время редактрования */
	@Comment("Время редактрования")
	@TimeString @DoTimeString @Persist
	public String getEditTime() {return editTime;}
	/** Пользователь, который создал запись */
	@Comment("Пользователь, который создал запись")
	@Persist
	public String getCreateUsername() {return createUsername;}
	/** Пользователь, который последний редактировал запись */
	@Comment("Пользователь, который последний редактировал запись")
	@Persist
	public String getEditUsername() {return editUsername;}

	/** Пользователь, который последний редактировал запись */
	private String editUsername;
	/** Пользователь, который создал запись */
	private String createUsername;
	/** Время редактрования */
	private String editTime;
	/** Время создания */
	private String createTime;
	/** Дата редактирования */
	private String editDate;
	/** Дата создания */
	private String createDate;
	
	/** Есть другая инвалидность */
	@Comment("Есть другая инвалидность")
	@Persist
	public Boolean getIsOtherInvalidity() {return isOtherInvalidity;}

	/** Есть другая инвалидность */
	private Boolean isOtherInvalidity;

}
