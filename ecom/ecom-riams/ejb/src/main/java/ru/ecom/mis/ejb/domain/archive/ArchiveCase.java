package ru.ecom.mis.ejb.domain.archive;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.Entity;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
public class ArchiveCase extends BaseEntity {

	/** Номер стат. карты */
	@Comment("Номер стат. карты")
	public Long getStatCard() {return theStatCard;}
	public void setStatCard(Long aStatCard) {theStatCard = aStatCard;}
	/** Номер стат. карты */
	private Long theStatCard;
	
	/** Пациент */
	@Comment("Пациент")
	public Long getPatient() {return thePatient;}
	public void setPatient(Long aPatient) {thePatient = aPatient;}
	/** Пациент */
	private Long thePatient;
	
	/** Дата создания */
	@Comment("Дата создания")
	public Date getCreateDate() {return theCreateDate;}
	public void setCreateDate(Date aCreateDate) {theCreateDate = aCreateDate;}
	/** Дата создания */
	private Date theCreateDate;
	
	/** Время создания */
	@Comment("Время создания")
	public Time getCreateTime() {return theCreateTime;}
	public void setCreateTime(Time aCreateTime) {theCreateTime = aCreateTime;}
	/** Время создания */
	private Time theCreateTime;
	
	/** Пользователь, создавший запись */
	@Comment("Пользователь, создавший запись")
	public String getCreateUsername() {return theCreateUsername;}
	public void setCreateUsername(String aCreateUsername) {theCreateUsername = aCreateUsername;}
	/** Пользователь, создавший запись */
	private String theCreateUsername;
	
	/** Рабочая функция создателя */
	@Comment("Рабочая функция создателя")
	public Long getWorkFunction() {return theWorkFunction;}
	public void setWorkFunction(Long aWorkFunction) {theWorkFunction = aWorkFunction;}
	/** Рабочая функция создателя */
	private Long theWorkFunction;
	
}
