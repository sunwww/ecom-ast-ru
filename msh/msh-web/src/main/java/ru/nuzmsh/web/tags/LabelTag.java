package ru.nuzmsh.web.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.apache.ecs.Element;
import org.apache.ecs.xhtml.div;
import org.apache.ecs.xhtml.input;
import org.apache.ecs.xhtml.span;

/**
 * @author ESinev
 * @jsp.tag     name="label"
 *              display-name="label"
 *              body-content="empty"
 *              description="Label JSP tag."
 */
public class LabelTag extends AbstractFieldTag {

	/** 
	 * Брать из request scope
	 * @jsp.attribute   description = "Атрибут из request"
	 *                     required = "false"
	 *                  rtexprvalue = "true"
	 */
	public boolean getFromRequestScope() {
		return theFromRequestScope;
	}

	public void setFromRequestScope(boolean aFromRequestScope) {
		theFromRequestScope = aFromRequestScope;
	}

	/** Брать из request scope */
	private boolean theFromRequestScope = false;
	
    protected Element getFieldElement() throws JspException {
        div divContainer = new div();
        
        String value ;
        if(theFromRequestScope) {
    		Object obj = pageContext.getRequest().getAttribute(getProperty());
    		if(obj == null) {
    			value = "нет значения в атрибуте '"+getProperty()+"'" ;
    		} else {
    			value = obj.toString();
    		}
        } else {
            input hiddenInput = new input();
            hiddenInput.setType("hidden") ;
            hiddenInput.setName(getProperty()) ;
            hiddenInput.setValue(getFormattedValue()) ;
            divContainer.addElement(hiddenInput) ;
    		value = getFormattedValue() ;
        }

        span s = new span(value);
        divContainer.addElement(s) ;

        return divContainer ;
    }

    protected void printEnterJavascript() throws JspException {
    }
}
