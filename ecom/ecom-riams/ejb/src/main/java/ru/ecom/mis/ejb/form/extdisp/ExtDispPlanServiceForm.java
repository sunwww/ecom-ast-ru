package ru.ecom.mis.ejb.form.extdisp;

import lombok.Setter;
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
@Setter
public class ExtDispPlanServiceForm extends IdEntityForm{
	/** План */
	@Comment("План")
	@Persist
	public Long getPlan() {return plan;}
	/** План */
	private Long plan;
	
	/** Пол */
	@Comment("Пол")
	@Persist
	public Long getSex() {return sex;}
	/** Пол */
	private Long sex;
	
	/** Тип услуги */
	@Comment("Тип услуги")
	@Persist
	public Long getServiceType() {return serviceType;}
	/** Тип услуги */
	private Long serviceType;
	
	/** Возрастная группа */
	@Comment("Возрастная группа")
	@Persist
	public Long getAgeGroup() {return ageGroup;}
	/** Возрастная группа */
	private Long ageGroup;
	
	/** Визит */
	@Comment("Визит")
	@Persist
	public Boolean getIsVisit() {return isVisit;}
	/** Визит */
	private Boolean isVisit;
}
