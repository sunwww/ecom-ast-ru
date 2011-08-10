package ru.nuzmsh.web.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import ru.nuzmsh.web.tags.helper.JavaScriptContextLast;

/**
 * Вывод всех JavaScript
 *
 * @jsp.tag     name = "javascriptContextLastWrite"
 *      display-name = "Вывод всех JavaScript"
 *      body-content = "scriptless"
 *       description = "Вывод всех JavaScript"
 */
public class JavaScriptContextLastWriteTag extends SimpleTagSupport {

    public void doTag() throws JspException, IOException {
        JspWriter out = getJspContext().getOut() ;
        out.println("<!-- POST JAVASCRIPT -->") ;
        out.println("<script type='text/javascript'>") ;
        JavaScriptContextLast.getContext((PageContext) getJspContext()
                , this).writeJavaScript(getJspContext().getOut());
        out.println("</script>") ;
    }

}
