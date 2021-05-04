package ru.ecom.mis.ejb.domain.calc;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.live.DeleteListener;
import ru.ecom.mis.ejb.domain.medcase.MedCase;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.OneToOne;
import javax.persistence.Table;


/**
 * Результаты калькуляции
 */
@Comment("Результаты калькуляции")
@Entity
@Table(schema = "SQLUser")
@EntityListeners(DeleteListener.class)
@Getter
@Setter
public class CalculationsResult extends BaseEntity {


    /**
     * СЛО
     */
    @Comment("СЛО")
    @OneToOne
    public MedCase getDepartmentMedCase() {
        return departmentMedCase;
    }

    private MedCase departmentMedCase;

    /**
     * Калькулятор
     */
    @Comment("Калькулятор")
    @OneToOne
    public Calculator getCalculator() {
        return calculator;
    }

    private Calculator calculator;

    /**
     * Дата
     */
    private String resDate;

    /**
     * Результат
     */
    private String result;


}
