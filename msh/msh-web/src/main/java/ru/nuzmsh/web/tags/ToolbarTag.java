package ru.nuzmsh.web.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import ru.nuzmsh.util.StringUtil;
import ru.nuzmsh.web.tags.helper.RolesHelper;

/**
 * @jsp.tag name="toolbar"
 *          display-name="Toolbar Element"
 *          body-content="scriptless"
 *          description="Toolbar Element"
 */
public class ToolbarTag extends AbstractGuidSimpleSupportTag {

    /**
     * Политики безопасности, разделенный ;
     * @jsp.attribute   description="Политики безопасности, разделенный ;"
     *                  required="false"
     *                  rtexprvalue="true"
     */
    public String getRoles() { return theRoles ; }
    public void setRoles(String aRoles) { theRoles = aRoles ; }


    public void doTag() throws JspException, IOException {
        printIdeStart();
    	JspWriter out = getJspContext().getOut() ;
        PageContext ctx = (PageContext) getJspContext() ;

        if(StringUtil.isNullOrEmpty(theRoles) || RolesHelper.checkRoles(theRoles, getJspContext())) {
//            out.println("<table class='linkButtons'><tr>") ;
            out.println("<div class='linkButtons'>") ;
            getJspBody().invoke(out);
            out.println("</div>") ;
//            out.println("</tr></table>") ;
        } else {
            out.print("<!--") ;
            out.print("Нет политик: ") ;
            out.print(theRoles) ;
            out.println("-->") ;
        }
        printIdeEnd();
    }

    /** Политики безопасности, разделенный ; */
    private String theRoles ;


}
