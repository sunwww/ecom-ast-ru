package ru.ecom.expomc.ejb.uc.snils;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.form.simple.AFormatFieldSuggest;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@AIndexes ({
	@AIndex(properties={"policyNumber","policySeries","insuranceCompany"
			,"lastname1", "firstname1", "middlename1","birthYear"})
	,@AIndex(properties={"insuranceCompany","policySeries","policyNumber"})		
	,@AIndex(properties={"lastname1","firstname1","middlename1","birthYear"})		
	,@AIndex(properties={"lastname1","firstname1","middlename1"})		
	,@AIndex(properties={"snils"})		
			
})
@NamedQueries({
	@NamedQuery( name="OmcSnils.findSnilsByPolicyAndPerson"
		, query="from OmcSnils where policyNumber=:policyNumber"
			+" and policySeries=:policySeries and insuranceCompany=:insuranceCompany"
			+" and lastname1=:lastname1 and firstname1=:firstname1"
			+" and middlename1=:middlename1 and birthYear=:birthYear")
,	@NamedQuery( name="OmcSnils.findSnilsByPolicy"
	, query="from OmcSnils where policyNumber=:policyNumber"
		+" and policySeries=:policySeries and insuranceCompany=:insuranceCompany"
)

,	@NamedQuery( name="OmcSnils.findSnilsByPatient"
	, query="from OmcSnils where "
		+" lastname1=:lastname1 and firstname1=:firstname1"
		+" and middlename1=:middlename1 and birthYear=:birthYear")

,	@NamedQuery( name="OmcSnils.findSnilsByPatientWithoutYear"
			, query="from OmcSnils where "
				+" lastname1=:lastname1 and firstname1=:firstname1"
				+" and middlename1=:middlename1")

})
@Table(schema="SQLUser")
@Comment("Данные СНИЛС из фонда ОМС")
public class OmcSnils extends BaseEntity {
	/** СНИЛС */
	@AFormatFieldSuggest("SS")
	@Comment("СНИЛС")
	public String getSnils() {
		return theSnils;
	}

	public void setSnils(String aSnils) {
		theSnils = aSnils;
	}
	
	/** Номер полиса */
	@Comment("Номер полиса")
	@AFormatFieldSuggest("N_POLIS")
	public String getPolicyNumber() {
		return thePolicyNumber;
	}

	public void setPolicyNumber(String aPolicyNumber) {
		thePolicyNumber = aPolicyNumber;
	}

	/** Серия полиса */
	@AFormatFieldSuggest("S_POLIS")
	@Comment("Серия полиса")
	public String getPolicySeries() {
		return thePolicySeries;
	}

	public void setPolicySeries(String aPolicySeries) {
		thePolicySeries = aPolicySeries;
	}

	/** Страховая компания */
	@AFormatFieldSuggest("SK")
	@Comment("Страховая компания")
	public String getInsuranceCompany() {
		return theInsuranceCompany;
	}

	public void setInsuranceCompany(String aInsuranceCompany) {
		theInsuranceCompany = aInsuranceCompany;
	}

	/** Первая буква фамилии */
	@Comment("Первая буква фамилии")
	@AFormatFieldSuggest("F1")
	public String getLastname1() {
		return theLastname1;
	}

	public void setLastname1(String aLastname1) {
		theLastname1 = aLastname1;
	}

	/** Первая буква имени */
	@Comment("Первая буква имени")
	@AFormatFieldSuggest("I1")
	public String getFirstname1() {
		return theFirstname1;
	}

	public void setFirstname1(String aFirstname1) {
		theFirstname1 = aFirstname1;
	}

	/** Первая буква отчества */
	@Comment("Первая буква отчества")
	@AFormatFieldSuggest("O1")
	public String getMiddlename1() {
		return theMiddlename1;
	}

	public void setMiddlename1(String aMiddlename1) {
		theMiddlename1 = aMiddlename1;
	}

	/** Год рождения */
	@Comment("Год рождения")
	@AFormatFieldSuggest("Y1")
	public int getBirthYear() {
		return theBirthYear;
	}

	public void setBirthYear(int aBirthYear) {
		theBirthYear = aBirthYear;
	}

	/** Пол */
	@Comment("Пол")
	@AFormatFieldSuggest("W")
	public String getSex() {
		return theSex;
	}

	public void setSex(String aSex) {
		theSex = aSex;
	}

	/** признак наличия в региональном сегменте федерального регистра лиц имеющих право на ГСП */
	@Comment("признак наличия в региональном сегменте федерального регистра лиц имеющих право на ГСП")
	@AFormatFieldSuggest("FL")
	public String getFl() {
		return theFl;
	}

	public void setFl(String aFl) {
		theFl = aFl;
	}

	/** признак наличия в регистре неработающих пенсионеров */
	@Comment("признак наличия в регистре неработающих пенсионеров")
	@AFormatFieldSuggest("NP")
	public String getNp() {
		return theNp;
	}

	public void setNp(String aNp) {
		theNp = aNp;
	}
	
	/** Имя */
	@Comment("Имя")
	public String getFirstname() {
		return theFirstname;
	}

	public void setFirstname(String aFirstname) {
		theFirstname = aFirstname;
	}

	/** Имя */
	private String theFirstname;
	
	/** Фамилия */
	@Comment("Фамилия")
	public String getLastname() {
		return theLastname;
	}

	public void setLastname(String aLastname) {
		theLastname = aLastname;
	}

	/** Фамилия */
	private String theLastname;
	
	/** Отчество */
	@Comment("Отчество")
	public String getMiddlename() {
		return theMiddlename;
	}

	public void setMiddlename(String aMiddlename) {
		theMiddlename = aMiddlename;
	}

	/** Отчество */
	private String theMiddlename;

	/** Пол */
	private String theSex;
	/** признак наличия в регистре неработающих пенсионеров */
	private String theNp;
	/** признак наличия в региональном сегменте федерального регистра лиц имеющих право на ГСП */
	private String theFl;
	/** Год рождения */
	private int theBirthYear;
	/** Первая буква отчества */
	private String theMiddlename1;
	/** Первая буква имени */
	private String theFirstname1;
	/** Первая буква фамилии */
	private String theLastname1;
	/** Страховая компания */
	private String theInsuranceCompany;
	/** Серия полиса */
	private String thePolicySeries;
	/** Номер полиса */
	private String thePolicyNumber;

	/** СНИЛС */
	private String theSnils;
}
