package test.jaas;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.nuzmsh.util.format.DateFormat;

import java.text.ParseException;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TestSubString {

	@Test
	@DisplayName("Считать ли период на отчетном сервере")
	void makeTest() {
		//System.out.println(isReportBase("29.10.2014","30.10.2014")) ;
		assertTrue(isReportBase("29.10.2014","29.10.2014")); ;
		assertTrue(isReportBase("22.10.2014","29.10.2014")) ;
		assertTrue(isReportBase("23.10.2014","29.10.2014")) ;
		assertTrue(isReportBase("01.10.2014","29.10.2014")) ;

	}
	 boolean isReportBase(String aBeginDate,String aEndDate) {

		boolean isRepBase = true ;
		try {
			java.util.Date d1 = DateFormat.parseDate(aBeginDate) ;
			Calendar c1 = Calendar.getInstance() ;c1.setTime(d1) ;
			c1.set(Calendar.AM_PM, 0) ;
			java.util.Date d2 = DateFormat.parseDate(aEndDate) ;
			Calendar c2 = Calendar.getInstance() ;c2.setTime(d2) ;
			c2.set(Calendar.AM_PM, 0) ;
			java.util.Date d3 = new java.sql.Date(new java.util.Date().getTime()) ;
			Calendar c3 = Calendar.getInstance() ;c3.setTime(d3) ;
			c3.set(Calendar.SECOND, 0) ;
			c3.set(Calendar.HOUR_OF_DAY, 0) ;
			c3.set(Calendar.MINUTE, 0) ;
			c3.set(Calendar.MILLISECOND, 0) ;
			
			c1.add(Calendar.DAY_OF_MONTH, 7) ;
			if (c2.getTime().getTime() == c3.getTime().getTime() && c1.after(c3)) { //если дата окончания периода = сегодня и начало периода менее 7 дней
				isRepBase = false;
			}
		} catch (ParseException e) {
			
		}
		return isRepBase ;
	}
}
