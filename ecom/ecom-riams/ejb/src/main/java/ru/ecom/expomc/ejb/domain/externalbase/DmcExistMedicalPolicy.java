package ru.ecom.expomc.ejb.domain.externalbase;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Comment("Действующие медицинские полисы ДМС")
@Entity
@Table(schema="SQLUser")
public class DmcExistMedicalPolicy extends BaseEntity {
	/** Фамилия */
	@Comment("Фамилия")
	public String getLastname() {return theLastname;}
	public void setLastname(String aLastname) {theLastname = aLastname;}
	
	/** Имя */
	@Comment("Имя")
	public String getFirstname() {return theFirstname;}
	public void setFirstname(String aFirstname) {theFirstname = aFirstname;}
	
	/** Код страховой компании */
	@Comment("Код страховой компании")
	public int getInsuranceCode() {return theInsuranceCode;}
	public void setInsuranceCode(int aCodeInsuranceCompany) {theInsuranceCode = aCodeInsuranceCompany;}
	
	/** Дата начал актуальности */
	@Comment("Дата начала актуальности")
	public Date getStartActualDate() {return theStartActualDate;}
	public void setStartActualDate(Date aStartActualDate) {theStartActualDate = aStartActualDate;}
	
	/** Дата окончания актуальности */
	@Comment("Дата окончания актуальности")
	public Date getEndActualDate() {return theEndActualDate;}
	public void setEndActualDate(Date aEndActualDate) {theEndActualDate = aEndActualDate;}
	
	/** Отчетство */
	@Comment("Отчетство")
	public String getMiddlename() {return theMiddlename;}
	public void setMiddlename(String aMiddlename) {theMiddlename = aMiddlename;}

	/** СНИЛС */
	@Comment("СНИЛС")
	public String getSnils() {return theSnils;}
	public void setSnils(String aSnils) {theSnils = aSnils;}

	/** Серия */
	@Comment("Серия")
	public String getSeries() {return theSeries;}
	public void setSeries(String aSeries) {theSeries = aSeries;}

	/** Номер */
	@Comment("Номер")
	public String getNumber() {return theNumber;}
	public void setNumber(String aNumber) {theNumber = aNumber;}

	/** Дата рождения */
	@Comment("Дата рождения")
	public Date getBirthday() {return theBirthday;}
	public void setBirthday(Date aNAME) {theBirthday = aNAME;}

	/** Дата рождения */
	private Date theBirthday;
	/** Номер */
	private String theNumber;
	/** Серия */
	private String theSeries;
	/** СНИЛС */
	private String theSnils;
	/** Отчетство */
	private String theMiddlename;
	/** Дата окончания актуальности */
	private Date theEndActualDate;
	/** Дата начала актуальности */
	private Date theStartActualDate;
	/** Код страховой компании */
	private int theInsuranceCode;
	/** Имя */
	private String theFirstname;
	/** Фамилия */
	private String theLastname;
}
