package ru.ecom.mis.ejb.service.worker;

import java.io.Serializable;
import java.sql.Date;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@SuppressWarnings("serial")
public class TableTimeBySpecialists implements Serializable {
	/** Порядковый номер */
	@Comment("Порядковый номер")
	public long getSn() {return theSn;}
	public void setSn(long aSn) {theSn = aSn;}

	/** Специалист (WorkFunction) */
	//@Comment("Специалист (WorkFunction)")
	//public String getSpecialist() {return theSpecialist;}
	//public void setSpecialist(String aSpecialist) {theSpecialist = aSpecialist;}

	/** ИД специалиста */
	@Comment("ИД специалиста")
	public Long getSpecialistId() {return theSpecialistId;}
	public void setSpecialistId(Long aSpecialistId) {theSpecialistId = aSpecialistId;	}

	/** Дата */
	@Comment("Дата")
	public String getDateString() {return theDateString;}
	public void setDateString(String aDate) {theDateString = aDate;}

	/** Время минимальное */
	@Comment("Время минимальное")
	public String getTimeMin() {return theTimeMin;}
	public void setTimeMin(String aTimeMin) {theTimeMin = aTimeMin;}

	/** Время максимальное */
	@Comment("Время максимальное")
	public String getTimeMax() {return theTimeMax;}
	public void setTimeMax(String aTimeMax) {theTimeMax = aTimeMax;}

	/** CalendarDayId */
	@Comment("CalendarDayId")
	public Long getCalendarDayId() {return theCalendarDayId;}
	public void setCalendarDayId(Long aCalendarDayId) {theCalendarDayId = aCalendarDayId;}

	
	/** Дата */
	@Comment("Дата")
	public Date getDate() {return theDate;}
	public void setDate(Date aDate) {theDate = aDate;}

	/** Дата */
	private Date theDate;
	/** CalendarDayId */
	private Long theCalendarDayId;
	/** Время максимальное */
	private String theTimeMax;
	/** Время минимальное */
	private String theTimeMin;
	/** Дата */
	private String theDateString;
	/** ИД специалиста */
	private Long theSpecialistId;
	/** Специалист (WorkFunction) */
	//private String theSpecialist;
	/** Порядковый номер */
	private long theSn;

}
