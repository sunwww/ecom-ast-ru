package ru.ecom.mis.ejb.form.worker;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.worker.WorkFunctionService;
import ru.ecom.mis.ejb.form.medcase.MedServiceForm;
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

@EntityForm
@EntityFormPersistance(clazz=WorkFunctionService.class)
@Comment("Прикрепление раб. функции к услуге")
@WebTrail(comment = "Прикрепление раб. функции к услуге"
	, nameProperties= "id"
		, view="entityParentView-mis_medService_workFunction.do"
//			,list = "entityParentList-mis_medService.do"
				)
@Parent(property="medService", parentForm= MedServiceForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/MedService/VocWorkFunction")
public class WorkFunctionServiceGroupForm extends IdEntityForm {
	
	/** Специалист (группа) */
	@Comment("Специалист (группа)")
	@Persist
	public Long getWorkFunction() {return theWorkFunction;}
	public void setWorkFunction(Long aWorkFunction) {theWorkFunction = aWorkFunction;}
	/** Специалист (группа) */
	private Long theWorkFunction;

	/** Рабочая функция */
	@Comment("Рабочая функция")
	@Persist
	public Long getVocWorkFunction() {return theVocWorkFunction;}
	public void setVocWorkFunction(Long aVocWorkFunction) {theVocWorkFunction = aVocWorkFunction;}

	/** Рабочая функция */
	private Long theVocWorkFunction;
	
	/** Мед. услуга */
	@Comment("Мед. услуга")
	@Persist @Required
	public Long getMedService() {return theMedService;}
	public void setMedService(Long aMedService) {theMedService = aMedService;}

	/** Мед. услуга */
	private Long theMedService;
	
	/** ЛПУ */
	@Comment("ЛПУ")
	@Persist
	public Long getLpu() {return theLpu;}
	public void setLpu(Long aLpu) {theLpu = aLpu;}

	/** ЛПУ */
	private Long theLpu;
	
	/** Профиль коек */
	@Comment("Профиль коек")
	@Persist
	public Long getBedType() {return theBedType;}
	public void setBedType(Long aBedType) {theBedType = aBedType;}

	/** Профиль коек */
	private Long theBedType;
	
	/** Тип коек */
	@Comment("Тип коек")
	@Persist
	public Long getBedSubType() {return theBedSubType;}
	public void setBedSubType(Long aBedSubType) {theBedSubType = aBedSubType;}

	/** Тип коек */
	private Long theBedSubType;
	
	/** Уровень палат */
	@Comment("Уровень палат")
	@Persist
	public Long getRoomType() {return theRoomType;}
	public void setRoomType(Long aRoomType) {theRoomType = aRoomType;}

	/** Уровень палат */
	private Long theRoomType;

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
	/** Неактивно для назначений */
	@Comment("Неактивно для назначений")
	@Persist
	public Boolean getNoActiveByPrescript() {return theNoActiveByPrescript;}
	public void setNoActiveByPrescript(Boolean aNoActiveByPrescript) {theNoActiveByPrescript = aNoActiveByPrescript;}

	/** Неактивно для назначений */
	private Boolean theNoActiveByPrescript;

	/** Список диагнозов */
	@Comment("Список диагнозов")
	@Persist 
	public String getListIdc10() {return theListIdc10;}
	public void setListIdc10(String aListIdc10) {theListIdc10 = aListIdc10;}

	/** Список диагнозов */
	private String theListIdc10;
	
	
	/** Тип назначений */
	@Comment("Тип назначений")
	@Persist
	public Long getPrescriptType() {return thePrescriptType;}
	public void setPrescriptType(Long aPrescriptType) {thePrescriptType = aPrescriptType;}

	/** Тип назначений */
	private Long thePrescriptType;

}
