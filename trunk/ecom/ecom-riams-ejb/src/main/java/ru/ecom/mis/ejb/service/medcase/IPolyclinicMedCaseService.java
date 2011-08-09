package ru.ecom.mis.ejb.service.medcase;

import java.sql.Date;
import java.text.ParseException;

public interface IPolyclinicMedCaseService {
	public String getInfoDay(String aDate) ;
	public Long getSecUser() ;
	public Long getWorkFunction(Long SecUser) ;
	public Long getWorkFunction() ;
	public Long getWorkCalendar(Long aWorkFunction) ;
	public String getWorkCalendarDay(Long aWorkCalendar,Long aWorkFunction, String aCalendarDate) throws ParseException ;
	public String getFioBySpec() ;
}
