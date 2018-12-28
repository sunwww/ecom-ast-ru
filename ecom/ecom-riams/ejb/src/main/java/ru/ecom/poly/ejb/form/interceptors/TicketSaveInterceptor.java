package ru.ecom.poly.ejb.form.interceptors;

import org.apache.log4j.Logger;
import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.poly.ejb.domain.Ticket;
import ru.ecom.poly.ejb.form.TicketForm;

public class TicketSaveInterceptor implements IFormInterceptor {

    private static final Logger LOG = Logger.getLogger(TicketSaveInterceptor.class);
    private static final boolean CAN_DEBUG = LOG.isDebugEnabled();


	public void intercept(IEntityForm aForm, Object aEntity, InterceptorContext aContext) {
		TicketForm form = (TicketForm)aForm ;
		Ticket ticket = (Ticket)aEntity ;
		if (ticket.getStatus()!=Ticket.STATUS_CLOSED && form.getIsTicketClosed()!=null && form.getIsTicketClosed()==true) {
			if (CAN_DEBUG) LOG.debug("Закрытие талона...") ;
			ticket.setStatus(Ticket.STATUS_CLOSED) ;
		} else {
			if (aContext.getSessionContext().isCallerInRole("/Policy/Poly/Ticket/Reopen")
					&& (form.getIsTicketClosed()==null || form.getIsTicketClosed()==false)		
			) {
				if (CAN_DEBUG) LOG.debug("Открытие талона...") ;
				ticket.setStatus(Ticket.STATUS_INPROCESS) ;
			}
		}
	}
}
