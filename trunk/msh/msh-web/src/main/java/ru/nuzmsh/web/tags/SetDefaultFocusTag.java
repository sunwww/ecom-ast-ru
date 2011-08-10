package ru.nuzmsh.web.tags;

import java.util.Iterator;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.struts.action.ActionErrors;

import ru.nuzmsh.web.tags.helper.JavaScriptContext;

/**
 * @author ESinev
 * @jsp.tag name="setDefaultFocus"
 * display-name="Setting default forucs"
 * body-content="empty"
 * description="Setting default forucs"
 */
public class SetDefaultFocusTag extends TagSupport {

    /**
     * Идентификатор
     *
     * @jsp.attribute description="Идентификатор элемента"
     * required="true"
     * rtexprvalue="true"
     */
    public void setId(String aId) {
        theId = aId;
    }

    public String getId() {
        return theId;
    }

    public String findFirstIdError() throws JspException {
        String id = null;
        ActionErrors errors = (ActionErrors) pageContext.getRequest().getAttribute("org.apache.struts.action.ERROR");
        if (errors != null && !errors.isEmpty()) {
            Iterator iterator = errors.properties();
            while (iterator.hasNext()) {
                id = (String) iterator.next();
            }
        }
        return id;
    }


    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        String id = (String) pageContext.getRequest().getAttribute("firstErrorId");
        if (id == null) id = findFirstIdError();
        if (id == null) id = theId;
        JavaScriptContext.getContext(pageContext, this).println(
                        "    <!--\n" +
                        "    window.onload = function() {\n" +
                        "        document.getElementById('" + id + "').focus();\n" +
                        "    }\n" +
                        "    //-->\n"
        );
        return EVAL_BODY_INCLUDE;
    }

    /**
     * Идентификатор
     */
    private String theId;
}