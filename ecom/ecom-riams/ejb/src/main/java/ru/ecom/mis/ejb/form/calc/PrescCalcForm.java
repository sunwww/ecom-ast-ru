package ru.ecom.mis.ejb.form.calc;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.calc.CalcRisk;
import ru.ecom.mis.ejb.domain.calc.PrescCalc;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

import javax.persistence.OneToOne;

/**
 * Created by Milamesher on 18.12.2018.
 */
@EntityForm
@EntityFormPersistance(clazz = PrescCalc.class)
@Comment("Назначение в калькуляторе")
@WebTrail(comment = "Назначение в калькуляторе"
        , nameProperties= "id", list="entityParentList-calc_presc.do", view="entityParentView-calc_presc.do")
@Parent(property="calculator", parentForm=CalculatorForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Calc/Calculation")
public class PrescCalcForm  extends IdEntityForm {
    /** Калькулятор */
    @Comment("Калькулятор")
    @Persist
    public Long getCalculator() {return theCalculator;}
    public void setCalculator(Long aCalculator) {theCalculator = aCalculator;}
    private Long theCalculator;

    /** Назначение */
    @Comment("Назначение")
    @Persist
    public String getPrescValue() {return thePrescValue;}
    public void setPrescValue(String aPrescValue) {thePrescValue = aPrescValue;	}
    private String thePrescValue;

    /** Риск */
    @Comment("Риск")
    @Persist
    @OneToOne
    public Long getCalcRisk() {return theCalcRisk;}
    public void setCalcRisk(Long aCalcRisk) {theCalcRisk = aCalcRisk;	}
    private Long theCalcRisk;
}