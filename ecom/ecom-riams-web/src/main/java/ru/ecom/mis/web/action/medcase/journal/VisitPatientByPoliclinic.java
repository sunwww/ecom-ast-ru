package ru.ecom.mis.web.action.medcase.journal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.mis.ejb.service.medcase.IReportsService;
import ru.ecom.mis.web.action.util.ActionUtil;
import ru.ecom.poly.web.action.ticket.JournalBySpecialistForm;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.struts.BaseAction;

public class VisitPatientByPoliclinic  extends BaseAction {
    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
		JournalBySpecialistForm form = (JournalBySpecialistForm)aRequest.getSession().getAttribute("poly_journalBySpecForm");
		String id = aRequest.getParameter("id") ;
		IReportsService repService  = Injection.find(aRequest).getService(IReportsService.class) ;
		String groupBy =ActionUtil.updateParameter("VisitPatientByPoliclinic","typeGroup","1", aRequest) ;
		String view ; 
		
		String whereDop = "" ;
		if (id!=null && !id.equals("") && form!=null) {
			view=ActionUtil.setParameter("VisitPatientByPoliclinic","typeView","1", aRequest) ;
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
			if (ids[11]!=null && !ids[11].equals("")) 	form.setSocialStatus(Long.valueOf(ids[10])) ;
			if (ids[12]!=null && !ids[12].equals("")) 	form.setDefect(Long.valueOf(ids[10])) ;
			
		} else {
			view =ActionUtil.updateParameter("VisitPatientByPoliclinic","typeView","2", aRequest) ;
			//date =ActionUtil.updateParameter("VisitPatientByPoliclinic","typeDate","2", aRequest) ;
			aRequest.setAttribute("beginDate", form.getBeginDate()) ;
			aRequest.setAttribute("finishDate", form.getFinishDate()) ;
		}
		
		

		boolean isReestr = false ;
		if (view.equals("1")) isReestr=true ;
		
		
		//aRequest.setAttribute("specialist", form.getSpecialist()) ;
		aRequest.setAttribute("queryTextBegin", getTextQueryBegin(groupBy, form.getBeginDate(), form.getFinishDate(), form.getSpecialist()
				, form.getWorkFunction(), form.getLpu(), form.getServiceStream(),form.getWorkPlaceType(),form.getOrderLpu(),form.getOrderWorkFunction(), form.getSocialStatus(),form.getDefect())) ;
		aRequest.setAttribute("queryTextEnd", getTextQueryEnd(isReestr,whereDop,true, groupBy, form.getBeginDate()
				, form.getFinishDate(), form.getSpecialist(), form.getWorkFunction(), form.getLpu()
				, form.getServiceStream(),form.getWorkPlaceType(),form.getOrderLpu(),form.getOrderWorkFunction()
				, form.getSocialStatus(),form.getDefect())) ;
		
