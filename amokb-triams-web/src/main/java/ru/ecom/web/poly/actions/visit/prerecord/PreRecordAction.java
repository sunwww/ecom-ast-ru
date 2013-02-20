package ru.ecom.web.poly.actions.visit.prerecord;


import java.awt.Graphics;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.TreeMap;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.DocAttribute;
import javax.print.attribute.DocAttributeSet;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttribute;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.OrientationRequested;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ecs.xhtml.li;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.diary.ejb.service.protocol.IKdlDiaryService;
import ru.ecom.ejb.print.IPrintService;
import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.ejb.services.util.ConvertSql;
import ru.ecom.mis.ejb.service.worker.IWorkCalendarService;
import ru.ecom.web.login.LoginInfo;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.util.StringUtil;
import ru.nuzmsh.web.struts.BaseAction;

public class PreRecordAction  extends BaseAction {
    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
    	IWebQueryService serviceWeb = Injection.find(aRequest).getService(IWebQueryService.class) ;
    	IWorkCalendarService service = Injection.find(aRequest).getService(IWorkCalendarService.class) ;
    	
		
		//System.out.println("patid="+aPatientId) ;
    	String addParam=PreRecordAction.saveData(aRequest) ;
		String username = LoginInfo.find(aRequest.getSession(true)).getUsername() ;
		Long aFunction = ConvertSql.parseLong(aRequest.getParameter("vocWorkFunction")) ; 
		Long aSpec = ConvertSql.parseLong(aRequest.getParameter("workCalendar")) ; 
		Long aDay = ConvertSql.parseLong(aRequest.getParameter("workCalendarDay")) ; 
		Long aTime = ConvertSql.parseLong(aRequest.getParameter("workCalendarTime")) ; 
		String aPatInfo = aRequest.getParameter("lastname")+" "+aRequest.getParameter("firstname")+" "+aRequest.getParameter("middlename") ;
		Long aPatientId = null;
		service.preRecordByPatient(username, aFunction, aSpec,aDay,aTime,aPatInfo,aPatientId) ;
		String sql="" ;
		sql=sql+"select wct.id as wctid,to_char(wcd.calendarDate,'dd.mm.yyyy') as wcdcalendardate, cast(wct.timeFrom as varchar(5)) as wcttimeFrom, vwf.name as vwfname, wp.lastname ||' '||wp.firstname||' '||wp.middlename as wpmiddlename " ;
		sql=sql+" , coalesce(p.lastname ||' '||substring(p.firstname,1,1)||' '||substring(p.middlename,1,1),p1.lastname ||' '||substring(p1.firstname,1,1)||' '||substring(p1.middlename,1,1)) as fio ";
		sql=sql+" , coalesce(p.patientSync,p1.patientSync) as sync, case when wct.medCase_id is null then '(Пред. запись)' else '' end as pred,wct.prePatientInfo ";
		sql=sql+" , case when m.dateStart is null then '' else '+' end as priem";
		sql=sql+", 'Каб.: '||list(wpl.name)||'.' as wpname" ;
		sql=sql+" from WorkCalendarTime wct" ; 
		sql=sql+" left join WorkCalendarDay wcd on wcd.id=wct.workCalendarDay_id " 
				+" left join Medcase m on m.id=wct.medCase_id "
				+" left join WorkCalendar wc on wc.id=wcd.workCalendar_id " 
				+" left join WorkFunction wf on wf.id=wc.workFunction_id " 
				+" left join WorkPlace_WorkFunction wpf on wpf.workFunctions_id=wf.id" 
				+" left join WorkPlace wpl on wpl.id=wpf.workPlace_id and wpl.dtype='ConsultingRoom'" 
				+" left join VocWorkFunction vwf on vwf.id=wf.workFunction_id " 
				+" left join Worker w on w.id=wf.worker_id " 
				+" left join patient wp on wp.id=w.person_id " 
				+" left join Patient p on p.id=wct.prePatient_id " 
				+" left join Patient p1 on p1.id=m.patient_id " 
				+" left join medpolicy mp on mp.patient_id=p.id where wcd.calendarDate>=CURRENT_DATE " ;
		sql=sql+" and wct.id = '"+aTime+"' " ;
		sql=sql+" group by wct.id,wct.prePatient_id,m.dateStart, wcd.calendarDate, wct.timeFrom, vwf.name, wp.lastname,wp.middlename,wp.firstname ,p.id,p.patientSync,p.lastname,p.firstname,p.middlename,p.birthday,wct.prepatientInfo,wct.medcase_id,p1.patientSync,p1.lastname,p1.firstname,p1.middlename" ;
		sql=sql+" order by wcd.calendarDate,wct.timeFrom" ;
		Collection<WebQueryResult> list = serviceWeb.executeNativeSql(sql) ;
		WebQueryResult wqr = list.isEmpty()?null:list.iterator().next() ;
		aRequest.setAttribute("wctInfo", wqr.get1()) ;
		aRequest.setAttribute("patientInfo", wqr.get9()) ;
		aRequest.setAttribute("specialistInfo", wqr.get4()) ;
		aRequest.setAttribute("specialistFioInfo", wqr.get5()) ;
		aRequest.setAttribute("dateInfo", wqr.get2()) ;
		aRequest.setAttribute("timeInfo", wqr.get3()) ;
		aRequest.setAttribute("cabInfo", wqr.get10()) ;
		aRequest.setAttribute("documentInfo", "Вам необходимо подойти за <b>10-20</b> минут до приема в регистратуру со следующими документами:") ;
		Date date = new Date() ;
		Calendar calC = Calendar.getInstance() ;
		Calendar cal1 = Calendar.getInstance() ;
		cal1.set(Calendar.DAY_OF_MONTH, 1) ;
		cal1.set(Calendar.MONTH, Calendar.JANUARY) ;
		Calendar cal2 = Calendar.getInstance() ;
		cal1.set(Calendar.DAY_OF_MONTH, 13) ;
		cal1.set(Calendar.MONTH, Calendar.JANUARY) ;
		SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm") ;
		aRequest.setAttribute("currentDate", format.format(calC.getTime())) ;
		if (calC.after(cal1)) {
			aRequest.setAttribute("additionalMessage", "С наступающим новым годом!!!") ;
		} else if (cal2.equals(cal2)) {
			aRequest.setAttribute("additionalMessage", "Со старым Новым Годом!!!") ;
		} else if (calC.after(cal2)) {
			aRequest.setAttribute("additionalMessage", "С новым годом!!! С новым счастьем!!!") ;
		}
		/*
		PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null,null) ;
		PrintService printService = PrintServiceLookup.lookupDefaultPrintService() ;
		String listPrints = "" ;
		for (int i=0;i<printServices.length;i++) {
			System.out.println("printer-->"+printServices[i].getName()) ;
			listPrints=listPrints+","+printServices[i].getName() ;
		}
		aRequest.setAttribute("prints", listPrints.length()>0?listPrints.substring(1):"") ;
		if (printService!=null) {
			System.out.println("printerDefault-->"+printService.getName()) ;
			aRequest.setAttribute("defaultPrint", printService.getName()) ;
			DocPrintJob job1 = printService.createPrintJob() ;
			PrinterJob job=PrinterJob.getPrinterJob() ;
			DocFlavor[] docFlavors = printService.getSupportedDocFlavors() ;
			for (int i=0;i<docFlavors.length;i++) {
				System.out.println(docFlavors[i].toString()) ;
			}
			DocAttributeSet docAttributes = new HashDocAttributeSet() ;
			docAttributes.add(OrientationRequested.PORTRAIT) ;
			PrintRequestAttributeSet printAttributes=new HashPrintRequestAttributeSet() ;
			DocFlavor docFlavor = DocFlavor.INPUT_STREAM.AUTOSENSE ;
			printAttributes.add(new Copies(1)) ;


			System.out.println() ;
			System.out.println() ;
			System.out.println() ;
		}*/
		/*
		IPrintService service1 = Injection.find(aRequest).getService(IPrintService.class);
		IKdlDiaryService serviceKdl = Injection.find(aRequest).getService(IKdlDiaryService.class);
		String reportKey = aMapping.getParameter() ;
		TreeMap<String, String> map = new TreeMap<String, String>();
        Enumeration en = aRequest.getParameterNames() ;
		while (en.hasMoreElements()) {
        	String key = (String) en.nextElement();
            if (key.equals("id") && false) {
            	map.put(key, convertToString(aRequest.getParameterValues(key))) ;
            } else {
            	map.put(key, aRequest.getParameter(key)) ;
            }
            
//            System.out.println("key = " + key);
//            System.out.println("aRequest.getParameter(key) = " + aRequest.getParameter(key));
        }
		String filename = service1.print(new StringBuilder().append("begunok").append("-").append("termainal").toString()
        		,true,"infomat"
        		, "SmoVisitService"
        		, "printDirectionByTime", map) ;
		System.out.println("filename="+filename) ;
		String kdl=service2.getDir("tomcat.data.dir", "/opt/tomcat/webapps/rtf");
		//File file = new File(kdl+"/"+filename) ;
		//System.out.println("size print="+file.length()) ;
		System.out.println("remote adr="+aRequest.getRemoteAddr()) ;
		System.out.println("local adr="+aRequest.getLocalAddr()) ;
		IKdlDiaryService serviceKdl = Injection.find(aRequest).getService(IKdlDiaryService.class);
		
		String remoteAddress = aRequest.getRemoteAddr() ;
		String printer = "VKP80T1" ;
		if (remoteAddress.equals("192.168.4.9")) {
			printer = "" ;
		} else if (remoteAddress.equals("192.168.4.8")) {
			printer = "" ;
		}
		
		if (remoteAddress.equals("192.168.3.241")||remoteAddress.equals("127.0.0.1")||remoteAddress.equals("192.168.3.242")) {
			
		} else {
			System.out.println("lp -d "+printer+" "+kdl+"/"+filename) ;
			serviceKdl.run("lp -d "+printer+" "+kdl+"/"+filename) ;
		}
		*/

