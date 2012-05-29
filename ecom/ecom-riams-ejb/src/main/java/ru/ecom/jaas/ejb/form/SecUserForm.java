package ru.ecom.jaas.ejb.form;

import javax.persistence.Id;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.annotation.PersistManyToManyOneProperty;
import ru.ecom.jaas.ejb.domain.SecRole;
import ru.ecom.jaas.ejb.domain.SecUser;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;

/**
 * Пользователь
 */
@EntityForm
@EntityFormPersistance(clazz= SecUser.class)
@Comment("Пользователь")
@EntityFormSecurityPrefix("/Policy/Jaas/SecUser")
public class SecUserForm  extends IdEntityForm {
    /** Идентификатор */
    @Id
    public long getId() { return theId ; }
    public void setId(long aId) { theId = aId ; }

    /** Пароль */
    @Required
    @Persist
    public String getPassword() { return thePassword ; }
    public void setPassword(String aPassword) { thePassword = aPassword ; }

    /** Логин */
    @Required
    @Persist
    public String getLogin() { return theLogin ; }
    public void setLogin(String aLogin) { theLogin = aLogin ; }

    /** Полное имя */
    @Required
    @Persist
    public String getFullname() { return theFullname ; }
    public void setFullname(String aFullname) { theFullname = aFullname ; }

    /** Комментарий */
    @Persist
    public String getComment() { return theComment ; }
    public void setComment(String aComment) { theComment = aComment ; }

    /** Отключен */
	@Comment("Отключен")
	@Persist
	public Boolean getDisable() {return theDisable;}
	public void setDisable(Boolean aDisable) {theDisable = aDisable;}

	/** Закеширован */
	@Comment("Закеширован")
	@Persist
	public Boolean getIsHash() {return theIsHash;}
	public void setIsHash(Boolean aIsHash) {theIsHash = aIsHash;}

	/** Удаленный пользователь */
	@Comment("Удаленный пользователь")
	@Persist
	public Boolean getIsRemoteUser() {return theIsRemoteUser;}
	public void setIsRemoteUser(Boolean aIsRemoteUser) {theIsRemoteUser = aIsRemoteUser;}

	
	/** Рабочая функция */
	@Comment("Рабочая функция")
	public Long getWorkFunction() {return theWorkFunction;}
	public void setWorkFunction(Long aWorkFunction) {theWorkFunction = aWorkFunction;}

	/** Рабочая функция */
	private Long theWorkFunction;
	/** Удаленный пользователь */
	private Boolean theIsRemoteUser;
	/** Закеширован */
	private Boolean theIsHash;
	/** Отключен */
	private Boolean theDisable;
    /** Логин */
    private String theLogin ;
    /** Комментарий */
    private String theComment ;
    /** Идентификатор */
    private long theId ;
    /** Полное имя */
    private String theFullname ;
    /** Пароль */
    private String thePassword ;
}
