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
import ru.nuzmsh.forms.validator.validators.Required;

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
