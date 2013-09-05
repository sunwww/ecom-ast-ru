package ru.medos.ejb.persdata.form;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.medos.ejb.persdata.domain.Person;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;

@EntityForm
@EntityFormPersistance(clazz = Person.class)
@Comment("Персона")
@WebTrail(comment = "Персона", nameProperties= "id", list="entityParentList-personaldata_person.do", view="entityParentView-personaldata_person.do")
//@Parent(property="parent", parentForm=PARENT.class)
@EntityFormSecurityPrefix("/Policy/PersData/Person")
public class PersonForm extends IdEntityForm{
	/**
	 * Фамилия
	 */
	@Comment("Фамилия")
	@Persist
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
	@Persist
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
	@Persist
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
	@Persist
	@DateString @DoDateString
	public String getBirthday() {return theBirthday;}
	public void setBirthday(String aBirthday) {theBirthday = aBirthday;}
	/**
	 * Дата рождения
	 */
	private String theBirthday;
	/**
	 * Пол
	 */
	@Comment("Пол")
	@Persist
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
}
