package ru.medos.ejb.persdata.form;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.medos.ejb.persdata.domain.OutgoingDocument;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;

@EntityForm
@EntityFormPersistance(clazz = OutgoingDocument.class)
@Comment("Исходящий документ")
@WebTrail(comment = "Исходящий документ", nameProperties= "id", list="entityParentList-personaldata_outgoingDocument.do", view="entityParentView-personaldata_outgoingDocument.do")
//@Parent(property="parent", parentForm=PARENT.class)
@EntityFormSecurityPrefix("/Policy/Mis")
public class OutgoingDocumentForm extends JournalDataForm{
	/**
	 * Дата изготовления
	 */
	@Comment("Дата изготовления")
	@Persist
	@DateString @DoDateString
	public String getProductionDate() {
		return theProductionDate;
	}
	public void setProductionDate(String aProductionDate) {
		theProductionDate = aProductionDate;
	}
	/**
	 * Дата изготовления
	 */
	private String theProductionDate;
	/**
	 * Дата выдачи
	 */
	@Comment("Дата выдачи")
	@Persist
	@DateString @DoDateString
	public String getIssueDate() {
		return theIssueDate;
	}
	public void setIssueDate(String aIssueDate) {
		theIssueDate = aIssueDate;
	}
	/**
	 * Дата выдачи
	 */
	private String theIssueDate;
	/**
	 * Выдана лично владельцу
	 */
	@Comment("Выдана лично владельцу")
	@Persist
	public Boolean getIsPersonallyIssue() {
		return theIsPersonallyIssue;
	}
	public void setIsPersonallyIssue(Boolean aIsPersonallyIssue) {
		theIsPersonallyIssue = aIsPersonallyIssue;
	}
	/**
	 * Выдана лично владельцу
	 */
	private Boolean theIsPersonallyIssue;
	/**
	 * Номер
	 */
	@Comment("Номер")
	@Persist
	public String getDocumentNumber() {
		return theDocumentNumber;
	}
	public void setDocumentNumber(String aDocumentNumber) {
		theDocumentNumber = aDocumentNumber;
	}
	/**
	 * Номер
	 */
	private String theDocumentNumber;
	/**
	 * Персона
	 */
	@Comment("Персона")
	@Persist
	public Long getPerson() {
		return thePerson;
	}
	public void setPerson(Long aPerson) {
		thePerson = aPerson;
	}
	/**
	 * Персона
	 */
	private Long thePerson;
}
