package ru.ecom.mis.ejb.domain.worker;

import java.math.BigDecimal;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.ecom.mis.ejb.domain.worker.voc.VocPostBusyType;
import ru.ecom.mis.ejb.domain.worker.voc.VocWorkRecordBase;
import ru.ecom.mis.ejb.domain.worker.voc.VocWorkRecordType;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Запись трудовой книжки
 * @author azviagin
 *
 */
@Comment("Запись трудовой книжки")
@Entity
@Table(schema="SQLUser")
public class WorkBookRecord extends BaseEntity{
	
	/** Трудовая книжка */
	@Comment("Трудовая книжка")
	@ManyToOne
	public WorkBook getWorkBook() {
		return theWorkBook;
	}

	public void setWorkBook(WorkBook aWorkBook) {
		theWorkBook = aWorkBook;
	}

	/** Трудовая книжка */
	private WorkBook theWorkBook;
	
	/** Дата записи */
	@Comment("Дата записи")
	public Date getRecordDate() {
		return theRecordDate;
	}

	public void setRecordDate(Date aRecordDate) {
		theRecordDate = aRecordDate;
	}

	/** Дата записи */
	private Date theRecordDate;
	
	/** Тип записи */
	@Comment("Тип записи")
	@OneToOne
	public VocWorkRecordType getWorkRecordType() {
		return theWorkRecordType;
	}

	public void setWorkRecordType(VocWorkRecordType aWorkRecordType) {
		theWorkRecordType = aWorkRecordType;
	}

	/** Тип записи */
	private VocWorkRecordType theWorkRecordType;
	
	/** Основание записи */
	@Comment("Основание записи")
	@OneToOne
	public VocWorkRecordBase getWorkRecordBase() {
		return theWorkRecordBase;
	}

	public void setWorkRecordBase(VocWorkRecordBase aWorkRecordBase) {
		theWorkRecordBase = aWorkRecordBase;
	}

	/** Основание записи */
	private VocWorkRecordBase theWorkRecordBase;
	
	/** Тип занятия должности */
	@Comment("Тип занятия должности")
	@OneToOne
	public VocPostBusyType getPostBusyType() {
		return thePostBusyType;
	}

	public void setPostBusyType(VocPostBusyType aPostBusyType) {
		thePostBusyType = aPostBusyType;
	}

	/** Тип занятия должности */
	private VocPostBusyType thePostBusyType;
	
	/** Штатная единица */
	@Comment("Штатная единица")
	@ManyToOne
	public StaffUnit getStaffUnit() {
		return theStaffUnit;
	}

	public void setStaffUnit(StaffUnit aStaffUnit) {
		theStaffUnit = aStaffUnit;
	}

	/** Штатная единица */
	private StaffUnit theStaffUnit;
	
	/** Количество штатных единиц */
	@Comment("Количество штатных единиц")
	public BigDecimal getStaffUnitAmount() {
		return theStaffUnitAmount;
	}

	public void setStaffUnitAmount(BigDecimal aStaffUnitAmount) {
		theStaffUnitAmount = aStaffUnitAmount;
	}

	/** Количество штатных единиц */
	private BigDecimal theStaffUnitAmount;
	
	/** ЛПУ */
	@Comment("ЛПУ")
	@Transient
	public MisLpu getLpu() {
		
		if(getStaffUnit()!=null && getStaffUnit().getStaff()!=null) {
			return getStaffUnit().getStaff().getLpu() ;
		} else {
			return null ;
		}
	}

	
	/** Комментарии */
	@Comment("Комментарии")
	public String getComments() {
		return theComments;
	}

	public void setComments(String aComments) {
		theComments = aComments;
	}

	/** Штатная единица (текст) */
	@Comment("Штатная единица (текст)")
	@Transient
	public String getStaffUnitText() {
		return theStaffUnit!=null ? theStaffUnit.getInfo() : "";
	}
	/** Тип записи (text) */
	@Comment("Тип записи (text)")
	@Transient
	public String getWorkRecordTypeText() {
		return theWorkRecordType!=null? theWorkRecordType.getName():"";
	}
	/** Комментарии */
	private String theComments;



}
