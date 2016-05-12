package ru.ecom.poly.ejb.services;

import java.io.Serializable;
import java.sql.Date;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

public class GroupByDate implements Serializable {
	/** Дата */
	@Comment("Дата")
	public Date getDate() {return theDate;}
	public void setDate(Date aDate) {theDate = aDate;}

	/** Кол-во */
	@Comment("Кол-во")
	public Long getCnt() {return theCnt;}
	public void setCnt(Long aCnt) {theCnt = aCnt;}

	/** Дата инфо */
	@Comment("Дата инфо")
	public String getDateInfo() {return theDateInfo;}
	public void setDateInfo(String aDateInfo) {theDateInfo = aDateInfo;}

	/** №№ */
	@Comment("№№")
	public long getSn() {return theSn;}
	public void setSn(long aSn) {theSn = aSn;}

	/** Комментарий */
	@Comment("Комментарий")
	public String getComment() {return theComment;}
	public void setComment(String aComment) {theComment = aComment;}

	/** Кол-во 1 */
	@Comment("Кол-во 1")
	public Long getCnt1() {return theCnt1;}
	public void setCnt1(Long aCnt1) {theCnt1 = aCnt1;}

	/** Кол-во 2 */
	@Comment("Кол-во 2")
	public Long getCnt2() {return theCnt2;}
	public void setCnt2(Long aCnt2) {theCnt2 = aCnt2;}

	/** Кол-во койко дней */
	@Comment("Кол-во койко дней")
	public Long getCntDays() {
		return theCntDays;
	}

	public void setCntDays(Long aCntDays) {
		theCntDays = aCntDays;
	}

	/** Кол-во койко дней */
	private Long theCntDays;
	/** Кол-во 2 */
	private Long theCnt2;
	/** Кол-во 1 */
	private Long theCnt1;
	/** Комментарий */
	private String theComment;
	/** №№ */
	private long theSn;
	/** Дата инфо */
	private String theDateInfo;
	/** Кол-во */
	private Long theCnt;
	/** Дата */
	private Date theDate;

}
