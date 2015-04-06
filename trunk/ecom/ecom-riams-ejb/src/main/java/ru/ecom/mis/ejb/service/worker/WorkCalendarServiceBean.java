package ru.ecom.mis.ejb.service.worker;

import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.annotation.EJB;
import javax.annotation.Resource;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.jdom.IllegalDataException;

import ru.ecom.ejb.services.entityform.ILocalEntityFormService;
import ru.ecom.ejb.services.live.domain.journal.DeleteJournal;
import ru.ecom.ejb.services.util.ConvertSql;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.ecom.mis.ejb.domain.medcase.Visit;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.ecom.mis.ejb.domain.workcalendar.WorkCalendar;
import ru.ecom.mis.ejb.domain.workcalendar.WorkCalendarDay;
import ru.ecom.mis.ejb.domain.workcalendar.WorkCalendarDayPattern;
import ru.ecom.mis.ejb.domain.workcalendar.WorkCalendarPattern;
import ru.ecom.mis.ejb.domain.workcalendar.WorkCalendarTime;
import ru.ecom.mis.ejb.domain.workcalendar.WorkCalendarTimeExample;
import ru.ecom.mis.ejb.domain.workcalendar.WorkCalendarTimePattern;
import ru.ecom.mis.ejb.domain.workcalendar.voc.VocServiceReserveType;
import ru.ecom.mis.ejb.domain.workcalendar.voc.VocServiceStream;
import ru.ecom.mis.ejb.domain.workcalendar.voc.VocWorkBusy;
import ru.ecom.mis.ejb.domain.worker.JournalPatternCalendar;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.nuzmsh.util.format.DateFormat;


@Stateless
@Local(IWorkCalendarService.class)
@Remote(IWorkCalendarService.class)
@SuppressWarnings("unchecked")
public class WorkCalendarServiceBean implements IWorkCalendarService{
	
