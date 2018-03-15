package ru.nuzmsh.web.tags;

import javax.servlet.jsp.tagext.TagSupport;

import ru.nuzmsh.web.util.IdeTagHelper;

public class AbstractGuidSupportTag extends TagSupport implements IGuidSupport {

	protected void printIdeStart(String aName) {
		IdeTagHelper ide = IdeTagHelper.getInstance() ;
		ide.printParentStart(aName, pageContext);
		ide.printMarker(aName, this, pageContext);		
	}

	protected void printIdeEnd() {
		IdeTagHelper ide = IdeTagHelper.getInstance() ;
		ide.printParentEnd(pageContext);		
	}
	
	protected void showException(Exception e) {
		IdeTagHelper.getInstance().showException(this, e, pageContext) ;
	}
	
	/**
	 * GUID
	 * @jsp.attribute   description = "GUID"
	 *                     required = "false"
	 *                  rtexprvalue = "true"
	 */
	public String getGuid() {
	        return theGuid;
	}
	public void setGuid(String aGuid) {
	        theGuid = aGuid;
	}
	/** GUID */
	private String theGuid;
	
}
