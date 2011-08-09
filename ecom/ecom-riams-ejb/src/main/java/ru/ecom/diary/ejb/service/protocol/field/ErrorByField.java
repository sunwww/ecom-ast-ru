package ru.ecom.diary.ejb.service.protocol.field;

import java.util.Iterator;


import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

public class ErrorByField {
	public ErrorByField(ActionErrors aErrors,String aProperty) {
		setErrors(aErrors);
		setProperty(aProperty);
	}
	
	public StringBuilder createError() {
		//fieldErrors
		StringBuilder ulError = new StringBuilder() ;
		 boolean hasErrors = false;
		 ulError.append("<ul class=\"fieldErrors\">") ;
        if (theErrors != null && !theErrors.isEmpty()) {
            Iterator iterator = theErrors.get(theProperty);
            while (iterator.hasNext()) {
            	hasErrors = true ;
                ActionMessage msg = (ActionMessage) iterator.next();
                ulError.append("<li>").append(msg.getKey()).append("</li>");
            }
        }
        ulError.append("</ul>");
        return hasErrors? ulError:null ;
	}
	/** ActionError */
	public ActionErrors getErrors() {return theErrors;}
	public void setErrors(ActionErrors aErrors) {theErrors = aErrors;}
	/** Свойство */
	public String getProperty() {return theProperty;}
	public void setProperty(String aProperty) {theProperty = aProperty;}

	/** Свойство */
	private String theProperty;
	/** ActionError */
	private ActionErrors theErrors;

}
