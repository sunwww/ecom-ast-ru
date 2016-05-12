package ru.ecom.mis.ejb.domain.report;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.mis.ejb.domain.report.voc.VocReportSetType;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Отчетный показатель
 * @author azviagin
 *
 */
@Comment("Отчетный показатель")
@Entity
@Table(schema="SQLUser")
public class ReportSet extends BaseEntity{
	
	/** Потомки */
	@Comment("Потомки")
	@OneToMany(mappedBy="parent", cascade=CascadeType.ALL)
	public List<ReportSet> getChildren() {
		return theChildren;
	}

	public void setChildren(List<ReportSet> aChildren) {
		theChildren = aChildren;
	}

	/** Потомки */
	private List<ReportSet> theChildren;
	
	/** Родитель */
	@Comment("Родитель")
	@ManyToOne
	public ReportSet getParent() {
		return theParent;
	}

	public void setParent(ReportSet aParent) {
		theParent = aParent;
	}

	/** Родитель */
	private ReportSet theParent;
	
	/** Параметры */
	@Comment("Параметры")
	@OneToMany(mappedBy="reportSet", cascade=CascadeType.ALL)
	public List<ReportSetParameter> getParameters() {
		return theParameters;
	}

	public void setParameters(List<ReportSetParameter> aParameters) {
		theParameters = aParameters;
	}

	/** Параметры */
	private List<ReportSetParameter> theParameters;
	
	/** Версии */
	@Comment("Версии")
	@OneToMany(mappedBy="reportSet", cascade=CascadeType.ALL)
	public List<ReportSetVersion> getVersions() {
		return theVersions;
	}

	public void setVersions(List<ReportSetVersion> aVersions) {
		theVersions = aVersions;
	}

	/** Версии */
	private List<ReportSetVersion> theVersions;
	
	/** Тип показателя */
	@Comment("Тип показателя")
	@OneToOne
	public VocReportSetType getType() {
		return theType;
	}

	public void setType(VocReportSetType aType) {
		theType = aType;
	}

	/** Тип показателя */
	private VocReportSetType theType;

}
