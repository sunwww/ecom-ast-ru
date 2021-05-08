package ru.nuzmsh.web.tags;

import lombok.Getter;
import lombok.Setter;
import org.apache.struts.action.ActionErrors;
import ru.nuzmsh.web.tags.helper.JavaScriptContext;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.Iterator;

/**
 * @author ESinev
 * @jsp.tag name="setDefaultFocus"
 * display-name="Setting default forucs"
 * body-content="empty"
 * description="Setting default forucs"
 */
@Getter
@Setter
public class SetDefaultFocusTag extends TagSupport {

    /**
     * Идентификатор
     *
     * @jsp.attribute description="Идентификатор элемента"
     * required="true"
     * rtexprvalue="true"
     */
    private String id;

    public String findFirstIdError() {
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

    @Override
    public int doStartTag() throws JspException {
        String id = (String) pageContext.getRequest().getAttribute("firstErrorId");
        if (id == null) id = findFirstIdError();
        if (id == null) id = this.id;
        JavaScriptContext.getContext(pageContext, this).println(
                "    <!--\n" +
                        "    window.onload = function() {\n" +
                        "        document.getElementById('" + id + "').focus();\n" +
                        "    }\n" +
                        "    //-->\n"
        );
        return EVAL_BODY_INCLUDE;
    }
}