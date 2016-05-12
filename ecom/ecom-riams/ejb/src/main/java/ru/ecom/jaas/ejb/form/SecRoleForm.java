package ru.ecom.jaas.ejb.form;

import javax.persistence.Id;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.annotation.PersistManyToManyOneProperty;
import ru.ecom.jaas.ejb.domain.SecRole;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.transforms.DoTimeString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;
import ru.nuzmsh.forms.validator.validators.TimeString;

/**
 * Роль
 */
@EntityForm
@EntityFormPersistance(clazz= SecRole.class)
@Comment("Роль")
@EntityFormSecurityPrefix("/Policy/Jaas/SecRole")
public class SecRoleForm  extends IdEntityForm {
    /** Идентификатор */
    @Id
    public long getId() { return theId ; }
    public void setId(long aId) { theId = aId ; }

    /** Название */
    @Persist
    @Required
    public String getName() { return theName ; }
    public void setName(String aName) { theName = aName ; }

    /** Комментарий */
    @Persist
    public String getComment() { return theComment ; }
    public void setComment(String aComment) { theComment = aComment ; }

    /** Ключ */
	@Comment("Ключ")
	@Persist @Required 
	public String getKey() {return theKey;}
	public void setKey(String aKey) {theKey = aKey;}
	
	/** Дочерние роли */
	@Comment("Дочерние роли")
	@Persist @PersistManyToManyOneProperty(collectionGenericType=SecRole.class)
	public String getChildren() {return theChildren;}
	public void setChildren(String aChildren) {theChildren = aChildren;}
	/** Системный? */
	@Comment("Системный?")
	@Persist
	public Boolean getIsSystems() {return theIsSystems;}
	public void setIsSystems(Boolean aIsSystems) {theIsSystems = aIsSystems;}

	/** Дата создания */
	@Comment("Дата создания")
	@DateString @DoDateString @Persist
	public String getCreateDate() {return theCreateDate;}
	public void setCreateDate(String aCreateDate) {theCreateDate = aCreateDate;}
	
	/** Дата редактирования */
	@Comment("Дата редактирования")
	@DateString @DoDateString @Persist
	public String getEditDate() {return theEditDate;}
	public void setEditDate(String aEditDate) {theEditDate = aEditDate;}
	
	/** Время создания */
	@Comment("Время создания")
	@TimeString @DoTimeString @Persist
	public String getCreateTime() {return theCreateTime;}
	public void setCreateTime(String aCreateTime) {theCreateTime = aCreateTime;}
	/** Время редактрования */
	@Comment("Время редактрования")
	@TimeString @DoTimeString @Persist
	public String getEditTime() {return theEditTime;}
	public void setEditTime(String aEditTime) {theEditTime = aEditTime;}
	/** Пользователь, который создал запись */
	@Comment("Пользователь, который создал запись")
	@Persist
	public String getCreateUsername() {return theCreateUsername;}
	public void setCreateUsername(String aCreateUsername) {theCreateUsername = aCreateUsername;}
	/** Пользователь, который последний редактировал запись */
	@Comment("Пользователь, который последний редактировал запись")
	@Persist
	public String getEditUsername() {return theEditUsername;}
	public void setEditUsername(String aEditUsername) {theEditUsername = aEditUsername;}

	/** Пользователь, который последний редактировал запись */
	private String theEditUsername;
	/** Пользователь, который создал запись */
	private String theCreateUsername;
	/** Время редактрования */
	private String theEditTime;
	/** Время создания */
	private String theCreateTime;
	/** Дата редактирования */
	private String theEditDate;
	/** Дата создания */
	private String theCreateDate;
	/** Системный? */
	private Boolean theIsSystems;
	/** Дочерние роли */
	private String theChildren;
	
	/** Ключ */
	private String theKey;
    /** Комментарий */
    private String theComment ;
    /** Название */
    private String theName ;
    /** Идентификатор */
    private long theId ;
}
