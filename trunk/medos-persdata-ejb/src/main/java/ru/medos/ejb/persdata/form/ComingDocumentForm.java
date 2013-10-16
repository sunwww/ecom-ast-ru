package ru.medos.ejb.persdata.form;

import ru.ecom.ejb.services.entityform.WebTrail;
import ru.medos.ejb.persdata.domain.ComingDocument;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;

@EntityForm
@EntityFormPersistance(clazz = ComingDocument.class)
@Comment("Входящий документ")
@WebTrail(comment = "Входящий документ", nameProperties= "id"
, view="entityParentView-pd_comingDocument.do")
@Parent(property="person", parentForm=PersonForm.class)
@EntityFormSecurityPrefix("/Policy/PersData/ComingDocument")
public class ComingDocumentForm extends JournalDataForm{
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
	/**
	 * Серия
	 */
	@Comment("Серия")
	@Persist
	public String getSeries() {
		return theSeries;
	}
	public void setSeries(String aSeries) {
		theSeries = aSeries;
	}
	/**
	 * Серия
	 */
	private String theSeries;
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
	 * Государственный орган
	 */
	@Comment("Государственный орган")
	@Persist
	public Long getStateStructure() {
		return theStateStructure;
	}
	public void setStateStructure(Long aStateStructure) {
		theStateStructure = aStateStructure;
	}
	/**
	 * Государственный орган
	 */
	private Long theStateStructure;
	/**
	 * Фамилия
	 */
	@Comment("Фамилия")
	@Persist
	public String getLastname() {
		return theLastname;
	}
	public void setLastname(String aLastname) {
		theLastname = aLastname;
	}
	/**
	 * Фамилия
	 */
	private String theLastname;
	/**
	 * Отчество
	 */
	@Comment("Отчество")
	@Persist
	public String getPatronymic() {
		return thePatronymic;
	}
	public void setPatronymic(String aPatronymic) {
		thePatronymic = aPatronymic;
	}
	/**
	 * Отчество
	 */
	private String thePatronymic;
	/**
	 * Имя
	 */
	@Comment("Имя")
	@Persist
	public String getFirstname() {
		return theFirstname;
	}
	public void setFirstname(String aFirstname) {
		theFirstname = aFirstname;
	}
	/**
	 * Имя
	 */
	private String theFirstname;
	/**
	 * Дата рождения
	 */
	@Comment("Дата рождения")
	@Persist
	@DateString @DoDateString
	public String getBirthdate() {
		return theBirthdate;
	}
	public void setBirthdate(String aBirthdate) {
		theBirthdate = aBirthdate;
	}
	/**
	 * Дата рождения
	 */
	private String theBirthdate;
	/**
	 * Тип документа
	 */
	@Comment("Тип документа")
	@Persist
	public Long getType() {
		return theType;
	}
	public void setType(Long aType) {
		theType = aType;
	}
	/**
	 * Тип документа
	 */
	private Long theType;
	/**
	 * Комментарий
	 */
	@Comment("Комментарий")
	@Persist
	public String getComment() {
		return theComment;
	}
	public void setComment(String aComment) {
		theComment = aComment;
	}
	/**
	 * Комментарий
	 */
	private String theComment;


}
