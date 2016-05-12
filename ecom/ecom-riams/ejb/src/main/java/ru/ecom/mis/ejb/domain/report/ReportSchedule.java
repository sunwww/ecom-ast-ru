package ru.ecom.mis.ejb.domain.report;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import ru.ecom.mis.ejb.domain.report.voc.VocReportPeriod;
import ru.ecom.mis.ejb.domain.workcalendar.voc.VocPeriodType;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;

/**
 * Планировщик отчетов
 *
 */

@Comment("Планировщик отчетов")
@Entity
@Table(schema="SQLUser")
public class ReportSchedule extends BaseEntity{
	
	/** Отчет */
	@Comment("Отчет")
	@OneToOne
	public Report getReport() {
		return theReport;
	}

	public void setReport(Report aReport) {
		theReport = aReport;
	}

	/** Тип периода отчета */
	@Comment("Тип периода отчета")
	@OneToOne
	public VocPeriodType getPeriodType() {
		return thePeriodType;
	}

	public void setPeriodType(VocPeriodType aPeriodType) {
		thePeriodType = aPeriodType;
	}

	/** Дата начала */
	@Comment("Дата начала")
	public Date getStartDate() {
		return theStartDate;
	}

	public void setStartDate(Date aStartDate) {
		theStartDate = aStartDate;
	}

	/** Дата окончания */
	@Comment("Дата окончания")
	public Date getFinishDate() {
		return theFinishDate;
	}

	public void setFinishDate(Date aFinishDate) {
		theFinishDate = aFinishDate;
	}

	/** Период */
	@Comment("Период")
	@OneToOne
	public VocReportPeriod getPeriod() {
		return thePeriod;
	}

	public void setPeriod(VocReportPeriod aPeriod) {
		thePeriod = aPeriod;
	}

	/** Заказчик */
	@Comment("Заказчик")
	public WorkFunction getCustomer() {
		return theCustomer;
	}

	public void setCustomer(WorkFunction aCustomer) {
		theCustomer = aCustomer;
	}

	/** Дата заказа */
	@Comment("Дата заказа")
	public Date getOrderDate() {
		return theOrderDate;
	}

	public void setOrderDate(Date aOrderDate) {
		theOrderDate = aOrderDate;
	}
	 /** Параметры */
	@Comment("Параметры")
	@OneToMany(mappedBy="schedule", cascade=CascadeType.ALL)
	public List<ReportScheduleParameter> getParameters() {
		return theParameters;
	}

	public void setParameters(List<ReportScheduleParameter> aParameters) {
		theParameters = aParameters;
	}

	/** Параметры */
	private List<ReportScheduleParameter> theParameters;
	
	/** Дата начала расчета */
	@Comment("Дата начала расчета")
	public Date getCalculationStartDate() {
		return theCalculationStartDate;
	}

	public void setCalculationStartDate(Date aCalculationStartDate) {
		theCalculationStartDate = aCalculationStartDate;
	}

	/** Дата начала расчета */
	private Date theCalculationStartDate;
	
	/** Дата окончания расчета */
	@Comment("Дата окончания расчета")
	public Date getCalculationFinishDate() {
		return theCalculationFinishDate;
	}

	public void setCalculationFinishDate(Date aCalculationFinishDate) {
		theCalculationFinishDate = aCalculationFinishDate;
	}

	/** Дата окончания расчета */
	private Date theCalculationFinishDate;
	
	/** Время начала расчета */
	@Comment("Время начала расчета")
	public Time getCalculationStartTime() {
		return theCalculationStartTime;
	}

	public void setCalculationStartTime(Time aCalculationStartTime) {
		theCalculationStartTime = aCalculationStartTime;
	}

	/** Время начала расчета */
	private Time theCalculationStartTime;
	
	/** Время окончания расчета */
	@Comment("Время окончания расчета")
	public Time getCalculationFinishTime() {
		return theCalculationFinishTime;
	}

	public void setCalculationFinishTime(Time aCalculationFinishTime) {
		theCalculationFinishTime = aCalculationFinishTime;
	}
	
	/** Время окончания расчета */
	private Time theCalculationFinishTime;


	/** Дата заказа */
	private Date theOrderDate;
	/** Заказчик */
	private WorkFunction theCustomer;
	/** Период */
	private VocReportPeriod thePeriod;
	/** Дата окончания */
	private Date theFinishDate;
	/** Дата начала */
	private Date theStartDate;
	/** Тип периода отчета */
	private VocPeriodType thePeriodType;
	/** Отчет */
	private Report theReport;
	
	/** Успех расчета */
	@Comment("Успех расчета")
	public Boolean getSuccess() {
		return theSuccess;
	}

	public void setSuccess(Boolean aSuccess) {
		theSuccess = aSuccess;
	}

	/** Успех расчета */
	private Boolean theSuccess;
}
