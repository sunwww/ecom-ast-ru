package ru.ecom.expomc.ejb.services.price;

import java.math.BigDecimal;
import java.sql.Date;

import ru.ecom.expomc.ejb.services.check.ICheckLog;

/**
 * Вычисление цены случая
 */
public class CalcPriceServiceBean {


    public CalcPrice calcPrice(String aUsluga
            , Date aAdmissionDate
            , Date aDischargeDate
            , Date aBirthDay
            , int aDays
            , String aMkb
    ) {

      return null ;
    }




    public BigDecimal getSrednKolDayPoDnevnomuStacionary(Date aActualDate
            , ICheckLog aLog
            , String aProfOtd
            , int aLevel
            , boolean aIsChild) {
        return null ;
    }

}
