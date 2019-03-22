package ru.ecom.mis.web.action.bypassexport;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.mis.ejb.service.addresspoint.IAddressPointService;
import ru.ecom.web.util.ActionUtil;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.util.format.DateFormat;
import ru.nuzmsh.web.struts.BaseAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

public class AttachmentByLpuAction extends BaseAction {
    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
    	AttachmentByLpuForm form =(AttachmentByLpuForm)aForm ;
    	
    	if (form!=null ) {
    		ActionErrors  erros = form.validate(aMapping, aRequest) ;
    		
    	//	System.out.println(erros) ;
    		if (erros.isEmpty()) {
    		IAddressPointService service = Injection.find(aRequest).getService(IAddressPointService.class);
    		String typeRead = ActionUtil.updateParameter("PatientAttachment","typeRead","1", aRequest) ; 
    		String typeView = ActionUtil.updateParameter("PatientAttachment","typeView","1", aRequest) ; 
    		String typeAge = ActionUtil.updateParameter("PatientAttachment","typeAge","3", aRequest) ; 
    		String typeAttachment = ActionUtil.updateParameter("PatientAttachment","typeAttachment","3", aRequest) ; 
    		String typeDefect = ActionUtil.updateParameter("PatientAttachment","typeDefect","3", aRequest) ; 
    		String typeChange = ActionUtil.updateParameter("PatientAttachment","typeChange","1", aRequest) ; 
    		String typeCompany = ActionUtil.updateParameter("PatientAttachment","typeCompany","3", aRequest) ; 
    		String typeDivide = ActionUtil.updateParameter("PatientAttachment", "typeDivide", "1",aRequest);
    		String typeAreaCheck = ActionUtil.updateParameter("PatientAttachment", "typeAreaCheck", "3",aRequest);
    		String returnType = ActionUtil.updateParameter("PatientAttachment", "typeResult", "xml",aRequest);
    		String typeDispPlan = ActionUtil.updateParameter("PatientAttachment", "typeDispPlan", "ATTACHMENT",aRequest); //По какому алгоритму формировать выгрузку (план либо прикрепления)
				//--- String typePatientFond = ActionUtil.updateParameter("PatientAttachment", "typePatientFond", "1",aRequest);
    		//boolean checkLpu = form.getLpu()!=null&&form.getLpu()>0?true:false; //
    		//SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy") ;
    		Date cur = DateFormat.parseDate(form.getPeriod()) ;
    		Calendar cal = Calendar.getInstance() ;
        	Calendar calTo = Calendar.getInstance() ;
        	cal.setTime(cur) ;
        	Date curTo = DateFormat.parseDate(form.getPeriodTo()) ;
        	calTo.setTime(curTo) ;
        	SimpleDateFormat format1 = new SimpleDateFormat("yyMM") ;
        	SimpleDateFormat format3 = new SimpleDateFormat("yyyy-MM-dd") ;
	    	SimpleDateFormat format2 = new SimpleDateFormat("dd.MM.yyyy") ;
	    	//String filename = null;
	    	String age = null ;
	    	String prefix="" ;
	    	StringBuilder sqlAdd=new StringBuilder() ;
	    	if (typeAge!=null) {
	    		if (typeAge.equals("1")) {
		    		age = "<=18" ;
		    	} else if (typeAge.equals("2")) {
		    		age = ">=18" ;
		    	}
	    		if (typeAge.equals("1")||typeAge.equals("2")) {
	    			sqlAdd.append(" and cast(to_char(to_date('").append(form.getPeriodTo()).append("','dd.mm.yyyy'),'yyyy') as int) -cast(to_char(p.birthday,'yyyy') as int) +(case when (cast(to_char(to_date('").append(form.getPeriodTo()).append("','dd.mm.yyyy'), 'mm') as int) -cast(to_char(p.birthday, 'mm') as int) +(case when (cast(to_char(to_date('").append(form.getPeriodTo()).append("','dd.mm.yyyy'),'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end) <0) then -1 else 0 end) ").append(age) ;
	    		}
	    	}
	    	if (typeView!=null && typeView.equals("2")) {
	        	prefix="_no_addresss" ;
	        	sqlAdd.append(" and p.address_addressid is null ") ;
	        }
	        if (typeAttachment!=null&&typeAttachment.equals("1")) {
	        	sqlAdd.append(" and (vat.code='1' or (vat.id is null and lp.id is null)) ") ;
	        } else if (typeAttachment!=null&&typeAttachment.equals("2")) {
	        	sqlAdd.append(" and vat.code='2'") ;
	        }
	        if (typeRead==null||!typeRead.equals("3")) { 
		        if (typeChange!=null&&typeChange.equals("1")) {
		        	sqlAdd.append(" and (lp.dateFrom between to_date('")
		        	.append(form.getPeriod()).append("','dd.mm.yyyy') and to_date('").append(form.getPeriodTo()).append("','dd.mm.yyyy') or lp.dateTo between to_date('")
		        	.append(form.getPeriod()).append("','dd.mm.yyyy') and to_date('").append(form.getPeriodTo()).append("','dd.mm.yyyy'))") ;
		        } else if (typeChange!=null&&typeChange.equals("2")) {
		        	sqlAdd.append(" and (lp.dateFrom<=to_date('").append(form.getPeriodTo()).append("','dd.mm.yyyy')" +
		        	" or (lp.dateTo is not null and lp.dateTo<=to_date('").append(form.getPeriodTo()).append("','dd.mm.yyyy')))");
		        	//sqlAdd.append(" and (lp.dateTo is null or lp.dateTo <= to_date('").append(form.getPeriodTo()).append("','dd.mm.yyyy'))") ;
		        }
    		}
    		if (form.getChangedDateFrom()!=null&&!form.getChangedDateFrom().equals("")) {
 	        	sqlAdd.append(" and (coalesce(lp.editDate,lp.createDate) >= to_date('").append(form.getChangedDateFrom()).append("','dd.mm.yyyy')  )") ;
 	        	
	        } 
    		if (typeAreaCheck!=null&&typeAreaCheck.equals("1")) {
    			sqlAdd.append(" and lp.area_id is not null ") ;
    		} else if (typeAreaCheck!=null&&typeAreaCheck.equals("2")) {
    			sqlAdd.append(" and lp.area_id is null ") ;
    		}
    		if (typeDefect!=null&&typeDefect.equals("1")) {
    			sqlAdd.append(" and lp.defectText!='' and lp.defectText is not null") ;
    		} else if (typeDefect!=null&&typeDefect.equals("2")) {
    			sqlAdd.append(" and (lp.defectText='' or lp.defectText is null)") ;
    		}
    		if (form.getCompany()!=null&& form.getCompany()!=0) {
    			sqlAdd.append(" and lp.company_id='").append(form.getCompany()).append("' ");
    		}
    		if (typeCompany!=null &&typeCompany.equals("1")){
    			sqlAdd.append(" and lp.company_id is not null ");
    		} else if (typeCompany!=null &&typeCompany.equals("2")){
    			sqlAdd.append(" and lp.company_id is null ");
    		}
    		
    		if (typeRead!=null&&(typeRead.equals("1")||typeRead.equals("3"))) {
//    			String fs = null;
    			WebQueryResult fs ;
    			boolean bNeedDivide = true;
    			if (typeDivide!=null&&typeDivide.equals("2")) {
    				bNeedDivide = false;
    			}
    		//	if (typeWork.equals("1")) { //база системная
    				if (typeRead.equals("1")){
    				fs = service.exportAll(null,prefix,sqlAdd.toString(),true
		        		, form.getLpu(),form.getArea(),format2.format(cal.getTime()),format2.format(calTo.getTime()),format1.format(calTo.getTime()), form.getNumberReestr()
		        		, form.getNumberPackage(),form.getCompany(),bNeedDivide, "1", returnType);
    				} else {
    					fs = service.exportExtDispPlanAll(null,prefix,sqlAdd.toString()
    			        		, form.getLpu(),form.getArea(),format2.format(cal.getTime()),format2.format(calTo.getTime()),format3.format(calTo.getTime()), form.getNumberReestr()
    			        		, form.getNumberPackage(),form.getCompany(), typeDispPlan);
    				}
		        if (fs!=null) {
		        	Collection<WebQueryResult> def = (Collection<WebQueryResult>)fs.get2(); 
		        	String[] files = fs.get1().toString().split("#") ;
		        	StringBuilder sb = new StringBuilder() ;
		        	for (String file:files) {
		        		sb.append("<a href='../rtf/").append(file).append("'>").append(file).append("</a> ");
		        	}
		        	form.setFilename(sb.toString()) ;
		        	if (def!=null&&!def.isEmpty()){
		        		aRequest.setAttribute("defectWQR", def);
		        	}
		        	
		        	
		        	
		        } else {
		        	form.setFilename("---") ;
		        }
	    	} else {

		    		if (form.getLpu()!=null&&form.getLpu()>0) {	
		    			sqlAdd.append(" and lp.lpu_id = ").append(form.getLpu());
						if (form.getArea()!=null&&form.getArea()>0) {
							sqlAdd.append(" and lp.area_id=").append(form.getArea());
						}
		    		}


	    		
	    		aRequest.setAttribute("sqlAdd", sqlAdd.toString()) ;
	    	}
        } 
    		}
        return aMapping.findForward(SUCCESS) ;
    }
}