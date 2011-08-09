package ru.amokb.asset.ejb.form;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.amokb.asset.ejb.domain.PersonalComputer;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

@EntityForm
@EntityFormPersistance(clazz = PersonalComputer.class)
@Comment("Персональный компьютер")
@WebTrail(comment = "Персональный компьютер", nameProperties= "id", list="entityParentList-asset_personalComputer.do", view="entityParentView-asset_personalComputer.do")
@Parent(property="automatedWorkplace", parentForm=AutomatedWorkplaceForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Asset/PermanentAsset/AutomatedWorkplace/Equipment/PersonalComputer")
public class PersonalComputerForm extends ComputerForm{
	/**
	 * Пароль локального администратора
	 */
	@Comment("Пароль локального администратора")
	@Persist
	public Boolean getLocalAdminPassword() {
		return theLocalAdminPassword;
	}
	public void setLocalAdminPassword(Boolean aLocalAdminPassword) {
		theLocalAdminPassword = aLocalAdminPassword;
	}
	/**
	 * Пароль локального администратора
	 */
	private Boolean theLocalAdminPassword;
}
