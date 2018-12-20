package ru.ecom.mis.ejb.form.calc;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.calc.CalcRisk;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

/**
 * Created by Milamesher on 18.12.2018.
 */
@EntityForm
@EntityFormPersistance(clazz = CalcRisk.class)
@Comment("Форма риска в калькуляторе")
@WebTrail(comment = "Форма риска в калькуляторе"
        , nameProperties= "id", list="entityParentList-calc_risk.do", view="entityParentView-calc_risk.do")
@Parent(property="calculator", parentForm=CalculatorForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Calc/Calculation")
public class CalcRiskForm extends IdEntityForm {
    /** Калькулятор */
    @Comment("Калькулятор")
    @Persist
    public Long getCalculator() {return theCalculator;}
    public void setCalculator(Long aCalculator) {theCalculator = aCalculator;}
    private Long theCalculator;

    /** Наименование */
    @Comment("Наименование")
    @Persist
    public String getRiskValue() {return theRiskValue;}
    public void setRiskValue(String aRiskValue) {theRiskValue = aRiskValue;	}
    private String theRiskValue;

    /** Нижняя граница баллов */
    @Comment("Нижняя граница баллов")
    @Persist
    public Long getLowScore() {return theLowScore;}
    public void setLowScore(Long aLowScore) {theLowScore = aLowScore;	}
    private Long theLowScore;

    /** Верхняя граница баллов */
    @Comment("Верхняя граница баллов")
    @Persist
    public Long getUpScore() {return theUpScore;}
    public void setUpScore(Long aUpScore) {theUpScore = aUpScore;	}
    private Long theUpScore;
}