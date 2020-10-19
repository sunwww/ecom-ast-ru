package ru.ecom.jaas.ejb.domain;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

/**
 * Пользователь
 */
@Entity
@AIndexes({
    @AIndex(properties= ("login"))
    ,@AIndex(properties= ("fullname"))
    ,@AIndex(properties="disable")
    ,@AIndex(properties="isRemoteUser")
})
@Table(schema="SQLUser")
public class SecUser extends BaseEntity {

	/** Сменить пароль при входе в систему */
	@Comment("Сменить пароль при входе в систему")
	public Boolean getChangePasswordAtLogin() {return theChangePasswordAtLogin;}
	public void setChangePasswordAtLogin(Boolean aChangePasswordAtLogin) {theChangePasswordAtLogin = aChangePasswordAtLogin;}
	/** Сменить пароль при входе в систему */
	private Boolean theChangePasswordAtLogin;

	/** Дата последнего изменение пароля */
	@Comment("Дата последнего изменение пароля")
	public Date getPasswordChangedDate() {return thePasswordChangedDate;}
	public void setPasswordChangedDate(Date aPasswordChangedDate) {thePasswordChangedDate = aPasswordChangedDate;}
	/** Дата последнего изменение пароля */
	private Date thePasswordChangedDate;

	/** Наименование пользователя */
    public String getLogin() { return theLogin ; }
    public void setLogin(String aLogin) { theLogin = aLogin ; }

    /** Полное имя пользователя */
    public String getFullname() { return theFullname ; }
    public void setFullname(String aFullname) { theFullname = aFullname ; }

    /** Комментарий */
    public String getComment() { return theComment ; }
    public void setComment(String aComment) { theComment = aComment ; }

    /** Пароль */
    public String getPassword() { return thePassword ; }
    public void setPassword(String aPassword) { thePassword = aPassword ; }

    /** Отключен */
    public boolean getDisabled() { return theDisabled ; }
    public void setDisabled(boolean aDisabled) { theDisabled = aDisabled ; }

    /** Роли пользователя */
    @ManyToMany
    public List<SecRole> getRoles() { return theRoles ; }
    public void setRoles(List<SecRole> aRoles) { theRoles = aRoles ; }

    /** Отключен */
	@Comment("Отключен")
	public Boolean getDisable() {return theDisable;}
	public void setDisable(Boolean aDisable) {theDisable = aDisable;}

	/** Закеширован */
	@Comment("Закеширован")
	public Boolean getIsHash() {return theIsHash;}
	public void setIsHash(Boolean aIsHash) {theIsHash = aIsHash;}

	/** Удаленный пользователь */
	@Comment("Удаленный пользователь")
	public Boolean getIsRemoteUser() {return theIsRemoteUser;}
	public void setIsRemoteUser(Boolean aIsRemoteUser) {theIsRemoteUser = aIsRemoteUser;}

	/** Системный? */
	@Comment("Системный?")
	public Boolean getIsSystems() {return theIsSystems;}
	public void setIsSystems(Boolean aIsSystems) {theIsSystems = aIsSystems;}

	/** Дата создания */
	@Comment("Дата создания")
	public Date getCreateDate() {return theCreateDate;}
	public void setCreateDate(Date aCreateDate) {theCreateDate = aCreateDate;}
	
	/** Дата редактирования */
	@Comment("Дата редактирования")
	public Date getEditDate() {return theEditDate;}
	public void setEditDate(Date aEditDate) {theEditDate = aEditDate;}
	
	/** Время создания */
	@Comment("Время создания")
	public Time getCreateTime() {return theCreateTime;}
	public void setCreateTime(Time aCreateTime) {theCreateTime = aCreateTime;}
	/** Время редактрования */
	@Comment("Время редактрования")
	public Time getEditTime() {return theEditTime;}
	public void setEditTime(Time aEditTime) {theEditTime = aEditTime;}
	/** Пользователь, который создал запись */
	@Comment("Пользователь, который создал запись")
	public String getCreateUsername() {return theCreateUsername;}
	public void setCreateUsername(String aCreateUsername) {theCreateUsername = aCreateUsername;}
	/** Пользователь, который последний редактировал запись */
	@Comment("Пользователь, который последний редактировал запись")
	public String getEditUsername() {return theEditUsername;}
	public void setEditUsername(String aEditUsername) {theEditUsername = aEditUsername;}

	/** Можно копировать роли в инфекционное отделение */
	@Comment("Можно копировать роли в инфекционное отделение")
	public Boolean getEnabledForCopy() {return theEnabledForCopy;}
	public void setEnabledForCopy(Boolean aEnabledForCopy) {theEnabledForCopy = aEnabledForCopy;}

	/** Пользователь, который последний редактировал запись */
	private String theEditUsername;
	/** Пользователь, который создал запись */
	private String theCreateUsername;
	/** Время редактрования */
	private Time theEditTime;
	/** Время создания */
	private Time theCreateTime;
	/** Дата редактирования */
	private Date theEditDate;
	/** Дата создания */
	private Date theCreateDate;
	/** Системный? */
	private Boolean theIsSystems;
	/** Удаленный пользователь */
	private Boolean theIsRemoteUser;
	/** Закеширован */
	private Boolean theIsHash;
	/** Отключен */
	private Boolean theDisable;
/** Роли пользователя */
    private List<SecRole> theRoles ;
    /** Отключен */
    private boolean theDisabled ;
    /** Комментарий */
    private String theComment ;
    /** Полное имя пользователя */
    private String theFullname ;
    /** Наименование пользователя */
    private String theLogin ;
    /** Пароль */
    private String thePassword ;
    /** Можно копировать роли в инфекционное отделение*/
	private Boolean theEnabledForCopy ;
}
