package ru.ecom.mis.ejb.form.claim;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.claim.WorkFunctionClaimType;
import ru.ecom.mis.ejb.form.worker.GroupWorkFunctionForm;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;


@EntityForm
@EntityFormPersistance(clazz= WorkFunctionClaimType.class)
@Comment("Привязка рабочей функции к типу заявки")
@WebTrail(comment = "Привязка рабочей функции к типу заявки", nameProperties= "id", view="entityParentView-mis_workFunctionClaimType.do" ,list = "entityParentList-mis_workFunctionClaimType.do")
@EntityFormSecurityPrefix("/Policy/Mis/Claim")
@Parent(parentForm=GroupWorkFunctionForm.class, property="workfunction")
public class WorkFunctionClaimTypeForm extends IdEntityForm{
	
	/** Рабочая функция */
	@Comment("Рабочая функция")
	@Persist
	public Long getWorkfunction() {return theWorkfunction;}
	public void setWorkfunction(Long aWorkfunction) {theWorkfunction = aWorkfunction;}
	/** Рабочая функция */
	private Long theWorkfunction;
	
	/** Тип заявки */
	@Comment("Тип заявки")
	@Persist
	public Long getClaimType() {return theClaimType;}
	public void setClaimType(Long aClaimType) {theClaimType = aClaimType;}
	/** Тип заявки */
	private Long theClaimType;

}
