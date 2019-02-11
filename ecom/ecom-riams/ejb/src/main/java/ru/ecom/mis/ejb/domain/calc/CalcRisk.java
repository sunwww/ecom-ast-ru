package ru.ecom.mis.ejb.domain.calc;

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
public class CalcRisk extends BaseEntity {
    /** Калькулятор */
    @Comment("Калькулятор")
    @OneToOne
    public Calculator getCalculator() {return theCalculator;}
    public void setCalculator(Calculator aCalculator) {theCalculator = aCalculator;	}
    private Calculator theCalculator;

    /** Наименование */
    @Comment("Наименование")
    public String getRiskValue() {return theRiskValue;}
    public void setRiskValue(String aRiskValue) {theRiskValue = aRiskValue;	}
    private String theRiskValue;

    /** Нижняя граница баллов */
    @Comment("Нижняя граница баллов")
    public Long getLowScore() {return theLowScore;}
    public void setLowScore(Long aLowScore) {theLowScore = aLowScore;	}
    private Long theLowScore;
    
    /** Верхняя граница баллов */
    @Comment("Верхняя граница баллов")
    public Long getUpScore() {return theUpScore;}
    public void setUpScore(Long aUpScore) {theUpScore = aUpScore;	}
    private Long theUpScore;
}