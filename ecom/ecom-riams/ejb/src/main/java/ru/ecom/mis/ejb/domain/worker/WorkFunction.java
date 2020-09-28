package ru.ecom.mis.ejb.domain.worker;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.equipment.KkmEquipment;
import ru.ecom.mis.ejb.domain.lpu.CopyingEquipment;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.ecom.mis.ejb.domain.lpu.WorkPlace;
import ru.ecom.mis.ejb.domain.workcalendar.WorkCalendar;
import ru.ecom.mis.ejb.domain.worker.voc.VocAcademicDegree;
import ru.ecom.mis.ejb.domain.worker.voc.VocCategory;
import ru.ecom.mis.ejb.domain.worker.voc.VocWorkFunction;
import ru.ecom.queue.domain.voc.VocQueue;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;

/**
 * Место работы
 */
@Entity
@Comment("Рабочая функция")
@AIndexes({
	@AIndex(properties="workFunction")
	,@AIndex(properties={"code"})
})
@Table(schema="SQLUser")
abstract public class WorkFunction extends BaseEntity {

	/** Дата начала работы */
	@Comment("Дата начала работы")
	public Date getStartDate() {return theStartDate;}
	public void setStartDate(Date aStartDate) {theStartDate = aStartDate;}
	/** Дата начала работы */
	private Date theStartDate;

	/** Дата окончания работы */
	@Comment("Дата окончания работы")
	public Date getFinishDate() {return theFinishDate;}
	public void setFinishDate(Date aFinishDate) {theFinishDate = aFinishDate;}
	/** Дата окончания работы */
	private Date theFinishDate;

	/** Рабочий календарь */
	@Comment("Рабочий календарь")
	@OneToOne
	@Deprecated
	public WorkCalendar getWorkCalendar() {
		return theWorkCalendar;
	}

	public void setWorkCalendar(WorkCalendar aWorkCalendars) {
		theWorkCalendar = aWorkCalendars;
	}

	/** Рабочий календарь */
	private WorkCalendar theWorkCalendar;
  
    /** Наименование */
	@Comment("Наименование")
	@Transient
	public String getName() {
		StringBuilder ret = new StringBuilder() ;
		if (theDegrees!=null) {ret.append(theDegrees.getCode()).append(" ");}
		if (theWorkFunction!=null) ret.append(theWorkFunction.getName()) ;
		return ret.toString() ;
	}
	
	
	@Transient @Comment("Информация")
	public String getWorkFunctionInfo() {
		return getName() ;
	}

	@Transient @Comment("Информация по коду ОМС врача")
	public String getOmcCodeInfo() {
		String ret="" ;
		if (theWorkFunction!=null && theWorkFunction.getVocPost()!=null
				&&theWorkFunction.getVocPost().getOmcDoctorPost()!=null) {
			ret = theWorkFunction.getVocPost().getOmcDoctorPost().getCode();
		}
		return ret ;
	}
	
	@Transient
	public MisLpu getLpuRegister() {
		return null ;
	}
	
	/** Функция */
	@Comment("Функция")
	@OneToOne
	public VocWorkFunction getWorkFunction() {
		return theWorkFunction;
	}

	public void setWorkFunction(VocWorkFunction aWorkFunction) {
		theWorkFunction = aWorkFunction;
	}

	/** Функция */
	private VocWorkFunction theWorkFunction;    
	
	/** Поместить в архив */
	@Comment("Поместить в архив")
	public Boolean getArchival() {
		return theArchival;
	}

	public void setArchival(Boolean aArchival) {
		theArchival = aArchival;
	}

	/** Поместить в архив */
	private Boolean theArchival;
	
	@Transient
	public String getWorkerLpuInfo() {
		return "";
	}
	@Transient 
	public String getInfo() {
		return "" ;
	}
	@Transient
	public boolean getViewDirect() {
		return true ;
	}
	@Transient
	public String getWorkerInfo() {
		return "" ;
	}
	
	/** ЛПУ */
	@Comment("ЛПУ")
	@ManyToOne
	public MisLpu getLpu() {return theLpu;}
	public void setLpu(MisLpu aLpu) {theLpu = aLpu;}
	/** ЛПУ */
	private MisLpu theLpu;
	/** Код специалиста */
	@Comment("Код специалиста")
	public String getCode() {return theCode;}
	public void setCode(String aCode) {theCode = aCode;}

	/** Хирургическая специальность */
	@Comment("Хирургическая специальность")
	public Boolean getIsSurgical() {return theIsSurgical;}
	public void setIsSurgical(Boolean aIsSurgical) {theIsSurgical = aIsSurgical;}

