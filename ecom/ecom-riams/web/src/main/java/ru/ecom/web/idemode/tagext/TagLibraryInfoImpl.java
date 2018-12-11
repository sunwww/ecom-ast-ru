package ru.ecom.web.idemode.tagext;

import javax.servlet.jsp.tagext.TagInfo;
import javax.servlet.jsp.tagext.TagLibraryInfo;
import java.util.ArrayList;

public class TagLibraryInfoImpl extends TagLibraryInfo {

	protected TagLibraryInfoImpl(String aName, String aUri) {
		super(aName, aUri);
	}

	protected void addTagInfo(TagInfo aTagInfo) {
		theInfos.add(aTagInfo);
	}
	
	
	@Override
	public TagInfo[] getTags() {
		TagInfo[] ret = new TagInfo[theInfos.size()];
		return theInfos.toArray(ret);
	}

	public TagLibraryInfo[] getTagLibraryInfos() {
		return new TagLibraryInfo[0];
	}


	private ArrayList<TagInfo> theInfos = new ArrayList<TagInfo>() ;
	
}
