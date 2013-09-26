package ru.ecom.expomc.ejb.domain.externalbase;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.NoLiveBaseEntity;
import ru.ecom.ejb.form.simple.AFormatFieldSuggest;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.expomc.ejb.domain.impdoc.IImportData;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@AIndexes({
	@AIndex(properties={"lastname"})
	,@AIndex(properties={"firstname"})
	,@AIndex(properties={"middlename"})
	,@AIndex(properties={"birthdate"})		
	,@AIndex(properties={"insuranceCompany"})
	,@AIndex(properties={"policySeries"})
	,@AIndex(properties={"policyNumber"})		
})
@NamedQueries({
@NamedQuery(name="externalPersonInfo.findByFiod"
	, query="from ExternalPersonInfo where lastname=:lastname and firstname=:firstname and middlename=:middlename and birthdate=:birthdate")

	, @NamedQuery(name="externalPersonInfo.findByFio"
	, query="from ExternalPersonInfo where lastname=:lastname and firstname=:firstname and middlename=:middlename")

	,@NamedQuery(name="externalPersonInfo.findByPolicy"
	, query="from ExternalPersonInfo where insuranceCompany=:company and policySeries=:series and policyNumber=:number")		
	
})
@Table(schema="SQLUser")
public class ExternalPersonInfo extends NoLiveBaseEntity implements IImportData {

    /**
     * Импорт
     */
    public long getTime() {
        return theTime;
    }

    public void setTime(long aTime) {
        theTime = aTime;
    }

    long theTime ;
	/** Страховая сомпания */
	@Comment("Страховая компания")
	@AFormatFieldSuggest("SK")
	public String getInsuranceCompany() { return theInsuranceCompany ; }
	public void setInsuranceCompany(String aInsuranceCompany) { theInsuranceCompany = aInsuranceCompany ; }
	
	/** Страховая сомпания */
	private String theInsuranceCompany ;

	/** Серия полиса */
	@Comment("Серия полиса")
	@AFormatFieldSuggest("S_POLIS")
	public String getPolicySeries() {
		return thePolicySeries;
	}

