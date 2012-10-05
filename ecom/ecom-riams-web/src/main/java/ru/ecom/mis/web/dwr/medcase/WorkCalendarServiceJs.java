package ru.ecom.mis.web.dwr.medcase;

import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import javax.naming.NamingException;
import javax.print.attribute.standard.Finishings;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

import org.jdom.IllegalDataException;

import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.ejb.services.util.ConvertSql;
import ru.ecom.mis.ejb.service.medcase.IPolyclinicMedCaseService;
import ru.ecom.mis.ejb.service.worker.IWorkCalendarService;
import ru.ecom.mis.ejb.service.worker.IWorkerService;
import ru.ecom.mis.ejb.service.worker.TableTimeBySpecialists;
import ru.ecom.web.login.LoginInfo;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.util.format.DateFormat;
import ru.nuzmsh.web.tags.helper.RolesHelper;

public class WorkCalendarServiceJs {
	public static boolean remoteUser(IWebQueryService service,HttpServletRequest aRequest) {
		String username = LoginInfo.find(aRequest.getSession(true)).getUsername() ;
		boolean ret = false ;
		Collection<WebQueryResult> list = service.executeNativeSql("select case when isRemoteUser='1' then 1 else null end as remote,id from secUser where login='"+username+"'",1) ;
		WebQueryResult wqr = list.iterator()!=null?list.iterator().next():null ;
		if (wqr!=null && wqr.get1()!=null) return true ;
		return ret;
	}
	public String checkPolicyByPatient(Long aPatientId,String aDatePlan, Long aServiceStream, HttpServletRequest aRequest) throws NamingException {
		System.out.println(aPatientId) ;
		System.out.println(aDatePlan) ;
		System.out.println(aServiceStream) ;
		aDatePlan = aDatePlan.trim() ;
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		String sql = "select id,code from VocServiceStream where id='"+aServiceStream+"' and code='OBLIGATORYINSURANCE'" ;
		Collection<WebQueryResult> list1 = service.executeNativeSql(sql.toString(),1) ;
		if (list1.size()>0) {
			sql = "SELECT id,dtype " 
	                +"FROM MedPolicy where patient_id='"+aPatientId+"' "
	                +"AND actualDateFrom<=to_date('"+aDatePlan+"','dd.mm.yyyy') and (actualDateTo is null or actualDateTo>=to_date('"+aDatePlan+"','dd.mm.yyyy')) "
	                +"and DTYPE like 'MedPolicyOmc%'" ;
			System.out.println(sql) ;
			Collection<WebQueryResult> list = service.executeNativeSql(sql.toString(),1) ;
			if (list.size()==0) return "1" ;
			if (list.size()>1) return "2" ;
		}
		return "0" ;
	}
	public String getInfoByWorkFunction(Long aWorkFunction,HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		StringBuilder sql = new StringBuilder() ;
		sql.append("select wp.lastname,wp.firstname,wp.middlename,to_char(wp.birthday,'ddMMyyyy'),vwf.name as vwfname,lpu.name as lpuname")
		.append(" from WorkFunction wf left join VocWorkFunction vwf on vwf.id=wf.workFunction_id left join Worker w on w.id=wf.worker_id left join MisLpu lpu on lpu.id=w.lpu_id left join Patient wp on wp.id=w.person_id where wf.id='").append(aWorkFunction).append("'") ;
		 Collection<WebQueryResult> list = service.executeNativeSql(sql.toString(),1) ;
		for (WebQueryResult res:list) {
			String lastname = ""+res.get1() ;
			String firstname = ""+res.get2() ;
			String user = firstname.substring(0,1) +lastname;
			user = ConvertSql.translate(user) ;
			return user+"#"+res.get1()+" "+res.get2()+" "+res.get3()+"#"+res.get4()+"#"+res.get5()+" "+res.get6() ;
		}
        return null ;
	}
	public String deleteEmptyCalendarDays(String aDate1, String aDate2, Long aSpecialist,HttpServletRequest aRequest) throws NamingException, ParseException {
		IWorkCalendarService service = Injection.find(aRequest).getService(IWorkCalendarService.class) ;
		Date beginDate = DateFormat.parseSqlDate(aDate1) ;
		if (aDate2==null || aDate2.equals("")) aDate2=aDate1 ;
		Date finishDate = DateFormat.parseSqlDate(aDate2) ;
		java.sql.Date dateCur = new java.sql.Date(new java.util.Date().getTime()) ;
		if (dateCur.getTime()>beginDate.getTime()) throw new IllegalArgumentException("дата начала должна быть больше текущей") ;
		if (dateCur.getTime()>finishDate.getTime()) throw new IllegalArgumentException("дата окончания должна быть больше текущей") ;
		service.deleteEmptyCalendarDays(aSpecialist, beginDate, finishDate);
		return "Удалено" ;
	}
	public String moveDatePlanBySpec(String aDate1, String aDate2,Long aSpecialist,HttpServletRequest aRequest) throws NamingException, ParseException {
		IWorkCalendarService service = Injection.find(aRequest).getService(IWorkCalendarService.class) ;
		Date beginDate = DateFormat.parseSqlDate(aDate1) ;
		Date finishDate = DateFormat.parseSqlDate(aDate2) ;
		java.sql.Date dateCur = new java.sql.Date(new java.util.Date().getTime()) ;
		if (dateCur.getTime()>beginDate.getTime()) throw new IllegalArgumentException("дата начала должна быть больше текущей") ;
		if (dateCur.getTime()>finishDate.getTime()) throw new IllegalArgumentException("дата окончания должна быть больше текущей") ;
		service.moveDate(aSpecialist, beginDate, finishDate);
		return "Изменено" ;
	}
	public String moveSpecialistPlanByDate(String aDateFrom, String aDateTo, Long aSpecialist1,Long aSpecialist2,HttpServletRequest aRequest) throws NamingException, ParseException {
		IWorkCalendarService service = Injection.find(aRequest).getService(IWorkCalendarService.class) ;
		if (aDateTo==null || aDateTo.equals("")) aDateTo=aDateFrom ;
		Date dateTo = DateFormat.parseSqlDate(aDateTo) ;
        Date dateFrom = DateFormat.parseSqlDate(aDateFrom) ;
		java.sql.Date dateCur = new java.sql.Date(new java.util.Date().getTime()) ;
		if (dateCur.getTime()>dateTo.getTime()) throw new IllegalArgumentException("дата начала должна быть больше текущей") ;
		if (dateCur.getTime()>dateFrom.getTime()) throw new IllegalArgumentException("дата окончания должна быть больше текущей") ;
		//if (dateCur.getTime()>finishDate.getTime()) throw new IllegalArgumentException("дата окончания должна быть больше текущей") ;
		service.moveSpecialist(aSpecialist1, aSpecialist2, dateFrom, dateTo);
		return "Изменено" ;
	}
	public int deleteWorkCalendarTime(Long aTimeId,HttpServletRequest aRequest) throws NamingException {
		IWorkCalendarService service = Injection.find(aRequest).getService(IWorkCalendarService.class) ;
		service.deleteWorkCalendarTime(aTimeId) ;
		return 1 ;
	}
	public String findDoubleBySpecAndDate(Long aId, Long aPatient, Long aSpec, String aDate, HttpServletRequest aRequest) throws NamingException, Exception {
		//ITicketService service = Injection.find(aRequest).getService(ITicketService.class) ;
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		StringBuilder sql = new StringBuilder() ;
        sql.append("select m.id,p.lastname|| ' ' || p.firstname|| ' ' || p.middlename||' '||to_char(p.birthday,'dd.mm.yyyy'),to_char(wcd.calendardate,'dd.mm.yyyy'),cast(wct.timeFrom as varchar(5)),vwf.name|| ' ' || wp.lastname|| ' ' || wp.firstname|| ' ' || wp.middlename")
        	.append(" from MedCase as m ")
        	.append(" left join WorkCalendarDay wcd on wcd.id=m.datePlan_id")
        	.append(" left join WorkCalendarTime wct on wct.id=m.timePlan_id")
			.append(" left join workfunction as wf on wf.id=m.workfunctionPlan_id")
			.append(" left join VocWorkFunction as vwf on vwf.id=wf.workFunction_id")
			.append(" left join worker as w on wf.worker_id=w.id")
			.append(" left join patient as wp on wp.id = w.person_id")
			.append(" left join patient as p on p.id = m.patient_id")
			.append(" where m.patient_id='").append(aPatient)
			.append("' and m.dtype='Visit' and m.workFunctionPlan_id='")
			.append(aSpec).append("' and m.datePlan_id='").append(aDate).append("' ") ;
        if (aId!=null && aId>Long.valueOf(0)) sql.append(" and m.id!=").append(aId);

        Collection<WebQueryResult> doubles = service.executeNativeSql(sql.toString()) ;
        	
        	
        if (doubles.size()>0) {
        	StringBuilder ret = new StringBuilder() ;
			ret.append("<br/><ol>") ;
			for (WebQueryResult res:doubles) {
				ret.append("<li>")
				.append("<a href='entitySubclassView-mis_medCase.do?id=").append(res.get1())
				.append("'>пациент: ")
				.append(res.get2())
				.append(" дата приема ").append(res.get3()).append(" ").append(res.get4()).append(" ")
				.append(" специал. ").append(res.get5())
				.append("</a>")
				.append("</li>") ;
			}
			ret.append("</ol><br/>") ;
			return ret.toString() ;
        } 
        return null ;
		
		//return service.findDoubleBySpecAndDate(aId , aMedcard, aSpec, aDate) ;
	}
	public String getWorkFunctionByUsername(HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		String username = LoginInfo.find(aRequest.getSession(true)).getUsername() ;
		StringBuilder sql = new StringBuilder() ;
		sql.append("select wf1.id as wf1id,vwf.name ||' '||coalesce(wp.lastname||' '||wp.middlename||' '||wp.firstname,wf.groupName) as workFunction ") ;
		sql.append(" from WorkFunction wf")
			.append(" left join SecUser su on su.id=wf.secUser_id ")
			.append(" left join Worker w on w.id=wf.worker_id")
			.append(" left join worker w1 on w1.person_id=w.person_id")
			.append(" left join WorkFunction wf1 on wf1.worker_id=w1.id")
			.append(" left join WorkCalendar wc1 on wf1.id=wc1.workFunction_id")
			.append(" left join VocWorkFunction vwf on vwf.id=wf1.workFunction_id")
			.append(" left join patient wp on wp.id=w1.person_id ")
			.append("where su.login='").append(username).append("' and wc1.id is not null ") ;
		Collection<WebQueryResult> list = service.executeNativeSql(sql.toString());
		StringBuilder res = new StringBuilder() ;
		res.append("<ul>") ;
		for (WebQueryResult wqr:list) {
			
			res.append("<li onclick=\"this.childNodes[1].checked='checked';get10DaysByWorkFunction('")
				.append(wqr.get1()).append("','")
				.append(wqr.get2()).append("')\">") ;
			res.append(" <input class='radio' type='radio' name='rdFunc' id='rdFunc' ") ;

			res.append(" value='")
			.append(wqr.get1()).append("#").append(wqr.get2()).append("#").append(wqr.get3())
			.append("'>") ;
			res.append(wqr.get2()) ;
			res.append("</li>") ;
		}
		res.append("</ul><b>Выберите день:</b><div id='divDayByWorkFunction'><i>Выберите рабочую функцию</i></div>") ;
		return res.toString() ;
	}
	public String get10DaysByWorkFunction(Long aWorkFunction, HttpServletRequest aRequest) throws NamingException  {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		StringBuilder sql = new StringBuilder() ;
		Calendar cal = Calendar.getInstance() ;
		SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy") ;
		sql.append("select wcd.id as wcdid, to_char(wcd.calendarDate,'dd.mm.yyyy') as wcdcalendardate ") ;
		sql.append(" from WorkCalendarDay wcd left join WorkCalendar wc on wc.id=wcd.workCalendar_id where wc.workFunction_id='").append(aWorkFunction).append("' and wcd.calendarDate between to_date('")
		.append(format.format(cal.getTime()))
		.append("','dd.mm.yyyy') and to_date('");
		cal.add(Calendar.DATE, 14) ;
		sql.append(format.format(cal.getTime()))
		.append("','dd.mm.yyyy') order by wcd.calendarDate") ;
		Collection<WebQueryResult> list = service.executeNativeSql(sql.toString());
		StringBuilder res = new StringBuilder() ;
		res.append("<ul>") ;
		for (WebQueryResult wqr:list) {
			
			res.append("<li onclick=\"this.childNodes[1].checked='checked';getTimeByDayAndWorkFunction('")
				.append(aWorkFunction).append("','").append(wqr.get1()).append("')\">") ;
			res.append(" <input class='radio' type='radio' name='rdDay' id='rdDay' ") ;
			res.append(" value='").append(wqr.get1()).append("#").append(wqr.get2()).append("#").append(wqr.get3()).append("'>") ;
			res.append(wqr.get2()) ;
			res.append("</li>") ;
		}
		res.append("</ul><b>Добавить после времени:</b><div id='divTimeByDayAndWorkFunction'><i>Выберите день</i></div>") ;
		return res.toString() ;		
	}
	public String getTimeByDayAndWorkFunction(Long aWorkFunction, Long aWorkCalendarDay,HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		StringBuilder sql = new StringBuilder() ;

		sql.append("select to_char(wcd.calendarDate,'dd.mm.yyyy') as wcdid, cast(wct.timeFrom as varchar(5)) as wcttimeFrom,cast(min(wct1.timeFrom) as varchar(5)) as wct1timeFrom ") ;
		sql.append(",vwf.name as vwfname,coalesce(wp.lastname||' '||wp.firstname||' '||coalesce(wp.middlename,''),wf.groupName) as wfInfo")
		.append(" from WorkCalendarTime wct ")
		.append(" left join WorkCalendarTime wct1 on wct1.workCalendarDay_id=wct.workCalendarDay_id")
		.append(" left join WorkCalendarDay wcd on wcd.id=wct.workCalendarDay_id")
		.append(" left join WorkCalendar wc on wc.id=wcd.workCalendar_id")
		.append(" left join WorkFunction wf on wf.id=wc.workFunction_id")
		.append(" left join VocWorkFunction vwf on vwf.id=wf.workFunction_id")
		.append(" left join Worker w on w.id=wf.worker_id")
		.append(" left join Patient wp on wp.id=w.person_id")
		.append(" where wc.workFunction_id='").append(aWorkFunction)
		.append("' and wcd.id = '").append(aWorkCalendarDay)
		.append("' and wct1.timeFrom>wct.timeFrom")
		.append(" group by wct.id,wcd.calendarDate,wct.timeFrom,vwf.name,wp.lastname,wp.firstname,wp.middlename,wf.groupName order by wct.timeFrom")
		;
		Collection<WebQueryResult> list = service.executeNativeSql(sql.toString());
		StringBuilder res = new StringBuilder() ;
		res.append("<ul>") ;
		for (WebQueryResult wqr:list) {
			res.append("<li ondblclick=\"this.childNodes[1].checked='checked';addNewTimeBySpecialist('")
				.append(aWorkCalendarDay).append("','")
				.append(wqr.get1()).append("','")
				.append(aWorkFunction).append("','")
				.append(wqr.get2()).append("','")
				.append(wqr.get3()).append("','")
				.append(wqr.get4()).append(" ").append(wqr.get5())
				.append("')\">") ;
			res.append(" <input style='display:none' class='radio' type='radio' name='rdTime' id='rdTime' ") ;
			res.append(" value='").append(wqr.get1()).append("#").append(wqr.get2()).append("#").append(wqr.get3()).append("'>") ;
			res.append(wqr.get2()).append("-").append(wqr.get3()) ;
			res.append("<input type='button' onclick=\"addNewTimeBySpecialist('")
				.append(aWorkCalendarDay).append("','")
				.append(wqr.get1()).append("','")
				.append(aWorkFunction).append("','")
				.append(wqr.get2()).append("','")
				.append(wqr.get3()).append("','")
				.append(wqr.get4()).append(" ").append(wqr.get5())
				.append("')\" value='Добавить'/>") ;
			res.append("</li>") ;
		}
		res.append("</ul>") ;
		return res.toString() ;		
	}
	
	
	public String getDataByTime(Long aTime,HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		StringBuilder sql = new StringBuilder() ;
		sql.append("select wf.id as wfid,vwf.name ||' '||coalesce(wp.lastname||' '||wp.middlename||' '||wp.firstname,wf.groupName) as workFunction, wcd.id as wcdid, to_char(wcd.calendarDate,'dd.mm.yyyy') as wcdcaldate,wct.id as wctid,  cast(wct.timeFrom as varchar(5)) as timefrom ") ;
		sql.append(" from WorkCalendarTime wct left join WorkCalendarDay wcd on wcd.id=wct.workCalendarDay_id left join WorkCalendar wc on wc.id=wcd.workCalendar_id left join WorkFunction wf on wf.id=wc.workFunction_id left join VocWorkFunction vwf on vwf.id=wf.workFunction_id left join Worker w on w.id=wf.worker_id left join patient wp on wp.id=w.person_id where wct.id='").append(aTime).append("' ") ;
		Collection<WebQueryResult> list = service.executeNativeSql(sql.toString(),1);
		StringBuilder res = new StringBuilder() ;
		if (!list.isEmpty()) {
			WebQueryResult wqr = list.iterator().next() ;
			res.append(wqr.get1()).append("#") ;
			res.append(wqr.get2()).append("#") ;
			res.append(wqr.get3()).append("#") ;
			res.append(wqr.get4()).append("#") ;
			res.append(wqr.get5()).append("#") ;
			res.append(wqr.get6()).append("#") ;
			
		}
		return res.toString() ; 
	}
	public String getPreRecord(Long aWorkCalendarDay, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		StringBuilder sql = new StringBuilder() ;
		sql.append("select wct.id, cast(wct.timeFrom as varchar(5)), coalesce(") ;
		sql.append(" prepat.lastname ||coalesce(' ('||prepat.patientSync||')',' ('||prepat.id||')')") ;
		sql.append(",wct.prepatientInfo)") ;
		sql.append(" as fio from WorkCalendarTime wct ")
			.append(" left join Patient prepat on prepat.id=wct.prepatient_id ")
			.append(" where wct.workCalendarDay_id='").append(aWorkCalendarDay).append("' ") ;
		sql.append(" and wct.medCase_id is null and (wct.prepatient_id is not null or (wct.prepatientinfo is not null and wct.prepatientinfo!=''))") ;
		Collection<WebQueryResult> list = service.executeNativeSql(sql.toString(),50);
		StringBuilder res = new StringBuilder() ;
		res.append("<table border=1><tr><th>Предварительно записанные к специалисту</th><th>Оформленные вместо других пациентов</th></tr><tr><td><ul>") ;
		for (WebQueryResult wqr:list) {
			
			res.append("<li onclick=\"this.childNodes[1].checked='checked';checkRecord('")
				.append(wqr.get1()).append("','")
				.append(wqr.get2()).append("')\">") ;
			res.append(" <input class='radio' type='radio' name='rdTime' id='rdTime' ") ;

			res.append(" value='")
			.append(wqr.get1()).append("#").append(wqr.get2()).append("#").append(wqr.get3())
			.append("'>") ;
			res.append(wqr.get3()).append(" (").append(wqr.get2()).append(")") ;
			res.append("</li>") ;
		}
		res.append("</ul></td><td><ul>") ;
		list.clear() ;
		sql = new StringBuilder() ;
		sql.append("select wct.id, cast(wct.timeFrom as varchar(5)), coalesce(") ;
		sql.append(" prepat.lastname ||coalesce(' ('||prepat.patientSync||')',' ('||prepat.id||')'),wct.prepatientInfo) as prepat") ;
		sql.append(",pc.lastname ||coalesce(' ('||pc.patientSync||')',' ('||pc.id||')') as camepat") ;
		sql.append("") ;
		sql.append(" from WorkCalendarTime wct left join MedCase m on m.id=wct.medCase_id left join Patient pc on pc.id=m.patient_id left join Patient prepat on prepat.id=wct.prepatient_id where wct.workCalendarDay_id='").append(aWorkCalendarDay).append("' ") ;
		sql.append(" and wct.medCase_id is not null and (wct.prepatient_id is not null and m.patient_id!=wct.prepatient_id")
		.append(" or (wct.prepatientinfo is not null and wct.prepatientinfo!='' and wct.prepatientinfo not like pc.lastname||' %'))") ;
		
		list = service.executeNativeSql(sql.toString(),50);

		for (WebQueryResult wqr:list) {
			
			res.append("<li style='padding-left:10px;list-style: none'><i>") ;
			//res.append(" <input class='radio' type='radio' name='rdTime' id='rdTime' ") ;

			//res.append(" value='")
			//.append(wqr.get1()).append("#").append(wqr.get2()).append("#").append(wqr.get3())
			//.append("'>") ;
			res.append(wqr.get4()).append(" вместо ").append(wqr.get3()).append("") ;
			res.append("</i></li>") ;
		}
		res.append("</ul></td></tr></table>") ;
		return res.toString() ;
		
	}
	public String getDirectByPatient(String aLastname, String aFirstname, String aMiddlename
			, String aBirthday, String aPolicySeries, String aPolicyNumber
			,Long aRayon, String aRayonName, HttpServletRequest aRequest) throws NamingException {
		StringBuilder sql = new StringBuilder() ;
		StringBuilder preInfo = new StringBuilder() ;
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		sql.append("select wct.id as wctid,wct.prePatient_id, to_char(wcd.calendarDate,'dd.mm.yyyy'), cast(wct.timeFrom as varchar(5)), vwf.name, wp.lastname as wplastname,wp.firstname as wpfirstname,wp.middlename as wpmiddlename ") ;
		sql.append(" ,coalesce(");
		//sql.append("pat.lastname||' '||pat.firstname||' '||pat.middlename||' '||to_char(p.birthday,'dd.mm.yyyy')||coalesce(' ('||pat.patientSync||')','')") ;
		sql.append(" p.lastname||' '||p.firstname||' '||p.middlename||' '||to_char(p.birthday,'dd.mm.yyyy') ||coalesce(' ('||p.patientSync||')','')") ;
		sql.append(",wct.prepatientInfo) as fio")
			.append(", case when su.isRemoteUser='1' then 'preDirectRemoteUsername' when su1.isRemoteUser='1' then 'directRemoteUsername' else '' end as fontDirect") ;
		sql.append(" from WorkCalendarTime wct ")
			.append(" left join medcase vis on vis.id=wct.medcase_id ")
			.append(" left join SecUser su on su.login=wct.createPreRecord ")
			.append(" left join SecUser su1 on su1.login=vis.username ")
			.append(" left join WorkCalendarDay wcd on wcd.id=wct.workCalendarDay_id ")
			.append(" left join WorkCalendar wc on wc.id=wcd.workCalendar_id ")
			.append(" left join WorkFunction wf on wf.id=wc.workFunction_id ")
			.append(" left join VocWorkFunction vwf on vwf.id=wf.workFunction_id ")
			.append(" left join Worker w on w.id=wf.worker_id ")
			.append(" left join patient wp on wp.id=w.person_id ")
			.append("left join Patient p on p.id=wct.prePatient_id ")
			.append("left join medpolicy mp on mp.patient_id=p.id ")
			.append("where wcd.calendarDate>=CURRENT_DATE and (p.lastname like '").append(aLastname.toUpperCase()).append("%' and wct.medCase_id is null ") ;
		preInfo.append(aLastname).append(" ") ;
		if (aFirstname!=null && !aFirstname.equals("")) {
			sql.append(" and p.firstname like '").append(aFirstname.toUpperCase()).append("%' ") ;
			preInfo.append(aFirstname).append(" ") ;
		} else {
			preInfo.append("% ") ;
		}
		if (aMiddlename!=null && !aMiddlename.equals("")) {
			sql.append(" and p.middlename like '").append(aMiddlename.toUpperCase()).append("%' ") ;
			preInfo.append(aMiddlename).append(" ");
		} else {
			preInfo.append("% ") ;
		}
		if (aBirthday!=null && !aBirthday.equals("")) {
			sql.append(" and p.birthday=to_date('").append(aBirthday).append("','dd.mm.yyyy') ") ;
			preInfo.append(aBirthday).append(" ");
		} else {
			preInfo.append("% ") ;
		}
		if (aPolicySeries!=null && !aPolicySeries.equals("")) {
			sql.append(" and mp.series='").append(aPolicySeries.toUpperCase()).append("' ") ;
			preInfo.append(aPolicySeries.toUpperCase()).append(" ");
		} else {
			preInfo.append("% ") ;
		}
		if (aPolicyNumber!=null && !aPolicyNumber.equals("")) {
			sql.append(" and mp.polNumber='").append(aPolicyNumber.toUpperCase()).append("' ") ;
			preInfo.append(aPolicyNumber.toUpperCase()).append(" ");
		} else {
			preInfo.append("% ") ;
		}
		if (aRayon!=null && aRayon>Long.valueOf(0)) {
			sql.append(" and p.rayon_id='").append(aRayon).append("' ") ;
			preInfo.append(aRayonName);
		} else {
			preInfo.append("%") ;
		}
		System.out.println(preInfo) ;
		sql.append("or wct.prePatientInfo like '").append(preInfo).append("') ") ;
		sql.append(" group by wct.id,wct.prePatient_id, wcd.calendarDate, wct.timeFrom, vwf.name, wp.lastname,wp.middlename,wp.firstname ,p.id,p.patientSync,p.lastname,p.firstname,p.middlename,p.birthday,wct.prepatientInfo,su.isremoteuser,su1.isremoteuser") ;
		sql.append(" order by p.lastname,p.firstname,p.middlename,p.birthday") ;
		Collection<WebQueryResult> list = service.executeNativeSql(sql.toString(),20);
		StringBuilder res = new StringBuilder() ;
		res.append("<form name='frmDirect' id='frmDirect' action='javascript:void(0) ;'><ul id='listDirects'>") ;
		for (WebQueryResult wqr:list) {
			
			res.append("<li class='liTimePre ").append(wqr.get10()!=null?wqr.get10():"").append("'>") ;
			res.append("<a href=\"javascript:patientCame('")
				.append(wqr.get1()).append("', '")
				.append(wqr.get9()).append("', '")
				.append(wqr.get2()).append("')\">") ;
			res.append(wqr.get3()) ;
			res.append(" ").append(wqr.get4()) ;
			res.append("</a>") ;
			res.append(" ").append(wqr.get5()) ;
			res.append(" ").append(wqr.get6()) ;
			res.append(" ").append(wqr.get7()) ;
			res.append(" ").append(wqr.get8()) ;
			res.append(" - <b>").append(wqr.get9()) ;
			res.append("</b>") ;
			if (wqr.get2()!=null) {
				res.append("<a onclick='getDefinition(\"entityShortView-mis_patient.do?id=")
					.append(wqr.get2()).append("\", event); return false ;' ondblclick='javascript:goToPage(\"entityView-mis_patient.do\",\"")
					.append(wqr.get2()).append("\")'><img src=\"/skin/images/main/view1.png\" alt=\"Просмотр записи\" title=\"Просмотр записи\" height=\"16\" width=\"16\"></a>") ;
			}

			res.append(" <a href=\"javascript:deleteTime('").append(wqr.get1()).append("',1)\">У</a>")  ;
			res.append("</li>") ;
		}
		sql = new StringBuilder() ;
		sql.append("select wct.medCase_id as wctmedcaseid, to_char(wcd.calendarDate,'dd.mm.yyyy'), cast(wct.timeFrom as varchar(5)), vwf.name, wp.lastname as wplastname,wp.firstname as wpfirstname,wp.middlename as wpmiddlename ") ;
		sql.append(" ,");
		//sql.append("pat.lastname||' '||pat.firstname||' '||pat.middlename||' '||to_char(p.birthday,'dd.mm.yyyy')||coalesce(' ('||pat.patientSync||')','')") ;
		sql.append(" p.lastname||' '||p.firstname||' '||p.middlename||' '||to_char(p.birthday,'dd.mm.yyyy') ||coalesce(' ('||p.patientSync||')','')") ;
		sql.append(" as fio") 
		.append(", case when su.isRemoteUser='1' then 'preDirectRemoteUsername' when su1.isRemoteUser='1' then 'directRemoteUsername' else '' end as fontDirect") ;
		sql.append(" from WorkCalendarTime wct ")
			.append(" left join WorkCalendarDay wcd on wcd.id=wct.workCalendarDay_id")
			.append(" left join WorkCalendar wc on wc.id=wcd.workCalendar_id ")
			.append(" left join WorkFunction wf on wf.id=wc.workFunction_id ")
			.append(" left join VocWorkFunction vwf on vwf.id=wf.workFunction_id ")
			.append(" left join Worker w on w.id=wf.worker_id ")
			.append(" left join patient wp on wp.id=w.person_id ")
			.append(" left join MedCase m on m.id=wct.medCase_id ")
			.append(" left join SecUser su on su.login=wct.createPreRecord ")
			.append(" left join SecUser su1 on su1.login=m.username ")
			.append(" left join Patient p on p.id=m.patient_id ")
			.append(" left join medpolicy mp on mp.patient_id=p.id ")
			.append(" where wcd.calendarDate>=CURRENT_DATE and (p.lastname like '").append(aLastname.toUpperCase()).append("%' ") ;
		if (aFirstname!=null && !aFirstname.equals("")) {
			sql.append(" and p.firstname like '").append(aFirstname.toUpperCase()).append("%' ") ;
		}
		if (aMiddlename!=null && !aMiddlename.equals("")) {
			sql.append(" and p.middlename like '").append(aMiddlename.toUpperCase()).append("%' ") ;
		}
		if (aBirthday!=null && !aBirthday.equals("")) {
			sql.append(" and p.birthday=to_date('").append(aBirthday).append("','dd.mm.yyyy') ") ;
		}
		if (aPolicySeries!=null && !aPolicySeries.equals("")) {
			sql.append(" and mp.series='").append(aPolicySeries.toUpperCase()).append("' ") ;
		}
		if (aPolicyNumber!=null && !aPolicyNumber.equals("")) {
			sql.append(" and mp.polNumber='").append(aPolicyNumber.toUpperCase()).append("' ") ;
		}
		if (aRayon!=null && aRayon>Long.valueOf(0)) {
			sql.append(" and p.rayon_id='").append(aRayon).append("' ") ;
		}
		sql.append(") and m.dateStart is null ") ;
		sql.append(" group by wct.id,wct.medCase_id, wcd.calendarDate, wct.timeFrom, vwf.name, wp.lastname,wp.middlename,wp.firstname ,p.id,p.patientSync,p.lastname,p.firstname,p.middlename,p.birthday,su.isremoteuser,su1.isremoteuser") ;
		sql.append(" order by p.lastname,p.firstname,p.middlename,p.birthday") ;
		list.clear() ;
		list = service.executeNativeSql(sql.toString(),20);
		//res.append("<li><b>Список направленных</b></li>") ;
		for (WebQueryResult wqr:list) {
			
			res.append("<li class='liTimeDirect ").append(wqr.get9()!=null?wqr.get9():"").append("'><b>") ;
			res.append(wqr.get2()) ;
			res.append(" ").append(wqr.get3()) ;
			res.append("</b> ").append(wqr.get4()) ;
			res.append(" ").append(wqr.get5()) ;
			res.append(" ").append(wqr.get6()) ;
			res.append(" ").append(wqr.get7()) ;
			res.append(" - <b>").append(wqr.get8()) ;
			res.append("</b>") ;
			
				res.append("<a onclick='getDefinition(\"entityShortView-smo_direction.do?id=")
					.append(wqr.get1()).append("\", event); return false ;' ondblclick='javascript:goToPage(\"entityView-smo_direction.do\",\"")
					.append(wqr.get1()).append("\")'><img src=\"/skin/images/main/view1.png\" alt=\"Просмотр записи\" title=\"Просмотр записи\" height=\"16\" width=\"16\"></a>") ;
			

			//res.append(" <a href=\"javascript:deleteTime('").append(wqr.get1()).append("',1)\">У</a>")  ;
			res.append("</li>") ;
		}
		res.append("</ul></form>") ;

		return res.toString() ;
	}
	public String getPatients(String aLastname, String aFirstname, String aMiddlename
			, String aBirthday, String aPolicySeries, String aPolicyNumber
			,Long aRayon, HttpServletRequest aRequest) throws NamingException {
		StringBuilder sql = new StringBuilder() ;
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		sql.append("select p.id,p.patientSync,p.lastname,p.firstname,p.middlename,to_char(p.birthday,'dd.mm.yyyy') from patient p left join medpolicy mp on mp.patient_id=p.id where p.lastname like '").append(aLastname.toUpperCase()).append("%' ") ;
		if (aFirstname!=null && !aFirstname.equals("")) {
			sql.append(" and p.firstname like '").append(aFirstname.toUpperCase()).append("%' ") ;
		}
		if (aMiddlename!=null && !aMiddlename.equals("")) {
			sql.append(" and p.middlename like '").append(aMiddlename.toUpperCase()).append("%' ") ;
		}
		if (aBirthday!=null && !aBirthday.equals("")) {
			sql.append(" and p.birthday=to_date('").append(aBirthday).append("','dd.mm.yyyy') ") ;
		}
		if (aPolicySeries!=null && !aPolicySeries.equals("")) {
			sql.append(" and mp.series='").append(aPolicySeries.toUpperCase()).append("' ") ;
		}
		if (aPolicyNumber!=null && !aPolicyNumber.equals("")) {
			sql.append(" and mp.polNumber='").append(aPolicyNumber.toUpperCase()).append("' ") ;
		}
		if (aRayon!=null && aRayon>Long.valueOf(0)) {
			sql.append(" and p.rayon_id='").append(aRayon).append("' ") ;
		}
		sql.append(" group by p.id,p.patientSync,p.lastname,p.firstname,p.middlename,p.birthday") ;
		sql.append(" order by p.lastname,p.firstname,p.middlename,p.birthday") ;
		Collection<WebQueryResult> list = service.executeNativeSql(sql.toString(),20);
		StringBuilder res = new StringBuilder() ;
		res.append("<form name='frmPatient' id='frmPatient' action='javascript:step6()'><ul id='listPatients'>") ;
		boolean isFirst = false ;
		for (WebQueryResult wqr:list) {
			
			res.append("<li ondblclick=\"this.childNodes[1].checked='checked';document.forms['frmPatient'].action='javascript:step6Finish()';document.forms['frmPatient'].submit();\" onclick=\"this.childNodes[1].checked='checked';document.forms['frmPatient'].action='javascript:step6()';document.forms['frmPatient'].submit()\">") ;
			res.append(" <input class='radio' type='radio' name='rdPatient' id='rdPatient' ") ;
			if (isFirst) {
				res.append(" checked='true' ") ;
				isFirst=false ;
			}
			res.append(" value='")
			.append(wqr.get1()).append("#").append(wqr.get2()).append("#").append(wqr.get3())
			.append("#").append(wqr.get4()).append("#").append(wqr.get5()).append("'>") ;
			res.append(wqr.get2()) ;
			res.append(" ").append(wqr.get3()) ;
			res.append(" ").append(wqr.get4()) ;
			res.append(" ").append(wqr.get5()) ;
			res.append(" ").append(wqr.get6()) ;
			if (wqr.get1()!=null) {
				res.append("<a onclick='getDefinition(\"entityShortView-mis_patient.do?id=")
					.append(wqr.get1()).append("\", event); return false ;' ondblclick='javascript:goToPage(\"entityView-mis_patient.do\",\"")
					.append(wqr.get1()).append("\")'><img src=\"/skin/images/main/view1.png\" alt=\"Просмотр записи\" title=\"Просмотр записи\" height=\"16\" width=\"16\"></a>") ;
				res.append(" <a target='_blank' href=\"print-begunok.do?s=SmoVisitService&m=printDirectionByPatient&patientId=").append(wqr.get1()).append("\"").append(wqr.get1()).append("'\">ПЕЧАТЬ</a> ")  ;

			}
			res.append("</li>") ;
		}
		res.append("<li ondblclick=\"this.childNodes[1].checked='checked';document.forms['frmPatient'].action='javascript:step6Finish()';document.forms['frmPatient'].submit();\" onclick=\"this.childNodes[1].checked='checked';document.forms['frmPatient'].submit()\"> <input type='radio' name='rdPatient' id='rdPatient' value='0'/>НЕТ В БАЗЕ</li>") ;
		res.append("</ul></form>") ;
		
		return res.toString() ;
	}
	public String getVocWorkFunction(boolean aManyIs,HttpServletRequest aRequest) throws NamingException {
		StringBuilder sql = new StringBuilder() ;
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		boolean remoteUser = remoteUser(service, aRequest) ;
		sql.append("select vwf.id as vwfid,vwf.code as vwfcode,vwf.name as vwfname") ;
		sql.append(" from VocWorkFunction vwf ") ;
		sql.append(" left join WorkFunction wf on vwf.id=wf.workFunction_id") ; 
		sql.append(" left join MisLpu m1 on m1.id=wf.lpu_id") ; 
		sql.append(" left join Worker w on w.id=wf.worker_id") ; 
		sql.append(" left join MisLpu m2 on m2.id=w.lpu_id") ; 
		sql.append(" left join WorkCalendar wc on wc.workFunction_id=wf.id") ;
		sql.append(" left join WorkCalendarDay wcd on wcd.workCalendar_id=wc.id") ;
 
		sql.append(" where wcd.calendarDate>=Current_date");
		if (remoteUser) sql.append(" and (wf.DTYPE='PersonalWorkFunction' and (m2.isNoViewRemoteUser is null or m2.isNoViewRemoteUser='0') or wf.dtype='GroupWorkFunction' and (m1.isNoViewRemoteUser is null or m1.isNoViewRemoteUser='0')) and (wf.isNoViewRemoteUser is null or wf.isNoViewRemoteUser='0')") ;
		sql.append(" group by vwf.id,vwf.code,vwf.name") ;
		sql.append(" order by vwf.name") ;
		
		StringBuilder res = new StringBuilder() ;
		Collection<WebQueryResult> list = service.executeNativeSql(sql.toString(),50);
		res.append("<form name='frmFunctions' id='frmFunctions' action='javascript:step3()'><ul id='listFunctions'>") ;
		res.append("<li class='title'>Специалисты</li>");
		for (WebQueryResult wqr:list) {
			
			if (aManyIs) {
				res.append("<li onclick=\"if (this.childNodes[1].checked) {this.childNodes[1].checked=false;}else{this.childNodes[1].checked=true} step3('")
				.append(wqr.get1()).append("#").append(wqr.get3()).append("')\">") ;
				res.append(" <input style='display:none' readOnly='true' class='");
				res.append("radio") ;
			} else {
				res.append("<li onclick=\"this.childNodes[1].checked='checked';step3('")
				.append(wqr.get1()).append("#").append(wqr.get3()).append("')\">") ;
				res.append(" <input class='");
				res.append("radio") ;
			}
			res.append("' type='");
			if (aManyIs) {
				res.append("checkbox") ;
			} else {
				res.append("radio") ;
			}
			res.append("' name='rdFunction' id='rdFunction' value='")
			.append(wqr.get1()).append("#").append(wqr.get3()).append("'>") ;
			res.append(wqr.get3()) ;
			res.append("</li>") ;
		}
		res.append("</ul></form>") ;
		return res.toString() ;
	}
	public String getSpecialistsByWorkFunctions(String aVocWorkFunctions, HttpServletRequest aRequest) throws NamingException{
		StringBuilder res = new StringBuilder() ;
		String[] ids = aVocWorkFunctions.split(";") ;
		res.append("<table border=1><tr>") ;
		for (String par:ids) {
			String[] id = par.split("#") ; 
			res.append("<th>")
			.append(id[1]).append("    <a href=\"javascript:uncheckedVocWorkFunction('").append(par).append("')\"> Х ").append("</a>")
			.append("</th>") ;
		}
		res.append("</tr><tr>") ;
		for (String par:ids) {
			String[] id = par.split("#") ; 
			res.append("<td>")
			.append(getSpecialistsByWorkFunction(true,Long.valueOf(id[0]), aRequest))
			.append("</td>") ;
		}
		res.append("</tr><tr>") ;
		for (String par:ids) {
			String[] id = par.split("#") ; 
			res.append("<td valign='top' align='center'>")
			.append("<div id='rowStep5Date_").append(id[0]).append("'>").append("</div>")
			.append("</td>") ;
		}
		res.append("</tr></table>") ;
		return res.toString() ;
	}

	
	public String getSpecialistsByWorkFunction(boolean aIsMany,Long aVocWorkFunction, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		boolean remoteUser = remoteUser(service, aRequest) ;
		StringBuilder sql = new StringBuilder() ;
		StringBuilder res = new StringBuilder() ;
		String frmName="frmSpecialist" ;
		if (aIsMany) frmName=frmName+"_"+aVocWorkFunction ;
		if (!aIsMany) {
			Collection<WebQueryResult> l = service.executeNativeSql("select name, id from vocworkfunction where id='"+aVocWorkFunction+"'",1) ;
			WebQueryResult ll = l.iterator().next() ;
			res.append("<p><b><font color='MidnightBlue'>").append(ll!=null?ll.get1():"-").append("</font></b></p>") ;
		}
		sql.append("select case when wf.dtype='PersonalWorkFunction' then m2.id else m1.id end as lpuid") ;
		sql.append(" ,case when wf.dtype='PersonalWorkFunction' then m2.name else m1.name end as lpuname") ;
		sql.append(" from WorkFunction wf") ;
		sql.append(" left join Worker w on w.id=wf.worker_id");
		sql.append(" left join Patient wp on wp.id=w.person_id");
		sql.append(" left join WorkCalendar wc on wc.workFunction_id=wf.id");
		sql.append(" left join WorkCalendarDay wcd on wcd.workCalendar_id=wc.id");
		sql.append(" left join WorkCalendarTime wct on wct.workCalendarDay_id=wcd.id");

		sql.append(" left join MisLpu m1 on m1.id=wf.lpu_id") ; 
		sql.append(" left join MisLpu m2 on m2.id=w.lpu_id") ; 
 
		
		
		sql.append(" where wf.workFunction_id='").append(aVocWorkFunction).append("'");
		sql.append(" and wcd.calendarDate>=CURRENT_DATE");
		sql.append(" and wct.medCase_id is null");
		if (remoteUser) sql.append(" and (wf.DTYPE='PersonalWorkFunction' and (m2.isNoViewRemoteUser is null or m2.isNoViewRemoteUser='0') and (wf.isNoViewRemoteUser is null or wf.isNoViewRemoteUser='0') or wf.dtype='GroupWorkFunction' and (m1.isNoViewRemoteUser is null or m1.isNoViewRemoteUser='0') and (wf.isNoViewRemoteUser is null or wf.isNoViewRemoteUser='0'))") ;
		sql.append(" group by case when wf.dtype='PersonalWorkFunction' then m2.id else m1.id end,case when wf.dtype='PersonalWorkFunction' then m2.name else m1.name end");
		Collection<WebQueryResult> listLpu = service.executeNativeSql(sql.toString(),50);
		
		res.append("<form name='").append(frmName).append("' id='").append(frmName).append("' action='javascript:step4()'><ul id='listSpecialists'>") ;
		for (WebQueryResult wqrLpu:listLpu) {
			sql = new StringBuilder() ;
			sql.append("select wc.id as wcid").append(" , coalesce(wp.lastname||' '||wp.firstname||' '||coalesce(wp.middlename,''),wf.groupName) || coalesce(' (<font color=red>'||upper(wf.comment)||')</font>','') as wfInfo") ;
			sql.append(" ,to_char(min(wcd.calendarDate),'dd.mm.yyyy') as CDdate") ;
			sql.append(" ,to_char(min(wcd.calendarDate),'yyyy') as CDyear");
			sql.append(" ,to_char(min(wcd.calendarDate),'mm') as CDmonth ");
			//sql.append(" ,case when wf.dtype='PersonalWorkFunction' then m2.name else m1.name end as lpu") ;
			sql.append(" from WorkFunction wf") ;
			sql.append(" left join Worker w on w.id=wf.worker_id");
			sql.append(" left join Patient wp on wp.id=w.person_id");
			sql.append(" left join WorkCalendar wc on wc.workFunction_id=wf.id");
			sql.append(" left join WorkCalendarDay wcd on wcd.workCalendar_id=wc.id");
			sql.append(" left join WorkCalendarTime wct on wct.workCalendarDay_id=wcd.id");
			sql.append(" left join MisLpu m1 on m1.id=wf.lpu_id") ; 
			sql.append(" left join MisLpu m2 on m2.id=w.lpu_id") ; 
			
			sql.append(" where wf.workFunction_id='").append(aVocWorkFunction).append("'");
			sql.append(" and wcd.calendarDate>=CURRENT_DATE");
			sql.append(" and wct.medCase_id is null") ;
			if (remoteUser) sql.append(" and (wf.DTYPE='PersonalWorkFunction' and m2.id='").append(wqrLpu.get1()).append("' and (m2.isNoViewRemoteUser is null or m2.isNoViewRemoteUser='0') and (wf.isNoViewRemoteUser is null or wf.isNoViewRemoteUser='0') or wf.dtype='GroupWorkFunction' and m1.id='").append(wqrLpu.get1()).append("' and (m1.isNoViewRemoteUser is null or m1.isNoViewRemoteUser='0') and (wf.isNoViewRemoteUser is null or wf.isNoViewRemoteUser='0'))") ;
			if (!remoteUser) sql.append(" and (wf.DTYPE='PersonalWorkFunction' and m2.id='").append(wqrLpu.get1()).append("' or wf.dtype='GroupWorkFunction' and m1.id='").append(wqrLpu.get1()).append("' )") ;
			sql.append(" group by wc.id,wp.lastname,wp.firstname,wp.middlename,wf.groupName,wf.comment");
			sql.append(" order by wf.groupName,wp.lastname,wp.firstname,wp.middlename") ;
			Collection<WebQueryResult> list = service.executeNativeSql(sql.toString(),50);
			res.append("<li><u>").append(wqrLpu.get2()).append("</u></li>") ;
			for (WebQueryResult wqr:list) {
				res.append("<li onclick=\"this.childNodes[1].checked='checked';step4('")
				.append(wqr.get1()).append("','").append(wqr.get5()).append("','").append(wqr.get4()) ;
				if (aIsMany) res.append("','").append(aVocWorkFunction);
				res.append("')\">") ;
				res.append(" <input class='radio' type='radio' name='rdSpecialist' id='rdSpecialist' value='")
				.append(wqr.get1()).append("#").append(wqr.get5()).append("#").append(wqr.get4())
				.append("#").append(wqr.get2()).append("#").append(wqr.get3()).append("'>") ;
				res.append(wqr.get2()) ;
				if (!aIsMany)res.append(" (").append(wqr.get3()).append(")") ;
				//res.append(" ").append(wqr.get6());
				res.append("</li>") ;
			}
		}
		res.append("</ul></form>") ;
		return res.toString() ;
	}

