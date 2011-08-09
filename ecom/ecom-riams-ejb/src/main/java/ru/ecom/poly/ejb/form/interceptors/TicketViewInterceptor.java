package ru.ecom.poly.ejb.form.interceptors;

import org.apache.log4j.Logger;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.poly.ejb.form.TicketForm;

public class TicketViewInterceptor implements IFormInterceptor {

	   private final static Logger LOG = Logger.getLogger(TicketViewInterceptor.class);
	    private final static boolean CAN_DEBUG = LOG.isDebugEnabled();
	public void intercept(IEntityForm aForm, Object aEntity, InterceptorContext aContext) {
		TicketForm form=(TicketForm)aForm ;
        if (!aContext.getSessionContext().isCallerInRole("/Policy/Poly/Ticket/Reopen") 
        		&& form.getIsTicketClosed()!=null 
        		&& form.getIsTicketClosed()==true) {
            if (CAN_DEBUG) LOG.debug("Можно открыть талон...") ;
            form.addDisabledField("isTicketClosed");
        }
	}
}
