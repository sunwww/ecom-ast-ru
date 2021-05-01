package ru.ecom.mis.ejb.form.calc;

import lombok.Setter;
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
@Setter
public class PrescCalcForm  extends IdEntityForm {
    /** Калькулятор */
    @Comment("Калькулятор")
    @Persist
    public Long getCalculator() {return calculator;}
    private Long calculator;

    /** Назначение */
    @Comment("Назначение")
    @Persist
    public String getPrescValue() {return prescValue;}
    private String prescValue;

    /** Риск */
    @Comment("Риск")
    @Persist
    @OneToOne
    public Long getCalcRisk() {return calcRisk;}
    private Long calcRisk;
}