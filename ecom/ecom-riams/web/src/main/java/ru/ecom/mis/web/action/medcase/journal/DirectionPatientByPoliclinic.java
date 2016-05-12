package ru.ecom.mis.web.action.medcase.journal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.hibernate.hql.classic.WhereParser;

import ru.ecom.mis.ejb.service.medcase.IReportsService;
import ru.ecom.web.util.ActionUtil;
import ru.ecom.poly.web.action.ticket.JournalBySpecialistForm;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.struts.BaseAction;

public class DirectionPatientByPoliclinic extends BaseAction {
    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
		JournalBySpecialistForm form = (JournalBySpecialistForm)aRequest.getSession().getAttribute("poly_journalBySpecForm");
		String id = aRequest.getParameter("id") ;
		//boolean aTicketIs =  fr!=null &&fr.equals("1")?true:false;
		//IWorkerService service = Injection.find(aRequest).getService(IWorkerService.class) ;
		IReportsService repService  = Injection.find(aRequest).getService(IReportsService.class) ;
		String groupBy =ActionUtil.updateParameter("DirectionPatientByPoliclinic","typeGroup","1", aRequest) ;
		String typeUser =ActionUtil.updateParameter("DirectionPatientByPoliclinic","typeUser","3", aRequest) ;
		String view ; 
		String date ;
		String whereDop = "" ;
		if (id!=null && !id.equals("")) {
			view=ActionUtil.setParameter("DirectionPatientByPoliclinic","typeView","1", aRequest) ;
			String[] ids = id.split(":") ;
			aRequest.setAttribute("beginDate", ids[0]) ;
			aRequest.setAttribute("finishDate", ids[1]) ;
			System.out.println(ids[3]) ;
			if (ids[3]!=null && !ids[3].equals("") ) 	{
				whereDop = ids[3] ;
				if (ids[2]!=null && !ids[2].equals("")&& !ids[2].equals("0")) {
					if (whereDop.equals("wcd.calendarDate") || whereDop.equals("t.createDate")) {
						whereDop=whereDop+"=to_date('"+ids[2]+"','dd.mm.yyyy')" ;
					} else {
						whereDop=whereDop+"='"+ids[2]+"'" ;
					}
				} else{
					whereDop=whereDop+" is null" ;
				}
			}
			if (ids[4]!=null && !ids[4].equals("")) 	form.setSpecialist(Long.valueOf(ids[4])) ;
			if (ids[5]!=null && !ids[5].equals("")) 	form.setWorkFunction(Long.valueOf(ids[5])) ;
			if (ids[6]!=null && !ids[6].equals("")) 	form.setLpu(Long.valueOf(ids[6])) ;
			if (ids[7]!=null && !ids[7].equals("")) 	form.setServiceStream(Long.valueOf(ids[7])) ;
			if (ids[8]!=null && !ids[8].equals("")) 	form.setWorkPlaceType(Long.valueOf(ids[8])) ;
			if (ids[9]!=null && !ids[9].equals("")) 	form.setOrderLpu(Long.valueOf(ids[9])) ;
			if (ids[10]!=null && !ids[10].equals("")) 	form.setOrderWorkFunction(Long.valueOf(ids[10])) ;
			if (ids[11]!=null && !ids[11].equals("")) {
				if (ids[11].equals("wcd.calendarDate")) {
					date = ActionUtil.setParameter("DirectionPatientByPoliclinic","typeDate","2", aRequest) ;
				} else {
					date = ActionUtil.setParameter("DirectionPatientByPoliclinic","typeDate","1", aRequest) ;
				}
				
			} else {
				date =ActionUtil.updateParameter("DirectionPatientByPoliclinic","typeDate","2", aRequest) ;
			}
		} else {
			view =ActionUtil.updateParameter("DirectionPatientByPoliclinic","typeView","2", aRequest) ;
			date =ActionUtil.updateParameter("DirectionPatientByPoliclinic","typeDate","2", aRequest) ;
			aRequest.setAttribute("beginDate", form.getBeginDate()) ;
			aRequest.setAttribute("finishDate", form.getFinishDate()) ;
		}
		
		String fldDate = "" ;

