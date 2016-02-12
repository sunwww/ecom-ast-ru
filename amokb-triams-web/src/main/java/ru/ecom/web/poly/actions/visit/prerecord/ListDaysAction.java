package ru.ecom.web.poly.actions.visit.prerecord;

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
import ru.nuzmsh.web.struts.BaseAction;

public class ListDaysAction  extends BaseAction {
    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
    	IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		boolean isRemoteUser = true ; 
		StringBuilder sql = new StringBuilder() ;
		String addParam=PreRecordAction.saveData(aRequest) ;
		String vocWorkFunction= aRequest.getParameter("vocWorkFunction") ;
		String department= aRequest.getParameter("department") ;
		String workCalendar = aRequest.getParameter("workCalendar") ;
		String year=aRequest.getParameter("year") ; ;
		String month=aRequest.getParameter("month") ; ;
		sql.append(" select  wcd.id as wcdid, to_char(wcd.calendardate,'dd.mm.yyyy') as wcdcalendardate");
		sql.append(" ,to_char(wcd.calendardate,'dd') as CDday");
		sql.append(" ,count(case when wct.medCase_id is null and wct.prepatient_id is null and (wct.prepatientinfo is null or wct.prepatientinfo='') then 1 else null end) as cntFree");
		//if ()
		sql.append(" ,count(case when (vsrt.id is not null and (vsrt.isViewRemoteUser='0' or vsrt.isViewRemoteUser is null)) or (vsrt.id is null and wct.medCase_id is not null or wct.prepatient_id is not null or (wct.prepatientinfo is not null and wct.prepatientinfo!='')) then 1 else null end) as cntBusy");
		sql.append(" ,count(wct.id) as cntAll");
		sql.append(" from workCalendar wc"); 
		sql.append(" left join workcalendarday wcd on wcd.workcalendar_id=wc.id");
		sql.append(" left join workcalendartime wct on wct.workcalendarday_id=wcd.id");
		sql.append(" left join VocServiceReserveType vsrt on vsrt.id=wct.reserveType_id") ;
		sql.append(" where wc.id='").append(workCalendar).append("'"); 
		sql.append(" and wcd.calendardate>=CURRENT_DATE and to_char(wcd.calendardate,'mm.yyyy')='")
			.append(month).append(".").append(year).append("'");
		if (isRemoteUser) sql.append(" and (vsrt.isViewRemoteUser is null or vsrt.isViewRemoteUser='0')");
		sql.append(" group by wcd.id,wcd.calendardate");
		sql.append(" order by wcd.calendardate") ;
		Collection<WebQueryResult> list = service.executeNativeSql(sql.toString(),50);
		StringBuilder res = new StringBuilder() ;
		res.append("<form name='frmDate' id='frmDate' action='javascript:step5()'>") ;
		int monthInt= Integer.valueOf(month) ;
		res.append("<span class = 'spanNavigMonth'>") ;
		if (monthInt==1) {
			res.append("<a href=\"javascript:step4('")
			.append("month=").append(getMonth(12,false))
			.append("&year=").append(Integer.valueOf(year)-1).append(addParam);
			res.append("');\">")
			.append("<-")
			//.append(getMonth(12,true)).append(" ").append(Integer.valueOf(aYear)-1)
			.append("</a> ") ;
		} else {
			res.append("<a href=\"javascript:step4('")
			.append("month=").append(getMonth(monthInt-1,false))
			.append("&year=").append(year).append(addParam);
			res.append("');\">").append("<-")
			//.append(getMonth(month-1,true)).append(" ").append(Integer.valueOf(aYear))
			.append("</a> ") ;
		}
		res.append(" ").append(getMonth(monthInt,true).toUpperCase()).append(" ").append(year) ;
		if (monthInt==12) {
			res.append(" <a href=\"javascript:step4('")
			.append("month=").append(getMonth(1,false))
			.append("&year=").append(Integer.valueOf(year)+1).append(addParam);
			res.append("');\">")
			//.append("").append(getMonth(1,true))
			//.append(" ").append(Integer.valueOf(aYear)+1)
			.append("-></a>") ;
		} else {
			res.append("<a href=\"javascript:step4('")
			.append("month=").append(getMonth(monthInt+1,false))
			.append("&year=").append(Integer.valueOf(year)).append(addParam);
			res.append("');\">").append("")
			//.append(getMonth(month+1,true)).append(" ").append(Integer.valueOf(aYear))
			.append("-></a> ") ;
		}
		res.append("</span>") ;
		Calendar cal = Calendar.getInstance() ;
		cal.set(Calendar.YEAR, Integer.valueOf(year)) ;
		monthInt-- ;
		cal.set(Calendar.MONTH, monthInt) ;
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
				res.append("onclick=\"step5('workCalendarDay=").append(wqr.get1())
				.append("&date=").append(wqr.get2()) ;
				
				res.append(addParam);
				
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
		if (vocWorkFunction!=null) {
			res.append("<tr>") ;
			res.append("<td colspan='7' valign='top'><div id='rowStep6Time_").append("").append(vocWorkFunction).append("' >Выберите дату</div></td>") ;
			res.append("</tr>") ;
		}	
		res.append("</table></form>") ;
		aRequest.setAttribute("days", res.toString()) ;
        return aMapping.findForward("success") ;
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
}
