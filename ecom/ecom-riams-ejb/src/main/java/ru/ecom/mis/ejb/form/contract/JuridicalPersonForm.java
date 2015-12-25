package ru.ecom.mis.ejb.form.contract;

import javax.persistence.OneToOne;

import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.expomc.ejb.domain.registry.RegInsuranceCompany;
import ru.ecom.mis.ejb.domain.contract.JuridicalPerson;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoUpperCase;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@EntityFormPersistance(clazz = JuridicalPerson.class)
@Comment("Юридическое лицо")
@WebTrail(comment = "Юридическое лицо", nameProperties= "id", list="entityParentList-contract_juridicalPerson.do", view="entityParentView-contract_juridicalPerson.do")
//@Parent(property="parent", parentForm=PARENT.class)
@EntityFormSecurityPrefix("/Policy/Mis/Contract/ContractPerson/JuridicalPerson")
public class JuridicalPersonForm extends ContractPersonForm{
	/**
	 * Электронная почта
	 */
	@Comment("Электронная почта")
	@Persist
	public String getEmail() {
		return theEmail;
	}
	public void setEmail(String aEmail) {
		theEmail = aEmail;
	}
	/**
	 * Электронная почта
	 */
	private String theEmail;
	/**
	 * Телефоны
	 */
	@Comment("Телефоны")
	@Persist
	public String getPhones() {
		return thePhones;
	}
	public void setPhones(String aPhones) {
		thePhones = aPhones;
	}
	/**
	 * Телефоны
	 */
	private String thePhones;
	/**
	 * Факс
	 */
	@Comment("Факс")
	@Persist
	public String getFax() {
		return theFax;
	}
	public void setFax(String aFax) {
		theFax = aFax;
	}
	/**
	 * Факс
	 */
	private String theFax;
	/**
	 * КПП
	 */
	@Comment("КПП")
	@Persist
	public String getKpp() {
		return theKpp;
	}
	public void setKpp(String aKpp) {
		theKpp = aKpp;
	}
	/**
	 * КПП
	 */
	private String theKpp;
	/**
	 * ИНН
	 */
	@Comment("ИНН")
	@Persist
	public String getInn() {
		return theInn;
	}
	public void setInn(String aInn) {
		theInn = aInn;
	}
	/**
	 * ИНН
	 */
	private String theInn;
	/**
	 * БИК
	 */
	@Comment("БИК")
	@Persist
	public String getBic() {
		return theBic;
	}
	public void setBic(String aBic) {
		theBic = aBic;
	}
	/**
	 * БИК
	 */
	private String theBic;
	/**
	 * Короткое название
	 */
	@Comment("Короткое название")
	@Persist @DoUpperCase
	public String getShortName() {
		return theShortName;
	}
	public void setShortName(String aShortName) {
		theShortName = aShortName;
	}
	/**
	 * Короткое название
	 */
	private String theShortName;
	/**
	 * Полное название
	 */
	@Comment("Полное название")
	@Persist @Required @DoUpperCase
	public String getName() {
		return theName;
	}
	public void setName(String aName) {
		theName = aName;
	}
	/**
	 * Полное название
	 */
	private String theName;
	/**
	 * Директор
	 */
	@Comment("Директор")
	@Persist
	public String getDirector() {
		return theDirector;
	}
	public void setDirector(String aDirector) {
		theDirector = aDirector;
	}
	/**
	 * Директор
	 */
	private String theDirector;
	/**
	 * Почтовый адрес
	 */
	@Comment("Почтовый адрес")
	@Persist
	public String getPostAddress() {
		return thePostAddress;
	}
	public void setPostAddress(String aPostAddress) {
		thePostAddress = aPostAddress;
	}
	/**
	 * Почтовый адрес
	 */
	private String thePostAddress;
	/**
	 * Счет
	 */
	@Comment("Счет")
	@Persist
	public String getAccount() {
		return theAccount;
	}
	public void setAccount(String aAccount) {
		theAccount = aAccount;
	}
	/**
	 * Счет
	 */
	private String theAccount;
	/**
	 * Корреспондентский счет
	 */
	@Comment("Корреспондентский счет")
	@Persist
	public String getCorAccount() {
		return theCorAccount;
	}
	public void setCorAccount(String aCorAccount) {
		theCorAccount = aCorAccount;
	}
	/**
	 * Корреспондентский счет
	 */
	private String theCorAccount;
	/**
	 * Юридический адрес
	 */
	@Comment("Юридический адрес")
	@Persist
	public String getJuridicalAddress() {
		return theJuridicalAddress;
	}
	public void setJuridicalAddress(String aJuridicalAddress) {
		theJuridicalAddress = aJuridicalAddress;
	}
	/**
	 * Юридический адрес
	 */
	private String theJuridicalAddress;
	/**
	 * Тип юридической персоны
	 */
	@Comment("Тип юридической персоны")
	@Persist
	public Long getJuridicalPersonType() {
		return theJuridicalPersonType;
	}
	public void setJuridicalPersonType(Long aJuridicalPersonType) {
		theJuridicalPersonType = aJuridicalPersonType;
	}
	/**
	 * Тип юридической персоны
	 */
	private Long theJuridicalPersonType;
	/**
	 * Организация
	 */
	@Comment("Организация")
	@Persist
	public Long getOrganization() {
		return theOrganization;
	}
	public void setOrganization(Long aOrganization) {
		theOrganization = aOrganization;
	}
	/**
	 * Организация
	 */
	private Long theOrganization;
	/**
	 * Статус обслуживаемой персоны
	 */
	@Comment("Статус обслуживаемой персоны")
	@Persist
	public Long getServedPersonStatus() {
		return theServedPersonStatus;
	}
	public void setServedPersonStatus(Long aServedPersonStatus) {
		theServedPersonStatus = aServedPersonStatus;
	}
	/**
	 * Статус обслуживаемой персоны
	 */
	private Long theServedPersonStatus;
	/**
	 * Программа обслуживания
	 */
	@Comment("Программа обслуживания")
	@Persist
	public Long getServiceProgram() {
		return theServiceProgram;
	}
	public void setServiceProgram(Long aServiceProgram) {
		theServiceProgram = aServiceProgram;
	}
	/**
	 * Программа обслуживания
	 */
	private Long theServiceProgram;
	/**
	 * Территория
	 */
	@Comment("Территория")
	@Persist
	public Long getTerritory() {
		return theTerritory;
	}
	public void setTerritory(Long aTerritory) {
		theTerritory = aTerritory;
	}
	/**
	 * Территория
	 */
	private Long theTerritory;

	/** Страховая компания */
	@Comment("Страховая компания")
	@Persist
	public Long getRegCompany() {return theRegCompany;}
	public void setRegCompany(Long aRegCompany) {theRegCompany = aRegCompany;}

	/** Страховая компания */
	private Long theRegCompany;

}
