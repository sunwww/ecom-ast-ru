package ru.ecom.mis.ejb.domain.report;

/**
 * Отчет
 */

import java.util.List;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.mis.ejb.domain.report.voc.ReportCalculationPeriod;
import ru.ecom.mis.ejb.domain.report.voc.VocReportDays;
import ru.ecom.mis.ejb.domain.report.voc.VocReportType;

@Comment("Отчет")
@Entity
@Table(schema="SQLUser")
public class Report extends BaseEntity{
	 
	/** Тип отчета */
	@Comment("Тип отчета")
	@OneToOne
	public VocReportType getReportType() {
		return theReportType;
	}

	public void setReportType(VocReportType aReportType) {
		theReportType = aReportType;
	}

	/** Тип отчета */
	private VocReportType theReportType;
	
	/** Программа расчета */
	@Comment("Программа расчета")
	public String getProgramm() {
		return theProgramm;
	}

	public void setProgramm(String aProgramm) {
		theProgramm = aProgramm;
	}

	/** Программа расчета */
	private String theProgramm;
	
	/** Действует */
	@Comment("Действует")
	public Boolean getActive() {
		return theActive;
	}

	public void setActive(Boolean aActive) {
		theActive = aActive;
	}

	/** Действует */
	private Boolean theActive;
	
	/** Дни расчета */
	@Comment("Дни расчета")
	@OneToOne
	public VocReportDays getDays() {
		return theDays;
	}

	public void setDays(VocReportDays aDays) {
		theDays = aDays;
	}

	/** Дни расчета */
	private VocReportDays theDays;
	
	/** Периоды */
	@Comment("Периоды")
	@OneToMany(mappedBy="report", cascade=CascadeType.ALL)
	public List<ReportCalculationPeriod> getPeriods() {
		return thePeriods;
	}

	public void setPeriods(List<ReportCalculationPeriod> aPeriods) {
		thePeriods = aPeriods;
	}

	/** Периоды */
	private List<ReportCalculationPeriod> thePeriods;
	
	/** Параметры */
	@Comment("Параметры")
	@OneToMany(mappedBy="report", cascade=CascadeType.ALL)
	public List<ReportParameter> getParameters() {
		return theParameters;
	}

	public void setParameters(List<ReportParameter> aParameters) {
		theParameters = aParameters;
	}

	/** Параметры */
	private List<ReportParameter> theParameters;
	
	/** Комментарий */
	@Comment("Комментарий")
	public String getComment() {
		return theComment;
	}

	public void setComment(String aComment) {
		theComment = aComment;
	}

	/** Комментарий */
	private String theComment;
	
	/** Предшественники по расчету */
	@Comment("Предшественники по расчету")
	@OneToMany(mappedBy="report", cascade=CascadeType.ALL)
	public List<ReportSequence> getPrecursors() {
		return thePrecursors;
	}

	public void setPrecursors(List<ReportSequence> aPrecursors) {
		thePrecursors = aPrecursors;
	}

	/** Предшественники по расчету */
	private List<ReportSequence> thePrecursors;
	
	/** Список ЛПУ */
	@Comment("Список ЛПУ")
	@OneToMany(mappedBy="report", cascade=CascadeType.ALL)
	public List<ReportLpu> getLpuList() {
		return theLpuList;
	}

	public void setLpuList(List<ReportLpu> aLpuList) {
		theLpuList = aLpuList;
	}

	/** Список ЛПУ */
	private List<ReportLpu> theLpuList;
	
	

}