	/** Администратор */
	@Comment("Администратор")
	public Boolean getIsAdministrator() {return theIsAdministrator;}
	public void setIsAdministrator(Boolean aAdministrator) {theIsAdministrator = aAdministrator;}

	/** WorkPlace */
	@Comment("WorkPlace")
	@OneToOne
	public WorkPlace getWorkPlace() {return theWorkPlace;}
	public void setWorkPlace(WorkPlace aWorkPlace) {theWorkPlace = aWorkPlace;}

	/** WorkPlace */
	private WorkPlace theWorkPlace;
	/** Администратор */
	private Boolean theIsAdministrator;
	/** Хирургическая специальность */
	private Boolean theIsSurgical;
	/** Код специалиста */
	private String theCode;
	
	/** Интервал разрешенной регистрации */
	@Comment("Интервал разрешенной регистрации")
	public Integer getRegistrationInterval() {return theRegistrationInterval;}
	public void setRegistrationInterval(Integer aRegistrationInterval) {theRegistrationInterval = aRegistrationInterval;}

	/** Интервал разрешенной регистрации */
	private Integer theRegistrationInterval;
	/** Операционная сестра */
	@Comment("Операционная сестра")
	public Boolean getIsInstrumentNurse() {
		return theIsInstrumentNurse;
	}

	public void setIsInstrumentNurse(Boolean aOperationSister) {
		theIsInstrumentNurse = aOperationSister;
	}

	/** Операционная сестра */
	private Boolean theIsInstrumentNurse;
	
	/** Комментарий */
	@Comment("Комментарий")
	public String getComment() {
		return theComment;
	}

	public void setComment(String aComment) {
		theComment = aComment;
	}

	/** Комментарий */
	private String theComment;
	/** Не показывать удаленным пользователям */
	@Comment("Не показывать удаленным пользователям")
	public Boolean getIsNoViewRemoteUser() {return theIsNoViewRemoteUser;}
	public void setIsNoViewRemoteUser(Boolean aNoViewRemoteUser) {theIsNoViewRemoteUser = aNoViewRemoteUser;}

	/** Не показывать удаленным пользователям */
	private Boolean theIsNoViewRemoteUser;
	
	/** Принтер по умолчанию */
	@Comment("Принтер по умолчанию")
	@OneToOne
	public CopyingEquipment getCopyingEquipmentDefault() {return theCopyingEquipmentDefault;}
	public void setCopyingEquipmentDefault(CopyingEquipment aCopyingEquipmentDefault) {theCopyingEquipmentDefault = aCopyingEquipmentDefault;}

	/** Принтер по умолчанию */
	private CopyingEquipment theCopyingEquipmentDefault;

	/** Дата создания */
	@Comment("Дата создания")
	public Date getCreateDate() {return theCreateDate;}
	public void setCreateDate(Date aCreateDate) {theCreateDate = aCreateDate;}
	
	/** Дата редактирования */
	@Comment("Дата редактирования")
	public Date getEditDate() {return theEditDate;}
	public void setEditDate(Date aEditDate) {theEditDate = aEditDate;}
	
	/** Время создания */
	@Comment("Время создания")
	public Time getCreateTime() {return theCreateTime;}
	public void setCreateTime(Time aCreateTime) {theCreateTime = aCreateTime;}
	/** Время редактрования */
	@Comment("Время редактрования")
	public Time getEditTime() {return theEditTime;}
	public void setEditTime(Time aEditTime) {theEditTime = aEditTime;}
	/** Пользователь, который создал запись */
	@Comment("Пользователь, который создал запись")
	public String getCreateUsername() {return theCreateUsername;}
	public void setCreateUsername(String aCreateUsername) {theCreateUsername = aCreateUsername;}
	/** Пользователь, который последний редактировал запись */
	@Comment("Пользователь, который последний редактировал запись")
	public String getEditUsername() {return theEditUsername;}
	public void setEditUsername(String aEditUsername) {theEditUsername = aEditUsername;}

	/** Пользователь, который последний редактировал запись */
	private String theEditUsername;
	/** Пользователь, который создал запись */
	private String theCreateUsername;
	/** Время редактрования */
	private Time theEditTime;
	/** Время создания */
	private Time theCreateTime;
	/** Дата редактирования */
	private Date theEditDate;
	/** Дата создания */
	private Date theCreateDate;
	
	/** Экстренность */
	@Comment("Экстренность")
	public Boolean getEmergency() {return theEmergency;}
	public void setEmergency(Boolean aEmergency) {theEmergency = aEmergency;}

