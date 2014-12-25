package ru.ecom.mis.ejb.service.extdisp;

import java.sql.Date;
import java.text.ParseException;

import javax.naming.NamingException;

public interface IExtDispService {
	public String exportOrph (String aStartDate, String aFinishDate, String aFileNameSuffix, String aSqlAdd, String aFizGroup, String aHeight, String aWeight, String aHeadSize, String aAnalysesText, String aZOJReccomend, String aReccomend ) throws ParseException, NamingException ;
	public String getErrorText ()throws ParseException, NamingException;
	public String getBadCards ();
}
