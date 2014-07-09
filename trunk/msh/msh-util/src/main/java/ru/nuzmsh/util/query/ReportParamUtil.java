package ru.nuzmsh.util.query;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import ru.nuzmsh.util.format.DateFormat;

public class ReportParamUtil {
	public static String getEmergencySql(String aTypeEmergency, String aFieldSls) {
		String textSql = "" ;
		if (aTypeEmergency!=null && aTypeEmergency.equals("1")) {
			textSql =" and "+aFieldSls+".emergency='1'" ;
		} else if (aTypeEmergency!=null && aTypeEmergency.equals("2")) {
			textSql = " and ("+aFieldSls+".emergency is null or "+aFieldSls+".emergency = '0')" ;
		}
		return textSql ;
	}
	public static String getEmergencyInfo(String aTypeEmergency) {
		String textInfo = "все" ;
		if (aTypeEmergency!=null && aTypeEmergency.equals("1")) {
			textInfo="экстренно" ;
		} else if (aTypeEmergency!=null && aTypeEmergency.equals("2")) {
			textInfo = "планово" ;
		}
		return textInfo ;
	}
	public static String getPeriodByDate(Boolean aIsAnd,String aFieldDate, String aFieldTime, String aDate, String aTime) throws ParseException {
		Date dat = DateFormat.parseDate(aDate) ;
		Calendar cal = Calendar.getInstance() ;
		cal.setTime(dat) ;
		cal.add(Calendar.DAY_OF_MONTH, 1) ;
		SimpleDateFormat format=new SimpleDateFormat("dd.MM.yyyy") ;
		String date1=format.format(cal.getTime()) ;
		return getPeriodByDate(aIsAnd,false, aFieldDate, aFieldTime, aDate, date1, aTime) ;
	}
	public static String getDateTo(Boolean aIsAnd,String aFieldDate, String aFieldTime, String aDate, String aTime) throws ParseException {
		StringBuilder period = new StringBuilder() ;
		if (aIsAnd) period.append(" and ") ;
		if (aTime!=null) {
			period.append(" (").append(aFieldDate).append(" < to_date('").append(aDate).append("','dd.mm.yyyy') ") ;
			period.append(" or ").append(aFieldDate).append(" = to_date('").append(aDate).append("','dd.mm.yyyy') ") ;
			period.append(" and ").append(aFieldTime).append("< cast('").append(aTime).append("' as time)");
			period.append(") ") ;
		}else {
			period.append(aFieldDate).append(" < to_date('").append(aDate).append("','dd.mm.yyyy') ");
		}
		return period.toString() ;
	}
	public static String getDateFrom(Boolean aIsAnd,String aFieldDate, String aFieldTime, String aDate, String aTime) throws ParseException {
		StringBuilder period = new StringBuilder() ;
		if (aIsAnd) period.append(" and ") ;
		if (aTime!=null) {
			period.append(" (").append(aFieldDate).append(" > to_date('").append(aDate).append("','dd.mm.yyyy') ") ;
			period.append(" or ").append(aFieldDate).append(" = to_date('").append(aDate).append("','dd.mm.yyyy') ") ;
			period.append(" and ").append(aFieldTime).append(">= cast('").append(aTime).append("' as time)");
			period.append(") ") ;
		}else {
			period.append(aFieldDate).append(" > to_date('").append(aDate).append("','dd.mm.yyyy') ");
		}
		return period.toString() ;
	}
	public static String getDateFromByDtype(Boolean aIsAnd,String aFieldDate, String aFieldTime, String aDate
			, String aTime, String aDtype,String aFieldDate2, String aFieldTime2) throws ParseException {
		StringBuilder period = new StringBuilder() ;
		if (aIsAnd) period.append(" and ") ;
		if (aTime!=null) {
			/*
			  (sls.dateFinish is null  or  (sls.dateFinish >= to_date('09.07.2014','dd.mm.yyyy')))
  and  sls.dtype='HospitalMedCase'
  and sls.dateStart <= to_date('09.07.2014','dd.mm.yyyy')
  and (sls.dateFinish != to_date('09.07.2014','dd.mm.yyyy') or sls.dateFinish = to_date('09.07.2014','dd.mm.yyyy') and sls.dischargeTime>= cast('08:00' as time)) 
  and  (sls.dateStart != to_date('09.07.2014','dd.mm.yyyy') or sls.dateStart = to_date('09.07.2014','dd.mm.yyyy')  and sls.entranceTime< cast('08:00' as time))  
 
			 */
			period.append("(").append(aFieldDate).append(" is null or ").append(aFieldDate).append(">= to_date('").append(aDate).append("','dd.mm.yyyy') )");
			if (aDtype!=null) period.append(" and ").append(aDtype) ;
			if (aFieldDate2!=null) period.append("(").append(aFieldDate).append("<= to_date('").append(aDate).append("','dd.mm.yyyy') )");
			period.append(" (").append(aFieldDate).append(" != to_date('").append(aDate).append("','dd.mm.yyyy') ") ;
			period.append(" or ").append(aFieldDate).append(" = to_date('").append(aDate).append("','dd.mm.yyyy') ") ;
			period.append(" and ").append(aFieldTime).append(">= cast('").append(aTime).append("' as time)");
			period.append(") ") ;
			if (aFieldDate2!=null) {
				period.append(" (").append(aFieldDate2).append(" != to_date('").append(aDate).append("','dd.mm.yyyy') ") ;
				period.append(" or ").append(aFieldDate2).append(" = to_date('").append(aDate).append("','dd.mm.yyyy') ") ;
				period.append(" and ").append(aFieldTime2).append("< cast('").append(aTime).append("' as time)");
				period.append(") ");
			}
		}else {
			period.append(aFieldDate).append(" > to_date('").append(aDate).append("','dd.mm.yyyy') ");
			if (aDtype!=null) period.append(" and ").append(aDtype) ;
			period.append(aFieldDate2).append(" <= to_date('").append(aDate).append("','dd.mm.yyyy') ");
		}
		return period.toString() ;
	}

    public static String getPeriodByDate(boolean aIsAnd,boolean aIsAddBetween, String aFieldDate, String aFieldTime, String aDate, String aDateNext, String aTime) {
    	StringBuilder period = new StringBuilder() ;
    	if (aIsAnd) period.append(" and ") ;
    	if (aTime==null) {
    		if (!aIsAddBetween) {
	        	period.append(" ").append(aFieldDate).append(" = to_date('").append(aDate).append("','dd.mm.yyyy') ") ;
    		} else  {
	        	period.append(" ").append(aFieldDate).append(" between to_date('").append(aDate).append("','dd.mm.yyyy') and ")
	        		.append(" to_date('").append(aDateNext).append("','dd.mm.yyyy') ") ;
    		}
    	} else {
        	period.append(" ((").append(aFieldDate).append("= to_date('").append(aDate).append("','dd.mm.yyyy') and ")
        	.append(aFieldTime).append(">= cast('").append(aTime).append("' as time) )")
        	.append(" or (").append(aFieldDate).append("= to_date('")
        	.append(aDateNext).append("','dd.mm.yyyy') and ").append(aFieldTime).append("< cast('").append(aTime).append("' as time) )") ;
        	if (aIsAddBetween) {
        		period.append(" ").append(aFieldDate).append(" > to_date('").append(aDate).append("','dd.mm.yyyy') and ")
        			.append(aFieldDate).append(" < to_date('").append(aDateNext).append("','dd.mm.yyyy') ") ;
        	}
        	period.append(")") ;
    	}
    	return period.toString() ;
    	
    }

}
