package ru.ecom.mis.ejb.form.diet;

import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.diet.DishMealMenu;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

/**
 * Шаблон блюда меню
 * @author oegorova
 */

@EntityForm
@EntityFormPersistance(clazz = DishMealMenu.class)
@Comment("Шаблон блюда меню")
@WebTrail(comment = "Шаблон блюда меню", nameProperties= "id", view="entityView-diet_dishMealMenuTemplate.do")
@Parent(property="menu", parentForm=ChildMealMenuTemplateForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/InvalidFood/DishMealMenu")
public class DishMealMenuTemplateForm extends DishMealMenuForm {

}