package ru.ecom.mis.ejb.service.worker;

import java.sql.Date;
import java.sql.Time;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.EJB;
import javax.annotation.Resource;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ru.ecom.ejb.services.entityform.ILocalEntityFormService;
import ru.ecom.ejb.services.util.ConvertSql;
import ru.ecom.jaas.ejb.domain.SecUser;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.ecom.mis.ejb.domain.workcalendar.voc.VocServiceStream;
import ru.ecom.mis.ejb.domain.worker.PersonalWorkFunction;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.ecom.mis.ejb.domain.worker.Worker;
import ru.ecom.mis.ejb.domain.worker.voc.VocWorkFunction;
import ru.nuzmsh.util.format.DateFormat;

/**
 *
 */
@Stateless
@Local(IWorkerService.class)
@Remote(IWorkerService.class)
public class WorkerServiceBean implements IWorkerService{
	public String getWorkFunctions(Long aWorkFunction){
		StringBuilder sql =new StringBuilder().append("select wf.id from WorkFunction wf left join Worker w on w.id=wf.worker_id left join WorkFunction wf1 on wf1.worker_id=wf.worker_id where wf1.id='").append(aWorkFunction).append("'") ;
		//System.out.println(sql) ;
		List<Object> list = theManager.createNativeQuery(sql.toString()).getResultList() ;
		StringBuilder res = new StringBuilder() ;
		for (Object id :list) {
			res.append(",").append(id) ;
		}
		return list.size()>0 ? res.substring(1) :String.valueOf(aWorkFunction) ;	
	}
    public void onUpdate(Worker aWorker) {
       /* theManager.createQuery("update StateList set freeRate=fullRate")
                //.setParameter("post", aWorker.getPost().getId())
                .executeUpdate() ;     */
    }

	public Long getWorkingLpu() {
		String username = theContext.getCallerPrincipal().toString() ;
		List<PersonalWorkFunction> list= theManager.createQuery("from WorkFunction where secUser.login = :login")
			.setParameter("login", username) 
			.getResultList() ;
		StringBuffer err = new StringBuffer() ;
		if(list.size()==0) throw
			new IllegalArgumentException(
				err.append("Обратитесь к администратору системы. Ваш профиль настроен неправильно. Нет соответсвия между рабочей функцией и пользователем (WorkFunction и SecUser)")
				.toString()
				);	
		MisLpu lpu =list.get(0).getLpuRegister() ;
		
		if (lpu!=null) {
				System.out.println("lpu="+lpu.getId()) ;
				return lpu.getId() ;
		}
		System.out.println("Not found") ;
		return Long.valueOf(0) ;
	}
	
