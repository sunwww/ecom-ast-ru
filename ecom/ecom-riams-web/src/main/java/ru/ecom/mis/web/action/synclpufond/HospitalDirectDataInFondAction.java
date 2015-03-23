package ru.ecom.mis.web.action.synclpufond;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.ejb.services.util.ConvertSql;
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
	        } else if (typeView!=null && typeView.equals("7")) {
		        	WebQueryResult wqr = service.exportN1_planHosp(format_n.format(cal.getTime()),format_n.format(calTo.getTime()), format1.format(calTo.getTime()), form.getNumberReestr(),null) ;
		        	filename= null;
		        	StringBuilder sb = new StringBuilder() ;
	        		sb.append("<a href='../rtf/"+wqr.get1()+"'>"+wqr.get1()+"</a>").append("</br>") ;
	        		if (wqr.get2()!=null) sb.append("<a href='../rtf/"+wqr.get2()+"'>"+wqr.get2()+"</a>").append("</br>") ;
	        		if (wqr.get3()!=null) sb.append("<a href='../rtf/"+wqr.get3()+"'>"+wqr.get3()+"</a>").append("</br>") ;
	        		form.setFilename(sb.toString()) ;
		        	aRequest.setAttribute("listExist", wqr.get4()) ;
		        	aRequest.setAttribute("listError", wqr.get5()) ;
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
    		
    	} else if (typeMode!=null && typeMode.equals("4")) {
    		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
    		SimpleDateFormat f = new SimpleDateFormat("dd.MM.yyyy") ;
    		StringBuilder sql = new StringBuilder() ;
    		String[] dates = {"=to_date('datF','dd.mm.yyyy')"
    				," between to_date('datS','dd.mm.yyyy') and to_date('datT','dd.mm.yyyy')"} ;
    		
    		sql.append("select ") ;
    		sql.append(" hdf.id as hdfid,(select list(''||pat.id) from patient pat");
    		sql.append(" where pat.lastname=hdf.lastname and pat.firstname=hdf.firstname and pat.middlename = hdf.middlename ") ;
    		sql.append(" and pat.birthday=hdf.birthday  ) as pat") ;
    		sql.append(" ,hdf.statcard as statcard, to_char(coalesce(hdf.hospdate,hdf.prehospdate),'dd.mm.yyyy')") ;
    		sql.append(" from hospitaldatafond hdf ") ;
    		sql.append(" where ") ;
    		sql.append(" hdf.hospitalmedcase_id is null ") ;
    		sql.append(" and (case when hdf.istable4='1' then '1' else null end  is null) ") ;
    		//System.out.println(sql) ;
    		Collection<WebQueryResult> list = service.executeNativeSql(sql.toString()) ;
    		//System.out.println("Проверка по госпитализациям"+list.size()) ;
    		for (WebQueryResult wqr:list) {
    			Object hdfId=wqr.get1() ;
    			boolean next = true ;
    			if (wqr.get4()!=null && wqr.get2()!=null &&!((""+wqr.get2()).equals(""))) {
    				for (String dat:dates) {
    					if (next) {
    						String dat1 = dat.replaceAll("datF", ""+wqr.get4()) ;
							dat1 = dat1.replaceAll("datS", f.format(ConvertSql.parseDate(""+wqr.get4(), -2))) ;
							dat1 = dat1.replaceAll("datT", f.format(ConvertSql.parseDate(""+wqr.get4(), 7))) ;
							//System.out.print(dat) ;
							//System.out.print("--") ;
							//System.out.println(dat1) ;
    						sql = new StringBuilder() ;
    						sql.append(" select list(''||sls.id) from medcase sls ") ;
    						sql.append(" left join statisticstub ss on ss.id=sls.statisticstub_id ") ;
    						sql.append(" left join patient pat on pat.id=sls.patient_id ") ;
    						sql.append(" left join medcase slo on slo.parent_id=sls.id and slo.dtype='DepartmentMedCase' ") ;
    						sql.append(" left join bedfund bf on bf.id=slo.bedfund_id ") ;
    						sql.append(" left join vocbedtype vbt on vbt.id=bf.bedtype_id ") ;
    						sql.append(" left join vocbedsubtype vbst on vbst.id=bf.bedsubtype_id ") ;
    						sql.append(" where sls.dtype='HospitalMedCase' and slo.prevmedcase_id is null ") ;
    						sql.append(" and pat.id in (").append(wqr.get2()).append(")");
    						sql.append(" and sls.datestart").append(dat1);
    						if (wqr.get3()!=null) sql.append(" and ss.code='"+wqr.get3()+"' ") ;
    						Collection<WebQueryResult> l1 = service.executeNativeSql(sql.toString()) ;
    						if (!l1.isEmpty()) {
    							String medcase ;
    							Object slss = l1.iterator().next().get1() ;
    							if (slss!=null) {
    								medcase = freeMedCase(hdfId,slss, service) ;
    								if (medcase!=null) {
    									next = false ;
    								}
    							}
    						}
    					}
    				}
    				if (next && wqr.get3()!=null) {
    					//System.out.println("no statcard") ;
    					for (String dat:dates) {
	        			if (next) {
	        				String dat1 = dat.replaceAll("datF", ""+wqr.get4()) ;
							dat1 = dat1.replaceAll("datS", f.format(ConvertSql.parseDate(""+wqr.get4(), -7))) ;
							dat1 = dat1.replaceAll("datT", f.format(ConvertSql.parseDate(""+wqr.get4(), 7))) ;
							
	        				sql = new StringBuilder() ;
	        				sql.append(" select list(''||sls.id) from medcase sls ") ;
	        				sql.append(" left join statisticstub ss on ss.id=sls.statisticstub_id ") ;
	        				sql.append(" left join patient pat on pat.id=sls.patient_id ") ;
	        				sql.append(" left join medcase slo on slo.parent_id=sls.id and slo.dtype='DepartmentMedCase' ") ;
	        				sql.append(" left join bedfund bf on bf.id=slo.bedfund_id ") ;
	        				sql.append(" left join vocbedtype vbt on vbt.id=bf.bedtype_id ") ;
	        				sql.append(" left join vocbedsubtype vbst on vbst.id=bf.bedsubtype_id ") ;
	        				sql.append(" where sls.dtype='HospitalMedCase' and slo.prevmedcase_id is null ") ;
	        				sql.append(" and pat.id in (").append(wqr.get2()).append(")");
	        				sql.append(" and sls.datestart").append(dat1);
	        				//if (wqr.get3()!=null) sql.append(" and ss.code=TRIM('"+wqr.get3()+"') ") ;
	        				Collection<WebQueryResult> l1 = service.executeNativeSql(sql.toString()) ;
	        				if (!l1.isEmpty()) {
	        					String medcase ;
	        					Object slss = l1.iterator().next().get1() ;
	        					if (slss!=null) {
	        						medcase = freeMedCase(hdfId,slss, service) ;
	        						if (medcase!=null) {
	        							next = false ;
	        						}
	        					}
	        				}
	        			}}
	        		}
        		} else {
        			//System.out.println("no="+wqr.get1());
        		}
    			
    		}
    	}
        return aMapping.findForward("success") ;
    }
    
    private String freeMedCase(Object aHDF,Object aSLSs, IWebQueryService aService) {
    	if (aSLSs== null ||(""+aSLSs).trim().equals("")) return null ;
    	String [] s = (""+aSLSs).split(",") ;
    	//System.out.println("--ПРОВЕРКА НА ЗАНЯТОСТЬ:"+aSLSs+" ДЛЯ "+aHDF) ;
    	for (String m:s) {
    		m=m.trim() ;
    		//System.out.println("----=-=-=select id from hospitaldatafond where hospitalmedcase_id='"+m+"'") ;
    		Collection<WebQueryResult> l = aService.executeNativeSql("select id from hospitaldatafond where hospitalmedcase_id='"+m+"'") ;
    		if (l.isEmpty()) {
    			//System.out.println("--------++") ;
    	    	aService.executeUpdateNativeSql("update hospitaldatafond set hospitalMedCase_id='"+m+"' where id='"+aHDF+"'");
    	    	return m ;
    		}
    	}
    	return null ;
    }
}