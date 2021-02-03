package ru.ecom.web.idemode;

import javax.servlet.jsp.tagext.TagInfo;
import java.util.HashMap;
import java.util.Map;

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
	public Map<String,String> getValues() {
		return theValues;
	}

	/** Значения */
	private final HashMap<String,String> theValues = new HashMap<>();
	/** TagInfo */
	private TagInfo theTagInfo;
}
