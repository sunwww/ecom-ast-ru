package ru.ecom.mis.ejb.domain.report.voc;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Тип параметра отчетного показателя
 * @author azviagin
 *
 */

@Comment("Тип параметра отчетного показателя")
@Entity
@Table(schema="SQLUser")
public class VocReportSetParameterType extends VocBaseEntity{

	/** Типы отчетных показателей */
	@Comment("Типы отчетных показателей")
	@OneToMany(mappedBy="parameterType", cascade=CascadeType.ALL)
	public List<ReportSetTypeParameterType> getReportSetTypes() {
		return theReportSetTypes;
	}

	public void setReportSetTypes(List<ReportSetTypeParameterType> aReportSetTypes) {
		theReportSetTypes = aReportSetTypes;
	}

	/** Типы отчетных показателей */
	private List<ReportSetTypeParameterType> theReportSetTypes;
	
	/** Имя класса данных */
	@Comment("Имя класса данных")
	public String getClassName() {
		return theClassName;
	}

	public void setClassName(String aClassName) {
		theClassName = aClassName;
	}

	/** Имя класса данных */
	private String theClassName;

}
