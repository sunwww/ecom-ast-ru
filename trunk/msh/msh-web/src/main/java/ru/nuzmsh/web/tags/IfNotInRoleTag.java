package ru.nuzmsh.web.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import ru.nuzmsh.util.StringUtil;
import ru.nuzmsh.web.tags.helper.RolesHelper;

/**
 * @jsp.tag           name = "ifNotInRole"
 *            display-name = "Если нет такой роли"
 *            body-content = "scriptless"
 *             description = "Если нет такой роли"
 */
public class IfNotInRoleTag extends AbstractGuidSimpleSupportTag{

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

        if(StringUtil.isNullOrEmpty(theRoles) || !RolesHelper.checkRoles(theRoles, getJspContext())) {
            getJspBody().invoke(out);
        } else {
            out.print("<!--") ;
            out.print("Есть политика: ") ;
            out.print(theRoles) ;
            out.println("-->") ;

        }
    	printIdeEnd();
    }

    /** Роли */
    private String theRoles ;

}