	public String getDatesBySpecialist(Long aWorkCalendar, String aMonth, String aYear,Long aVocWorkFunction, HttpServletRequest aRequest) throws NamingException {
		StringBuilder sql = new StringBuilder() ;
		sql.append(" select  wcd.id as wcdid, to_char(wcd.calendardate,'dd.mm.yyyy') as wcdcalendardate");
		sql.append(" ,to_char(wcd.calendardate,'dd') as CDday");
		sql.append(" ,count(case when wct.medCase_id is null and wct.prepatient_id is null and (wct.prepatientinfo is null or wct.prepatientinfo='') then 1 else null end) as cntFree");
		sql.append(" ,count(case when wct.medCase_id is not null or wct.prepatient_id is not null or (wct.prepatientinfo is not null and wct.prepatientinfo!='') then 1 else null end) as cntBasy");
		sql.append(" ,count(wct.id) as cntAll");
		sql.append(" from workCalendar wc"); 
		sql.append(" left join workcalendarday wcd on wcd.workcalendar_id=wc.id");
		sql.append(" left join workcalendartime wct on wct.workcalendarday_id=wcd.id");
		sql.append(" where wc.id='").append(aWorkCalendar).append("'"); 
		sql.append(" and to_char(wcd.calendardate,'mm.yyyy')='")
			.append(aMonth).append(".").append(aYear).append("'");
		sql.append(" group by wcd.id,wcd.calendardate");
		sql.append(" order by wcd.calendardate") ;
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		Collection<WebQueryResult> list = service.executeNativeSql(sql.toString(),50);
		StringBuilder res = new StringBuilder() ;
		res.append("<form name='frmDate' id='frmDate' action='javascript:step5()'>") ;
		int month= Integer.valueOf(aMonth) ;
		res.append("<span class = 'spanNavigMonth'>") ;
		if (month==1) {
			res.append("<a href=\"javascript:step4(")
			.append(aWorkCalendar).append(",'").append(getMonth(12,false))
			.append("','").append(Integer.valueOf(aYear)-1);
			if (aVocWorkFunction!=null) res.append("','").append(aVocWorkFunction);
			res.append("');\">")
			.append("<-")
			//.append(getMonth(12,true)).append(" ").append(Integer.valueOf(aYear)-1)
			.append("</a> ") ;
		} else {
			res.append("<a href=\"javascript:step4(")
			.append(aWorkCalendar).append(",'").append(getMonth(month-1,false))
			.append("','").append(aYear);
			if (aVocWorkFunction!=null) res.append("','").append(aVocWorkFunction);
			res.append("');\">").append("<-")
			//.append(getMonth(month-1,true)).append(" ").append(Integer.valueOf(aYear))
			.append("</a> ") ;
		}
		res.append(" ").append(getMonth(month,true).toUpperCase()).append(" ").append(aYear) ;
		if (month==12) {
			res.append(" <a href=\"javascript:step4(")
			.append(aWorkCalendar).append(",'").append(getMonth(1,false))
			.append("','").append(Integer.valueOf(aYear)+1);
			if (aVocWorkFunction!=null) res.append("','").append(aVocWorkFunction);
			res.append("');\">")
			//.append("").append(getMonth(1,true))
			//.append(" ").append(Integer.valueOf(aYear)+1)
			.append("-></a>") ;
		} else {
			res.append("<a href=\"javascript:step4(")
			.append(aWorkCalendar).append(",'").append(getMonth(month+1,false))
			.append("','").append(Integer.valueOf(aYear));
			if (aVocWorkFunction!=null) res.append("','").append(aVocWorkFunction);
			res.append("');\">").append("")
			//.append(getMonth(month+1,true)).append(" ").append(Integer.valueOf(aYear))
			.append("-></a> ") ;
		}
		res.append("</span>") ;
		Calendar cal = Calendar.getInstance() ;
		cal.set(Calendar.YEAR, Integer.valueOf(aYear)) ;
		month-- ;
		cal.set(Calendar.MONTH, month) ;
		cal.set(Calendar.DATE, 1) ;
		int day = 1 ;
		int oldday = 0 ;
		int week = cal.get(Calendar.DAY_OF_WEEK)-1 ;
		//System.out.println() ;
		//System.out.println(cal.toString()) ;
		if (week==0) {
			week=7;
		} else{
			
		}
		week-- ;
		
		res.append("<table class='listDates'>") ;
		res.append("<tr>")
			.append("<th>Пон</th>")
			.append("<th>Вт</th>")
			.append("<th>Ср</th>")
			.append("<th>Чет</th>")
			.append("<th>Пят</th>")
			.append("<th>Суб</th>")
			.append("<th>Вос</th>")
			.append("<tr>") ;

		res.append("<tr>") ;
		res.append(getFreeDay(0, week, false,1)) ;
		for (WebQueryResult wqr:list) {
			oldday = Integer.valueOf(""+wqr.get3()) ;
			res.append(getFreeDay(day, oldday, true,week)) ;
			week = (week+oldday-day)%7 ;
			if (week==0) week = 7 ;
			week++ ;
			if (week>7) {
				res.append("</tr><tr>") ;
			}
			boolean isBusy = Integer.valueOf(""+wqr.get4())==0?true:false ;
			res.append("<td id='tdDay").append(wqr.get3()).append("'");
			//if (true) {
				res.append("onclick=\"step5(this,'").append(aWorkCalendar).append("','").append(wqr.get1())
				.append("','").append(wqr.get2()) ;
				if (aVocWorkFunction!=null) {
					res.append("','").append(aVocWorkFunction);
				}
				res.append("')\"");	
			//}else {
				
			//}
			res.append(" class='").append(isBusy?"busyDay":"visitDay").append("'>") ;
			res.append(isBusy?"":"<b>").append(Integer.valueOf(""+wqr.get3())) ;
			res.append(" <br>(").append(wqr.get5()).append("/").append(wqr.get6()).append(")") ;
			res.append(isBusy?"":"</b>").append("</td>") ;
			day = oldday+1 ;
			//res.append("<li onclick=\"this.childNodes[1].checked='checked';\">") ;
			//res.append(" <input type='radio' name='rdDate' id='rdDate' checked='true' value='").append(aWorkCalendar).append("#").append(wqr.get1())
			//.append("#").append(wqr.get2()).append("#").append(wqr.get4()).append("#").append(wqr.get6()).append("'>") ;
			//res.append(wqr.get2()) ;
			//res.append(" (").append(wqr.get4()).append(" из ").append(wqr.get6()).append(")") ;
			//res.append("</li>") ;
		}
		int max = cal.getActualMaximum(Calendar.DAY_OF_MONTH) ;
		//System.out.println("act="+cal.getActualMaximum(Calendar.DAY_OF_MONTH)) ;
		res.append(getFreeDay(day, max+1, true,week)) ;
		if (oldday>0) {
			week = (week+max-day)%7 ;
			if (week==0) week = 7 ;
		} else {
			week = (week+max)%7 ;
			if (week==0) week = 7 ;
		}
		week++ ;
		res.append(getFreeDay(week, 7, false,1)) ;
		res.append("</tr>") ;
		if (aVocWorkFunction!=null) {
			res.append("<tr>") ;
			res.append("<td colspan='7' valign='top'><div id='rowStep6Time_").append("").append(aVocWorkFunction).append("' >Выберете дату</div></td>") ;
			res.append("</tr>") ;
		}	
		res.append("</table></form>") ;
		return res.toString() ;
	}
	private String getMonth(int aMonth,boolean aFullname) {
		String month = "" ;
		switch (aMonth) {
			case 1:	month=aFullname?"Январь":"01" ;	break;
			case 2:	month=aFullname?"Февраль":"02" ;break;
			case 3:	month=aFullname?"Март":"03" ;break;	
			case 4:	month=aFullname?"Апрель":"04" ;	break;
			case 5: month=aFullname?"Май":"05" ;break;
			case 6:	month=aFullname?"Июнь":"06" ;break;
			case 7:	month=aFullname?"Июль":"07" ;break;
			case 8:	month=aFullname?"Август":"08" ;break;
			case 9:	month=aFullname?"Сентябрь":"09" ;break;
			case 10:month=aFullname?"Октябрь":"10" ;break;
			case 11:month=aFullname?"Ноябрь":"11" ;	break;
			case 12:month=aFullname?"Декабрь":"12" ;break;
			default:month = aFullname?"":""+aMonth ;break;
		}
		return month ;
	}
	private StringBuilder getFreeDay(int aFrom, int aTo, boolean aView,int aWeek) {
		
		StringBuilder res = new StringBuilder() ;
		for (int i=aFrom;i<aTo;i++) {

			aWeek = aWeek%7 ;
			if (aWeek==0) aWeek = 7 ;
			aWeek++ ;

			if (aWeek>7) {
				res.append("</tr><tr>") ;
				aWeek=1 ;
			}
			if (aView) {
				res.append("<td id='tdDay").append(getMonth(i, false)).append("'").append("class='freeDay'").append(">").append(i).append("</td>") ;
			} else {
				res.append("<td>&nbsp;</td>") ;
			}
		}
		return res ;
	}
	
