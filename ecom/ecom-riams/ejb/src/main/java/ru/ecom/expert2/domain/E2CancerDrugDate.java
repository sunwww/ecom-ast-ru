package ru.ecom.expert2.domain;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.sql.Date;

@Entity
public class E2CancerDrugDate extends BaseEntity {

    /** Лекарство */
    @Comment("Лекарство")
    @ManyToOne
    public E2CancerDrug getDrug() {return theDrug;}
    public void setDrug(E2CancerDrug aDrug) {theDrug = aDrug;}
    /** Онкологический случай */
    private E2CancerDrug theDrug ;

    /** Дата введения препарата */
    @Comment("Дата введения препарата")
    public Date getDate() {return theDate;}
    public void setDate(Date aDate) {theDate = aDate;}
    /** Дата введения препарата */
    private Date theDate ;

    public E2CancerDrugDate() {}
    public E2CancerDrugDate(E2CancerDrug aDrug) {theDrug = aDrug;}
}
