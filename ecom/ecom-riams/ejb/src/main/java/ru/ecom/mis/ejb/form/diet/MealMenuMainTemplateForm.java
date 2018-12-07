package ru.ecom.mis.ejb.form.diet;

import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.diet.MealMenuTemplate;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

/**
 * Шаблон меню приема пищи
 * @author oegorova
 *
 */

@Comment("Шаблон меню-раскладки")
@EntityForm
@EntityFormPersistance(clazz = MealMenuTemplate.class)
@WebTrail(comment = "Шаблон меню-раскладки ", nameProperties= "description", view="entityView-diet_mealMenuTemplate.do")
@Parent(property="parentMenu", parentForm=MealMenuTemplateForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/InvalidFood/MealMenuTemplate")

public class MealMenuMainTemplateForm extends MealMenuTemplateForm {
}
	
	
