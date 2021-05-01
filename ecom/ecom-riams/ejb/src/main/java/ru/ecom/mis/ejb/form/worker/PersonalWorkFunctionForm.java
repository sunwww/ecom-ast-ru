package ru.ecom.mis.ejb.form.worker;

import lombok.Setter;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.jaas.ejb.form.SecUserForm;
import ru.ecom.mis.ejb.domain.worker.PersonalWorkFunction;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@EntityFormPersistance(clazz= PersonalWorkFunction.class)
@Comment("Рабочая функция специалиста")
@WebTrail(comment = "Рабочая функция специалиста", nameProperties= "name", view="entityParentView-work_personalWorkFunction.do",list= "entityParentList-work_personalWorkFunction.do")
@Parent(property="worker", parentForm= WorkerForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Worker/WorkFunction")
@Setter
public class PersonalWorkFunctionForm extends WorkFunctionForm {
	/** Сотрудник */
	@Comment("Сотрудник")
	@Persist @Required
	public Long getWorker() {return worker;}

	/** Пользователь системы */
	@Comment("Пользователь системы")
	@Persist
	public Long getSecUser() {return secUser;}

	/** Групповая рабочая функция */
	@Comment("Групповая рабочая функция")
	@Persist
	public Long getGroup() {return group;}

	   /** Информация о работнике */
	@Comment("Информация о работнике")
	@Persist
	public String getWorkerInfo() {return workerInfo;}

	/** Информация о рабочей группе */
	@Comment("Информация о рабочей группе")
	@Persist
	public String getGroupInfo() {return groupInfo;}

	/** Пользователь */
	@Comment("Пользователь")
	public SecUserForm getUserForm() {return userForm;}

	/** Создавать календарь */
	@Comment("Создавать календарь")
	public Boolean getIsCalendarCreate() {return isCalendarCreate;}

	/** Категория специалиста */
	@Comment("Категория специалиста")
	@Persist
	public Long getDegrees() {return degrees;}

	/** Очередь, которую обслуживает раб. функция */
	@Comment("Очередь, которую обслуживает раб. функция")
	@Persist
	public Long getQueue() {return queue;}
	/** Очередь, которую обслуживает раб. функция */
	private Long queue ;

	/** Номер окна в электронной очереди */
	@Comment("Номер окна в электронной очереди")
	@Persist
	public String getWindowNumber() {return windowNumber;}
	private String windowNumber ;

	public String getAllGroups() {
		return allGroups;
	}

	/** Категория специалиста */
	private Long degrees;
	/** Создавать календарь */
	private Boolean isCalendarCreate;
	/** Пользователь */
	private SecUserForm userForm;
	/** Информация о рабочей группе */
	private String groupInfo;

	/** Информация о работнике */
	private String workerInfo;
	/** Групповая рабочая функция */
	private Long group;
	/** Пользователь системы */
	private Long secUser;
	/** Сотрудник */
	private Long worker;
	/** Дополнительные групповые рабочие функции */
	private String allGroups;
}
