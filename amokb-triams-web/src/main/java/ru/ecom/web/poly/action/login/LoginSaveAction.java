package ru.ecom.web.poly.action.login;

import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.ejb.EJBAccessException;
import javax.naming.CommunicationException;
import javax.security.auth.login.FailedLoginException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.ejb.services.login.ILoginService;
import ru.ecom.web.login.LoginErrorMessage;
import ru.ecom.web.login.LoginForm;

import ru.ecom.web.login.LoginInfo;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.util.StringUtil;
import ru.nuzmsh.web.util.StringSafeEncode;

public class LoginSaveAction extends LoginExitAction {

    private final static Log LOG = LogFactory.getLog(LoginSaveAction.class) ;
    private final static boolean CAN_TRACE = LOG.isTraceEnabled() ;


    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
        super.myExecute(aMapping, aForm, aRequest, aResponse) ;
        LoginForm form = (LoginForm) aForm ;

        //String password = getHashPassword(form.getUsername(), form.getPassword()) ;
        String password = form.getPassword() ;
        LoginInfo loginInfo = new LoginInfo(form.getUsername(), password);
        
        HttpSession session = aRequest.getSession(true) ;
        loginInfo.saveTo(session) ;

        if (CAN_TRACE) LOG.trace("Сохранение в сесии " + loginInfo.getUsername());
        try {

            ILoginService service = Injection.find(aRequest).getService(ILoginService.class) ;
            if(service==null) throw new IllegalStateException("Невозможно получить сервис "+ILoginService.class.getSimpleName()) ;

            logLoginUserInvironment(aRequest) ;
            
            Set<String> roles = service.getUserRoles() ;
            
            if(roles==null) throw new NullPointerException("Нет ролей у пользователя roles==null") ;
            loginInfo.setUserRoles(service.getUserRoles());

        } catch (Exception e) {
            LOG.error("Ошибка при входе: "+getErrorMessage(e),e);
            e.printStackTrace() ;
            LoginErrorMessage.setMessage(aRequest, getErrorMessage(e));
            return aMapping.getInputForward() ;
        }
        if(StringUtil.isNullOrEmpty(form.getNext())) {
            return aMapping.findForward("success") ;
        } else {
            String next = form.getNext() ; //.substring(form.getNext().indexOf('/',2)) ;
            try {
                LOG.debug("next(1) = "+next) ;
                next = new StringSafeEncode().decode(next);
                LOG.debug("next(2) = "+next) ;
                next = next.substring(next.indexOf('/',2)) ;
                LOG.debug("next(3) = "+next) ;
            } catch (Exception e) {
            	LOG.warn("next в URLEncode: "+next, e);
            	next = form.getNext().substring(form.getNext().indexOf('/',2)) ;
                LOG.debug("next(4) = "+next) ;
            }
            
//            if(next.indexOf('?')>0) {
//                String path  = next.substring(0,next.indexOf('?'));
//                String param = next.substring(next.indexOf('?')+1) ;
//                next = path + URLEncoder.encode(param) ;
//            }
            
            return new ActionForward(next,true) ;
        }
    }


    public static String getHashPassword(String aUsername, String aPassword) {
    	String hash = String.valueOf(aPassword.hashCode() + aUsername.hashCode()) ;
    	//System.out.println(hash) ;
    	return hash;
    }

	private String getErrorMessage(Throwable aException) {
        if(aException instanceof FailedLoginException || aException instanceof EJBAccessException) {
            return "Неправильное имя пользователя или неверный пароль" ;
        } else if (aException instanceof CommunicationException){
            return "Ошибка подключения к серверу приложений" ;
        } else {
            if(aException.getCause()!=null) {
                 return getErrorMessage(aException.getCause()) ;
            } else {
                return aException.getMessage() ;
            }
        }
    }

	private void logLoginUserInvironment(HttpServletRequest aRequest) {
        LOG.info("Login user env : {") ;
        for(Map.Entry entry : createUserEnvironmentInfo(aRequest).entrySet() ) {
        	LOG.info("    "+entry.getKey()+" = "+entry.getValue()) ;
        }
        LOG.info("}") ;
		
	}

    private Properties createUserEnvironmentInfo(HttpServletRequest aRequest) {
    	Properties prop = new Properties() ;
    	Enumeration headers = aRequest.getHeaderNames();
    	while(headers.hasMoreElements()) {
    		String header = (String) headers.nextElement() ;
    		prop.setProperty(header, aRequest.getHeader(header));
    	}
    	return prop ;
    }
}