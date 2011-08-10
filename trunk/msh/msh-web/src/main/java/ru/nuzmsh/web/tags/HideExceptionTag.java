package ru.nuzmsh.web.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @jsp.tag name="hideException"
 *          display-name="hideException"
 *          body-content="scriptless"
 *          description="hideException"
 */
public class HideExceptionTag extends SimpleTagSupport {

    private final static Log LOG = LogFactory.getLog(HideExceptionTag.class) ;
    private final static boolean CAN_TRACE = LOG.isTraceEnabled() ;


    public void doTag() throws JspException, IOException {
        JspWriter out = getJspContext().getOut() ;
        try {
            getJspBody().invoke(out);
        } catch (Exception e) {
            if(CAN_TRACE) LOG.trace("Ошибка",e) ;
            out.print("<div class='error'>") ;
            out.print(e.getMessage()) ;
            out.print("</div>") ;
        }
    }


}