	private final static Logger LOG = Logger.getLogger(WorkCalendarServiceBean.class);
	private final static boolean CAN_DEBUG = LOG.isDebugEnabled();
	public void moveDate(Long aWorkFunction, Date aDateFrom, Date aDateTo) {
		List<WorkCalendar> list = theManager
				.createQuery("from WorkCalendar where workFunction_id=:wf")
				.setParameter("wf", aWorkFunction).getResultList() ;
		//WorkCalendarPattern pattern = theManager.find(WorkCalendarPattern.class, aPattern);
		
		for (WorkCalendar wc:list) {
			List<Object> list1 = theManager.createNativeQuery("select id from WorkCalendarDay where workCalendar_id='"
					+wc.getId()+"' and calendarDate=:dat").setParameter("dat", aDateTo).getResultList() ;
			if (list1.size()==0) {
				theManager.createNativeQuery("update WorkCalendarDay set calendarDate=:datTo where workCalendar_id='"
						+wc.getId()+"' and calendarDate=:datFrom").setParameter("datTo", aDateTo).setParameter("datFrom", aDateFrom).executeUpdate() ;
			}
		}
	}
	public void moveSpecialist(Long aWorkFunctionMove, Long aWorkFunctionOrig, Date aDateFrom, Date aDateTo){
		List<WorkCalendar> list = theManager
				.createQuery("from WorkCalendar where workFunction_id=:wf")
				.setParameter("wf", aWorkFunctionMove).getResultList() ;
		List<WorkCalendar> list1 = theManager
				.createQuery("from WorkCalendar where workFunction_id=:wf")
				.setParameter("wf", aWorkFunctionOrig).getResultList() ;
			//WorkCalendarPattern pattern = theManager.find(WorkCalendarPattern.class, aPattern);
		if (list1.size()>0) {
			WorkCalendar wc1 = list1.get(0) ;
		
			for (WorkCalendar wc:list) {
				List<Object> list2 = theManager.createNativeQuery("select id from WorkCalendarDay where workCalendar_id='"
						+wc1.getId()+"' and calendarDate between :dat1 and :dat2").setParameter("dat1", aDateFrom).setParameter("dat2", aDateTo).getResultList() ;
				if (list2.size()==0) {
					theManager.createNativeQuery("update WorkCalendarDay set workCalendar_id="+wc1.getId()+" where workCalendar_id='"
							+wc.getId()+"' and calendarDate between :dat1 and :dat2").setParameter("dat1", aDateFrom).setParameter("dat2", aDateTo).executeUpdate() ;
				} else {
					throw new IllegalArgumentException(
							"Уже создан день!!!"
							
							) ; 
				}
			}
		} else {
			throw new IllegalArgumentException(
					"Ошибка при перемещении!!!"
					
					) ; 
		}
	}
	public void deleteWorkCalendarTime(Long aTime) {
		WorkCalendarTime wct = theManager.find(WorkCalendarTime.class, aTime) ;
		if (wct.getMedCase()!=null || wct.getPrePatient()!=null || wct.getPrePatientInfo()!=null &&!wct.getPrePatientInfo().equals("")) {
			throw new IllegalArgumentException(
					"Ошибка при удалении!!!"
					
					) ; 
		} else {
			theManager.remove(wct) ;
		}
	}
	public String deletePreRecord(String aUsername, Long aTime) {
		WorkCalendarTime wct = theManager.find(WorkCalendarTime.class, aTime) ;
		if (wct.getMedCase()==null) {
			DeleteJournal dj = new DeleteJournal() ;
			java.util.Date date = new java.util.Date() ;
			dj.setDeleteDate(new java.sql.Date(date.getTime())) ;
			dj.setDeleteTime(new Time(date.getTime())) ;
			dj.setClassName("WorkFunctionTime PreRecord") ;
			StringBuilder comment = new StringBuilder() ;
			comment.append(wct.getPrePatientInfo()!=null?wct.getPrePatientInfo():"")
				.append(" # ").append(wct.getPrePatient()!=null?wct.getPrePatient().getFio():"") ;
			WorkCalendarDay wcd = wct.getWorkCalendarDay() ;
			WorkFunction wf = wcd.getWorkCalendar()!=null?wcd.getWorkCalendar().getWorkFunction():null ;
			comment.append(" # ").append(wcd!=null?wcd.getCalendarDate():"") ;
			comment.append(" # ").append(wct.getTimeFrom()) ;
			comment.append(" # ").append(wf.getInfo());
			dj.setSerialization(comment.toString()) ;
			dj.setObjectId(String.valueOf(wct.getId())) ;
			dj.setLoginName(aUsername) ;
			
			wct.setPrePatient(null) ;
			wct.setPrePatientInfo(null) ;
			theManager.persist(wct) ;
			StringBuilder sql = new StringBuilder() ;
			sql.append("select wct.id as wctid,wc.id as wcid,wcd.id as wcddid,to_char(wcd.calendarDate,'dd.mm.yyyy') as calendardate,wf.workFunction_id from workcalendartime wct")
			.append(" left join workcalendarday wcd on wcd.id=wct.workcalendarday_id")
			.append(" left join workcalendar wc on wc.id=wcd.workcalendar_id")
			.append(" left join workfunction wf on wc.workFunction_id=wf.id")
			.append(" where wct.id='").append(aTime).append("'")
			//.append("' and wct.medcase_id is null and wct.prepatient_id is null and (wct.prepatientInfo is null or wct.prepatientInfo='') ") 
			;
			//System.out.println("sql="+sql) ;
			List<Object[]> l = theManager.createNativeQuery(sql.toString()).setMaxResults(1).getResultList() ;
			if (l.size()>0) {
				Object[] obj = l.get(0) ;
				theManager.persist(dj) ;
				return new StringBuilder().append(obj[1]).append("#").append(obj[2])
						.append("#").append(obj[3]).append("#")
						.append(obj[4]).toString() ;
			}
		}
		throw new IllegalArgumentException(
				"Ошибка при удалении!!!"
				
				) ; 
	}
	public String preRecordByPatient(String aUsername,Long aTime
			,String aPatientInfo,Long aPatientId) {
		StringBuilder sql = new StringBuilder() ;
		sql.append("select wct.id as wctid,wc.id as wcid,wcd.id as wcddid,to_char(wcd.calendarDate,'dd.mm.yyyy') as calendardate,wf.workFunction_id from workcalendartime wct")
		.append(" left join workcalendarday wcd on wcd.id=wct.workcalendarday_id")
		.append(" left join workcalendar wc on wc.id=wcd.workcalendar_id")
		.append(" left join workfunction wf on wc.workFunction_id=wf.id")
		.append(" where wct.id='").append(aTime)
		.append("' and wct.medcase_id is null and wct.prepatient_id is null and (wct.prepatientInfo is null or wct.prepatientInfo='') ") ;
		//System.out.println("sql="+sql) ;
		List<Object[]> l = theManager.createNativeQuery(sql.toString()).setMaxResults(1).getResultList() ;
		if (l.size()>0) {
			
			//System.out.println("patid="+aPatientId) ;
			
			sql = new StringBuilder() ;
			if (aPatientId!=null && aPatientId>Long.valueOf(0)) {
				sql.append("update WorkCalendarTime set createPreRecord='").append(aUsername).append("',prePatient_id='").append(aPatientId).append("',").append(getInfoByCreate("createDatePreRecord", "createTimePreRecord")).append(" where id='").append(aTime).append("'") ;
			} else {
				String info = aPatientInfo.replace("#", " ").toUpperCase() ;
				sql.append("update WorkCalendarTime set createPreRecord='").append(aUsername).append("',prePatientInfo='").append(info).append("',").append(getInfoByCreate("createDatePreRecord", "createTimePreRecord")).append(" where id='").append(aTime).append("'") ;
			}
			theManager.createNativeQuery(sql.toString()).executeUpdate() ;
			Object[] obj = l.get(0) ;
			return new StringBuilder().append(obj[1]).append("#").append(obj[2])
					.append("#").append(obj[3]).append("#")
					.append(obj[4]).toString() ;
			
		} else {
			StringBuilder err = new StringBuilder() ;
			throw new IllegalArgumentException(
					err.append("Ошибка при записи!!!")
					
					.toString()) ;
		}
		
	}
	public void recordByPatient(String aUsername, Long aFunction
			, Long aSpecialist, Long aDay, Long aTime
			,String aPatientInfo,Long aPatientId, Long aOrderLpu) {
		StringBuilder sql = new StringBuilder() ;
		sql.append("select wct.id as wctid,wc.id as wcid,wf.id as wfid from workcalendartime wct")
		.append(" left join workcalendarday wcd on wcd.id=wct.workcalendarday_id")
		.append(" left join workcalendar wc on wc.id=wcd.workcalendar_id")
		.append(" left join workfunction wf on wc.workFunction_id=wf.id")
		.append(" where wct.id='").append(aTime)
		.append("' and wcd.id='").append(aDay)
		.append("' and wc.id='").append(aSpecialist)
		.append("' and wf.workFunction_id='").append(aFunction)
		.append("' and wct.medcase_id is null and wct.prepatient_id is null and (wct.prepatientInfo is null or wct.prepatientInfo='') ") ;
		
		System.out.println("sql="+sql) ;
		List<Object[]> l = theManager.createNativeQuery(sql.toString()).setMaxResults(1).getResultList() ;
		if (l.size()>0) {
			
			sql = new StringBuilder() ;
			 sql.append("select to_char(wcd.calendarDate,'dd.mm.yyyy'),cast(wct.timeFrom as varchar(5)),case when wf.registrationInterval>0 then wf.registrationInterval when lpu1.registrationInterval>0 then lpu1.registrationInterval else lpu2.registrationInterval end from workCalendarTime wct left join WorkCalendarDay wcd on wcd.id=wct.workCalendarDay_id left join WorkCalendar wc on wc.id=wcd.workCalendar_id left join WorkFunction wf on wf.id=wc.workFunction_id left join worker w on wf.worker_id=w.id left join MisLpu lpu2 on lpu2.id=w.lpu_id left join MisLpu lpu1 on lpu1.id=wf.lpu_id where wct.id='")
				.append(aTime).append("' and wcd.id='").append(aDay)
				.append("' and wf.id='").append(aFunction).append("'") ; 
			//System.out.println("patid="+aPatientId) ;
			l = theManager.createNativeQuery(sql.toString()).setMaxResults(1).getResultList() ;
			if (l.size()>0) {
				throw new IllegalArgumentException(
						("Ошибка при записи у Вас уже есть направление к этому врачу!!!")) ;
			} 
			sql = new StringBuilder() ;
			if (aPatientId!=null && aPatientId>Long.valueOf(0)) {
				sql.append("update WorkCalendarTime set createPreRecord='").append(aUsername).append("',prePatient_id='").append(aPatientId).append("',").append(getInfoByCreate("createDatePreRecord", "createTimePreRecord")).append(" where id='").append(aTime).append("'") ;
			} else {
				String info = aPatientInfo.replace("#", " ").toUpperCase() ;
				sql.append("update WorkCalendarTime set createPreRecord='").append(aUsername).append("',prePatientInfo='").append(info).append("',").append(getInfoByCreate("createDatePreRecord", "createTimePreRecord")).append(" where id='").append(aTime).append("'") ;
			}
			Visit vis = new Visit() ;
			WorkCalendarDay wcd = theManager.find(WorkCalendarDay.class, aDay) ;
			vis.setDatePlan(wcd) ;
			WorkCalendarTime wct = theManager.find(WorkCalendarTime.class, aTime) ;
			vis.setTimePlan(wct) ;
			WorkCalendar wc = theManager.find(WorkCalendar.class, aSpecialist) ;
			WorkFunction wf = wc.getWorkFunction() ;
			vis.setWorkFunctionPlan(wf) ;
			List<VocServiceStream> lVss = theManager.createQuery("from VocServiceStream where code='OBLIGATORYINSURANCE' order by id").getResultList() ;
			vis.setServiceStream(lVss.isEmpty()?null:lVss.get(0)) ;
			Patient pat = theManager.find(Patient.class, aPatientId) ;
			vis.setPatient(pat) ;
			MisLpu ordLpu = theManager.find(MisLpu.class, aOrderLpu) ;
			vis.setOrderLpu(ordLpu) ;
			//theManager.createNativeQuery(sql.toString()).executeUpdate() ;
			theManager.persist(vis) ;
			wct.setMedCase(vis) ;
			theManager.persist(wct) ;
			
		} else {
			StringBuilder err = new StringBuilder() ;
			throw new IllegalArgumentException(
					err.append("Ошибка при записи!!!")
					
					.toString()) ;
		}
		
	}
	public void preRecordByPatient(String aUsername, Long aFunction, Long aSpecialist, Long aDay, Long aTime
			,String aPatientInfo,Long aPatientId, Long aServiceStream) {
		StringBuilder sql = new StringBuilder() ;
		sql.append("select wct.id,wc.id from workcalendartime wct")
			.append(" left join workcalendarday wcd on wcd.id=wct.workcalendarday_id")
			.append(" left join workcalendar wc on wc.id=wcd.workcalendar_id")
			.append(" left join workfunction wf on wc.workFunction_id=wf.id")
			.append(" where wct.id='").append(aTime)
			.append("' and wcd.id='").append(aDay)
			.append("' and wc.id='").append(aSpecialist)
			.append("' and wf.workFunction_id='").append(aFunction)
			.append("' and wct.medcase_id is null and wct.prepatient_id is null and (wct.prepatientInfo is null or wct.prepatientInfo='') ") ;
		System.out.println("sql="+sql) ;
		StringBuilder serviceStream = new StringBuilder() ;
		if (aServiceStream!=null&&!aServiceStream.equals(Long.valueOf(0))) serviceStream.append(" serviceStream_id='").append(aServiceStream).append("' ,");
		List<Object[]> l = theManager.createNativeQuery(sql.toString()).setMaxResults(1).getResultList() ;
		if (l.size()>0) {
			//System.out.println("patid="+aPatientId) ;
			sql = new StringBuilder() ;
			if (aPatientId!=null && aPatientId>Long.valueOf(0)) {
				sql.append("update WorkCalendarTime set ").append(serviceStream).append("createPreRecord='").append(aUsername).append("',prePatient_id='").append(aPatientId).append("',").append(getInfoByCreate("createDatePreRecord", "createTimePreRecord")).append(" where id='").append(aTime).append("'") ;
			} else {
				String info = aPatientInfo.replace("#", " ").toUpperCase() ;
				sql.append("update WorkCalendarTime set ").append(serviceStream).append(" createPreRecord='").append(aUsername).append("',prePatientInfo='").append(info).append("',").append(getInfoByCreate("createDatePreRecord", "createTimePreRecord")).append(" where id='").append(aTime).append("'") ;
			}
			theManager.createNativeQuery(sql.toString()).executeUpdate() ;
			
			
		} else {
			StringBuilder err = new StringBuilder() ;
			throw new IllegalArgumentException(
					err.append("Ошибка при записи!!!")
					
					.toString()) ;
		}
		
	}
	private String getInfoByCreate(String aFldDate,String aFldTime) {
		java.util.Date date = new java.util.Date() ;
		SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy") ;
		//java.sql.Time time = new java.sql.Time (date.getTime());
		SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm");
        //TIME_FORMAT.format(time) ;
		StringBuilder ret = new StringBuilder() ;
		ret.append(aFldDate).append("=to_date('").append(format.format(date)).append("','dd.mm.yyyy')") ;
		ret.append(",").append(aFldTime).append("='").append(TIME_FORMAT.format(date)).append("'") ;
        return ret.toString() ; 
	}
	public String getIntervalBySpecAndDate(String aDate
			, Long aSpecialist) throws ParseException {
		Date date = DateFormat.parseSqlDate(aDate) ;
		 //int duration = aDuration.intValue() ;
		 List<WorkCalendar> list = theManager
					.createQuery("from WorkCalendar where workFunction_id=:wf")
					.setParameter("wf", aSpecialist).setMaxResults(1).getResultList() ;
		 if (list.size()>0) {
			WorkCalendar wc = list.get(0) ;
			
			List<WorkCalendarDay> listD = theManager
					.createQuery("from WorkCalendarDay where workCalendar=:calen and calendarDate=:day")
					.setParameter("calen", wc)
					.setParameter("day", date) 
					.setMaxResults(1).getResultList() ;
			if (listD.size()>0) {
				WorkCalendarDay day = listD.get(0) ;
				StringBuilder sql1 = new StringBuilder() ;
				sql1.append(" select cast(min(t.timeFrom) as varchar(5)),cast(max(t.timeFrom) as varchar(5))");
				sql1.append(" from WorkCalendarTime t ");
				sql1.append(" where t.workCalendarDay_id = '")
					.append(day.getId()).append("'");
				List<Object[]> times = theManager
						.createNativeQuery(sql1.toString())
						.setMaxResults(1).getResultList() ;
				if (times.size()>0) {
					StringBuilder ret = new StringBuilder() ;
					Object[] objs = times.get(0) ;
					ret.append(objs[0]!=null?objs[0]:"").append("-") ;
					ret.append(objs[1]!=null?objs[1]:"") ;
					return ret.toString() ;
				}
			}
			
		 }
		return "" ;
	}
	// Получить новые времена по специалисту за определенное число
	public String getTimesBySpecAndDate(String aDate
			, Long aSpecialist, Long aCountVisits
			, String aBeginTime, String aEndTime
			) throws ParseException {
		 Date date = DateFormat.parseSqlDate(aDate) ;
		 //int duration = aDuration.intValue() ;
		 List<Object> list = theManager
					.createNativeQuery("select id from WorkCalendar where workFunction_id=:wf")
					.setParameter("wf", aSpecialist).setMaxResults(1).getResultList() ;
		 if (list.size()>0) {
			Object wc = list.get(0) ;
			
			List<Object> listD = theManager
					.createNativeQuery("select id from WorkCalendarDay where workCalendar_id=:calen and calendarDate=:day")
					.setParameter("calen", ConvertSql.parseLong(wc))
					.setParameter("day", date) 
					.setMaxResults(1).getResultList() ;
			java.sql.Time timeFrom = DateFormat.parseSqlTime(aBeginTime) ;
			java.sql.Time timeTo = DateFormat.parseSqlTime(aEndTime) ;
			Calendar cal1 = java.util.Calendar.getInstance() ;
			Calendar cal2 = java.util.Calendar.getInstance() ;
			cal1.setTime(timeFrom) ;
			cal2.setTime(timeTo) ;
			int cnt = aCountVisits.intValue() ;
			StringBuilder ret = new StringBuilder() ;
			if (listD.size()>0) {
				Object day = listD.get(0) ;
				StringBuilder sql1 = new StringBuilder() ;
				sql1.append(" select count(*)");
				sql1.append(" from WorkCalendarTime t ");
				sql1.append(" where t.workCalendarDay_id = '")
					.append(day).append("'");
				sql1.append(" and t.timeFrom=:tim") ;
				//List<Object[]> listT1 = theManager.createNativeQuery(sql1.toString()).setMaxResults(1).getResultList() ;
				boolean isFirstExist = theManager.createNativeQuery(sql1.toString()).setParameter("tim", timeFrom).setMaxResults(1).getResultList().size()>0?true:false ;
				boolean isEndExist = theManager.createNativeQuery(sql1.toString()).setParameter("tim", timeTo).setMaxResults(1).getResultList().size()>0?true:false ;
				
				java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("HH:mm") ;
				if (cnt<1) {
					return getInterval(timeFrom, timeTo, aCountVisits, true) ;
				}
				if (!isFirstExist) {
					ret.append(",").append(format.format(cal1.getTime())) ;
					cnt-- ;
				}
				if (cnt<1) {
					return ret.substring(1) ;
				}
				if (!isEndExist) {
					cnt-- ;
				}
				if (cnt<1) {
					ret.append(",").append(format.format(cal2.getTime())) ;
					
				} else {
					StringBuilder sql = new StringBuilder() ;
					sql.append(" select t.timeFrom as t1timeFrom,t1.timeFrom as t2timeFrom");
					sql.append(" from WorkCalendarTime t ");
					sql.append(" left join WorkCalendarTime t1 on t1.workCalendarDay_id=t.workCalendarDay_id"); 
					sql.append(" left join WorkCalendarTime t2 on t2.workCalendarDay_id=t.workCalendarDay_id ");
					sql.append(" where t.workCalendarDay_id = '")
						.append(day).append("' and t.timeFrom between cast('")
						.append(aBeginTime).append("' as time) and cast('")
						.append(aEndTime).append("' as time) and t.timeFrom<t1.timeFrom ");
					sql.append(" and t2.timeFrom between t.timeFrom and t1.timeFrom");
					sql.append(" group by t.timeFrom,t1.timeFrom,t1.timeFrom-t.timeFrom");
					sql.append(" having count(t2.id)=2");
					sql.append(" order by t1.timeFrom-t.timeFrom desc,t.timeFrom desc");
					List<Object[]> listT = theManager.createNativeQuery(sql.toString()).setMaxResults(cnt).getResultList() ;
					int dop = cnt-listT.size() ;
					StringBuilder ret1= new StringBuilder() ;
					for (Object[] objs:listT) {
						int rec=1 ;
						if (dop>0) {
							LOG.info("dop="+dop) ;
							rec++ ;
						}
						ret1.insert(0,getInterval((java.sql.Time)objs[0],(java.sql.Time)objs[1],Long.valueOf(rec),false)) ;
						ret1.insert(0,",") ;
						dop-- ;
					}
					ret.append(ret1);
					if (!isEndExist) {ret.append(",").append(format.format(cal1.getTime())) ;}
				}
				return ret.length()>0?ret.substring(1):"" ;
			} else {
				return getInterval(timeFrom, timeTo, aCountVisits, true) ;
			}

		 }
		 return "" ;
	 }
	 
