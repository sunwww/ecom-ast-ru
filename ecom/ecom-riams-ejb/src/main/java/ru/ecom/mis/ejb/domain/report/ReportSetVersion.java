package ru.ecom.mis.ejb.domain.report;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.mis.ejb.domain.report.voc.VocReportSetVersionType;
import ru.ecom.mis.ejb.domain.report.voc.VocReportSourceType;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Версия отчетного показателя
 * @author azviagin
 *
 */

@Comment("Версия отчетного показателя")
@Entity
@Table(schema="SQLUser")
public class ReportSetVersion extends BaseEntity{
	
	/** Тип источника отчета */
	@Comment("Тип источника отчета")
	@OneToOne
	public VocReportSourceType getSourceType() {
		return theSourceType;
	}

	public void setSourceType(VocReportSourceType aSourceType) {
		theSourceType = aSourceType;
	}

	/** Тип источника отчета */
	private VocReportSourceType theSourceType;
	
	/** Завершенность */
	@Comment("Завершенность")
	public Boolean getMaturity() {
		return theMaturity;
	}

	public void setMaturity(Boolean aMaturity) {
		theMaturity = aMaturity;
	}

	/** Завершенность */
	private Boolean theMaturity;
	
	/** Недействительность */
	@Comment("Недействительность")
	public Boolean getNoActuality() {
		return theNoActuality;
	}

	public void setNoActuality(Boolean aNoActuality) {
		theNoActuality = aNoActuality;
	}

	/** Недействительность */
	private Boolean theNoActuality;
	
	/** Дата изменения */
	@Comment("Дата изменения")
	public Date getChangeDate() {
		return theChangeDate;
	}

	public void setChangeDate(Date aChangeDate) {
		theChangeDate = aChangeDate;
	}

	/** Дата изменения */
	private Date theChangeDate;
	
	/** Дата создания */
	@Comment("Дата создания")
	public Date getCreateDate() {
		return theCreateDate;
	}

	public void setCreateDate(Date aCreateDate) {
		theCreateDate = aCreateDate;
	}

	/** Дата создания */
	private Date theCreateDate;
	
	/** Значение */
	@Comment("Значение")
	public String getVersionValue() {
		return theVersionValue;
	}

	public void setVersionValue(String aVersionValue) {
		theVersionValue = aVersionValue;
	}

	/** Значение */
	private String theVersionValue;
	
	/** Тип версии */
	@Comment("Тип версии")
	@OneToOne
	public VocReportSetVersionType getType() {
		return theType;
	}

	public void setType(VocReportSetVersionType aType) {
		theType = aType;
	}

	/** Тип версии */
	private VocReportSetVersionType theType;
	
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