	public String getTimesByWorkCalendarDay(Long aWorkCalendar,Long aWorkCalendarDay,Long aVocWorkFunction,HttpServletRequest aRequest) throws NamingException, JspException {
		StringBuilder sql = new StringBuilder() ;
		sql.append(" select wct.id,cast(wct.timeFrom as varchar(5)) as wcttimeFrom")
			.append(" ,case when wct.medCase_id is null and wct.prepatient_id is null and (wct.prepatientinfo is null or wct.prepatientinfo='') then 0 when wct.prepatient_id is not null or (wct.prepatientinfo is not null and wct.prepatientinfo!='') then 2 else 1 end")
			.append(" ,wct.medCase_id");
		sql.append(" ,coalesce(pat.lastname||coalesce(' ('||pat.patientSync||')','')") ;
		sql.append(", prepat.lastname ||coalesce(' ('||prepat.patientSync||')','')") ;
		sql.append(",wct.prepatientInfo) as fio") ;
		sql.append(", prepat.id as prepatid,vis.dateStart as visdateStart") ;
		sql.append(",coalesce(prepat.lastname,wct.prepatientInfo) as prepatLast") ;
		sql.append(",pat.lastname as patLast,coalesce(pat.id,prepat.id) as patid")
			.append(", case when su.isRemoteUser='1' then 'preDirectRemoteUsername' when su1.isRemoteUser='1' then 'directRemoteUsername' else '' end as fontDirect") ; ;
		sql.append(" from WorkCalendarTime wct") ; 
		sql.append(" left join MedCase vis on vis.id=wct.medCase_id")
			.append(" left join SecUser su on su.login=wct.createPreRecord ")
			.append(" left join SecUser su1 on su1.login=vis.username ");
		sql.append(" left join patient pat on pat.id=vis.patient_id");
		sql.append(" left join patient prepat on prepat.id=wct.prepatient_id");
		sql.append(" where wct.workCalendarDay_id='").append(aWorkCalendarDay).append("'");
		sql.append(" order by wct.timeFrom");
		StringBuilder res = new StringBuilder() ;
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		Collection<WebQueryResult> list = service.executeNativeSql(sql.toString(),50);
		String frmName = "frmTime" ;
		if (aVocWorkFunction!=null) frmName=frmName+"_"+aVocWorkFunction ;
		res.append("<form name='").append(frmName).append("' id='").append(frmName).append("' action='javascript:step6()'><ul class='listTimes'>") ;
		int cntLi = 1 ;
		int row = list.size()/cntLi ;
		if (list.size()%cntLi>0) {
			row ++ ;
		}
		//System.out.println("row="+row) ;
		int i=0 ;
		boolean first =false;
		for (WebQueryResult wqr:list) {
			i++ ;
			if (i==1) res.append("<li class='liList'><ul class='ulTime'>") ;
			
			int pre = Integer.valueOf(""+wqr.get3());
			if (pre==1) {
				if (wqr.get7()!=null) {
					res.append("<li id='liTimeDirect' class='").append(wqr.get11()!=null?wqr.get11():"").append("' ><strike>") 
					.append(wqr.get2()) .append(" ") ;
					res.append(wqr.get5()).append("</strike>") ;
				} else {
					res.append("<li id='liTimeDirect' class='").append(wqr.get11()!=null?wqr.get11():"").append("'>") 
					.append(wqr.get2()) .append(" ") ;
					res.append(wqr.get5()) ;
				}
			} else if (pre==2) {
				
				if (wqr.get4()!=null) {
					String prelastname = ""+wqr.get8() ;
					String lastname = ""+wqr.get9() ;
					res.append("<li id='liTimePre' class='").append(wqr.get11()!=null?wqr.get11():"").append("'>") ;
					String add = "" ;
					if (prelastname!=null && lastname!=null) {
						if (!prelastname.startsWith(lastname)) {
							add = " <i> вместо "+prelastname+"</i> " ;
						}
					}
					if (wqr.get7()!=null) {
						res.append(" <strike><u>")
						.append(wqr.get2()).append("")
						.append(" ") ;
						res.append(wqr.get5()).append(add).append("</strike></u>")  ;
					} else {
						res.append(" <u>");
						res.append(" <a target='_blank' href=\"print-begunok.do?s=SmoVisitService&m=printDirectionByTime&wct=").append(wqr.get1()).append("\"").append(wqr.get1()).append("'\">ПЕЧАТЬ</a> ")  ;
						res.append(wqr.get2()).append("")
						.append(" ") ;
						res.append(wqr.get5()).append(add).append("</u>")  ;
					}
				} else {
					res.append(" <a target='_blank' href=\"print-begunok.do?s=SmoVisitService&m=printDirectionByTime&wct=").append(wqr.get1()).append("\"").append(wqr.get1()).append("'\">ПЕЧАТЬ</a> ")  ;
					res.append("<li id='liTimePre' class='").append(wqr.get11()!=null?wqr.get11():"").append("'>") .append(" <a href=\"javascript:patientCame('")
					.append(wqr.get1()).append("','").append(wqr.get5())
					.append("','").append(wqr.get6()).append("')\">")
					.append(wqr.get2()).append("</a>")
					.append(" ") ;
					res.append(wqr.get5()).append(" <a href=\"javascript:deleteTime('").append(wqr.get1()).append("')\">У</a>")  ;
					
				}
			} else {
				res.append("<li id='liTime' ondblclick=\"this.childNodes[1].checked='checked';step6Finish('").append(wqr.get1()).append("')\" onclick=\"this.childNodes[1].checked='checked';step6();\">") ;
				
				res.append(" <input class='radio' type='radio' name='rdTime' id='rdTime' ");
				if (first) res.append(" checked='true'");
				res.append(" value='").append(aWorkCalendarDay).append("#").append(wqr.get1()).append("#").append(wqr.get2()).append("'>") ;
				if (RolesHelper.checkRoles("/Policy/Mis/Worker/WorkCalendar/DeleteTime", aRequest)) {
					res.append("<a href=\"javascript:deleteWCTime('").append(wqr.get1()).append("')\">DEL</a>") ;
				}
				res.append(wqr.get2()) ;
			}
			if (wqr.get10()!=null) {
				res.append("<a onclick='getDefinition(\"entityShortView-mis_patient.do?id=")
					.append(wqr.get10()).append("\", event); return false ;' ondblclick='javascript:goToPage(\"entityView-mis_patient.do\",\"")
					.append(wqr.get10()).append("\")'><img src=\"/skin/images/main/view1.png\" alt=\"Просмотр записи\" title=\"Просмотр записи\" height=\"16\" width=\"16\"></a>") ;
			}
			//res.append(" (").append(wqr.get3()).append(" из ").append(wqr.get5()).append(")") ;
			res.append("</li>") ;
			if (i>=cntLi) {
				res.append("</ul></li>") ;
				i=0 ;
			}
			first=false ;
		}
		if (i<=cntLi) {
			res.append("</ul></li>") ;
		}
		res.append("</ul>") ;
		//res.append("<button>Выбрать</button>");
		return res.toString() ;
	}
	public String preRecordByPatient(String aPatInfo,Long aPatientId
			,Long aFunction,Long aSpec,Long aDay,Long aTime
			,HttpServletRequest aRequest
			) throws NamingException {
		IWorkCalendarService service = Injection.find(aRequest).getService(IWorkCalendarService.class) ;
		//System.out.println("patid="+aPatientId) ;
		String username = LoginInfo.find(aRequest.getSession(true)).getUsername() ;
		service.preRecordByPatient(username, aFunction, aSpec,aDay,aTime,aPatInfo,aPatientId) ;
		return "Сохранено" ;
	}
	public String preRecordByTimeAndPatient(String aPatInfo,Long aPatientId
			,Long aTime
			,HttpServletRequest aRequest
			) throws NamingException {
		IWorkCalendarService service = Injection.find(aRequest).getService(IWorkCalendarService.class) ;
		//System.out.println("patid="+aPatientId) ;
		String username = LoginInfo.find(aRequest.getSession(true)).getUsername() ;
		return service.preRecordByPatient(username, aTime,aPatInfo,aPatientId) ;
		//return "Сохранено" ;
	}
	public String deletePreRecord(Long aTime, HttpServletRequest aRequest) throws NamingException {
		IWorkCalendarService service = Injection.find(aRequest).getService(IWorkCalendarService.class) ;
		String username = LoginInfo.find(aRequest.getSession(true)).getUsername() ;
		return service.deletePreRecord(username,aTime) ;
		//return "Удалено" ;
	}
	// Получить минимальное и максимальное время приема за день по специалисту
	public String getIntervalBySpecAndDate(String aDate
			, Long aSpecialist,HttpServletRequest aRequest) throws ParseException, NamingException {
		IWorkCalendarService service = Injection.find(aRequest).getService(IWorkCalendarService.class) ;
		return service.getIntervalBySpecAndDate(aDate, aSpecialist) ;
	}
			
