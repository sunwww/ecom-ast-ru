package ru.amokb.asset.ejb.form;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.amokb.asset.ejb.domain.ServerRole;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

@EntityForm
@EntityFormPersistance(clazz = ServerRole.class)
@Comment("Роль сервера")
@WebTrail(comment = "Роль сервера", nameProperties= "id", list="entityParentList-asset_serverRole.do", view="entityParentView-asset_serverRole.do")
//@Parent(property="parent", parentForm=PARENT.class)
@EntityFormSecurityPrefix("/Policy/Mis")
public class ServerRoleForm extends IdEntityForm{
	/**
	 * Сервер
	 */
	@Comment("Сервер")
	@Persist
	public Long getServer() {
		return theServer;
	}
	public void setServer(Long aServer) {
		theServer = aServer;
	}
	/**
	 * Сервер
	 */
	private Long theServer;
	/**
	 * Тип роли
	 */
	@Comment("Тип роли")
	@Persist
	public Long getRoleType() {
		return theRoleType;
	}
	public void setRoleType(Long aRoleType) {
		theRoleType = aRoleType;
	}
	/**
	 * Тип роли
	 */
	private Long theRoleType;
}
