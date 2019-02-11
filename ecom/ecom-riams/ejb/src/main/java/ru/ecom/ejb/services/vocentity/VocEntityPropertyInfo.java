package ru.ecom.ejb.services.vocentity;

import java.io.Serializable;

public class VocEntityPropertyInfo implements Serializable{

	public VocEntityPropertyInfo(String aName, String aComment) {
		theName = aName ;
		theComment = aComment ;
	}
	
	/** Комментарий */
	public String getComment() {
		return theComment;
	}

	/** Комментарий */
	private final String theComment;
	/** Название свойства */
	public String getName() {
		return theName;
	}

	/** Название свойства */
	private final String theName;
}
