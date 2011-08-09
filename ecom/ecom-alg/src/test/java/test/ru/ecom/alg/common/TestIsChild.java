package test.ru.ecom.alg.common;

import junit.framework.TestCase;

import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;

import ru.ecom.alg.common.IsChild;

/**
 * Ребенок
 */
public class TestIsChild extends TestCase {

    public void testIsChild() throws ParseException {
        assertEquals(true, isChild("1975.01.01","1986.01.01"));
        assertEquals(false, isChild("1975.01.01","2010.01.01"));
    }

    private boolean isChild(String aBirthDate, String aAdmissionDate) throws ParseException {
        Date birthDate = FORMAT.parse(aBirthDate) ;
        Date admissionDate = FORMAT.parse(aAdmissionDate) ;
        return new IsChild().isChild(birthDate, admissionDate) ;
    }

    private static SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy.MM.dd");
}
