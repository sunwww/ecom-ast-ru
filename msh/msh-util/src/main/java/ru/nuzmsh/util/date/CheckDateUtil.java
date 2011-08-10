package ru.nuzmsh.util.date;

import ru.nuzmsh.util.format.DateFormat;

import java.text.ParseException;
import java.util.Date;
import java.util.Calendar;

/**
 * Проверка дат
 */
public class CheckDateUtil {

    /**
     *
     * @param aBirthDate Формат даты dd.MM.yyyyy
     * @param aAge
     * @throws ParseException
     */
    public static void checkBirthDay(String aBirthDate, int aAge) throws ParseException {
        checkBirthDay(DateFormat.parseDate(aBirthDate), aAge) ;
    }

    public static void checkBirthDay(Date aBirthDate, int aAge) throws IllegalArgumentException {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.YEAR, -aAge);
        System.out.println("cal = " + cal.getTime());
        if(cal.getTime().getTime()>aBirthDate.getTime()) {
            throw new IllegalArgumentException("Возраст не может быть больше "+aAge+" лет") ;
        } else if(aBirthDate.getTime()>date.getTime()) {
            throw new IllegalArgumentException("Дата рождения должна быть раньше текущей даты") ;
        }
    }

    public static void main(String[] args) throws ParseException {
          checkBirthDay("12.12.1915",90);
    }

}
