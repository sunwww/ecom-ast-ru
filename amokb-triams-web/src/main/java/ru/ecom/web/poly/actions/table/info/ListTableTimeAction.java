package ru.ecom.web.poly.actions.table.info;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.util.format.DateFormat;
import ru.nuzmsh.web.struts.BaseAction;

public class ListTableTimeAction extends BaseAction {
    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
    	
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		boolean remoteUser = true ;
		boolean aManyIs = false ;
		//String addParam=PreRecordAction.saveData(aRequest) ;
		String addParam="";
		StringBuilder sql = new StringBuilder() ;
		String vocworkFunction = aRequest.getParameter("vocWorkFunction") ;
		String workFunction = aRequest.getParameter("workFunction") ;
		StringBuilder addVWF = new StringBuilder() ;
		if (workFunction!=null) {
			addVWF.append(" and wf.id='").append(workFunction).append("' ") ;
		} else if (vocworkFunction!=null) {
			addVWF.append(" and vwf.id='").append(vocworkFunction).append("' ") ;
		} 
		System.out.println(addVWF) ;
		sql.append(" select wf.dtype,wf.id,vwf.name ");
		sql.append(" ,case when wf.dtype='GroupWorkFunction' then wf.groupName");
		sql.append(" 	when wf.dtype='PersonalWorkFunction' then wp.lastname||' '||wp.firstname||' '||wp.middlename");
		sql.append(" 	else '' end") ;
		sql.append(" ,list(") ;
		//sql.append("'Кабинет №'||");
		sql.append("room.name") ;
		//sql.append("||'<br/> Дни и время приема: '||room.comment");
		sql.append(")") ;
		sql.append(" ,coalesce(vad.code||' ','')||coalesce(vc.code||' ','') as dp ") ;
		sql.append(" ,to_char(min(case when wct.medCase_id is null and wct.prepatient_id is null and (wct.prepatientinfo is null or wct.prepatientinfo='') then wcd.calendarDate else null end),'dd.mm.yyyy') as datmin") ;
		sql.append(" ") ;
		sql.append(" ,").append(timelist("0")) ;
		sql.append(" ,").append(timelist("1")) ;
		sql.append(" ,").append(timelist("2")) ;
		sql.append(" ,").append(timelist("3")) ;
		sql.append(" ,").append(timelist("4")) ;
		sql.append(" ,").append(timelist("5")) ;
		sql.append(" ,").append(timelist("6")) ;
		sql.append(" ") ;
		 
		sql.append(" from WorkFunction wf") ;
		sql.append(" left join Worker w on w.id=wf.worker_id") ;
		sql.append(" left join Patient wp on wp.id=w.person_id") ;
		sql.append(" left join WorkCalendar wc on wc.workFunction_id=wf.id") ;
		sql.append(" left join WorkCalendarDay wcd on wcd.workCalendar_id=wc.id") ;
		sql.append(" left join WorkCalendarTime wct on wct.workCalendarDay_id=wcd.id") ;
		sql.append(" left join VocWorkFunction vwf on vwf.id=wf.workFunction_id") ;
		sql.append(" left join VocCategory vc on vc.id=category_id") ;
		sql.append(" left join VocAcademicDegree vad on vad.id=wf.degrees_id") ;
		sql.append(" left join MisLpu mlG on mlG.id=wf.lpu_id") ;
		sql.append(" left join MisLpu mlP on mlP.id=w.lpu_id") ;
		sql.append(" left join WorkPlace_WorkFunction wpf on wf.id=wpf.workFunctions_id") ;
		sql.append(" left join WorkPlace room on wpf.workPlace_id=room.id	") ;
		sql.append(" left join WorkPlace fb on fb.id=room.parent_id") ;
		sql.append(" left join MisLpu m1 on m1.id=wf.lpu_id ") ;
		sql.append(" left join MisLpu m2 on m2.id=w.lpu_id ") ;
		sql.append(" where") ;
		sql.append("  wc.id is not null and (wf.archival is null or wf.archival='0')") ; 
		sql.append("  ").append(addVWF) ;
		sql.append(" and (wf.DTYPE='PersonalWorkFunction' and (m2.isNoViewRemoteUser is null or m2.isNoViewRemoteUser='0') or wf.dtype='GroupWorkFunction' and (m1.isNoViewRemoteUser is null or m1.isNoViewRemoteUser='0')) and (wf.isNoViewRemoteUser is null or wf.isNoViewRemoteUser='0')") ;
		sql.append("  and wcd.calendardate between current_date and current_date+7") ;
		sql.append(" group by wf.dtype,wf.id,vwf.name,vad.code,vc.code,wf.groupName,wp.lastname,wp.firstname,wp.middlename,mlP.name,mlG.name") ;
		sql.append(" order by vwf.name") ;
		
