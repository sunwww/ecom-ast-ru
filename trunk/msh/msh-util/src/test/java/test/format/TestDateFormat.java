package test.format;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import ru.nuzmsh.util.format.DateFormat;

import junit.framework.TestCase;

public class TestDateFormat extends TestCase {

	public void testParseDate() throws ParseException {
		assertEquals(createDate(12,12,1947), DateFormat.parseDate("12.12.1947"));
		assertEquals(createDate(12,12,1947), DateFormat.parseDate("12.12.47"));
		assertEquals(createDate(12,12,2007), DateFormat.parseDate("12.12.07"));
		assertEquals(createDate(12,12,2007), DateFormat.parseDate("12.12.2007"));
	}
	
	private Date createDate(int aDay, int aMonth, int aYear) {
		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.set(Calendar.DATE, aDay);
		cal.set(Calendar.YEAR, aYear);
		cal.set(Calendar.MONTH, aMonth-1);
		System.out.println(cal.getTime()) ;
		return cal.getTime();
	}
	
}
