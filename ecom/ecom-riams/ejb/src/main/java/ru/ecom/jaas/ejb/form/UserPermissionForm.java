package ru.ecom.jaas.ejb.form;

import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.jaas.ejb.domain.UserPermission;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

@EntityForm
@EntityFormPersistance(clazz = UserPermission.class)
@Comment("Разрешения пользователю")
@EntityFormSecurityPrefix("/Policy/Jaas/Permission/User")
@WebTrail(comment="Разрешения пользователю", nameProperties="id", view="entityView-sec_userPermission.do")
public class UserPermissionForm extends PermissionForm {
	/** Пользователь */
	@Comment("Пользователь")
	@Persist 
	public Long getUsername() {return theUsername;}
	public void setUsername(Long aUsername) {theUsername = aUsername;}

	/** Пользователь инфо */
	@Comment("Пользователь инфо")
	@Persist
	public String getUserInfo() {return theUserInfo;}
	public void setUserInfo(String aUserInfo) {theUserInfo = aUserInfo;}

	/** Пользователь инфо */
	private String theUserInfo;
	/** Пользователь */
	private Long theUsername;
}
