package ru.ecom.web.idemode;

import java.util.HashMap;

import javax.servlet.jsp.tagext.TagInfo;

public class EditTagMessage {

	protected EditTagMessage(TagInfo aTagInfo) {
		theTagInfo = aTagInfo ;
	}
	
	/** TagInfo */
	public TagInfo getTagInfo() {
		return theTagInfo;
	}

	public void setTagInfo(TagInfo aTagInfo) {
		theTagInfo = aTagInfo;
	}

	/** Значения */
	public HashMap<String,String> getValues() {
		return theValues;
	}

//	public void setValues(HashMap<String,Object> aValues) {
//		theValues = aValues;
//	}

	/** Значения */
	private final HashMap<String,String> theValues = new HashMap<String, String>();
	/** TagInfo */
	private TagInfo theTagInfo;
}
