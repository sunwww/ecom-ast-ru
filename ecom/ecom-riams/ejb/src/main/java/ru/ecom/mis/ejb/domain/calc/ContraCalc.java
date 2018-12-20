package ru.ecom.mis.ejb.domain.calc;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * Created by Milamesher on 18.12.2018.
 *  Противопоказания в калькуляторе */
@Entity
public class ContraCalc extends BaseEntity {
    /** Калькулятор */
    @Comment("Калькулятор")
    @OneToOne
    public Calculator getCalculator() {return theCalculator;}
    public void setCalculator(Calculator aCalculator) {theCalculator = aCalculator;	}
    private Calculator theCalculator;

    /** Противопоказание */
    @Comment("Противопоказание")
    public String getContraValue() {return theContraValue;}
    public void setContraValue(String aContraValue) {theContraValue = aContraValue;	}
    private String theContraValue;
}