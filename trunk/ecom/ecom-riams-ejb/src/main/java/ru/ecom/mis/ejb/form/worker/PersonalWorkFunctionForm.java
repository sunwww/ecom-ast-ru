package ru.ecom.mis.ejb.form.worker;

import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.jaas.ejb.form.SecUserForm;
import ru.ecom.mis.ejb.domain.worker.PersonalWorkFunction;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@EntityFormPersistance(clazz= PersonalWorkFunction.class)
@Comment("Рабочая функция специалиста")
@WebTrail(comment = "Рабочая функция специалиста", nameProperties= "name", view="entityParentView-work_personalWorkFunction.do",list= "entityParentList-work_personalWorkFunction.do")
@Parent(property="worker", parentForm= WorkerForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Worker/WorkFunction")
public class PersonalWorkFunctionForm extends WorkFunctionForm {
	/** Сотрудник */
	@Comment("Сотрудник")
	@Persist @Required
	public Long getWorker() {return theWorker;}
	public void setWorker(Long aWorkers) {theWorker = aWorkers;}

	/** Пользователь системы */
	@Comment("Пользователь системы")
		@Persist
	public Long getSecUser() {return theSecUser;}
	public void setSecUser(Long aSecUser) {theSecUser = aSecUser;}

	/** Групповая рабочая функция */
	@Comment("Групповая рабочая функция")
	@Persist
	public Long getGroup() {return theGroup;}
	public void setGroup(Long aGroup) {theGroup = aGroup;}

	   /** Информация о работнике */
	@Comment("Информация о работнике")
	@Persist
	public String getWorkerInfo() {return theWorkerInfo;}
	public void setWorkerInfo(String aWorkerInfo) {theWorkerInfo = aWorkerInfo;}
	
	/** Информация о рабочей группе */
	@Comment("Информация о рабочей группе")
	@Persist
	public String getGroupInfo() {return theGroupInfo;}
	public void setGroupInfo(String aGroupInfo) {theGroupInfo = aGroupInfo;}

	/** Пользователь */
	@Comment("Пользователь")
	public SecUserForm getUserForm() {return theUserForm;}
	public void setSecUserForm(SecUserForm aUserForm) {theUserForm = aUserForm;}

	/** Создавать календарь */
	@Comment("Создавать календарь")
	public Boolean getIsCalendarCreate() {return theIsCalendarCreate;}
	public void setIsCalendarCreate(Boolean aIsCalendarCreate) {theIsCalendarCreate = aIsCalendarCreate;}


	/** Создавать календарь */
	private Boolean theIsCalendarCreate;
	/** Пользователь */
	private SecUserForm theUserForm;
	/** Информация о рабочей группе */
	private String theGroupInfo;

	/** Информация о работнике */
	private String theWorkerInfo;
	/** Групповая рабочая функция */
	private Long theGroup;
	/** Пользователь системы */
	private Long theSecUser;
	/** Сотрудник */
	private Long theWorker;
}