		boolean isReestr = false ;
		if (view.equals("1")) isReestr=true ;
		if (date.equals("2")) {
			fldDate = "wcd.calendarDate" ;
		} else {
			fldDate = "t.createDate" ;
		}

		
		//aRequest.setAttribute("specialist", form.getSpecialist()) ;
		aRequest.setAttribute("queryTextBegin", getTextQueryBegin(fldDate, groupBy, form.getBeginDate(), form.getFinishDate(), form.getSpecialist()
				, form.getWorkFunction(), form.getLpu(), form.getServiceStream(),form.getWorkPlaceType(),form.getOrderLpu(),form.getOrderWorkFunction())) ;
		aRequest.setAttribute("queryTextEnd", getTextQueryEnd(isReestr,whereDop,fldDate,true, groupBy, form.getBeginDate()
				, form.getFinishDate(), form.getSpecialist(), form.getWorkFunction(), form.getLpu()
				, form.getServiceStream(),form.getWorkPlaceType(),form.getOrderLpu(),form.getOrderWorkFunction()
				, typeUser)) ;
		
		aRequest.setAttribute("filterInfo", repService.getFilterInfoByOrder(
				form.getSpecialist(), form.getWorkFunction()
				, form.getLpu(), form.getServiceStream()
				,form.getWorkPlaceType(),form.getOrderLpu()
				,form.getOrderWorkFunction())) ;
		aRequest.setAttribute("groupByTitle", getTitle(groupBy,date,typeUser)) ;
		return aMapping.findForward("success") ;

    }
    public String getTitle(String aGroupBy,String aTypeDate, String aTypeUser) {
		String title = "" ;
		if (aGroupBy.equals("2")) {
			title = "группировка: ЛПУ" ;
		} else if (aGroupBy.equals("3")) {
			title = "группировка: Врач" ;
		} else if (aGroupBy.equals("4")) {
			title = "группировка: Специальность" ;
		} else if (aGroupBy.equals("5")) {
			title = "группировка: Поток обслуживания" ;
		} else if (aGroupBy.equals("6")) {
			title = "группировка: Место обслуживания" ;
		} else if (aGroupBy.equals("7")) {
			title = "группировка: Внешний направитель" ;
		} else if (aGroupBy.equals("8")) {
			title = "группировка: Направитель" ;
		} else {

			
		}
		if (aTypeDate.equals("2")) {
			title = "искать по дате: направления " +title;
		} else{
			title = "искать по дате: создания " +title;
		}
		if (aTypeUser.equals("1")) {
			title = title+" поиск по пользователям из других ЛПУ" ;
		} else if (aTypeUser.equals("2")) {
			title = title+" поиск по пользователям из текущего ЛПУ" ;
		}
		return title ;
	}
	public String getFilter(Long aSpecialist
			, Long aWorkFunction, Long aLpu, Long aServiceStream, Long aWorkPlaceType, Long aOrderLpu, Long aOrderWF) {
		StringBuilder filter = new StringBuilder() ;
		
		if (aSpecialist!=null&&aSpecialist>Long.valueOf(0)){
			filter.append(" and wf.id="+aSpecialist) ;
		}
		if (aWorkFunction!=null&&aWorkFunction>Long.valueOf(0)){
			filter.append(" and wf.workFunction_id="+aWorkFunction) ;
		}
		if (aLpu!=null&&aLpu>Long.valueOf(0)){
			filter.append(" and w.lpu_id="+aLpu) ;
		}
		if (aServiceStream!=null&&aServiceStream>Long.valueOf(0)){
			filter.append(" and t.serviceStream_id="+aServiceStream) ;
		}
		if (aWorkPlaceType!=null&&aWorkPlaceType>Long.valueOf(0)){
			filter.append(" and t.workPlaceType_id="+aWorkPlaceType) ;
		}
		if (aOrderLpu!=null&&aOrderLpu>Long.valueOf(0)){
			filter.append(" and t.orderLpu_id="+aOrderLpu) ;
		}
		if (aOrderWF!=null&&aOrderWF>Long.valueOf(0)){
			filter.append(" and t.orderWorkFunction_id="+aOrderWF) ;
		}
		return filter.toString() ;
	}
	public String getFilterId(String aTypeDate, Long aSpecialist
			, Long aWorkFunction, Long aLpu, Long aServiceStream, Long aWorkPlaceType
			, Long aOrderLpu, Long aOrderWF) {
		StringBuilder filter = new StringBuilder() ;
		filter.append("||':'") ;
		if (aSpecialist!=null&&aSpecialist>Long.valueOf(0)){
			filter.append("||").append(aSpecialist) ;
		}
		filter.append("||':'") ;
		if (aWorkFunction!=null&&aWorkFunction>Long.valueOf(0)){
			filter.append("||").append(aWorkFunction) ;
		}
		filter.append("||':'") ;
		if (aLpu!=null&&aLpu>Long.valueOf(0)){
			filter.append("||").append(aLpu) ;
		}
		filter.append("||':'") ;
		if (aServiceStream!=null&&aServiceStream>Long.valueOf(0)){
			filter.append("||").append(aServiceStream) ;
		}
		filter.append("||':'") ;
		if (aWorkPlaceType!=null&& aWorkPlaceType>Long.valueOf(0)){
			filter.append("||").append(aWorkPlaceType) ;
		}
		filter.append("||':'") ;
		if (aOrderLpu!=null&& aOrderLpu>Long.valueOf(0)){
			filter.append("||").append(aOrderLpu) ;
		}
		filter.append("||':'") ;
		if (aOrderWF!=null&& aOrderWF>Long.valueOf(0)){
			filter.append("||").append(aOrderWF) ;
		}
		filter.append("||':'") ;
		filter.append("||'").append(aTypeDate).append("'||':'") ;
		return filter.toString() ;
	}
	

	public String getTextQueryBegin(String aTypeDate, String aGroupBy,String aStartDate, String aFinishDate
			, Long aSpecialist, Long aWorkFunction, Long aLpu, Long aServiceStream, Long aWorkPlaceType
			, Long aOrderLpu, Long aOrderWF) {
		StringBuilder sql = new StringBuilder() ;
		String id = "" ;
		String id1 = "" ;
		String name = "" ;
		boolean idrep = false ;
		if (aGroupBy.equals("2")) {
			//LPU
			id= "lpu.id";
			name = "lpu.name" ;
		} else if (aGroupBy.equals("3")) {
			id = "wf.id" ;
			name = "vwf.name||' '||wp.lastname||' '||wp.firstname||' '||wp.middlename" ;
		} else if (aGroupBy.equals("4")) {
			//vocWorkFunction
			id = "vwf.id" ;
			name = "vwf.name" ;
		} else if (aGroupBy.equals("5")) {
			//vocWorkFunction
			id = "t.serviceStream_id" ;
			name = "vss.name" ;
		} else if (aGroupBy.equals("6")) {
			id = "t.workPlaceType_id" ;
			name = "vwpt.name" ;
		} else if (aGroupBy.equals("7")) {
			//vocWorkFunction
			id = "t.orderLpu_id" ;
			name = "olpu.name" ;
		} else if (aGroupBy.equals("8")) {
			//vocWorkFunction
			id = "owf.id" ;
			name = "ovwf.name||' '||owp.lastname||' '||owp.firstname||' '||owp.middlename" ;
		} else{
			//date
			id1 = aTypeDate ;
			id = "to_char("+aTypeDate+",'dd.mm.yyyy')" ;
			name = "to_char("+aTypeDate+",'dd.mm.yyyy')" ;
			idrep = true ;
		}
		
		sql.append("SELECT '").append(aStartDate).append(":")
		.append(aFinishDate).append(":'||coalesce(").append(id).append(",'0')||':")
		.append(idrep?""+id1:id).append("'").append(getFilterId(aTypeDate, aSpecialist, aWorkFunction, aLpu, aServiceStream, aWorkPlaceType,aOrderLpu,aOrderWF)).append(" as id") ;
		sql.append(",").append(name).append(" as tfield") ;
		
		return sql.toString() ;
	}
	public String getTextQueryEnd(boolean aReestr,String aWhereDop, String aTypeDate,boolean aOrderByProcent,String aGroupBy,String aStartDate, String aFinishDate
			, Long aSpecialist, Long aWorkFunction, Long aLpu, Long aServiceStream, Long aWorkPlaceType
			, Long aOrderLpu, Long aOrderWF,String aTypeUser) {
		StringBuilder sql = new StringBuilder() ;
		String group = "" ;
		String order = "" ;
		if (aGroupBy.equals("2")) {
			//LPU
			group = "lpu.id,lpu.name" ;
			order = "lpu.name" ;
		} else if (aGroupBy.equals("3")) {
			//doctor
			group = "wf.id,vwf.name,wp.lastname,wp.firstname,wp.middlename" ;
			order = "vwf.name,wp.lastname,wp.firstname,wp.middlename" ;
		} else if (aGroupBy.equals("4")) {
			//vocWorkFunction
			group = "vwf.id,vwf.name" ;
			order = "vwf.name" ;
		} else if (aGroupBy.equals("5")) {
			//vocWorkFunction
			group= "t.serviceStream_id,vss.name" ;
			order = "vss.name" ;
		} else if (aGroupBy.equals("6")) {
			//vocWorkFunction
			group= "t.workPlaceType_id,vwpt.name" ;
			order = "vwpt.name" ;
		} else if (aGroupBy.equals("7")) {
			group= "t.orderLpu_id,olpu.name" ;
			order = "olpu.name" ;
		} else if (aGroupBy.equals("8")) {
			group = "owf.id,ovwf.name,owp.lastname,owp.firstname,owp.middlename" ;
			order = "ovwf.name,owp.lastname,owp.firstname,owp.middlename" ;
		} else if (aGroupBy.equals("9")) {
			group = "wcd.calendarDate" ;
			order = "wcd.calendarDate" ;
		} else{
			//date
			//group = "t.dateStart" ;
			//order = "t.dateStart" ;
			group = aTypeDate ;
			order = aTypeDate ;
		}
		
		sql.append("from WorkCalendarDay wcd")
			.append(" left join WorkCalendar wc on wc.id=wcd.workcalendar_id")
			.append(" left join WorkCalendarTime wct on wct.workcalendarday_id=wcd.id")
			.append(" left join WorkFunction wf on wf.id=wc.workfunction_id")
			.append(" left join worker w on w.id=wf.worker_id")
			.append(" left join patient wp on wp.id=w.person_id")
			.append(" left join VocWorkFunction vwf on vwf.id=wf.workFunction_id")
			.append(" left join medcase t on wct.id=t.timeplan_id and t.dtype='Visit'")
			.append(" left join WorkFunction owf on owf.id=t.orderWorkFunction_id")
			.append(" left join SecUser osu on osu.id=owf.secUser_id")
			.append(" left join worker ow on ow.id=owf.worker_id")
			.append(" left join patient owp on owp.id=ow.person_id")
			.append(" left join VocWorkFunction ovwf on ovwf.id=owf.workFunction_id");
		sql.append(" LEFT JOIN VocReason vr on vr.id=t.visitReason_id") ;
		sql.append(" LEFT JOIN vocWorkPlaceType vwpt on vwpt.id=t.workPlaceType_id") ;
		sql.append(" LEFT JOIN VocServiceStream vss on vss.id=t.serviceStream_id") ;
		sql.append(" LEFT JOIN Patient p ON p.id=t.patient_id") ;
		sql.append(" left join vocrayon pvr on pvr.id=p.rayon_id") ;
		sql.append(" left join Address2 adr on adr.addressid=p.address_addressid");
		sql.append(" left join Omc_Oksm ok on p.nationality_id=ok.id") ;
		sql.append(" LEFT JOIN MisLpu lpu on lpu.id=w.lpu_id") ;
		sql.append(" LEFT JOIN MisLpu olpu on olpu.id=t.orderLpu_id") ;
		 ;
		sql.append(" WHERE ") ;
		sql.append("  ").append(aTypeDate) ;
		sql.append(" BETWEEN TO_DATE('").append(aStartDate).append("','dd.mm.yyyy') and TO_DATE('").append(aFinishDate).append("','dd.mm.yyyy')");
		sql.append(" ") ;
		sql.append(getFilter(aSpecialist, aWorkFunction, aLpu, aServiceStream,aWorkPlaceType,aOrderLpu,aOrderWF)) ;
		if (aTypeUser.equals("1")) {
			sql.append(" and osu.isRemoteUser='1'") ;
		} else if (aTypeUser.equals("2")) {
			sql.append(" and (osu.isRemoteUser='0' or osu.isRemoteUser is null)") ;
		}
		if (aReestr) {
			sql.append(" and wct.medcase_id is not null") ;
			if (!aWhereDop.equals(""))sql.append(" and ").append(aWhereDop) ;
			sql.append(" ORDER BY ").append(order).append("");
		} else {
			
			sql.append(" GROUP BY ").append(group)
				.append(" having count(distinct wct.medcase_id)>0") ;
			if (aOrderByProcent) {
				sql.append(" ORDER BY ")
				//.append("(count(distinct case when t.visitResult_id is not null and (t.noActuality is null or t.noActuality='0') then t.id else null end) )/cast(count(distinct wct.medcase_id) as numeric) desc ")
				//	.append(",count(distinct wct.medCase_id),")
				.append(order).append("");
			} else {
				sql.append(" ORDER BY ").append(order).append("");
			}
		}
			
		return sql.toString() ;
	}
}
