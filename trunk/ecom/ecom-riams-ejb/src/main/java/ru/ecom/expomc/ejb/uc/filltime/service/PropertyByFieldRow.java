package ru.ecom.expomc.ejb.uc.filltime.service;

import java.io.Serializable;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;


public class PropertyByFieldRow implements Serializable {

	/** Идентификатор */
	@Comment("Идентификатор")
	public String getId() {
		return theId;
	}

	public void setId(String aId) {
		theId = aId;
	}

	/** Название свойства */
	@Comment("Название свойства")
	public String getProperty() {
		return theProperty;
	}

	public void setProperty(String aProperty) {
		theProperty = aProperty;
	}

	/** Комментария */
	@Comment("Комментария")
	public String getComment() {
		return theComment;
	}

	public void setComment(String aComment) {
		theComment = aComment;
	}

	/** Поле */
	@Comment("Поле")
	public String getField() {
		return theField;
	}

	public void setField(String aField) {
		theField = aField;
	}

	/** Текст преобразования */
	@Comment("Текст преобразования")
	public String getTransformText() {
		return theTransformText;
	}

	public void setTransformText(String aTransformText) {
		theTransformText = aTransformText;
	}

	/** Текст преобразования */
	private String theTransformText;
	/** Поле */
	private String theField;
	/** Комментария */
	private String theComment;
	/** Название свойства */
	private String theProperty;
	/** Идентификатор */
	private String theId;
}
