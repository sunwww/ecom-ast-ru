package ru.nuzmsh.web.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import ru.nuzmsh.web.tags.helper.JavaScriptContext;

/**
 * Вывод всех JavaScript
 *
 * @jsp.tag     name = "javascriptContextWrite"
 *      display-name = "Вывод всех JavaScript"
 *      body-content = "scriptless"
 *       description = "Вывод всех JavaScript"
 */
public class JavaScriptContextWriteTag extends SimpleTagSupport {

    public void doTag() throws JspException, IOException {
        JspWriter out = getJspContext().getOut() ;
        out.println("<!-- PRE javascript -->") ;
        out.println("<script type='text/javascript'>") ;
        JavaScriptContext.getContext((PageContext) getJspContext()
                , this).writeJavaScript(getJspContext().getOut());
        out.println("</script>") ;
    }

}
