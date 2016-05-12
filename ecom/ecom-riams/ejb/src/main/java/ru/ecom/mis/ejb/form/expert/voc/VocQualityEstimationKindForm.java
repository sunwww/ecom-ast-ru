package ru.ecom.mis.ejb.form.expert.voc;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.expert.voc.VocQualityEstimationKind;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;
/**
 * Справочник видов оценок качества
 * @author stkacheva
 */
@EntityForm
@EntityFormPersistance(clazz = VocQualityEstimationKind.class)
@Comment("Справочник видов оценок качества")
@WebTrail(comment = "Вид оценок качества", nameProperties = "name", view = "entityView-exp_vocKind.do")
@EntityFormSecurityPrefix("/Policy/Voc/VocQualityEstimationKind")
public class VocQualityEstimationKindForm extends IdEntityForm {
	/** Наименование */
	@Comment("Наименование")
	@Persist @Required
	public String getName() {return theName;}
	public void setName(String aName) {theName = aName;}
	
	/** Код */
	@Comment("Код")
	@Persist @Required
	public String getCode() {return theCode;}
	public void setCode(String aCode) {theCode = aCode;}

	/** Код */
	private String theCode;
	/** Наименование */
	private String theName;
}
