package ru.ecom.mis.web.action.synclpufond;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.mis.ejb.service.addresspoint.IAddressPointService;
import ru.ecom.mis.ejb.service.medcase.IHospitalMedCaseService;
import ru.ecom.mis.web.action.bypassexport.AttachmentByLpuForm;
import ru.ecom.web.util.ActionUtil;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.util.format.DateFormat;
import ru.nuzmsh.web.struts.BaseAction;

public class HospitalDirectDataInFondAction extends BaseAction {
    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
    	AttachmentByLpuForm form =(AttachmentByLpuForm)aForm ;
    	String typeMode=ActionUtil.updateParameter("HospitalDirectDataInFond","typeMode","1", aRequest) ;
    	// Export xml
    	if (form!=null && typeMode!=null && typeMode.equals("1")) {
    		if (form!=null) {
    			
    		
    		//ActionErrors  erros = form.validate(aMapping, aRequest) ;
    		//System.out.println(erros) ;
    		if (form.getLpu()!=null &&!form.getLpu().equals(Long.valueOf(0))
    			) {
    		IHospitalMedCaseService service = Injection.find(aRequest).getService(IHospitalMedCaseService.class);
	    	String typeView = ActionUtil.updateParameter("HospitalDirectDataInFond","typeView","1", aRequest) ; 
	    	SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy") ;
    		SimpleDateFormat format_n = new SimpleDateFormat("yyyy-MM-dd") ;
    		Date cur = DateFormat.parseDate(form.getPeriod()) ;
    		Calendar cal = Calendar.getInstance() ;
        	Calendar calTo = Calendar.getInstance() ;
        	cal.setTime(cur) ;
        	String dateTo = form.getPeriodTo() ;
        	if (dateTo==null || dateTo.equals("")) dateTo=form.getPeriod() ;
        	Date curTo = DateFormat.parseDate(dateTo) ;
        	calTo.setTime(curTo) ;
        	SimpleDateFormat format1 = new SimpleDateFormat("yyMM") ;
	    	//SimpleDateFormat format2 = new SimpleDateFormat("dd.MM.yyyy") ;
	    	String filename =null;
	        if (typeView!=null && typeView.equals("1")) {
	        	WebQueryResult wqr = service.exportN1(format_n.format(cal.getTime()),format_n.format(calTo.getTime()), format1.format(calTo.getTime()), form.getNumberReestr(),null) ;
	        	filename= null;
	        	StringBuilder sb = new StringBuilder() ;
        		sb.append("<a href='../rtf/"+wqr.get1()+"'>"+wqr.get1()+"</a>").append("</br>") ;
        		if (wqr.get2()!=null) sb.append("<a href='../rtf/"+wqr.get2()+"'>"+wqr.get2()+"</a>").append("</br>") ;
        		if (wqr.get3()!=null) sb.append("<a href='../rtf/"+wqr.get3()+"'>"+wqr.get3()+"</a>").append("</br>") ;
        		form.setFilename(sb.toString()) ;
	        	aRequest.setAttribute("listExist", wqr.get4()) ;
	        	aRequest.setAttribute("listError", wqr.get5()) ;
	        } else if (typeView!=null && typeView.equals("2")) {
	        	filename=service.exportN2(format_n.format(cal.getTime()), 
	    	        	format_n.format(calTo.getTime()), format1.format(calTo.getTime()), form.getNumberReestr(),null) ;
	        } else if (typeView!=null && typeView.equals("3")) {
	        	WebQueryResult wqr = service.exportN3(format_n.format(cal.getTime()), 
	    	        	format_n.format(calTo.getTime()), format1.format(calTo.getTime()), form.getNumberReestr(),null) ;
	        	filename=""+wqr.get1() ;
	        } else if (typeView!=null && typeView.equals("4")) {
	        	filename=service.exportN4(format_n.format(cal.getTime()), 
	    	        	format_n.format(calTo.getTime()), format1.format(calTo.getTime()), form.getNumberReestr(),null) ;
	        } else if (typeView!=null && typeView.equals("5")) {
	        	filename=service.exportN5(format_n.format(cal.getTime()), 
	    	        	format_n.format(calTo.getTime()), format1.format(calTo.getTime()), form.getNumberReestr(),null) ;
	        } else if (typeView!=null && typeView.equals("6")) {
	        	filename=service.exportN6(format_n.format(cal.getTime()), 
	    	        	format_n.format(calTo.getTime()), format1.format(calTo.getTime()), form.getNumberReestr(),null) ;
	        } else if (typeView!=null && typeView.equals("8")) {
	        	WebQueryResult[] filenameList=service.exportFondZip13(format_n.format(cal.getTime()), 
	    	        	format_n.format(calTo.getTime()), format1.format(calTo.getTime()), form.getNumberReestr()) ;
	        	StringBuilder sb = new StringBuilder() ;
	        	for (WebQueryResult wqr:filenameList) {
	        		sb.append("<a href='../rtf/"+wqr.get1()+"'>"+wqr.get1()+"</a>").append("</br>") ;
	        		if (wqr.get2()!=null) sb.append("<a href='../rtf/"+wqr.get2()+"'>"+wqr.get2()+"</a>").append("</br>") ;
	        		if (wqr.get3()!=null) sb.append("<a href='../rtf/"+wqr.get3()+"'>"+wqr.get3()+"</a>").append("</br>") ;
		        	//aRequest.setAttribute("listExist", fn.get4()) ;
		        	//aRequest.setAttribute("listError", fn.get5()) ;
	        	}
	        	form.setFilename(sb.toString()) ;
	        } else if (typeView!=null && typeView.equals("9")) {
	        	String[] filenameList=service.exportFondZip45(format_n.format(cal.getTime()), 
	    	        	format_n.format(calTo.getTime()), format1.format(calTo.getTime()), "01") ;
	        	StringBuilder sb = new StringBuilder() ;
	        	for (String fn:filenameList) {
	        		sb.append("<a href='../rtf/"+fn+"'>"+fn+"</a>").append("</br>") ;
	        	}
        		form.setFilename(sb.toString()) ;
	        }
	        if (filename!=null) form.setFilename("<a href='../rtf/"+filename+"'>"+filename+"</a>") ;
        }}
    	} else if (typeMode!=null && typeMode.equals("2")) {
    		
    	} else if (typeMode!=null && typeMode.equals("3")) {
    		
    	}
        return aMapping.findForward("success") ;
    }
}