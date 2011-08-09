package ru.ecom.mis.ejb.form.prescription.template;

import ru.ecom.diary.ejb.domain.category.TemplateCategory;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.annotation.PersistManyToManyOneProperty;
import ru.ecom.mis.ejb.domain.prescription.PrescriptListTemplate;
import ru.ecom.mis.ejb.form.prescription.AbstractPrescriptionListForm;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;

/**
 * Шаблон листа назначения
 * @author STkacheva
 */
@EntityForm
@EntityFormPersistance(clazz=PrescriptListTemplate.class)
@Comment("Шаблон листа назначения")
@WebTrail(comment = "Шаблон листа назначения", nameProperties = { "name" }, view = "entityView-pres_template.do")
@EntityFormSecurityPrefix("/Policy/Mis/Prescription/Template")
public class PrescriptListForm extends AbstractPrescriptionListForm {
	/** Категории классификатора */
	@Comment("Категории классификатора")
	@PersistManyToManyOneProperty(collectionGenericType=TemplateCategory.class)
	@Persist
	public String getCategories() {return theCategories;}
	public void setCategories(String aCategories) {theCategories = aCategories;	}
	
	/** Название шаблона */
	@Comment("Название шаблона")
	@Persist @Required
	public String getName() {return theName;}
	public void setName(String aName) {theName = aName;}
	
	private String theName ;
	/** Категории классификатора */
	private String theCategories;

}
