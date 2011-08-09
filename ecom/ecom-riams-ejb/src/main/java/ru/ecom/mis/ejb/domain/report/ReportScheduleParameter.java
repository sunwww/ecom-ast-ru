package ru.ecom.mis.ejb.domain.report;

/**
 * Параметер расписания отчета
 */

import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;

@Comment("Параметер расписания отчета")
@Entity
@Table(schema="SQLUser")
public class ReportScheduleParameter extends BaseEntity{
	
	/** Значение */
	@Comment("Значение")
	public String getParameterValue() {
		return theParameterValue;
	}

	public void setParameterValue(String aParameterValue) {
		theParameterValue = aParameterValue;
	}
	
	/** Параметер */
	@Comment("Параметер")
	@OneToOne
	public ReportParameter getParameter() {
		return theParameter;
	}

	public void setParameter(ReportParameter aParameter) {
		theParameter = aParameter;
	}
	
	/** Расписание */
	@Comment("Расписание")
	@ManyToOne
	public ReportSchedule getSchedule() {
		return theSchedule;
	}

	public void setSchedule(ReportSchedule aSchedule) {
		theSchedule = aSchedule;
	}

	/** Расписание */
	private ReportSchedule theSchedule;

	/** Параметер */
	private ReportParameter theParameter;
	/** Значение */
	private String theParameterValue;
}
