package ru.ecom.web.tags;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.apache.log4j.Logger;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.web.tags.AbstractGuidSimpleSupportTag;

public class SetAttributeTag  extends AbstractGuidSimpleSupportTag {
	private final static Logger LOG = Logger.getLogger(WebQueryTag.class);
	private final static boolean CAN_DEBUG = LOG.isDebugEnabled();
	
    public void doTag() throws JspException, IOException {
    	printIdeStart() ;
        //JspWriter out = getJspContext().getOut() ;
        PageContext ctx = (PageContext) getJspContext() ;
        HttpServletRequest request = (HttpServletRequest) ctx.getRequest() ;
			request.setAttribute(theName, theValue) ;
    }
    /** Наименование атрибута */
	@Comment("Наименование атрибута")
	public String getName() {return theName;}
	public void setName(String aName) {theName = aName;}

	/** Значение атрибута */
	@Comment("Значение атрибута")
	public String getValue() {return theValue;}
	public void setValue(String aValue) {theValue = aValue;}

	/** Значение атрибута */
	private String theValue;
	/** Наименование атрибута */
	private String theName;
    
}