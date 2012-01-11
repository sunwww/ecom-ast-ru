package test.date;

import junit.framework.TestCase;

import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import ru.nuzmsh.util.date.AgeUtil;

/**
 *
 */
public class TestAgeUtil extends TestCase {
	
	public static void main(String[] args) {
		java.util.Date date = new java.util.Date() ;
        Calendar c1 = Calendar.getInstance() ;
        c1.setTime(date) ;
        Calendar c2 = Calendar.getInstance() ;
        c2.set(Calendar.MONTH,11) ;
        c2.set(Calendar.DAY_OF_MONTH,25) ;
        c2.set(Calendar.YEAR,2006) ;
        java.sql.Date d1 = new java.sql.Date(c2.getTime().getTime()) ;
        java.sql.Date d2 = new java.sql.Date(c1.getTime().getTime()) ;
        System.out.println("age0="+AgeUtil.getAgeCache(d1,d2, 0)) ;
        System.out.println("age1="+AgeUtil.getAgeCache(d1,d2, 1)) ;
        System.out.println("age2="+AgeUtil.getAgeCache(d1,d2, 2)) ;
        System.out.println("age3="+AgeUtil.getAgeCache(d1,d2, 3)) ;
        System.out.println("DATE="+c1.get(Calendar.DATE)) ;
        System.out.println("DAY_OF_MONTH="+c1.get(Calendar.DAY_OF_MONTH) ) ;
        System.out.println("DAY_OF_WEEK="+c1.get(Calendar.DAY_OF_WEEK) ) ;
        System.out.println("DAY_OF_WEEK_IN_MONTH="+c1.get(Calendar.DAY_OF_WEEK_IN_MONTH) ) ;
        System.out.println("DAY_OF_YEAR="+c1.get(Calendar.DAY_OF_YEAR) ) ;
        System.out.println("WEEK_OF_MONTH="+c1.get(Calendar.WEEK_OF_MONTH) ) ;
        System.out.println("MONTH="+c1.get(Calendar.MONTH) ) ;
        System.out.println("MONTH="+c1.getActualMaximum(Calendar.DAY_OF_MONTH))  ;
        
	}
	
    public void testAgeYear() throws ParseException {
        
    	check("31.12.1975", "07.06.2006", 30 ) ;
        check("12.02.1976", "07.06.2006", 30 ) ;
        check("26.06.2003", "07.06.2006",  2 ) ;
        check("06.04.1977", "07.06.2006", 29 ) ;

        check("06.06.2001", "07.06.2006",  5 ) ;
        check("25.08.2001", "07.06.2006",  4 ) ;
        check("25.02.2001", "07.06.2006",  5 ) ;
        check("25.11.2001", "07.06.2006",  4 ) ;

        check("06.06.2006", "07.06.2006",  1 ) ;
        check("07.06.2005", "07.06.2006",  1 ) ;
        check("08.06.2005", "07.06.2006",  0 ) ;

        check("07.06.2006", "07.06.2006",  0 ) ;


        try {
            check("08.06.3006", "07.06.2006",  1 ) ;
            TestCase.fail("Нет проверки да будующую дату");
        } catch (IllegalArgumentException e) {
//            e.printStackTrace() ;
        }
    }

    private void check(String aBirth, String aFrom, int age) throws ParseException {
//        System.out.println("aBirth = " + aBirth +" "+aFrom);
        Date birth = FORMAT.parse(aBirth) ;
        Date from  = FORMAT.parse(aFrom) ;
        assertEquals(age, AgeUtil.calcAgeYear(birth, from)) ;
    }

    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("dd.MM.yyyy");
}
