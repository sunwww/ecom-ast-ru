package ru.ecom.mis.ejb.service.medcase;

import java.text.ParseException;

public interface IPolyclinicMedCaseService {
	Long getSecUser() ;
	Long getWorkFunction() ;
	Long getWorkCalendar(Long aWorkFunction) ;
	String getWorkCalendarDay(Long aWorkCalendar,Long aWorkFunction, String aCalendarDate) throws ParseException ;
	String getFioBySpec() ;
}
