package ru.ecom.ejb.services.entityform.map.model;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;


public class MapPropertyInfo {

	/** Название */
	@Comment("Название")
	public String getName() {
		return theName;
	}

	public void setName(String aName) {
		theName = aName;
	}

	/** Сохраняется ли в Entity */
	@Comment("Сохраняется ли в Entity")
	public boolean getPersist() {
		return thePersist;
	}

	public void setPersist(boolean aPersist) {
		thePersist = aPersist;
	}

	/** Сохраняется ли в Entity */
	private boolean thePersist = false;
	/** Название */
	private String theName;
}
