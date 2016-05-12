package ru.ecom.mis.ejb.domain.disability;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
public class DisabilityBlanks extends BaseEntity {
	
	/** Дата получения */
	@Comment("Дата получения")
	public Date getReceiptDate() {return theReceiptDate;}
	public void setReceiptDate(Date aReceiptDate) {theReceiptDate = aReceiptDate;}
	
	/** Дата расходования */
	@Comment("Дата расходования")
	public Date getExpenditureDate() {return theExpenditureDate;}
	public void setExpenditureDate(Date aExpenditureDate) {theExpenditureDate = aExpenditureDate;}
	
	
	/** Номер */
	@Comment("Номер")
	public String getNumberFrom() {return theNumberFrom;}
	public void setNumberFrom(String aNumber) {theNumberFrom = aNumber;}
	
	/** По номер */
	@Comment("По номер")
	public String getNumberTo() {return theNumberTo;}
	public void setNumberTo(String aNumberTo) {theNumberTo = aNumberTo;}


	/** Специалист */
	@Comment("Специалист получил")
	@OneToOne
	public WorkFunction getWorkFunction() {return theWorkFunction;}
	public void setWorkFunction(WorkFunction aWorkFunction) {theWorkFunction = aWorkFunction;}

	/** Специалист */
	private WorkFunction theWorkFunction;
	/** По номер */
	private String theNumberTo;
	/** Номер */
	private String theNumberFrom;
	/** Дата расходования */
	private Date theExpenditureDate;
	/** Дата получения */
	private Date theReceiptDate;
	/** Количество */
	@Comment("Количество")
	public Long getBlanksCount() {return theBlanksCount;}
	public void setBlanksCount(Long aBlankCount) {theBlanksCount = aBlankCount;}

	/** Отступ сверху */
	@Comment("Отступ сверху")
	public Long getSpaceBefore() {return theSpaceBefore;}
	public void setSpaceBefore(Long aSpaceBefore) {theSpaceBefore = aSpaceBefore;}

	/** Отступ слева */
	@Comment("Отступ слева")
	public Long getLeftIndent() {return theLeftIndent;}
	public void setLeftIndent(Long aLeftIndent) {theLeftIndent = aLeftIndent;}

	/** Отступ слева */
	private Long theLeftIndent;
	/** Отступ сверху */
	private Long theSpaceBefore;
	/** Количество */
	private Long theBlanksCount;

}
