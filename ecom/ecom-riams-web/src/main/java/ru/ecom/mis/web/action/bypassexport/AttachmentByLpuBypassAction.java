package ru.ecom.mis.web.action.bypassexport;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import ru.ecom.ejb.services.monitor.IRemoteMonitorService;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.expomc.ejb.services.form.importformat.IImportFormatService;
import ru.ecom.expomc.ejb.services.importservice.ImportException;
import ru.ecom.expomc.ejb.services.importservice.ImportFileForm;
import ru.ecom.mis.ejb.service.addresspoint.IAddressPointService;
import ru.ecom.web.util.ActionUtil;
import ru.ecom.web.util.FileUploadUtil;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.util.format.DateFormat;
import ru.nuzmsh.web.struts.BaseAction;

public class AttachmentByLpuBypassAction extends BaseAction {
    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
    	AttachmentByLpuForm form =(AttachmentByLpuForm)aForm ;
    	
    	if (form!=null ) {
    		//ActionErrors  erros = form.validate(aMapping, aRequest) ;
    	//	System.out.println(erros) ;
    		if (true) {
    		IAddressPointService service = Injection.find(aRequest).getService(IAddressPointService.class);
    		String typeView = ActionUtil.updateParameter("PatientAttachment","typeView","1", aRequest) ; 
    		String typeAge = ActionUtil.updateParameter("PatientAttachment","typeAge","3", aRequest) ; 
    		String typeAttachment = ActionUtil.updateParameter("PatientAttachment","typeAttachment","3", aRequest) ; 
    		String typeDefect = ActionUtil.updateParameter("PatientAttachment","typeDefect","3", aRequest) ; 
    		String typeCompany = ActionUtil.updateParameter("PatientAttachment","typeCompany","3", aRequest) ; 
    		String typeAreaCheck = ActionUtil.updateParameter("PatientAttachment", "typeAreaCheck", "3",aRequest);
    		String typeSex=ActionUtil.updateParameter("PatientAttachment", "typeSex", "3", aRequest);
    		String age = null ;
	    	StringBuilder sqlAdd=new StringBuilder() ;
	    	if (typeAge!=null) {
	    		if (typeAge!=null&&typeAge.equals("1")) {
		    		age = "<=18" ;
		    	} else if (typeAge!=null&&typeAge.equals("2")) {
		    		age = ">=18" ;
		    	}
	    		if (typeAge.equals("1")||typeAge.equals("2")) {
	    			sqlAdd.append(" and cast(to_char(to_date('").append(form.getPeriodTo()).append("','dd.mm.yyyy'),'yyyy') as int) -cast(to_char(p.birthday,'yyyy') as int) +(case when (cast(to_char(to_date('").append(form.getPeriodTo()).append("','dd.mm.yyyy'), 'mm') as int) -cast(to_char(p.birthday, 'mm') as int) +(case when (cast(to_char(to_date('").append(form.getPeriodTo()).append("','dd.mm.yyyy'),'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end) <0) then -1 else 0 end) ").append(age) ;
	    		}
	    	}
	        if (typeSex!=null&&typeSex.equals("1")) {
	        	sqlAdd.append(" and vs.omccode='1'");
	        	
	        } else if (typeSex!=null&&typeSex.equals("2")) {
	        	sqlAdd.append(" and vs.omccode='2'");
	        }
	    	if (typeView!=null && typeView.equals("2")) {
	        	sqlAdd.append(" and p.address_addressid is null ") ;
	        }
	        if (typeAttachment!=null&&typeAttachment.equals("1")) {
	        	sqlAdd.append(" and (vat.code='1') ") ;
	        } else if (typeAttachment!=null&&typeAttachment.equals("2")) {
	        	sqlAdd.append(" and vat.code='2'") ;
	        } else if (typeAttachment!=null&&typeAttachment.equals("4")) {
	        	sqlAdd.append(" and lp.attachedType_id is null") ;
	        } 
    		if (typeAreaCheck!=null&&typeAreaCheck.equals("1")) {
    			sqlAdd.append(" and lp.area_id is not null ") ;
    		} else if (typeAreaCheck!=null&&typeAreaCheck.equals("2")) {
    			sqlAdd.append(" and (lp.area_id is null or lp.lpu_id is null)") ;
    		}
    		if (typeDefect!=null&&typeDefect.equals("1")) {
    			sqlAdd.append(" and lp.defectText!='' and lp.defectText is not null") ;
    		} else if (typeDefect!=null&&typeDefect.equals("2")) {
    			sqlAdd.append(" and (lp.defectText='' or lp.defectText is null)") ;
    		}
    		if (form.getCompany()!=null&& form.getCompany()!=0) {
    			sqlAdd.append(" and lp.company_id='"+form.getCompany()+"' ");
    		}
    		if (typeCompany!=null &&typeCompany.equals("1")){
    			sqlAdd.append(" and lp.company_id is not null ");
    		} else if (typeCompany!=null &&typeCompany.equals("2")){
    			sqlAdd.append(" and lp.company_id is null ");
    		}    		
		    		if (form.getLpu()!=null&&form.getLpu()>0) {	
		    			sqlAdd.append(" and lp.lpu_id = ").append(form.getLpu());
		    		}
		    		if (form.getArea()!=null&&form.getArea()>0) {
		    			sqlAdd.append(" and lp.area_id=").append(form.getArea());
		    		}
	    		aRequest.setAttribute("sqlAdd", sqlAdd.toString()) ;
        } 
    		}
        return aMapping.findForward("success") ;
    }
}