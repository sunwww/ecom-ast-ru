package ru.ecom.mis.ejb.form.birth;

import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.birth.HereditaryScreening;
import ru.ecom.mis.ejb.form.medcase.MedCaseForm;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;

/**
 * Скрининг на наследственные заболевания 
 * @author oegorova
 *
 */

@EntityForm
@EntityFormPersistance(clazz= HereditaryScreening .class)
@Comment("Скрининг на наследственные заболевания ")
@WebTrail(comment = "Скрининг на наследственные заболевания ", nameProperties= "id", view="entityParentView-preg_hereditaryScreening.do" ,list = "entityParentList-preg_inspection.do")
@Parent(property="medCase", parentForm= MedCaseForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Inspection/HereditaryScreening")
public class HereditaryScreeningForm extends InspectionForm{
	
	/** Описание */
	@Comment("Описание")
	@Persist @Required
	public String getNotes() {return theNotes;}
	public void setNotes(String aNotes) {theNotes = aNotes;}
	/** Описание */
	private String theNotes;
}
