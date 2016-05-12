package ru.ecom.mis.ejb.form.licence;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.licence.voc.VocDocumentParameter;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

@EntityForm
@EntityFormPersistance(clazz = VocDocumentParameter.class)
@Comment("Параметер")
@WebTrail(comment = "Параметер", nameProperties = "name", view = "entityView-voc_documentParameter.do")
@EntityFormSecurityPrefix("/Policy/Voc/VocDocumentParameter")
public class VocDocumentParameterForm extends IdEntityForm{
	/**
	 * Группа параметров
	 */
	@Comment("Группа параметров")
	@Persist
	public Long getParameterGroup() {
		return theParameterGroup;
	}
	public void setParameterGroup(Long aParameterGroup) {
		theParameterGroup = aParameterGroup;
	}
	/**
	 * Группа параметров
	 */
	private Long theParameterGroup;
	/**
	 * Минимум нормы
	 */
	@Comment("Минимум нормы")
	@Persist
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
	@Persist
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
	@Persist
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
	@Persist
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
