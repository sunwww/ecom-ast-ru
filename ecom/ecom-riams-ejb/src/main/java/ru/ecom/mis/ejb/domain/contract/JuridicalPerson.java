package ru.ecom.mis.ejb.domain.contract;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.expomc.ejb.domain.omcvoc.OmcKodTer;
import ru.ecom.expomc.ejb.domain.registry.RegInsuranceCompany;
import ru.ecom.mis.ejb.domain.contract.voc.VocJuridicalPerson;
import ru.ecom.mis.ejb.domain.contract.voc.VocServedPersonStatus;
import ru.ecom.mis.ejb.domain.contract.voc.VocServiceProgram;
import ru.ecom.mis.ejb.domain.patient.voc.VocOrg;
import ru.ecom.mis.ejb.domain.workcalendar.voc.VocServiceStream;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
	/**
	 * Юридическое лицо
	 */
	@Comment("Юридическое лицо")
@Entity
@Table(schema="SQLUser")
@AIndexes(value = { 
		@AIndex(properties = { "name" },table="ContractPerson")
		}
)

public class JuridicalPerson extends ContractPerson{
	/**
	 * Электронная почта
	 */
	@Comment("Электронная почта")
	
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
	@OneToOne
	public VocJuridicalPerson getJuridicalPersonType() {
		return theJuridicalPersonType;
	}
	public void setJuridicalPersonType(VocJuridicalPerson aJuridicalPersonType) {
		theJuridicalPersonType = aJuridicalPersonType;
	}
	/**
	 * Тип юридической персоны
	 */
	private VocJuridicalPerson theJuridicalPersonType;
	/**
	 * Организация
	 */
	@Comment("Организация")
	@OneToOne
	public VocOrg getOrganization() {
		return theOrganization;
	}
	public void setOrganization(VocOrg aOrganization) {
		theOrganization = aOrganization;
	}
	/**
	 * Организация
	 */
	private VocOrg theOrganization;
	/**
	 * Статус обслуживаемой персоны
	 */
	@Comment("Статус обслуживаемой персоны")
	@OneToOne
	public VocServedPersonStatus getServedPersonStatus() {
		return theServedPersonStatus;
	}
	public void setServedPersonStatus(VocServedPersonStatus aServedPersonStatus) {
		theServedPersonStatus = aServedPersonStatus;
	}
	/**
	 * Статус обслуживаемой персоны
	 */
	private VocServedPersonStatus theServedPersonStatus;
	/**
	 * Программа обслуживания
	 */
	@Comment("Программа обслуживания")
	@OneToOne
	public VocServiceProgram getServiceProgram() {
		return theServiceProgram;
	}
	public void setServiceProgram(VocServiceProgram aServiceProgram) {
		theServiceProgram = aServiceProgram;
	}
	/**
	 * Программа обслуживания
	 */
	private VocServiceProgram theServiceProgram;
	/**
	 * Территория
	 */
	@Comment("Территория")
	@OneToOne
	public OmcKodTer getTerritory() {
		return theTerritory;
	}
	public void setTerritory(OmcKodTer aTerritory) {
		theTerritory = aTerritory;
	}
	/**
	 * Территория
	 */
	private OmcKodTer theTerritory;
	
	@Transient
	public String getInformation() {
		return new StringBuilder().append("Юрид. лицо: ").append(theName).toString() ;
	}
	/** Страховая компания */
	@Comment("Страховая компания")
	@OneToOne
	public RegInsuranceCompany getRegCompany() {return theRegCompany;}
	public void setRegCompany(RegInsuranceCompany aRegCompany) {theRegCompany = aRegCompany;}

	/** Страховая компания */
	private RegInsuranceCompany theRegCompany;
	
	/** Поток обслуживания */
	@Comment("Поток обслуживания")
	public VocServiceStream getServiceStream() {return theServiceStream;}
	public void setServiceStream(VocServiceStream aServiceStream) {theServiceStream = aServiceStream;}

	/** Поток обслуживания */
	private VocServiceStream theServiceStream;
}
