package ru.ecom.mis.ejb.form.extdisp;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.extdisp.ExtDispPlanService;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

@EntityForm
@EntityFormPersistance(clazz = ExtDispPlanService.class)
@Comment("Услуга плана доп. диспансеризации")
@WebTrail(comment = "Услуга плана доп. диспансеризации", nameProperties= "id"
, list="entityParentList-extDisp_vocPlanService.do", view="entityParentView-extDisp_vocPlanService.do")
@Parent(property = "plan",parentForm=ExtDispPlanForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/ExtDisp/Card/Voc/Plan/Service")
public class ExtDispPlanServiceForm extends IdEntityForm{
	/** План */
	@Comment("План")
	@Persist
	public Long getPlan() {return thePlan;}
	public void setPlan(Long aPlan) {thePlan = aPlan;}
	/** План */
	private Long thePlan;
	
	/** Пол */
	@Comment("Пол")
	@Persist
	public Long getSex() {return theSex;}
	public void setSex(Long aSex) {theSex = aSex;}
	/** Пол */
	private Long theSex;
	
	/** Тип услуги */
	@Comment("Тип услуги")
	@Persist
	public Long getServiceType() {return theServiceType;}
	public void setServiceType(Long aServiceType) {theServiceType = aServiceType;}
	/** Тип услуги */
	private Long theServiceType;
	
	/** Возрастная группа */
	@Comment("Возрастная группа")
	@Persist
	public Long getAgeGroup() {return theAgeGroup;}
	public void setAgeGroup(Long aAgeGroup) {theAgeGroup = aAgeGroup;}
	/** Возрастная группа */
	private Long theAgeGroup;
	
	/** Визит */
	@Comment("Визит")
	@Persist
	public Boolean getIsVisit() {return theIsVisit;}
	public void setIsVisit(Boolean aIsVisit) {theIsVisit = aIsVisit;}
	/** Визит */
	private Boolean theIsVisit;
}
