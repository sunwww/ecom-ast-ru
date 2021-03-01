package ru.nuzmsh.web.filter.caching;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

public class RewritingHttpServletRequest implements HttpServletRequest {

	private static final Logger LOG = Logger.getLogger(RewritingHttpServletRequest.class);
	private static final boolean CAN_TRACE = LOG.isDebugEnabled();
	
	
	public RewritingHttpServletRequest(HttpServletRequest aRequest) {
		theRequest = aRequest ;
		
	}

	private static String rewrite(String aStr) {
		if(aStr==null) return aStr ;
		int pos = aStr.indexOf("-CA") ;
		String ret ;
		if(pos>0) {
			int to = aStr.lastIndexOf('.') ;
			ret = aStr.substring(0, pos) + aStr.substring(to) ;
		} else {
			ret = aStr;
		}
		if(CAN_TRACE) LOG.info("Translated to "+ret) ;
		return ret ;
	}

	public String getPathInfo() {
		return rewrite(theRequest.getPathInfo()) ;
	}

	public String getPathTranslated() {
		return rewrite(theRequest.getPathTranslated()) ;
	}

	public String getQueryString() {
		return rewrite(theRequest.getQueryString()) ;
	}

	public String getRequestURI() {
		return rewrite(theRequest.getRequestURI()) ;
	}

	public StringBuffer getRequestURL() {
		String url = rewrite(theRequest.getRequestURL().toString()) ;
		return new StringBuffer(url) ;
	}


///////////////////

	public String getAuthType() {
		return theRequest.getAuthType() ;
	}

	public String getContextPath() {
		return theRequest.getContentType() ;
	}

	public Cookie[] getCookies() {
		return theRequest.getCookies() ;
	}

	public long getDateHeader(String arg0) {
		return theRequest.getDateHeader(arg0) ;
	}

	public String getHeader(String arg0) {
		return theRequest.getHeader(arg0) ;
	}

	public Enumeration getHeaderNames() {
		return theRequest.getHeaderNames() ;
	}

	public Enumeration getHeaders(String arg0) {
		return theRequest.getHeaders(arg0) ;
	}

	public int getIntHeader(String arg0) {
		return theRequest.getIntHeader(arg0) ;
	}

	public String getMethod() {
		return theRequest.getMethod() ;
	}

	public String getRemoteUser() {
		return theRequest.getRemoteUser() ;
	}

	public String getRequestedSessionId() {
		return theRequest.getRequestedSessionId() ;
	}

	public String getServletPath() {
		return theRequest.getServletPath() ;
	}

	public HttpSession getSession() {
		return theRequest.getSession();
	}

	public String changeSessionId() {
		return null;
	}

	public HttpSession getSession(boolean arg0) {
		return theRequest.getSession(arg0) ;
	}

	public Principal getUserPrincipal() {
		return theRequest.getUserPrincipal();
	}

	public boolean isRequestedSessionIdFromCookie() {
		return theRequest.isRequestedSessionIdFromCookie() ;
	}

	public boolean isRequestedSessionIdFromUrl() {
		return theRequest.isRequestedSessionIdFromUrl() ;
	}

	public boolean authenticate(HttpServletResponse httpServletResponse) throws IOException, ServletException {
		return false;
	}

	public void login(String s, String s1) throws ServletException {

	}

	public void logout() throws ServletException {

	}

	public Collection<Part> getParts() throws IOException, ServletException {
		return null;
	}

	public Part getPart(String s) throws IOException, ServletException {
		return null;
	}


	public boolean isRequestedSessionIdFromURL() {
		return theRequest.isRequestedSessionIdFromURL() ;
	}

	public boolean isRequestedSessionIdValid() {
		return theRequest.isRequestedSessionIdValid() ;
	}

	public boolean isUserInRole(String arg0) {
		return theRequest.isUserInRole(arg0) ;
	}

	public Object getAttribute(String arg0) {
		return theRequest.getAttribute(arg0) ;
	}

	public Enumeration getAttributeNames() {
		return theRequest.getAttributeNames() ;
	}

	public String getCharacterEncoding() {
		return theRequest.getCharacterEncoding();
	}

	public int getContentLength() {
		return theRequest.getContentLength() ;
	}

	public long getContentLengthLong() {
		return 0;
	}

	public String getContentType() {
		return theRequest.getContentType() ;
	}

	public ServletInputStream getInputStream() throws IOException {
		return theRequest.getInputStream() ;
	}

	public String getLocalAddr() {
		return theRequest.getLocalAddr() ;
	}

	public String getLocalName() {
		return theRequest.getLocalName() ;
	}

	public int getLocalPort() {
		return theRequest.getLocalPort() ;
	}

	public ServletContext getServletContext() {
		return null;
	}

	public AsyncContext startAsync() throws IllegalStateException {
		return null;
	}

	public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse) throws IllegalStateException {
		return null;
	}

	public boolean isAsyncStarted() {
		return false;
	}

	public boolean isAsyncSupported() {
		return false;
	}

	public AsyncContext getAsyncContext() {
		return null;
	}

	public DispatcherType getDispatcherType() {
		return null;
	}

	public Locale getLocale() {
		return theRequest.getLocale() ;
	}

	public Enumeration getLocales() {
		return theRequest.getLocales() ;
	}

	public String getParameter(String arg0) {
		return theRequest.getParameter(arg0) ;
	}

	public Map getParameterMap() {
		return theRequest.getParameterMap() ;
	}

	public Enumeration getParameterNames() {
		return theRequest.getParameterNames() ;
	}

	public String[] getParameterValues(String arg0) {
		return theRequest.getParameterValues(arg0) ;
	}

	public String getProtocol() {
		return theRequest.getProtocol() ;
	}

	public BufferedReader getReader() throws IOException {
		return theRequest.getReader() ;
	}

	public String getRealPath(String arg0) {
		return theRequest.getRealPath(arg0) ;
	}

	public String getRemoteAddr() {
		return theRequest.getRemoteAddr() ;
	}

	public String getRemoteHost() {
		return theRequest.getRemoteHost() ;
	}

	public int getRemotePort() {
		return theRequest.getRemotePort() ;
	}

	public RequestDispatcher getRequestDispatcher(String arg0) {
 		return theRequest.getRequestDispatcher(arg0) ;
	}

	public String getScheme() {
		return theRequest.getScheme() ;
	}

	public String getServerName() {
		return theRequest.getServerName() ;
	}

	public int getServerPort() {
		return theRequest.getServerPort();
	}

	public boolean isSecure() {
		return theRequest.isSecure();
	}

	public void removeAttribute(String arg0) {
		theRequest.removeAttribute(arg0) ;
	}

	public void setAttribute(String arg0, Object arg1) {
		theRequest.setAttribute(arg0, arg1);
	}

	public void setCharacterEncoding(String arg0) throws UnsupportedEncodingException {
		theRequest.setCharacterEncoding(arg0) ;
	}

	private final HttpServletRequest theRequest ;

}
