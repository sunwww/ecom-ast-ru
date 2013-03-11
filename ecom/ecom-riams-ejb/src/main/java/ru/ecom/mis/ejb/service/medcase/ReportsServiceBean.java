package ru.ecom.mis.ejb.service.medcase;

import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.Remote;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.annotation.security.SecurityDomain;

import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.ecom.mis.ejb.domain.patient.voc.VocWorkPlaceType;
import ru.ecom.mis.ejb.domain.workcalendar.voc.VocServiceStream;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.ecom.mis.ejb.domain.worker.voc.VocWorkFunction;

@Stateless
@Remote(IReportsService.class)
@SecurityDomain("other")
public class ReportsServiceBean implements IReportsService {
	public String getTitle(String aGroupBy) {
		String title = "" ;
		if (aGroupBy.equals("2")) {
			title = "ЛПУ" ;
		} else if (aGroupBy.equals("3")) {
			title = "Врач" ;
		} else if (aGroupBy.equals("4")) {
			title = "Специальность" ;
		} else if (aGroupBy.equals("5")) {
			title = "Поток обслуживания" ;
		} else if (aGroupBy.equals("6")) {
			title = "Место обслуживания" ;
		} else {
			title = "Дата" ;
		}
		return title ;
	}
	public String getFilter(Boolean aIsTicket, Long aSpecialist
			, Long aWorkFunction, Long aLpu, Long aServiceStream, Long aWorkPlaceType) {
		StringBuilder filter = new StringBuilder() ;
		String username = theContext.getCallerPrincipal().toString() ;
		boolean isViewAll = false ;
		if (aIsTicket) {
			isViewAll=theContext.isCallerInRole("/Policy/Poly/Ticket/ShowInfoAllUsers") ;
		} else {
			isViewAll=theContext.isCallerInRole("/Policy/Mis/MedCase/Visit/ViewAll") ;
		}
		if (!isViewAll) {
			List<Object[]>listWQR = theManager.createNativeQuery("select w.id,wf.id from Worker w left join WorkFunction wf on wf.worker_id=w.id left join secuser su on su.id=wf.secuser_id where su.login='"+username+"'").setMaxResults(1).getResultList() ;
			//List<Object[]>listWQR = theManager.createNativeQuery("select w.person_id,wf.id from Worker w left join WorkFunction wf on wf.worker_id=w.id left join secuser su on su.id=wf.secuser_id where su.login='"+username+"'").setMaxResults(1).getResultList() ;
			if (listWQR.isEmpty()) {
				filter.append(" and w.id='0'") ;
			} else {
				filter.append(" and w.id='").append(listWQR.get(0)[0]).append("'") ;
			}
			//if (listWQR.isEmpty()) {
			//	filter.append(" and w.person_id='0'") ;
			//} else {
			//	filter.append(" and w.person_id='").append(listWQR.get(0)[0]).append("'") ;
			//}
		}
		if (aSpecialist!=null&&aSpecialist>Long.valueOf(0)){
			if (aIsTicket) {
				filter.append(" and t.workFunction_id="+aSpecialist) ;
			} else {
				filter.append(" and t.workFunctionExecute_id="+aSpecialist) ;
			}
		}
		if (aWorkFunction!=null&&aWorkFunction>Long.valueOf(0)){
				filter.append(" and wf.workFunction_id="+aWorkFunction) ;
			//aRequest.setAttribute("workFunctionInfo", service.getVocWorkFunctionByIdInfo(form.getWorkFunction())) ;
		}
		if (aLpu!=null&&aLpu>Long.valueOf(0)){
			filter.append(" and w.lpu_id="+aLpu) ;
			//aRequest.setAttribute("lpuInfo", service.getWorkingLpuInfo(aLpu)) ;
		}
		if (aServiceStream!=null&&aServiceStream>Long.valueOf(0)){
			if (aIsTicket) {
				filter.append(" and t.vocPaymentType_id="+aServiceStream) ;
			} else  {
				filter.append(" and t.serviceStream_id="+aServiceStream) ;
			}
			//aRequest.setAttribute("serviceStreamInfo", service.getVocServiceStreamByIdInfo(form.getServiceStream())) ;
		}
		if (aWorkPlaceType!=null&&aWorkPlaceType>Long.valueOf(0)){
			if (aIsTicket) {
				filter.append(" and t.vocServicePlace_id="+aWorkPlaceType) ;
			} else  {
				filter.append(" and t.workPlaceType_id="+aWorkPlaceType) ;
			}
		}
		return filter.toString() ;
	}
	public String getFilterId(Boolean aIsTicket, Long aSpecialist
			, Long aWorkFunction, Long aLpu, Long aServiceStream, Long aWorkPlaceType) {
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
			//if (aIsTicket) {
			filter.append("||").append(aServiceStream) ;
			//} else  {
			//filter.append("||min(t.serviceStream_id)") ;
			//}
		}
		filter.append("||':'") ;
		if (aWorkPlaceType!=null&& aWorkPlaceType>Long.valueOf(0)){
			//if (aIsTicket) {
				filter.append("||").append(aWorkPlaceType) ;
			//} else  {
				//filter.append("||min(t.serviceStream_id)") ;
			//}
		}
		return filter.toString() ;
	}
	public String getFilterInfo(EntityManager aManager, boolean aIsTicket, Long aSpecialist
			, Long aWorkFunction, Long aLpu, Long aServiceStream, Long aWorkPlaceType) {
		StringBuilder filter = new StringBuilder() ;
		
		if (aSpecialist>Long.valueOf(0)){
			WorkFunction wf = aManager.find(WorkFunction.class,aSpecialist) ;
			filter.append(" Специалист: ").append(wf!=null?wf.getWorkFunctionInfo():"") ;
		}
		if (aWorkFunction>Long.valueOf(0)){
			VocWorkFunction vwf = aManager.find(VocWorkFunction.class, aWorkFunction) ;
			filter.append(" Должность: ").append(vwf!=null?vwf.getName():"") ;
		}
		if (aLpu>Long.valueOf(0)){
			MisLpu lpu = aManager.find(MisLpu.class, aLpu) ;
			filter.append(" Подразделение: ").append(lpu!=null?lpu.getName():"") ;
		}
		if (aServiceStream>Long.valueOf(0)){
			VocServiceStream vss = aManager.find(VocServiceStream.class, aServiceStream) ;
			filter.append(" Поток обслуживания: ").append(vss.getName()) ;
		}
		if (aWorkPlaceType>Long.valueOf(0)){
			VocWorkPlaceType vwpt = aManager.find(VocWorkPlaceType.class, aWorkPlaceType) ;
			filter.append(" Место обслуживания: ").append(vwpt.getName()) ;
		}
		return filter.toString() ;
	}

	public String getFilterInfo(boolean aIsTicket, Long aSpecialist
			, Long aWorkFunction, Long aLpu, Long aServiceStream, Long aWorkPlaceType) {
		return getFilterInfo(theManager, aIsTicket, aSpecialist, aWorkFunction, aLpu, aServiceStream,aWorkPlaceType) ;
	}
	public String getTextQueryBegin(boolean aIsTicket, String aGroupBy,String aStartDate, String aFinishDate
			, Long aSpecialist, Long aWorkFunction, Long aLpu, Long aServiceStream, Long aWorkPlaceType) {
		StringBuilder sql = new StringBuilder() ;
		String id = "" ;
		String name = "" ;
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
			if (aIsTicket) {
				id = "t.vocPaymentType_id" ;
			} else {
				id = "t.serviceStream_id" ;
			}
			name = "vss.name" ;
		} else if (aGroupBy.equals("6")) {
			//vocWorkFunction
			if (aIsTicket) {
				id = "t.vocServicePlace_id" ;
			} else {
				id = "t.workPlaceType_id" ;
			}
			name = "vwpt.name" ;
		} else{
			//date
			if (aIsTicket) {
				id = "to_char(t.date,'dd.mm.yyyy')" ;
				name = "to_char(t.date,'dd.mm.yyyy')" ;
			} else {
				id = "to_char(t.dateStart,'dd.mm.yyyy')" ;
				name = "to_char(t.dateStart,'dd.mm.yyyy')" ;
			}
		}
		
		sql.append("SELECT '").append(aStartDate).append(":")
		.append(aFinishDate).append(":'||").append(id).append("||':")
		.append(aGroupBy).append("'").append(getFilterId(aIsTicket, aSpecialist, aWorkFunction, aLpu, aServiceStream, aWorkPlaceType)).append(" as id") ;
		sql.append(",").append(name).append(" as tfield") ;
		
		return sql.toString() ;
	}
	public String getTextQueryEnd(boolean aIsTicket, String aGroupBy,String aStartDate, String aFinishDate
			, Long aSpecialist, Long aWorkFunction, Long aLpu, Long aServiceStream, Long aWorkPlaceType) {
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
			if (aIsTicket) {
				group = "t.vocPaymentType_id,vss.name" ;
			} else {
				group= "t.serviceStream_id,vss.name" ;
			}
			order = "vss.name" ;
		} else if (aGroupBy.equals("6")) {
			//vocWorkFunction
			if (aIsTicket) {
				group = "t.vocServicePlace_id,vwpt.name" ;
			} else {
				group= "t.workPlaceType_id,vwpt.name" ;
			}
			order = "vwpt.name" ;
		} else{
			//date
			if (aIsTicket) {
				group = "t.date" ;
				order = "t.date" ;
			} else {
				group = "t.dateStart" ;
				order = "t.dateStart" ;
			}
		}
		if (aIsTicket) {
			sql.append(" FROM TICKET t ") ;
			sql.append(" LEFT JOIN Medcard m on m.id=t.medcard_id") ;
			sql.append(" LEFT JOIN Patient p ON p.id=m.person_id") ;
		} else {
			sql.append(" FROM MedCase t ") ;
			sql.append(" LEFT JOIN Patient p ON p.id=t.patient_id") ;
		}
		sql.append(" LEFT JOIN Address2 ad1 on ad1.addressId=p.address_addressId") ;
		sql.append(" LEFT JOIN Address2 ad2 on ad2.addressId=ad1.parent_addressId ") ;
		if (aIsTicket) {
			sql.append(" LEFT JOIN VocReason vr on vr.id=t.vocReason_id") ;
			sql.append(" LEFT JOIN vocWorkPlaceType vwpt on vwpt.id=t.vocServicePlace_id") ;
			sql.append(" LEFT JOIN VocServiceStream vss on vss.id=t.vocPaymentType_id") ;
			sql.append(" LEFT JOIN WorkFunction wf on wf.id=t.workFunction_id") ;
			
		} else {
			sql.append(" LEFT JOIN VocReason vr on vr.id=t.visitReason_id") ;
			sql.append(" LEFT JOIN vocWorkPlaceType vwpt on vwpt.id=t.workPlaceType_id") ;
			sql.append(" LEFT JOIN VocServiceStream vss on vss.id=t.serviceStream_id") ;
			sql.append(" LEFT JOIN WorkFunction wf on wf.id=t.workFunctionExecute_id") ;
		}
		sql.append(" LEFT JOIN VocWorkFunction vwf on vwf.id=wf.workFunction_id") ;
		sql.append(" LEFT JOIN Worker w on w.id=wf.worker_id") ;
		sql.append(" LEFT JOIN Patient wp on wp.id=w.person_id") ;
		sql.append(" LEFT JOIN MisLpu lpu on lpu.id=w.lpu_id") ;
		sql.append(" WHERE ") ;
		if (!aIsTicket) {
			sql.append(" t.dtype='Visit' and t.dateStart") ;
		} else {
			sql.append(" t.date");
		}
		sql.append(" BETWEEN TO_DATE('").append(aStartDate).append("','dd.mm.yyyy') and TO_DATE('").append(aFinishDate).append("','dd.mm.yyyy')");
		if (aIsTicket) {
			sql.append(" and t.status='2'") ;
		} else {
			sql.append(" and (t.noActuality is null or t.noActuality='0')") ;
		}
		sql.append(" ") ;
		sql.append(getFilter(aIsTicket, aSpecialist, aWorkFunction, aLpu, aServiceStream,aWorkPlaceType)) ;
		sql.append(" GROUP BY ").append(group).append(" ORDER BY ").append(order).append("");
		return sql.toString() ;
	}
	
	
	public String getFilterInfoByOrder(Long aSpecialist
			, Long aWorkFunction, Long aLpu
			, Long aServiceStream, Long aWorkPlaceType
			, Long aOrderLpu, Long aOrderWF) {
		StringBuilder filter = new StringBuilder() ;
		
		if (aSpecialist!=null && aSpecialist>Long.valueOf(0)){
			WorkFunction wf = theManager.find(WorkFunction.class,aSpecialist) ;
			filter.append(" Специалист: ").append(wf!=null?wf.getWorkFunctionInfo():"") ;
		}
		if (aWorkFunction!=null && aWorkFunction>Long.valueOf(0)){
			VocWorkFunction vwf = theManager.find(VocWorkFunction.class, aWorkFunction) ;
			filter.append(" Должность: ").append(vwf!=null?vwf.getName():"") ;
		}
		if (aLpu!=null && aLpu>Long.valueOf(0)){
			MisLpu lpu = theManager.find(MisLpu.class, aLpu) ;
			filter.append(" Подразделение: ").append(lpu!=null?lpu.getName():"") ;
		}
		if (aServiceStream!=null && aServiceStream>Long.valueOf(0)){
			VocServiceStream vss = theManager.find(VocServiceStream.class, aServiceStream) ;
			filter.append(" Поток обслуживания: ").append(vss.getName()) ;
		}
		if (aWorkPlaceType!=null && aWorkPlaceType>Long.valueOf(0)){
			VocWorkPlaceType vwpt = theManager.find(VocWorkPlaceType.class, aWorkPlaceType) ;
			filter.append(" Место обслуживания: ").append(vwpt.getName()) ;
		}
		if (aOrderLpu!=null && aOrderLpu>Long.valueOf(0)){
			MisLpu vwpt = theManager.find(MisLpu.class, aOrderLpu) ;
			filter.append(" Внешний направитель: ").append(vwpt.getName()) ;
		}
		if (aOrderWF!=null && aOrderWF>Long.valueOf(0)){
			WorkFunction owf = theManager.find(WorkFunction.class, aOrderWF) ;
			filter.append(" Направитель: ").append(owf.getWorkFunctionInfo()) ;
		}
		return filter.toString() ;
	}
	public void setManager(EntityManager aManager) {
		theManager=aManager ;
	}
	public void setContext(SessionContext aContext) {
		theContext=aContext ;
	}
	@PersistenceContext EntityManager theManager ;
	@Resource SessionContext theContext ;
}