	 private String getInterval(String aBeginTime, String aEndTime, Long aCountVisits,boolean aFirstExists) throws ParseException {
		java.sql.Time timeFrom = DateFormat.parseSqlTime(aBeginTime) ;
		java.sql.Time timeTo = DateFormat.parseSqlTime(aEndTime) ;
		return getInterval(timeFrom, timeTo, aCountVisits, aFirstExists) ;
	 }
	 private String getInterval(java.sql.Time aBeginTime, java.sql.Time aEndTime, Long aCountVisits,boolean aFirstExists) throws ParseException {
		LOG.info("c "+aBeginTime+" по "+aEndTime+" "+aCountVisits) ;
		Calendar cal1 = java.util.Calendar.getInstance() ;
		Calendar cal2 = java.util.Calendar.getInstance() ;
		cal1.setTime(aBeginTime) ;
		cal2.setTime(aEndTime) ;
		int cnt = aCountVisits.intValue() ;
		int hour1 = cal1.get(Calendar.HOUR_OF_DAY) ;
		int hour2 = cal2.get(Calendar.HOUR_OF_DAY) ;
		int min1 = cal1.get(Calendar.MINUTE) ;
		int min2 = cal2.get(Calendar.MINUTE) ;
		int dif = (hour2-hour1)*60 +min2- min1 ;
		/*LOG.info("Кол-во минут="+dif) ;
		LOG.info("Кол-во минут1="+min1) ;
		LOG.info("Кол-во минут2="+min2) ;
		LOG.info("Кол-во часов1="+hour1) ;
		LOG.info("Кол-во часов2="+hour2) ;*/
		if (dif<cnt) throw new IllegalDataException("Разница между временами должна быть больше кол-ва посещений")  ;
		StringBuilder ret = new StringBuilder() ;
		java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("HH:mm") ;
		if (dif%cnt == 0) {
			if (!aFirstExists) {
				cnt=cnt+1 ;
			}
			int interval = dif/cnt ;
			LOG.info("Интервал1="+interval) ;
			if (interval<0) throw new IllegalDataException("Отрицательный интервал")  ;
			if (!aFirstExists) {
				cal1.add(Calendar.MINUTE, interval) ;
			}
			while (cal2.after(cal1)) {
				//System.out.println(format.format(cal1.getTime())) ;
				ret.append(",").append(format.format(cal1.getTime())) ;
				cal1.add(java.util.Calendar.MINUTE, interval) ;
			}
		} else {
			int interval = dif/cnt ;
			LOG.info("Интервал2="+interval) ;
			if (interval<0) throw new IllegalDataException("Отрицательный интервал")  ;
			int dop = dif % cnt ;
			if (!aFirstExists) {
				cal1.add(Calendar.MINUTE, interval) ;
				if (dop>0) {
					dop-- ;
					cal1.add(Calendar.MINUTE, 1) ;
				}
			}
			while (cal2.after(cal1)) {
				//System.out.println(format.format(cal1.getTime())) ;
				java.sql.Time sqlTime = new java.sql.Time(cal1.getTime().getTime()) ;
				ret.append(",").append(DateFormat.formatToTime(sqlTime)) ;
				cal1.add(Calendar.MINUTE, interval) ;
				if (dop>0) {
					dop-- ;
					cal1.add(Calendar.MINUTE, 1) ;
				}
			}			
		}
		LOG.info("Времена: "+(ret.length()>0?ret.substring(1):"")) ;
		return ret.length()>0?ret.substring(1):"";
	}
			
