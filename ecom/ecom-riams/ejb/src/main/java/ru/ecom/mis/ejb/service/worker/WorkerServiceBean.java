package ru.ecom.mis.ejb.service.worker;

import ru.ecom.ejb.services.entityform.ILocalEntityFormService;
import ru.ecom.ejb.services.util.ConvertSql;
import ru.ecom.jaas.ejb.domain.SecUser;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.ecom.mis.ejb.domain.workcalendar.voc.VocServiceStream;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.ecom.mis.ejb.domain.worker.Worker;
import ru.ecom.mis.ejb.domain.worker.voc.VocWorkFunction;
import ru.nuzmsh.util.format.DateFormat;

import javax.annotation.EJB;
import javax.annotation.Resource;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Date;
import java.sql.Time;
import java.util.LinkedList;
import java.util.List;

/**
 *
 */
@Stateless
@Local(IWorkerService.class)
@Remote(IWorkerService.class)
public class WorkerServiceBean implements IWorkerService {

    /*Все рабочие функции по одному работнику (по одному отделению)*/
    public String getWorkFunctions(Long aWorkFunction) {
        String sql = "select wf.id from WorkFunction wf left join Worker w on w.id=wf.worker_id left join WorkFunction wf1 on wf1.worker_id=wf.worker_id where wf1.id=workFunctionId";
        List<Object> list = manager.createNativeQuery(sql).setParameter("workFunctionId", aWorkFunction).getResultList();
        StringBuilder res = new StringBuilder();
        for (Object id : list) {
            res.append(",").append(id);
        }
        return !list.isEmpty() ? res.substring(1) : String.valueOf(aWorkFunction);
    }

    public void onUpdate(Worker aWorker) {
    }

    /*Отделение по рабочей функции*/
    public Long getWorkingLpu() {
        WorkFunction workFunction = getWorkFunction(context, manager);
        MisLpu lpu = workFunction.getLpuRegister();
        return lpu != null ? lpu.getId() : 0L; //TODO allways null
    }

    public String getWorkFunctionInfo(Long aWorkFunction) {
        WorkFunction worker = manager.find(WorkFunction.class, aWorkFunction);
        return worker.getWorkFunctionInfo();

    }

    /**
     * Получение ID SecUser
     */
    @SuppressWarnings({"unchecked"})
    public Long getSecUser() {
        String username = context.getCallerPrincipal().toString();
        List<SecUser> list = manager.createQuery("from SecUser where login = :login")
                .setParameter("login", username)
                .getResultList();
        if (list.isEmpty())
            throw new IllegalArgumentException("Обратитесь к администратору системы. Ваш профиль настроен неправильно. Нет соответсвия между " + username + " и SecUser");
        if (list.size() > 1)
            throw new IllegalArgumentException("Обратитесь к администратору системы. Ваш профиль настроен неправильно. Больше одного раза встречается имя " + username + " в SecUser");
        return list.iterator().next().getId();
    }

    @SuppressWarnings({"unchecked"})
    public Long getWorkFunction() {
        WorkFunction workFunction = getWorkFunction(context, manager);
        return workFunction.getId();
    }

    public String getUsernameByWorkFunction(Long aWorkFunction) {
        List<Object[]> list = manager.createNativeQuery("select count(*), su.login from WorkFunction wf left join SecUser su on wf.secUser_id=su.id where wf.id=:workFunction group by su.login")
                .setParameter("workFunction", aWorkFunction).getResultList();
        return !list.isEmpty() ? String.valueOf(list.get(0)[1]) : "";
    }

    public static WorkFunction getWorkFunction(SessionContext aContext, EntityManager aManager) {
        String username = aContext.getCallerPrincipal().toString();
        List<WorkFunction> list = aManager.createQuery("from WorkFunction where secUser.login = :login")
                .setParameter("login", username)
                .getResultList();
        if (list.isEmpty())
            throw new IllegalArgumentException("Обратитесь к администратору системы. Ваш профиль настроен неправильно. Нет соответсвия между WorkFunction и SecUser");
        if (list.size() > 1)
            throw new IllegalArgumentException("Обратитесь к администратору системы. Ваш профиль настроен неправильно. Больше одного раза встречается имя " + username + " в SecUser");
        return list.get(0);
    }

    public Long getWorkFunction(Long aSecUser) {
        //	String username = context.getCallerPrincipal().toString() ;
        List<WorkFunction> list = manager.createQuery("from WorkFunction where secUser_id = :secUser")
                .setParameter("secUser", aSecUser).getResultList();
        if (list.isEmpty())
            throw new IllegalArgumentException("Обратитесь к администратору системы. Ваш профиль настроен неправильно. Нет соответсвия между рабочей функцией и пользователем (WorkFunction и SecUser)");
        return list.get(0).getId();
    }

    public String getWorkFunctionInfoById(Long aWorkFunctionId) {
        StringBuilder sql = new StringBuilder();

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

        List<Object[]> list = manager.createNativeQuery(sql.toString()).setParameter("WFid", aWorkFunctionId).getResultList();
        sql = new StringBuilder();
        if (!list.isEmpty()) {
            Object[] obj = list.get(0);
            if (obj[1] != null) {
                sql.append(obj[1]);
            } else {
                sql.append(obj[0] == null ? "" : obj[0]);
            }
        }
        return sql.toString();
    }

    @EJB
    ILocalEntityFormService entityFormService;
    @PersistenceContext
    EntityManager manager;
    @Resource
    SessionContext context;

