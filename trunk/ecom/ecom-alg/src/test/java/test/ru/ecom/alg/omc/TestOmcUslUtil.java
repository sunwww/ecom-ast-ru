package test.ru.ecom.alg.omc;

import ru.ecom.alg.omc.OmcUslUtil;
import junit.framework.TestCase;

/**
 * Проверка услуг
 */
public class TestOmcUslUtil extends TestCase {

    public void testIsPolyclinic() {
        assertEquals(false, OmcUslUtil.isPolyclinic("000B0Y")) ;
        assertEquals(true, OmcUslUtil.isPolyclinic("000C0Y")) ;

    }
}
