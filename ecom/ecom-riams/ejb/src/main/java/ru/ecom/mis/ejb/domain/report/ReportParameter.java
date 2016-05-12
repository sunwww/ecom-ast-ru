package ru.ecom.mis.ejb.domain.report;

/**
 * Параметер отчета
 */

import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;

@Comment("Параметер отчета")
@Entity
@Table(schema="SQLUser")
public class ReportParameter extends BaseEntity{
	
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
	
	/** Тип параметра */
	@Comment("Тип параметра")
	public String getParameterType() {
		return theParameterType;
	}

	public void setParameterType(String aParameterType) {
		theParameterType = aParameterType;
	}

	/** Тип параметра */
	private String theParameterType;
	
	/** Обязательный */
	@Comment("Обязательный")
	public Boolean getRequired() {
		return theRequired;
	}

	public void setRequired(Boolean aRequired) {
		theRequired = aRequired;
	}

	/** Обязательный */
	private Boolean theRequired;
	
	/** Название */
	@Comment("Название")
	public String getName() {
		return theName;
	}

	public void setName(String aName) {
		theName = aName;
	}

	/** Название */
	private String theName;
	
	/** Порядковый номер в параметрах отчета */
	@Comment("Порядковый номер в параметрах отчета")
	public Integer getCallPosition() {return theCallPosition;}
	public void setCallPosition(Integer aCallPosition) {theCallPosition = aCallPosition;}

	/** Порядковый номер в параметрах отчета */
	private Integer theCallPosition;

}
