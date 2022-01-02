package ru.ecom.expert2.domain;

import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.expert2.domain.voc.federal.VocE2FondN020;
import ru.ecom.expert2.domain.voc.federal.VocE2FondV032;
import ru.ecom.expert2.domain.voc.federal.VocE2FondV034;
import ru.ecom.expert2.domain.voc.federal.VocE2FondV035;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.sql.Date;

@Entity
@Setter
/**
 * Сведения о введенном лекарственном препарате
 */
public class E2DrugEntry extends BaseEntity {

    private E2Entry entry;
    private Date injectDate;
    private VocE2FondV032 drugGroupSchema;
    private VocE2FondN020 drug;
    private VocE2FondV034 injectUnit;
    private String injectAmount;
    private Integer injectNumber;
    private VocE2FondV035 injectMethod;

    @ManyToOne
    public E2Entry getEntry() {
        return entry;
    }

    /**
     * Дата введения препарата
     */
    public Date getInjectDate() {
        return injectDate;
    }

    /**
     * Схема лечения (**группа препарата)
     */
    @ManyToOne
    public VocE2FondV032 getDrugGroupSchema() {
        return drugGroupSchema;
    }

    /**
     * Лекарственный препарат
     */
    @ManyToOne
    public VocE2FondN020 getDrug() {
        return drug;
    }

    /**
     * Единица измерения дозы введения препарата
     */
    @ManyToOne
    public VocE2FondV034 getInjectUnit() {
        return injectUnit;
    }

    /**
     * Доза введения препарата
     */
    public String getInjectAmount() {
        return injectAmount;
    }

    /**
     * Количество введения препарата
     */
    public Integer getInjectNumber() {
        return injectNumber;
    }

    /**
     * Метод введения препарата
     */
    @ManyToOne
    public VocE2FondV035 getInjectMethod() {
        return injectMethod;
    }
}
