package ru.ecom.mis.ejb.domain.report.voc;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.patient.voc.VocSex;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Тип параметра по типу отчетного показателя
 * @author azviagin
 *
 */

@Comment("Тип параметра по типу отчетного показателя")
@Entity
@Table(schema="SQLUser")
@AIndexes(value = { @AIndex(properties = { "codeFrom","codeTo" })
	,@AIndex(properties = { "parameterType" })
	,@AIndex(properties = { "sex" })
})
public class ReportSetTypeParameterType extends VocBaseEntity{
	
	/** Тип параметра */
	@Comment("Тип параметра")
	@OneToOne
	public VocReportSetParameterType getParameterType() {
		return theParameterType;
	}

	public void setParameterType(VocReportSetParameterType aParameterType) {
		theParameterType = aParameterType;
	}
	
	/** Начальный параметр */
	@Comment("Начальный параметр")
	public String getCodeFrom() {return theCodeFrom;}
	public void setCodeFrom(String aCodeFrom) {theCodeFrom = aCodeFrom;}

	/** Последний параметер */
	@Comment("Последний параметр")
	public String getCodeTo() {
		return theCodeTo;
	}

	public void setCodeTo(String aCodeTo) {
		theCodeTo = aCodeTo;
	}

	/** Пол код */
	@Comment("Пол код")
	public String getSexCode() {return theSexCode;}
	public void setSexCode(String aSexCode) {theSexCode = aSexCode;}

	/** Пол */
	@Comment("Пол")
	@OneToOne
	public VocSex getSex() {
		return theSex;
	}

	public void setSex(VocSex aSex) {
		theSex = aSex;
	}

	/** Пол */
	private VocSex theSex;
	/** Пол код */
	private String theSexCode;
	/** Последний параметр */
	private String theCodeTo;
	/** Начальный параметр */
	private String theCodeFrom;

	/** Тип параметра */
	private VocReportSetParameterType theParameterType;
}
