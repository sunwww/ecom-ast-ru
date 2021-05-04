package ru.ecom.ejb.services.vocentity;

import lombok.Getter;

import java.io.Serializable;
@Getter
public class VocEntityPropertyInfo implements Serializable{

	public VocEntityPropertyInfo(String aName, String aComment) {
		name = aName ;
		comment = aComment ;
	}
	

	/** Комментарий */
	private final String comment;

	/** Название свойства */
	private final String name;
}
