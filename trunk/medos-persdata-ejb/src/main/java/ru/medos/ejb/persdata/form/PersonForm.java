package ru.medos.ejb.persdata.form;

import ru.ecom.ejb.services.entityform.WebTrail;
import ru.medos.ejb.persdata.domain.Person;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.transforms.DoUpperCase;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@EntityFormPersistance(clazz = Person.class)
@Comment("Персона")
@WebTrail(comment = "Персона", nameProperties= "id", view="entityParentView-pd_person.do"
,shortView="entityParentView-pd_person.do?short=Short")
@EntityFormSecurityPrefix("/Policy/PersData/Person")
public class PersonForm extends JournalDataForm {
	/**
	 * Фамилия
	 */
	@Comment("Фамилия")
	@Persist @Required @DoUpperCase
	public String getLastname() {return theLastname;}
	public void setLastname(String aLastname) {theLastname = aLastname;}
	/**
	 * Фамилия
	 */
	private String theLastname;
	/**
	 * Отчество
	 */
	@Comment("Отчество")
	@Persist @DoUpperCase
	public String getPatronymic() {return thePatronymic;}
	public void setPatronymic(String aPatronymic) {thePatronymic = aPatronymic;}
	/**
	 * Отчество
	 */
	private String thePatronymic;
	/**
	 * Имя
	 */
	@Comment("Имя")
	@Persist @Required @DoUpperCase
	public String getFirstname() {return theFirstname;}
	public void setFirstname(String aFirstname) {theFirstname = aFirstname;}
	/**
	 * Имя
	 */
	private String theFirstname;
	/**
	 * Дата рождения
	 */
	@Comment("Дата рождения")
	@Persist @Required
	@DateString @DoDateString
	public String getBirthdate() {return theBirthdate;}
	public void setBirthdate(String aBirthdate) {theBirthdate = aBirthdate;}
	/**
	 * Дата рождения
	 */
	private String theBirthdate;
	/**
	 * Пол
	 */
	@Comment("Пол")
	@Persist @Required
	public Long getSex() {return theSex;}
	public void setSex(Long aSex) {theSex = aSex;}
	/**
	 * Пол
	 */
	private Long theSex;
	/**
	 * СНИЛС
	 */
	@Comment("СНИЛС")
	@Persist
	public String getSnils() {return theSnils;}
	public void setSnils(String aSnils) {theSnils = aSnils;}
	/**
	 * СНИЛС
	 */
	private String theSnils;
	/**
	 * Национальность
	 */
	@Comment("Национальность")
	@Persist
	public Long getNationality() {return theNationality;}
	public void setNationality(Long aNationality) {theNationality = aNationality;}
	/**
	 * Национальность
	 */
	private Long theNationality;
	/**
	 * Электронная почта
	 */
	@Comment("Электронная почта")
	@Persist
	public String getEmail() {return theEmail;}
	public void setEmail(String aEmail) {theEmail = aEmail;}
	/**
	 * Электронная почта
	 */
	private String theEmail;
	/** Наименование подразделения */
	@Comment("Наименование подразделения")
	@Persist
	public String getStateStructureName() {return theStateStructureName;}
	public void setStateStructureName(String aStateStructureName) {theStateStructureName = aStateStructureName;}

	/** Код подразделения */
	@Comment("Код подразделения")
	@Persist
	public String getStateStructureCode() {return theStateStructureCode;}
	public void setStateStructureCode(String aStateStructureCode) {theStateStructureCode = aStateStructureCode;}

	/** Код подразделения */
	private String theStateStructureCode;
	/** Наименование подразделения */
	private String theStateStructureName;
}
