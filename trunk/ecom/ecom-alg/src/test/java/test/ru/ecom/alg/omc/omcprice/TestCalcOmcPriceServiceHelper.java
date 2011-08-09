package test.ru.ecom.alg.omc.omcprice;

import junit.framework.TestCase;

import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;
import java.math.BigDecimal;

import ru.ecom.alg.omc.omcprice.impl.OmcPriceRequestImmutable;
import ru.ecom.alg.omc.omcprice.CalcOmcPriceServiceHelper;
import ru.ecom.alg.omc.omcprice.OmcCalcPriceException;
import ru.ecom.alg.omc.omcprice.OmcPriceCalcResult;

/**
 * Тестирование определения цены
 */
public class TestCalcOmcPriceServiceHelper extends TestCase {

    public void test()  {
        CalcOmcPriceServiceHelper service = new CalcOmcPriceServiceHelper();
        SimpleOmcPriceService priceService = new SimpleOmcPriceService();

        OmcPriceRequestImmutable request = new OmcPriceRequestImmutable(
                d("1975.01.01"), d("2001.01.01"), d("2001.01.10")
               , 10, "O00.1", false, "000B0Y", 1, "0Y", 0, false, 1
        );

        OmcPriceCalcResult result = null ;
        try {
            result = service.calc(request, priceService);
            check(result, "3909.78", "365.4", 3, "10.7");

            request = new OmcPriceRequestImmutable(
                    d("1975.01.01"), d("2001.01.01"), d("2001.01.10")
                   , 11, "K25.9", true, "000B03", 1, "03", 0, false, 1
            );
            check(service.calc(request, priceService), "7715.14", "488.3", 1, "15.8");

            request = new OmcPriceRequestImmutable(
                    d("1975.01.01"), d("2001.01.01"), d("2001.01.10")
                   , 11, "K25.9", true, "000C0A", 1, "03", 0, false, 1
            );
            check(service.calc(request, priceService), "33.10", "33.1", 4, "1");

        } catch (OmcCalcPriceException e) {
            e.printStackTrace();
            for (String s : e.getMessages()) {
                System.err.println(" " + s);
            }
        }
//        System.out.println("result = " + result);
    }


    private void check(OmcPriceCalcResult aResult, String aPrice, String aTariff, int aLevel, String aCalcBedDays) {
        System.out.println("aResult = " + aResult);
        BigDecimal price = new BigDecimal(aPrice);
        BigDecimal tariff = new BigDecimal(aTariff);
        BigDecimal calcBedDays = new BigDecimal(aCalcBedDays);
        assertEquals(aResult.getCalcPrice(), price);
        assertEquals(aResult.getCalcBedDays(), calcBedDays);
        assertEquals(aResult.getTariff(), tariff);
        assertEquals(aResult.getLevel(), aLevel);

    }

    private static Date d(String aDate) {
        try {
            return FORMAT.parse(aDate);
        } catch (ParseException e) {
            throw new IllegalArgumentException(aDate,e) ;
        }
    }
    private static SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy.MM.dd");

}
