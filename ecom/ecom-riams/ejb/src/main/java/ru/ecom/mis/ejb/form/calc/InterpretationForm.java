package ru.ecom.mis.ejb.form.calc;

/**
 * Created by Milamesher on 21.11.2018.
 */

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
public class InterpretationForm extends IdEntityForm {
    /** Калькулятор */
    @Comment("Калькулятор")
    @Persist
    public Long getCalculator() {return theCalculator;}
    public void setCalculator(Long aCalculator) {theCalculator = aCalculator;}
    private Long theCalculator;

    /** Значение */
    @Comment("Значение")
    @Persist
    public String getValue() {return theValue;}
    public void setValue(String aValue) {theValue = aValue;	}
    private String theValue;

    /** Результат */
    @Comment("Результат")
    @Persist
    public String getResult() {return theResult;}
    public void setResult(String aResult) {theResult = aResult;}
    private String theResult;
}