	public String getWorkFunctionInfo(Long aWorkFunction) {
		WorkFunction worker = theManager.find(WorkFunction.class, aWorkFunction) ;
		return worker.getWorkFunctionInfo() ;

	}
	/**
	 * Получение ID SecUser
	 */
	@SuppressWarnings({ "unchecked"})
	public Long getSecUser() {
		String username = theContext.getCallerPrincipal().toString() ;
		List<SecUser> list = theManager.createQuery("from SecUser where login = :login")
			.setParameter("login", username) 
			.getResultList() ;
		StringBuffer err = new StringBuffer() ;
		if(list.size()==0) throw 
			new IllegalArgumentException(
					err.append("Обратитесь к администратору системы. Ваш профиль настроен неправильно. Нет соответсвия между ")
					.append(username)
					.append(" и SecUser").toString());	
		if(list.size()>1)  throw 
			new IllegalArgumentException(
					err.append("Обратитесь к администратору системы. Ваш профиль настроен неправильно. Больше одного раза встречается имя ")
					.append(username)
					.append(" в SecUser")
					.toString()) ;
		return list.iterator().next().getId();
	}
	@SuppressWarnings({ "unchecked" })
	public Long getWorkFunction()  {
		String username = theContext.getCallerPrincipal().toString() ;
		List<WorkFunction> list= theManager.createQuery("from WorkFunction where secUser.login = :login")
			.setParameter("login", username) 
			.getResultList() ;
		StringBuffer err = new StringBuffer() ;
		if(list.size()==0) throw
			new IllegalArgumentException(
				err.append("Обратитесь к администратору системы. Ваш профиль настроен неправильно. Нет соответсвия между WorkFunction и SecUser")
				.toString()
				);	
		if(list.size()>1)  throw 
			new IllegalArgumentException(
					err.append("Обратитесь к администратору системы. Ваш профиль настроен неправильно. Больше одного раза встречается имя ")
					.append(username)
					.append(" в SecUser")
					.toString()) ;
	return list.get(0).getId() ;
	}
	public String getUsernameByWorkFunction(Long aWorkFunction) {
		List<Object[]> list = theManager.createNativeQuery("select count(*), su.login from WorkFunction wf left join SecUser su on wf.secUser_id=su.id where wf.id='"+aWorkFunction+"' group by su.login").getResultList() ;
		return list.size()>0 ? String.valueOf(list.get(0)[1]) :"" ;
	}
	public static WorkFunction getWorkFunction(SessionContext aContext, EntityManager aManager)  {
		String username = aContext.getCallerPrincipal().toString() ;
		List<WorkFunction> list=aManager.createQuery("from WorkFunction where secUser.login = :login")
			.setParameter("login", username) 
			.getResultList() ;
		StringBuffer err = new StringBuffer() ;
		if(list.size()==0) throw
			new IllegalArgumentException(
				err.append("Обратитесь к администратору системы. Ваш профиль настроен неправильно. Нет соответсвия между WorkFunction и SecUser")
				.toString()
				);	
		if(list.size()>1)  throw 
			new IllegalArgumentException(
					err.append("Обратитесь к администратору системы. Ваш профиль настроен неправильно. Больше одного раза встречается имя ")
					.append(username)
					.append(" в SecUser")
					.toString()) ;
	return list.get(0) ;
	}

	public Long getWorkFunction(Long aSecUser)  {
		String username = theContext.getCallerPrincipal().toString() ;
		List<WorkFunction> list= theManager.createQuery("from WorkFunction where secUser_id = :secUser")
			.setParameter("secUser", aSecUser) 
			.getResultList() ;
		StringBuffer err = new StringBuffer() ;
		if(list.size()==0) throw
			new IllegalArgumentException(
				err.append("Обратитесь к администратору системы. Ваш профиль настроен неправильно. Нет соответсвия между рабочей функцией и пользователем (WorkFunction и SecUser)")
				.toString()
				);	
		return list.get(0).getId() ;
	}
	
	public String getWorkFunctionInfoById(Long aWorkFunctionId) {
		StringBuilder sql = new StringBuilder() ;
		
		sql.append("select pat.lastname || ' ' ||pat.firstname || ' ' ||pat.middlename as patinfo,wf.groupname")
			.append(" from WorkFunction wf ")
			//.append(" left join WorkCalendar wc on wf.workcalendar_id=wc.id")
			//.append(" left join WorkCalendarDay wcd on wc.id=wcd.workcalendar_id")
			//.append(" left join WorkCalendarTime wct on wcd.id=wct.workcalendarday_id")
			.append(" left join Worker w on w.id=wf.worker_id")
			.append(" left join Patient pat on pat.id = w.person_id")
			.append(" where wf.id=:WFid")
			//.append(" group by wf.id,wcd.calendardate order by wf.id,wcd.calendardate") 
		;

		List<Object[]> list = theManager.createNativeQuery(sql.toString()).setParameter("WFid", aWorkFunctionId).getResultList() ;
		sql = new StringBuilder() ;
		if (list.size()>0) {
			Object[] obj = list.get(0);
			if (obj[1]!=null) {
				sql.append(obj[1]) ;
			} else {
				sql.append(obj[0]==null?"":obj[0]) ;
			}
		}
		return sql.toString() ;
	}

	@EJB ILocalEntityFormService theEntityFormService ;
    @PersistenceContext EntityManager theManager ;
    @Resource SessionContext theContext ;
	
