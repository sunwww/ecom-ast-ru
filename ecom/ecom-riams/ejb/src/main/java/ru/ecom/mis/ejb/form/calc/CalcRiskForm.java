package ru.ecom.mis.ejb.form.calc;

import lombok.Setter;
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
@Setter
public class CalcRiskForm extends IdEntityForm {
    /** Калькулятор */
    @Comment("Калькулятор")
    @Persist
    public Long getCalculator() {return calculator;}
    private Long calculator;

    /** Наименование */
    @Comment("Наименование")
    @Persist
    public String getRiskValue() {return riskValue;}
    private String riskValue;

    /** Нижняя граница баллов */
    @Comment("Нижняя граница баллов")
    @Persist
    public Long getLowScore() {return lowScore;}
    private Long lowScore;

    /** Верхняя граница баллов */
    @Comment("Верхняя граница баллов")
    @Persist
    public Long getUpScore() {return upScore;}
    private Long upScore;
}