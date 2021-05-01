package ru.ecom.mis.ejb.domain.calc;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * Created by Milamesher on 18.12.2018.
 *  Противопоказания в калькуляторе */
@Entity
@Getter
@Setter
public class ContraCalc extends BaseEntity {
    /** Калькулятор */
    @Comment("Калькулятор")
    @OneToOne
    public Calculator getCalculator() {return calculator;}
    private Calculator calculator;

    /** Противопоказание */
    private String contraValue;
}