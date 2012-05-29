package ru.ecom.web.print;

import java.util.Enumeration;
import java.util.TreeMap;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.ejb.print.IPrintService;
import ru.ecom.web.login.LoginInfo;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.messages.InfoMessage;
import ru.nuzmsh.web.struts.BaseAction;

public class PrintAction extends BaseAction {

	@Override
	public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
		String reportKey = aMapping.getParameter() ;
		IPrintService service = Injection.find(aRequest).getService(IPrintService.class);
        TreeMap<String, String> map = new TreeMap<String, String>();
        Enumeration en = aRequest.getParameterNames() ;
        boolean isMultyId = !(aRequest.getParameter("multy")==null?true:aRequest.getParameter("multy").equals("")) ;
        //System.out.println("multy="+isMultyId) ;
        //aRequest.getParameterValues("id").length ;
        String login = LoginInfo.find(aRequest.getSession(true)).getUsername() ; 
        while (en.hasMoreElements()) {
        	String key = (String) en.nextElement();
            if (key.equals("id") && isMultyId) {
            	map.put(key, convertToString(aRequest.getParameterValues(key))) ;
            } else {
            	map.put(key, aRequest.getParameter(key)) ;
            }
            
//            System.out.println("key = " + key);
//            System.out.println("aRequest.getParameter(key) = " + aRequest.getParameter(key));
        }
        String filename = service.print(login,reportKey
        		, aRequest.getParameter("s")
        		, aRequest.getParameter("m"), map) ;
        String next = aRequest.getParameter("next") ;
        if (next!=null && !next.equals("")) {
        	
        	new InfoMessage(aRequest, "Документ отправлен в очередь на печать") ;
        	return new ActionForward(next.replace("__", "?"),true);
        } else {
        	return new ActionForward("../rtf/"+filename,true);
        }
        
	}
	public static String convertToString(String aStr[]) {
		StringBuilder ret = new StringBuilder() ;
        for (int i = 0; i < aStr.length; i++) {
            ret.append(aStr[i]);
            if (i<aStr.length-1) ret.append(",") ;
        }
        return ret.toString() ;
    }

}
