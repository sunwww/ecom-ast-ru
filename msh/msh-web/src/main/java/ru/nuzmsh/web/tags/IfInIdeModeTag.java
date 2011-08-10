package ru.nuzmsh.web.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import ru.nuzmsh.web.util.IdeTagHelper;

/**
 * @jsp.tag           name = "ifInIdeMode"
 *            display-name = "В режиме IDE?"
 *            body-content = "scriptless"
 *             description = "В режиме IDE?"
 */
public class IfInIdeModeTag extends SimpleTagSupport {

    public void doTag() throws JspException, IOException {

    	if(IdeTagHelper.getInstance().isInIdeMode(getJspContext())) {
            getJspBody().invoke(getJspContext().getOut());
    	}
    }
	 
}
