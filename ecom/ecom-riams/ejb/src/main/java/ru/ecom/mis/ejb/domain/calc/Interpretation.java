package ru.ecom.mis.ejb.domain.calc;

import lombok.Getter;
import lombok.Setter;
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
@Getter
@Setter
public class Interpretation extends BaseEntity {
    /** Калькулятор */
    @Comment("Калькулятор")
    @OneToOne
    public Calculator getCalculator() {return calculator;}
    private Calculator calculator;

    /** Значение */
    private String value;

    /** Результат */
    private String result;
}