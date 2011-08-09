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
