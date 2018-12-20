package ru.ecom.mis.ejb.domain.calc;


import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * Created by Milamesher on 18.12.2018.
 *  Назначения в калькуляторе */
@Entity
public class PrescCalc extends BaseEntity {
    /** Калькулятор */
    @Comment("Калькулятор")
    @OneToOne
    public Calculator getCalculator() {return theCalculator;}
    public void setCalculator(Calculator aCalculator) {theCalculator = aCalculator;	}
    private Calculator theCalculator;

    /** Назначение */
    @Comment("Назначение")
    public String getPrescValue() {return thePrescValue;}
    public void setPrescValue(String aPrescValue) {thePrescValue = aPrescValue;	}
    private String thePrescValue;

    /** Риск */
    @Comment("Риск")
    @OneToOne
    public CalcRisk getCalcRisk() {return theCalcRisk;}
    public void setCalcRisk(CalcRisk aCalcRisk) {theCalcRisk = aCalcRisk;	}
    private CalcRisk theCalcRisk;
}