    	return aMapping.findForward("success") ;
    }
	public static String convertToString(String aStr[]) {
		StringBuilder ret = new StringBuilder() ;
        for (int i = 0; i < aStr.length; i++) {
            ret.append(aStr[i]);
            if (i<aStr.length-1) ret.append(",") ;
        }
        return ret.toString() ;
    }
    public static String saveData(HttpServletRequest aRequest) {
		String year=aRequest.getParameter("year") ; 
		aRequest.setAttribute("year", year) ;
		//System.out.println("year="+ year) ;
		String month=aRequest.getParameter("month") ; ;
		aRequest.setAttribute("month", month) ;
		//System.out.println("month="+ month) ;
		String vocWorkFunction= aRequest.getParameter("vocWorkFunction") ;
		aRequest.setAttribute("vocWorkFunction", vocWorkFunction) ;
		//System.out.println("vocWorkFunction="+ vocWorkFunction) ;
		String department= aRequest.getParameter("department") ;
		aRequest.setAttribute("department", department) ;
		//System.out.println("department="+ department) ;
		String workCalendar = aRequest.getParameter("workCalendar") ;
		aRequest.setAttribute("workCalendar", workCalendar) ;
		//System.out.println("workCalendar="+ workCalendar) ;
		String lastname = aRequest.getParameter("lastname") ;
		aRequest.setAttribute("lastname", lastname) ;
		//System.out.println("lastname="+ lastname) ;
		String firstname = aRequest.getParameter("firstname") ;
		aRequest.setAttribute("firstname", firstname) ;
		//System.out.println("firstname="+ firstname) ;
		String middlename = aRequest.getParameter("middlename") ;
		aRequest.setAttribute("middlename", middlename) ;
		//System.out.println("middlename="+ middlename) ;
		String birthday = aRequest.getParameter("birthday") ;
		aRequest.setAttribute("birthday", birthday) ;
		//System.out.println("birthday="+ birthday) ;
		String patient = aRequest.getParameter("patient") ;
		aRequest.setAttribute("patient", patient) ;
		//System.out.println("patient="+ patient) ;
		String workCalendarDay = aRequest.getParameter("workCalendarDay") ;
		aRequest.setAttribute("workCalendarDay", workCalendarDay) ;    	
		//System.out.println("workCalendarDay="+ workCalendarDay) ;
		String path = aRequest.getServletPath() ;
		String p1 = "" ;String p2 = "",p3="" ;int path2=-1 ;
		//System.out.println(path) ;
		String[] pt = path.split("record_") ;
		if (pt.length>1) {
			System.out.println(pt[1]) ;
			p3 = pt[0] ;
			p2 = pt[0]+"record_" ;
			pt = pt[1].split(".do") ;
			p1 = pt.length>0?pt[0]:"" ;
			System.out.println("p1="+p1) ;
			System.out.println("p3="+p3) ;
			System.out.println("p2="+p2.substring(1)) ;
			if (p3.endsWith("pre_")) {
				System.out.println("Предварительная запись") ;
				aRequest.setAttribute("infoRecord", "ОФОРМЛЕНИЕ ПРЕДВАРИТЕЛЬНОЙ ЗАПИСИ.") ;
			} else {
				aRequest.setAttribute("infoRecord", "ОФОРМЛЕНИЕ ЗАПИСИ НА ПРИЁМ.") ;
			}
			aRequest.setAttribute("path_rec", p2.substring(1)) ;
			if (p1.length()==1) path2 = Integer.valueOf(p1) ;
		}
		
		StringBuilder addParam = new StringBuilder() ;
		StringBuilder addParam1 = new StringBuilder() ;
		int path3=-2 ;
		aRequest.setAttribute("stepinfo", "check") ;
		if (!StringUtil.isNullOrEmpty(lastname) && !StringUtil.isNullOrEmpty(firstname) && !StringUtil.isNullOrEmpty(middlename)) {
			aRequest.setAttribute("step0", "check") ;
			
			addParam.append("&lastname=").append(lastname) ;
			addParam.append("&firstname=").append(firstname) ;
			addParam.append("&middlename=").append(middlename) ;
			addParam.append("&birthday=").append(birthday!=null?birthday:"") ;
			addParam.append("&patient=").append(patient!=null?patient:"") ;
			addParam1.append("&lastname=").append(lastname) ;
			addParam1.append("&firstname=").append(firstname) ;
			addParam1.append("&middlename=").append(middlename) ;
			addParam1.append("&birthday=").append(birthday!=null?birthday:"") ;
			addParam1.append("&patient=").append(patient!=null?patient:"") ;
			path3=0 ;
			if (!StringUtil.isNullOrEmpty(department)) {
				aRequest.setAttribute("step1", "check") ;
				addParam.append("&department=").append(department) ;
				path3=1 ;
				if (!StringUtil.isNullOrEmpty(vocWorkFunction)&&aRequest.getParameterValues("department").length<2) {
					aRequest.setAttribute("step2", "check") ;
					addParam.append("&vocWorkFunction=").append(vocWorkFunction) ;
					path3=2 ;
					if (!StringUtil.isNullOrEmpty(workCalendar)&&aRequest.getParameterValues("vocWorkFunction").length<2) {
						aRequest.setAttribute("step3", "check") ;
						addParam.append("&workCalendar=").append(workCalendar) ;
						addParam.append("&year=").append(year) ;
						addParam.append("&month=").append(month) ;
						path3=3 ;
						if (!StringUtil.isNullOrEmpty(workCalendarDay)&&aRequest.getParameterValues("workCalendar").length<2) {
							aRequest.setAttribute("step4", "check") ;
							addParam.append("&workCalendarDay=").append(workCalendarDay) ;
							path3=4 ;
						}
					}
				}
			}
		}
		if (p2.length()>1) p2=p2.substring(1) ;
		path3++ ;
		aRequest.setAttribute("step"+(path3), "next") ;
		aRequest.setAttribute("addParam", addParam.toString()) ;
		aRequest.setAttribute("addParam1", addParam1.toString()) ;
		aRequest.setAttribute("step"+p1, "play") ;
		aRequest.setAttribute("stepclickinfo", "window.location='"+p2+"info"+".do?tmp="+addParam+"';") ;
		System.out.println("path3="+path3) ;
		
		for (int i=0;i<=path3;i++) {
			aRequest.setAttribute("stepclick"+i, "window.location='"+p2+i+".do?tmp="+addParam+"';") ;
		}
		return addParam.toString() ;
    }
}
