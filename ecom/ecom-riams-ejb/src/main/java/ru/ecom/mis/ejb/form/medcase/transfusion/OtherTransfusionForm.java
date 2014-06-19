package ru.ecom.mis.ejb.form.medcase.transfusion;

import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.medcase.OtherTransfusion;
import ru.ecom.mis.ejb.form.medcase.MedCaseForm;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@EntityFormPersistance(clazz= OtherTransfusion.class)
@Comment("Переливание кровезамещающих растворов")
@WebTrail(comment = "Переливание кровезамещающих растворов", nameProperties= "id", view="entityParentView-trans_other.do",list = "entityParentList-trans_transfusion.do")
@Parent(property="medCase", parentForm= MedCaseForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/MedCase/Transfusion/Other")
public class OtherTransfusionForm extends TransfusionForm {
	/** Другая трансфузионная жидкость */
	@Comment("Другая трансфузионная жидкость")
	@Persist @Required
	public Long getOtherPreparation() {return theOtherPreparation;}
	public void setOtherPreparation(Long aOtherPreparation) {theOtherPreparation = aOtherPreparation;}

	/** Другая трансфузионная жидкость */
	private Long theOtherPreparation;
}
