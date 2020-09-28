package test.med;

import org.junit.jupiter.api.Test;
import ru.nuzmsh.util.med.CheckMkbRangeHelper;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author esinev
 * Date: 31.10.2006
 * Time: 13:07:14
 */
class TestCheckMkbRange {

    @Test
    void test() {

        check("A21", "A00","T98",true) ;
        check("A21", "A00","A19",false) ;
        check("A21.4", "A21.2","A21.5",true) ;
        check("A21.4", "A21.2","A21.4",true) ;
        check("A21.4", "A25.4","Z00",false) ;

    }


    private static void check(String aMkb, String aFrom, String aTo, boolean isTrue) {
        CheckMkbRangeHelper check = new CheckMkbRangeHelper() ;
        assertEquals(check.isIn(aMkb, aFrom, aTo),isTrue);
    }
}
