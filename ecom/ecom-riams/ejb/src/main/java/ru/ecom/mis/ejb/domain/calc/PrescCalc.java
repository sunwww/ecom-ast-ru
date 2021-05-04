package ru.ecom.mis.ejb.domain.calc;


import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * Created by Milamesher on 18.12.2018.
 *  Назначения в калькуляторе */
@Entity
@Getter
@Setter
public class PrescCalc extends BaseEntity {
    /** Калькулятор */
    @Comment("Калькулятор")
    @OneToOne
    public Calculator getCalculator() {return calculator;}
    private Calculator calculator;

    /** Назначение */
    private String prescValue;

    /** Риск */
    @Comment("Риск")
    @OneToOne
    public CalcRisk getCalcRisk() {return calcRisk;}
    private CalcRisk calcRisk;
}