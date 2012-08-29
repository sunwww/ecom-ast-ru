package ru.ecom.mis.ejb.domain.licence.voc;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.ecom.mis.ejb.domain.licence.voc.VocDocumentParameterGroup;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * Справочник параметров документа
	 */
	@Comment("Справочник параметров документа")
@Entity
@Table(schema="SQLUser")
public class VocDocumentParameter extends VocBaseEntity{
	/**
	 * Группа параметров
	 */
	@Comment("Группа параметров")
	@OneToOne
	public VocDocumentParameterGroup getParameterGroup() {
		return theParameterGroup;
	}
	public void setParameterGroup(VocDocumentParameterGroup aParameterGroup) {
		theParameterGroup = aParameterGroup;
	}
	/**
	 * Группа параметров
	 */
	private VocDocumentParameterGroup theParameterGroup;
	/**
	 * Минимум нормы
	 */
	@Comment("Минимум нормы")
	
	public String getNormMinimum() {
		return theNormMinimum;
	}
	public void setNormMinimum(String aNormMinimum) {
		theNormMinimum = aNormMinimum;
	}
	/**
	 * Минимум нормы
	 */
	private String theNormMinimum;
	/**
	 * Максимум нормы
	 */
	@Comment("Максимум нормы")
	
	public String getNormMaximum() {
		return theNormMaximum;
	}
	public void setNormMaximum(String aNormMaximum) {
		theNormMaximum = aNormMaximum;
	}
	/**
	 * Максимум нормы
	 */
	private String theNormMaximum;
	/**
	 * Размерность
	 */
	@Comment("Размерность")
	
	public String getDimension() {
		return theDimension;
	}
	public void setDimension(String aDimension) {
		theDimension = aDimension;
	}
	/**
	 * Размерность
	 */
	private String theDimension;
	/**
	 * Норма
	 */
	@Comment("Норма")
	
	public String getNorm() {
		return theNorm;
	}
	public void setNorm(String aNorm) {
		theNorm = aNorm;
	}
	/**
	 * Норма
	 */
	private String theNorm;
}
