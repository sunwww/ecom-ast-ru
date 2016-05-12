package ru.ecom.web.print;

import java.util.Collection;
import java.util.Enumeration;
import java.util.Map;
import java.util.TreeMap;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ecs.xhtml.area;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.diary.ejb.service.protocol.IKdlDiaryService;
import ru.ecom.ejb.print.IPrintService;
import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.ejb.services.script.IScriptService;
import ru.ecom.web.login.LoginInfo;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.web.messages.InfoMessage;
import ru.nuzmsh.web.struts.BaseAction;
import ru.nuzmsh.web.tags.helper.RolesHelper;

public class PrintAction extends BaseAction {

	@Override
	public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
		String reportKey = aMapping.getParameter() ;
		String appName = null ;
		String ispRepBase = aRequest.getParameter("isReportBase") ;
		String servJs = aRequest.getParameter("s") ;
		String methodJs = aRequest.getParameter("m") ;
		if (ispRepBase!=null &&ispRepBase.toUpperCase().equals("TRUE") 
	        		&& RolesHelper.checkRoles("/Policy/Config/IsReportBase",aRequest) 
	        		&& servJs!=null && servJs.equals("PrintService")) {
	        	appName = Injection.getWebName(aRequest, null) ;
	        	appName = appName.substring(0,1)+"rep"+appName.substring(1) ;
	        } else {
	        	appName = Injection.getWebName(aRequest, null) ;
	        }
		IPrintService service  = Injection.find(aRequest).getService(IPrintService.class);
	    TreeMap<String, String> map = new TreeMap<String, String>();
        Enumeration en = aRequest.getParameterNames() ;
        boolean isMultyId = !(aRequest.getParameter("multy")==null?true:aRequest.getParameter("multy").equals("")) ;
        //System.out.println("multy="+isMultyId) ;
        //aRequest.getParameterValues("id").length ;
        String login = LoginInfo.find(aRequest.getSession(true)).getUsername() ;
        StringBuilder sql = new StringBuilder() ;
        sql.append("select ce.name,ce.id,case when ce.isTxtFile='1' then '1' else null end as istxtfile,ce.commandPrintTxt from WorkFunction wf left join SecUser su on su.id=wf.secUser_id left join CopyingEquipment ce on ce.id=wf.copyingEquipmentDefault_id where su.login='").append(login).append("' and ce.id is not null") ;
        IWebQueryService service1 = Injection.find(aRequest).getService(IWebQueryService.class) ;
        IScriptService serviceScr = Injection.find(aRequest,appName).getService(IScriptService.class) ;
		Collection<WebQueryResult> list = service1.executeNativeSql(sql.toString(),1);
		String print = "no" ;
		boolean isTxtFile = false ;
		WebQueryResult printMain = null ;
        if (list.size()>0) {
        	printMain = list.iterator().next() ;
        } else {
            sql = new StringBuilder() ;
            sql.append("select ce.name,ce.id,case when ce.isTxtFile='1' then '1' else null end as istxtfile,ce.commandPrintTxt from WorkFunction wf left join SecUser su on su.id=secUser_id left join Worker w on w.id=wf.worker_id left join MisLpu lpu on lpu.id=w.lpu_id left join CopyingEquipment ce on ce.id=lpu.copyingEquipmentDefault_id where su.login='").append(login).append("'  and ce.id is not null") ;
            list = service1.executeNativeSql(sql.toString(),1);
            if (list.size()>0) {
            	printMain = list.iterator().next() ;
            }
        }
        //System.out.println("print="+printMain.get1()) ;
		if (printMain!=null) {
            sql = new StringBuilder() ;
            sql.append("select ce.name,ce.id,case when ce.isTxtFile='1' then '1' else null end as istxtfile,ce.commandPrintTxt from  CopyingEquipment ce where ce.parent_id='"+printMain.get2()+"' and ce.maskFiles = substring('"+reportKey+"',1,length(ce.maskFiles))") ;
            //System.out.println(sql.toString());
            list = service1.executeNativeSql(sql.toString(),1);
            if (list.size()>0) {
            	printMain = list.iterator().next() ;
            }
        	if (printMain.get1()!=null) print = ""+printMain.get1() ;
        	if (printMain.get3()!=null) isTxtFile = true ;
		}
		//System.out.println("print="+printMain.get2()) ;
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
        //Map<String,Object> values =  ;
        String filename = service.print(new StringBuilder().append(print).append("-").append(login).toString()
        		,isTxtFile,reportKey,
        		serviceScr,servJs, methodJs, map) ;
        
        String next = aRequest.getParameter("next") ;
        if (next!=null && !next.equals("") &&filename.toLowerCase().contains(".txt")) {
        	IKdlDiaryService serviceKdl = Injection.find(aRequest).getService(IKdlDiaryService.class) ;
        	String path=serviceKdl.getDir("tomcat.data.rtf","/opt/tomcat/webapps/rtf") ;
        	if (print!=null && !print.equals("no")) {run("lp -d "+print+" "+path+"/"+filename) ;}
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
	private String run(String Command){
		try{
			//System.out.println("command: " + Command );
			Runtime.getRuntime().exec(Command);
			return("0");
		}
		catch (Exception e){
			System.out.println("Error running command: " + Command + "\n" + e.getMessage());
			return(e.getMessage());
		}
	} 
}

