package ru.ecom.web.idemode.tagext;

import javax.servlet.jsp.tagext.TagAttributeInfo;

public class TagAttributeInfoImpl extends TagAttributeInfo {

	public TagAttributeInfoImpl(String name, boolean required, String type,
			boolean reqTime, String aDescription) {
		super(name, required, type, reqTime);
		theDescription = aDescription ;
	}
	
	/** Описание */
	public String getDescription() {
		return theDescription;
	}

	/** Описание */
	private final String theDescription;

}
