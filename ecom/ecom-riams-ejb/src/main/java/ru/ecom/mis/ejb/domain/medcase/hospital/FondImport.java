package ru.ecom.mis.ejb.domain.medcase.hospital;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Comment("Госпитализации данные фонда таблица импорта")
@Entity
@Table(schema="SQLUser")
public class FondImport extends BaseEntity{
	/** Дата импорта */
	@Comment("Дата импорта")
	public Date getImportDate() {return theImportDate;}
	public void setImportDate(Date aImportDate) {theImportDate = aImportDate;}

	/** Дата импорта */
	private Date theImportDate;
	
	/** Время импорта */
	@Comment("Время импорта")
	public Time getImportTime() {return theImportTime;}
	public void setImportTime(Time aImportTime) {theImportTime = aImportTime;}

	/** Пользователь */
	@Comment("Пользователь")
	public String getUsername() {return theUsername;}
	public void setUsername(String aUsername) {theUsername = aUsername;}

	/** Таблица */
	@Comment("Таблица")
	public String getImportTable() {return theImportTable;}
	public void setImportTable(String aImportTable) {theImportTable = aImportTable;}

	/** Таблица */
	private String theImportTable;
	/** Пользователь */
	private String theUsername;
	/** Время импорта */
	private Time theImportTime;
	
	/** Имя файла */
	@Comment("Имя файла")
	public String getFilename() {return theFilename;}
	public void setFilename(String aFilename) {theFilename = aFilename;}

	/** Имя файла */
	private String theFilename;
	
	/** Кол-во импортируемых данных */
	@Comment("Кол-во импортируемых данных")
	public Long getCntImport() {return theCntImport;}
	public void setCntImport(Long aCntImport) {theCntImport = aCntImport;}

	/** Кол-во импортируемых данных */
	private Long theCntImport;
	
	/** Кол-во дефектных данных */
	@Comment("Кол-во дефектных данных")
	public Long getCntDefect() {return theCntDefect;}
	public void setCntDefect(Long aCntDefect) {theCntDefect = aCntDefect;}

	/** Кол-во дефектных данных */
	private Long theCntDefect;
	
	/** Номер импорта */
	@Comment("Номер импорта")
	public String getImportNumber() {return theImportNumber;}
	public void setImportNumber(String aImportNumber) {theImportNumber = aImportNumber;}
	/** Номер импорта */
	private String theImportNumber;
}