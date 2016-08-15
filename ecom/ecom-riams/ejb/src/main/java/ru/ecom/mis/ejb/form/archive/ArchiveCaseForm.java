package ru.ecom.mis.ejb.form.archive;

import java.sql.Date;
import java.sql.Time;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.archive.ArchiveCase;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@EntityFormPersistance(clazz= ArchiveCase.class)
@Comment("Архивное дело")
@WebTrail(comment = "Архивное дело", nameProperties= "id", view="entityView-mis_archiveCase.do" ,list = "entityList-mis_archiveCase.do")
@EntityFormSecurityPrefix("/Policy/Mis/ArchiveCase")
public class ArchiveCaseForm extends IdEntityForm{
	/** Номер стат. карты */
	@Comment("Номер стат. карты")
	@Persist @Required
	public Long getStatCard() {return theStatCard;}
	public void setStatCard(Long aStatCard) {theStatCard = aStatCard;}
	/** Номер стат. карты */
	private Long theStatCard;
	
	/** Пациент */
	@Comment("Пациент")
	@Persist
	public Long getPatient() {return thePatient;}
	public void setPatient(Long aPatient) {thePatient = aPatient;}
	/** Пациент */
	private Long thePatient;
	
	/** Дата создания */
	@Comment("Дата создания")
	@Persist
	public Date getCreateDate() {return theCreateDate;}
	public void setCreateDate(Date aCreateDate) {theCreateDate = aCreateDate;}
	/** Дата создания */
	private Date theCreateDate;
	
	/** Время создания */
	@Comment("Время создания")
	@Persist
	public Time getCreateTime() {return theCreateTime;}
	public void setCreateTime(Time aCreateTime) {theCreateTime = aCreateTime;}
	/** Время создания */
	private Time theCreateTime;
	
	/** Пользователь, создавший запись */
	@Comment("Пользователь, создавший запись")
	@Persist
	public String getCreateUsername() {return theCreateUsername;}
	public void setCreateUsername(String aCreateUsername) {theCreateUsername = aCreateUsername;	}
	/** Пользователь, создавший запись */
	private String theCreateUsername;
	
	/** Рабочая функция создателя */
	@Comment("Рабочая функция создателя")
	@Persist
	public Long getWorkFunction() {return theWorkFunction;}
	public void setWorkFunction(Long aWorkFunction) {theWorkFunction = aWorkFunction;}
	/** Рабочая функция создателя */
	private Long theWorkFunction;
}