	 // Создать новые времена по специалисту за определенное число
	 public void getCreateNewTimesBySpecAndDate(String aDate
			 , Long aSpecialist, String aTimes, Long aReserveType) throws ParseException {
		 VocServiceReserveType vsrt = (aReserveType!=null && !aReserveType.equals(Long.valueOf(0)))?
				 theManager.find(VocServiceReserveType.class, aReserveType):null ;
		 if (aTimes!=null) {
			 aTimes=aTimes.replace(" ", "") ;
			 if (!aTimes.equals("")) {
				 Date date = DateFormat.parseSqlDate(aDate) ;
				 String[] times = aTimes.split(",") ;
				 List<WorkCalendar> list = theManager
						 .createQuery("from WorkCalendar where workFunction_id=:wf")
						 .setParameter("wf", aSpecialist).setMaxResults(1).getResultList() ;
				 if (list.size()>0) {
					 WorkCalendar wc = list.get(0) ;
					 WorkCalendarDay day = createCalendarDay(wc,date) ;
					 for (String time:times) {
						 java.sql.Time t = DateFormat.parseSqlTime(time) ;
						 createCalendarTime(t,vsrt, day,true) ;
					 }
				 }
			 }
		 }
		 
	 }
	// Создать новые времена по специалисту за определенное число
	 public String addCreateNewTimeBySpecAndDate(String aDate
			, Long aSpecialist, String aTime,Long aReserveType) throws ParseException {
		 
		 if (aTime!=null) {
			 aTime=aTime.replace(" ", "") ;
			 if (!aTime.equals("")) {
				 Date date = DateFormat.parseSqlDate(aDate) ;
				 List<WorkCalendar> list = theManager
					.createQuery("from WorkCalendar where workFunction_id=:wf")
					.setParameter("wf", aSpecialist).setMaxResults(1).getResultList() ;
				 if (list.size()>0) {
					 WorkCalendar wc = list.get(0) ;
					 WorkCalendarDay day = createCalendarDay(wc,date) ;
					 java.sql.Time t = DateFormat.parseSqlTime(aTime) ;
					 Long tid = createCalendarTime(t,aReserveType, day) ;
					 return tid!=null?new StringBuilder().append(tid)
							 .append("#").append(aTime).toString():"" ;
				 }
			 }
		 }
		 return "" ;
	 }
	public void deleteEmptyCalendarDays(Long aWorkFunction, Date aDateFrom, Date aDateTo) {
		List<WorkCalendar> list = theManager
				.createQuery("from WorkCalendar where workFunction_id=:wf")
				.setParameter("wf", aWorkFunction).getResultList() ;
			//WorkCalendarPattern pattern = theManager.find(WorkCalendarPattern.class, aPattern);
			for (WorkCalendar wc:list) {
				deleteCalendarDaysByWorkFunction(wc,aDateFrom,aDateTo) ;
			}
	}
	public void addBusyPatternByWorkFunction(Long aWorkFunction,Date aBeginDate,Date aFinishDate, Long aPattern) {
		//WorkFunction wf = theManager.find(WorkFunction.class, aWorkFunction) ;
		List<WorkCalendar> list = theManager
			.createQuery("from WorkCalendar where workFunction_id=:wf")
			.setParameter("wf", aWorkFunction).getResultList() ;
		WorkCalendarPattern pattern = theManager.find(WorkCalendarPattern.class, aPattern);
		for (WorkCalendar wc:list) {
			addBusyPattern(wc,aBeginDate,aFinishDate,pattern) ;
		}
		//ru.nuzmsh.util.format.DateFormat.parseSqlTime(aTimeString)
	}
	
