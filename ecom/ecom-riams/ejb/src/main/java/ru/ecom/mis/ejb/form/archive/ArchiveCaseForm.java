package ru.ecom.mis.ejb.form.archive;

import java.sql.Date;
import java.sql.Time;

import lombok.Setter;
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
@Setter
public class ArchiveCaseForm extends IdEntityForm{
	/** Номер стат. карты */
	@Comment("Номер стат. карты")
	@Persist @Required
	public Long getStatCard() {return statCard;}
	/** Номер стат. карты */
	private Long statCard;
	
	/** Пациент */
	@Comment("Пациент")
	@Persist
	public Long getPatient() {return patient;}
	/** Пациент */
	private Long patient;
	
	/** Дата создания */
	@Comment("Дата создания")
	@Persist
	public Date getCreateDate() {return createDate;}
	/** Дата создания */
	private Date createDate;
	
	/** Время создания */
	@Comment("Время создания")
	@Persist
	public Time getCreateTime() {return createTime;}
	/** Время создания */
	private Time createTime;
	
	/** Пользователь, создавший запись */
	@Comment("Пользователь, создавший запись")
	@Persist
	public String getCreateUsername() {return createUsername;}
	/** Пользователь, создавший запись */
	private String createUsername;
	
	/** Рабочая функция создателя */
	@Comment("Рабочая функция создателя")
	@Persist
	public Long getWorkFunction() {return workFunction;}
	/** Рабочая функция создателя */
	private Long workFunction;
}
