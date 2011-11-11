package ru.ecom.mis.ejb.service.medcase;

import java.io.Serializable;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

public class AddressSheetPrintForm implements Serializable {
	/** Фамилия */
	@Comment("Фамилия")
	public String getLastname() {return theLastname;}
	public void setLastname(String aLastname1) {theLastname = aLastname1;}

	/** Имя 1 */
	@Comment("Имя 1")
	public String getFirstname() {return theFirstname;}
	public void setFirstname(String aFirstname1) {theFirstname = aFirstname1;}

	/** Отчество 1 */
	@Comment("Отчество 1")
	public String getMiddlename() {return theMiddlename;}
	public void setMiddlename(String aMiddlename1) {theMiddlename = aMiddlename1;}

	/** Дата рождения 1 */
	@Comment("Дата рождения 1")
	public String getBirthday() {return theBirthday;}
	public void setBirthday(String aBirthday1) {theBirthday = aBirthday1;}
	
	/** Пол 1 */
	@Comment("Пол 1")
	public String getSex() {return theSex;}
	public void setSex(String aSex1) {theSex = aSex1;}
		
	/** Место рождения 1 */
	@Comment("Место рождения 1")
	public String getBirthPlace() {return theBirthPlace;}
	public void setBirthPlace(String aBirthPlace1) {theBirthPlace = aBirthPlace1;}

	/** Национальность 1 */
	@Comment("Национальность 1")
	public String getNationality() {return theNationality;}
	public void setNationality(String aNationality1) {theNationality = aNationality1;}

	
	/** Документ 1 */
	@Comment("Документ 1")
	public String getDocument() {return theDocument;}
	public void setDocument(String aDocument1) {theDocument = aDocument1;}

	/** Дата составления 1 */
	@Comment("Дата составления 1")
	public String getCompilationDate() {return theCompilationDate;}
	public void setCompilationDate(String aCompilationDate1) {theCompilationDate = aCompilationDate1;}

	/** Дата составления 1 */
	private String theCompilationDate;
	/** Документ 1 */
	private String theDocument;
	/** Национальность 1 */
	private String theNationality;
	/** Место рождения 1 */
	private String theBirthPlace;
	/** Пол 1 */
	private String theSex;
	/** Дата рождения 1 */
	private String theBirthday;
	/** Отчество 1 */
	private String theMiddlename;
	/** Имя 1 */
	private String theFirstname;
	/** Фамилия */
	private String theLastname;

	/** Sn */
	@Comment("Sn")
	public String getSn() {return theSn;}
	public void setSn(String aSn1) {theSn = aSn1;}

	/** Sn */
	private String theSn;
	
	/** Дата выписки 1 */
	@Comment("Дата выписки 1")
	public String getDischargeDate() {return theDischargeDate;}
	public void setDischargeDate(String aDateDischarge1) {theDischargeDate = aDateDischarge1;}

	/** Дата выписки 1 */
	private String theDischargeDate;
	
	/** Адрес 1 */
	@Comment("Адрес 1")
	public String getAddress() {return theAddress;}
	public void setAddress(String aAddress1) {theAddress = aAddress1;}

	/** Адрес 1 */
	private String theAddress;
	
	

}
