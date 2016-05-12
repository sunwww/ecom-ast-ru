package ru.ecom.expomc.ejb.services.registry.print;

import java.math.BigDecimal;

/**
 * Услуги
 */
public class RegistryPrintRenderHolder {
    /** Порядковый номер */
    public int getSerialNumber() { return theSerialNumber ; }
    public void setSerialNumber(int aSerialNumber) { theSerialNumber = aSerialNumber ; }

    /** Код услуги */
    public String getRenderCode() { return theRenderCode ; }
    public void setRenderCode(String aRenderCode) { theRenderCode = aRenderCode ; }

    /** Наименование медицинской услуги */
    public String getRenderName() { return theRenderName ; }
    public void setRenderName(String aRenderName) { theRenderName = aRenderName ; }

    /** Количество услуг */
    public int getRenderCount() { return theRenderCount ; }
    public void setRenderCount(int aRenderCount) { theRenderCount = aRenderCount ; }

    /** Тариф */
    public BigDecimal getTariff() { return theTariff ; }
    public void setTariff(BigDecimal aTariff) { theTariff = aTariff ; }

    /** Стоимость */
    public BigDecimal getPrice() { return thePrice ; }
    public void setPrice(BigDecimal aPrice) { thePrice = aPrice ; }

    /** Уровень оказания мед. помощи */
    public String getLevel() { return theLevel ; }
    public void setLevel(String aLevel) { theLevel = aLevel ; }

    public Integer getLevelInteger() {
    	try {
    		return Integer.valueOf(theLevel) ;
    	} catch (Exception e) {
    		e.printStackTrace() ;
    		return -1 ;
    	}
    }
    /** Количество койко-дней (принятое для расчета) */
    public BigDecimal getBedDaysCalc() { return theBedDaysCalc ; }
    public void setBedDaysCalc(BigDecimal aBedDaysCalc) { theBedDaysCalc = aBedDaysCalc ; }

    /** Количество койко-дней (фактическое) */
    public int getBedDaysReal() { return theBedDaysReal ; }
    public void setBedDaysReal(int aBedDaysReal) { theBedDaysReal = aBedDaysReal ; }

    /** Количество койко-дней (фактическое) */
    private int theBedDaysReal = 0 ;
    /** Количество койко-дней (принятое для расчета) */
    private BigDecimal theBedDaysCalc = new BigDecimal(0);
    /** Уровень оказания мед. помощи */
    private String theLevel = "NO_LEVEL";
    /** Стоимость */
    private BigDecimal thePrice = new BigDecimal(0);
    /** Тариф */
    private BigDecimal theTariff = new BigDecimal(0);
    /** Количество услуг */
    private int theRenderCount = 0 ;
    /** Наименование медицинской услуги */
    private String theRenderName = "no name";
    /** Код услуги */
    private String theRenderCode = "no code";
    /** Порядковый номер */
    private int theSerialNumber = -1 ;

    public void addBedDaysReal(int aBedDays) {
        theBedDaysReal+=aBedDays ;
    }
    //zav
    public void addBedDaysCalc(BigDecimal aBedDaysCalc) {
//    	BigDecimal dec0= new BigDecimal(0);
        theBedDaysCalc=aBedDaysCalc!=null ? theBedDaysCalc.add(aBedDaysCalc): theBedDaysCalc;
    }
    //zav

    public void addPrice(BigDecimal aPrice) {
        if(aPrice!=null) thePrice = thePrice.add(aPrice) ;
    }

    public void addRenderCount(int aCount) {
        theRenderCount+=aCount ;
    }
}
