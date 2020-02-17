package ru.nuzmsh.web.tags;

import ru.nuzmsh.web.messages.InfoMessage;
import ru.nuzmsh.web.tags.helper.JavaScriptContext;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

/**
 * Печать информационного сообщения
 *
 * @jsp.tag     name = "infoMessage"
 *      display-name = "Печать информационного сообщения"
 *      body-content = "scriptless"
 *       description = "Печать информационного сообщения"
 *
 */
public class InfoMessageTag extends SimpleTagSupport {

    public void doTag() throws JspException, IOException {
        PageContext ctx = (PageContext) getJspContext() ;
        InfoMessage message = InfoMessage.findMessage(ctx.getRequest()) ;
        if(message!=null) {
            JspWriter out = getJspContext().getOut() ;
            out.println("<div id='formInfoMessageTemp'"+(message.getAutoHide() ? "onclick='hideInfoMessage()'" : "")+" >") ;
            out.println("  <table style='margin-left: 4em'><tr><td>");
            out.println("    <div class='formInfoMessage'>") ;
            out.println(removeExceptionText(message.getMessage())) ;
            out.println("    </div>") ;
            out.println("  </td></tr></table>") ;
            out.println("</div>") ;

            if (message.getAutoHide()) {
                JavaScriptContext js = JavaScriptContext.getContext(ctx, this) ;
                js.println("function hideInfoMessage() {");
                js.println("  $('formInfoMessageTemp').style.display = 'none' ;");
                js.println("}");
                js.println("window.setTimeout(hideInfoMessage, 20000);");
            }

            InfoMessage.removeFromSession(ctx.getSession()) ;

        }
    }
    private static String removeExceptionText(String aStr) {
    	aStr = aStr.replace("java.lang.IllegalStateException:","") ;
    	aStr = aStr.replace("javax.ejb.EJBException:","") ;
    	aStr = aStr.replace("org.mozilla.javascript.JavaScriptException:","") ;
    	aStr = aStr.replace("EJBException:; nested exception is:","") ;
        aStr = aStr.replace("ru.nuzmsh.ejb.exceptions.EJBExceptionCause:","") ;
        int cnt = aStr.indexOf("(/META-INF/formjs") ; 
        if (cnt>0) {
        	aStr = aStr.substring(0,cnt);
        }
        return aStr ;
    }

}
