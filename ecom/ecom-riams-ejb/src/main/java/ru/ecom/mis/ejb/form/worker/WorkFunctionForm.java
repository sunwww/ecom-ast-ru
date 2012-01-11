package ru.ecom.mis.ejb.form.worker;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.Subclasses;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.jaas.ejb.form.SecUserForm;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.ecom.mis.ejb.form.lpu.MisLpuForm;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;

/**
 * Рабочая функция
 */
@EntityForm
@EntityFormPersistance(clazz= WorkFunction.class)
@Comment("Рабочая функция")
@WebTrail(comment = "Рабочая функция", nameProperties= "info", view="entitySubclassView-work_workFunction.do")
@Parent(property="lpuRegister", parentForm= MisLpuForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Worker/WorkFunction")
@Subclasses({PersonalWorkFunctionForm.class, GroupWorkFunctionForm.class})
public class WorkFunctionForm extends IdEntityForm{
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
}
