package test.ru.ecom.alg.omc.omcprice;

import ru.ecom.alg.omc.omcprice.IOmcPriceService;
import ru.ecom.alg.omc.omcprice.KsgInfo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Реализация сервиса для проверки
 */
public class SimpleOmcPriceService implements IOmcPriceService {
    public BigDecimal findTariffByUsl(Date aActualDate, String aUslOmcCode, int aLevel, boolean aIsChild) {
        if(aUslOmcCode.equals("000B0Y") && aLevel==3 && !aIsChild) return new BigDecimal("365.4");
//        else if(aUslOmcCode.equals("000B03") && aLevel==3 && !aIsChild) return null ;
        else if(aUslOmcCode.equals("000B03") && aLevel==1 && !aIsChild) return new BigDecimal("488.3") ;
        else if(aUslOmcCode.equals("000C0A") && aLevel==4 && !aIsChild) return new BigDecimal("33.1") ;
        return null;
    }

    public BigDecimal findAverageDaysByDailyHospital(Date aDischargeDate, String aDepartmentCode, int aDepartmentLevel, boolean aIsChild) {
        return null;
    }

    public BigDecimal findAverageDaysByHospital(Date aDischargeDate, String aDepartmentCode, boolean aIsChild) {
        if(aDepartmentCode.equals("03") && !aIsChild) return new BigDecimal("15.8");
        return null;
    }

    public KsgInfo findKsgInfo(Date aDischargeDate, String aMkb, boolean aIsChild) {
        if(aMkb.equals("O00.1")) return new KsgInfo(3, new BigDecimal("10.7"));
        else if(aMkb.equals("K25.9")) return new KsgInfo(3, new BigDecimal("13.8"));
        return null;
    }

    public Integer findKsgLevel(Date aDischargeDate, String aMkb, boolean aIsChild) {
        if(aMkb.equals("O00.1")) return 3;
        else if(aMkb.equals("K25.9")) return 3;
        return null;
    }

    public BigDecimal findKsgAverageDays(Date aDischargeDate, String aMkb, boolean aIsChild) {
        if(aMkb.equals("O00.1")) return new BigDecimal("10.7");
        else if(aMkb.equals("K25.9")) return new BigDecimal("13.8");
        return null;
    }
    public Integer findDepartmentLevel(Date aDischargeDate, int aKodLpu, String aOmcDepType){
    	return 1;
    };
}