	private void addBusyPattern(WorkCalendar aWorkCalendar, Date aDateFrom, 
			Date aDateTo, WorkCalendarPattern aPattern) {
		
		StringBuilder sql = new StringBuilder() ;
		sql.append("from JournalPatternCalendar  where workCalendar=:wc")
			//.append(aWorkCalendar.getId())
			.append("  and dateFrom>=:dateFrom and dateTo<=:dateTo") ;
		List<JournalPatternCalendar> list = theManager
				.createQuery(sql.toString())
				.setParameter("wc", aWorkCalendar)
				.setParameter("dateFrom", aDateFrom)
				.setParameter("dateTo", aDateTo)
				.getResultList() ;
		Calendar calFrom = Calendar.getInstance() ;
		calFrom.setTime(aDateFrom) ;
		Calendar calTo = Calendar.getInstance() ;
		calTo.setTime(aDateTo) ;
		JournalPatternCalendar jpcnew = new JournalPatternCalendar() ;
		jpcnew.setDateFrom(aDateFrom) ;
		jpcnew.setDateTo(aDateTo) ;
		jpcnew.setNoActive(false) ;
		jpcnew.setPattern(aPattern) ;
		jpcnew.setWorkCalendar(aWorkCalendar) ;
		theManager.persist(jpcnew ) ;
		//theManager.refresh(jpcnew) ;
		//long calLTo = calTo.getTime().getTime() ;
		//long calLFrom = calFrom.getTime().getTime() ;
		//SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy") ;
		//System.out.println("---------calFrom="+format.format(calFrom.getTime())) ;
		//System.out.println("---------calTo="+format.format(calTo.getTime())) ;
		calTo.add(Calendar.DAY_OF_MONTH, 1) ;
		calFrom.add(Calendar.DAY_OF_MONTH, -1) ;		
		for (JournalPatternCalendar jpc: list) {
			Calendar calFrom1 = Calendar.getInstance() ;
			calFrom1.setTime(jpc.getDateFrom()) ;
			Calendar calTo1 = Calendar.getInstance() ;
			calTo1.setTime(jpc.getDateTo()) ;
			//long calLTo1 = calTo1.getTime().getTime() ;
			//long calLFrom1 = calFrom1.getTime().getTime() ;
			
			//calTo1.add(Calendar.DAY_OF_MONTH, 1) ;
			/*System.out.println("----------------------------------------------------------") ;
			System.out.println("---------------jcp="+jpc.getId()) ;
			System.out.println("---------------calFrom1="+format.format(calFrom1.getTime())) ;
			System.out.println("---------------calTo1="+format.format(calTo1.getTime())) ;
			System.out.println("---------------calLFrom1="+calLFrom1) ;
			System.out.println("---------------calLFrom="+calLFrom) ;
			System.out.println("---------------calLTo1="+calLTo1) ;
			System.out.println("---------------calLTo="+calLTo) ;
			System.out.println("---------------calTo  after calTo1="+calTo.after(calTo1)) ;
			System.out.println("---------------calTo  after calFrom1="+calTo.after(calFrom1)) ;
			System.out.println("---------------calFrom  after calTo1="+calFrom.after(calTo1)) ;
			System.out.println("---------------calFrom  after calFrom1="+calFrom.after(calFrom1)) ;
			*/
			if (calFrom1.after(calFrom) && (calTo1.after(calTo) )) {
				//System.out.println("--------------++++++--1-") ;
				
				jpc.setDateFrom(new Date(calTo.getTime().getTime())) ;
			} else if (calFrom.after(calFrom1) && (calTo.after(calTo1) )) {
				//System.out.println("--------------++++++--2-") ;
				//calFrom1.add(Calendar.DAY_OF_MONTH, -1) ;
				jpc.setDateTo(new Date(calFrom.getTime().getTime())) ;
			} else if ((calFrom1.after(calFrom)) && (calTo.after(calTo1))) {
				//System.out.println("--------------++++++--3-") ;
				//calTo1.setTime(jpc.getDateTo()) ;
				jpc.setNoActive(Boolean.TRUE); 
				
			} else if (calFrom1.before(calFrom) && calTo1.before(calTo)) {
				//System.out.println("--------------++++++--4-") ;

				JournalPatternCalendar jpc1 = new JournalPatternCalendar() ;
				jpc1.setDateFrom(new Date(calTo.getTime().getTime())) ;
				jpc1.setDateTo(jpc.getDateTo()) ;
				jpc1.setPattern(jpc.getPattern()) ;
				jpc1.setWorkCalendar(jpc.getWorkCalendar()) ;
				jpc1.setNoActive(jpc.getNoActive()) ;
				jpc.setDateTo(new Date(calFrom.getTime().getTime())) ;
				theManager.persist(jpc1) ;
				//theManager.refresh(jpc1) ;
			}
			//System.out.println("-----res noado="+jpc.getNoActive()) ;
			theManager.persist(jpc) ;
			//theManager.refresh(jpc) ;
			//System.out.println("-----res noalast="+jpc.getNoActive()) ;
			//System.out.println("-----res from="+format.format(jpc.getDateFrom())) ;
			//System.out.println("-----res to="+format.format(jpc.getDateTo())) ;
		}
	}
	public void addNotBusyPattern(Long aWorkFunction,Date aDateFrom, Date aDateTo, Long aReason) {
		List<WorkCalendar> list = theManager
				.createQuery("from WorkCalendar where workFunction_id=:wf")
				.setParameter("wf", aWorkFunction).getResultList() ;
		VocWorkBusy workBusy = theManager.find(VocWorkBusy.class, aReason);
		for (WorkCalendar wc:list) {
			JournalPatternCalendar jpc = new JournalPatternCalendar() ;
			jpc.setDateFrom(aDateFrom) ;
			jpc.setDateTo(aDateTo) ;
			jpc.setWorkBusy(workBusy) ;
			jpc.setWorkCalendar(wc) ;
			jpc.setNoActive(Boolean.FALSE) ;
			theManager.persist(jpc) ;
		}
	}
	
	public void generateCalendarByWorkFunction(Long aWorkFunction,Date aBeginDate,Date aFinishDate) {
		//WorkFunction wf = theManager.find(WorkFunction.class, aWorkFunction) ;
		List<WorkCalendar> list = theManager
			.createQuery("from WorkCalendar where workFunction_id=:wf")
			.setParameter("wf", aWorkFunction).getResultList() ;
		
		for (WorkCalendar wc:list) {
			generateByPattern(wc,aBeginDate,aFinishDate) ;
		}
		//ru.nuzmsh.util.format.DateFormat.parseSqlTime(aTimeString)
	}
	
	
	
