package ru.ecom.mis.ejb.domain.licence;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Comment("Акт в военкомат")
@Table(schema="SQLUser")
public class RequitDirectionDocument extends InternalDocuments {
	/** Номер направления */
	@Comment("Номер направления")
	public String getOrderNumber() {return theOrderNumber;}
	public void setOrderNumber(String aOrderNumber) {theOrderNumber = aOrderNumber;}
	/** Номер направления */
	private String theOrderNumber;
	
	/** Дата направления */
	@Comment("Дата направления")
	public Date getOrderDate() {return theOrderDate;}
	public void setOrderDate(Date aOrderDate) {theOrderDate = aOrderDate;}
	/** Дата направления */
	private Date theOrderDate;
	
	/** Направитель */
	@Comment("Направитель")
	public String getOrderOffice() {return theOrderOffice;}
	public void setOrderOffice(String aOrderOffice) {theOrderOffice = aOrderOffice;}
	/** Направитель */
	private String theOrderOffice;
	
	/** Данные объективного исследования */
	@Comment("Данные объективного исследования")
	public String getResearch() {return theResearch;}
	public void setResearch(String aResearch) {theResearch = aResearch;}
	/** Данные объективного исследования */
	private String theResearch;
	
	/** Результаты диагностических исследований */
	@Comment("Результаты диагностических исследований")
	public String getLabResearch() {return theLabResearch;}
	public void setLabResearch(String aLabResearch) {theLabResearch = aLabResearch;}
	/** Результаты диагностических исследований */
	private String theLabResearch;
	
	/** Жалобы */
	@Comment("Жалобы")
	public String getAbuses() {return theAbuses;}
	public void setAbuses(String aAbuses) {theAbuses = aAbuses;}
	/** Жалобы */
	private String theAbuses;
}
