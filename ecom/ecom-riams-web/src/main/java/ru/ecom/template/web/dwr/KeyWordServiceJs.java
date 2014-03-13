package ru.ecom.template.web.dwr;

import ru.nuzmsh.util.StringUtil;
import ru.ecom.diary.ejb.service.template.IKeyWordService;
import ru.ecom.web.util.Injection;

import javax.servlet.http.HttpServletRequest;
import javax.naming.NamingException;

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
        	
            //System.out.println("-"+aReduction+"-") ;
        	IKeyWordService service = Injection.find(aRequest).getService(IKeyWordService.class) ;
        	String ret = service.getDecryption(aReduction) ;
        	///System.out.println("-"+ret+"-") ;
            return ret ;
        }
    }

    
}
