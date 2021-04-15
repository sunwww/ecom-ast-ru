package ru.ecom.expert2.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.sql.Date;

@Entity
@Getter
@Setter
@Accessors(prefix = "the")
public class E2CancerDrugDate extends BaseEntity {

    /** Лекарство */
    @ManyToOne
    private E2CancerDrug theDrug ;

    /** Дата введения препарата */
    private Date theDate ;

    public E2CancerDrugDate() {}
    public E2CancerDrugDate(E2CancerDrug aDrug) {theDrug = aDrug;}
}
