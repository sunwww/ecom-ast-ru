package ru.ecom.mis.ejb.form.worker;

import lombok.Setter;
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
@Setter
public class WorkFunctionServiceForm extends IdEntityForm {
		
	/** Специалист (группа) */
	@Comment("Специалист (группа)")
	@Persist
	public Long getWorkFunction() {return workFunction;}
	/** Специалист (группа) */
	private Long workFunction;

	/** Рабочая функция */
	@Comment("Рабочая функция")
	@Persist
	public Long getVocWorkFunction() {return vocWorkFunction;}

	/** Рабочая функция */
	private Long vocWorkFunction;
	
	/** Мед. услуга */
	@Comment("Мед. услуга")
	@Persist @Required
	public Long getMedService() {return medService;}

	/** Мед. услуга */
	private Long medService;
	
	/** ЛПУ */
	@Comment("ЛПУ")
	@Persist
	public Long getLpu() {return lpu;}

	/** ЛПУ */
	private Long lpu;
	
	/** Профиль коек */
	@Comment("Профиль коек")
	@Persist
	public Long getBedType() {return bedType;}

	/** Профиль коек */
	private Long bedType;
	
	/** Тип коек */
	@Comment("Тип коек")
	@Persist
	public Long getBedSubType() {return bedSubType;}

	/** Тип коек */
	private Long bedSubType;
	
	/** Уровень палат */
	@Comment("Уровень палат")
	@Persist
	public Long getRoomType() {return roomType;}

	/** Уровень палат */
	private Long roomType;

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
	/** Неактивно для назначений */
	@Comment("Неактивно для назначений")
	@Persist
	public Boolean getNoActiveByPrescript() {return noActiveByPrescript;}

	/** Неактивно для назначений */
	private Boolean noActiveByPrescript;

	/** Список диагнозов */
	@Comment("Список диагнозов")
	@Persist 
	public String getListIdc10() {return listIdc10;}

	/** Список диагнозов */
	private String listIdc10;
	
	
	/** Запрещенный тип назначений */
	@Comment("Запрещенный тип назначений")
	@Persist
	public Long getPrescriptType() {return prescriptType;}

	/** Запрещенный тип назначений */
	private Long prescriptType;

}
