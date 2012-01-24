package ru.ecom.mis.ejb.service.worker;

import java.sql.Date;
import java.text.ParseException;

import ru.ecom.mis.ejb.domain.workcalendar.WorkCalendar;

public interface IWorkCalendarService {
	// Получить новые времена по специалисту за определенное число
	public String getIntervalBySpecAndDate(String aDate
			, Long aSpecialist) throws ParseException ;
	 public String getTimesBySpecAndDate(String aDate
			, Long aSpecialist, Long aCountVisits
			, String aBeginTime, String aEndTime) throws ParseException;
	// Создать новые времена по специалисту за определенное число
	 public void getCreateNewTimesBySpecAndDate(String aDate
			, Long aSpecialist, String aTimes) throws ParseException;
	 void generateCalendarByWorkFunction(Long aWorkFunction,Date aBeginDate,Date aFinishDate) ;
	 public void deleteCalendarDaysByWorkFunction(WorkCalendar aCalendar,  Date aDateFrom, Date aDateTo);
	 public void addBusyPatternByWorkFunction(Long aWorkFunction,Date aBeginDate,Date aFinishDate, Long aPattern);
}
