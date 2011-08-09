package ru.ecom.template.web.dwr;

import java.math.BigInteger;
import java.util.HashMap;

import ru.nuzmsh.util.StringUtil;
import ru.ecom.web.login.LoginInfo;
import ru.ecom.web.util.Injection;
import ru.ecom.diary.ejb.service.protocol.IDiaryService;
import ru.ecom.diary.ejb.service.template.ITemplateProtocolService;
import ru.ecom.diary.web.action.protocol.template.TemplateSaveAction;
import ru.ecom.ejb.services.script.IScriptService;

import javax.servlet.http.HttpServletRequest;
import javax.naming.NamingException;

/**
 * Created by IntelliJ IDEA.
 * User: STkacheva
 * Date: 25.12.2006
 * Time: 17:27:00
 * To change this template use File | Settings | File Templates.
 */
public class TemplateProtocolJs {
    public String getText(String aId, HttpServletRequest aRequest) throws NamingException {
        if (StringUtil.isNullOrEmpty(aId)) {
            return "" ;
        } else {
            ITemplateProtocolService service = Injection.find(aRequest).getService(ITemplateProtocolService.class) ;
            return service.getTextTemplate(Long.valueOf(aId)) ;
        }
    }
    public String getPreviousText(String aId, HttpServletRequest aRequest) throws NamingException {
        if (StringUtil.isNullOrEmpty(aId)) {
            return "" ;
        } else {
            ITemplateProtocolService service = Injection.find(aRequest).getService(ITemplateProtocolService.class) ;
            return service.getTextByProtocol(Long.valueOf(aId)) ;
        }
    }
    public void saveParametersByMedService(long aProtocol, String[] aAdds, String[] aRemoves, HttpServletRequest aRequest) throws NamingException {
    	long[] adds = TemplateSaveAction.getLongs(aAdds);
    	long[] removes = TemplateSaveAction.getLongs(aRemoves);
    	IDiaryService service = (IDiaryService) Injection.find(aRequest).getService("DiaryService");
    	service.saveParametersByTemplateProtocol(aProtocol, adds, removes) ;
    }
    public Long getCountSymbolsInProtocol(long aVisit,  HttpServletRequest aRequest) throws NamingException {
    	 ITemplateProtocolService service = Injection.find(aRequest).getService(ITemplateProtocolService.class) ;
    	 return service.getCountSymbolsInProtocol(aVisit) ;
    	
    }
    public static String getUsername(HttpServletRequest aRequest) {
    	 LoginInfo loginInfo = LoginInfo.find(aRequest.getSession()) ;
    	return loginInfo!=null?loginInfo.getUsername():"" ;
    }
    public boolean isCanEditProtocol(Long aIdProt, String aUserCreate, HttpServletRequest aRequest) throws NamingException {
    	if (getUsername(aRequest).equals(aUserCreate)) {
    		return true ;
    	}
    	IScriptService service = (IScriptService)Injection.find(aRequest).getService("ScriptService") ;
    	
    	HashMap param = new HashMap() ;
		param.put("obj","Protocol") ;
		param.put("permission" ,"editOtherUser") ;
		param.put("id", aIdProt) ;
		
    	Object res = service.invoke("WorkerService", "checkPermission", new Object[]{param});
    	//System.out.println("res="+res) ;
    	long res1 =  parseLong(res);
    	if (res1==0) {
			 //return false;
		} else {
			return true ;
		}
    	return false ;
    }
	public static Long parseLong(Object aValue) {
		Long ret =null;
		if (aValue==null) return ret ;
		if (aValue instanceof Integer) {
			
			return Long.valueOf((Integer) aValue) ;
		}
		if(aValue instanceof BigInteger) {
			BigInteger bigint = (BigInteger) aValue ;
			
			return bigint!=null?bigint.longValue() : null;
		} 
		if (aValue instanceof Number) {
			Number number = (Number) aValue ;
			return number!=null?number.longValue() : null ;
		}
		if (aValue instanceof String) {
			return Long.valueOf((String) aValue);
		}
		return ret ;
	}
}
