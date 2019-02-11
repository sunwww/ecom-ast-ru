package ru.ecom.mis.ejb.service.extdisp;

import javax.naming.NamingException;
import java.sql.Date;
import java.text.ParseException;

public interface IExtDispService {
	String exportOrph(Date aStartDate, Date aFinishDate, String aFileNameSuffix, String aSqlAdd, int aFizGroup, int aHeight, int aWeight, int aHeadSize
			, String aAnalysesText, String aZOJReccomend, String aReccomend, int divideNum, Long aLpu ) throws ParseException, NamingException ;
	String setOrphCodes() throws NamingException;
}
