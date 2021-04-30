package ru.ecom.expomc.ejb.services.registry.print;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Услуги
 */
@Getter
@Setter
public class RegistryPrintRenderHolder {

    public Integer getLevelInteger() {
    	try {
    		return Integer.valueOf(level) ;
    	} catch (Exception e) {
    		e.printStackTrace() ;
    		return -1 ;
    	}
    }

    /** Количество койко-дней (фактическое) */
    private int bedDaysReal = 0 ;
    /** Количество койко-дней (принятое для расчета) */
    private BigDecimal bedDaysCalc = new BigDecimal(0);
    /** Уровень оказания мед. помощи */
    private String level = "NO_LEVEL";
    /** Стоимость */
    private BigDecimal price = new BigDecimal(0);
    /** Тариф */
    private BigDecimal tariff = new BigDecimal(0);
    /** Количество услуг */
    private int renderCount = 0 ;
    /** Наименование медицинской услуги */
    private String renderName = "no name";
    /** Код услуги */
    private String renderCode = "no code";
    /** Порядковый номер */
    private int serialNumber = -1 ;

    public void addBedDaysReal(int aBedDays) {
        bedDaysReal+=aBedDays ;
    }
    public void addBedDaysCalc(BigDecimal aBedDaysCalc) {
        bedDaysCalc=aBedDaysCalc!=null ? bedDaysCalc.add(aBedDaysCalc): bedDaysCalc;
    }

    public void addPrice(BigDecimal aPrice) {
        if(aPrice!=null) price = price.add(aPrice) ;
    }

    public void addRenderCount(int aCount) {
        renderCount+=aCount ;
    }
}
