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
import ru.nuzmsh.forms.validator.validators.DateString;

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
}
