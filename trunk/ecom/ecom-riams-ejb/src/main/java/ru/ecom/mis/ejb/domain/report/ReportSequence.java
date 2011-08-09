package ru.ecom.mis.ejb.domain.report;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.mis.ejb.domain.workcalendar.voc.VocPeriodType;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Предшественник отчета по расчету
 * @author azviagin
 *
 */

@Comment("Предшественник отчета по расчету")
@Entity
@Table(schema="SQLUser")
public class ReportSequence extends BaseEntity{
	
	/** Отчет */
	@Comment("Отчет")
	@ManyToOne
	public Report getReport() {
		return theReport;
	}

	public void setReport(Report aReport) {
		theReport = aReport;
	}

	/** Отчет */
	private Report theReport;
	
	/** Предшественник */
	@Comment("Предшественник")
	@OneToOne
	public Report getPrecursor() {
		return thePrecursor;
	}

	public void setPrecursor(Report aPrecursor) {
		thePrecursor = aPrecursor;
	}

	/** Предшественник */
	private Report thePrecursor;
	
	/** Тип периода расчета */
	@Comment("Тип периода расчета")
	@OneToOne
	public VocPeriodType getPeriodType() {
		return thePeriodType;
	}

	public void setPeriodType(VocPeriodType aPeriodType) {
		thePeriodType = aPeriodType;
	}

	/** Тип периода расчета */
	private VocPeriodType thePeriodType;

}
