package ru.ecom.web.print;

import java.util.Collection;
import java.util.Enumeration;
import java.util.TreeMap;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.ejb.print.IPrintService;
import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.web.login.LoginInfo;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
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
        StringBuilder sql = new StringBuilder() ;
        sql.append("select ce.name,wf.id,case when ce.isTxtFile='1' then '1' else null end as istxtfile from WorkFunction wf left join SecUser su on su.id=secUser_id left join Worker w on w.id=wf.worker_id left join MisLpu lpu on lpu.id=w.lpu_id left join CopyingEquipment ce on ce.id=lpu.copyingEquipmentDefault_id where su.login='").append(login).append("'") ;
        IWebQueryService service1 = Injection.find(aRequest).getService(IWebQueryService.class) ;
		Collection<WebQueryResult> list = service1.executeNativeSql(sql.toString(),1);
		String print = "no" ;
		boolean isTxtFile = false ;
        if (list.size()>0) {
        	WebQueryResult wqr = list.iterator().next() ;
        	if (wqr.get1()!=null) 	print = ""+wqr.get1() ;
        	if (wqr.get3()!=null) isTxtFile = true ;
        }
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
        String filename = service.print(new StringBuilder().append(print).append("-").append(login).toString()
        		,isTxtFile,reportKey
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
