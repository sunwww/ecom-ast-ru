package ru.ecom.mis.ejb.domain.birth;

/**
 * День отчета по рождаемости
 */

import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Comment("День отчета по рождаемости")
@Entity
@Table(schema="SQLUser")
public class BirthReportDate extends BaseEntity{
	
	/** Редакция завершена */
	@Comment("Редакция завершена")
	public Boolean getEditComplete() {
		return theEditComplete;
	}

	public void setEditComplete(Boolean aEditComplete) {
		theEditComplete = aEditComplete;
	}

	/** Редакция завершена */
	private Boolean theEditComplete;
	
	/** Строки отчета */
	@Comment("Строки отчета")
	@OneToMany(mappedBy="birthReportDate", cascade=CascadeType.ALL)
	public List<BirthReportRow> getReportRows() {
		return theReportRows;
	}

	public void setReportRows(List<BirthReportRow> aReportRows) {
		theReportRows = aReportRows;
	}

	/** Строки отчета */ 
	private List<BirthReportRow> theReportRows;
	
	/** Дата */
	@Comment("Дата")
	public Date getReportDate() {
		return theReportDate;
	}

	public void setReportDate(Date aReportDate) {
		theReportDate = aReportDate;
	}

	/** Дата */
	private Date theReportDate;
	
	/** ЛПУ */
	@Comment("ЛПУ")
	@ManyToOne
	public MisLpu getLpu() {
		return theLpu;
	}

	public void setLpu(MisLpu aLpu) {
		theLpu = aLpu;
	}

	/** ЛПУ */
	private MisLpu theLpu;

}
