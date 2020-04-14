package ru.ecom.mis.web.action.medcase.journal;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import ru.ecom.mis.ejb.service.worker.IWorkerService;
import ru.ecom.web.actions.entity.ListAction;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.forms.response.FormMessage;
import ru.nuzmsh.web.tags.helper.RolesHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
        	String per = "between to_date('" + FORMAT_1.format(prev) + "','yyyy-mm-dd')  and to_date('" + FORMAT_1.format(cur) + "','yyyy-mm-dd')";
        	aRequest.setAttribute("onlyMonthInfo", " ЗА МЕСЯЦ");
        	aRequest.setAttribute("onlyMonthH", " and m.dateStart " + per);
        	aRequest.setAttribute("onlyMonthD", " and d.transferDate " + per);
    	} else {
    		aRequest.setAttribute("onlyMonth",0) ;
    		aRequest.setAttribute("onlyMonthS", "");
    		aRequest.getSession(true).setAttribute("ByDepartmentAdmission.onlyMonth", "0") ;
    	}
        if (RolesHelper.checkRoles("/Policy/Mis/MedCase/Stac/Journal/ShowInfoAllDepartments", aRequest)) {
            if (aForm!=null) {
            	DepartmentJournalForm form =(DepartmentJournalForm) aForm ;
            	lpu = form.getDepartment() ;
            	
            	if (lpu==null || lpu.equals(0L)) {
            		lpu=Long.valueOf(aRequest.getParameter("id")!=null?aRequest.getParameter("id"):"0") ;
            		if (lpu.equals(0L) && !(aForm instanceof DepartmentMicroBioJournalForm)) {
            			form.addMessage(new FormMessage("Укажите отделение поиска")) ;
            		}
            	}
            	
            } else{
            	 return aMapping.findForward(SUCCESS) ;
            }
        } else {
            try {
                lpu = service.getWorkingLpu() ;
            } catch(Exception e) {
            	System.out.println("lpu not found") ;
            	return aMapping.findForward("successerror");
            }
        	
        }
        if (lpu!=null && lpu!=0) { 
	        aRequest.setAttribute("department",lpu) ;
	        String lpuinfo = service.getWorkingLpuInfo(lpu) ;
	        aRequest.setAttribute("departmentInfo",lpuinfo) ;
        }
        return aMapping.findForward(SUCCESS) ;
	}

}
