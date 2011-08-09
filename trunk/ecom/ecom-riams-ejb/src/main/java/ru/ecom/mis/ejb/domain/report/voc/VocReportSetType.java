package ru.ecom.mis.ejb.domain.report.voc;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Справочник типов отчетных показателей
 * @author azviagin
 *
 */

@Comment("Справочник типов отчетных показателей")
@Entity
@Table(schema="SQLUser")
public class VocReportSetType extends VocBaseEntity{
	
	/** Типы параметров */
	@Comment("Типы параметров")
	@OneToMany(mappedBy="reportSetType", cascade=CascadeType.ALL)
	public List<ReportSetTypeParameterType> getParameterTypes() {
		return theParameterTypes;
	}

	public void setParameterTypes(List<ReportSetTypeParameterType> aParameterTypes) {
		theParameterTypes = aParameterTypes;
	}

	/** Типы параметров */
	private List<ReportSetTypeParameterType> theParameterTypes;
	
	/** Метод расчета */
	@Comment("Метод расчета")
	@OneToOne
	public VocReportCalculateMethod getCalculateMethod() {
		return theMethod;
	}

	public void setCalculateMethod(VocReportCalculateMethod aMethod) {
		theMethod = aMethod;
	}

	/** Метод расчета */
	private VocReportCalculateMethod theMethod;

}
