package ru.ecom.mis.ejb.service.worker;

import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.SimpleFormatter;

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

import ru.ecom.ejb.services.entityform.ILocalEntityFormService;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.ecom.mis.ejb.domain.workcalendar.WorkCalendar;
import ru.ecom.mis.ejb.domain.workcalendar.WorkCalendarAlgorithm;
import ru.ecom.mis.ejb.domain.workcalendar.WorkCalendarDay;
import ru.ecom.mis.ejb.domain.workcalendar.WorkCalendarDayPattern;
import ru.ecom.mis.ejb.domain.workcalendar.WorkCalendarPattern;
import ru.ecom.mis.ejb.domain.workcalendar.WorkCalendarTime;
import ru.ecom.mis.ejb.domain.workcalendar.WorkCalendarTimeExample;
import ru.ecom.mis.ejb.domain.workcalendar.WorkCalendarTimePattern;
import ru.ecom.mis.ejb.domain.worker.JournalPatternCalendar;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.ecom.mis.ejb.domain.worker.Worker;
import ru.ecom.mis.ejb.form.workcalendar.WorkCalendarPatternBySpecialistForm;
import ru.ecom.mis.ejb.service.lpu.LpuServiceBean;
import ru.nuzmsh.util.format.DateFormat;


@Stateless
@Local(IWorkCalendarService.class)
@Remote(IWorkCalendarService.class)
@SuppressWarnings("unchecked")
public class WorkCalendarServiceBean implements IWorkCalendarService{
	
	private final static Logger LOG = Logger.getLogger(WorkCalendarServiceBean.class);
	private final static boolean CAN_DEBUG = LOG.isDebugEnabled();
	
