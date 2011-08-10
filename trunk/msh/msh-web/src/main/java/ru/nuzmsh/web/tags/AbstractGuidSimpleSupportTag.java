package ru.nuzmsh.web.tags;

import javax.servlet.jsp.tagext.SimpleTagSupport;

import ru.nuzmsh.web.util.IdeTagHelper;

public class AbstractGuidSimpleSupportTag extends SimpleTagSupport
	implements IGuidSupport {

	protected void printIdeStart() {
		printIdeStart(getClass().getSimpleName());
	}	
	
	protected void printMarker() {
		IdeTagHelper ide = IdeTagHelper.getInstance() ;
		ide.printMarker(getClass().getSimpleName(), this, getJspContext());		
	}
	
	protected void printIdeStart(String aName) {
		IdeTagHelper ide = IdeTagHelper.getInstance() ;
		ide.printParentStart(aName, getJspContext());
		ide.printMarker(aName, this, getJspContext());		
	}

	protected void printIdeEnd() {
		IdeTagHelper ide = IdeTagHelper.getInstance() ;
		ide.printParentEnd(getJspContext());		
	}
	
	protected void showException(Exception e) {
		IdeTagHelper.getInstance().showException(this, e, getJspContext()) ;
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
