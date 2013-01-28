package ru.ecom.mis.ejb.domain.report.voc;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.patient.voc.VocSex;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Тип параметра отчетного показателя
 * @author azviagin
 *
 */

@Comment("Тип параметра отчетного показателя")
@Entity
@Table(schema="SQLUser")
@AIndexes(value = { @AIndex(properties = { "className","strCode" })
})
public class VocReportSetParameterType extends VocBaseEntity{
	
	/** Имя класса данных */
	@Comment("Имя класса данных")
	public String getClassName() {
		return theClassName;
	}

	public void setClassName(String aClassName) {
		theClassName = aClassName;
	}
	
	/** Пол */
	@Comment("Пол")
	@OneToOne
	public VocSex getSex() {
		return theSex;
	}

	public void setSex(VocSex aSex) {
		theSex = aSex;
	}
	/** Пол код */
	@Comment("Пол код")
	public String getSexCode() {
		return theSexCode;
	}

	public void setSexCode(String aSexCode) {
		theSexCode = aSexCode;
	}

	/** Пол код */
	private String theSexCode;

	/** Строка */
	@Comment("Строка")
	public String getStrCode() {
		return theStrCode;
	}

	public void setStrCode(String aStr) {
		theStrCode = aStr;
	}

	/** Строка */
	private String theStrCode;
	/** Пол */
	private VocSex theSex;

	/** Имя класса данных */
	private String theClassName;

}
