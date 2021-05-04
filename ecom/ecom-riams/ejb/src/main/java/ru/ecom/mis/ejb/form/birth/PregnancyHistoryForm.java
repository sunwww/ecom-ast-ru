package ru.ecom.mis.ejb.form.birth;

import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.birth.PregnancyHistory;
import ru.ecom.mis.ejb.form.medcase.MedCaseForm;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;

/**
 * История родов
 * @author stkacheva
 */
@EntityForm
@EntityFormPersistance(clazz= PregnancyHistory.class)
@Comment("История родов")
@WebTrail(comment = "История родов", nameProperties= "id", view="entityParentView-preg_pregHistory.do")
@Parent(property="medCase", parentForm= MedCaseForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Pregnancy/History")
@Setter
public class PregnancyHistoryForm extends IdEntityForm {
	/** Беременность */
	@Comment("Беременность")
	@Persist 
	public Long getPregnancy() {return pregnancy;}

	/** СМО */
	@Comment("СМО")
	@Persist @Required
	public Long getMedCase() {return medCase;}

	
	/** СМО */
	private Long medCase;
	/** Беременность */
	private Long pregnancy;
}
