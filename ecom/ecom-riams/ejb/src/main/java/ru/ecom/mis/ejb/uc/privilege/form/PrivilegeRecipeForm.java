package ru.ecom.mis.ejb.uc.privilege.form;

import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.uc.privilege.domain.PrivilegeRecipe;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

@EntityForm
@EntityFormPersistance(clazz= PrivilegeRecipe.class)
@WebTrail(comment = "Рецепт", nameProperties= "info"
	, view="entityParentView-mis_privilegeRecipe.do"
	, shortView="entityShortView-mis_privilegeRecipe.do")
@Parent(property="privilege", parentForm=PrivilegeForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Person/Privilege/Recipe")
public class PrivilegeRecipeForm extends RecipeForm {
	/** Льгота */
	@Comment("Льгота")
	public Long getPrivilege() {
		return thePrivilege;
	}

	public void setPrivilege(Long aPrivilege) {
		thePrivilege = aPrivilege;
	}
	/** Льгота */
	private Long thePrivilege;
	
	/** Решение КЭК */
	@Comment("Решение КЭК")
	public boolean getKekDesicion() {
		return theKekDesicion;
	}

	public void setKekDesicion(boolean aKekDesicion) {
		theKekDesicion = aKekDesicion;
	}
	
	/** Решение КЭК */
	private boolean theKekDesicion;
}
