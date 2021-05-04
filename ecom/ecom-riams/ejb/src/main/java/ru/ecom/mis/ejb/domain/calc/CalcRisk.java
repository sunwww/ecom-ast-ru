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
 * Created by Milamesher on 18.12.2018.
 *  Риск в калькуляторе */
@Comment("Риск в калькуляторе")
@Entity
@Table(schema="SQLUser")
@EntityListeners(DeleteListener.class)
@Getter
@Setter
public class CalcRisk extends BaseEntity {
    /** Калькулятор */
    @Comment("Калькулятор")
    @OneToOne
    public Calculator getCalculator() {return calculator;}
    private Calculator calculator;

    /** Наименование */
    private String riskValue;

    /** Нижняя граница баллов */
    private Long lowScore;
    
    /** Верхняя граница баллов */
    private Long upScore;
}