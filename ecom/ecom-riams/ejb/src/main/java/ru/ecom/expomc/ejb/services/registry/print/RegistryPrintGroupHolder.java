package ru.ecom.expomc.ejb.services.registry.print;

import java.math.BigDecimal;

/**
 * Группа
 */
public class RegistryPrintGroupHolder {
    /** Название */
    public String getName() { return name ; }
    public void setName(String aName) { name = aName ; }

    /** Количество */
    public int getCount() { return count ; }
//    public void setCount(int aCount) { count = aCount ; }

    /** Сумма */
    public BigDecimal getSumm() { return summ ; }
//    public void setSumm(BigDecimal aSumm) { summ = aSumm ; }

    /** Сумма */
    private BigDecimal summ = new BigDecimal(0);
    /** Количество */
    private int count = 0 ;
    /** Название */
    private String name = "no name";

    public void addSumm(BigDecimal aPrice) {
        if(aPrice!=null) summ = summ.add(aPrice) ;
    }

    public void addCount(int i) {
        count+=i ;
    }
}
