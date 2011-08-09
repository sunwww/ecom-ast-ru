package ru.ecom.expomc.ejb.uc.findpolicy;

import java.io.Serializable;
import java.util.Date;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.util.StringUtil;

public class PolicyRow implements Serializable {

	public PolicyRow(int aType, long aPolicyId, long aMessageId) {
		theMessageId = aMessageId ;
		theType = aType ;
		thePolicyId =aPolicyId ;
	}
	
	public String getId() {
		return theMessageId+","+theType+","+thePolicyId ;
	}
	
	private final long theMessageId ;
	private final int theType ;
	private final long thePolicyId ;
	
	public String getFio() {
		StringBuilder sb = new StringBuilder() ;
		sb.append(!StringUtil.isNullOrEmpty(getLastname())?getLastname():"") ;
		sb.append(" ") ;
		sb.append(!StringUtil.isNullOrEmpty(getFirstname())?getFirstname():"") ;
		sb.append(" ") ;
		sb.append(!StringUtil.isNullOrEmpty(getMiddlename())?getMiddlename():"") ;
		return sb.toString() ;
	}
	
	public String getPolicySeriesNumber() {
		StringBuilder sb = new StringBuilder() ;
		sb.append(!StringUtil.isNullOrEmpty(getPolicySeries())?getPolicySeries():"") ;
		sb.append(" ") ;
		sb.append(!StringUtil.isNullOrEmpty(getPolicyNumber())?getPolicyNumber():"") ;
		return sb.toString() ;
	}	
	/** Тип поиска */
	@Comment("Тип поиска")
	public String getFindedType() {
		return theFindedType;
	}

	public void setFindedType(String aFindedType) {
		theFindedType = aFindedType;
	}

	/** Год рождения */
	@Comment("Год рождения")
	public int getBirthYear() {
		return theBirthYear;
	}

	public void setBirthYear(int aBirthYear) {
		theBirthYear = aBirthYear;
	}

	/** Год рождения */
	private int theBirthYear;
	/** Тип поиска */
	private String theFindedType;
	/** Страховая компания */
	@Comment("Страховая компания")
	public String getInsuranceCompany() {
		return theInsuranceCompany;
	}

	public void setInsuranceCompany(String aInsuranceCompany) {
		theInsuranceCompany = aInsuranceCompany;
	}

	/** Серия полиса */
	@Comment("Серия полиса")
	public String getPolicySeries() {
		return thePolicySeries;
	}

	public void setPolicySeries(String aPolicySeries) {
		thePolicySeries = aPolicySeries;
	}

	/** Номер полиса */
	@Comment("Номер полиса")
	public String getPolicyNumber() {
		return thePolicyNumber;
	}

	public void setPolicyNumber(String aPolicyNumber) {
		thePolicyNumber = aPolicyNumber;
	}

	/** Фамилия */
	@Comment("Фамилия")
	public String getLastname() {
		return theLastname;
	}

	public void setLastname(String aLastname) {
		theLastname = aLastname;
	}

	/** Имя */
	@Comment("Имя")
	public String getFirstname() {
		return theFirstname;
	}

	public void setFirstname(String aFirstname) {
		theFirstname = aFirstname;
	}

	/** Отчество */
	@Comment("Отчество")
	public String getMiddlename() {
		return theMiddlename;
	}

	/** Дата рождения */
	@Comment("Дата рождения")
	public Date getBirthDate() {
		return theBirthDate;
	}

	public void setBirthDate(Date aBirthDate) {
		theBirthDate = aBirthDate;
	}

	
	/** Дата рождения */
	private Date theBirthDate;
	public void setMiddlename(String aMiddlename) {
		theMiddlename = aMiddlename;
	}

	/** СНИЛС */
	@Comment("СНИЛС")
	public String getSnils() {
		return theSnils;
	}

	public void setSnils(String aSnils) {
		theSnils = aSnils;
	}

	/** СНИЛС */
	private String theSnils;
	/** Отчество */
	private String theMiddlename;
	/** Имя */
	private String theFirstname;
	/** Фамилия */
	private String theLastname;
	/** Номер полиса */
	private String thePolicyNumber;
	
	/** Серия полиса */
	private String thePolicySeries;
	
	/** Страховая компания */
	private String theInsuranceCompany;
}
