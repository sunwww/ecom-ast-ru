package ru.ecom.diary.ejb.form.category;

import ru.ecom.diary.ejb.domain.category.TemplateCategory;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.transforms.DoUpperCase;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;

/**
 * Форма категории классификатора шаблонов
 * @author STkacheva
 */
@Comment("Классификатор шаблонов")
@EntityForm
@EntityFormPersistance(clazz= TemplateCategory.class)
@WebTrail(comment = "Классификатор шаблонов", nameProperties= "name", view="entityParentView-temp_category.do")
@Parent(property="parent", parentForm= TemplateCategoryForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Template/Category")
public class TemplateCategoryForm extends IdEntityForm {
	/** Родитель */
	@Comment("Родитель")
	@Persist
	public Long getParent() {return theParent;	}
	public void setParent(Long aParent) {theParent = aParent;}

	/** Название категории */
	@Comment("Название категории")
	@Persist @Required @DoUpperCase 
	public String getName() {return theName;}
	public void setName(String aName) {theName = aName;}

	/** Комментарии */
	@Comment("Комментарии")
	@Persist
	public String getComments() {return theComments;}
	public void setComments(String aComments) {theComments = aComments;}

	/** Пользователь */
	@Comment("Пользователь")
	@Persist
	public String getUsername() {return theUsername;}
	public void setUsername(String aUsername) {theUsername = aUsername;}

	/** Дата создания */
	@Comment("Дата создания")
	@Persist @DateString @DoDateString
	public String getDateCreate() {return theDateCreate;}
	public void setDateCreate(String aDateCreate) {theDateCreate = aDateCreate;}

	/** Дата создания */
	private String theDateCreate;
	/** Пользователь */
	private String theUsername;
	/** Комментарии */
	private String theComments;
	/** Название категории */
	private String theName;
	/** Родитель */
	private Long theParent;
}
