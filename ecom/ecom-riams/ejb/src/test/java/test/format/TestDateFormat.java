package test.format;

import junit.framework.TestCase;
import org.junit.Assert;
import ru.nuzmsh.util.format.DateFormat;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class TestDateFormat extends TestCase {

	public void testParseDate() throws ParseException {
		Assert.assertEquals(createDate(12,12,1947), DateFormat.parseDate("12.12.1947"));
		Assert.assertEquals(createDate(12,12,1947), DateFormat.parseDate("12.12.47"));
		Assert.assertEquals(createDate(12,12,2007), DateFormat.parseDate("12.12.07"));
		Assert.assertEquals(createDate(12,12,2007), DateFormat.parseDate("12.12.2007"));
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
