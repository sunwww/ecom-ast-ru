package ru.nuzmsh.web.tags;

import ru.nuzmsh.web.util.IdeTagHelper;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import java.io.IOException;

/**
 * Содержимое секции
 *
 * @jsp.tag name="sectionContent"
 * display-name="Содержимое секции"
 * body-content="scriptless"
 * description="Содержимое секции"
 */
public class SectionContentTag extends AbstractGuidSimpleSupportTag {

    @Override
    public void doTag() throws JspException, IOException {
        JspWriter out = getJspContext().getOut();
        out.print("<div class='sectionContent'>");

        // IDE MODE
        IdeTagHelper.getInstance().printMarker("SectionContent", this, getJspContext());

        getJspBody().invoke(out);
        out.println("</div>");
    }
}
