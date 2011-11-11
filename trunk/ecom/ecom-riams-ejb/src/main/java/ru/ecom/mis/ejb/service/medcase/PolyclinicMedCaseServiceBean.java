package ru.ecom.mis.ejb.service.medcase;

import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.EJB;
import javax.annotation.Resource;
import javax.ejb.Remote;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ru.ecom.ejb.services.entityform.ILocalEntityFormService;
import ru.ecom.jaas.ejb.domain.SecUser;
import ru.ecom.mis.ejb.domain.workcalendar.WorkCalendar;
import ru.ecom.mis.ejb.domain.workcalendar.WorkCalendarDay;
import ru.ecom.mis.ejb.domain.worker.PersonalWorkFunction;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.nuzmsh.util.format.DateFormat;

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
	public String getFioBySpec() {
		String username = theContext.getCallerPrincipal().toString() ;
		List<PersonalWorkFunction> list= theManager.createQuery("from WorkFunction where secUser.login = :login")
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
		return list.get(0).getWorker()!=null?list.get(0).getWorker().getDoctorInfo():"";
	}
	
	@EJB ILocalEntityFormService theEntityFormService ;
    @PersistenceContext EntityManager theManager ;
    @Resource
	SessionContext theContext ;
	public Long getWorkCalendar(Long aWorkFunction) {
		
		List<Object[]> list = theManager.createNativeQuery("select wc.id,case when wf.group_id is null then wc.id else (select wc1.id from WorkCalendar as wc1 where wc1.workFunction_id = wf.group_id ) end from WorkCalendar as wc"
					+	" left join WorkFunction as wf on wf.id=wc.workFunction_id"
					+" where wc.workFunction_id = :funct")
			.setParameter("funct", aWorkFunction)
			.getResultList() ;
		if(list.size()==0) {
			StringBuffer err = new StringBuffer() ;
			throw
				new IllegalArgumentException(
						err.append("Обратитесь к администратору системы. Ваш профиль настроен неправильно. Нет соответсвия между рабочей функцией и календарем")
						.toString()
					);	
		}
		StringBuilder ret = new StringBuilder() ;
		ret.append(list.get(0)[1]) ;
		
		System.out.println("workfunction = "+aWorkFunction) ;
		System.out.println("workcalendar = "+ret) ;
		return Long.valueOf(ret.toString());
	}

	public String getWorkCalendarDay(Long aWorkCalendar,Long aWorkFunction, String aCalendarDate) throws ParseException {
			Date date = DateFormat.parseSqlDate(aCalendarDate);
			Long workCalen = getWorkCalendar(aWorkFunction) ;
			System.out.println("workCalend="+aWorkCalendar);
			
			System.out.println("workCalend1="+workCalen);
				List<WorkCalendarDay> list = theManager.createQuery("from WorkCalendarDay where workCalendar_id = :workCalend and calendarDate = :date")
					.setParameter("workCalend", workCalen)
					.setParameter("date",date)
					.getResultList() ;
				if(list.size()==0) {
					return "0#0#0" ;
				}
				Long workCalendarDayId = list.get(0).getId() ;
				Long workFunc = list.get(0).getWorkFunction().getId() ;
				Object executed = theManager.createNativeQuery("select count(*)"
                     +  " from medcase"
                     + " where workfunctionExecute_id=:workFunction"
                     + " and dateStart=:date")
                   //  .setParameter("workFunction",aWorkFunction)
                   .setParameter("workFunction",workFunc)
                   .setParameter("date", date)
                   .getSingleResult();
				Object planned = theManager.createNativeQuery("select count(id) from medcase"
					+" where workfunctionplan_id =:workFunction"
                   +" and datePlan_id=:workCalendarDay"
						)
//						.setParameter("workFunction",aWorkFunction)
                   .setParameter("workFunction",workFunc)
                   .setParameter("workCalendarDay", workCalendarDayId)
                   .getSingleResult();
				System.out.println("day="+workCalendarDayId) ;
				System.out.println("exec="+executed) ;
				System.out.println("plan="+planned) ;
				return new StringBuilder().append(workCalendarDayId).append("#")
						.append(executed).append("#").append(planned).toString();
				
	}
}