	// Получить новые времена по специалисту за определенное число
	public String getTimesBySpecAndDate(String aDate
			, Long aSpecialist, Long aCountVisits
			, String aBeginTime, String aEndTime
			, HttpServletRequest aRequest) throws NamingException, ParseException {
		IWorkCalendarService service = Injection.find(aRequest).getService(IWorkCalendarService.class) ;
		return service.getTimesBySpecAndDate(aDate, aSpecialist, aCountVisits, aBeginTime, aEndTime) ;
		//return "" ;
	}
	public String addNewTimeBySpecialist(String aDate, Long aSpecialist, String aBeginTime,String aEndTime, HttpServletRequest aRequest) throws ParseException, NamingException {
		java.sql.Time timeFrom = DateFormat.parseSqlTime(aBeginTime) ;
		java.sql.Time timeTo = DateFormat.parseSqlTime(aEndTime) ;
		Calendar cal1 = java.util.Calendar.getInstance() ;
		Calendar cal2 = java.util.Calendar.getInstance() ;
		cal1.setTime(timeFrom) ;
		cal2.setTime(timeTo) ;
		int hour1 = cal1.get(Calendar.HOUR_OF_DAY) ;
		int hour2 = cal2.get(Calendar.HOUR_OF_DAY) ;
		int min1 = cal1.get(Calendar.MINUTE) ;
		int min2 = cal2.get(Calendar.MINUTE) ;
		int dif = (hour2-hour1)*60 +min2- min1 ;
		if (dif==0) throw new IllegalDataException("Разница между временами должна быть больше 1 минуты")  ;
		int interval = dif/2 ; 
		cal1.add(java.util.Calendar.MINUTE, interval) ;
		java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("HH:mm") ;
		IWorkCalendarService service = Injection.find(aRequest).getService(IWorkCalendarService.class) ;
		return service.addCreateNewTimeBySpecAndDate(aDate, aSpecialist, format.format(cal1.getTime())) ;
		
	}
	// Создать новые времена по специалисту за определенное число
	public String getCreateNewTimesBySpecAndDate(String aDate
			, Long aSpecialist, String aTimes
			, HttpServletRequest aRequest) throws NamingException, ParseException {
		IWorkCalendarService service = Injection.find(aRequest).getService(IWorkCalendarService.class) ;
		service.getCreateNewTimesBySpecAndDate(aDate, aSpecialist, aTimes) ;
		return "Созданы" ;
	}
	public String generateBySpecialist(Long aWorkFunction, HttpServletRequest aRequest) throws NamingException {
		IWorkCalendarService service = Injection.find(aRequest).getService(IWorkCalendarService.class) ;
		java.util.Date date = new java.util.Date() ;
		Calendar cal = Calendar.getInstance() ;
		cal.set(Calendar.DAY_OF_MONTH, 50) ;
		service.generateCalendarByWorkFunction(aWorkFunction, new Date(date.getTime()), new Date(cal.getTime().getTime())) ;
		return "Сгенерировано";
		
	}
	public String addBusyPattern(Long aWorkFunction, String aDateFrom, 
			String aDateTo, Long aPattern, HttpServletRequest aRequest) throws NamingException, ParseException {
		IWorkCalendarService service = Injection.find(aRequest).getService(IWorkCalendarService.class) ;
		java.sql.Date dateFrom = DateFormat.parseSqlDate(aDateFrom) ;
		java.sql.Date dateTo = DateFormat.parseSqlDate(aDateTo) ;
		service.addBusyPatternByWorkFunction(aWorkFunction, dateFrom, dateTo, aPattern) ;
		return "Добавлено" ;
	}
	
	
	public String getInfoDay(String aDate, HttpServletRequest aRequest) throws NamingException {
		IPolyclinicMedCaseService service = Injection.find(aRequest).getService(IPolyclinicMedCaseService.class) ;
		return aDate ;
		
	}
	
