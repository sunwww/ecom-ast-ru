package ru.nuzmsh.web.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import ru.nuzmsh.web.util.IdeTagHelper;

/**
 * @jsp.tag name="separator"
 *          display-name="Separator"
 *          body-content="scriptless"
 *          description="separator"
 */
public class SeparatorTag extends AbstractGuidSimpleSupportTag {

    /**
     * Количество столбцов
     * @jsp.attribute   description="Количество столбцов"
     *                  required="true"
     *                  rtexprvalue="true"
     */
    public int getColSpan() { return theColSpan ; }
    public void setColSpan(int aColSpan) { theColSpan = aColSpan ; }

    /**
     * Текст
     * @jsp.attribute   description="Текст"
     *                  required="true"
     *                  rtexprvalue="true"
     */
    public String getLabel() { return theLabel ; }
    public void setLabel(String aLabel) { theLabel = aLabel ; }

    public void doTag() throws JspException, IOException {
        JspWriter out = getJspContext().getOut() ;
        out.println("<td class='separator' colspan='"+getColSpan()+"'>") ;

        // IDE
        IdeTagHelper.getInstance().printMarker("Separator", this, getJspContext());
        
        out.println("<div class='h3'><h3>"+getLabel()+"</h3></div></td>") ;
        
//        out.println(
//                String.format("<td class='separator' colspan='%1$s'>"
//                        +"<div class='h3'><h3>%2$s</h3></div>"
//                +"</td>", getColSpan()+"", getLabel())
//        ) ;
    }

    /** Текст */
    private String theLabel ;
    /** Количество столбцов */
    private int theColSpan ;
}
