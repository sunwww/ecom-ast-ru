package test.excel.example;

import java.math.BigDecimal;

/**
 *
 */
public class RegistryPrintGroupHolder {
    /** Название */
    public String getName() { return theName ; }
    public void setName(String aName) { theName = aName ; }

    /** Количество */
    public int getCount() { return theCount ; }
//    public void setCount(int aCount) { theCount = aCount ; }

    /** Сумма */
    public BigDecimal getSumm() { return theSumm ; }
//    public void setSumm(BigDecimal aSumm) { theSumm = aSumm ; }

    /** Сумма */
    private BigDecimal theSumm = new BigDecimal(0);
    /** Количество */
    private int theCount = 0 ;
    /** Название */
    private String theName = "no name";

    public void addSumm(BigDecimal aPrice) {
        theSumm = theSumm.add(aPrice) ;
    }

    public void addCount(int i) {
        theCount+=i ;
    }
}