	public Long getSecUser(HttpServletRequest aRequest) throws NamingException {
		IPolyclinicMedCaseService service = Injection.find(aRequest).getService(IPolyclinicMedCaseService.class) ;
		return service.getSecUser() ;
	}
	
	public Long getWorkFunction(HttpServletRequest aRequest) throws NamingException {
		IPolyclinicMedCaseService service = Injection.find(aRequest).getService(IPolyclinicMedCaseService.class) ;
		return service.getWorkFunction() ;
	}
	public Long getWorkFunctionBySecUser(Long aSecUser, HttpServletRequest aRequest) throws NamingException {
		IPolyclinicMedCaseService service = Injection.find(aRequest).getService(IPolyclinicMedCaseService.class) ;
		return service.getWorkFunction(aSecUser) ;
	}
	public Long getWorkCalendar(Long aWorkFunction, HttpServletRequest aRequest) throws NamingException {
		IPolyclinicMedCaseService service = Injection.find(aRequest).getService(IPolyclinicMedCaseService.class) ;
		return service.getWorkCalendar(aWorkFunction);
		
	}
	public String getWorkCalendarDay(Long aWorkCalendar,Long aWorkFucntion,String aCalendarDate, HttpServletRequest aRequest) throws NamingException, ParseException {
		IPolyclinicMedCaseService service = Injection.find(aRequest).getService(IPolyclinicMedCaseService.class) ;
		return service.getWorkCalendarDay(aWorkCalendar,aWorkFucntion, aCalendarDate) ;
		
	}
	public Long getCurrentCalendarDay(HttpServletRequest aRequest) {
		Long curCalDay = (Long)aRequest.getSession().getAttribute("smo_visit.currentCalendarDay") ;
		return curCalDay ;
	}
	