		StringBuilder res = new StringBuilder() ;
		Collection<WebQueryResult> list = service.executeNativeSql(sql.toString(),50);
		
		 res.append("<table border='1px solid'>" ) ;
			res.append("<tr><th>ФИО врача</th><th>Кабинет</th>" ) ;
			Calendar cal = Calendar.getInstance() ;
			SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy") ;
			for (int i=0;i<7;i++) {
				res.append("<th>") ;
				res.append(format.format(cal.getTime())) ;
				res.append("</th>") ;
				cal.add(Calendar.DAY_OF_MONTH, 1);
			}
			res.append("</tr>") ;

		 for (WebQueryResult wqrS:list) {
				res.append("<tr>") ;
				res.append("<td><b>").append(wqrS.get3()).append("</b><br/>")
				.append(wqrS.get4()).append("<br/>") 
				.append(wqrS.get6()).append("</td>") ;
				res.append("<td>").append(wqrS.get5()).append("</td>") ;;
				res.append("<td>").append(replaceInTable(wqrS.get8())).append("</td>") ;
				res.append("<td>").append(replaceInTable(wqrS.get9())).append("</td>") ;
				res.append("<td>").append(replaceInTable(wqrS.get10())).append("</td>") ;
				res.append("<td>").append(replaceInTable(wqrS.get11())).append("</td>") ;
				res.append("<td>").append(replaceInTable(wqrS.get11())).append("</td>") ;
				res.append("<td>").append(replaceInTable(wqrS.get11())).append("</td>") ;
				res.append("<td>").append(replaceInTable(wqrS.get11())).append("</td>") ;
				res.append("</tr>") ;
		}
		res.append("</table>") ;
		aRequest.setAttribute("dep listFunctions", list) ;
		aRequest.setAttribute("tableTime", res.toString()) ;
        return aMapping.findForward("success") ;

    }
    private StringBuilder timelist (String aCalendar) {
		StringBuilder sql =new StringBuilder() ;
		sql.append(" list(distinct case when wcd.calendardate=current_date+").append(aCalendar).append(" then") ; 
		sql.append(" case when wct.medCase_id is null and wct.prepatient_id is null and") ; 
		sql.append(" (wct.prepatientinfo is null or wct.prepatientinfo='') then cast(wct.timeFrom as varchar(5))") ;
		sql.append(" else cast(wct.timeFrom as varchar(5))||' занято' end") ;
		sql.append(" else null") ;
		sql.append(" end) as timelist_").append(aCalendar) ;

    	return sql ; 
    }
    private StringBuilder replaceInTable(Object aTime) {
    	String timeS1 = ""+aTime ;
    	StringBuilder ret = new StringBuilder() ;
    	String[] times = timeS1.split(",") ;
    	ret.append("<table>") ;
    	ret.append("<tr><td>") ;
    	for (String tm:times) {
    		if (tm.indexOf("занято")!=-1) {
    			ret.append("<span class='busyTd'>").append(tm.replace(" занято", "")).append("</span>") ;
    		} else {
    			ret.append(tm) ;
    		}
        	ret.append("</td></tr><tr><td>") ;
    	}
    	ret.append("</td></tr>") ;
    	ret.append("</table>") ;
    	return ret ;
    }
}