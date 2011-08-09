package ru.ecom.web.servlet;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;

import ru.ecom.web.util.Injection;

public class ClearSessionListener implements HttpSessionListener {

	private final static Logger LOG = Logger
			.getLogger(ClearSessionListener.class);
	private final static boolean CAN_DEBUG = LOG.isDebugEnabled();
	
	public void sessionCreated(HttpSessionEvent aEvent) {
		
	}

	public void sessionDestroyed(HttpSessionEvent aEvent) {
		HttpSession session = aEvent.getSession() ;
		if(session!=null) {
			Injection.removeFromSession(session);
		}
	}
	

}
