package ru.ecom.jaas.ejb.form;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.jaas.ejb.domain.SecUser;
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

import javax.persistence.Id;

/**
 * Пользователь
 */
@EntityForm
@EntityFormPersistance(clazz= SecUser.class)
@Comment("Пользователь")
@EntityFormSecurityPrefix("/Policy/Jaas/SecUser")
public class SecUserForm  extends IdEntityForm {

	/** Сменить пароль при входе в систему */
	@Comment("Сменить пароль при входе в систему")
	@Persist
	public Boolean getChangePasswordAtLogin() {return theChangePasswordAtLogin;}
	public void setChangePasswordAtLogin(Boolean aChangePasswordAtLogin) {theChangePasswordAtLogin = aChangePasswordAtLogin;}
	/** Сменить пароль при входе в систему */
	private Boolean theChangePasswordAtLogin;
	
	/** Дата изменение пароля */
	@Comment("Дата изменение пароля")
	@Persist
	public String getPasswordChangedDate() {return thePasswordChangedDate;}
	public void setPasswordChangedDate(String aPasswordChangedDate) {thePasswordChangedDate = aPasswordChangedDate;}
	/** Дата изменение пароля */
	private String thePasswordChangedDate;
	
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

	/** Пользователь */
	@Comment("Пользователь")
	public Long getUserCopy() {return theUserCopy;}
	public void setUserCopy(Long aUserCopy) {theUserCopy = aUserCopy;}

	/** Можно копировать роли в инфекционное отделение */
	@Comment("Можно копировать роли в инфекционное отделение")
	public Boolean getEnabledForCopy() {return theEnabledForCopy;}
	public void setEnabledForCopy(Boolean aEnabledForCopy) {theEnabledForCopy = aEnabledForCopy;}

	/** Пользователь */
	private Long theUserCopy;
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
	/** Можно копировать роли в инфекционное отделение*/
	private Boolean theEnabledForCopy ;
}
