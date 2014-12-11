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
import ru.ecom.web.util.ActionUtil;
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
    		String typeRead = ActionUtil.updateParameter("PatientAttachment","typeRead","1", aRequest) ; 
    		String typeView = ActionUtil.updateParameter("PatientAttachment","typeView","1", aRequest) ; 
    		String typeAge = ActionUtil.updateParameter("PatientAttachment","typeAge","3", aRequest) ; 
    		String typeAttachment = ActionUtil.updateParameter("PatientAttachment","typeAttachment","3", aRequest) ; 
    		String typeDefect = ActionUtil.updateParameter("PatientAttachment","typeDefect","3", aRequest) ; 
    		String typeChange = ActionUtil.updateParameter("PatientAttachment","typeChange","1", aRequest) ; 
	    	 
    		SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy") ;
    		Date cur = DateFormat.parseDate(form.getPeriod()) ;
    		Calendar cal = Calendar.getInstance() ;
        	Calendar calTo = Calendar.getInstance() ;
        	cal.setTime(cur) ;
        	Date curTo = DateFormat.parseDate(form.getPeriodTo()) ;
        	calTo.setTime(curTo) ;
        	SimpleDateFormat format1 = new SimpleDateFormat("yyMM") ;
	    	SimpleDateFormat format2 = new SimpleDateFormat("dd.MM.yyyy") ;
	    	String filename = null;
	    	String age = null ;
	    	if (typeAge!=null&&typeAge.equals("1")) {
	    		age = "<=18" ;
	    	} else if (typeAge!=null&&typeAge.equals("2")) {
	    		age = ">=18" ;
	    	}
	    	String prefix="" ;
	    	StringBuilder sqlAdd=new StringBuilder() ;
	        if (typeView!=null && typeView.equals("2")) {
	        	prefix="_no_addresss" ;
	        	sqlAdd.append(" and p.address_addressid is null ") ;
	        }
	        if (typeAttachment!=null&&typeAttachment.equals("1")) {
	        	sqlAdd.append(" and (vat.code='1' or (vat.id is null and lp.id is null)) ") ;
	        } else if (typeAttachment!=null&&typeAttachment.equals("2")) {
	        	sqlAdd.append(" and vat.code='2'") ;
	        }
	        if (typeChange!=null&&typeChange.equals("1")) {
	        	sqlAdd.append(" and (lp.dateFrom between to_date('")
	        	.append(form.getPeriod()).append("','dd.mm.yyyy') and to_date('").append(form.getPeriodTo()).append("','dd.mm.yyyy') or lp.dateTo between to_date('")
	        	.append(form.getPeriod()).append("','dd.mm.yyyy') and to_date('").append(form.getPeriodTo()).append("','dd.mm.yyyy'))") ;
	        } else if (typeChange!=null&&typeChange.equals("2")) {
	        	sqlAdd.append(" and (lp.dateTo is null or lp.dateTo <= to_date('").append(form.getPeriodTo()).append("','dd.mm.yyyy'))") ;
	        }
    		if (form.getChangedDateFrom()!=null&&!form.getChangedDateFrom().equals("")) {
 	        	sqlAdd.append(" and (coalesce(lp.editDate,lp.createDate) >= to_date('").append(form.getChangedDateFrom()).append("','dd.mm.yyyy')  )") ;
 	        	
	        } 
    		if (typeDefect!=null&&typeDefect.equals("1")) {
    			sqlAdd.append(" and lp.defectText!='' and lp.defectText is not null") ;
    		} else if (typeDefect!=null&&typeDefect.equals("2")) {
    			sqlAdd.append(" and (lp.defectText='' or lp.defectText is null)") ;
    		}
    		if (typeRead!=null&&typeRead.equals("1")) {
		    	filename = service.exportAll(age,prefix,sqlAdd.toString(),form.getNoCheckLpu()!=null&&form.getNoCheckLpu().equals(Boolean.TRUE)?false:true
		        		, form.getLpu(),form.getArea(),format2.format(cal.getTime()),format2.format(calTo.getTime()),format1.format(calTo.getTime()), form.getNumberReestr()
		        		, form.getNumberPackage());
		        if (filename!=null) {
		        form.setFilename("<a href='../rtf/"+filename+"'>"+filename+"</a>") ;
		        } else {
		        	form.setFilename("---") ;
		        }
	    	} else {
	    		aRequest.setAttribute("sqlAdd", sqlAdd.toString()) ;
	    	}
        }}
        return aMapping.findForward("success") ;
    }
}