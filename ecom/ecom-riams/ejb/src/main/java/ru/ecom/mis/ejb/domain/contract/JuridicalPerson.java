package ru.ecom.mis.ejb.domain.contract;

import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.expomc.ejb.domain.omcvoc.OmcKodTer;
import ru.ecom.expomc.ejb.domain.registry.RegInsuranceCompany;
import ru.ecom.mis.ejb.domain.contract.voc.VocJuridicalPerson;
import ru.ecom.mis.ejb.domain.contract.voc.VocServedPersonStatus;
import ru.ecom.mis.ejb.domain.contract.voc.VocServiceProgram;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.ecom.mis.ejb.domain.patient.voc.VocOrg;
import ru.ecom.mis.ejb.domain.workcalendar.voc.VocServiceStream;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
	/**
	 * Юридическое лицо
	 */
	@Comment("Юридическое лицо")
@Entity
@AIndexes(value = {
		@AIndex(properties = { "name" },table="ContractPerson")
		}
)

public class JuridicalPerson extends ContractPerson{
	/** Электронная почта */
	@Comment("Электронная почта")
	public String getEmail() {
		return theEmail;
	}
	public void setEmail(String aEmail) {
		theEmail = aEmail;
	}
	private String theEmail;

	/** Телефоны */
	@Comment("Телефоны")
	public String getPhones() {
		return thePhones;
	}
	public void setPhones(String aPhones) {
		thePhones = aPhones;
	}
	private String thePhones;

	/** Факс */
	@Comment("Факс")
	public String getFax() {
		return theFax;
	}
	public void setFax(String aFax) {
		theFax = aFax;
	}
	private String theFax;

	/** КПП */
	@Comment("КПП")
	public String getKpp() {
		return theKpp;
	}
	public void setKpp(String aKpp) {
		theKpp = aKpp;
	}
	private String theKpp;

	/** ИНН */
	@Comment("ИНН")
	public String getInn() {
		return theInn;
	}
	public void setInn(String aInn) {
		theInn = aInn;
	}
	private String theInn;

	/** БИК */
	@Comment("БИК")
	public String getBic() {
		return theBic;
	}
	public void setBic(String aBic) {
		theBic = aBic;
	}
	private String theBic;

	/** Короткое название */
	@Comment("Короткое название")
	public String getShortName() {
		return theShortName;
	}
	public void setShortName(String aShortName) {
		theShortName = aShortName;
	}
	private String theShortName;

	/** Полное название */
	@Comment("Полное название")
	public String getName() {
		return theName;
	}
	public void setName(String aName) {
		theName = aName;
	}
	private String theName;

	/** Директор */
	@Comment("Директор")
	public String getDirector() {
		return theDirector;
	}
	public void setDirector(String aDirector) {
		theDirector = aDirector;
	}
	private String theDirector;

	/** Почтовый адрес */
	@Comment("Почтовый адрес")
	public String getPostAddress() {
		return thePostAddress;
	}
	public void setPostAddress(String aPostAddress) {
		thePostAddress = aPostAddress;
	}
	private String thePostAddress;

	/** Счет */
	@Comment("Счет")
	public String getAccount() {
		return theAccount;
	}
	public void setAccount(String aAccount) {
		theAccount = aAccount;
	}
	private String theAccount;

	/** Корреспондентский счет */
	@Comment("Корреспондентский счет")
	public String getCorAccount() {
		return theCorAccount;
	}
	public void setCorAccount(String aCorAccount) {
		theCorAccount = aCorAccount;
	}
	private String theCorAccount;

	/** Юридический адрес */
	@Comment("Юридический адрес")
	public String getJuridicalAddress() {
		return theJuridicalAddress;
	}
	public void setJuridicalAddress(String aJuridicalAddress) {
		theJuridicalAddress = aJuridicalAddress;
	}
	private String theJuridicalAddress;

	/** Тип юридической персоны */
	@Comment("Тип юридической персоны")
	@OneToOne
	public VocJuridicalPerson getJuridicalPersonType() {
		return theJuridicalPersonType;
	}
	public void setJuridicalPersonType(VocJuridicalPerson aJuridicalPersonType) {
		theJuridicalPersonType = aJuridicalPersonType;
	}
	private VocJuridicalPerson theJuridicalPersonType;

	/** Организация */
	@Comment("Организация")
	@OneToOne
	public VocOrg getOrganization() {
		return theOrganization;
	}
	public void setOrganization(VocOrg aOrganization) {
		theOrganization = aOrganization;
	}
	private VocOrg theOrganization;

	/** Статус обслуживаемой персоны */
	@Comment("Статус обслуживаемой персоны")
	@OneToOne
	public VocServedPersonStatus getServedPersonStatus() {
		return theServedPersonStatus;
	}
	public void setServedPersonStatus(VocServedPersonStatus aServedPersonStatus) {
		theServedPersonStatus = aServedPersonStatus;
	}
	private VocServedPersonStatus theServedPersonStatus;

	/** Программа обслуживания */
	@Comment("Программа обслуживания")
	@OneToOne
	public VocServiceProgram getServiceProgram() {
		return theServiceProgram;
	}
	public void setServiceProgram(VocServiceProgram aServiceProgram) {
		theServiceProgram = aServiceProgram;
	}
	private VocServiceProgram theServiceProgram;

	/** Территория */
	@Comment("Территория")
	@OneToOne
	public OmcKodTer getTerritory() {
		return theTerritory;
	}
	public void setTerritory(OmcKodTer aTerritory) {
		theTerritory = aTerritory;
	}
	private OmcKodTer theTerritory;
	
	@Transient
	public String getInformation() {
		return "Юрид. лицо: " + theName;
	}

	/** Страховая компания */
	@Comment("Страховая компания")
	@OneToOne
	public RegInsuranceCompany getRegCompany() {return theRegCompany;}
	public void setRegCompany(RegInsuranceCompany aRegCompany) {theRegCompany = aRegCompany;}
	private RegInsuranceCompany theRegCompany;
	
	/** Поток обслуживания */
	@Comment("Поток обслуживания")
	@OneToOne
	public VocServiceStream getServiceStream() {return theServiceStream;}
	public void setServiceStream(VocServiceStream aServiceStream) {theServiceStream = aServiceStream;}
	private VocServiceStream theServiceStream;

	/** ЛПУ*/
	@Comment("ЛПУ")
	@OneToOne
	public MisLpu getLpu() {
		return theLpu;
	}
	public void setLpu(MisLpu aLpu) {
		theLpu = aLpu;
	}
	private MisLpu theLpu;
}