	/** Экстренность */
	private Boolean theEmergency;
	/** Категория специалиста */
	@Comment("Категория специалиста")
	@OneToOne
	public VocAcademicDegree getDegrees() {return theDegrees;}
	public void setDegrees(VocAcademicDegree aCategory) {theDegrees = aCategory;}

	/** Категория специалиста */
	private VocAcademicDegree theDegrees;

	/** Категория специалиста */
	@Comment("Категория специалиста")
	@OneToOne
	public VocCategory getCategory() {return theCategory;}
	public void setCategory(VocCategory aCategory) {theCategory = aCategory;}

	/** Категория специалиста */
	private VocCategory theCategory;
	
	/** Импорт */
	@Comment("Импорт")
	public Boolean getIsImport() {return theIsImport;}
	public void setIsImport(Boolean aIsImport) {theIsImport = aIsImport;}

	/** Импорт */
	private Boolean theIsImport;
	
	/** Запрет на направление к себе */
	@Comment("Запрет на направление к себе")
	public Boolean getIsNoDirectSelf() { return theIsNoDirectSelf; }
	public void setIsNoDirectSelf(Boolean aIsNoDirectSelf) { theIsNoDirectSelf = aIsNoDirectSelf; }

	/** Запрет на направление к себе */
	private Boolean theIsNoDirectSelf;
	
	/** Ротация */
	@Comment("Ротация")
	public Boolean getIsRotation() {return theIsRotation;}
	public void setIsRotation(Boolean aIsRotation) {theIsRotation = aIsRotation;}

	/** Ротация */
	private Boolean theIsRotation;
	
	/** Не синхронизировать с ПАРУСом */
	@Comment("Не синхронизировать с ПАРУСом")
	public Boolean getIsNoP7Sync() {return theIsNoP7Sync;}
	public void setIsNoP7Sync(Boolean aIsNoP7Sync) {theIsNoP7Sync = aIsNoP7Sync;}
	/** Не синхронизировать с ПАРУСом */
	private Boolean theIsNoP7Sync;
	
	/** Доверенность */
	@Comment("Доверенность")
	@OneToOne
	public Attorney getAttorney() {return theAttorney;}
	public void setAttorney(Attorney aAttorney) {theAttorney = aAttorney;}
	/** Доверенность */
	private Attorney theAttorney;

	@Comment("Код рабочего места в промеде")
	@Column(name="promedCode_workstaff")
	public String getPromedCodeWorkstaff() {
		return thePromedCodeWorkstaff;
	}
	public void setPromedCodeWorkstaff(String aPromedCodeWorkstaff) {
		thePromedCodeWorkstaff = aPromedCodeWorkstaff;
	}
	private String thePromedCodeWorkstaff;


	@Comment("Код отделения в промеде")
	@Column(name="promedCode_lpusection")
	public String getPromedCodeLpuSection() {
		return thePromedCodeLpuSection;
	}
	public void setPromedCodeLpuSection(String aPromedCodeLpuSection) {thePromedCodeLpuSection = aPromedCodeLpuSection;}
	private String thePromedCodeLpuSection;

	/** Разрешено записывать на дату без указания времени */
	@Comment("Разрешено записывать на дату без указания времени")
	public Boolean getIsDirectionNoTime() {return theIsDirectionNoTime;}
	public void setIsDirectionNoTime(Boolean aIsDirectionNoTime) {theIsDirectionNoTime = aIsDirectionNoTime;}
	/** Разрешено записывать на дату без указания времени */
	private Boolean theIsDirectionNoTime=false ;

	/** Очередь, которую обслуживает раб. функция */
	@Comment("Очередь, которую обслуживает раб. функция")
	@OneToOne
	public VocQueue getQueue() {return theQueue;}
	public void setQueue(VocQueue aQueue) {theQueue = aQueue;}
	/** Очередь, которую обслуживает раб. функция */
	private VocQueue theQueue ;

	/** Номер окна в электронной очереди */
	@Comment("Номер окна в электронной очереди")
	public String getWindowNumber() {return theWindowNumber;}
	public void setWindowNumber(String aWindowNumber) {theWindowNumber = aWindowNumber;}
	/** Номер окна в электронной очереди */
	private String theWindowNumber ;

	/** ККМ по умолчанию */
	@Comment("ККМ по умолчанию")
	@OneToOne
	public KkmEquipment getKkmEquipmentDefault() {return theKkmEquipmentDefault;}
	public void setKkmEquipmentDefault(KkmEquipment aKkmEquipmentDefault) {theKkmEquipmentDefault = aKkmEquipmentDefault;}

	/** ККМ по умолчанию */
	private KkmEquipment theKkmEquipmentDefault;
}
