package test.med;

import junit.framework.TestCase;
import org.junit.Assert;
import ru.nuzmsh.util.med.CheckMkbRangeHelper;

/**
 * @author esinev
 * Date: 31.10.2006
 * Time: 13:07:14
 */
public class TestCheckMkbRange extends TestCase {

    public void test() {

        check("A21", "A00","T98",true) ;
        check("A21", "A00","A19",false) ;
        check("A21.4", "A21.2","A21.5",true) ;
        check("A21.4", "A21.2","A21.4",true) ;
        check("A21.4", "A25.4","Z00",false) ;

    }


    private static void check(String aMkb, String aFrom, String aTo, boolean isTrue) {
        CheckMkbRangeHelper check = new CheckMkbRangeHelper() ;
        System.out.println(aMkb + " "+aFrom+" - "+aTo+" "+check.isIn(aMkb, aFrom, aTo));
        Assert.assertEquals(check.isIn(aMkb, aFrom, aTo),isTrue);
    }
}