    public String getWorkingLpuInfo(Long aLpu) {
		MisLpu lpu = theManager.find(MisLpu.class, aLpu) ;
		return lpu!=null?lpu.getFullname():"" ;
	}
    
    public String getVocWorkFunctionByIdInfo(Long aId) {
    	VocWorkFunction vwf = theManager.find(VocWorkFunction.class, aId) ;
    	return vwf!=null?vwf.getName():"" ;
    }
    public String getVocServiceStreamByIdInfo(Long aId) {
    	VocServiceStream vss = theManager.find(VocServiceStream.class, aId) ;
    	return vss!=null?vss.getName():"" ;
    }
	public List<TableTimeBySpecialists> getTableByDayAndFunction(Date aDateStart, Date aDateFinish, Long aVocWorkFunctionId) {
		StringBuilder sql = new StringBuilder() ;
		
		sql.append("select wf.id as wfid")
			.append(",wcd.id as wcdid")
			.append(",wcd.calendardate as wcdcalendardate")
			.append(",MIN(wct.timeFrom),MAX(wct.timeFrom)")
		//	.append(", pat.lastname || ' ' ||pat.firstname || ' ' ||pat.middlename as patinfo,wf.groupname,wf.worker_id")
			.append(" from WorkFunction wf ")
			.append(" left join WorkCalendar wc on wf.workcalendar_id=wc.id")
			.append(" left join WorkCalendarDay wcd on wc.id=wcd.workcalendar_id")
			.append(" left join WorkCalendarTime wct on wcd.id=wct.workcalendarday_id")
		//	.append(" left join Worker w on w.id=wf.worker_id")
		//	.append(" left join Patient pat on pat.id = w.person_id")
			.append(" where wf.workfunction_id=:WFid and (wf.group_id is null) and (wf.archival is null or cast(wf.archival as integer)=0) and wc.workfunction_id is not null") 
			.append(" and wcd.calendarDate between :dateStart and :dateFinish")
			.append(" and wct.medcase_id is null and wct.prepatient_id is null and (wct.prepatientinfo is null or wct.prepatientinfo='')")
			.append(" group by wf.id,wcd.id,wcd.calendardate order by wf.id,wcd.calendardate") ;
		List<Object[]> list = theManager.createNativeQuery(sql.toString())
				.setParameter("dateStart", aDateStart)
				.setParameter("dateFinish", aDateFinish)
				.setParameter("WFid", aVocWorkFunctionId)
				.getResultList() ;
		LinkedList<TableTimeBySpecialists> ret = new LinkedList<TableTimeBySpecialists>() ;
		long i = 0 ;
		for (Object[] row : list) {
			TableTimeBySpecialists result = new TableTimeBySpecialists() ;
			System.out.println("Specialist") ;
			//BigInteger idbi = (BigInteger)row[0] ;
			//String idst = Long.valueOf(row[0]) ;
			result.setSpecialistId(ConvertSql.parseLong(row[0])) ;
			System.out.println("Calendar day") ;
			result.setCalendarDayId(ConvertSql.parseLong(row[1]));
			//String specialist ;
			//System.out.println("Specialist info") ;
			/*
			if (row[5]==null) {
				result.setSpecialist((String) row[6]) ;
			} else {
				result.setSpecialist((String) row[5]) ;
			}*/
			Time timeMin = (Time)row[3];
			Time timeMax = (Time)row[4];
			Date day = (Date)row[2];
			//System.out.println("Min date") ;
			result.setTimeMax(DateFormat.formatToTime(timeMax)) ;
			//System.out.println("Max date") ;
			result.setTimeMin(DateFormat.formatToTime(timeMin)) ;
			//System.out.println("Day string") ;
			result.setDateString(DateFormat.formatToDate(day)) ;
			//System.out.println("Day id") ;
			result.setDate(day) ;
			//System.out.println("Sn") ;
			result.setSn(Long.valueOf(++i)) ;
			//System.out.println("Min date") ;
			ret.add(result) ;
		}
		return ret ;
	}

