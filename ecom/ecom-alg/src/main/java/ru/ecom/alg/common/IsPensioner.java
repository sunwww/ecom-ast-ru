package ru.ecom.alg.common;

import java.util.Date;
import java.util.Calendar;

/**
 * Ребенок или взрослый
 */
public class IsPensioner {

    /**
     * Ребенок?
     * @param aBirthDate      дата рождения
     * @param aAdmissionDate  дата поступления
     * @param aIsMan          мужчина
     * @return true, если пенсионер
     */
    public boolean isPensioner(Date aBirthDate, Date aAdmissionDate, boolean aIsMan) {
        if(aBirthDate==null) throw new IllegalArgumentException("Нет даты рождения") ;
        if(aAdmissionDate==null) throw new IllegalArgumentException("Нет даты поступления") ;
        Calendar cal = Calendar.getInstance();
        final int PENS_AGE = aIsMan ? 60 : 55 ;
        cal.setTime(aAdmissionDate);
        cal.add(Calendar.YEAR, -18);
        cal.add(Calendar.DAY_OF_MONTH, 1);
        return aBirthDate.getTime() > cal.getTime().getTime();
    }

    public boolean isChild55(Date aBirthDate, Date aAdmissionDate) {
        if(aBirthDate==null) throw new IllegalArgumentException("Нет даты рождения") ;
        if(aAdmissionDate==null) throw new IllegalArgumentException("Нет даты поступления") ;
        Calendar cal = Calendar.getInstance();
        cal.setTime(aAdmissionDate);
        cal.add(Calendar.YEAR, -15);
        cal.add(Calendar.DAY_OF_MONTH, 1);
        return aBirthDate.getTime() > cal.getTime().getTime();
    }
    

}
