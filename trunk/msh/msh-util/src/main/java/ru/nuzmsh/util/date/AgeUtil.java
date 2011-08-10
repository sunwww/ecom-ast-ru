package ru.nuzmsh.util.date;

import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Возраст
 */
public class AgeUtil {

    public static int calcAgeYear(Date aBirthDate, Date aFromDate) {
        if (aBirthDate == null) throw new IllegalArgumentException("Не установлена дата рождения");
        if (aFromDate == null) throw new IllegalArgumentException("Не установлена дата, с которой вычисляется возраст");
        if(aBirthDate.getTime()>aFromDate.getTime()) {
            throw new IllegalArgumentException("Дата рождения "+aBirthDate+" не может быть позже даты "+aFromDate) ;
        }
        Calendar birth = Calendar.getInstance();
        birth.setTime(aBirthDate);
        Calendar from = Calendar.getInstance();
        from.setTime(aFromDate);
        int year = from.get(Calendar.YEAR) - birth.get(Calendar.YEAR);
        int monthCalc = from.get(Calendar.MONTH) - birth.get(Calendar.MONTH);
        int dayCalc = from.get(Calendar.DAY_OF_MONTH) - birth.get(Calendar.DAY_OF_MONTH);
//        System.out.println("year = " + year);
//        System.out.println("monthCalc = " + monthCalc);
//        System.out.println("dayCalc = " + dayCalc);
        if(year==0 && monthCalc==0 && dayCalc>0) {
            year++ ;
        } else if (monthCalc == 0) {
            if (dayCalc < 0) {
                year--;
            }
        } else if (monthCalc < 0) {
            year--;
        }
//        }
        return year;
    }
    public static String calculateAge(GregorianCalendar initDate, GregorianCalendar actualDate) { 
    	int ageYears, ageMonths, ageDays; Calendar bd = initDate; 
    	//new GregorianCalendar(year, month, day); 
    	Calendar cd = actualDate; 
    	//Calendar.getInstance(); 
    	ageYears = cd.get(Calendar.YEAR) - bd.get(Calendar.YEAR); 
    	if (bd.get(Calendar.MONTH) > cd.get(Calendar.MONTH)){ 
    		ageYears --; ageMonths = 12 - bd.get(Calendar.MONTH) + cd.get(Calendar.MONTH); 
    	} else{ 
    		ageMonths = cd.get(Calendar.MONTH) - bd.get(Calendar.MONTH) ; 
    	} if (bd.get(Calendar.DAY_OF_MONTH) > cd.get(Calendar.DAY_OF_MONTH)){ 
    		ageMonths --; 
    		int monthT = cd.get(Calendar.MONTH) -1; 
    		int compareMonth = 0;
    		if (monthT == 0 || monthT == 2 || monthT == 4 || monthT == 6 || monthT == 7 || monthT == 9 || monthT == 11){ 
    			compareMonth = 30; 
    		} else if (monthT == 3 || monthT == 5 || monthT == 8 || monthT == 10){ 
    			compareMonth = 31; 
    		} else if (new GregorianCalendar().isLeapYear(cd.get(Calendar.YEAR))){ compareMonth = 29; 
    		} else { 
    			compareMonth = 28; 
    		} 
    		ageDays = compareMonth - bd.get(Calendar.DAY_OF_MONTH) + cd.get(Calendar.DAY_OF_MONTH); 
    	}else{ 
    		ageDays = cd.get(Calendar.DAY_OF_MONTH) - bd.get(Calendar.DAY_OF_MONTH) ; 
    	} 
    	System.out.print("Age of the person : " + ageYears + " year, " + ageMonths + " months and " + ageDays + " days."); 
    	return ageYears + ". " + ageMonths + "." + ageDays; 
    }
}
