package ru.ecom.mis.ejb.service.worker;

import java.sql.Date;
import java.text.ParseException;

import ru.ecom.mis.ejb.domain.workcalendar.WorkCalendar;

public interface IWorkCalendarService {
	public void deleteWorkCalendarTime(Long aTime) ;
	public String deletePreRecord(String aUsername,Long aTime) ;
	// Удалить свободные времена
	public void deleteEmptyCalendarDays(Long aWorkFunction, Date aDateFrom, Date aDateTo);
	// Переместить времена с одной даты на другую
	public void moveDate(Long aWorkFunction, Date aDateFrom, Date aDateTo);
	public void moveSpecialist(Long aWorkFunctionMove, Long aWorkFunctionOrig, Date aDateFrom, Date aDateTo);
	// Установить нерабочее время
	public void addNotBusyPattern(Long aWorkFunction,Date aDateFrom, Date aDateTo, Long aReason) ;
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
	 public void preRecordByPatient(String aUsername,Long aFunction, Long aSpecialist, Long aDay, Long aTime
				,String aPatientInfo,Long aPatientId) ;
	 public String preRecordByPatient(String aUsername,Long aTime
				,String aPatientInfo,Long aPatientId);
	 public String addCreateNewTimeBySpecAndDate(String aDate
				, Long aSpecialist, String aTime) throws ParseException ;
}