	public void clearOldData(Long aLpu, Date aDateFrom, Date aDateTo) {
		MisLpu lpu = theManager.find(MisLpu.class, aLpu)  ;
		for (Worker w : lpu.getWorker()) {
			
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
			.append(" and dateFrom>=:dateFrom and dateTo<=:dateTo") ;
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
		theManager.refresh(jpcnew) ;
		long calLTo = calTo.getTime().getTime() ;
		long calLFrom = calFrom.getTime().getTime() ;
		SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy") ;
		System.out.println("---------calFrom="+format.format(calFrom.getTime())) ;
		System.out.println("---------calTo="+format.format(calTo.getTime())) ;
		calTo.add(Calendar.DAY_OF_MONTH, 1) ;
		calFrom.add(Calendar.DAY_OF_MONTH, -1) ;		
		for (JournalPatternCalendar jpc: list) {
			Calendar calFrom1 = Calendar.getInstance() ;
			calFrom1.setTime(jpc.getDateFrom()) ;
			Calendar calTo1 = Calendar.getInstance() ;
			calTo1.setTime(jpc.getDateTo()) ;
			long calLTo1 = calTo1.getTime().getTime() ;
			long calLFrom1 = calFrom1.getTime().getTime() ;
			
			//calTo1.add(Calendar.DAY_OF_MONTH, 1) ;
			System.out.println("----------------------------------------------------------") ;
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
			if (calFrom1.after(calFrom) && (calTo1.after(calTo) )) {
				System.out.println("--------------++++++--1-") ;
				
				jpc.setDateFrom(new Date(calTo.getTime().getTime())) ;
			} else if (calFrom.after(calFrom1) && (calTo.after(calTo1) )) {
				System.out.println("--------------++++++--2-") ;
				//calFrom1.add(Calendar.DAY_OF_MONTH, -1) ;
				jpc.setDateTo(new Date(calFrom.getTime().getTime())) ;
			} else if ((calFrom1.after(calFrom)) && (calTo.after(calTo1))) {
				System.out.println("--------------++++++--3-") ;
				//calTo1.setTime(jpc.getDateTo()) ;
				jpc.setNoActive(Boolean.TRUE); 
				
			} else if (calFrom1.before(calFrom) && calTo1.before(calTo)) {
				System.out.println("--------------++++++--4-") ;

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
			System.out.println("-----res noado="+jpc.getNoActive()) ;
			theManager.persist(jpc) ;
			//theManager.refresh(jpc) ;
			System.out.println("-----res noalast="+jpc.getNoActive()) ;
			System.out.println("-----res from="+format.format(jpc.getDateFrom())) ;
			System.out.println("-----res to="+format.format(jpc.getDateTo())) ;
		}
	}
	public void addNotBusyPattern(Long aWorkCalendar,Date aDateFrom, Date aDateTo, Long aBusy) {
		
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
			System.out.println("---------------cal1="+format.format(cal1.getTime())) ;
			//int weekYear =cal1.get(Calendar.WEEK_OF_YEAR) ;
			//boolean parityYear= (weekYear%2==1)?true:false ;
			int weekMonth =cal1.get(Calendar.WEEK_OF_MONTH) ;
			boolean parityWeek= (weekMonth%2==1)?true:false ;
			int weekDayV =cal1.get(Calendar.DAY_OF_WEEK);
			int weekDay =cal1.get(Calendar.DAY_OF_WEEK) -1;
			if (weekDay==0) weekDay=7;
			int monthDay =cal1.get(Calendar.DAY_OF_MONTH) ;
			boolean parityDay= (monthDay%2==1)?true:false ;
			
			//System.out.println("--------------->calFrom="+format.format(calFrom1.getTime())) ;
			//System.out.println("--------------->calTo="+format.format(calTo1.getTime())) ;
			//System.out.println("--------------->calTo after calFrom="+calTo1.after(calFrom1)) ;
			// Определение действующего журнала
			boolean isNewJour = false;
			if (jpc==null || !calTo1.after(cal1)) {
				System.out.println("<<<<<<--------------->calTo after calFrom="+calTo1.after(calFrom1)) ;
				isNewJour=true ;
			}
			if (isNewJour) {
				List<JournalPatternCalendar> list1 = theManager
					.createQuery("from JournalPatternCalendar where workCalendar=:cal and (NoActive is null or cast(NoActive as integer)=0) and dateFrom<:dateCal and (dateTo is null or dateTo>:dateCal) order by dateFrom")
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
			
			
			

			if (jpc==null) {
				
			} else {
				System.out.println("------------<>----jpc="+jpc) ;
				System.out.println("------------<>----jpc id="+jpc.getId()) ;
				System.out.println("------------<>----jpc from="+jpc.getDateFrom()) ;
				System.out.println("------------<>----jpc to="+jpc.getDateTo()) ;
				String weekDaySt = String.valueOf(weekDay) ;
				String monthOrder = getOrderWeek(cal1,weekMonth);
				WorkCalendarPattern pattern = jpc.getPattern() ;
				if (pattern!=null) {
					// Поиск алгоритма по профдню WorkCalendarProphDayAlgorithm
					StringBuilder sql = new StringBuilder() ;
					
					sql.append("select wca.id,wca.dayPattern_id from WorkCalendarAlgorithm wca ").append(" left join VocWeekMonthOrder vwmo on vwmo.id=wca.monthOrder_id").append(" left join VocWeekDay vwd on vwd.id=wca.weekDay_id").append(" where dtype='WorkCalendarProphDayAlgorithm' and pattern_id=:pattern").append(" and (wca.monthDay=:monthDay or vwmo.code=:monthOrder").append(" and vwd.code=:weekDay)") ;
					Query query = theManager.createNativeQuery(sql.toString()).setParameter("pattern", pattern.getId()).setParameter("monthDay", monthDay).setParameter("monthOrder", monthOrder).setParameter("weekDay", weekDaySt) ;
					if (query.getResultList().isEmpty()) {
						// Поиск алгоритма по датам WorkCalendarDatesAlgorithm
						sql = new StringBuilder() ;
						boolean nextSearch =true ;
						Date dateCur = new Date(cal1.getTime().getTime()) ;
						sql.append("select wca.id,wca.dayPattern_id from WorkCalendarAlgorithm wca ").append(" where wca.dtype='WorkCalendarDatesAlgorithm' and pattern_id=:pattern").append(" and :day between wca.dateFrom and wca.dateTo") ;
						query = theManager.createNativeQuery(sql.toString()).setParameter("pattern", pattern.getId()).setParameter("day", cal1.getTime());
						System.out.println("----------------WorkCalendarDatesAlgorithm=");
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
							.append(" or (vwcp.code='WEEK' and vdp.code=:weekParity)")
							.append(" or (vwcp.code='DAY' and vdp.code=:dayParity) )") ;
							query = theManager.createNativeQuery(sql.toString()).setParameter("pattern", pattern.getId()).setParameter("dayBy", weekDay).setParameter("weekParity", parityWeek?"YES":"NO").setParameter("dayParity", parityDay?"YES":"NO");
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
								.append(" or (vwcp.code='WEEK' and vdp.code=:weekParity)")
								.append(" or (vwcp.code='DAY' and vdp.code=:dayParity) )") ;
								query = theManager.createNativeQuery(sql.toString()).setParameter("pattern", pattern.getId()).setParameter("weekParity", parityWeek?"YES":"NO").setParameter("dayParity", parityDay?"YES":"NO");
								System.out.println("----------------WorkCalendarWeekDaysAlgorithm=");
								getParrentDay(aWorkCalendar, dateCur, query) ;
								//System.out.println("----------------WorkCalendarWeekDaysAlgorithm="+listA.size());
							}
						
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
			Long dayPattern = WorkerServiceBean.parseLong(objs[1]) ;
			WorkCalendarDayPattern pattern =theManager.find(WorkCalendarDayPattern.class, dayPattern) ;
			WorkCalendarDay day = createCalendarDay(aWorkCalendar, aDate) ;
			LOG.info(new StringBuilder().append("day=").append(day).toString()) ;
			//List<WorkCalendarTime> times = day.getWorkCalendarTimes();
			for (WorkCalendarTimePattern timePattern :pattern.getTimePatterns() ) {
				LOG.info(new StringBuilder().append("timeParrent=").append(timePattern).toString()) ;
				if (timePattern instanceof WorkCalendarTimeExample) {
					
					//WorkCalendarTime time = new WorkCalendarTime() ;
					WorkCalendarTimeExample ex = (WorkCalendarTimeExample) timePattern ;
					createCalendarTime(ex.getCalendarTime(), day);
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
	private void createCalendarTime(java.sql.Time aTime, WorkCalendarDay aWorkCalendarDay) {
		LOG.info(new StringBuilder().append("----------++createCalendarTime").toString()) ;
		// проверяем на занятость
		if (aTime== null) return ;
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
			theManager.persist(t) ;
		}	
		
		
	}	
	private void  deleteUnMedCasesCalendarTimes(WorkCalendar aCalendar, Date aDateFrom, Date aDateTo) {
		theManager.createNativeQuery(
			    " delete from WorkCalendarTime"
				+" where WorkCalendarTime.workCalendarDay_id in "
				+"      ( select  WorkCalendarDay.id from WorkCalendarDay where  "
		        +"          WorkCalendarDay.workCalendar_id =:cal_id and WorkCalendarDay.calendarDate between :dateFrom and :dateTo and WorkCalendarTime.workCalendarDay_id=WorkCalendarDay.id"
		        +"      )"
		        +"   and WorkCalendarTime.medcase_id is null"
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
