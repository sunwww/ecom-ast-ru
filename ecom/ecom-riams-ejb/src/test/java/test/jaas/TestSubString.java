package test.jaas;

import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
//import java.util.Calendar;

import org.apache.tools.ant.taskdefs.Length;

import ru.nuzmsh.util.format.DateFormat;

public class TestSubString {
	public static void main(String[] args) throws ParseException {
		//System.out.println(getParent("par1  hat par1 jjj", "par1","par2")) ;
		getInterval("08:30","08:35",Long.valueOf(5)) ;
		getInterval("08:30","10:00",Long.valueOf(7)) ;
		getInterval("08:30","10:00",Long.valueOf(11)) ;
		getInterval("11:30","10:00",Long.valueOf(10)) ;
		/*
		Time timeFrom = DateFormat.parseSqlTime("12:00") ;
		Time timeTo = DateFormat.parseSqlTime("14:00") ;
		int minute = 15 ; 
		getTimes(timeFrom, timeTo, minute) ;
		java.sql.Date dat1 = DateFormat.parseSqlDate("01.01.2011") ;
		java.sql.Date dat2 = DateFormat.parseSqlDate("11.02.2011") ;
		getWeek(dat1,dat2) ;
		String test = "ghjhg=:parent and jklajfkl=:parent" ;
		test.replace(":parent", "value") ;
		System.out.println(test) ;
		System.out.println(test.replace(":parent", "value")) ;
		Calendar cal = Calendar.getInstance() ;
		cal.setTime(dat1) ;
		System.out.println("weekmonthlast="+cal.getActualMaximum(Calendar.WEEK_OF_MONTH) );
		System.out.println("weekmonthlast="+cal.getActualMaximum(Calendar.DAY_OF_MONTH) );
		*/
	}
	
	static String getInterval(String aBeginTime, String aEndTime, Long aCountVisits) throws ParseException {
		System.out.println("c "+aBeginTime+" по "+aEndTime+" "+aCountVisits) ;
		java.sql.Time timeFrom = DateFormat.parseSqlTime(aBeginTime) ;
		java.sql.Time timeTo = DateFormat.parseSqlTime(aEndTime) ;
		Calendar cal1 = java.util.Calendar.getInstance() ;
		Calendar cal2 = java.util.Calendar.getInstance() ;
		cal1.setTime(timeFrom) ;
		cal2.setTime(timeTo) ;
		int cnt = aCountVisits.intValue() ;
		int hour1 = cal1.get(Calendar.HOUR) ;
		int hour2 = cal2.get(Calendar.HOUR) ;
		int min1 = cal1.get(Calendar.MINUTE) ;
		int min2 = cal2.get(Calendar.MINUTE) ;
		int dif = (hour2-hour1)*60 +min2- min1 ;
		if (dif<cnt) return "Разница между временами должна быть больше кол-ва посещений"  ;
		StringBuilder ret = new StringBuilder() ;
		java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("HH:mm") ;
		if (dif%cnt == 0) {
			int interval = dif/cnt ;
			System.out.println("Интервал="+interval) ;
			if (interval<0) return "Отрицательный интервал"  ;
			while (cal2.after(cal1)) {
				//System.out.println(format.format(cal1.getTime())) ;
				ret.append(",").append(format.format(cal1.getTime())) ;
				cal1.add(java.util.Calendar.MINUTE, interval) ;
			}
		} else {
			int interval = dif/cnt ;
			System.out.println("Интервал="+interval) ;
			if (interval<0) return "Отрицательный интервал"  ;
			int dop = dif % cnt ;
			while (cal2.after(cal1)) {
				//System.out.println(format.format(cal1.getTime())) ;
				java.sql.Time sqlTime = new java.sql.Time(cal1.getTime().getTime()) ;
				ret.append(",").append(DateFormat.formatToTime(sqlTime)) ;
				cal1.add(java.util.Calendar.MINUTE, interval) ;
				if (dop>0) {
					dop-- ;
					ret.append("+") ;
					cal1.add(java.util.Calendar.MINUTE, 1) ;
				}
			}			
		}
		System.out.println(ret.substring(1)) ;
		return ret.substring(1);
	}
	static void getWeek (Date aDateFrom, Date aDateTo) {
		java.util.Calendar cal1 = java.util.Calendar.getInstance() ;
		java.util.Calendar cal2 = java.util.Calendar.getInstance() ;
		cal1.setTime(aDateFrom) ;
		cal2.setTime(aDateTo) ;
		java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("dd.MM.yyyy") ;
		while (cal2.after(cal1)) {
			System.out.print(format.format(cal1.getTime())) ;
			System.out.print("   WEEK_OF_MONTH=") ;
			System.out.print(cal1.get(java.util.Calendar.WEEK_OF_MONTH)) ;
			System.out.print("  WEEK_OF_YEAR=") ;
			System.out.print(cal1.get(java.util.Calendar.WEEK_OF_YEAR)) ;
			System.out.print("  DATE=") ;
			System.out.print(cal1.get(java.util.Calendar.DATE)) ;
			System.out.print("  DAY_OF_WEEK_IN_MONTH=") ;
			System.out.print(cal1.get(java.util.Calendar.DAY_OF_WEEK_IN_MONTH)) ;
			System.out.print("  DAY_OF_WEEK=") ;
			System.out.print(cal1.get(java.util.Calendar.DAY_OF_WEEK)) ;
			System.out.print("  DAY_OF_WEEK_IN_MONTH=") ;
			System.out.print(cal1.get(java.util.Calendar.DAY_OF_WEEK_IN_MONTH)) ;
			System.out.println(" ") ;
			cal1.add(java.util.Calendar.DAY_OF_MONTH, 1) ;
		}
		
	}
	static void getTimes(Time aTimeFrom, Time aTimeTo,int aMinute) {
		java.util.Calendar cal1 = java.util.Calendar.getInstance() ;
		java.util.Calendar cal2 = java.util.Calendar.getInstance() ;
		cal1.setTime(aTimeFrom) ;
		cal2.setTime(aTimeTo) ;
		System.out.println("cal1="+cal1.getTime());
		System.out.println("cal2="+cal2.getTime());
		java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("HH:mm") ;
		while (cal2.after(cal1)) {
			System.out.println(format.format(cal1.getTime())) ;
			cal1.add(java.util.Calendar.MINUTE, aMinute) ;
		}
	}
	  static String getParent(String aParentField,String aFind, String aParentId) {
    	StringBuilder ret;
    	String result = aParentField ;
    	String val= "<><>";
    	System.out.println(val.replace("<", "&lt;")) ;
    	System.out.println(val) ;
    	if (result !=null && result.length()>0) {
    		int a=0 ;
    		int b=0 ;
    		
    		while(a!=-1) {
    			a=result.indexOf(aFind,b) ;
    			if (a!=-1) {
    				ret=new StringBuilder() ;
    				ret.append(result.substring(0,a)) ;
    				ret.append(" '").append(aParentId).append("' ") ;
    				b=a+aParentId.length() ;
    				System.out.println() ;
    				ret.append(result.substring(a+aFind.length())) ;
    				result=ret.toString() ;
    			}  
    			
    		}
    	}
    	return result ;
    	
    }
}
