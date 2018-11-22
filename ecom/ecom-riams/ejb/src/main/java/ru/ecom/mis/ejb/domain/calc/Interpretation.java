package ru.ecom.mis.ejb.domain.calc;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.live.DeleteListener;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Created by Milamesher on 21.11.2018.
 *  Интерпретация результата */
@Comment("Интерпретация результата")
@Entity
@Table(schema="SQLUser")
@EntityListeners(DeleteListener.class)
public class Interpretation extends BaseEntity {
    /** Калькулятор */
    @Comment("Калькулятор")
    @OneToOne
    public Calculator getCalculator() {return theCalculator;}
    public void setCalculator(Calculator aCalculator) {theCalculator = aCalculator;	}
    private Calculator theCalculator;

    /** Значение */
    @Comment("Значение")
    public String getValue() {return theValue;}
    public void setValue(String aValue) {theValue = aValue;	}
    private String theValue;

    /** Результат */
    @Comment("Результат")
    public String getResult() {return theResult;}
    public void setResult(String aResult) {theResult = aResult;}
    private String theResult;
}