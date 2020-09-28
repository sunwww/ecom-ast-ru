package ru.nuzmsh.util.format;

import org.apache.log4j.Logger;
import ru.nuzmsh.util.StringUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author esinev
 * Date: 06.03.2006
 * Time: 8:30:58
 */
public class DateConverter {

    private static final Logger LOG = Logger.getLogger(DateConverter.class) ;

    public static Date getDateFromGlobale(String aStr) throws ParseException {
        try {
        	SimpleDateFormat GLOBALE_DATE_FORMAT_1 = new SimpleDateFormat("yyyyMMdd");
            return !StringUtil.isNullOrEmpty(aStr) ? GLOBALE_DATE_FORMAT_1.parse(aStr) : null;
        } catch (Exception e) {
            try {
            	SimpleDateFormat GLOBALE_DATE_FORMAT_2 = new SimpleDateFormat("dd.MM.yy");
                return GLOBALE_DATE_FORMAT_2.parse(aStr);
            } catch (Exception e2) {
                try {SimpleDateFormat GLOBALE_DATE_FORMAT_3 = new SimpleDateFormat("dd.MM.yyyy");
                    return GLOBALE_DATE_FORMAT_3.parse(aStr);
                } catch (Exception e3) {
                    LOG.error("Ошибка преобразования даты: {" +aStr+"}", e3);
                    return null ;
                }
            }
        }
    }

    public static Date createDateTime(String aDate, String aTime) throws ParseException {
        return createDateTime(DateFormat.parseDate(aDate), aTime) ;
    }
    public static Date createDateTime(java.sql.Date aDate,java.sql.Time aTime) throws ParseException {
    	SimpleDateFormat GLOBALE_DATE_FORMAT_2 = new SimpleDateFormat("dd.MM.yyyy");
    	SimpleDateFormat GLOBALE_TIME_FORMAT_2 = new SimpleDateFormat("HH.mm");
    	SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd.MM.yyyy HH.mm");
    	StringBuilder sb = new StringBuilder() ;
    	sb.append(GLOBALE_DATE_FORMAT_2.format(aDate)).append(" ").append(GLOBALE_TIME_FORMAT_2.format(aTime)) ;
    	try {
            return dateTimeFormat.parse(sb.toString());
        } catch (ParseException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    	
    }

    public static Date createDateTime(Date aDate, String aTime) {
        if (aDate != null) {
            if (aTime == null) {
                return aDate;
            } else {
                SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
                StringBuilder sb = new StringBuilder(df.format(aDate));
                sb.append(" ");
                sb.append(aTime.replace(':', '.'));
                SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd.MM.yyyy HH.mm");
                try {
                    return dateTimeFormat.parse(sb.toString());
                } catch (ParseException e) {
                    throw new RuntimeException(e.getMessage(), e);
                }
            }
        } else {
            return null;
        }
    }
}
