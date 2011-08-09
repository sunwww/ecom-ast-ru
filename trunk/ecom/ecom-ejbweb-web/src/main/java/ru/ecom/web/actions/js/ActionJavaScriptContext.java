package ru.ecom.web.actions.js;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.ejb.services.script.IScriptService;
import ru.ecom.web.util.Injection;

public class ActionJavaScriptContext {

	
	public ActionJavaScriptContext(ActionMapping aMapping, HttpServletRequest aRequest) {
		theRequest = aRequest ;
		theMapping = aMapping ;
	}
	
	public HttpSession getSession() {
		return theRequest.getSession(true);
	}
	
	/** Request */
	public HttpServletRequest getRequest() {
		return theRequest;
	}

	public void setRequest(HttpServletRequest aRequest) {
		theRequest = aRequest;
	}

	/** Request */
	private HttpServletRequest theRequest;
	
	/** ActionMapping */
	public ActionMapping getMapping() {
		return theMapping;
	}

	public ActionForward findForward(String aPath) {
		return theMapping.findForward(aPath);
	}
	
	public ActionForward createForward(String aPath) {
		ActionForward ret = new ActionForward(aPath, false) ;
		return ret ;
	}

	public ActionForward createForwardRedirect(String aPath) {
		ActionForward ret = new ActionForward(aPath, true) ;
		return ret ;
	}

	public Object getService(Class aClass)  {
		try {
			return Injection.find(theRequest).getService(aClass);
		} catch (NamingException e) {
			throw new IllegalStateException("Не найден сервис "+aClass+": "+e,e) ;
		}
	}
	
	
	private Object invokeScriptByArray(String aServiceName, String aMethod, Object[] args) {
		try {
			IScriptService service = Injection.find(theRequest).getService(IScriptService.class) ;
			return service.invoke(aServiceName, aMethod, args) ;
		} catch (NamingException e) {
			throw new RuntimeException(e) ;
		}
	}
	
	public Object invokeScript(String aServiceName, String aMethod, Object arg0, Object arg1, Object arg2, Object arg3, Object arg4) {
		return invokeScriptByArray(aServiceName, aMethod, new Object[] {arg0, arg1, arg2, arg3, arg4}) ;
	}
	public Object invokeScript(String aServiceName, String aMethod, Object arg0, Object arg1, Object arg2, Object arg3) {
		return invokeScriptByArray(aServiceName, aMethod, new Object[] {arg0, arg1, arg2, arg3}) ;
	}
	public Object invokeScript(String aServiceName, String aMethod, Object arg0, Object arg1, Object arg2) {
		return invokeScriptByArray(aServiceName, aMethod, new Object[] {arg0, arg1, arg2}) ;
	}
	public Object invokeScript(String aServiceName, String aMethod, Object arg0, Object arg1) {
		return invokeScriptByArray(aServiceName, aMethod, new Object[] {arg0, arg1}) ;
	}
	public Object invokeScript(String aServiceName, String aMethod, Object arg0) {
		return invokeScriptByArray(aServiceName, aMethod, new Object[] {arg0}) ;
	}
	public Object invokeScript(String aServiceName, String aMethod) {
		return invokeScriptByArray(aServiceName, aMethod, new Object[] {}) ;
	}
	
	/** ActionMapping */
	private final ActionMapping theMapping;
}
