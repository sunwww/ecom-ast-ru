package ru.ecom.jaas.ejb.domain;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
/**
 * Временный класс для импорта пользователей
 * @author stkacheva
 *
 */
@Entity
@Table(schema="SQLUser")
public class ImportUser extends VocBaseEntity{
	/** Фамилия */
	@Comment("Фамилия")
	public String getLastname() {return theLastname;}
	public void setLastname(String aLastname) {theLastname = aLastname;}

	/** Имя */
	@Comment("Имя")
	public String getFirstname() {return theFirstname;}
	public void setFirstname(String aFirstname) {theFirstname = aFirstname;}

	/** Отчество */
	@Comment("Отчество")
	public String getMiddlename() {return theMiddlename;}
	public void setMiddlename(String aMiddlename) {theMiddlename = aMiddlename;}

	/** Дата рождения */
	@Comment("Дата рождения")
	public Date getBirthday() {return theBirthday;}
	public void setBirthday(Date aBirthday) {theBirthday = aBirthday;}

	/** СНИЛС */
	@Comment("СНИЛС")
	public String getSnils() {return theSnils;}
	public void setSnils(String aSnils) {theSnils = aSnils;}

	/** Рабочая функция */
	@Comment("Рабочая функция")
	public String getWorkpost() {return theWorkpost;}
	public void setWorkpost(String aWorkpost) {theWorkpost = aWorkpost;}

	/** ЛПУ  */
	@Comment("ЛПУ ")
	public String getLpu() {return theLpu;}
	public void setLpu(String aLpu) {theLpu = aLpu;	}

	/** Пол */
	@Comment("Пол")
	public String getSex() {return theSex;}
	public void setSex(String aSex) {theSex = aSex;}

	/** Подразделение */
	@Comment("Подразделение")
	public String getDepartment() {return theDepartment;}
	public void setDepartment(String aDepartment) {theDepartment = aDepartment;}

	/** Подразделение */
	private String theDepartment;
	/** Пол */
	private String theSex;
	/** ЛПУ */
	private String theLpu;
	/** Рабочая функция */
	private String theWorkpost;
	/** СНИЛС */
	private String theSnils;
	/** Дата рождения */
	private Date theBirthday;
	/** Отчество */
	private String theMiddlename;
	/** Имя */
	private String theFirstname;
	/** Фамилия */
	private String theLastname;

}
