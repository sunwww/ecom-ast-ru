package ru.ecom.web.poly.actions.visit.record;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.ejb.services.util.ConvertSql;
import ru.ecom.mis.ejb.service.worker.IWorkCalendarService;
import ru.ecom.web.login.LoginInfo;
import ru.ecom.web.poly.actions.visit.prerecord.PreRecordAction;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.struts.BaseAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;

public class RecordAction extends BaseAction  {
    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
    	IWebQueryService serviceWeb = Injection.find(aRequest).getService(IWebQueryService.class) ;
    	IWorkCalendarService service = Injection.find(aRequest).getService(IWorkCalendarService.class) ;
    	
		
		//System.out.println("patid="+aPatientId) ;
    	PreRecordAction.saveData(aRequest) ;
		String username = LoginInfo.find(aRequest.getSession(true)).getUsername() ;
		Long aFunction = ConvertSql.parseLong(aRequest.getParameter("vocWorkFunction")) ; 
		Long aSpec = ConvertSql.parseLong(aRequest.getParameter("workCalendar")) ; 
		Long aDay = ConvertSql.parseLong(aRequest.getParameter("workCalendarDay")) ; 
		Long aTime = ConvertSql.parseLong(aRequest.getParameter("workCalendarTime")) ; 
		String aPatInfo = aRequest.getParameter("lastname")+" "+aRequest.getParameter("firstname")+" "+aRequest.getParameter("middlename") ;
		Long aPatientId = ConvertSql.parseLong(aRequest.getParameter("patient")) ; 
		service.recordByPatient(username, aFunction, aSpec,aDay,aTime,aPatInfo,aPatientId, 0L,2L) ;
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
		Calendar calC = Calendar.getInstance() ;
		SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm") ;
		aRequest.setAttribute("currentDate", format.format(calC.getTime())) ;
		aRequest.setAttribute("documentInfo", "Для приема врача Вам необходимо иметь при себе следующие документы :") ;
    	return aMapping.findForward("success") ;
    }
}