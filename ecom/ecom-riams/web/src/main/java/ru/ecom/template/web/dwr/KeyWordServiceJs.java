package ru.ecom.template.web.dwr;

import ru.ecom.diary.ejb.service.template.IKeyWordService;
import ru.ecom.web.login.LoginInfo;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.util.StringUtil;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by IntelliJ IDEA.
 * User: STkacheva
 * Date: 22.12.2006
 * Time: 12:22:20
 * To change this template use File | Settings | File Templates.
 */
public class KeyWordServiceJs {
    public String getDecryption(String aReduction, HttpServletRequest aRequest) throws NamingException {
        if (aReduction!=null) aReduction = aReduction.trim() ;
    	if (StringUtil.isNullOrEmpty(aReduction)) {
            return "" ;
        } else {
        	String login = LoginInfo.find(aRequest.getSession(true)).getUsername() ;
        	IKeyWordService service = Injection.find(aRequest).getService(IKeyWordService.class) ;
            return service.getDecryption(aReduction,login) ;
        }
    }

    
}
