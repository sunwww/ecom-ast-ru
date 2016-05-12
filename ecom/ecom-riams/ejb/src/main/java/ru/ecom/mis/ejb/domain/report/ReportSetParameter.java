package ru.ecom.mis.ejb.domain.report;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.mis.ejb.domain.report.voc.VocReportSetParameterType;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Параметр отчетного показателя
 * @author azviagin
 *
 */

@Comment("Параметр отчетного показателя")
@Entity
@Table(schema="SQLUser")
public class ReportSetParameter extends BaseEntity{
	
	/** Значение отчетного показателя */
	@Comment("Значение отчетного показателя")
	public String getParameterValue() {
		return theParameterValue;
	}

	public void setParameterValue(String aParameterValue) {
		theParameterValue = aParameterValue;
	}

	/** Значение отчетного показателя */
	private String theParameterValue;
	
	/** Тип параметра */
	@Comment("Тип параметра")
	@OneToOne
	public VocReportSetParameterType getType() {
		return theType;
	}

	public void setType(VocReportSetParameterType aType) {
		theType = aType;
	}

	/** Тип параметра */
	private VocReportSetParameterType theType;
	
	/** Отчетный показатель */
	@Comment("Отчетный показатель")
	@ManyToOne
	public ReportSet getReportSet() {
		return theReportSet;
	}

	public void setReportSet(ReportSet aReportSet) {
		theReportSet = aReportSet;
	}

	/** Отчетный показатель */
	private ReportSet theReportSet;

}