	private void generateByPattern(WorkCalendar aWorkCalendar,Date aBeginDate,Date aFinishDate) {
		//List <JournalPatternCalendar> list = theManager.createQuery("from JournalPatternCalendar where (NoActive is null or cast(NoActive as integer)=0) and dateFrom<:finishDate and (dateTo is null or dateTo>:aBegin) order by dateFrom")
		//	.getResultList() ;
		
		Calendar cal1 = java.util.Calendar.getInstance() ;
		Calendar cal2 = java.util.Calendar.getInstance() ;
		
		cal1.setTime(aBeginDate) ;
		cal2.setTime(aFinishDate) ;
		cal2.add(Calendar.DAY_OF_MONTH, 1) ;
		
		JournalPatternCalendar jpc =null ;
		Calendar calFrom1 = Calendar.getInstance() ;
		Calendar calTo1 = Calendar.getInstance() ;

		SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy") ;
		//System.out.println("---------------cal1="+format.format(cal1.getTime())) ;
		//System.out.println("---------------cal2="+format.format(cal2.getTime())) ;
		
		// Очистить данные за период
		deleteUnMedCasesCalendarTimes(aWorkCalendar,aBeginDate,aFinishDate) ;
		
		while (cal2.after(cal1)) {
			// Обработка дней
			//System.out.println("---------------cal1="+format.format(cal1.getTime())) ;
			//int weekYear =cal1.get(Calendar.WEEK_OF_YEAR) ;
			//boolean parityYear= (weekYear%2==1)?true:false ;
			int weekMonth =cal1.get(Calendar.WEEK_OF_MONTH) ;
			boolean parityWeek= (weekMonth%2==1)?false:true ;
			int weekDayV =cal1.get(Calendar.DAY_OF_WEEK);
			int weekDay =cal1.get(Calendar.DAY_OF_WEEK) -1;
			if (weekDay==0) weekDay=7;
			int monthDay =cal1.get(Calendar.DAY_OF_MONTH) ;
			boolean parityDay= (monthDay%2==1)?false:true ;
			int monthId = cal1.get(Calendar.MONTH)+1 ;
			boolean parityMonth= (monthId%2==1)?false:true ;
			
			//System.out.println("--------------->calFrom="+format.format(calFrom1.getTime())) ;
			//System.out.println("--------------->calTo="+format.format(calTo1.getTime())) ;
			//System.out.println("--------------->calTo after calFrom="+calTo1.after(calFrom1)) ;
			// Определение действующего журнала
			boolean isNewJour = false;
			if (jpc==null || !calTo1.after(cal1)) {
				//System.out.println("<<<<<<--------------->calTo after calFrom="+calTo1.after(calFrom1)) ;
				isNewJour=true ;
			}
			if (isNewJour) {
				List<JournalPatternCalendar> list1 = theManager
					.createQuery("from JournalPatternCalendar where workCalendar=:cal and (workBusy_id is null or workBusy.isWorking='1') and (NoActive is null or NoActive='0') and dateFrom<=:dateCal and (dateTo is null or dateTo>=:dateCal) order by dateFrom")
					.setParameter("cal", aWorkCalendar)
					.setParameter("dateCal",cal1.getTime())
					.getResultList() ;
				
				jpc = list1.isEmpty()?null:list1.get(0) ;
				
				if (jpc!=null) {
	
					calFrom1.setTime(jpc.getDateFrom()); 
					if (jpc.getDateTo()==null) {
						calTo1.setTime(cal2.getTime()) ;
					} else {
						calTo1.setTime(jpc.getDateTo()) ;
						//calTo.add(Calendar.DAY_OF_MONTH, 1) ;
					}
				}
			}
			
			
			List<Object> cnt = theManager
					.createNativeQuery("select count(*) from JournalPatternCalendar jpc left join VocWorkBusy  vwb on vwb.id=jpc.workBusy_id  where jpc.workCalendar_id=:cal and (jpc.noActive is null or jpc.noActive='0') and (vwb.isWorking is null or vwb.isWorking='0') and jpc.dateFrom<=:dateCal and (jpc.dateTo is null or jpc.dateTo>=:dateCal) ") 
					.setParameter("cal", aWorkCalendar.getId())
					.setParameter("dateCal",new Date(cal1.getTime().getTime()))
					.getResultList() ;
			
			System.out.println("------------<>----выходной день cnt="+cnt) ;
			Long cntNotBusy = cnt.size()>0?ConvertSql.parseLong(cnt.get(0)):null ;
			
			if (cntNotBusy!=null&&cntNotBusy>Long.valueOf(0) || jpc==null) {
				
				deleteEmptyCalendarDays(aWorkCalendar, new Date(cal1.getTime().getTime()), new Date(cal1.getTime().getTime()));
				//throw new IllegalDataException(""+cntNotBusy) ;
			} else {
				// Проверка есть ли не рабочее время на специалиста
				// else {
				
					String weekDaySt = String.valueOf(weekDay) ;
					String monthOrder = getOrderWeek(cal1,weekMonth);
					WorkCalendarPattern pattern = jpc.getPattern() ;
					if (pattern!=null) {
						// Поиск алгоритма по профдню WorkCalendarProphDayAlgorithm
						StringBuilder sql = new StringBuilder() ;
						
						sql.append("select wca.id,wca.dayPattern_id from WorkCalendarAlgorithm wca ")
						.append(" left join VocWeekMonthOrder vwmo on vwmo.id=wca.monthOrder_id")
						.append(" left join VocWeekDay vwd on vwd.id=wca.weekDay_id")
						.append(" left join VocWorkCalendarParity vwcp on vwcp.id=wca.calendarParity_id")
						.append(" left join VocDayParity vdp on vdp.id=wca.parity_id")
						.append(" where dtype='WorkCalendarProphDayAlgorithm' and pattern_id=:pattern")
						.append(" and (wca.monthDay=:monthDay or vwmo.code=:monthOrder")
						.append(" and vwd.code=:weekDay) and (wca.parity_id is null ")	
								.append(" or (vwcp.code='MONTH' and vdp.code=:monthParity)")
								.append(" or (vwcp.code='WEEK' and vdp.code=:weekParity)")
								.append(" or (vwcp.code='DAY' and vdp.code=:dayParity) )") ;
						Query query = theManager.createNativeQuery(sql.toString())
								.setParameter("pattern", pattern.getId())
								.setParameter("monthDay", monthDay)
								.setParameter("monthOrder", monthOrder)
								.setParameter("weekDay", weekDaySt) 
										.setParameter("monthParity",parityMonth?"YES":"NO")
										.setParameter("weekParity", parityWeek?"YES":"NO")
										.setParameter("dayParity", parityDay?"YES":"NO");
								;
						if (query.getResultList().isEmpty()) {
							// Поиск алгоритма по датам WorkCalendarDatesAlgorithm
							sql = new StringBuilder() ;
							boolean nextSearch =true ;
							Date dateCur = new Date(cal1.getTime().getTime()) ;
							sql.append("select wca.id,wca.dayPattern_id from WorkCalendarAlgorithm wca ")
							.append(" left join VocWorkCalendarParity vwcp on vwcp.id=wca.calendarParity_id")
							.append(" left join VocDayParity vdp on vdp.id=wca.parity_id")
							.append(" where wca.dtype='WorkCalendarDatesAlgorithm' and wca.pattern_id=:pattern")
							.append(" and :day between wca.dateFrom and wca.dateTo and (wca.parity_id is null ")	
								.append(" or (vwcp.code='MONTH' and vdp.code=:monthParity)")
								.append(" or (vwcp.code='WEEK' and vdp.code=:weekParity)")
								.append(" or (vwcp.code='DAY' and vdp.code=:dayParity) )") ;
							query = theManager.createNativeQuery(sql.toString())
									.setParameter("pattern", pattern.getId()).setParameter("day", cal1.getTime())
										.setParameter("monthParity",parityMonth?"YES":"NO")
										.setParameter("weekParity", parityWeek?"YES":"NO")
										.setParameter("dayParity", parityDay?"YES":"NO");
									;
							//System.out.println("----------------WorkCalendarDatesAlgorithm=");
							nextSearch= !getParrentDay(aWorkCalendar, dateCur, query) ;
							//System.out.println("----------------WorkCalendarDatesAlgorithm="+listA.size());
							
							// Поиск алгоритма по дням недели WorkCalendarWeekAlgorithm
							if (nextSearch) {  
								sql = new StringBuilder() ;
								
								sql.append("select wca.id,wca.dayPattern_id from WorkCalendarAlgorithm wca ")
								.append(" left join VocWorkWeek vww on vww.id=wca.workWeek_id")
								.append(" left join VocWorkCalendarParity vwcp on vwcp.id=wca.calendarParity_id")
								.append(" left join VocDayParity vdp on vdp.id=wca.parity_id")
								.append(" where dtype='WorkCalendarWeekAlgorithm' and pattern_id=:pattern")	
								.append(" and cast(vww.code as int)>=:dayBy and (wca.parity_id is null ")	
								.append(" or (vwcp.code='MONTH' and vdp.code=:monthParity)")
								.append(" or (vwcp.code='WEEK' and vdp.code=:weekParity)")
								.append(" or (vwcp.code='DAY' and vdp.code=:dayParity) )") ;
								query = theManager.createNativeQuery(sql.toString())
										.setParameter("pattern", pattern.getId()).setParameter("dayBy", weekDay)
										.setParameter("monthParity",parityMonth?"YES":"NO")
										.setParameter("weekParity", parityWeek?"YES":"NO")
										.setParameter("dayParity", parityDay?"YES":"NO");
								System.out.println("----------------WorkCalendarWeekAlgorithm=");
								System.out.println("----------------dayBy="+weekDay);
								nextSearch = !getParrentDay(aWorkCalendar, dateCur, query) ;
								//System.out.println("----------------WorkCalendarWeekAlgorithm="+listA.size());
							}
							if (nextSearch) {
							//if (false) {
								// Поиск алгоритма по неделям WorkCalendarWeekDaysAlgorithm
								sql = new StringBuilder() ;
								String daySymbol = getWeekDaySymbol(weekDayV) ;
								if (daySymbol!=null) {
									sql.append("select wca.id,wca.").append(daySymbol)
									.append("_id from WorkCalendarAlgorithm wca ")
									.append(" left join VocWorkCalendarParity vwcp on vwcp.id=wca.calendarParity_id")
									.append(" left join VocDayParity vdp on vdp.id=wca.parity_id")	
									.append(" where dtype='WorkCalendarWeekDaysAlgorithm' and pattern_id=:pattern")	
									.append(" and wca.").append(daySymbol).append("_id is not null")
									.append(" and (wca.parity_id is null ")
									.append(" or (vwcp.code='WONTH' and vdp.code=:monthParity)")
									.append(" or (vwcp.code='WEEK' and vdp.code=:weekParity)")
									.append(" or (vwcp.code='DAY' and vdp.code=:dayParity) )") ;
									query = theManager.createNativeQuery(sql.toString())
											.setParameter("pattern", pattern.getId())
											.setParameter("monthParity",parityMonth?"YES":"NO")
											.setParameter("weekParity", parityWeek?"YES":"NO")
											.setParameter("dayParity", parityDay?"YES":"NO");
									System.out.println("----------------WorkCalendarWeekDaysAlgorithm=");
									getParrentDay(aWorkCalendar, dateCur, query) ;
									//System.out.println("----------------WorkCalendarWeekDaysAlgorithm="+listA.size());
								}
							
						//	}
							
						}
					}
				}
			}
			cal1.add(Calendar.DAY_OF_MONTH, 1) ;
			
		}
		
		deleteEmptyCalendarDays(aWorkCalendar, aBeginDate, aFinishDate) ;
		theManager.flush() ;
		theManager.clear() ;
		/*
		while( cal.getTime().compareTo(aPattern.dateTo)<=0) {
			m = m + cal.getTime() ;
			deleteUnMedCasesCalendarTimesOnDay(aPattern.workCalendar, cal.getTime(), aContext.manager) ;	//and	
			var workCalendarDay = createCalendarDay(cal.getTime(), aPattern.workCalendar, aContext.manager) ;
			cal.add(java.util.Calendar.DATE, 1) ;
			
			// время по минутам
			var from = createTimeCal(cal.getTime(), aPattern.defaultTimeFrom) ;
			var to = createTimeCal(cal.getTime(), aPattern.defaultTimeTo) ;
			//throw from+" < "+to;
			var timeCal = java.util.Calendar.getInstance() ;
			timeCal.setTime(from) ;
			while ( timeCal.getTime().compareTo(to)<=0) {
				m = m + " time "+ timeCal.getTime() ;
				var workCalendarTime = createCalendarTime(timeCal.getTime(), workCalendarDay, aContext.manager) ;
				timeCal.add(java.util.Calendar.MINUTE, aPattern.defaultTimeInterval) ;
			}
			if(max--<0) throw "Превышен интервал в 365 дней" ;
		}*/
		
	}
	private boolean getParrentDay(WorkCalendar aWorkCalendar, Date aDate, Query aQuery) {
		List<Object[]> list = aQuery.getResultList() ;
		if (!list.isEmpty()) {
			Object[] objs = list.get(0) ;
			Long dayPattern = ConvertSql.parseLong(objs[1]) ;
			WorkCalendarDayPattern pattern =theManager.find(WorkCalendarDayPattern.class, dayPattern) ;
			WorkCalendarDay day = createCalendarDay(aWorkCalendar, aDate) ;
			LOG.info(new StringBuilder().append("day=").append(day).toString()) ;
			//List<WorkCalendarTime> times = day.getWorkCalendarTimes();
			for (WorkCalendarTimePattern timePattern :pattern.getTimePatterns() ) {
				LOG.info(new StringBuilder().append("timeParrent=").append(timePattern).toString()) ;
				if (timePattern instanceof WorkCalendarTimeExample) {
					
					//WorkCalendarTime time = new WorkCalendarTime() ;
					WorkCalendarTimeExample ex = (WorkCalendarTimeExample) timePattern ;
					createCalendarTime(ex.getCalendarTime(),ex.getReserveType(), day,false);
					//times.add(time) ;
				}
			}
			//day.setWorkCalendarTimes(times) ;
			//theManager.persist(times) ;
			
			return true ;
		}
		return false ;
	}
	/**Создаем календарный день, если он уже есть - возвращаем первый существующий*/
	private WorkCalendarDay createCalendarDay(WorkCalendar aWorkCalendar, Date aDate) {
		LOG.info(new StringBuilder().append("----------++createCalendarDay").toString()) ;
		List<WorkCalendarDay> list = theManager.createQuery("from WorkCalendarDay where workCalendar = :workCalendar and calendarDate = :calendarDate")
			.setParameter("calendarDate", aDate)
			.setParameter("workCalendar", aWorkCalendar)
			.getResultList() ;
		WorkCalendarDay day = null;	
		if(list.isEmpty()) { 
			day = new WorkCalendarDay() ;
			day.setWorkCalendar(aWorkCalendar) ;
			day.setCalendarDate(aDate) ;
			theManager.persist(day) ;
		} else {
			day = list.get(0) ;
		}
		SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy") ;
		LOG.info(new StringBuilder().append("----------+++day=").append(day).append("  ")
				.append(format.format(day.getCalendarDate())).toString()) ;
		return day ;
	}
	
	
	private String getWeekDaySymbol(int aWeekDay) {
		if (aWeekDay==Calendar.MONDAY) return "MONDAY" ;
		if (aWeekDay==Calendar.TUESDAY) return "TUESDAY" ;
		if (aWeekDay==Calendar.WEDNESDAY) return "WEDNESDAY" ;
		if (aWeekDay==Calendar.THURSDAY) return "THURSDAY" ;
		if (aWeekDay==Calendar.FRIDAY) return "FRIDAY" ;
		if (aWeekDay==Calendar.SATURDAY) return "SATURDAY" ;
		if (aWeekDay==Calendar.SUNDAY) return "SUNDAY" ;
		return null ;
	}
	
