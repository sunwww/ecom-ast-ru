package ru.ecom.ejb.services.entityform.map.model.forclass;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

public class ParentAnnotation extends AbstractClassAnnotation {
	/** Свойство */
	@Comment("Свойство")
	public String getProperty() {
		return theProperty;
	}

	public void setProperty(String aProperty) {
		theProperty = aProperty;
	}

	/** Родительская форма */
	@Comment("Родительская форма")
	public String getParentForm() {
		return theParentForm;
	}

	public void setParentForm(String aParentForm) {
		theParentForm = aParentForm;
	}

	/** Родительская форма */
	private String theParentForm;
	/** Свойство */
	private String theProperty;
}
