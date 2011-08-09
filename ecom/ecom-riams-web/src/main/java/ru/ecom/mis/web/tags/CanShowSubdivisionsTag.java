package ru.ecom.mis.web.tags;

import ru.ecom.mis.ejb.service.lpu.ILpuService;
import ru.ecom.web.util.Injection;

import javax.servlet.jsp.tagext.SimpleTagSupport;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.http.HttpServletRequest;
import javax.naming.NamingException;
import java.io.IOException;

/**
 *
 */
public class CanShowSubdivisionsTag extends SimpleTagSupport {

    public void doTag() throws JspException, IOException {
        PageContext ctx = (PageContext) getJspContext() ;
        HttpServletRequest request = (HttpServletRequest) ctx.getRequest() ;
        try {
            ILpuService service = Injection.find(request).getService(ILpuService.class) ;
            if(service.canShowSubdivisions(Long.parseLong(request.getParameter("id")))) {
                getJspBody().invoke(getJspContext().getOut());
            }
        } catch (NamingException e) {
            throw new JspException(e) ;
        }
    }
}
