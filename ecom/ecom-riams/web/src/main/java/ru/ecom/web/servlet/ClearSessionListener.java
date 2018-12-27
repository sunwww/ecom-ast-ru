package ru.ecom.web.servlet;

import ru.ecom.web.util.Injection;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class ClearSessionListener implements HttpSessionListener {

	public void sessionCreated(HttpSessionEvent aEvent) {
		
	}

	public void sessionDestroyed(HttpSessionEvent aEvent) {
		HttpSession session = aEvent.getSession() ;
		if(session!=null) {
			Injection.removeFromSession(session);
		}
	}
}