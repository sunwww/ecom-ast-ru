package ru.ecom.web.poly.action.visit.prerecord;

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
import ru.nuzmsh.web.tags.helper.RolesHelper;

public class ListTimesAction  extends BaseAction {
    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
    	StringBuilder sql = new StringBuilder() ;
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		String addParam=PreRecordAction.saveData(aRequest) ;
		Boolean isRemoteUser = true ;
		String vocWorkFunction= aRequest.getParameter("vocWorkFunction") ;
		String workCalendarDay = aRequest.getParameter("workCalendarDay") ;

		String lpuRemoteUser="-1";
		sql.append(" select wct.id,cast(wct.timeFrom as varchar(5)) as wcttimeFrom")
			.append(" ,case when wct.medCase_id is null and wct.prepatient_id is null and (wct.prepatientinfo is null or wct.prepatientinfo='') then 0 when wct.prepatient_id is not null or (wct.prepatientinfo is not null and wct.prepatientinfo!='') then 2 else 1 end")
			.append(" ,wct.medCase_id");
		sql.append(" ,coalesce(pat.lastname||' '||pat.firstname||' '||coalesce(pat.middlename,'Х')||coalesce(' '||pat.phone,'')||coalesce(' ('||pat.patientSync||')','')") ;
		sql.append(", prepat.lastname ||' '||prepat.firstname||' '||coalesce(prepat.middlename,'Х')||coalesce(' '||prepat.phone,'')||coalesce(' ('||prepat.patientSync||')','')") ;
		sql.append(",wct.prepatientInfo) as fio") ;
		sql.append(", prepat.id as prepatid,vis.dateStart as visdateStart") ;
		sql.append(",coalesce(prepat.lastname,wct.prepatientInfo) as prepatLast") ;
		sql.append(",pat.lastname as patLast,coalesce(pat.id,prepat.id) as patid")
			.append(", case when su.isRemoteUser='1' then 'preDirectRemoteUsername' when su1.isRemoteUser='1' then 'directRemoteUsername' else '' end as fontDirect") ; 
		if (isRemoteUser) {
			sql.append(", case when vsrt.isRemoteRayon='1' then 'ЗАНЯТО' when vsrt.isViewOnlyMineDoctor='1' then 'ЗАНЯТО'  when vsrt.isViewOnlyDoctor='1' then 'ЗАНЯТО' else null end as reserve") ;
		} else {
			sql.append(", case when wcd.calendarDate!=current_date then case when vsrt.isRemoteRayon='1' then 'РЕЗЕРВ УДАЛ.РАЙОН' when vsrt.isViewOnlyMineDoctor='1' then 'РЕЗЕРВ ВРАЧА'  when vsrt.isViewOnlyDoctor='1' then 'РЕЗЕРВ ВРАЧАМ' else null end else null end as reserve") ;
		}
		if (isRemoteUser) {
			sql.append(", case when sw.lpu_id!='"+(lpuRemoteUser!=null?lpuRemoteUser:"")+"' then 1 else null end as notViewRetomeUser1") ;
			sql.append(", case when w.lpu_id!='"+(lpuRemoteUser!=null?lpuRemoteUser:"")+"' then 1 else null end as notViewRetomeUser2") ;
		}
		sql.append(" from WorkCalendarTime wct") ;
		sql.append(" left join VocServiceReserveType vsrt on vsrt.id=wct.reserveType_id") ;
		sql.append(" left join MedCase vis on vis.id=wct.medCase_id")
			.append(" left join SecUser su on su.login=wct.createPreRecord ") 
			.append(" left join SecUser su1 on su1.login=vis.username ");
		sql.append(" left join WorkCalendarDay wcd on wcd.id=wct.workCalendarDay_id") ;
		if (isRemoteUser) {
			sql.append(" left join WorkCalendar wc on wc.id=wcd.workCalendar_id") ;
			sql.append(" left join WorkFunction wf on wf.id=wc.workFunction_id") ;
			sql.append(" left join Worker w on w.id=wf.worker_id") ;
			sql.append(" left join WorkFunction swf on swf.secUser_id=su.id") ;
			sql.append(" left join Worker sw on sw.id=swf.worker_id") ;
		}
		sql.append(" left join patient pat on pat.id=vis.patient_id");
		sql.append(" left join patient prepat on prepat.id=wct.prepatient_id");
		sql.append(" where wct.workCalendarDay_id='").append(workCalendarDay).append("'");
		if (isRemoteUser) {
			sql.append(" and (vsrt.isViewRemoteUser is null or vsrt.isViewRemoteUser='0') ");
		}
		sql.append(" order by wct.timeFrom");
		StringBuilder res = new StringBuilder() ;
		
