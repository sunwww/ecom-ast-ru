package ru.medos.web.persdata;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.mis.ejb.service.addresspoint.IAddressPointService;
import ru.ecom.mis.web.action.bypassexport.AttachmentByLpuForm;
import ru.ecom.web.util.Injection;
import ru.medos.ejb.persdata.form.CopiesTransferActForm;
import ru.medos.ejb.persdata.service.IDefaultService;
import ru.nuzmsh.util.format.DateFormat;
import ru.nuzmsh.web.struts.BaseAction;

public class ExportIdentifierByCopiesTransferActServletAction extends BaseAction {
    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
    	CopiesTransferActForm form =(CopiesTransferActForm)aForm ;
    	if (form!=null && form.getActNumber()!=null 
    			&&!form.getActNumber().equals("")) {
    		IDefaultService service = Injection.find(aRequest).getService(IDefaultService.class);
	    	
    		SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy") ;
    		Date cur = DateFormat.parseDate(form.getUrgencyStartDate()) ;
        	Calendar cal = Calendar.getInstance() ;
        	cal.setTime(cur) ;
        	
	    	SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMdd") ;
	        String filename = service.exportIdentifierByCopiesTransferActServlet(form.getId());
	        form.setFilenameInfo("<a href='../persdoc/export/identifier/"+filename+"'>"+filename+"</a>") ;
        }
        return aMapping.findForward("success") ;
    }
}