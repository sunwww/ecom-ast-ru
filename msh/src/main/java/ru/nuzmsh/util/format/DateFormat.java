package ru.nuzmsh.util.format;

import ru.nuzmsh.util.StringUtil;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author esinev
 * Date: 06.03.2006
 * Time: 8:35:28
 */
public class DateFormat {
    
    //private static SimpleDateFormat FORMAT_1 = new SimpleDateFormat("dd.MM.yyyy");

    //private static SimpleDateFormat FORMAT_LOGIC = new SimpleDateFormat("yyyyMMdd");
    //private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH.mm");
	/** Преобразование даты в произвольный формат */
public static String convertOtherFormat(String aDate, String aFromFormat, String aToFormat)  throws ParseException {
	SimpleDateFormat fromFormat = new SimpleDateFormat(aFromFormat);
	SimpleDateFormat toFormat = new SimpleDateFormat(aToFormat);
	return toFormat.format(fromFormat.parse(aDate));
}
    /** Проверка дня (строка в формате 25.03.2018) - является ли воскресеньем*/
	public static Boolean isHoliday (String aDate) throws ParseException {
    		return isHoliday(parseDate(aDate), false);
    }

    /** Проверка дня (строка в формате 25.03.2018) - является ли воскресеньем*/
	public static Boolean isHoliday (Date aDate, boolean isSaturdayHoliday) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(aDate);
		int day = cal.get(java.util.Calendar.DAY_OF_WEEK);
		return day==Calendar.SUNDAY || isSaturdayHoliday && day==Calendar.SATURDAY;
	}
	/** Из строк 2018-03-25, 12:34 в java.util.Date */
    public static Date formatDateFromDateTime(String aSqlDate, String aSqlTime) {
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:MI");
    	try{
    		return format.parse(aSqlDate+" "+aSqlTime);
		} catch (Exception e){e.printStackTrace();return null;}
	}
	/** Из java.util.Date в 20180325 */
	public static String formatToLogic(Date aDate) throws ParseException {
    	SimpleDateFormat FORMAT_LOGIC = new SimpleDateFormat("yyyyMMdd");
        return FORMAT_LOGIC.format(aDate) ;
    }
	/** Из строки 25.03.2018 в 2018-03-25 */
    public static String formatToJDBC(String aDate) throws ParseException {
    	SimpleDateFormat FORMAT_LOGIC = new SimpleDateFormat("dd.MM.yyyy");
    	SimpleDateFormat FORMAT_JDBC = new SimpleDateFormat("yyyy-MM-dd");
    	Date date =FORMAT_LOGIC.parse(aDate) ;
    	return FORMAT_JDBC.format(date) ;
    }
	/** Из 2018-03-25 в 25.03.2018	*/
	public static String formatFromJDBC(String aDate) throws ParseException {
		SimpleDateFormat FORMAT_LOGIC = new SimpleDateFormat("dd.MM.yyyy");
		SimpleDateFormat FORMAT_JDBC = new SimpleDateFormat("yyyy-MM-dd");
		Date date =FORMAT_JDBC.parse(aDate) ;
		return FORMAT_LOGIC.format(date) ;
	}

	/** Из строки 20180325 в java.util.Date */
    public static Date parseFromLogic(String aDate) throws ParseException {
    	SimpleDateFormat FORMAT_LOGIC = new SimpleDateFormat("yyyyMMdd");
        return FORMAT_LOGIC.parse(aDate) ;
    }
	/** Из строки 25.03.2018 в java.sql.Date */
    public static java.sql.Date parseSqlDate(String aDate) throws ParseException {
        Date utilDate = parseDate(aDate) ;
        return utilDate != null ? new java.sql.Date(utilDate.getTime()) : null;
    }
	/** Из строки в заданном формате в java.util.Date * ограничение на минимальную дату */
    public static Date parseDate(String aDate,String aFormat) throws ParseException {
    	SimpleDateFormat FORMAT_1 = new SimpleDateFormat(aFormat);
    	Date ret ;
    	if(StringUtil.isNullOrEmpty(aDate)) {
    		ret = null ;
    	} else {
    		if(aDate.indexOf(',')>=0) aDate = aDate.replace(',', '.') ;
    		ret =  FORMAT_1.parse(aDate) ;
    	}
    	if(ret!=null) {
    		final Date minDate = new Date(-3786836400000L) ;
    		if(ret.before(minDate)) {
    			Calendar cal = Calendar.getInstance() ;
    			cal.setTime(ret);
    			if(cal.get(Calendar.YEAR)<35) {
    				cal.add(Calendar.YEAR, 2000) ;
    			} else if(cal.get(Calendar.YEAR)<100) {
    				cal.add(Calendar.YEAR, 1900) ;
    			}
    			ret = cal.getTime() ;
    		}
    		if(ret.before(minDate)) {
    			throw new IllegalArgumentException("Дата "+FORMAT_1.format(ret)+" не должна быть меньше "+FORMAT_1.format(minDate)) ;
    		}
    	}
    	return ret ;
    }
	/** Из строки в заданном формате в java.sql.Date * ограничение на минимальную дату */
    public static java.sql.Date parseSqlDate(String aDate,String aFormat) throws ParseException {
    	SimpleDateFormat FORMAT_1 = new SimpleDateFormat(aFormat);
        Date ret ;
        if(StringUtil.isNullOrEmpty(aDate)) {
            ret = null ;
        } else {
            if(aDate.indexOf(',')>=0) aDate = aDate.replace(',', '.') ;
            ret =  FORMAT_1.parse(aDate) ;
        }
	if(ret!=null) {
		final Date minDate = new Date(-3786836400000L) ;
		if(ret.before(minDate)) {
			Calendar cal = Calendar.getInstance() ;
			cal.setTime(ret);
			if(cal.get(Calendar.YEAR)<35) {
				cal.add(Calendar.YEAR, 2000) ;
			} else if(cal.get(Calendar.YEAR)<100) {
				cal.add(Calendar.YEAR, 1900) ;
			}
			ret = cal.getTime() ;
			}
			if(ret.before(minDate)) {
			    throw new IllegalArgumentException("Дата "+FORMAT_1.format(ret)+" не должна быть меньше "+FORMAT_1.format(minDate)) ;
			}
		}
        return ret !=null ? new java.sql.Date(ret.getTime()) : null ;
    }

	/** Из строки 25.03.2018 в java.util.Date */
    public static Date parseDate(String aDate) throws ParseException {
        return parseDate(aDate,"dd.MM.yyyy") ;
    }
	/** Из java.util.Date в 25.03.2018*/
    public static String formatToDate(Date aDate) {
    	SimpleDateFormat FORMAT_1 = new SimpleDateFormat("dd.MM.yyyy");
        return aDate!=null ? FORMAT_1.format(aDate) : null;
    }
	/** Из java.sql.Date в 25.03.2018*/
    public static String formatToDate(java.sql.Date aDate) {
    	SimpleDateFormat FORMAT_1 = new SimpleDateFormat("dd.MM.yyyy");
        return aDate!=null ? FORMAT_1.format(aDate) : null;
    }

    public static String parseTime(String aTimeString) throws ParseException {
        String ret ;
        if(StringUtil.isNullOrEmpty(aTimeString)) {
            ret = null ;
        } else {
            if(aTimeString.indexOf(',')>=0) 
            	aTimeString = aTimeString.replace(',', '.') ;
            if(aTimeString.indexOf(':')>=0) 
            	aTimeString = aTimeString.replace(':', '.') ;
            
            SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH.mm");
            TIME_FORMAT.parse(aTimeString) ;
            ret = aTimeString ;
        }
        return ret ;
    }
    public static Time parseSqlTime(String aTimeString) throws ParseException {
        Time ret;
        if(StringUtil.isNullOrEmpty(aTimeString)) {
            ret = null;
        } else {
            SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm");
            Date d = TIME_FORMAT.parse(aTimeString) ;
            ret = new java.sql.Time (d.getTime());
        }
        return ret ;
    }
   
   
    public static String formatToTime(Time aTime) {
        return aTime!=null ? new SimpleDateFormat("HH:mm").format(aTime) : null;
    }

    public static String formatToDateTime(Date aDateTime){
    	SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm");
    	return format.format(aDateTime);
	}
}
