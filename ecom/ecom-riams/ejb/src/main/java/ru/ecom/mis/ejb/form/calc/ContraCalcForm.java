package ru.ecom.mis.ejb.form.calc;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.calc.ContraCalc;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

/**
 * Created by Milamesher on 18.12.2018.
 */
@EntityForm
@EntityFormPersistance(clazz = ContraCalc.class)
@Comment("Противопоказание в калькуляторе")
@WebTrail(comment = "Противопоказание в калькуляторе"
        , nameProperties= "id", list="entityParentList-calc_contra.do", view="entityParentView-calc_contra.do")
@Parent(property="calculator", parentForm=CalculatorForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Calc/Calculation")
public class ContraCalcForm extends IdEntityForm {
    /** Калькулятор */
    @Comment("Калькулятор")
    @Persist
    public Long getCalculator() {return theCalculator;}
    public void setCalculator(Long aCalculator) {theCalculator = aCalculator;}
    private Long theCalculator;



    /** Противопоказание */
    @Comment("Противопоказание")
    @Persist
    public String getContraValue() {return theContraValue;}
    public void setContraValue(String aContraValue) {theContraValue = aContraValue;	}
    private String theContraValue;
}