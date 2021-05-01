package ru.ecom.mis.ejb.form.licence;

import lombok.Setter;
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
@Setter
public class VocDocumentParameterForm extends IdEntityForm{
	/**
	 * Группа параметров
	 */
	@Comment("Группа параметров")
	@Persist
	public Long getParameterGroup() {
		return parameterGroup;
	}
	/**
	 * Группа параметров
	 */
	private Long parameterGroup;
	/**
	 * Минимум нормы
	 */
	@Comment("Минимум нормы")
	@Persist
	public String getNormMinimum() {
		return normMinimum;
	}
	/**
	 * Минимум нормы
	 */
	private String normMinimum;
	/**
	 * Максимум нормы
	 */
	@Comment("Максимум нормы")
	@Persist
	public String getNormMaximum() {
		return normMaximum;
	}
	/**
	 * Максимум нормы
	 */
	private String normMaximum;
	/**
	 * Размерность
	 */
	@Comment("Размерность")
	@Persist
	public String getDimension() {
		return dimension;
	}
	/**
	 * Размерность
	 */
	private String dimension;
	/**
	 * Норма
	 */
	@Comment("Норма")
	@Persist
	public String getNorm() {
		return norm;
	}
	/**
	 * Норма
	 */
	private String norm;
}