	public String getTableHeaderByDayAndFunction(String aDateStart, String aDateFinish,Long aWidthSpec, Long aWidthDate, HttpServletRequest aRequest) throws Exception {
		Date dateStart = DateFormat.parseSqlDate(aDateStart) ;
		Date dateFinish = DateFormat.parseSqlDate(aDateFinish) ;
		StringBuilder tr  = new StringBuilder() ;
		SimpleDateFormat FORMAT_1 = new SimpleDateFormat("dd.MM.yyyy");
		Calendar calnext = Calendar.getInstance() ;
		calnext.setTime(dateStart) ;
		Calendar calstop = Calendar.getInstance() ;
		calstop.setTime(dateFinish) ;
		calstop.add(Calendar.DATE, 1) ;
		StringBuilder div = new StringBuilder() ;
		tr.append("<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\"><tr class=\"x-grid-hd-row x-grid-header\">");
		tr.append("<td class=\"x-grid-hd grid-td-id\"><div class='x-grid-hd-spec'>") ;
		tr.append("Специалист") ;
		tr.append("</div></td>") ;
		 Long width = aWidthSpec+3  ;
		div.append("<div class='x-grid-split x-grid-split-spec' unselectable='on' style='left:")
			.append(width)
			.append("px'> </div>");
		calnext.setTime(dateStart) ;
		int i = 0;
		while (
					calnext.getTime().getTime()<calstop.getTime().getTime()
					) {
			i++ ;
			width = width+aWidthDate + 6;
			tr.append("<td class=\"x-grid-hd \"><div class='x-grid-hd-date'>");
			tr.append(FORMAT_1.format(calnext.getTime())) ;
			tr.append("</div></td>");
			div.append("<div class='x-grid-split x-grid-split-date' unselectable='on' style='left:")
			.append(width)
			.append("px'> </div>");
			calnext.add(Calendar.DATE, 1) ;
		}
		tr.append("</tr></table>") ;
		tr.append(div) ;
		return tr.toString();		
	}
	@SuppressWarnings("deprecation")
	public String getTableBodyByDayAndFunction(String aDateStart, String aDateFinish, Long aVocWorkFunctionId,String aMethodName, HttpServletRequest aRequest) throws Exception {
		IWorkerService service = Injection.find(aRequest).getService(IWorkerService.class) ;
		Date dateStart = DateFormat.parseSqlDate(aDateStart) ;
		Date dateFinish = DateFormat.parseSqlDate(aDateFinish) ;
		//System.out.println("Загрузка данных... VWF = "+aVocWorkFunctionId + " период="+aDateStart+"-"+aDateFinish) ;
		List<TableTimeBySpecialists> listSpec  = service.getTableByDayAndFunction(dateStart, dateFinish, aVocWorkFunctionId);
		//System.out.println("Обработка данных... построение таблицы") ;
		Calendar calnext = Calendar.getInstance() ;
		calnext.setTime(dateStart) ;
		Calendar calstop = Calendar.getInstance() ;
		calstop.setTime(dateFinish) ;
		calstop.add(Calendar.DATE, 1) ;
		Calendar calcurrent = Calendar.getInstance() ;
		//Date datestop = dateFinish.setDate(dateFinish.getDay()+1) ;
		Long idspec = null ;
		String spec = "" ;
		StringBuilder tr  = new StringBuilder() ;
		SimpleDateFormat FORMAT_1 = new SimpleDateFormat("dd.MM.yyyy");
		//SimpleDateFormat FORMAT_2 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat FORMAT_2 = new SimpleDateFormat("dd.MM.yyyy");
		calnext.setTime(dateStart) ;
		int j =0;

		for (int i = 0 ; i< listSpec.size(); i++) {
			TableTimeBySpecialists row = listSpec.get(i) ;
			if (idspec==null) {
				spec = service.getWorkFunctionInfoById(row.getSpecialistId()) ;
				calnext.setTime(dateStart) ;
				tr.append("<tr") ;
				tr.append(" class=\"x-grid-row x-grid-row-alt\">") ;
				tr.append("<td class=\"x-grid-col \"><div class='x-grid-hd-spec'>") ;
				tr.append(spec) ;
				tr.append("</div></td>") ;
				j=1;
			}
			if (idspec!=null &&!row.getSpecialistId().equals(idspec)) {
				spec = service.getWorkFunctionInfoById(row.getSpecialistId()) ;
				// новая строка
				while (
						calnext.getTime().getTime()<calstop.getTime().getTime()
							)  {
					 tr.append("<td >")  
					 	.append("<div class='x-grid-col x-grid-cell-inner x-grid-hd-date'>")
					 	.append("-")
					 	.append("</div>")
					 	.append("</td>");
				
//tr.append("<td class=\"x-grid-hd-date\"><div class='x-grid-col x-grid-cell-inner'>-</div></td>");
					calnext.add(Calendar.DATE, 1) ;
				}
				
					if (j==1) {
						j=2 ;
					} else{
						j=1 ;
					}
			
			
				calnext.setTime(dateStart) ;
				tr.append("</tr><tr");
				if (j==1)  {
					tr.append(" class=\"x-grid-row x-grid-row-alt\"") ;
				} else {
					tr.append(" class=\"x-grid-row\"");
				}
				tr.append(">") ;
				tr.append("<td class=\"x-grid-col \"><div class='x-grid-hd-spec'>") ;
				//tr.append(row.getSpecialist()) ;
				tr.append(spec) ;
				tr.append("</div></td>") ;
				
			} else {
				// продолжается текущая строка
			}
			calcurrent.setTime(row.getDate()) ;
			
			if (calcurrent.getTime().getTime()==calnext.getTime().getTime()) {	
				 tr.append("<td >")  
				 .append("<div class='x-grid-col x-grid-cell-inner x-grid-hd-date'>")
				    .append("<a href='javascript:")
				 	.append(aMethodName)
				 	.append("(\"").append(row.getSpecialistId())	.append("\"")
				 	.append(",\"").append(row.getCalendarDayId()).append("\"")
				 	.append(",\"").append(row.getTimeMin()).append("\"")
				 	//.append(",\"").append(row.getSpecialist()).append("\"")
				 	.append(",\"").append(spec).append("\"")
				 	.append(",\"").append(FORMAT_2.format(row.getDate().getTime())).append("\"")
				 	.append(",\"").append(1).append("\"")
				 	.append(")")
				 	.append("'>") 
				 	.append(row.getTimeMin())
				 	.append("</a>-")
				 	.append("<a href='javascript:")
				 	.append(aMethodName)
				 	.append("(\"").append(row.getSpecialistId())	.append("\"")
				 	.append(",\"").append(row.getCalendarDayId()).append("\"")
				 	.append(",\"").append(row.getTimeMax()).append("\"")
				 	//.append(",\"").append(row.getSpecialist()).append("\"")
				 	.append(",\"").append(spec).append("\"")
				 	.append(",\"").append(FORMAT_2.format(row.getDate().getTime())).append("\"")
				 	.append(",\"").append(0).append("\"")
				 	.append(")")
				 	.append("'>") 
				 	.append(row.getTimeMax())
				 	.append("</a>")
				 	.append("</div>")
				 	.append("</td>") ;
			} else {
				while (
						calcurrent.getTime().getTime()!=calnext.getTime().getTime() 
							&& calcurrent.getTime().getTime()<calstop.getTime().getTime()
							) {
					 tr.append("<td >")  
					 	.append("<div class='x-grid-col x-grid-cell-inner x-grid-hd-date'>")
					 	.append("-")
					 	.append("</div>")
					 	.append("</td>");
//tr.append("<td class=\"x-grid-hd-date\"><div class='x-grid-col x-grid-cell-inner'>-</div></td>");
					calnext.add(Calendar.DATE, 1) ;
				}
				if (calcurrent.getTime().getTime()==calnext.getTime().getTime()) {	
					 tr.append("<td><div class='x-grid-col x-grid-col-id x-grid-cell-inner x-grid-hd-date'>")  
				 	.append("<a href='javascript:")
				 	.append(aMethodName)
				 	.append("(\"").append(row.getSpecialistId())	.append("\"")
				 	.append(",\"").append(row.getCalendarDayId()).append("\"")
				 	.append(",\"").append(row.getTimeMin()).append("\"")
				 	//.append(",\"").append(row.getSpecialist()).append("\"")
				 	.append(",\"").append(spec).append("\"")
				 	.append(",\"").append(FORMAT_2.format(row.getDate().getTime())).append("\"")
				 	.append(",\"").append(1).append("\"")
				 	.append(")")
				 	.append("'>") 
				 	.append(row.getTimeMin())
				 	.append("</a>- ")
				 	.append("<a href='javascript:")
				 	.append(aMethodName)
				 	.append("(\"").append(row.getSpecialistId())	.append("\"")
				 	.append(",\"").append(row.getCalendarDayId()).append("\"")
				 	.append(",\"").append(row.getTimeMax()).append("\"")
				 	//.append(",\"").append(row.getSpecialist()).append("\"")
				 	.append(",\"").append(spec).append("\"")
				 	.append(",\"").append(FORMAT_2.format(row.getDate().getTime())).append("\"")
				 	.append(",\"").append(0).append("\"")
				 	.append(")")
				 	.append("'>") 
				 	.append(row.getTimeMax())
				 	.append("</a>")
				 	.append("</div></td>") ;
				}				
			}
			
			calnext.add(Calendar.DATE, 1);
			idspec = row.getSpecialistId() ;
		}
		if (listSpec.size()>0) {
			
				while (
				calnext.getTime().getTime()<calstop.getTime().getTime()
					)  {
			 tr.append("<td >")  
			 	.append("<div class='x-grid-col x-grid-cell-inner x-grid-hd-date'>")
			 	.append("-")
			 	.append("</div>")
			 	.append("</td>");
		
//tr.append("<td class=\"x-grid-hd-date\"><div class='x-grid-col x-grid-cell-inner'>-</div></td>");
			calnext.add(Calendar.DATE, 1) ;
				}
		} else {
			tr.append("<td>Нет данных</td>") ;
		}
		return tr.toString();
	}
	public String getCalendarTimeId(Long aCalendarDay, String aCalendarTime, Long aMinIs, HttpServletRequest aRequest) throws Exception {
		IWorkerService service = Injection.find(aRequest).getService(IWorkerService.class) ;
		Time time = DateFormat.parseSqlTime(aCalendarTime) ;
		String ret = service.getCalendarTimeId(aCalendarDay, time, aMinIs) ;
		if (ret==null) {
			throw new IllegalArgumentException("На это число нет свободного времени") ;
		}
		return ret ;
	}
	public String getDefaultDate(Long aFuncId, HttpServletRequest aRequest) throws Exception {
		IWorkerService service = Injection.find(aRequest).getService(IWorkerService.class) ;
		return service.getDayBySpec(aFuncId);
		
	}
}
