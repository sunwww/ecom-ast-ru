package ru.ecom.mis.ejb.form.birth;

import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.birth.AudiologicScreening;
import ru.ecom.mis.ejb.form.medcase.MedCaseForm;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;

/**
 * Аудиологический скрининг
 * @author oegorova
 *
 */

@EntityForm
@EntityFormPersistance(clazz= AudiologicScreening.class)
@Comment("Аудиологический скрининг")
@WebTrail(comment = "Аудиологический скрининг", nameProperties= "id", view="entityParentView-preg_audiologicScreening.do", list = "entityParentList-preg_inspection.do")
@Parent(property="medCase", parentForm= MedCaseForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Inspection/AudiologicScreening")
public class AudiologicScreeningForm extends InspectionForm {
	
	/** Описание */
	@Comment("Описание")
	@Persist @Required
	public String getNotes() {return theNotes;}
	public void setNotes(String aNotes) {theNotes = aNotes;}
	/** Описание */
	private String theNotes;
}
