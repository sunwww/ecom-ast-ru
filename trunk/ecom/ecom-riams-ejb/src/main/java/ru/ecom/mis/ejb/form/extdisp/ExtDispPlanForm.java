package ru.ecom.mis.ejb.form.extdisp;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.extdisp.ExtDispPlan;
import ru.ecom.mis.ejb.form.extdisp.voc.VocExtDispForm;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

@EntityForm
@EntityFormPersistance(clazz = ExtDispPlan.class)
@Comment("План дополнительной диспансеризации")
@WebTrail(comment = "План дополнительной диспансеризации", nameProperties= "id"
, list="entityParentList-extDisp_vocPlan.do", view="entityParentView-extDisp_vocPlan.do")
@Parent(property="dispType", parentForm=VocExtDispForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/ExtDisp/Card/Voc/Plan")
public class ExtDispPlanForm extends IdEntityForm{
	/** Тип дополнительной диспансеризации */
	@Comment("Тип дополнительной диспансеризации")
	@Persist
	public Long getDispType() {return theDispType;}
	public void setDispType(Long aDispType) {theDispType = aDispType;}
	/** Тип дополнительной диспансеризации */
	private Long theDispType;
}
