package ru.ecom.mis.ejb.service.extdisp;

import java.sql.Date;
import java.text.ParseException;

import javax.naming.NamingException;

public interface IExtDispService {
	public String exportOrph (String aStartDate, String aFinishDate, String aFileNameSuffix ) throws ParseException, NamingException ;
	public String getErrorText ()throws ParseException, NamingException;
}
