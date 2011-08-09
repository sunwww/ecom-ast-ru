package ru.ecom.alg.omc.omcprice;

import java.util.List;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.io.Serializable;

/**
 * Результат определения цены
 */
public class OmcPriceCalcResult implements Serializable {

    /** Цена */
    public BigDecimal getCalcPrice() {
        return theCalcPrice!=null ? theCalcPrice.setScale(2, RoundingMode.HALF_UP) : null ;
    }

    /** Уровень */
    public int getLevel() { return theLevel ; }

    /** Тариф */
    public BigDecimal getTariff() { return theTariff ; }

    /** Количество дней, принятоя для ресчета */
    public BigDecimal getCalcBedDays() { return theCalcBedDays ; }

    /**
     * Сообщения, при определении цены
     */
    public List<String> getLogMessages() {  return theLog.getMessages();   }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Цена = ").append(getCalcPrice()) ;
        sb.append(", тариф = ").append(getTariff()) ;
        sb.append(", расчетное кол-во кой-ко дней = ").append(getCalcBedDays()) ;
        sb.append(", уровень = ").append(getLevel()) ;
        sb.append("\nLOG:") ;
        for (String msg : getLog().getMessages()) {
            sb.append("\n ") ;
            sb.append(msg) ;
        }
        return sb.toString();
    }

    protected CalcLog getLog() {  return theLog ;  }
    protected void setCalcPrice(BigDecimal aCalcPrice) { theCalcPrice = aCalcPrice ; }
    protected void setCalcBedDays(BigDecimal aCalcBedDays) { theCalcBedDays = aCalcBedDays ; }
    protected void setLevel(int aLevel) { theLevel = aLevel ; }
    protected void setTariff(BigDecimal aTariff) { theTariff = aTariff ; }


    /** Цена */
    private BigDecimal theCalcPrice ;
    private CalcLog theLog = new CalcLog();
    /** Уровень */
    private int theLevel ;
    /** Количество дней, принятоя для ресчета */
    private BigDecimal theCalcBedDays ;
    /** Тариф */
    private BigDecimal theTariff ;
}
