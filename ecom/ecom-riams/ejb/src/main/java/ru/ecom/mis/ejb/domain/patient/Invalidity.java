package ru.ecom.mis.ejb.domain.patient;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.ejb.services.live.DeleteListener;
import ru.ecom.expomc.ejb.domain.med.VocIdc10;
import ru.ecom.mis.ejb.domain.disability.voc.VocInvalidity;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.ecom.mis.ejb.domain.patient.voc.VocCategoryWorker;
import ru.ecom.mis.ejb.domain.patient.voc.VocInvalidityHealthViolation;
import ru.ecom.mis.ejb.domain.patient.voc.VocInvalidityVitalRestriction;
import ru.ecom.mis.ejb.domain.patient.voc.VocInvalidWorkPlace;
import ru.ecom.mis.ejb.domain.patient.voc.VocProfileIllness;
import ru.ecom.mis.ejb.domain.psychiatry.voc.VocLawCourt;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Инвалидность
 */

@Entity
@AIndexes({
	@AIndex(properties={"patient"})
}
)
@Table(schema="SQLUser")
@EntityListeners(DeleteListener.class)
@Getter
@Setter
public class Invalidity extends BaseEntity{
	 /**
	  * Инвалид ВОВ
	  */
	 @Comment("Инвалид ВОВ")
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
	 @OneToOne
	 public VocInvalidityHealthViolation getHealthViolation() {
	  return healthViolation;
	 }
	 /**
	  * Главное нарушение состояния здоровья
	  */
	 private VocInvalidityHealthViolation healthViolation;
	 /**
	  * Ведущее ограничение жизнедеятельности
	  */
	 @Comment("Ведущее ограничение жизнедеятельности")
	 @OneToOne
	 public VocInvalidityVitalRestriction getVitalRestriction() {
	  return vitalRestriction;
	 }
	 /**
	  * Ведущее ограничение жизнедеятельности
	  */
	 private VocInvalidityVitalRestriction vitalRestriction;
	 /**
	  * Место работы
	  */
	 @Comment("Место работы")
	 @OneToOne
	 public VocInvalidWorkPlace getWorkPlace() {
	  return workPlace;
	 }
	 /**
	  * Место работы
	  */
	 private VocInvalidWorkPlace workPlace;
	 /**
	  * Дата начала
	  */
	 private Date dateFrom;
	 /**
	  * Дата окончания
	  */
	 private Date dateTo;
	 /**
	  * Группа
	  */
	 @Comment("Группа")
	 @OneToOne
	 public VocInvalidity getGroup() {
	  return group;
	 }
	 /**
	  * Группа
	  */
	 private VocInvalidity group;
	 /**
	  * Пациент
	  */
	 @Comment("Пациент")
	 @OneToOne
	 public Patient getPatient() {
	  return patient;
	 }
	 /**
	  * Пациент
	  */
	 private Patient patient;
	 /**
	  * Мкб10
	  */
	 @Comment("Мкб10")
	 @OneToOne
	 public VocIdc10 getIdc10() {
	  return idc10;
	 }
	 /**
	  * Мкб10
	  */
	 private VocIdc10 idc10;
	 /**
	  * Диагноз
	  */
	 private String diagnosis;
	 /**
	  * Дата последнего пересмотра
	  */
	 private Date lastRevisionDate;
	 /**
	  * Дата следующего пересмотра
	  */
	 private Date nextRevisionDate;
	 /**
	  * Инвалид с детства
	  */
	 private Boolean childhoodInvalid;
	 
	/** Дата постановки (впервые) */
	private Date firstDiscloseDate;
	
	/** Дата освидетельствования */
	private Date revisionDate;
	
	/** Категория работника */
	@Comment("Категория работника")
	@OneToOne
	public VocCategoryWorker getCategoryWorker() {
		return categoryWorker;
	}

	/** Категория работника */
	private VocCategoryWorker categoryWorker;
	
	/** Место регистрации */
	@Comment("Место регистрации")
	@OneToOne
	public MisLpu getRegistrationPlace() {
		return registrationPlace;
	}

	/** Место регистрации */
	private MisLpu registrationPlace;
	
	/** Профиль заболевания */
	@Comment("Профиль заболевания")
	@OneToOne
	public VocProfileIllness getProfileIllness() {
		return profileIllness;
	}

	/** Профиль заболевания */
	private VocProfileIllness profileIllness;

	/** Работа в условиях профвредности */
	private Boolean workProfDisutility;
	
	/** Стаж работы в отрасли */
	private Integer seniority;
	
	/** Стаж работы в нефтегазовой промышленности */
	private Integer seniorityNGP;
	
	/** Трудоспособен */
	private Boolean isWorking;
	
	/** Есть другая инвалидность */
	private Boolean isOtherInvalidity;
	
	/** Первичность */
	private Boolean initial;
	
	/** Без переосвидетельствования */
	private Boolean withoutExam;

	/** Недееспособный */
	private Boolean incapable;
	
	/** Суд */
	@Comment("Суд")
	@OneToOne
	public VocLawCourt getLawCourt() {return lawCourt;}

	/** Дата суда */
	private Date lawCourtDate;
	/** Суд */
	private VocLawCourt lawCourt;
	/** Пользователь, который последний редактировал запись */
	private String editUsername;
	/** Пользователь, который создал запись */
	private String createUsername;
	/** Время редактрования */
	private Time editTime;
	/** Время создания */
	private Time createTime;
	/** Дата редактирования */
	private Date editDate;
	/** Дата создания */
	private Date createDate;
}
