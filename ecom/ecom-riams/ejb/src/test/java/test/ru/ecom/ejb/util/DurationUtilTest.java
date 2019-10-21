package test.ru.ecom.ejb.util;

import org.junit.jupiter.api.Test;
import ru.ecom.ejb.util.DurationUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DurationUtilTest {

    @Test
    void getDuration() throws ParseException {
        String sFromDate = "10:40 16.10.2019";
        String sFromTo = "10:20 17.10.2019";
        String sFromTo2 = "10:50 17.10.2019";
        SimpleDateFormat format = new SimpleDateFormat("HH:mm dd.MM.yyyy");
        assertEquals(DurationUtil.getDuration(format.parse(sFromDate),format.parse(sFromTo)),"1");
        assertEquals(DurationUtil.getDuration(format.parse(sFromDate),format.parse(sFromTo2)),"2");
        assertEquals(DurationUtil.getDurationMedCase(format.parse(sFromDate),format.parse(sFromTo),1),"1");
        assertEquals(DurationUtil.getDurationMedCase(format.parse(sFromDate),format.parse(sFromTo2),1),"1");
    }

    @Test
    void getDurationMedCase() throws ParseException {
        String sFromDate = "10:40 15.09.2019";
        String sFromTo = "10:55 17.10.2019";
        SimpleDateFormat format = new SimpleDateFormat("HH:mm dd.MM.yyyy");
        String actualDays = DurationUtil.getDurationMedCase(format.parse(sFromDate),format.parse(sFromTo),1,1);
        assertEquals(actualDays,"33");
        assertEquals(DurationUtil.getDurationMedCase(format.parse(sFromDate),null,1,1),actualDays+" (на текущий момент)");
    }

    @Test
    void getDurationFull() throws ParseException {
        String sFromDate = "10:40 15.09.2019";
        String sFromTo = "10:55 17.10.2019";
        SimpleDateFormat format = new SimpleDateFormat("HH:mm dd.MM.yyyy");
        assertEquals(DurationUtil.getDurationFull(format.parse(sFromDate),format.parse(sFromTo)),"15.09.2019-17.10.2019");
    }
}