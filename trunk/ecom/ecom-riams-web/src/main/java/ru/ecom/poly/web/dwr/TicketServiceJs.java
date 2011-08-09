package ru.ecom.poly.web.dwr;

import java.text.ParseException;
import java.util.HashMap;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
//import javax.servlet.jsp.JspException;

import ru.ecom.ejb.services.script.IScriptService;
import ru.ecom.mis.ejb.service.worker.IWorkerService;
import ru.ecom.poly.ejb.services.ITicketService;
import ru.ecom.template.web.dwr.TemplateProtocolJs;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.tags.helper.RolesHelper;

public class TicketServiceJs {
	public String findDoubleBySpecAndDate(Long aId, Long aMedcard, Long aSpec, String aDate, HttpServletRequest aRequest) throws NamingException, Exception {
		ITicketService service = Injection.find(aRequest).getService(ITicketService.class) ;
		return service.findDoubleBySpecAndDate(aId , aMedcard, aSpec, aDate) ;
	}
	public String findProvReason(HttpServletRequest aRequest) throws NamingException {
		ITicketService service = Injection.find(aRequest).getService(ITicketService.class) ;
		return service.findProvReason();
		
	}
	public String getMedServiceBySpec(Long aSpec, String aDate, HttpServletRequest aRequest) throws ParseException, NamingException {
		ITicketService service = Injection.find(aRequest).getService(ITicketService.class) ;
		return service.getMedServiceBySpec(aSpec, aDate);
	}
	public Long getWorkFunction(HttpServletRequest aRequest) throws NamingException {
		IWorkerService servWorker = Injection.find(aRequest).getService(IWorkerService.class) ;
    	Long doctor = servWorker.getWorkFunction() ;
		return doctor;
	}
	public Long getUserByWorkFunction(HttpServletRequest aRequest) throws NamingException {
		IWorkerService servWorker = Injection.find(aRequest).getService(IWorkerService.class) ;
    	Long doctor = servWorker.getWorkFunction() ;
		return doctor;
	}
	public String isEditCheck(Long aIdTicket, Long aDoctor, HttpServletRequest aRequest) throws Exception {
    	IScriptService service = (IScriptService)Injection.find(aRequest).getService("ScriptService") ;
    	IWorkerService servWorker = Injection.find(aRequest).getService(IWorkerService.class) ;
    	String funcs = servWorker.getWorkFunctions(aDoctor) ;
    	String[] workFunctions = funcs.split(",") ;
		boolean isEditUser = false ; 
		for (String work:workFunctions) {
    		isEditUser = checkUser(aIdTicket, Long.valueOf(work), aRequest) ;
    		if (isEditUser) break ;
    	}

		
		System.out.println("isEditUser="+isEditUser) ;
		if (isEditUser) {
			boolean isClosedPeriod = checkPermission(service, "Ticket", "dateClosePeriod", aIdTicket, aRequest) ;
			System.out.println("isClosedPeriod="+isClosedPeriod) ;
			if (isClosedPeriod) {
				boolean isEditClose = checkPermission(service, "Ticket", "editDataClosePeriod", aIdTicket, aRequest); 
				System.out.println("isEditClose="+isEditClose) ;
				return  isEditClose?"1":"0#Период закрыт на редактирование";
			} else {
				return "1" ;
			}
		}
    	return "0#У Вас стоит ограничение на редактрование данного протокола!" ;
	}
    	
    private boolean checkUser(Long aIdTicket, Long aDoctor, HttpServletRequest aRequest) throws Exception {
    	boolean always = RolesHelper.checkRoles("/Policy/Poly/Ticket/IsDoctorEdit",aRequest) ;
    	if (always) {
    		IWorkerService servWorker = Injection.find(aRequest).getService(IWorkerService.class) ;
        	String userByDoc = servWorker.getUsernameByWorkFunction(aDoctor) ;
        	String userCur=TemplateProtocolJs.getUsername(aRequest) ;
        	if (userByDoc==null||userByDoc.equals("") || userCur.equals(userByDoc)) {
            	return true ;
            } else {
            	//HashMap param = new HashMap() ;
            	//long res1 ;
            	//Object res ;
            	IScriptService service = (IScriptService)Injection.find(aRequest).getService("ScriptService") ;
            	return checkPermission(service, "Ticket", "editOtherUser", aIdTicket, aRequest) ;
            }
    	}
    	return true ;
    }
    private boolean checkPermission(IScriptService aService, String aObject, String aPermission,  Long aIdTicket, HttpServletRequest aRequest) throws Exception {
    	HashMap<String, Comparable> param = new HashMap<String, Comparable>() ;
    	long res1 ;
    	Object res ;
    	param.put("obj",aObject) ;
		param.put("permission" ,aPermission) ;
		param.put("id", aIdTicket) ;
		res = aService.invoke("WorkerService", "checkPermission", new Object[]{param});
		res1 = TemplateProtocolJs.parseLong(res);
		if (res1>0) {return true ; }
    	return false ;
    }
}
