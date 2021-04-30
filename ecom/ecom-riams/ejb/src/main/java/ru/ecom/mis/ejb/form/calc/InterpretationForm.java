package ru.ecom.mis.ejb.form.calc;

/**
 * Created by Milamesher on 21.11.2018.
 */

import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.calc.Interpretation;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

@EntityForm
@EntityFormPersistance(clazz = Interpretation.class)
@Comment("Интерпретация результата")
@WebTrail(comment = "Интерпретация результата"
        , nameProperties= "id", list="entityParentList-interpretation.do", view="entityParentView-calc_interpretation.do")
@Parent(property="calculator", parentForm=CalculatorForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Calc/Calculator")
@Setter
public class InterpretationForm extends IdEntityForm {
    /** Калькулятор */
    @Comment("Калькулятор")
    @Persist
    public Long getCalculator() {return calculator;}
    private Long calculator;

    /** Значение */
    @Comment("Значение")
    @Persist
    public String getValue() {return value;}
    private String value;

    /** Результат */
    @Comment("Результат")
    @Persist
    public String getResult() {return result;}
    private String result;
}