		Collection<WebQueryResult> list = service.executeNativeSql(sql.toString(),50);
		String frmName = "frmTime" ;
		if (vocWorkFunction!=null) frmName=frmName+"_"+vocWorkFunction ;
		res.append("<form name='").append(frmName).append("' id='").append(frmName).append("' action=\"javascript:void\">") ;
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
			if (i==1) res.append("") ;
			boolean info=true ;
			boolean reserve = false ;
			int pre = Integer.valueOf(""+wqr.get3());
			//TODO = >
			if (pre>0) {
				if (isRemoteUser && wqr.get14()!=null && !(""+wqr.get14()).equals("")) info=false ;
				if (isRemoteUser && wqr.get13()!=null && !(""+wqr.get13()).equals("")) info=false ;
			}
			if (wqr.get12()!=null && !(""+wqr.get12()).equals("")) reserve = true ;
			if (pre==1) {
				
				if (!info) {
					res.append("<div id='liTimeBusyForRemoteUser' class='button fb'>") 
					.append("<p class='label'>").append(wqr.get2()) .append(" ") ;
					res.append("</p>") ;
				} else {
					if (wqr.get7()!=null) {
						res.append("<div id='liTimeDirect' class='").append(wqr.get11()!=null?wqr.get11():"").append("' ><strike>") 
						.append(wqr.get2()) .append(" ") ;
						res.append(wqr.get5()).append("</strike>") ;
					} else {
						res.append("<div id='liTimeDirect' class='").append(wqr.get11()!=null?wqr.get11():"").append("'>") 
						.append(wqr.get2()) .append(" ") ;
						res.append(wqr.get5()) ;
					}
				}
			} else if (pre==2) {
				if (!info) {
					res.append("<div id='liTimeBusyForRemoteUser' class='button fb'>") 
					.append("<p class='label'>").append(wqr.get2()) .append("</p>") ;
					//res.append("ЗАНЯТО").append("") ;
				} else {
					if (wqr.get4()!=null) {
						String prelastname = ""+wqr.get8() ;
						String lastname = ""+wqr.get9() ;
						res.append("<div id='liTimePre' class='").append(wqr.get11()!=null?wqr.get11():"").append("'>") ;
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
						res.append("<div id='liTimePre' class='").append(wqr.get11()!=null?wqr.get11():"").append("'>") .append(" <a href=\"javascript:patientCame('")
						.append(wqr.get1()).append("','").append(wqr.get5())
						.append("','").append(wqr.get6()).append("')\">")
						.append(wqr.get2()).append("</a>")
						.append(" ") ;
						res.append(wqr.get5()).append(" <a href=\"javascript:deleteTime('").append(wqr.get1()).append("')\">У</a>")  ;
						
					}
				}
			} else {
				if (!info) {
					res.append("<div id='liTimeBusyForRemoteUser' >") 
					.append(wqr.get2()) .append(" ") ;
					res.append("ЗАНЯТО").append("") ;
				} else {
					if (reserve) {
						res.append("<div id='liReserve'>") ;
						res.append(wqr.get2()).append(" ") ;
						res.append(wqr.get12()) ;
					} else {
						
						res.append("<div id='liTime' class=\"button fb \" onclick=\"step6('workCalendarTime=").append(wqr.get1()).append("&wct=").append(wqr.get1()).append(addParam).append("')\">") ;
						res.append("<p class='label'>").append(wqr.get2()).append("</p>") ;
					}
				}
			}
			if (wqr.get10()!=null && info) {
				res.append("<a onclick='getDefinition(\"entityShortView-mis_patient.do?id=")
					.append(wqr.get10()).append("\", event); return false ;' ondblclick='javascript:goToPage(\"entityView-mis_patient.do\",\"")
					.append(wqr.get10()).append("\")'><img src=\"/skin/images/main/view1.png\" alt=\"Просмотр записи\" title=\"Просмотр записи\" height=\"16\" width=\"16\"></a>") ;
			}
			//res.append(" (").append(wqr.get3()).append(" из ").append(wqr.get5()).append(")") ;
			res.append("</div>") ;
			if (i>=cntLi) {
				res.append("") ;
				i=0 ;
			}
			first=false ;
		}
		if (i<=cntLi) {
			res.append("") ;
		}
		res.append("") ;
		aRequest.setAttribute("listTimes", res.toString()) ;
        return aMapping.findForward("success") ;

    }
}