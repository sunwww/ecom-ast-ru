package test.date;

import junit.framework.TestCase;

import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;

import ru.nuzmsh.util.date.AgeUtil;

/**
 *
 */
public class TestAgeUtil extends TestCase {

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
