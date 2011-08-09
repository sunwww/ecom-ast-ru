package ru.ecom.alg.omc.omcprice;

import java.math.BigDecimal;
import java.io.Serializable;

/**
 * КСГ. Уровень и ср. количество койко-дней
 */
public class KsgInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     *
     * @param aLevel   уровень
     * @param aBedDays ср. кол-во койко-дней
     */
    public KsgInfo(int aLevel, BigDecimal aBedDays) {
        theLevel = aLevel;
        theBedDays = aBedDays;
    }

    /** Уровень */
    public int getLevel() { return theLevel ; }

    /** Ср. кол-во койко-дней */
    public BigDecimal getBedDays() { return theBedDays ; }

    /** Ср. кол-во койко-дней */
    private final BigDecimal theBedDays ;
    /** Уровень */
    private final int theLevel ;
}
