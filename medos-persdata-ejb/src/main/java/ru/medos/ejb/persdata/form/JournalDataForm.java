package ru.medos.ejb.persdata.form;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.medos.ejb.persdata.domain.JournalData;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.transforms.DoTimeString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.TimeString;

@EntityForm
@EntityFormPersistance(clazz = JournalData.class)
@Comment("Журналируемые данные")
@WebTrail(comment = "Журналируемые данные", nameProperties= "id", list="entityParentList-personaldata_journalData.do", view="entityParentView-personaldata_journalData.do")
//@Parent(property="parent", parentForm=PARENT.class)
@EntityFormSecurityPrefix("/Policy/Mis")
public class JournalDataForm extends IdEntityForm{
	/**
	 * Дата начала актуальности
	 */
	@Comment("Дата начала актуальности")
	@Persist
	@DateString @DoDateString
	public String getUrgencyStartDate() {
		return theUrgencyStartDate;
	}
	public void setUrgencyStartDate(String aUrgencyStartDate) {
		theUrgencyStartDate = aUrgencyStartDate;
	}
	/**
	 * Дата начала актуальности
	 */
	private String theUrgencyStartDate;
	/**
	 * Дата окончания актуальности
	 */
	@Comment("Дата окончания актуальности")
	@Persist
	@DateString @DoDateString
	public String getUrgencyExpiryDate() {
		return theUrgencyExpiryDate;
	}
	public void setUrgencyExpiryDate(String aUrgencyExpiryDate) {
		theUrgencyExpiryDate = aUrgencyExpiryDate;
	}
	/**
	 * Дата окончания актуальности
	 */
	private String theUrgencyExpiryDate;
	/**
	 * Удалено
	 */
	@Comment("Удалено")
	@Persist
	public Boolean getIsDeleted() {
		return theIsDeleted;
	}
	public void setIsDeleted(Boolean aIsDeleted) {
		theIsDeleted = aIsDeleted;
	}
	/**
	 * Удалено
	 */
	private Boolean theIsDeleted;
	
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

}
