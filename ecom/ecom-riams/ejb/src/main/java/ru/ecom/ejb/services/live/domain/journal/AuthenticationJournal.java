package ru.ecom.ejb.services.live.domain.journal;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Date;
import java.sql.Time;

@Entity
@AIndexes({
    @AIndex(properties= {"username"})
})
@Table(schema="SQLUser")
public class AuthenticationJournal extends BaseEntity {
	/** Пользователь */
	@Comment("Пользователь")
	public String getUsername() {return theUsername;}
	public void setUsername(String aUsername) {theUsername = aUsername;}

	/** Дата аутентификации */
	@Comment("Дата аутентификации")
	public Date getAuthDate() {return theAuthDate;}
	public void setAuthDate(Date aAuthDate) {theAuthDate = aAuthDate;}

	/** Время аутентификации */
	@Comment("Время аутентификации")
	public Time getAuthTime() {return theAuthTime;}
	public void setAuthTime(Time aAuthTime) {theAuthTime = aAuthTime;}

	/** Прошел проверку */
	@Comment("Прошел проверку")
	public Boolean getIsChecked() {return theIsChecked;}
	public void setIsChecked(Boolean aIsChecked) {theIsChecked = aIsChecked;}

	/** Remote Add */
	@Comment("Remote Add")
	public String getRemoteAdd() {return theRemoteAdd;}
	public void setRemoteAdd(String aRemoteAdd) {theRemoteAdd = aRemoteAdd;}

	/** Local Add */
	@Comment("Local Add")
	public String getLocalAdd() {return theLocalAdd;}
	public void setLocalAdd(String aLocalAdd) {theLocalAdd = aLocalAdd;}

	/** Server name */
	@Comment("Server name")
	public String getServerName() {return theServerName;}
	public void setServerName(String aServerName) {theServerName = aServerName;}

	/** Server name */
	private String theServerName;
	/** Local Add */
	private String theLocalAdd;
	/** Remote Add */
	private String theRemoteAdd;
	/** Прошел проверку */
	private Boolean theIsChecked;
	/** Время аутентификации */
	private Time theAuthTime;
	/** Дата аутентификации */
	private Date theAuthDate;
	/** Пользователь */
	private String theUsername;
	
	/** Описание ошибки */
	@Comment("Описание ошибки")
	@Column(length=400)
	public String getErrorMessage() {return theErrorMessage;}
	public void setErrorMessage(String aErrorMessage) {theErrorMessage = aErrorMessage;}

	/** Ошибочный пароль */
	@Comment("Ошибочный пароль")
	public String getErrorPassword() {return theErrorPassword;}
	public void setErrorPassword(String aErrorPassword) {theErrorPassword = aErrorPassword;}

	/** Ошибочный пароль */
	private String theErrorPassword;
	/** Описание ошибки */
	private String theErrorMessage;
}
