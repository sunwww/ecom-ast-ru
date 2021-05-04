package ru.ecom.diary.ejb.service.protocol.field;

import java.util.Iterator;


import lombok.Getter;
import lombok.Setter;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

@Setter
@Getter
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
        if (errors != null && !errors.isEmpty()) {
            Iterator iterator = errors.get(property);
            while (iterator.hasNext()) {
            	hasErrors = true ;
                ActionMessage msg = (ActionMessage) iterator.next();
                ulError.append("<li>").append(msg.getKey()).append("</li>");
            }
        }
        ulError.append("</ul>");
        return hasErrors? ulError:null ;
	}

	/** Свойство */
	private String property;
	/** ActionError */
	private ActionErrors errors;

}
