package test.med;

import ru.nuzmsh.util.med.CheckMkbRangeHelper;

/**
 * @author esinev
 * Date: 31.10.2006
 * Time: 13:07:14
 */
public class TestCheckMkbRange {

    public static void main(String[] args) {
        check("A21", "A00","T98") ;
        check("A21", "A00","A19") ;
        check("A21.4", "A21.2","A21.5") ;
        check("A21.4", "A21.2","A21.4") ;
        check("A21.4", "A25.4","Z00") ;

    }


    private static void check(String aMkb, String aFrom, String aTo) {
        CheckMkbRangeHelper check = new CheckMkbRangeHelper() ;
        System.out.println(aMkb + " "+aFrom+" - "+aTo+" "+check.isIn(aMkb, aFrom, aTo));
    }
}
