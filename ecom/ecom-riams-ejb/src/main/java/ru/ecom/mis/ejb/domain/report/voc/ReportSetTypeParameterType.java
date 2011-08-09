package ru.ecom.mis.ejb.domain.report.voc;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Тип параметра по типу отчетного показателя
 * @author azviagin
 *
 */

@Comment("Тип параметра по типу отчетного показателя")
@Entity
@Table(schema="SQLUser")
public class ReportSetTypeParameterType extends BaseEntity{
	
	/** Тип параметра */
	@Comment("Тип параметра")
	@ManyToOne
	public VocReportSetParameterType getParameterType() {
		return theParameterType;
	}

	public void setParameterType(VocReportSetParameterType aParameterType) {
		theParameterType = aParameterType;
	}

	/** Тип параметра */
	private VocReportSetParameterType theParameterType;
	
	/** Отчетный показатель */
	@Comment("Тип отчетного показателя")
	@ManyToOne
	public VocReportSetType getReportSetType() {
		return theReportSetType;
	}

	public void setReportSetType(VocReportSetType aReportSetType) {
		theReportSetType = aReportSetType;
	}

	/** Тип отчетного показателя */
	private VocReportSetType theReportSetType;

}
