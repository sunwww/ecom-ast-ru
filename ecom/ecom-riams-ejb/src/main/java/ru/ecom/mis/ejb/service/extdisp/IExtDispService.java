package ru.ecom.mis.ejb.service.extdisp;

import java.sql.Date;
import java.text.ParseException;

import javax.naming.NamingException;

public interface IExtDispService {
	public String exportOrph(String aStartDate, String aFinishDate, String aFileNameSuffix, String aSqlAdd, String aFizGroup, String aHeight, String aWeight, String aHeadSize, String aAnalysesText, String aZOJReccomend, String aReccomend, String divideNum, String aLpu ) throws ParseException, NamingException ;
	public String exportOrphDefaultValues(String aStartDate, String aFinishDate, String aFileNameSuffix, String aSqlAdd) throws ParseException,NamingException ;
	public String getBadCards();
	public String setOrphCodes() throws NamingException;
}
