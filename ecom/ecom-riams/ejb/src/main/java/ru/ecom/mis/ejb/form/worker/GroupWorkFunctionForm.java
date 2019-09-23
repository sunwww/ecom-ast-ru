package ru.ecom.mis.ejb.form.worker;

import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.worker.GroupWorkFunction;
import ru.ecom.mis.ejb.form.lpu.MisLpuForm;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoUpperCase;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@EntityFormPersistance(clazz= GroupWorkFunction.class)
@Comment("Групповая рабочая функция")
@WebTrail(comment = "Групповая рабочая функция", nameProperties= "name", view="entityParentView-work_groupWorkFunction.do",list= "entityParentList-work_groupWorkFunction.do")
@Parent(property="lpu", parentForm= MisLpuForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Worker/WorkFunction")

public class GroupWorkFunctionForm extends WorkFunctionForm {
	
	/** Направлять анализы по умолчанию в этот кабинет */
	@Comment("Направлять анализы по умолчанию в этот кабинет")
	@Persist
	public Boolean getIsDefaultLabCabinet() {return theIsDefaultLabCabinet;}
	public void setIsDefaultLabCabinet(Boolean aIsDefaultLabCabinet) {theIsDefaultLabCabinet = aIsDefaultLabCabinet;}
	/** Направлять анализы по умолчанию в этот кабинет */
	private Boolean theIsDefaultLabCabinet;
	
	/** Есть обслуживающий персонал */
	@Comment("Есть обслуживающий персонал")
	@Persist
	public Boolean getHasServiceStuff() {return theHasServiceStuff;}
	public void setHasServiceStuff(Boolean aHasServiceStuff) {theHasServiceStuff = aHasServiceStuff;}
	/** Есть обслуживающий персонал */
	private Boolean theHasServiceStuff;
	
	/** Наименование */
	@Comment("Наименование")
	@Persist
	public String getName() {return theName;}
	public void setName(String aName) {theName = aName;}

	/** ЛПУ */
	@Comment("ЛПУ")
	@Persist @Required
	public Long getLpu() {return theLpu;}
	public void setLpu(Long aLpu) {theLpu = aLpu;}

	/** Название группы */
	@Comment("Название группы")
	@Persist  @Required @DoUpperCase
	public String getGroupName() {return theGroupName;}
	public void setGroupName(String aGroupName) {theGroupName = aGroupName;}

	/** Название группы */
	private String theGroupName;
	/** ЛПУ */
	private Long theLpu;
	/** Наименование */
	private String theName;
}
