package ru.ecom.mis.web.action.bypassexport;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.mis.ejb.service.addresspoint.IAddressPointService;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.util.format.DateFormat;
import ru.nuzmsh.web.struts.BaseAction;

public class AttachmentByLpuAction extends BaseAction {
    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
    	AttachmentByLpuForm form =(AttachmentByLpuForm)aForm ;
    	if (form!=null && form.getLpu()!=null &&!form.getLpu().equals(Long.valueOf(0))) {
    		IAddressPointService service = Injection.find(aRequest).getService(IAddressPointService.class);
	    	
    		SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy") ;
    		Date cur = DateFormat.parseDate(form.getPeriod()) ;
        	Calendar cal = Calendar.getInstance() ;
        	cal.setTime(cur) ;
        	
	    	SimpleDateFormat format1 = new SimpleDateFormat("yyMM") ;
	        String filename = service.export(form.getNoCheckLpu()!=null&&form.getNoCheckLpu().equals(Boolean.TRUE)?false:true
	        		, form.getLpu(),format1.format(cal.getTime()), form.getNumberReestr()
	        		, form.getNumberPackage());
	        form.setFilename("<a href='../rtf/"+filename+"'>"+filename+"</a>") ;
        }
        return aMapping.findForward("success") ;
    }
}