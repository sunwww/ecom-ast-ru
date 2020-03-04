package ru.ecom.mis.ejb.domain.patient;
/**
 * Сервис работы с прикреплением
 * 
 * @author VTsybulin 28.11.2014
 */

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.expomc.ejb.domain.impdoc.IImportData;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Date;


@Entity
@Table(schema="SQLUser")
public class LpuAttachmentFomc extends BaseEntity implements IImportData {

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
	
	/** Серия документа */
	@Comment("Серия документа")
	public String getDocSeries() {
		return theDocSeries;
	}

	public void setDocSeries(String aDocSeries) {
		theDocSeries = aDocSeries;
	}

	/** Серия документа */
	private String theDocSeries;
	
	/** Номер документа */
	@Comment("Номер документа")
	public String getDocNum() {
		return theDocNum;
	}

	public void setDocNum(String aDocNum) {
		theDocNum = aDocNum;
	}

	/** Номер документа */
	private String theDocNum;
	
	/** Тип документа */
	@Comment("Тип документа")
	public String getDocType() {
		return theDocType;
	}

	public void setDocType(String aDocType) {
		theDocType = aDocType;
	}

	/** Тип документа */
	private String theDocType;
	

	
	/** Способ прикрепления (по терр. признаку/по заявлению) */
	@Comment("Способ прикрепления (по терр. признаку/по заявлению)")
	public String getMethodType() {
		return theMethodType;
	}

	public void setMethodType(String aMethodType) {
		theMethodType = aMethodType;
	}

	/** Способ прикрепления (по терр. признаку/по заявлению) */
	private String theMethodType;
		
	/** Дата прикрепления */
	@Comment("Дата прикрепления")
	public Date getAttachDate() {
		return theAttachDate;
	}

	public void setAttachDate(Date aAttachDate) {
		theAttachDate = aAttachDate;
	}

	/** Дата прикрепления */
	private Date theAttachDate;
	
	/** ЛПУ прикрепления */
	@Comment("ЛПУ прикрепления")
	public String getAttachLpu() {
		return theAttachLpu;
	}

	public void setAttachLpu(String aAttachLpu) {
		theAttachLpu = aAttachLpu;
	}

	/** ЛПУ прикрепления */
	private String theAttachLpu;
	
	/** Номер ЕПН */
	@Comment("Номер ЕПН")
	public String getCommonNumber() {
		return theCommonNumber;
	}

	public void setCommonNumber(String aCommonNumber) {
		theCommonNumber = aCommonNumber;
	}

	/** Номер ЕПН */
	private String theCommonNumber;
	
	/** Дата рождения */
	@Comment("Дата рождения")
	public Date getBirthday() {
		return theBirthday;
	}

	public void setBirthday(Date aBirthday) {
		theBirthday = aBirthday;
	}

	/** Дата рождения */
	private Date theBirthday;
	
	/** Пол */
	@Comment("Пол")
	public String getSex() {
		return theSex;
	}

	public void setSex(String aSex) {
		theSex = aSex;
	}

	/** Пол */
	private String theSex;
	
	
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

public long getTime() {
	return theTime;
}

public void setTime(long aTime) {
	theTime = aTime;
	
}
private long theTime ;
}