	private String getOrderWeek(Calendar aCal,int aWeekOfMonth) {
		//int weekNum = aCal.get(Calendar.WEEK_OF_MONTH) ;
		int maxWeekNum = aCal.getActualMaximum(Calendar.WEEK_OF_MONTH) ;
		if (aWeekOfMonth==maxWeekNum) return "LAST" ;
		if (aWeekOfMonth==(maxWeekNum-1)) return "PREVLAST" ;
		return String.valueOf(aWeekOfMonth) ;
	}
	/**Создаем java.util.Date из java.sql.Date + java.sql.Time*/
	public java.util.Date createTimeCal(Date aDate, Time aTime) {
		Calendar cal = java.util.Calendar.getInstance() ;
		cal.setTime(aDate) ;
		
		Calendar timeCal = java.util.Calendar.getInstance() ;
		timeCal.setTime(aTime) ;
		
		cal.set(java.util.Calendar.HOUR_OF_DAY, timeCal.get(java.util.Calendar.HOUR_OF_DAY)) ;
		cal.set(java.util.Calendar.MINUTE, timeCal.get(java.util.Calendar.MINUTE)) ;
		
		return cal.getTime() ;
		
	}
	
	/*
	 * Добавляем новый WorkCalendarTime по времени (aTime) и дню (aWorkCalendarDay)
	 *  Проверяем, не занято ли такое время
	 */
	private Long createCalendarTime(java.sql.Time aTime
			, Long aReserveType
			, WorkCalendarDay aWorkCalendarDay) {
		VocServiceReserveType reserveType=null ;
		if (aReserveType!=null) theManager.find(VocServiceReserveType.class, aReserveType) ;
		return createCalendarTime(aTime
				, reserveType
				, aWorkCalendarDay, true) ;
	}
	private Long createCalendarTime(java.sql.Time aTime
			, VocServiceReserveType aReserveType
			, WorkCalendarDay aWorkCalendarDay, boolean aAddData) {
		LOG.info(new StringBuilder().append("----------++createCalendarTime").toString()) ;
		// проверяем на занятость
		if (aTime== null) return null ;
		List<WorkCalendarTime> list = theManager.createQuery(
			// "from WorkCalendarTime where medCase is null "
			 "from WorkCalendarTime where"
			+" workCalendarDay = :day"
			+" and timeFrom = :timeFrom")
			.setParameter("timeFrom", aTime)
			.setParameter("day", aWorkCalendarDay) 
			.getResultList() ;
		if(list.isEmpty()) { // свободно, создаем новый
			WorkCalendarTime t = new WorkCalendarTime() ;
			t.setWorkCalendarDay(aWorkCalendarDay) ;
			//Time sqlTime = new java.sql.Time(aTime.getTime()) ;
			t.setTimeFrom(aTime) ;
			t.setAdditional(true) ;
			t.setReserveType(aReserveType) ;
			t.setAdditional(aAddData) ;
			t.setCreateDate(new java.sql.Date(new java.util.Date().getTime())) ;
			t.setCreateTime(new java.sql.Time(new java.util.Date().getTime())) ;
			t.setCreateUsername(theContext.getCallerPrincipal().getName()) ;
			theManager.persist(t) ;
			return t.getId() ;
		}	
		return null ;
	}	
	private void  deleteUnMedCasesCalendarTimes(WorkCalendar aCalendar, Date aDateFrom, Date aDateTo) {
		theManager.createNativeQuery(
			    " delete from WorkCalendarTime"
				+" where WorkCalendarTime.workCalendarDay_id in "
				+"      ( select  WorkCalendarDay.id from WorkCalendarDay where  "
		        +"          WorkCalendarDay.workCalendar_id =:cal_id and WorkCalendarDay.calendarDate between :dateFrom and :dateTo and WorkCalendarTime.workCalendarDay_id=WorkCalendarDay.id"
		        +"      )"
		        +"   and WorkCalendarTime.medcase_id is null and WorkCalendarTime.prePatient_id is null and WorkCalendarTime.prePatientInfo is null"
		       // +"   and WorkCalendarTime.id not in (select medcase.timePlan_id from medcase)"
		       )
		        .setParameter("cal_id", aCalendar.getId())
		        .setParameter("dateFrom", aDateFrom)
		        .setParameter("dateTo", aDateTo)
				.executeUpdate() ;
	}
	public void deleteCalendarDaysByWorkFunction(WorkCalendar aCalendar,  Date aDateFrom, Date aDateTo) {
		deleteUnMedCasesCalendarTimes(aCalendar, aDateFrom, aDateTo) ;
		deleteEmptyCalendarDays(aCalendar, aDateFrom, aDateTo) ;
	}
	private void deleteUnMedCasesCalendarTimesOnDay(WorkCalendar aCalendar, WorkCalendarDay aWorkCalendarDay) {
		theManager.createNativeQuery(
			    " delete from WorkCalendarTime"
				+" where WorkCalendarTime.workCalendarDay_id =:day_id "
				+" and WorkCalendarTime.medcase_id is null"
				)
		        .setParameter("day_id", aWorkCalendarDay.getId())
				.executeUpdate() ;
	}

