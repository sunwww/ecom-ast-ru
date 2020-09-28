package ru.ecom.mis.ejb.domain.assessmentcard;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import java.sql.Date;

@Entity
public class AssessmentCardTemplate extends VocBaseEntity{
	
	/** Группировать параметры по группам */
	@Comment("Группировать параметры по группам")
	public Boolean	getIsGroupParameters() {return theIsGroupParameters;}
	public void setIsGroupParameters(Boolean aIsGroupParameters) {theIsGroupParameters = aIsGroupParameters;}
	/** Группировать параметры по группам */
	private Boolean theIsGroupParameters;	

	/** Дата создания */
	@Comment("Дата создания")
	public Date getCreateDate() {return theCreateDate;}
	public void setCreateDate(Date aCreateDate) {theCreateDate = aCreateDate;}

	/** Пользователь создавший запись */
	@Comment("Пользователь создавший запись")
	public String getCreateUsername() {return theCreateUsername;}
	public void setCreateUsername(String aCreateUsername) {theCreateUsername = aCreateUsername;}

	/** Пользователь создавший запись */
	private String theCreateUsername;
	/** Дата создания */
	private Date theCreateDate;

	/** В архиве */
	@Comment("В архиве")
	public Boolean getIsArchive() {return theIsArchive;}
	public void setIsArchive(Boolean aIsArchive) {theIsArchive = aIsArchive;}
	/** В архиве */
	private Boolean theIsArchive;

}
