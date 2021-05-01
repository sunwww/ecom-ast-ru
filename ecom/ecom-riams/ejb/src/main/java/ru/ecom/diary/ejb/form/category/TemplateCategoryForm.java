package ru.ecom.diary.ejb.form.category;

import lombok.Getter;
import lombok.Setter;
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
@Setter
public class TemplateCategoryForm extends IdEntityForm {
	/** Родитель */
	@Comment("Родитель")
	@Persist
	public Long getParent() {return parent;	}

	/** Название категории */
	@Comment("Название категории")
	@Persist @Required @DoUpperCase 
	public String getName() {return name;}

	/** Комментарии */
	@Comment("Комментарии")
	@Persist
	public String getComments() {return comments;}

	/** Пользователь */
	@Comment("Пользователь")
	@Persist
	public String getUsername() {return username;}

	/** Дата создания */
	@Comment("Дата создания")
	@Persist @DateString @DoDateString
	public String getDateCreate() {return dateCreate;}

	/** Дата создания */
	private String dateCreate;
	/** Пользователь */
	private String username;
	/** Комментарии */
	private String comments;
	/** Название категории */
	private String name;
	/** Родитель */
	private Long parent;
}
