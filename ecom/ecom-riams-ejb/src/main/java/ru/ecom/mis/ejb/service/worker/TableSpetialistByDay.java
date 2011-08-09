package ru.ecom.mis.ejb.service.worker;

import java.io.Serializable;
import java.sql.Time;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@SuppressWarnings("serial")
public class TableSpetialistByDay implements Serializable{
	/** Время */
	@Comment("Время текст")
	public String getTimeString() {return theTimeString;}
	public void setTimeString(String aTimeString) {theTimeString = aTimeString;}

	/** Время */
	@Comment("Время")
	public Time getTime() {return theTime;}
	public void setTime(Time aTime) {theTime = aTime;}

	/** Порядковый номер */
	@Comment("Порядковый номер")
	public long getSn() {return theSn;}
	public void setSn(long aSn) {theSn = aSn;}

	/** ИД */
	@Comment("ИД")
	public Long getId() {return theId;}
	public void setId(Long aId) {theId = aId;}

	/** ИД */
	private Long theId;
	/** Порядковый номер */
	private long theSn;
	/** Время */
	private Time theTime;
	/** Время текст*/
	private String theTimeString;
}
