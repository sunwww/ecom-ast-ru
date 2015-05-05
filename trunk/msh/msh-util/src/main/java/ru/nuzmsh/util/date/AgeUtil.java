package ru.nuzmsh.util.date;

import java.text.ParseException;
import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

import ru.nuzmsh.util.format.DateFormat;

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
    	//Calendar bd = initDate; 
    	//new GregorianCalendar(year, month, day); 
    	//Calendar cd = actualDate; 
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
        int year1 = d1.get(Calendar.YEAR) ; int year2 = d2.get(Calendar.YEAR) ; 
        int month1 = d1.get(Calendar.MONTH) ; int month2 = d2.get(Calendar.MONTH) ;
        int day1 = d1.get(Calendar.DAY_OF_MONTH) ; int day2 = d2.get(Calendar.DAY_OF_MONTH) ;
    	int day = day1 - day2 ;
    	int tst;
    	int month ;
    	tst=0 ; 
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
    	if (aFormat==0) return new StringBuilder().append(year).toString() ;
    	if (aFormat==1) return new StringBuilder().append(year)
    			.append(".").append(month)
    			.append(".").append(day).toString() ;
    	String dy=(year==1)?" год ": 
    		((year==0||year>4)?" лет ":" года ") ;
    	String dm=(month==1)?" месяц ":
    		(((month>1)&&(month<5))?" месяца ":" месяцев " );
    	String dd = (day>4?" дней ":
    		((day==0||(day>10&&(day<15)))?" дней ":((day==1)?" день ":" дня ")));
    			 

    	return new StringBuilder().append(year)
    			.append(dy).append(month)
    			.append(dm).append(day)
    			.append(dd).toString() ;
    }
     
    /*
    dif(%d1,%d2,sh,work) ;
 ; ўлзЁб«Ґ­ЁҐ а §­Ёжл ¤ в ў ¤­пе б §­ Є®¬ Ё ўл¤ зҐ© Ї® и Ў«®­г
 ; sh=0- yy.mm.dd, sh=1- yy, sh=2- yy mm, sh=3- yy mm dd, sh=4-y m
 ; ЎҐ§ и Ў«®­  - ўл¤ з  а §­Ёжл ў ¤­пе,
 ; work- ўл¤ з  p §­Ёжл ў p Ў®зЁе ¤­пе
 q:$g(work) $$work(%d1,%d2)
 s $zt="q" 
 s %d1=$$in^Zdate($$ini(%d1,0)),%d2=$$in^Zdate($$ini(%d2,0))
 q:'$g(%d1,"")!('$g(%d2,"")) "" 
 q:'$d(sh) %d2-%d1
 n dif,dd,dlm,mm,yy 
 s dif=%d2-%d1 q:sh="" dif
 ;s yy=dif\365,mm=dif#365\31,dd=dif#365#31
 n sig s sig=$s(dif<0:"-",1:"")
 i sig="" s dif=%d1,%d1=%d2,%d2=dif ;1- Ў®«ми п, 2- ¬Ґ­ми п
 s %d1=$$ie(%d1,2),%d2=$$ie(%d2,2)
 s yy(1)=$e(%d1,1,4),yy(2)=$e(%d2,1,4)
 s mm(1)=$e(%d1,5,6),mm(2)=$e(%d2,5,6)
 s dd(1)=$e(%d1,7,8),dd(2)=$e(%d2,7,8)
 s dd=dd(1)-dd(2) 
 n tst s tst=0 
 i dd<0 s dd=$$lday(mm(2),yy(1))+dd,tst=1
 s mm=mm(1)-mm(2)-tst s tst=0
 i mm<0 s mm=12+mm s tst=1
 s yy=yy(1)-yy(2)-tst
 q:sh=0 yy_"."_mm_"."_dd
 s op=$e(yy,$l(yy)),dlm(1)=$s(op>4:" лет ",op=0!(yy>10&(yy<15)):" лет ",op=1:" год ",1:" года ")
 s dlm(2)=$s(mm=1:" месяц ",(mm'=0)&(mm<5):" месяца ",1:" месяцев ")
 s op=$e(dd,$l(dd)),dlm(3)=$s(op>4:" дней ",op=0!(dd>10&(dd<15)):" дней ",op=1:" день ",1:" дня ")
 i sh=4 q yy_$e(dlm(1),1,2)_" "_mm_$e(dlm(2),1,2)
 i sh=3 q yy_dlm(1)_mm_dlm(2)_dd_dlm(3) 
 i sh=2 q yy_dlm(1)_mm_" мес"
 q yy_dlm(1)

    */
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