    public String getWorkingLpuInfo(Long aLpu) {
        MisLpu lpu = manager.find(MisLpu.class, aLpu);
        return lpu != null ? lpu.getFullname() : "";
    }

    public String getVocWorkFunctionByIdInfo(Long aId) {
        VocWorkFunction vwf = manager.find(VocWorkFunction.class, aId);
        return vwf != null ? vwf.getName() : "";
    }

    public String getVocServiceStreamByIdInfo(Long aId) {
        VocServiceStream vss = manager.find(VocServiceStream.class, aId);
        return vss != null ? vss.getName() : "";
    }

    public List<TableTimeBySpecialists> getTableByDayAndFunction(Date aDateStart, Date aDateFinish, Long aVocWorkFunctionId) {
        StringBuilder sql = new StringBuilder();

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
                .append(" and wct.medcase_id is null and wct.prepatient_id is null and (wct.prepatientinfo is null or wct.prepatientinfo='') and (wcd.isDeleted is null or wcd.isDeleted='0') and (wct.isDeleted is null or wct.isDeleted='0')")
                .append(" group by wf.id,wcd.id,wcd.calendardate order by wf.id,wcd.calendardate");
        List<Object[]> list = manager.createNativeQuery(sql.toString())
                .setParameter("dateStart", aDateStart)
                .setParameter("dateFinish", aDateFinish)
                .setParameter("WFid", aVocWorkFunctionId)
                .getResultList();
        LinkedList<TableTimeBySpecialists> ret = new LinkedList<>();
        long i = 0;
        for (Object[] row : list) {
            TableTimeBySpecialists result = new TableTimeBySpecialists();
            //BigInteger idbi = (BigInteger)row[0] ;
            //String idst = Long.valueOf(row[0]) ;
            result.setSpecialistId(ConvertSql.parseLong(row[0]));
            result.setCalendarDayId(ConvertSql.parseLong(row[1]));
            //String specialist ;
			/*
			if (row[5]==null) {
				result.setSpecialist((String) row[6]) ;
			} else {
				result.setSpecialist((String) row[5]) ;
			}*/
            Time timeMin = (Time) row[3];
            Time timeMax = (Time) row[4];
            Date day = (Date) row[2];
            result.setTimeMax(DateFormat.formatToTime(timeMax));
            result.setTimeMin(DateFormat.formatToTime(timeMin));
            result.setDateString(DateFormat.formatToDate(day));
            result.setDate(day);
            result.setSn(++i);
            ret.add(result);
        }
        return ret;
    }

    public String getCalendarTimeId(Long aCalendarDay, Time aCalendarTime, Long aMinIs) {
        StringBuilder sql = new StringBuilder();
        sql.append("select id,timeFrom from WorkCalendarTime where workCalendarDay_id=:WCDid and medCase_id is null  and prepatient_id is null and (prepatientinfo is null or prepatientinfo='') and (isDeleted is null or isDeleted='0')");
        if (aMinIs != null && aMinIs.equals(1L)) {
            sql.append(" order by timeFrom asc");
        } else {
            sql.append(" order by timeFrom desc");
        }
        List<Object[]> list = manager.createNativeQuery(sql.toString())
                .setParameter("WCDid", aCalendarDay)
                .setMaxResults(1)
                //.setParameter("time", aCalendarTime)
                .getResultList();
        if (!list.isEmpty()) {
            Object[] row = list.get(0);
            Long id = ConvertSql.parseLong(row[0]);
            Time time = (Time) row[1];
            return id + "#" + time;
        }
        return null;
    }


    /**
     * Получить дату по умолчанию
     */
    public String getDayBySpec(Long aWorkFunction) {
        StringBuilder ret = new StringBuilder();
        //StringBuilder sqlmax = new StringBuilder() ;
        StringBuilder sqlmin = new StringBuilder();
        sqlmin.append("select to_char(wcd.calendarDate,'dd.MM.yyyy'),wcd.id from WorkCalendarDay as wcd")
                .append(" inner join WorkCalendar as wc on wc.id = wcd.workCalendar_id")
                .append(" where  wc.workfunction_id=:workFuncId and wcd.calendarDate >= CURRENT_DATE and (wcd.isDeleted is null or wcd.isDeleted='0')")
                .append(" group by wc.id,wcd.id,wcd.calendarDate")
                .append(" order by wcd.calendarDate")
        ;
		/*sqlmax.append(" select to_char(wcd.calendarDate,'dd.MM.yyyy'),wcd.id from WorkCalendarDay as wcd")
			.append(" inner join WorkCalendar as wc on wc.id = wcd.workCalendar_id") 
			.append(" where  wc.workfunction_id=:workFuncId and wcd.calendarDate <= CURRENT_DATE and (wcd.isDeleted is null or wcd.isDeleted='0')")
			.append(" group by wc.id,wcd.id,wcd.calendarDate")
			.append(" order by wcd.calendarDate desc")
			;*/
        List<Object[]> rownext = manager.createNativeQuery(sqlmin.toString())
                .setParameter("workFuncId", aWorkFunction)
                .setMaxResults(1)
                .getResultList();
		/*if (rownext.isEmpty() || rownext.get(0)[1]==null) {
			rownext = manager.createNativeQuery(sqlmax.toString())
				.setParameter("workFuncId", aWorkFunction) 
				.setMaxResults(1)
				.getResultList() ;

		}*/
        if (rownext.isEmpty() || rownext.get(0)[1] == null) {
            return null;
        }
        ret.append(rownext.get(0)[0]).append("#").append(rownext.get(0)[1]);
        return ret.toString();
    }
}
