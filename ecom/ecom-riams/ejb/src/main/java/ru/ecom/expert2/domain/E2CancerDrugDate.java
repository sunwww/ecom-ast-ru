package ru.ecom.expert2.domain;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.sql.Date;

@Entity
@Getter
@Setter
public class E2CancerDrugDate extends BaseEntity {

    /** Лекарство */
    @Comment("Лекарство")
    @ManyToOne
    public E2CancerDrug getDrug() {return drug;}
    private E2CancerDrug drug;

    /** Дата введения препарата */
    private Date date;

    public E2CancerDrugDate() {}
    public E2CancerDrugDate(E2CancerDrug aDrug) {
        drug = aDrug;}
}
