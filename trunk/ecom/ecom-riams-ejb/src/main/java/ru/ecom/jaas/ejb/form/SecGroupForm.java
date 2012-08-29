package ru.ecom.jaas.ejb.form;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.annotation.PersistManyToManyOneProperty;
import ru.ecom.jaas.ejb.domain.SecGroup;
import ru.ecom.jaas.ejb.domain.SecUser;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@EntityFormPersistance(clazz= SecGroup.class)
@Comment("Группы пользователей")
//@Parent(property = "parentSecPolicy", parentForm=SecPolicyForm.class, orderBy="name,key")
@EntityFormSecurityPrefix("/Policy/Jaas/SecGroup")
@WebTrail(comment="Группа пользователей", nameProperties="name", view="entityView-secgroup.do")
public class SecGroupForm extends IdEntityForm {
	/** Название группы */
	@Comment("Название группы")
	@Persist @Required 
	public String getName() {return theName;}
	public void setName(String aName) {theName = aName;}

	/** Список пользователей */
	@Comment("Список пользователей")
	@Persist @PersistManyToManyOneProperty(collectionGenericType=SecUser.class)
	public String getSecUsers() {return theSecUsers;}
	public void setSecUsers(String aUsers) {theSecUsers = aUsers;}

	/** Комментарий */
	@Comment("Комментарий")
	@Persist
	public String getComment() {return theComment;}
	public void setComment(String aComment) {theComment = aComment;}

	/** Комментарий */
	private String theComment;
	/** Список пользователей */
	private String theSecUsers;
	/** Название группы */
	private String theName;
}
