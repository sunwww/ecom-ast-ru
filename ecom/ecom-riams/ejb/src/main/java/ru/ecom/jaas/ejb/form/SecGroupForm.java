package ru.ecom.jaas.ejb.form;

import lombok.Getter;
import lombok.Setter;
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
@Setter
public class SecGroupForm extends IdEntityForm {
	/** Название группы */
	@Comment("Название группы")
	@Persist @Required 
	public String getName() {return name;}

	/** Список пользователей */
	@Comment("Список пользователей")
	@Persist @PersistManyToManyOneProperty(collectionGenericType=SecUser.class)
	public String getSecUsers() {return secUsers;}

	/** Комментарий */
	@Comment("Комментарий")
	@Persist
	public String getComment() {return comment;}

	/** Комментарий */
	private String comment;
	/** Список пользователей */
	private String secUsers;
	/** Название группы */
	private String name;
}
