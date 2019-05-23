package ru.ecom.mis.ejb.form.worker;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.Subclasses;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.ecom.mis.ejb.form.lpu.MisLpuForm;
import ru.ecom.mis.ejb.form.lpu.OperatingRoomForm;
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
 * Рабочая функция
 */
@EntityForm
@EntityFormPersistance(clazz= WorkFunction.class)
@Comment("Рабочая функция")
@WebTrail(comment = "Рабочая функция", nameProperties= "info", view="entitySubclassView-work_workFunction.do")
@Parent(property="lpuRegister", parentForm= MisLpuForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Worker/WorkFunction")
@Subclasses({PersonalWorkFunctionForm.class, GroupWorkFunctionForm.class, OperatingRoomForm.class})
public class WorkFunctionForm extends IdEntityForm{
	
	/** Дата начала работы */
	@Comment("Дата начала работы")
	@Persist
	@DateString @DoDateString
	public String getStartDate() {return theStartDate;}
	public void setStartDate(String aStartDate) {theStartDate = aStartDate;}
	/** Дата начала работы */
	private String theStartDate;
	
	/** Дата окончания работы */
	@Comment("Дата окончания работы")
	@Persist
	@DateString @DoDateString
	public String getFinishDate() {return theFinishDate;}
	public void setFinishDate(String aFinishDate) {theFinishDate = aFinishDate;}
	/** Дата окончания работы */
	private String theFinishDate;
	
	/** Наименование */
	@Comment("Наименование")
	@Persist
	public String getName() {return theName;}
	public void setName(String aName) {theName = aName;}

	
   /** Информация о рабочей фукции */
   @Comment("Информация о рабочей фукции")
   @Persist
	public String getWorkFunctionInfo() {return theWorkFunctionInfo;}
	public void setWorkFunctionInfo(String aWorkFunctionInfo) {theWorkFunctionInfo = aWorkFunctionInfo;}
	
	/** Функция */
	@Comment("Функция")
		@Persist @Required
	public Long getWorkFunction() {return theWorkFunction;}
	public void setWorkFunction(Long aWorkFunction) {theWorkFunction = aWorkFunction;}

	
	/** ЛПУ */
	@Comment("ЛПУ")
		@Persist
	public Long getLpuRegister() {return theLpuRegister;}
	public void setLpuRegister(Long aLpu) {theLpuRegister = aLpu;}

	/** Информация */
	@Comment("Информация")
	@Persist
	public String getInfo() {return theInfo;}
	public void setInfo(String aInfo) {theInfo = aInfo;}

	/** Поместить в архив? */
	@Comment("Поместить в архив?")
	@Persist
	public Boolean getArchival() {return theArchival;}
	public void setArchival(Boolean aArchival) {theArchival = aArchival;}
	

	/** Код специалиста */
	@Comment("Код специалиста")
	@Persist
	public String getCode() {return theCode;}
	public void setCode(String aCode) {theCode = aCode;}

	/** Хирургическая специальность */
	@Comment("Хирургическая специальность")
	@Persist
	public Boolean getIsSurgical() {return theIsSurgical;}
	public void setIsSurgical(Boolean aIsSurgical) {theIsSurgical = aIsSurgical;}

	/** Администратор */
	@Comment("Администратор")
	@Persist
	public Boolean getIsAdministrator() {return theIsAdministrator;}
	public void setIsAdministrator(Boolean aAdministrator) {theIsAdministrator = aAdministrator;}

	/** Интервал разрешенной регистрации */
	@Comment("Интервал разрешенной регистрации")
	@Persist
	public Integer getRegistrationInterval() {return theRegistrationInterval;}
	public void setRegistrationInterval(Integer aRegistrationInterval) {theRegistrationInterval = aRegistrationInterval;}

	/** Интервал разрешенной регистрации */
	private Integer theRegistrationInterval;
	/** Администратор */
	private Boolean theIsAdministrator;
	/** Хирургическая специальность */
	private Boolean theIsSurgical;
	/** Код специалиста */
	private String theCode;
	/** Поместить в архив? */
	private Boolean theArchival;
	/** Информация */
	private String theInfo;
	/** Наименование */
	private String theName;
	/** Информация о рабочей фукции */
	private String theWorkFunctionInfo;
	/** Функция */
	private Long theWorkFunction;    
	/** ЛПУ */
	private Long theLpuRegister;
	/** Операционная сестра */
	@Comment("Операционная сестра")
	@Persist
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
	@Persist
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
	@Persist
	public Boolean getIsNoViewRemoteUser() {return theIsNoViewRemoteUser;}
	public void setIsNoViewRemoteUser(Boolean aNoViewRemoteUser) {theIsNoViewRemoteUser = aNoViewRemoteUser;}

	/** Принтер по умолчанию */
	@Comment("Принтер по умолчанию")
	@Persist
	public Long getCopyingEquipmentDefault() {return theCopyingEquipmentDefault;}
	public void setCopyingEquipmentDefault(Long aCopyingEquipmentDefault) {theCopyingEquipmentDefault = aCopyingEquipmentDefault;}

	/** Принтер по умолчанию */
	private Long theCopyingEquipmentDefault;
	/** Не показывать удаленным пользователям */
	private Boolean theIsNoViewRemoteUser;

	/** Дата создания */
	@Comment("Дата создания")
	@DateString @DoDateString @Persist
	public String getCreateDate() {return theCreateDate;}
	public void setCreateDate(String aCreateDate) {theCreateDate = aCreateDate;}
	
	/** Дата редактирования */
	@Comment("Дата редактирования")
	@DateString @DoDateString @Persist
	public String getEditDate() {return theEditDate;}
	public void setEditDate(String aEditDate) {theEditDate = aEditDate;}
	
	/** Время создания */
	@Comment("Время создания")
	@TimeString @DoTimeString @Persist
	public String getCreateTime() {return theCreateTime;}
	public void setCreateTime(String aCreateTime) {theCreateTime = aCreateTime;}
	/** Время редактрования */
	@Comment("Время редактрования")
	@TimeString @DoTimeString @Persist
	public String getEditTime() {return theEditTime;}
	public void setEditTime(String aEditTime) {theEditTime = aEditTime;}
	/** Пользователь, который создал запись */
	@Comment("Пользователь, который создал запись")
	@Persist
	public String getCreateUsername() {return theCreateUsername;}
	public void setCreateUsername(String aCreateUsername) {theCreateUsername = aCreateUsername;}
	/** Пользователь, который последний редактировал запись */
	@Comment("Пользователь, который последний редактировал запись")
	@Persist
	public String getEditUsername() {return theEditUsername;}
	public void setEditUsername(String aEditUsername) {theEditUsername = aEditUsername;}

	/** Пользователь, который последний редактировал запись */
	private String theEditUsername;
	/** Пользователь, который создал запись */
	private String theCreateUsername;
	/** Время редактрования */
	private String theEditTime;
	/** Время создания */
	private String theCreateTime;
	/** Дата редактирования */
	private String theEditDate;
	/** Дата создания */
	private String theCreateDate;
	/** Экстренность */
	@Comment("Экстренность")
	@Persist
	public Boolean getEmergency() {return theEmergency;}
	public void setEmergency(Boolean aEmergency) {theEmergency = aEmergency;}

	/** Экстренность */
	private Boolean theEmergency;
	
	/** Категория специалиста */
	@Comment("Категория специалиста")
	@Persist
	public Long getCategory() {return theCategory;}
	public void setCategory(Long aCategory) {theCategory = aCategory;}

	/** Категория специалиста */
	private Long theCategory;
	/** Импорт */
	@Comment("Импорт")
	@Persist
	public Boolean getIsImport() {return theIsImport;}
	public void setIsImport(Boolean aIsImport) {theIsImport = aIsImport;}

	/** Импорт */
	private Boolean theIsImport;
	/** Запрет на направление к себе */
	@Comment("Запрет на направление к себе")
	@Persist
	public Boolean getIsNoDirectSelf() { return theIsNoDirectSelf; }
	public void setIsNoDirectSelf(Boolean aIsNoDirectSelf) { theIsNoDirectSelf = aIsNoDirectSelf; }

	/** Запрет на направление к себе */
	private Boolean theIsNoDirectSelf;

	/** Ратация */
	@Comment("Ратация")
	public Boolean getIsRotation() {return theIsRotation;}
	public void setIsRotation(Boolean aIsRotation) {theIsRotation = aIsRotation;}

	/** Ратация */
	private Boolean theIsRotation;
	
	/** Не синхронизировать с ПАРУСом */
	@Comment("Не синхронизировать с ПАРУСом")
	@Persist
	public Boolean getIsNoP7Sync() {return theIsNoP7Sync;}
	public void setIsNoP7Sync(Boolean aIsNoP7Sync) {theIsNoP7Sync = aIsNoP7Sync;}
	/** Не синхронизировать с ПАРУСом */
	private Boolean theIsNoP7Sync;
	
	/** Доверенность */
	@Comment("Доверенность")
	@Persist
	public Long getAttorney() {return theAttorney;}
	public void setAttorney(Long aAttorney) {theAttorney = aAttorney;}
	/** Доверенность */
	private Long theAttorney;

	/** Разрешено записывать на дату без указания времени */
	@Comment("Разрешено записывать на дату без указания времени")
	@Persist
	public Boolean getIsDirectionNoTime() {return theIsDirectionNoTime;}
	public void setIsDirectionNoTime(Boolean aIsDirectionNoTime) {theIsDirectionNoTime = aIsDirectionNoTime;}
	/** Разрешено записывать на дату без указания времени */
	private Boolean theIsDirectionNoTime ;

	/** ККМ по умолчанию */
	@Comment("ККМ по умолчанию")
	@Persist
	public Long getKkmEquipmentDefault() {return theKkmEquipmentDefault;}
	public void setKkmEquipmentDefault(Long aKkmEquipmentDefault) {theKkmEquipmentDefault = aKkmEquipmentDefault;}

	/** ККМ по умолчанию */
	private Long theKkmEquipmentDefault;

	/** Кабинет */
	@Comment("Кабинет")
	public String getCabinet() {return theCabinet;}
	public void setCabinet(String aCabinet) {theCabinet = aCabinet;}

	/** Кабинет */
	private String theCabinet;
	
}