	public List<TableSpetialistByDay> getTableSpetialistByDay(Date aDate, Long aWorkCalendarDay) {
		StringBuilder sql = new StringBuilder();
		sql.append("select id,timeFrom from WorkCalendarTime where workCalendarDay_id=:WCDid and medCase_id is null and prepatient_id is null and (prepatientinfo is null or prepatientinfo='')") ;
		List<Object[]> list = theManager.createNativeQuery(sql.toString())
				.setParameter("WCDid", aWorkCalendarDay)
				.getResultList() ;
		LinkedList<TableSpetialistByDay> ret = new LinkedList<TableSpetialistByDay>() ;
		long i =0 ;
		for (Object[] row: list ) {
			TableSpetialistByDay result = new TableSpetialistByDay() ;
			Time time = (Time)row[1] ;
			result.setId((Long)row[0]) ;
			result.setTime(time) ;
			result.setTimeString(DateFormat.formatToTime(time)) ;
			result.setSn(++i) ;
		}
		return ret;
		
	}
	public String getCalendarTimeId(Long aCalendarDay, Time aCalendarTime, Long aMinIs) {
		StringBuilder sql = new StringBuilder() ;
		sql.append("select id,timeFrom from WorkCalendarTime where workCalendarDay_id=:WCDid and medCase_id is null  and prepatient_id is null and (prepatientinfo is null or prepatientinfo='')") ;
		System.out.println("minIs="+aMinIs) ;
		if (aMinIs!=null && aMinIs.equals(Long.valueOf(1))) {
			sql.append(" order by timeFrom asc") ;
		} else {
			sql.append(" order by timeFrom desc") ;
		}
		List<Object[]> list = theManager.createNativeQuery(sql.toString())
			.setParameter("WCDid", aCalendarDay)
			.setMaxResults(1)
			//.setParameter("time", aCalendarTime) 
			.getResultList() ;
		if (list.size()>0) {
			Object[] row = list.get(0) ;
			Long id = ConvertSql.parseLong(row[0]) ;
			Time time = (Time)row[1] ;
			return new StringBuilder().append(id).append("#").append(time).toString() ;
		}
		return null ;
	}
	
	
	
	
	/**
	 * Получить дату по умолчанию
	 */
	public String getDayBySpec(Long aWorkFunction) {
		StringBuilder ret = new StringBuilder() ;
		StringBuilder sqlmax = new StringBuilder() ;
		StringBuilder sqlmin = new StringBuilder() ;
		sqlmin.append("select to_char(wcd.calendarDate,'dd.MM.yyyy'),wcd.id from WorkCalendarDay as wcd")
			.append(" inner join WorkCalendar as wc on wc.id = wcd.workCalendar_id") 
			.append(" where  wc.workfunction_id=:workFuncId and wcd.calendarDate >= CURRENT_DATE") 
			.append(" group by wc.id,wcd.id,wcd.calendarDate")
			.append(" order by wcd.calendarDate")
			; 
		sqlmax.append(" select to_char(wcd.calendarDate,'dd.MM.yyyy'),wcd.id from WorkCalendarDay as wcd")
			.append(" inner join WorkCalendar as wc on wc.id = wcd.workCalendar_id") 
			.append(" where  wc.workfunction_id=:workFuncId and wcd.calendarDate <= CURRENT_DATE") 
			.append(" group by wc.id,wcd.id,wcd.calendarDate")
			.append(" order by wcd.calendarDate desc")
			;
		List<Object[]> rownext = theManager.createNativeQuery(sqlmin.toString())
						.setParameter("workFuncId", aWorkFunction) 
						.setMaxResults(1)
						.getResultList() ;
		if (rownext.size()==0 || rownext.get(0)[1]==null) {
			rownext = theManager.createNativeQuery(sqlmax.toString())
				.setParameter("workFuncId", aWorkFunction) 
				.setMaxResults(1)
				.getResultList() ;
			if (rownext.size()==0 || rownext.get(0)[1]==null) {
				return null ;
			}
		} 
		ret.append(rownext.get(0)[0]).append("#").append(rownext.get(0)[1]) ;
		System.out.println("default date="+ret) ;
		return ret.toString() ;
	}
}
