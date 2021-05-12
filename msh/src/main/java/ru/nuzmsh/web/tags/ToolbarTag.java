package ru.nuzmsh.web.tags;

import ru.nuzmsh.util.StringUtil;
import ru.nuzmsh.web.tags.helper.RolesHelper;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import java.io.IOException;

/**
 * @jsp.tag name="toolbar"
 * display-name="Toolbar Element"
 * body-content="scriptless"
 * description="Toolbar Element"
 */
public class ToolbarTag extends AbstractGuidSimpleSupportTag {

    /**
     * Политики безопасности, разделенный ;
     *
     * @jsp.attribute description="Политики безопасности, разделенный ;"
     * required="false"
     * rtexprvalue="true"
     */
    public String getRoles() {
        return theRoles;
    }

    public void setRoles(String aRoles) {
        theRoles = aRoles;
    }

    @Override
    public void doTag() throws JspException, IOException {
        printIdeStart();
        JspWriter out = getJspContext().getOut();

        if (StringUtil.isNullOrEmpty(theRoles) || RolesHelper.checkRoles(theRoles, getJspContext())) {
            out.println("<div class='linkButtons'>");
            getJspBody().invoke(out);
            out.println("</div>");
        } else {
            out.print("<!--");
            out.print("Нет политик: ");
            out.print(theRoles);
            out.println("-->");
        }
        printIdeEnd();
    }

    /**
     * Политики безопасности, разделенный ;
     */
    private String theRoles;


}
