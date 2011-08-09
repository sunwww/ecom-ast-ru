package ru.amokb.asset.ejb.form;

import ru.ecom.ejb.services.entityform.WebTrail;
import ru.amokb.asset.ejb.domain.Server;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

@EntityForm
@EntityFormPersistance(clazz = Server.class)
@Comment("Сервер")
@WebTrail(comment = "Сервер", nameProperties= "id", list="entityParentList-asset_server.do", view="entityParentView-asset_server.do")
@Parent(property="automatedWorkplace", parentForm=AutomatedWorkplaceForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Asset/PermanentAsset/AutomatedWorkplace/Equipment/Server")
public class ServerForm extends ComputerForm {
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
