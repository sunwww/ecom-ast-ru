package ru.ecom.mis.ejb.domain.report;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Отчетное ЛПУ
 * @author azviagin
 *
 */

@Comment("Отчетное ЛПУ")
@Entity
@Table(schema="SQLUser")
public class ReportLpu extends BaseEntity{
	
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
	
	/** ЛПУ */
	@Comment("ЛПУ")
	@OneToOne
	public MisLpu getLpu() {
		return theLpu;
	}

	public void setLpu(MisLpu aLpu) {
		theLpu = aLpu;
	}

	/** ЛПУ */
	private MisLpu theLpu;

}
