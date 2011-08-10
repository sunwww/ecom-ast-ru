package ru.nuzmsh.util.date;

import java.util.Date;
import java.util.Calendar;
import java.text.SimpleDateFormat;

/**
 * Работа с периодами
 */
public class PeriodFormatUtil {

    /**
     * Создает текст:
     * с 20 по 26 ноября 2005 года
     * или
     * с 25 ноября по 10 декабря 2005 года
     * или
     * с 25 декабря 2005 по 10 января 2006 года
     */
    public static String createFromToText(Date aFrom, Date aTo) {
        Calendar calFrom = Calendar.getInstance();
        calFrom.setTime(aFrom);

        Calendar calTo = Calendar.getInstance();
        calTo.setTime(aTo);

        StringBuffer sb = new StringBuffer(" с ");
        // с 20 по 26 ноября 2005 года
        if (calFrom.get(Calendar.MONTH) == calTo.get(Calendar.MONTH)
                && calFrom.get(Calendar.YEAR) == calTo.get(Calendar.YEAR)) {
            sb.append(calFrom.get(Calendar.DAY_OF_MONTH));
            sb.append(" по ");
            sb.append(calTo.get(Calendar.DAY_OF_MONTH));
            sb.append(' ');
            sb.append(getMonthName(aTo));
            sb.append(' ');
            SimpleDateFormat YEAR_FORMAT = new SimpleDateFormat("yyyy");
            sb.append(YEAR_FORMAT.format(aTo));
            sb.append(" года");
            // с 25 ноября по 10 декабря 2005 года
        } else if (calFrom.get(Calendar.YEAR) == calTo.get(Calendar.YEAR)) {
        	SimpleDateFormat DAY_FORMAT = new SimpleDateFormat("d");
            sb.append(DAY_FORMAT.format(aFrom));
            sb.append(' ');
            sb.append(getMonthName(aFrom));
            sb.append(" по ");
            formatD1M5Y4(calTo, sb);
            sb.append(" года");
        } else {
            formatD1M5Y4(calFrom, sb);
            sb.append(" по ");
            formatD1M5Y4(calTo, sb);
            sb.append(" года");
        }
        return sb.toString();
    }

    private static void formatD1M5Y4(Calendar aCalendar, StringBuffer aSb) {
        aSb.append(aCalendar.get(Calendar.DAY_OF_MONTH));
        aSb.append(' ');
        aSb.append(MONTHS[aCalendar.get(Calendar.MONTH)]);
        aSb.append(' ');
        aSb.append(aCalendar.get(Calendar.YEAR));

    }

    private final static String getMonthName(Date aDate) {
        Calendar calFrom = Calendar.getInstance();
        calFrom.setTime(aDate);
        return MONTHS[calFrom.get(Calendar.MONTH)];
    }

    private static final String[] MONTHS = {"января", "февраля"
            , "марта", "апреля", "мая"
            , "июня", "июля", "августа"
            , "сентября", "октября", "ноября"
            , "декабря"
    };

    //private static final SimpleDateFormat DAY_FORMAT = new SimpleDateFormat("d");
    //private static final SimpleDateFormat YEAR_FORMAT = new SimpleDateFormat("yyyy");
//    private static final SimpleDateFormat DAY_MONTH_YEAR_FORMAT = new SimpleDateFormat("d MMMMMMM yyyy");

}
