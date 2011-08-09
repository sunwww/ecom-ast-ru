package ru.ecom.mis.ejb.domain.mortality;

import static javax.persistence.CascadeType.ALL;

import java.sql.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * День отчета по смертности
 * @author oegorova
 *
 */

@Comment("День отчета по смертности")
@Entity
@Table(schema="SQLUser")
public class MortalityReportDate extends BaseEntity{

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
	
	/** Записи отчета */
	@Comment("Записи отчета")
	@OneToMany(mappedBy="mortalityReportDate", cascade=ALL )
	public List<MortalityReportRow> getReportRows() {
		return theReportRows;
	}

	public void setReportRows(List<MortalityReportRow> aReportRows) {
		theReportRows = aReportRows;
	}

	/** Записи отчета */
	private List<MortalityReportRow> theReportRows;

}
