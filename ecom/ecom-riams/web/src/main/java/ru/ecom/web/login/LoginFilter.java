package ru.ecom.web.login;

import org.apache.log4j.Logger;
import org.jboss.security.SecurityAssociation;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.messages.ErrorMessage;
import ru.nuzmsh.web.util.StringSafeEncode;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Enumeration;

/**
 * Безопасность
 */
public class LoginFilter implements Filter {

	private static final Logger LOG = Logger.getLogger(LoginFilter.class) ;
    private static final boolean CAN_TRACE = LOG.isDebugEnabled() ;


    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest aRequest, ServletResponse aResponse, FilterChain aChain) throws IOException, ServletException {

    	if(CAN_TRACE) {
			LOG.info("LoginFilter.doingFilter ...[ hashCode()="+hashCode()+", currentThreadId="+Thread.currentThread().getId()+"]");
    	}
        HttpServletRequest request = (HttpServletRequest) aRequest ;
        String url = request.getRequestURI() ;
        
        boolean redirectToLogon ;
        //System.out.println("-->url"+url) ;
        boolean isDwr = url.matches("(.*)/dwr/(.*)") ;
        if (isDwr) {
        	redirectToLogon=false ;
        } else {
	        if(! url.endsWith("ecom_login.do") && !url.endsWith("ecom_loginSave.do")) {
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
        }
        if(redirectToLogon) {
            HttpServletResponse response = (HttpServletResponse) aResponse ;
            Enumeration params = request.getParameterNames() ;
            StringBuilder sb = new StringBuilder();
            sb.append(request.getRequestURI()) ;
            sb.append("?") ;
            if (params!=null) {
            while (params.hasMoreElements()) {
	                String param = (String) params.nextElement();
	                sb.append(param) ;
	                sb.append('=') ;
	                sb.append(request.getParameter(param)!=null?URLEncoder.encode(request.getParameter(param), "utf-8"):"") ;
	                sb.append("&") ;
	            }
            }
            
            //
            if (sb.length()>1999) {
            	request.setAttribute("next", sb.toString()) ;
            	request.getRequestDispatcher("ecom_login.do").forward(request, response) ;
            } else {
            	response.sendRedirect("ecom_login.do?next="
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
        		
				LOG.info("Очищаем SecurityAssociation ...[ hashCode()="+hashCode()+", currentThreadId="+Thread.currentThread().getId()+"]");
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
