package ru.ecom.expomc.web.tags;

import ru.ecom.expomc.web.actions.checkproperty.CheckPropertyListAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;


public class CheckPropertyListTag extends SimpleTagSupport {

    public void doTag() throws JspException, IOException {
        PageContext ctx = (PageContext) getJspContext();
        HttpServletRequest request = (HttpServletRequest) ctx.getRequest();
        try {
            CheckPropertyListAction.list(request);
        } catch (Exception e) {
            throw new JspException(e);
        }
    }
}
