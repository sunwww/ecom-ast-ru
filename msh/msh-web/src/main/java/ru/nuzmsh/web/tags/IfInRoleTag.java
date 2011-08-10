package ru.nuzmsh.web.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import ru.nuzmsh.util.StringUtil;
import ru.nuzmsh.web.tags.helper.RolesHelper;

/**
 * @jsp.tag           name = "ifInRole"
 *            display-name = "Есть ли такие роли"
 *            body-content = "scriptless"
 *             description = "Есть ли такие роли"
 */
public class IfInRoleTag extends AbstractGuidSimpleSupportTag {

    /**
     * Роли
     * @jsp.attribute   description = "Роли"
     *                     required = "true"
     *                  rtexprvalue = "true"
     */
    public String getRoles() { return theRoles ; }
    public void setRoles(String aRoles) { theRoles = aRoles ; }

    public void doTag() throws JspException, IOException {
    	printIdeStart("ifInRole");
    	
        JspWriter out = getJspContext().getOut() ;

        if(StringUtil.isNullOrEmpty(theRoles) || RolesHelper.checkRoles(theRoles, getJspContext())) {
            getJspBody().invoke(out);
        } else {
            out.print("<!--") ;
            out.print("Нет политик: ") ;
            out.print(theRoles) ;
            out.println("-->") ;

        }
    	printIdeEnd();
    }

    /** Роли */
    private String theRoles ;

}
