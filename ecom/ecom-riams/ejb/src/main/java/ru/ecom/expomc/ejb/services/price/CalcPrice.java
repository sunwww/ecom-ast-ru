package ru.ecom.expomc.ejb.services.price;

import java.math.BigDecimal;

/**
 * Расчетная цена
 */
public class CalcPrice {
    /**
     * Цена
     * @param aPrice цена
     * @param aLevel уровень КЛ
     * @param aTariff тариф 1 койко-дня
     * @param aCalcDays расчетное количество койко-дней
     */
    public CalcPrice(BigDecimal aPrice, int aLevel, BigDecimal aTariff, BigDecimal aCalcDays) {
        thePrice = aPrice ;
        theLevel = aLevel;
        theTariff = aTariff;
        theCalcDney = aCalcDays ;
    }

    /** Цена */
    public BigDecimal getPrice() { return thePrice ; }

    /** Уровень */
    public int getLevel() { return theLevel ; }

    /** Тариа */
    public BigDecimal getTariff() { return theTariff ; }

    /** Количество расчетных дней */
    public BigDecimal getCalcDney() { return theCalcDney ; }

    /** Количество расчетных дней */
    private final BigDecimal theCalcDney ;
    /** Тариа */
    private final BigDecimal theTariff ;
    /** Уровень */
    private final int theLevel ;
    /** Цена */
    private final BigDecimal thePrice ;
}
