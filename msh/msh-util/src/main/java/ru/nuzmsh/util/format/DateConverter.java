package ru.nuzmsh.util.format;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

import ru.nuzmsh.util.StringUtil;

import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;

/**
 * @author esinev
 * Date: 06.03.2006
 * Time: 8:30:58
 */
public class DateConverter {

    private final static Log LOG = LogFactory.getLog(DateConverter.class) ;

    //private static final SimpleDateFormat GLOBALE_DATE_FORMAT_1 = new SimpleDateFormat("yyyyMMdd");
    //private static final SimpleDateFormat GLOBALE_DATE_FORMAT_2 = new SimpleDateFormat("dd.MM.yy");
    //private static final SimpleDateFormat GLOBALE_DATE_FORMAT_3 = new SimpleDateFormat("dd.MM.yyyyy");

    public static Date getDateFromGlobale(String aStr) throws ParseException {
        try {
        	SimpleDateFormat GLOBALE_DATE_FORMAT_1 = new SimpleDateFormat("yyyyMMdd");
            return !StringUtil.isNullOrEmpty(aStr) ? GLOBALE_DATE_FORMAT_1.parse(aStr) : null;
        } catch (Exception e) {
            try {
            	SimpleDateFormat GLOBALE_DATE_FORMAT_2 = new SimpleDateFormat("dd.MM.yy");
                return GLOBALE_DATE_FORMAT_2.parse(aStr);
            } catch (Exception e2) {
                try {SimpleDateFormat GLOBALE_DATE_FORMAT_3 = new SimpleDateFormat("dd.MM.yyyyy");
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

    public static Date createDateTime(Date aDate, String aTime) {
        LOG.debug("aDate = " + aDate);
        LOG.debug("aTime = " + aTime);
        if (aDate != null) {
            if (aTime == null) {
                LOG.debug("1 = " + 1);
                return aDate;
            } else {
                SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
                StringBuffer sb = new StringBuffer(df.format(aDate));
                sb.append(" ");
                sb.append(aTime.replace(':', '.'));
                SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd.MM.yyyy HH.mm");
                try {
                    LOG.debug("sb = " + sb);
                    return dateTimeFormat.parse(sb.toString());
                } catch (ParseException e) {
                    throw new RuntimeException(e.getMessage(), e);
                }
            }
        } else {
            LOG.debug("2 = " + 2);
            return null;
        }
    }
}
