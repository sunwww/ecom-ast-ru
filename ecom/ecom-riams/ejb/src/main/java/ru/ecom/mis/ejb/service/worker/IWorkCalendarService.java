package ru.ecom.mis.ejb.service.worker;

import org.json.JSONException;
import ru.ecom.mis.ejb.domain.workcalendar.WorkCalendar;

import java.sql.Date;
import java.text.ParseException;

public interface IWorkCalendarService {
	String getFreeCalendarTimeForWorkFunction (Long aWorkFunctionId, String aCalendarDay, String aUsername) throws ParseException, JSONException;
	void autoGenerateCalendar(Long aCnt,Long aCntDayGererate) ;
	void deleteWorkCalendarTime(Long aTime) ;
	String deletePreRecord(String aUsername,Long aTime) ;
	// Удалить свободные времена
	void deleteEmptyCalendarDays(Long aWorkFunction, Date aDateFrom, Date aDateTo);
	// Переместить времена с одной даты на другую
	void moveDate(Long aWorkFunction, Date aDateFrom, Date aDateTo);
	void moveSpecialist(Long aWorkFunctionMove, Long aWorkFunctionOrig, Date aDateFrom, Date aDateTo);
	// Установить нерабочее время
	void addNotBusyPattern(Long aWorkFunction,Date aDateFrom, Date aDateTo, Long aReason) ;
	// Получить новые времена по специалисту за определенное число
	String getIntervalBySpecAndDate(String aDate
			, Long aSpecialist) throws ParseException ;
	 String getTimesBySpecAndDate(String aDate
			, Long aSpecialist, Long aCountVisits
			, String aBeginTime, String aEndTime) throws ParseException;
	// Создать новые времена по специалисту за определенное число
	 void getCreateNewTimesBySpecAndDate(Date aDate
			, Long aSpecialist, String aTimes,Long aReserveType) throws ParseException;
	 void generateCalendarByWorkFunction(Long aWorkFunction,Date aBeginDate,Date aFinishDate) ;
	 void deleteCalendarDaysByWorkFunction(WorkCalendar aCalendar,  Date aDateFrom, Date aDateTo);
	 void addBusyPatternByWorkFunction(Long aWorkFunction,Date aBeginDate,Date aFinishDate, Long aPattern);
	 void preRecordByPatient(String aUsername,Long aFunction, Long aSpecialist, Long aDay, Long aTime
				,String aPatientInfo,Long aPatientId,Long aServiceStream,String aPhone,Long aService, Long preWayOfRecord) ;
	 String preRecordByPatient(String aUsername,Long aTime
				,String aPatientInfo,Long aPatientId, Long preWayOfRecord);
	 void recordByPatient(String aUsername, Long aFunction, Long aSpecialist, Long aDay, Long aTime
				,String aPatientInfo,Long aPatientId, Long aOrderLpu, Long preWayOfRecord) ;
	 String addCreateNewTimeBySpecAndDate(String aDate
				, Long aSpecialist, String aTime,Long aReserveType) throws ParseException ;
}
