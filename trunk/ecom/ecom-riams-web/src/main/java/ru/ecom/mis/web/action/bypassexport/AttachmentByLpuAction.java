package ru.ecom.mis.web.action.bypassexport;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.mis.ejb.service.addresspoint.IAddressPointService;
import ru.ecom.mis.web.action.util.ActionUtil;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.util.format.DateFormat;
import ru.nuzmsh.web.struts.BaseAction;

public class AttachmentByLpuAction extends BaseAction {
    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
    	AttachmentByLpuForm form =(AttachmentByLpuForm)aForm ;
    	
    	if (form!=null ) {
    		ActionErrors  erros = form.validate(aMapping, aRequest) ;
    		//System.out.println(erros) ;
    		if (erros.isEmpty()&&form.getLpu()!=null &&!form.getLpu().equals(Long.valueOf(0))
    			) {
    		IAddressPointService service = Injection.find(aRequest).getService(IAddressPointService.class);
	    	String typeView = ActionUtil.updateParameter("PatientAttachment","typeView","1", aRequest) ; 
    		SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy") ;
    		Date cur = DateFormat.parseDate(form.getPeriod()) ;
    		Calendar cal = Calendar.getInstance() ;
        	Calendar calTo = Calendar.getInstance() ;
        	cal.setTime(cur) ;
        	Date curTo = DateFormat.parseDate(form.getPeriodTo()) ;
        	calTo.setTime(curTo) ;
        	SimpleDateFormat format1 = new SimpleDateFormat("yyMM") ;
	    	SimpleDateFormat format2 = new SimpleDateFormat("dd.MM.yyyy") ;
	    	String filename ;
	        if (typeView!=null && typeView.equals("1")) {
		    	filename = service.export(form.getNoCheckLpu()!=null&&form.getNoCheckLpu().equals(Boolean.TRUE)?false:true
		        		, form.getLpu(),form.getArea(),format2.format(cal.getTime()),format2.format(calTo.getTime()), format1.format(calTo.getTime()),form.getNumberReestr()
		        		, form.getNumberPackage());
	        } else if (typeView!=null && typeView.equals("2")) {
		    	filename = service.exportNoAddress(form.getNoCheckLpu()!=null&&form.getNoCheckLpu().equals(Boolean.TRUE)?false:true
		        		, form.getLpu(),form.getArea(),format2.format(cal.getTime()),format2.format(calTo.getTime()),format1.format(calTo.getTime()), form.getNumberReestr()
		        		, form.getNumberPackage());
	        } else {
		    	filename = service.exportAdult(form.getNoCheckLpu()!=null&&form.getNoCheckLpu().equals(Boolean.TRUE)?false:true
		        		, form.getLpu(),form.getArea()
		        		,format2.format(cal.getTime())
		        		,format2.format(calTo.getTime())
		        		,format1.format(calTo.getTime())
		        		, form.getNumberReestr()
		        		, form.getNumberPackage());
	        }
	        form.setFilename("<a href='../rtf/"+filename+"'>"+filename+"</a>") ;
        }}
        return aMapping.findForward("success") ;
    }
}