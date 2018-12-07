package ru.ecom.mis.ejb.form.diet;

import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.diet.MealMenuTemplate;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

//import ru.ecom.mis.ejb.domain.diet.ChildMealMenuTemplate;

/**
 * Шаблон меню приема пищи
 * @author oegorova
 *
 */

@Comment("Шаблон меню приема пищи")
@EntityForm
@EntityFormPersistance(clazz = MealMenuTemplate.class)
@WebTrail(comment = "Шаблон меню приема пищи", nameProperties= "description", view="entityParentView-diet_childMealMenuTemplate.do")
@Parent(property="parentMenu", parentForm=MealMenuTemplateForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/InvalidFood/MealMenuTemplate")

public class ChildMealMenuTemplateForm extends MealMenuTemplateForm{

	}
	
	
