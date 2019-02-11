package ru.ecom.mis.ejb.service.medcase;

import ru.ecom.ejb.services.entityform.ILocalEntityFormService;
import ru.ecom.jaas.ejb.domain.SecUser;
import ru.ecom.mis.ejb.domain.workcalendar.WorkCalendarDay;
import ru.ecom.mis.ejb.domain.worker.PersonalWorkFunction;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.nuzmsh.util.format.DateFormat;

import javax.annotation.EJB;
import javax.annotation.Resource;
import javax.ejb.Remote;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Date;
import java.text.ParseException;
import java.util.List;

@Stateless
@Remote(IPolyclinicMedCaseService.class)
public class PolyclinicMedCaseServiceBean implements IPolyclinicMedCaseService {
	
	public String getInfoDay(String aDate) {

		return "null";
	}

	//Получение ID SecUser
	public Long getSecUser() {
		String username = theContext.getCallerPrincipal().toString() ;
		List<SecUser> list = theManager.createQuery("from SecUser where login = :login")
			.setParameter("login", username) 
			.getResultList() ;
		if(list.isEmpty()) throw
			new IllegalArgumentException("Обратитесь к администратору системы. Ваш профиль настроен неправильно. Нет соответсвия между "+username+" и SecUser");
		if(list.size()>1)  throw 
			new IllegalArgumentException("Обратитесь к администратору системы. Ваш профиль настроен неправильно. Больше одного раза встречается имя "+username+" в SecUser") ;
		return list.iterator().next().getId();
	}
	public Long getWorkFunction()  {
		String username = theContext.getCallerPrincipal().toString() ;
		List<WorkFunction> list= theManager.createQuery("from WorkFunction where secUser.login = :login")
			.setParameter("login", username) 
			.getResultList() ;
		if(list.isEmpty()) throw
			new IllegalArgumentException("Обратитесь к администратору системы. Ваш профиль настроен неправильно. Нет соответсвия между WorkFunction и SecUser");
		if(list.size()>1)  throw 
			new IllegalArgumentException("Обратитесь к администратору системы. Ваш профиль настроен неправильно. Больше одного раза встречается имя "+username+" в SecUser") ;
	return list.get(0).getId() ;
	}

	public Long getWorkFunction(Long aSecUser)  {
	//	String username = theContext.getCallerPrincipal().toString() ;
		List<WorkFunction> list= theManager.createQuery("from WorkFunction where secUser_id = :secUser")
			.setParameter("secUser", aSecUser) 
			.getResultList() ;
		if(list.isEmpty()) throw new IllegalArgumentException("Обратитесь к администратору системы. Ваш профиль настроен неправильно. Нет соответсвия между рабочей функцией и пользователем (WorkFunction и SecUser)");
		return list.get(0).getId() ;
	}
	public String getFioBySpec() {
		String username = theContext.getCallerPrincipal().toString() ;
		List<PersonalWorkFunction> list= theManager.createQuery("from WorkFunction where secUser.login = :login")
			.setParameter("login", username) 
			.getResultList() ;
		if(list.isEmpty()) throw
			new IllegalArgumentException("Обратитесь к администратору системы. Ваш профиль настроен неправильно. Нет соответсвия между WorkFunction и SecUser");
		if(list.size()>1)  throw 
			new IllegalArgumentException("Обратитесь к администратору системы. Ваш профиль настроен неправильно. Больше одного раза встречается имя "+username+" в SecUser") ;
		return list.get(0).getWorker()!=null ? list.get(0).getWorker().getDoctorInfo() : "";
	}
	
