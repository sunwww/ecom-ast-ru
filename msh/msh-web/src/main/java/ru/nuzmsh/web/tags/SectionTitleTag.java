package ru.nuzmsh.web.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 * Заголовок секции
 *
 * @jsp.tag  name="sectionTitle"
 *          display-name="Заголовок секции"
 *          body-content="scriptless"
 *          description="Заголовок секции"
 *
 */
public class SectionTitleTag extends AbstractGuidSimpleSupportTag {

    public void doTag() throws JspException, IOException {
        JspWriter out = getJspContext().getOut() ;
        printIdeStart("sectionTitle");
        out.print("<h2 class='section'>") ;
        getJspBody().invoke(out);
        out.println("</h2>")  ;
        printIdeEnd();
    }
}
