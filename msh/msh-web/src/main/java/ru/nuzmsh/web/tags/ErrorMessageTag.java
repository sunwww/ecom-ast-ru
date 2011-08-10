package ru.nuzmsh.web.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import ru.nuzmsh.web.messages.ErrorMessage;

/**
 * Печать сообщения об ошибке
 *
 * @jsp.tag     name = "errorMessage"
 *      display-name = "Печать сообщения об ошибке"
 *      body-content = "scriptless"
 *       description = "Печать сообщения об ошибке"
 *
 */
public class ErrorMessageTag extends SimpleTagSupport {

    public void doTag() throws JspException, IOException {
        PageContext ctx = (PageContext) getJspContext() ;
        ErrorMessage message = ErrorMessage.findInRequest(ctx.getRequest()) ;
        if(message!=null) {
            JspWriter out = getJspContext().getOut() ;
            out.println("<table id='errorMessageContainer'style='margin-left: 4em'><tr><td>");
            out.println(" <div class='errorMessage'>") ;
            out.println(" <a href='#' class='errorMessageClose' title='Убрать сообщение об ошибке' onclick='$(\"errorMessageContainer\").style.display=\"none\"'>Убрать</a>") ;
            out.println(removeExceptionText(message.getMessage())) ;
            out.println(" </div>") ;
            out.println("</td></tr></table>") ;
        }
    }

    private static String removeExceptionText(String aStr) {
    	aStr = aStr.replace("java.lang.IllegalStateException:","") ;
    	aStr = aStr.replace("javax.ejb.EJBException:","") ;
    	aStr = aStr.replace("org.mozilla.javascript.JavaScriptException:","") ;
    	aStr = aStr.replace("EJBException:; nested exception is:","") ;
    	aStr = aStr.replace("ru.nuzmsh.ejb.exceptions.EJBExceptionCause:","") ;
        aStr = aStr.replace("Ошибка подготовки к созданию новой формы :","") ;
        int cnt = aStr.indexOf("(/META-INF/formjs") ; 
        if (cnt>0) {
        	aStr = aStr.substring(0,cnt);
        }
        return aStr ;
    }

}
