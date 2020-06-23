package ru.ecom.mis.ejb.domain.licence;

import ru.ecom.ejb.services.util.ColumnConstants;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.sql.Date;

@Entity
@Comment("Акт в военкомат")
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
	@Column(length=ColumnConstants.TEXT_MAXLENGHT)
	public String getResearch() {return theResearch;}
	public void setResearch(String aResearch) {theResearch = aResearch;}
	/** Данные объективного исследования */
	private String theResearch;
	
	/** Результаты диагностических исследований */
	@Comment("Результаты диагностических исследований")
	@Column(length=ColumnConstants.TEXT_MAXLENGHT)
	public String getLabResearch() {return theLabResearch;}
	public void setLabResearch(String aLabResearch) {theLabResearch = aLabResearch;}
	/** Результаты диагностических исследований */
	private String theLabResearch;
	
	/** Жалобы */
	@Comment("Жалобы")
	@Column(length=ColumnConstants.TEXT_MAXLENGHT)
	public String getAbuses() {return theAbuses;}
	public void setAbuses(String aAbuses) {theAbuses = aAbuses;}
	/** Жалобы */
	private String theAbuses;
}