		aRequest.setAttribute("filterInfo", repService.getFilterInfoByOrder(
				form.getSpecialist(), form.getWorkFunction()
				, form.getLpu(), form.getServiceStream()
				,form.getWorkPlaceType(),form.getOrderLpu()
				,form.getOrderWorkFunction())) ;
		aRequest.setAttribute("groupByTitle", getTitle(groupBy)) ;
		return aMapping.findForward("success") ;

    }
    public String getTitle(String aGroupBy) {
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
		} else if (aGroupBy.equals("9")) {
			title = "группировка: Социальный статус" ;
		} else if (aGroupBy.equals("10")) {
			title = "группировка: Дефекты" ;
		} else {

			
		}

		return title ;
	}
	public String getFilter(Long aSpecialist
			, Long aWorkFunction, Long aLpu, Long aServiceStream, Long aWorkPlaceType, Long aOrderLpu, Long aOrderWF
			, Long aSocialStatus, Long aDefect) {
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
		if (aSocialStatus!=null&&aSocialStatus>Long.valueOf(0)){
			filter.append(" and p.socialStatus_id="+aSocialStatus) ;
		}
		if (aDefect!=null&&aDefect>Long.valueOf(0)){
			filter.append(" and t.medCaseDefect_id="+aDefect) ;
		}
		return filter.toString() ;
	}
	public String getFilterId(Long aSpecialist
			, Long aWorkFunction, Long aLpu, Long aServiceStream, Long aWorkPlaceType
			, Long aOrderLpu, Long aOrderWF
			, Long aSocialStatus, Long aDefect) {
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
		filter.append("||'t.dateStart'||':'") ;
		filter.append("||':'") ;
		if (aSocialStatus!=null&& aSocialStatus>Long.valueOf(0)){
			filter.append("||").append(aSocialStatus) ;
		}
		filter.append("||':'") ;
		if (aDefect!=null&& aDefect>Long.valueOf(0)){
			filter.append("||").append(aDefect) ;
		}
		return filter.toString() ;
	}
	

	public String getTextQueryBegin(String aGroupBy,String aStartDate, String aFinishDate
			, Long aSpecialist, Long aWorkFunction, Long aLpu, Long aServiceStream, Long aWorkPlaceType
			, Long aOrderLpu, Long aOrderWF, Long aSocialStatus, Long aDefect) {
		StringBuilder sql = new StringBuilder() ;
		String id = "" ;
		String name = "" ;
		String id1=null ;
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
		} else if (aGroupBy.equals("9")) {
			//vocWorkFunction
			id = "pvs.id||'-'||pvss.id||'-'||ms.id" ;
			id1 = "pvs.id-pvss.id-ms.id" ;
			name = "pvss.name as pvssname,ms.name as msname,pvs.name" ;
		} else if (aGroupBy.equals("10")) {
			//vocWorkFunction
			id = "vmcd.id" ;
			name = "vmcd.name" ;
		} else{
			//date
			
			id = "to_char(t.dateStart,'dd.mm.yyyy')" ;
			name = "to_char(t.dateStart,'dd.mm.yyyy')" ;
		}
		
		sql.append("SELECT '").append(aStartDate).append(":")
		.append(aFinishDate).append(":'||coalesce(").append(id).append(",'0')||':")
		.append(id1!=null?id1:id).append("'").append(getFilterId(aSpecialist, aWorkFunction, aLpu, aServiceStream, aWorkPlaceType,aOrderLpu,aOrderWF,aSocialStatus,aDefect)).append(" as id") ;
		sql.append(",").append(name).append(" as tfield") ;
		
		return sql.toString() ;
	}
	public String getTextQueryEnd(boolean aReestr,String aWhereDop,boolean aOrderByProcent,String aGroupBy,String aStartDate, String aFinishDate
			, Long aSpecialist, Long aWorkFunction, Long aLpu, Long aServiceStream, Long aWorkPlaceType
			, Long aOrderLpu, Long aOrderWF, Long aSocialStatus, Long aDefect) {
		StringBuilder sql = new StringBuilder() ;
		String group = "" ;
		String order = "" ;
		String sqlAdd = "" ;
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
			group = "pvs.id,pvs.name,pvss.id,pvss.name,ms.id,ms.name" ;
			order = "ms.name,pvs.name,pvss.name" ;
			sqlAdd = " left join medCase usl on usl.parent_id=t.id and usl.dtype='ServiceMedCase' left join MedService ms on ms.id=usl.medService_id " ;
		} else if (aGroupBy.equals("10")) {
			group = "vmcd.id,vmcd.name" ;
			order = "vmcd.name" ;
		} else{
			//date
			group = "t.dateStart" ;
			order = "t.dateStart" ;
		}
		
		sql.append("from medcase t")
			.append(" left join WorkFunction wf on wf.id=t.workfunctionExecute_id")
			.append(" left join vocMedCaseDefect vmcd on vmcd.id=t.medCaseDefect_id")
			.append(" left join worker w on w.id=wf.worker_id")
			.append(" left join patient wp on wp.id=w.person_id")
			.append(" left join VocWorkFunction vwf on vwf.id=wf.workFunction_id")
			//.append(" left join medcase t on wct.id=t.timeplan_id and t.dtype='Visit'")
			.append(" left join WorkFunction owf on owf.id=t.orderWorkFunction_id")
			.append(" left join SecUser osu on osu.id=owf.secUser_id")
			.append(" left join worker ow on ow.id=owf.worker_id")
			.append(" left join patient owp on owp.id=ow.person_id")
			.append(" left join VocWorkFunction ovwf on ovwf.id=owf.workFunction_id");
		sql.append(" LEFT JOIN VocReason vr on vr.id=t.visitReason_id") ;
		sql.append(" LEFT JOIN vocWorkPlaceType vwpt on vwpt.id=t.workPlaceType_id") ;
		sql.append(" LEFT JOIN VocServiceStream vss on vss.id=t.serviceStream_id") ;
		sql.append(" LEFT JOIN Patient p ON p.id=t.patient_id") ;
		sql.append(" left join VocSocialStatus pvss on pvss.id=p.socialStatus_id");
		sql.append(" left join VocSex pvs on pvs.id=p.sex_id");
		sql.append(" left join vocrayon pvr on pvr.id=p.rayon_id") ;
		sql.append(" left join Address2 adr on adr.addressid=p.address_addressid");
		sql.append(" left join Omc_Oksm ok on p.nationality_id=ok.id") ;
		sql.append(" LEFT JOIN MisLpu lpu on lpu.id=w.lpu_id") ;
		sql.append(" LEFT JOIN MisLpu olpu on olpu.id=t.orderLpu_id") ;
		 sql.append(sqlAdd);
		sql.append(" WHERE ") ;
		sql.append("  t.dtype='Visit' and t.dateStart BETWEEN TO_DATE('").append(aStartDate).append("','dd.mm.yyyy') and TO_DATE('").append(aFinishDate).append("','dd.mm.yyyy')");
		sql.append(" and (t.noActuality is null or t.noActuality='0')") ;
		sql.append(getFilter(aSpecialist, aWorkFunction, aLpu, aServiceStream,aWorkPlaceType,aOrderLpu,aOrderWF,aSocialStatus,aDefect)) ;

		if (aReestr) {
			if (!aWhereDop.equals(""))sql.append(" and ").append(aWhereDop) ;
			sql.append(" ORDER BY ").append(order).append("");
		} else {
			
			sql.append(" GROUP BY ").append(group) ;
				//.append(" having count(distinct t.id)>0") ;
			if (aOrderByProcent) {
				sql.append(" ORDER BY ")
				//.append("(count(distinct case when t.visitResult_id is not null and (t.noActuality is null or t.noActuality='0') then t.id else null end) )/cast(count(distinct t.id) as numeric) desc ")
				//	.append(",count(distinct t.id),")
				.append(order).append("");
			} else {
				sql.append(" ORDER BY ").append(order).append("");
			}
		}
			
		return sql.toString() ;
	}
}
