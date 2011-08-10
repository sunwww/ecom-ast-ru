package ru.nuzmsh.web.tags;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;

import ru.nuzmsh.forms.validator.BaseValidatorForm;

/**
 *
 * Если форма только для просмотра
 * @jsp.tag  name="ifFormTypeIsView"
 *          display-name="Если форма только для просмотра"
 *          body-content="scriptless"
 *          description="Секция"
 *
 */
public class IfFormTypeIsViewTag extends AbstractGuidSimpleSupportTag {

    /**
     * Форма
     * @jsp.attribute   description = "Форма"
     *                     required = "true"
     *                  rtexprvalue = "true"
     */
    public String getFormName() { return theFormName ; }
    public void setFormName(String aFormName) { theFormName = aFormName ; }

    public void doTag() throws JspException, IOException {
        JspWriter out = getJspContext().getOut() ;
        PageContext ctx = (PageContext) getJspContext() ;
        
        if(!isInsideJavascript()) {
            printIdeStart(getClass().getSimpleName());
        }
        
        HttpServletRequest request = (HttpServletRequest) ctx.getRequest() ;
        Object oform = request.getAttribute(theFormName) ;
        if(oform!=null && oform instanceof BaseValidatorForm ) {
            BaseValidatorForm form = (BaseValidatorForm) oform ;
            if(checkForm(form)) {
            	if(getJspBody()==null) {
            		showException(new Exception("Нет внутренностей")) ;
            	} else {
                    getJspBody().invoke(out);
            	}
            }
        } else {
            if(isInsideJavascript()) {
            	out.print(" //") ;
            }
            out.print("<div class='errorMessage'>") ;
            out.print("Нет формы "+theFormName) ;
            out.println("</div>")  ;
        }
        if(!isInsideJavascript()) {
        	printIdeEnd() ;
        }
    }

    /** 
	 * Если внутри JavaScript
	 * @jsp.attribute   description = "Если внутри JavaScript"
	 *                     required = "false"
	 *                  rtexprvalue = "true"
	 */
	public boolean isInsideJavascript() {
		return theInsideJavascript;
	}

	public void setInsideJavascript(boolean aInsideJavascript) {
		theInsideJavascript = aInsideJavascript;
	}

	/** Если внутри JavaScript */
	private boolean theInsideJavascript = false;
	
    boolean checkForm(BaseValidatorForm aForm) {
        return aForm.isViewOnly() ;
    }



    /** Форма */
    private String theFormName ;
}
