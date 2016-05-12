package ru.ecom.mis.ejb.domain.report.voc;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Метод расчета отчетного показателя
 * @author azviagin
 *
 */

@Comment("Метод расчета отчетного показателя")
@Entity
@Table(schema="SQLUser")
public class VocReportCalculateMethod extends VocBaseEntity {
	
	/** Тип источника отчетов */
	@Comment("Тип источника отчетов")
	@OneToOne
	public VocReportSourceType getSourceType() {
		return theSourceType;
	}

	public void setSourceType(VocReportSourceType aSourceType) {
		theSourceType = aSourceType;
	}

	/** Тип источника отчетов */
	private VocReportSourceType theSourceType;
	
	/** Тип расчета */
	@Comment("Тип расчета")
	@OneToOne
	public VocReportCalculateMethodType getMethodType() {
		return theMethodType;
	}

	public void setMethodType(VocReportCalculateMethodType aMethodType) {
		theMethodType = aMethodType;
	}

	/** Тип расчета */
	private VocReportCalculateMethodType theMethodType;

	/** Путь метода */
	@Comment("Путь метода")
	public String getMethodPath() {
		return theMethodPath;
	}

	public void setMethodPath(String aMethodPath) {
		theMethodPath = aMethodPath;
	}

	/** Путь метода */
	private String theMethodPath;
}
