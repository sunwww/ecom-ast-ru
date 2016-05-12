package ru.ecom.mis.web.action.medcase.journal;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.mis.ejb.service.worker.IWorkerService;
import ru.ecom.web.actions.entity.ListAction;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.forms.response.FormMessage;
import ru.nuzmsh.web.tags.helper.RolesHelper;

public class ByDepartmentAdmissionSearchAction extends ListAction{

	public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
		
        IWorkerService service = Injection.find(aRequest).getService(IWorkerService.class) ;
        Long lpu ;
        String onlyMonth = aRequest.getParameter("onlyMonth") ;
        String onlyMonthH = aRequest.getParameter("onlyMonthH") ;
    	if (onlyMonth ==null && onlyMonthH==null) onlyMonthH = (String)aRequest.getSession(true).getAttribute("ByDepartmentAdmission.onlyMonth") ;
        
    	if ((onlyMonth==null && onlyMonthH==null) 
    			|| (onlyMonthH!=null && onlyMonthH.equals("1"))
    			||  ( onlyMonth!=null &&onlyMonthH==null &&( onlyMonth.equals("true") 
    					||onlyMonth.equals("1") 
    					|| onlyMonth.equals("on"))
    					)) {
    		
    		aRequest.setAttribute("onlyMonth",1) ;
    		aRequest.getSession(true).setAttribute("ByDepartmentAdmission.onlyMonth", "1") ;
        	Date cur = new Date() ;
        	Calendar cal = Calendar.getInstance() ;
        	cal.setTime(cur) ;
        	cal.add(Calendar.MONTH, -1) ;
        	Date prev = cal.getTime() ;
        	SimpleDateFormat FORMAT_1 = new SimpleDateFormat("yyyy-MM-dd") ;
        	aRequest.setAttribute("onlyMonth",1) ;
        	String per = new StringBuilder().append("between to_date('").append(FORMAT_1.format(prev)).append("','yyyy-mm-dd')  and to_date('").append(FORMAT_1.format(cur)).append("','yyyy-mm-dd')").toString() ;
        	aRequest.setAttribute("onlyMonthInfo", " ЗА МЕСЯЦ");
        	aRequest.setAttribute("onlyMonthH", new StringBuilder().append(" and m.dateStart ").append(per).toString());
        	aRequest.setAttribute("onlyMonthD", new StringBuilder().append(" and d.transferDate ").append(per).toString());
    	} else {
    		aRequest.setAttribute("onlyMonth",0) ;
    		aRequest.setAttribute("onlyMonthS", "");
    		aRequest.getSession(true).setAttribute("ByDepartmentAdmission.onlyMonth", "0") ;
    	}
        if (RolesHelper.checkRoles("/Policy/Mis/MedCase/Stac/Journal/ShowInfoAllDepartments", aRequest)) {
            if (aForm!=null) {
            	DepartmentJournalForm form =(DepartmentJournalForm) aForm ;
            	lpu = form.getDepartment() ;
            	
            	if (lpu==null || lpu.equals(Long.valueOf(0))) {
            		lpu=Long.valueOf(aRequest.getParameter("id")!=null?aRequest.getParameter("id"):"0") ;
            		if (lpu==null || lpu.equals(Long.valueOf(0))) {
            			form.addMessage(new FormMessage("Укажите отделение поиска")) ;
            		}
            	}
            	
            } else{
            	 return aMapping.findForward("success") ;
            }
        } else {
            try {
                lpu = service.getWorkingLpu() ;
                System.out.println("lpu="+lpu) ;
            } catch(Exception e) {
            	System.out.println("lpu not found") ;
            	return aMapping.findForward("successerror");
            }
        	
        }
        if (lpu!=null && lpu!=0) { 
	        System.out.println("lpu="+lpu) ;
	        aRequest.setAttribute("department",lpu) ;
	        String lpuinfo = service.getWorkingLpuInfo(lpu) ;
	        aRequest.setAttribute("departmentInfo",lpuinfo) ;
        }
        return aMapping.findForward("success") ;
	}

}
