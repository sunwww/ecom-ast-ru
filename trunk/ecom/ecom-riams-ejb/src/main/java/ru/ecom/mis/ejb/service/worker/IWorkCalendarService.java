package ru.ecom.mis.ejb.service.worker;

import java.sql.Date;

import ru.ecom.mis.ejb.domain.workcalendar.WorkCalendar;

public interface IWorkCalendarService {
	 void generateCalendarByWorkFunction(Long aWorkFunction,Date aBeginDate,Date aFinishDate) ;
	 public void deleteCalendarDaysByWorkFunction(WorkCalendar aCalendar,  Date aDateFrom, Date aDateTo);
	 public void addBusyPatternByWorkFunction(Long aWorkFunction,Date aBeginDate,Date aFinishDate, Long aPattern);
}
