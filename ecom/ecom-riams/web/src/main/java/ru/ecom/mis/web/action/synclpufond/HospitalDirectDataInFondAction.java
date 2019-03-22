package ru.ecom.mis.web.action.synclpufond;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.ejb.services.util.ConvertSql;
import ru.ecom.mis.ejb.service.medcase.IHospitalMedCaseService;
import ru.ecom.mis.web.action.bypassexport.AttachmentByLpuForm;
import ru.ecom.web.util.ActionUtil;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.util.format.DateFormat;
import ru.nuzmsh.web.messages.InfoMessage;
import ru.nuzmsh.web.struts.BaseAction;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

public class HospitalDirectDataInFondAction extends BaseAction {
    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
    	AttachmentByLpuForm form =(AttachmentByLpuForm)aForm ;
    	String typeMode=ActionUtil.updateParameter("HospitalDirectDataInFond","typeMode","1", aRequest) ;
    	String typeImport=ActionUtil.updateParameter("HospitalDirectDataInFond","typeImport","1", aRequest) ;
    	String typeLoad=ActionUtil.updateParameter("HospitalDirectDataInFond","typeLoad","1", aRequest) ;
    	// Export xml
    	boolean isSaveInFolder = typeLoad.equals("2") ;
    	if (form!=null && typeMode!=null && typeMode.equals("1")) {
    		if (form.getLpu()!=null && !form.getLpu().equals(0L)) {
    		IHospitalMedCaseService service = Injection.find(aRequest).getService(IHospitalMedCaseService.class);
	    	String typeView = ActionUtil.updateParameter("HospitalDirectDataInFond","typeView","1", aRequest) ; 
	    	//SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy") ;
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
	        if ("1".equals(typeView)) {
	        	WebQueryResult wqr = service.exportN1(format_n.format(cal.getTime()),format_n.format(calTo.getTime()), format1.format(calTo.getTime()), form.getNumberReestr(),null,true,false,isSaveInFolder) ;
	        	filename= null;
	        	StringBuilder sb = new StringBuilder() ;
        		sb.append("<a href='../rtf/").append(wqr.get1()).append("'>").append(wqr.get1()).append("</a>").append("</br>") ;
        		if (wqr.get2()!=null) sb.append("<a href='../rtf/").append(wqr.get2()).append("'>").append(wqr.get2()).append("</a>").append("</br>") ;
        		if (wqr.get3()!=null) sb.append("<a href='../rtf/").append(wqr.get3()).append("'>").append(wqr.get3()).append("</a>").append("</br>") ;
        		form.setFilename(sb.toString()) ;
	        	aRequest.setAttribute("listExist", wqr.get4()) ;
	        	aRequest.setAttribute("listError", wqr.get5()) ;
	        } else if ("2".equals(typeView)) {
	        	WebQueryResult wqr = service.exportN2(format_n.format(cal.getTime()),format_n.format(calTo.getTime()), format1.format(calTo.getTime()), form.getNumberReestr(),null,isSaveInFolder) ;
	        	filename= null;
	        	StringBuilder sb = new StringBuilder() ;
        		sb.append("<a href='../rtf/").append(wqr.get1()).append("'>").append(wqr.get1()).append("</a>").append("</br>") ;
        		if (wqr.get2()!=null) sb.append("<a href='../rtf/").append(wqr.get2()).append("'>").append(wqr.get2()).append("</a>").append("</br>") ;
        		if (wqr.get3()!=null) sb.append("<a href='../rtf/").append(wqr.get3()).append("'>").append(wqr.get3()).append("</a>").append("</br>") ;
        		form.setFilename(sb.toString()) ;
	        	aRequest.setAttribute("listExist", wqr.get4()) ;
	        	aRequest.setAttribute("listError", wqr.get5()) ;
	        } else if ("3".equals(typeView)) {
	        	WebQueryResult wqr = service.exportN3(format_n.format(cal.getTime()), 
	    	        	format_n.format(calTo.getTime()), format1.format(calTo.getTime()), form.getNumberReestr(),null,isSaveInFolder) ;
	        	filename=null ;
	        	StringBuilder sb = new StringBuilder() ;
	        	sb.append("<a href='../rtf/").append(wqr.get1()).append("'>").append(wqr.get1()).append("</a>").append("</br>") ;
        		if (wqr.get2()!=null) sb.append("<a href='../rtf/").append(wqr.get2()).append("'>").append(wqr.get2()).append("</a>").append("</br>") ;
        		if (wqr.get3()!=null) sb.append("<a href='../rtf/").append(wqr.get3()).append("'>").append(wqr.get3()).append("</a>").append("</br>") ;
        		form.setFilename(sb.toString()) ;
	        	aRequest.setAttribute("listExist", wqr.get4()) ;
	        	aRequest.setAttribute("listError", wqr.get5()) ;
	        } else if ("4".equals(typeView)) {
	        	WebQueryResult wqr = service.exportN1_planHosp(format_n.format(cal.getTime()),format_n.format(calTo.getTime()), format1.format(calTo.getTime()), form.getNumberReestr(),null,isSaveInFolder) ;
		        	WebQueryResult wqr1 = service.exportN2_plan_otherLpu(format_n.format(cal.getTime()),format_n.format(calTo.getTime()), format1.format(calTo.getTime()), form.getNumberReestr(),null,isSaveInFolder) ;
		        	filename= null;
		        	StringBuilder sb = new StringBuilder() ;
		        	sb.append("<a href='../rtf/").append(wqr.get1()).append("'>").append(wqr.get1()).append("</a>").append("</br>") ;
	        		sb.append("<a href='../rtf/").append(wqr1.get1()).append("'>").append(wqr1.get1()).append("</a>").append("</br>") ;
	        		if (wqr.get2()!=null) sb.append("<a href='../rtf/").append(wqr.get2()).append("'>").append(wqr.get2()).append("</a>").append("</br>") ;
	        		if (wqr1.get2()!=null) sb.append("<a href='../rtf/").append(wqr1.get2()).append("'>").append(wqr1.get2()).append("</a>").append("</br>") ;
	        		if (wqr.get3()!=null) sb.append("<a href='../rtf/").append(wqr.get3()).append("'>").append(wqr.get3()).append("</a>").append("</br>") ;
	        		if (wqr1.get3()!=null) sb.append("<a href='../rtf/").append(wqr1.get3()).append("'>").append(wqr1.get3()).append("</a>").append("</br>") ;
	        		form.setFilename(sb.toString()) ;
	        		aRequest.setAttribute("listExist", wqr.get4()) ;
	        		aRequest.setAttribute("listError", wqr.get5()) ;
		        	aRequest.setAttribute("listExist1", wqr1.get4()) ;
		        	aRequest.setAttribute("listError1", wqr1.get5()) ;
		        	
	        } else if ("5".equals(typeView)) {
	        	filename=service.exportN4(format_n.format(cal.getTime()), 
	    	        	format_n.format(calTo.getTime()), format1.format(calTo.getTime()), form.getNumberReestr(),null,isSaveInFolder) ;
	        } else if ("6".equals(typeView)) {
	        	//TODO доделять переводы внутри ЛПУ
	        	WebQueryResult wqr = service.exportN2_trasferInLpu(format_n.format(cal.getTime()), 
	    	        	format_n.format(calTo.getTime()), format1.format(calTo.getTime()), form.getNumberReestr(),null,isSaveInFolder) ;
	        	filename= null;
	        	StringBuilder sb = new StringBuilder() ;
	        	sb.append("<a href='../rtf/").append(wqr.get1()).append("'>").append(wqr.get1()).append("</a>").append("</br>") ;
	        	if (wqr.get2()!=null) sb.append("<a href='../rtf/").append(wqr.get2()).append("'>").append(wqr.get2()).append("</a>").append("</br>") ;
	        	if (wqr.get3()!=null) sb.append("<a href='../rtf/").append(wqr.get3()).append("'>").append(wqr.get3()).append("</a>").append("</br>") ;
	        	form.setFilename(sb.toString()) ;
	        	aRequest.setAttribute("listExist", wqr.get4()) ;
	        	aRequest.setAttribute("listError", wqr.get5()) ;
	        } else if ("7".equals(typeView)) {
	        	filename=service.exportN5(format_n.format(cal.getTime()), 
	        			format_n.format(calTo.getTime()), format1.format(calTo.getTime()), form.getNumberReestr(),null,isSaveInFolder) ;
	        } else if ("8".equals(typeView)) {
	        	WebQueryResult wqr = service.exportN1(format_n.format(cal.getTime()),format_n.format(calTo.getTime()), format1.format(calTo.getTime()), form.getNumberReestr(),null,false,true,isSaveInFolder) ;
	        	filename= null;
	        	StringBuilder sb = new StringBuilder() ;
	        	sb.append("<a href='../rtf/").append(wqr.get1()).append("'>").append(wqr.get1()).append("</a>").append("</br>") ;
	        	if (wqr.get2()!=null) sb.append("<a href='../rtf/").append(wqr.get2()).append("'>").append(wqr.get2()).append("</a>").append("</br>") ;
	        	if (wqr.get3()!=null) sb.append("<a href='../rtf/").append(wqr.get3()).append("'>").append(wqr.get3()).append("</a>").append("</br>") ;
	        	form.setFilename(sb.toString()) ;
	        	aRequest.setAttribute("listExist", wqr.get4()) ;
	        	aRequest.setAttribute("listError", wqr.get5()) ;
	        } else if ("9".equals(typeView)) {
	        	filename=service.exportN6(format_n.format(cal.getTime()), 
	    	        	format_n.format(calTo.getTime()), format1.format(calTo.getTime()), form.getNumberReestr(),null,isSaveInFolder) ;
	        } else if ("10".equals(typeView)) {
	        	WebQueryResult[] filenameList=service.exportFondZip23(format_n.format(cal.getTime()), 
	    	        	format_n.format(calTo.getTime()), format1.format(calTo.getTime()), form.getNumberReestr(),isSaveInFolder) ;
	        	StringBuilder sb = new StringBuilder() ;
	        	for (WebQueryResult wqr:filenameList) {
	        		sb.append("<a href='../rtf/").append(wqr.get1()).append("'>").append(wqr.get1()).append("</a>").append("</br>") ;
	        		if (wqr.get2()!=null) sb.append("<a href='../rtf/").append(wqr.get2()).append("'>").append(wqr.get2()).append("</a>").append("</br>") ;
	        		if (wqr.get3()!=null) sb.append("<a href='../rtf/").append(wqr.get3()).append("'>").append(wqr.get3()).append("</a>").append("</br>") ;
		        	//aRequest.setAttribute("listExist", fn.get4()) ;
		        	//aRequest.setAttribute("listError", fn.get5()) ;
	        	}
	        	form.setFilename(sb.toString()) ;
	        } else if ("11".equals(typeView)) {
	        	String[] filenameList=service.exportFondZip45(format_n.format(cal.getTime()), 
	    	        	format_n.format(calTo.getTime()), format1.format(calTo.getTime()), form.getNumberReestr(),isSaveInFolder) ;
	        	StringBuilder sb = new StringBuilder() ;
	        	for (String fn:filenameList) {
	        		sb.append("<a href='../rtf/").append(fn).append("'>").append(fn).append("</a>").append("</br>") ;
	        	}
        		form.setFilename(sb.toString()) ;
	        } else if ("12".equals(typeView)) {
	        	WebQueryResult wqr = service.exportN0(format_n.format(cal.getTime()),format_n.format(calTo.getTime()), format1.format(calTo.getTime()), form.getNumberReestr(),null,"N1",isSaveInFolder) ;
	        	filename= null;
				form.setFilename("<a href='../rtf/"+wqr.get1().toString()+"'>"+wqr.get1().toString()+"</a>"+"</br>") ;
	        } else if ("13".equals(typeView)) {
	        	WebQueryResult wqr = service.exportN0(format_n.format(cal.getTime()),format_n.format(calTo.getTime()), format1.format(calTo.getTime()), form.getNumberReestr(),null,"N2",isSaveInFolder) ;
	        	filename= null;
				form.setFilename("<a href='../rtf/"+wqr.get1().toString()+"'>"+wqr.get1().toString()+"</a>"+"</br>") ;
	        } else if ("14".equals(typeView)) {
	        	WebQueryResult wqr = service.exportN0(format_n.format(cal.getTime()),format_n.format(calTo.getTime()), format1.format(calTo.getTime()), form.getNumberReestr(),null,"N5",isSaveInFolder) ;
	        	filename= null;
				form.setFilename("<a href='../rtf/"+wqr.get1().toString()+"'>"+wqr.get1().toString()+"</a>"+"</br>") ;
	        } else if ("15".equals(typeView)) {
	        	WebQueryResult wqr = service.exportN0(format_n.format(cal.getTime()),format_n.format(calTo.getTime()), format1.format(calTo.getTime()), form.getNumberReestr(),null,"N8",isSaveInFolder) ;
	        	filename= null;
        		form.setFilename("<a href='../rtf/"+wqr.get1().toString()+"'>"+wqr.get1().toString()+"</a>"+"</br>") ;
	        
	        }
	        if (filename!=null) form.setFilename("<a href='../rtf/"+filename+"'>"+filename+"</a>") ;
        }
    	} else if ("2".equals(typeMode)) {
    		if (typeImport.equals("1")||typeImport.equals("3")) update(aRequest) ;
    		new InfoMessage(aRequest, aRequest.getParameter("infoImport")) ;
    		String errorFile = aRequest.getParameter("errorFile") ;
    		if (errorFile!=null && !errorFile.equals("") && !errorFile.equals("null")) {
    			form.setFilenameError("<a href='../rtf/"+errorFile+"'>"+errorFile+"</a>") ;
    		}
    	} /*else if (typeMode!=null && typeMode.equals("3")) {
    		
    	} else if (typeMode!=null && typeMode.equals("4")) {
    		
    	}*/
        return aMapping.findForward(SUCCESS) ;
    }
    private void update(HttpServletRequest aRequest) throws NamingException {
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
						sql.append(" left join medcase_medpolicy mcmp on mcmp.medcase_id=sls.id ") ;
						sql.append(" left join medpolicy mp on mcmp.policies_id=mp.id ") ;
						sql.append(" left join statisticstub ss on ss.id=sls.statisticstub_id ") ;
						sql.append(" left join patient pat on pat.id=sls.patient_id ") ;
						sql.append(" left join patient pat1 on pat1.id=mp.patient_id ") ;
						sql.append(" left join medcase slo on slo.parent_id=sls.id and slo.dtype='DepartmentMedCase' ") ;
						sql.append(" left join bedfund bf on bf.id=slo.bedfund_id ") ;
						sql.append(" left join vocbedtype vbt on vbt.id=bf.bedtype_id ") ;
						sql.append(" left join vocbedsubtype vbst on vbst.id=bf.bedsubtype_id ") ;
						sql.append(" where sls.dtype='HospitalMedCase' and slo.prevmedcase_id is null ") ;
						sql.append(" and (pat.id in (").append(wqr.get2()).append(") or pat1.id in (").append(wqr.get2()).append(")) ");
						sql.append(" and sls.datestart").append(dat1);
						if (wqr.get3()!=null) sql.append(" and ss.code='").append(wqr.get3()).append("' ");
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
        				sql.append(" left join medcase_medpolicy mcmp on mcmp.medcase_id=sls.id ") ;
						sql.append(" left join medpolicy mp on mcmp.policies_id=mp.id ") ;
						sql.append(" left join patient pat1 on pat1.id=mp.patient_id ") ;
						sql.append(" left join medcase slo on slo.parent_id=sls.id and slo.dtype='DepartmentMedCase' ") ;
        				sql.append(" left join bedfund bf on bf.id=slo.bedfund_id ") ;
        				sql.append(" left join vocbedtype vbt on vbt.id=bf.bedtype_id ") ;
        				sql.append(" left join vocbedsubtype vbst on vbst.id=bf.bedsubtype_id ") ;
        				sql.append(" where sls.dtype='HospitalMedCase' and slo.prevmedcase_id is null ") ;
        				sql.append(" and (pat.id in (").append(wqr.get2()).append(") or pat1.id in (").append(wqr.get2()).append("))");
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
    		}
			
		}
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