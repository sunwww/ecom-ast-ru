package ru.ecom.mis.ejb.domain.contract;

import ru.ecom.expomc.ejb.domain.omcvoc.OmcKodTer;
import ru.ecom.mis.ejb.domain.contract.voc.VocServedPersonStatus;
import ru.ecom.mis.ejb.domain.contract.voc.VocServiceProgram;
import ru.ecom.mis.ejb.domain.patient.MedPolicy;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.sql.Date;
	/**
	 * Медицинский полис по договору
	 */
	@Comment("Медицинский полис по договору")
@Entity
public class ContractMedPolicy extends ContractGuarantee {
	/** Медицинский полис */
	@Comment("Медицинский полис")
	@OneToOne
	public MedPolicy getMedPolicy() {
		return theMedPolicy;
	}
	public void setMedPolicy(MedPolicy aMedPolicy) {
		theMedPolicy = aMedPolicy;
	}
	private MedPolicy theMedPolicy;

	/** Фамилия */
	@Comment("Фамилия")
	public String getLastname() {
		return theLastname;
	}
	public void setLastname(String aLastname) {
		theLastname = aLastname;
	}
	private String theLastname;

	/** Имя */
	@Comment("Имя")
	public String getFirstname() {
		return theFirstname;
	}
	public void setFirstname(String aFirstname) {
		theFirstname = aFirstname;
	}
	private String theFirstname;

	/** Отчество */
	@Comment("Отчество")
	public String getMiddlename() {
		return theMiddlename;
	}
	public void setMiddlename(String aMiddlename) {
		theMiddlename = aMiddlename;
	}
	private String theMiddlename;

	/** День рождения */
	@Comment("День рождения")
	public Date getBirthday() {
		return theBirthday;
	}
	public void setBirthday(Date aBirthday) {
		theBirthday = aBirthday;
	}
	private Date theBirthday;

	/** Серия */
	@Comment("Серия")
	public String getSeries() {
		return theSeries;
	}
	public void setSeries(String aSeries) {
		theSeries = aSeries;
	}
	private String theSeries;

	/** Номер */
	@Comment("Номер")
	public String getNumber() {
		return theNumber;
	}
	public void setNumber(String aNumber) {
		theNumber = aNumber;
	}
	private String theNumber;

	/** Дата начала действия */
	@Comment("Дата начала действия")
	public Date getDateFrom() {
		return theDateFrom;
	}
	public void setDateFrom(Date aDateFrom) {
		theDateFrom = aDateFrom;
	}
	private Date theDateFrom;

	/** Дата окончания действия */
	@Comment("Дата окончания действия")
	public Date getDateTo() {
		return theDateTo;
	}
	public void setDateTo(Date aDateTo) {
		theDateTo = aDateTo;
	}
	private Date theDateTo;

	/** Дата объявления недействительности */
	@Comment("Дата объявления недействительности")
	public Date getNullityDate() {
		return theNullityDate;
	}
	public void setNullityDate(Date aNullityDate) {
		theNullityDate = aNullityDate;
	}
	private Date theNullityDate;

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
}