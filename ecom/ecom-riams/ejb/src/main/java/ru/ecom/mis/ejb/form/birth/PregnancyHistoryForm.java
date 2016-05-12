package ru.ecom.mis.ejb.form.birth;

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
public class PregnancyHistoryForm extends IdEntityForm {
	/** Беременность */
	@Comment("Беременность")
	@Persist 
	public Long getPregnancy() {return thePregnancy;}
	public void setPregnancy(Long aPregnancy) {thePregnancy = aPregnancy;}

	/** СМО */
	@Comment("СМО")
	@Persist @Required
	public Long getMedCase() {return theMedCase;}
	public void setMedCase(Long aMedCase) {theMedCase = aMedCase;}

	
	/** СМО */
	private Long theMedCase;
	/** Беременность */
	private Long thePregnancy;
}
