package test.format;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.nuzmsh.util.format.DateFormat;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestDateFormat {

	@Test
	@DisplayName("Проверка расчета дат")
	void testParseDate() throws ParseException {
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
		return cal.getTime();
	}
}
