package ru.nuzmsh.web.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.struts.taglib.TagUtils;

/**
 * @jsp.tag name="buttonCancel"
 *              display-name="Кнопка отмены"
 *              body-content="empty"
 *              description="Кнопка отмены"
 * @author ESinev
 */
public class ButtonCancelTag extends TagSupport {

    /**
     * Надпись на кнопке
     * @jsp.attribute   description="Надпись на кнопке"
     *                  required="false"
     *                  rtexprvalue="true"
     */
    public void setLabel(String aLabel) { theLabel = aLabel ; }
    public String getLabel() { return theLabel ; }

    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        String label = theLabel==null ? "Отменить" : theLabel ;
        try {
            out.println(
                     "<input type='hidden' name='hiddenCancel' value='' />"
                  +  "<input type='button' id='defaultCancelButton' name='org.apache.struts.taglib.html.CANCEL' value='"+label+"'"
                  +      "onclick='this.form.hiddenCancel.name=this.name; this.form.submit()' />"
            );
        } catch (IOException e) {
            TagUtils.getInstance().saveException(pageContext, e);
            new JspException(e.getMessage(), e) ;
        }
        return EVAL_BODY_INCLUDE;
    }
    /** Надпись на кнопке */
    private String theLabel ;
}
