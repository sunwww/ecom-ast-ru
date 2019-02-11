package ru.ecom.web.tags;

import ru.ecom.web.login.LoginInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

/**
 * Имя пользователя
 */
public class LoginNameTag extends SimpleTagSupport {
    public void doTag() throws JspException, IOException {
        JspWriter out = getJspContext().getOut() ;
        PageContext ctx = (PageContext) getJspContext() ;
        HttpServletRequest request = (HttpServletRequest) ctx.getRequest() ;
        LoginInfo loginInfo = LoginInfo.find(request.getSession()) ;
        out.print(loginInfo!=null ? loginInfo.getUsername() : "нет пользователя!") ;
    }
}
