package ru.nuzmsh.web.tags;

import lombok.Getter;
import lombok.Setter;
import ru.nuzmsh.web.util.IdeTagHelper;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import java.io.IOException;

/**
 * @jsp.tag name="separator"
 * display-name="Separator"
 * body-content="scriptless"
 * description="separator"
 */
@Getter
@Setter
public class SeparatorTag extends AbstractGuidSimpleSupportTag {

    /**
     * Количество столбцов
     *
     * @jsp.attribute description="Количество столбцов"
     * required="true"
     * rtexprvalue="true"
     */
    private int colSpan;
    /**
     * Текст
     *
     * @jsp.attribute description="Текст"
     * required="true"
     * rtexprvalue="true"
     */
    private String label;

    @Override
    public void doTag() throws JspException, IOException {
        JspWriter out = getJspContext().getOut();
        out.println("<td class='separator' colspan='" + getColSpan() + "'>");
        IdeTagHelper.getInstance().printMarker("Separator", this, getJspContext());
        out.println("<div class='h3'><h3>" + getLabel() + "</h3></div></td>");
    }
}