	@EJB ILocalEntityFormService theEntityFormService ;
    @PersistenceContext EntityManager theManager ;
    @Resource
	SessionContext theContext ;
	public Long getWorkCalendar(Long aWorkFunction) {
		if (aWorkFunction>Long.valueOf(0)) {
			List<Object[]> list = theManager.createNativeQuery("select wc.id,case when wf.group_id is null then wc.id else wcg.id end "
					+" from WorkFunction as wf"
					+	" left join WorkCalendar as wc on wf.id=wc.workFunction_id"
					+	" left join WorkCalendar as wcg on wf.group_id=wcg.workFunction_id"
					+" where wc.workFunction_id = :funct and (wcg.id is not null or wc.id is not null) order by wcg.id,wc.id")
			.setParameter("funct", aWorkFunction)
			.getResultList() ;
			if(list.isEmpty()) {
				throw new IllegalArgumentException("Обратитесь к администратору системы. Ваш профиль настроен неправильно. Нет соответсвия между рабочей функцией и календарем");
			}
			return Long.valueOf(list.get(0)[1].toString());
		} else {
			String username = theContext.getCallerPrincipal().toString() ;
			List<Object[]> list = theManager.createNativeQuery("select wc.id  as wcid,case when wf.group_id is null then wc.id else "
					+" wcg.id end  as wcname from WorkFunction as wf"
					+" left join WorkCalendar as wc on wf.id=wc.workFunction_id"
					+" left join WorkCalendar as wcg on wf.group_id=wcg.workFunction_id"
					+" left join SecUser su on su.id=wf.secUser_id"
					+" where su.login = :username and case when wf.group_id is not null then wcg.id else wc.id end is not null order by wcg.id,wc.id")
			.setParameter("username", username)
			.getResultList() ;
			if(list.isEmpty()) {
				list.clear() ;
				
				list = theManager.createNativeQuery("select wc.id as wcid, case when wf.group_id is not null then wcg.id else wc.id end as wcname"
					+" from WorkFunction wf"
					+" left join Worker w on w.id=wf.worker_id"
					+" left join Worker sw on sw.person_id=w.person_id"
					+" left join WorkFunction as swf on swf.worker_id=sw.id"
					+" left join SecUser su on su.id=swf.secUser_id"
					+" left join WorkCalendar wc on wc.workFunction_id=wf.id"
					+" left join WorkCalendar wcg on wcg.workFunction_id=wf.group_id"
					+" where su.login = :username and case when wf.group_id is not null then wcg.id else wc.id end is not null order by wcg.id,wc.id")
				.setParameter("username", username)
				.getResultList() ;
				if (list.isEmpty() || list.get(0)[1]==null) {
					throw new IllegalArgumentException("Обратитесь к администратору системы. Ваш профиль настроен неправильно. Нет соответсвия между рабочей функцией и календарем");
				}
			}
			return Long.valueOf(list.get(0)[1].toString());
		}
	}

	public String getWorkCalendarDay(Long aWorkCalendar,Long aWorkFunction, String aCalendarDate) throws ParseException {
			Date date = DateFormat.parseSqlDate(aCalendarDate);
			Long workCalen = getWorkCalendar(aWorkFunction) ;
				List<WorkCalendarDay> list = theManager.createQuery("from WorkCalendarDay where workCalendar_id = :workCalend and calendarDate = :date and (isDeleted is null or isDeleted='0')")
					.setParameter("workCalend", workCalen)
					.setParameter("date",date)
					.getResultList() ;
				if(list.isEmpty()) {
					return "0#0#0" ;
				}
				Long workCalendarDayId = list.get(0).getId() ;
				Long workFunc = list.get(0).getWorkFunction().getId() ;
				Object executed = theManager.createNativeQuery("select count(*)"
                     +  " from medcase"
                     + " where workfunctionExecute_id=:workFunction and dtype='Visit'"
                     + " and dateStart=:date")
                   //  .setParameter("workFunction",aWorkFunction)
                   .setParameter("workFunction",workFunc)
                   .setParameter("date", date)
                   .getSingleResult();
				Object planned = theManager.createNativeQuery("select count(id)||' из них оформлены '||count(distinct case when dateStart is not null then id else null end) from medcase"
						+" where workfunctionplan_id =:workFunction"
						+" and datePlan_id=:workCalendarDay"
						)
//						.setParameter("workFunction",aWorkFunction)
						.setParameter("workFunction",workFunc)
						.setParameter("workCalendarDay", workCalendarDayId)
						.getSingleResult();
				Object prerecord = theManager.createNativeQuery("select count(distinct wct.id) from workCalendarTime wct"
					+" left join WorkCalendarDay wcd on wcd.id=wct.workCalendarDay_id"
					+" left join WorkCalendar wc on wc.id=wcd.workCalendar_id"
					+" where wcd.id=:workCalendarDay and wc.workFunction_id =:workFunction"
                   +" and wct.medCase_id is null and (wct.prePatient_id is not null or wct.prePatientInfo is not null and wct.prePatientInfo!='') and (wct.isDeleted is null or wct.isDeleted='0')"
						)
//						.setParameter("workFunction",aWorkFunction)
                   .setParameter("workFunction",workFunc)
                   .setParameter("workCalendarDay", workCalendarDayId)
                   .getSingleResult();
				return workCalendarDayId+"#"+executed+"#"+planned+"#"+prerecord;
				
	}
}
