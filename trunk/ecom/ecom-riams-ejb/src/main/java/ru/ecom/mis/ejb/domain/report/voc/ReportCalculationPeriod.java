package ru.ecom.mis.ejb.domain.report.voc;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.mis.ejb.domain.report.Report;
import ru.ecom.mis.ejb.domain.workcalendar.voc.VocPeriodType;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 *Период расчета отчета 
 * @author azviagin
 *
 */

@Comment("Период расчета отчета")
@Entity
@Table(schema="SQLUser")
public class ReportCalculationPeriod extends BaseEntity{
	
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
	
	/** Тип периода */
	@Comment("Тип периода")
	@OneToOne
	public VocPeriodType getPeriodType() {
		return thePeriodType;
	}

	public void setPeriodType(VocPeriodType aPeriodType) {
		thePeriodType = aPeriodType;
	}

	/** Тип периода */
	private VocPeriodType thePeriodType;
	
	/** Исход */
	@Comment("Исход")
	public String getResult() {return theResult;}
	public void setResult(String aResult) {theResult = aResult;}

	/** Исход */
	private String theResult;
	
}