	private void deleteEmptyCalendarDays(WorkCalendar aCalendar, Date aDateFrom, Date aDateTo) {
		LOG.info(new StringBuilder().append("---------->>deleteEmptyCalendarDays").toString()) ;
		theManager.createNativeQuery(
			    "delete from WorkCalendarDay wcd where wcd.workCalendar_id = :cal and wcd.calendarDate between :dateFrom and :dateTo and (select count(*) from WorkCalendarTime wct where wct.workCalendarDay_id=wcd.id)=0"
				)
		        
			.setParameter("cal", aCalendar.getId())
	        .setParameter("dateFrom", aDateFrom)
	        .setParameter("dateTo", aDateTo)
			.executeUpdate() ;
			
			
	}
	/*

function onCreate(aForm, aPattern, aContext) {
	//if(!aPattern.dateFrom)	throw "Нет даты действия С" ;
	//if(!aPattern.dateTo)	throw "Нет даты действия ПО" ;
	*if(aPattern.dateTo.compareTo(aPattern.dateFrom)<0) {
		throw "Дата действия по ["+aPattern.dateTo
			+"] меньше даты действия С ["+aPattern.dateFrom+"]" ;
	}*
	if(aPattern.defaultTimeInterval<1) throw "Интервал рабочих времен должен быть больше нуля" ;
	
	// удаляем старые дни, где нет приемов
	//deleteUnMedCasesCalendarTimes(aPattern.workCalendar, aContext.manager) ; and
	//deleteEmptyCalendarDays(aPattern.workCalendar, aContext.manager) ;
	// создаем
	/
	
	
	//throw m ;
}



	 */

	@EJB ILocalEntityFormService theEntityFormService ;
    @PersistenceContext EntityManager theManager ;
    @Resource SessionContext theContext ;
   }
