package ru.ecom.web.poly.actions.login;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.security.SecurityAssociation;

import ru.ecom.web.login.LoginInfo;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.messages.ErrorMessage;
import ru.nuzmsh.web.util.StringSafeEncode;

public class LoginFilter  implements Filter {

    private final static Log LOG = LogFactory.getLog(LoginFilter.class) ;
    private final static boolean CAN_TRACE = LOG.isTraceEnabled() ;


    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest aRequest, ServletResponse aResponse, FilterChain aChain) throws IOException, ServletException {

    	if(CAN_TRACE) {
			LOG.trace("LoginFilter.doingFilter ...[ hashCode()="+hashCode()+", currentThreadId="+Thread.currentThread().getId()+"]"); 
    	}
        HttpServletRequest request = (HttpServletRequest) aRequest ;
        String url = request.getRequestURI() ;
        boolean redirectToLogon ;
        if(! url.endsWith("mis_login.do") && !url.endsWith("mis_loginSave.do")) {
            HttpSession session = request.getSession() ;
            if(session!=null) {
                LoginInfo loginInfo = LoginInfo.find(session) ;
                if(loginInfo!=null) {
                	try {
                    	loginInfo.checkRolesExists() ;
                    	redirectToLogon = false ;
                	} catch (Exception e) {
                		redirectToLogon = true ;
                		LOG.warn("Ошибка входа:"+e.getMessage()) ;
                		new ErrorMessage(aRequest, e) ;
                	}
                } else {
                    LOG.warn("Нет LoginInfo в сессии.") ;
                    redirectToLogon = true ;
                }
            } else {
                LOG.warn("Нет сесcии.") ;
                redirectToLogon = true ;
            }
        } else {
            redirectToLogon = false ;
        }
        if(redirectToLogon) {
            HttpServletResponse response = (HttpServletResponse) aResponse ;
            Enumeration params = request.getParameterNames() ;
            StringBuilder sb = new StringBuilder();
            sb.append(request.getRequestURI()) ;
            sb.append("?") ;

            while (params.hasMoreElements()) {
                String param = (String) params.nextElement();
                sb.append(param) ;
                sb.append('=') ;
                sb.append(URLEncoder.encode(request.getParameter(param), "utf-8")) ;
                sb.append("&") ;
            }
            if (url.matches("(.*)dwr(.*)")) {
                response.sendRedirect("../../mis_loginSave.do?username=terminal&password=terminal&next="
                		//+ URLEncoder.encode(sb.toString(), "utf-8"));
                		+ theStringSafeEncode.encode(sb.toString()));
            	
            } else {
                response.sendRedirect("mis_loginSave.do?username=terminal&password=terminal&next="
                		//+ URLEncoder.encode(sb.toString(), "utf-8"));
                		+ theStringSafeEncode.encode(sb.toString()));
            }
        } else {
            aChain.doFilter(request, aResponse);
        }
        // очистка 
        clearSecurityAssociation() ;
        // очистка сервисов
        Injection.clearThreadLocalServices();
    }

    /**
     *  Очищаем org.jboss.security.SecurityAssociation
     */
    private void clearSecurityAssociation() {
        try {
        	if (CAN_TRACE) {
        		
				LOG.trace("Очищаем SecurityAssociation ...[ hashCode()="+hashCode()+", currentThreadId="+Thread.currentThread().getId()+"]"); 
        	}
        	SecurityAssociation.clear() ;
        } catch (Exception e) {
        	LOG.warn("Ошибка очистки SecurityAssociation", e) ;
        }
    }
    public void destroy() {

    }

    private final StringSafeEncode theStringSafeEncode = new StringSafeEncode() ;
}