	public void setPolicySeries(String aPolicySeries) {
		thePolicySeries = aPolicySeries;
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

	/** Дата действия полиса с */
	@Comment("Дата действия полиса с")
	@AFormatFieldSuggest("DATAP")
	public Date getPolicyDateFrom() {
		return thePolicyDateFrom;
	}

	public void setPolicyDateFrom(Date aPolicyDateFrom) {
		thePolicyDateFrom = aPolicyDateFrom;
	}

	/** Дата действия полиса по */
	@Comment("Дата действия полиса по")
	@AFormatFieldSuggest("DATAPE")
	public Date getPolicyDateTo() {
		return thePolicyDateTo;
	}

	public void setPolicyDateTo(Date aPolicyDateTo) {
		thePolicyDateTo = aPolicyDateTo;
	}

	/** Фамилия */
	@Comment("Фамилия")
	@AFormatFieldSuggest("F")
	public String getLastname() {
		return theLastname;
	}

	
	public void setLastname(String aLastname) {
		theLastname = aLastname;
	}

	/** Имя */
	@Comment("Имя")
	@AFormatFieldSuggest("I")
	public String getFirstname() {
		return theFirstname;
	}

	public void setFirstname(String aFirstname) {
		theFirstname = aFirstname;
	}

	/** Отчество */
	@Comment("Отчество")
	@AFormatFieldSuggest("O")
	public String getMiddlename() {
		return theMiddlename;
	}

	public void setMiddlename(String aMiddlename) {
		theMiddlename = aMiddlename;
	}

	/** Дата рождения */
	@Comment("Дата рождения")
	@AFormatFieldSuggest("DR")
	public Date getBirthdate() {
		return theBirthdate;
	}

	public void setBirthdate(Date aBirthdate) {
		theBirthdate = aBirthdate;
	}

	/** Номер предприятия (старый) */
	@Comment("Номер предприятия (старый)")
	@AFormatFieldSuggest("RNUMBER")
	public String getOrgCodeOld() {
		return theOrgCodeOld;
	}

	public void setOrgCodeOld(String aOrgCodeOld) {
		theOrgCodeOld = aOrgCodeOld;
	}

	/** Номер предприятия (новый) */
	@Comment("Номер предприятия (новый)")
	@AFormatFieldSuggest("RNUMBER15")
	public String getOrgCodeNew() {
		return theOrgCodeNew;
	}

	public void setOrgCodeNew(String aOrgCodeNew) {
		theOrgCodeNew = aOrgCodeNew;
	}

	
	/** Социальна группа */
	@Comment("Социальна группа")
	@AFormatFieldSuggest("SGROUP")
	public String getSocialGroup() {
		return theSocialGroup;
	}

	/** Прикрепленное ЛПУ */
	@Comment("Прикрепленное ЛПУ")
	@AFormatFieldSuggest("LPU")
	public String getBindingLpu() {
		return theBindingLpu;
	}

	public void setBindingLpu(String aBindingLpu) {
		theBindingLpu = aBindingLpu;
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
	/** Прикрепленное ЛПУ */
	private String theBindingLpu;
	public void setSocialGroup(String aSocialGroup) {
		theSocialGroup = aSocialGroup;
	}

	/** Социальна группа */
	private String theSocialGroup;
	/** Тип документа, удостоверяющего личность */
	@Comment("Тип документа, удостоверяющего личность")
	@AFormatFieldSuggest("DOC_T")
	public String getDocType() {
		return theDocType;
	}

	public void setDocType(String aDocType) {
		theDocType = aDocType;
	}

	/** Номер документа, у.л. */
	@Comment("Номер документа, у.л.")
	@AFormatFieldSuggest("DOC_N")
	public String getDocNumber() {
		return theDocNumber;
	}

	public void setDocNumber(String aDocNumber) {
		theDocNumber = aDocNumber;
	}

	/** Серия документа, у.л. */
	@Comment("Серия документа, у.л.")
	@AFormatFieldSuggest("DOC_S")
	public String getDocSeries() {
		return theDocSeries;
	}

	public void setDocSeries(String aDocSeries) {
		theDocSeries = aDocSeries;
	}

	/** Дата выдачи */
	@Comment("Дата выдачи")
	@AFormatFieldSuggest("DOC_D")
	public Date getDocDateIssue() {
		return theDocDateIssue;
	}

	public void setDocDateIssue(Date aDocDateIssue) {
		theDocDateIssue = aDocDateIssue;
	}

	/** Пол */
	@Comment("Пол")
	@AFormatFieldSuggest("Пол")
	public String getSex() {
		return theSex;
	}

	public void setSex(String aSex) {
		theSex = aSex;
	}

	/** КЛАДР */
	@Comment("КЛАДР")
	@AFormatFieldSuggest("STREET_GNI")
	public String getKladr() {
		return theKladr;
	}

	public void setKladr(String aKladr) {
		theKladr = aKladr;
	}

	/** Номер дома */
	@Comment("Номер дома")
	@AFormatFieldSuggest("HOUSE")
	public String getHouseNumber() {
		return theHouseNumber;
	}

	public void setHouseNumber(String aHouseNumber) {
		theHouseNumber = aHouseNumber;
	}

	/** Корпус */
	@Comment("Корпус")
	@AFormatFieldSuggest("SECTION")
	public String getHouseBuilding() {
		return theHouseBuilding;
	}

	public void setHouseBuilding(String aHouseBuilding) {
		theHouseBuilding = aHouseBuilding;
	}

	/** Номер квартиры */
	@Comment("Номер квартиры")
	public String getFlatNumber() {
		return theFlatNumber;
	}

	public void setFlatNumber(String aFlatNumber) {
		theFlatNumber = aFlatNumber;
	}
	
	/** Кто выдал */
	@Comment("Кто выдал")
	public String getDocWhomIssued() {
		return theDocWhomIssued;
	}

	public void setDocWhomIssued(String aDocWhomIssued) {
		theDocWhomIssued = aDocWhomIssued;
	}

	/** Район астр. проживания */
	@Comment("Район астр. проживания")
	public String getRayon() {
		return theRayon;
	}

	public void setRayon(String aRayon) {
		theRayon = aRayon;
	}

	/** Район астр. проживания */
	private String theRayon;
	
	/** Кто выдал */
	private String theDocWhomIssued;
	

	/** Номер квартиры */
	private String theFlatNumber;
	/** Корпус */
	private String theHouseBuilding;
	
	/** Номер дома */
	private String theHouseNumber;
	/** КЛАДР */
	private String theKladr;
	/** Пол */
	private String theSex;
	/** Дата выдачи */
	private Date theDocDateIssue;
	/** Серия документа, у.л. */
	private String theDocSeries;
	/** Номер документа, у.л. */
	private String theDocNumber;
	/** Тип документа, удостоверяющего личность */
	private String theDocType;
	
	/** Номер предприятия (новый) */
	private String theOrgCodeNew;
	
	/** Номер предприятия (старый) */
	private String theOrgCodeOld;
	/** Дата рождения */
	private Date theBirthdate;
	/** Отчество */
	private String theMiddlename;
	/** Имя */
	private String theFirstname;
	/** Фамилия */
	private String theLastname;
	/** Дата действия полиса по */
	private Date thePolicyDateTo;
	
	/** Дата действия полиса с */
	private Date thePolicyDateFrom;
	
	/** Номер полиса */
	private String thePolicyNumber;
	/** Серия полиса */
	private String thePolicySeries;
	
	
}
