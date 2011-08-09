package ru.ecom.poly.web.tags;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ru.ecom.poly.ejb.services.ITicketService;
import ru.ecom.web.tags.ParentEntityListAllTag;
import ru.ecom.web.util.Injection;

/**
 *
 */
public class ParentTicketListTag extends ParentEntityListAllTag {

    private final static Log LOG = LogFactory.getLog(ParentTicketListTag.class) ;

    public void doTag() throws JspException, IOException {
//        JspWriter out = getJspContext().getOut() ;
        PageContext ctx = (PageContext) getJspContext() ;
        HttpServletRequest request = (HttpServletRequest) ctx.getRequest() ;
        try {
            ITicketService service = Injection.find(request).getService(ITicketService.class);
            long id = Long.parseLong(request.getParameter("id")) ;
            Collection list = service.findActiveMedcardTickets(id) ;
            request.setAttribute(getAttribute(), list);
        } catch (Exception e) {
            LOG.error("Ошибка при получении значений", e) ;
            e.printStackTrace() ;
            throw new JspException("Ошибка при получении значений: "+e,e) ;
        }
    }

}
