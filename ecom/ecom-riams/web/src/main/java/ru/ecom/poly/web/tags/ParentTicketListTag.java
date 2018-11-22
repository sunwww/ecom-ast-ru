package ru.ecom.poly.web.tags;

import org.apache.log4j.Logger;
import ru.ecom.poly.ejb.services.ITicketService;
import ru.ecom.web.tags.ParentEntityListAllTag;
import ru.ecom.web.util.Injection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import java.util.Collection;

/**
 *
 */
public class ParentTicketListTag extends ParentEntityListAllTag {

    private final static Logger LOG = Logger.getLogger(ParentTicketListTag.class) ;

    public void doTag() throws JspException {
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
