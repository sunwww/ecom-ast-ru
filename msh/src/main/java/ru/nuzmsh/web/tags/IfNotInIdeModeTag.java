package ru.nuzmsh.web.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import ru.nuzmsh.web.util.IdeTagHelper;

/**
 * @jsp.tag           name = "ifNotInIdeMode"
 *            display-name = "Не в режиме IDE?"
 *            body-content = "scriptless"
 *             description = "Не в режиме IDE?"
 */
public class IfNotInIdeModeTag extends SimpleTagSupport {

    public void doTag() throws JspException, IOException {

    	if(!IdeTagHelper.getInstance().isInIdeMode(getJspContext())) {
            getJspBody().invoke(getJspContext().getOut());
    	}
    }
	 
}
