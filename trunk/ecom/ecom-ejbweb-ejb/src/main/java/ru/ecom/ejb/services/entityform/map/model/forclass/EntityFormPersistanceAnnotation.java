package ru.ecom.ejb.services.entityform.map.model.forclass;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * 
 */
public class EntityFormPersistanceAnnotation extends AbstractClassAnnotation {
	/** Класс Entity */
	@Comment("Класс Entity")
	public String getClazz() {
		return theClazz;
	}

	public void setClazz(String aClazz) {
		theClazz = aClazz;
	}

	/** Класс Entity */
	private String theClazz;
}
