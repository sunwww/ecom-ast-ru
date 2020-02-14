package ru.nuzmsh.util.date;

import ru.nuzmsh.util.format.DateFormat;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Возраст
 */
public class AgeUtil {

    public static long calculateDays (Date aStartDate, Date aFinishDate) {
    	if (aStartDate ==null) throw new IllegalArgumentException("Не указана дата начала");
    	if (aFinishDate ==null) {aFinishDate = new java.util.Date();}
    	return (aFinishDate.getTime()-aStartDate.getTime())/3600000/24;

    }
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
        if(year==0 && monthCalc==0 && dayCalc>0) {
           // year++ ;
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
    
    public static String calculateAge(java.sql.Date aBirthDate, java.sql.Date aFromDate) {
        if (aBirthDate == null) throw new IllegalArgumentException("Не установлена дата рождения");
        if (aFromDate == null) throw new IllegalArgumentException("Не установлена дата, с которой вычисляется возраст");
        if(aBirthDate.getTime()>aFromDate.getTime()) {
            throw new IllegalArgumentException("Дата рождения "+aBirthDate+" не может быть позже даты "+aFromDate) ;
        }
        Calendar bd = Calendar.getInstance();
        bd.setTime(aBirthDate);
        Calendar cd = Calendar.getInstance();
        cd.setTime(aFromDate);
    	int ageYears, ageMonths, ageDays; 
    	ageYears = cd.get(Calendar.YEAR) - bd.get(Calendar.YEAR);
    	if (bd.get(Calendar.MONTH) > cd.get(Calendar.MONTH)){ 
    		ageYears --; ageMonths = 12 - bd.get(Calendar.MONTH) + cd.get(Calendar.MONTH); 
    	} else{ 
    		ageMonths = cd.get(Calendar.MONTH) - bd.get(Calendar.MONTH) ; 
    	} if (bd.get(Calendar.DAY_OF_MONTH) > cd.get(Calendar.DAY_OF_MONTH)){ 
    		ageMonths --; 
    		int monthT = cd.get(Calendar.MONTH) -1; 
    		int compareMonth ;
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
    //	System.out.print("Age of the person : " + ageYears + " year, " + ageMonths + " months and " + ageDays + " days.");
    	return ageYears + "." + ageMonths + "." + ageDays; 
    	
    }
    
    public static String getAge(String aBirthday, int aFormat) throws ParseException{
    	return getAgeCache( new Date(), DateFormat.parseDate(aBirthday), aFormat) ;
    }

    
    public static String getAgeCache(java.util.Date aDateFrom,java.util.Date aDateTo, int aFormat){
    	if (aDateFrom.getTime()<aDateTo.getTime()) return "Дата "+aDateFrom+" не может быть позже даты "+aDateTo ;
    	Calendar d1 = Calendar.getInstance();
        Calendar d2 = Calendar.getInstance();
    	d2.setTime(aDateTo);
        d1.setTime(aDateFrom);
        int year1 = d1.get(Calendar.YEAR) ;
        int year2 = d2.get(Calendar.YEAR) ;
        int month1 = d1.get(Calendar.MONTH) ;
        int month2 = d2.get(Calendar.MONTH) ;
        int day1 = d1.get(Calendar.DAY_OF_MONTH) ;
        int day2 = d2.get(Calendar.DAY_OF_MONTH) ;
    	int day = day1 - day2 ;
    	int tst=0 ;
		int month ;
    	if (day<0){
    		Calendar d3 = Calendar.getInstance() ;
    		d3.setTime(d1.getTime()) ;
    		d3.set(Calendar.MONTH, month2) ;
    		day=d3.getActualMaximum(Calendar.DAY_OF_MONTH)+day;tst=1 ;
    	}
    	month=month1-month2-tst;
    	tst=0 ;
    	if (month<0) {
    		month=12+month ;
    		tst=1 ;
    	}
    	int year=year1-year2-tst ;
    	if (aFormat==0) return year+"";
    	if (aFormat==1) return year+"."+month+"."+day;
    	String dy=(year==1) ? " год ":
    		((year==0||year>4)?" лет ":" года ") ;
    	String dm=(month==1) ? " месяц ":
    		(((month>1)&&(month<5)) ? " месяца " : " месяцев " );
    	String dd = (day>4 ? " дней " :
				(day==0 ? " дней " : (day==1 ? " день " : " дня ")));
    	return year+dy+month+dm+day+dd;
    }
     

    public static String calculateAge(GregorianCalendar initDate, GregorianCalendar actualDate) { 
    	int ageYears, ageMonths, ageDays; Calendar bd = initDate;
 //   	new GregorianCalendar(year, month, day);
    	Calendar cd = actualDate;
 //   	Calendar.getInstance();
    	ageYears = cd.get(Calendar.YEAR) - bd.get(Calendar.YEAR);
    	if (bd.get(Calendar.MONTH) > cd.get(Calendar.MONTH)){ 
    		ageYears --; ageMonths = 12 - bd.get(Calendar.MONTH) + cd.get(Calendar.MONTH); 
    	} else{ 
    		ageMonths = cd.get(Calendar.MONTH) - bd.get(Calendar.MONTH) ; 
    	} if (bd.get(Calendar.DAY_OF_MONTH) > cd.get(Calendar.DAY_OF_MONTH)){ 
    		ageMonths --; 
    		int monthT = cd.get(Calendar.MONTH) -1; 
    		int compareMonth ;
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
    	//System.out.print("Age of the person : " + ageYears + " year, " + ageMonths + " months and " + ageDays + " days.");
    	return ageYears + ". " + ageMonths + "." + ageDays; 
    }

    /*
    * Расчитываем возраст доп. диспансеризации
    * до года - помесячно, до 3х лет - интервалами. Остальное - полных лет на конец года.*/
    public static String calculateExtDispAge(Date aDispdate, Date aBirthdate) {
		String age = getAgeCache(aDispdate,aBirthdate, 1);
		int sb1 = age.indexOf('.');
		int sb2 = age.indexOf('.', sb1 + 1);
		String ageGroup;
		int yearDif = Integer.parseInt(age.substring(0, sb1));
		int monthDif = Integer.parseInt(age.substring(sb1 + 1, sb2));
		if (yearDif == 0) {
			ageGroup = yearDif + "." + monthDif;
		} else if (yearDif == 1) {
			if (monthDif >= 6) ageGroup = "1.6";
			else if (monthDif >= 3) ageGroup = "1.3";
			else ageGroup = "1";
		} else {
			int year1 = Integer.parseInt(DateFormat.formatToDate(aBirthdate).substring(6));
			int year2 = Integer.parseInt(DateFormat.formatToDate(aDispdate).substring(6));
			if (year2 < 25) year2 = year2 + 2000;
			if (year2 < 100) year2 = year2 + 1900;
			ageGroup = "" + (year2 - year1);
		}
		return ageGroup;
	}
}
