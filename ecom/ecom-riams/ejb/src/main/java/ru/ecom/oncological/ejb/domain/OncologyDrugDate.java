package ru.ecom.oncological.ejb.domain;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.sql.Date;

@Entity
/** Использование лекарственного препарата в онкологическом случае*/
public class OncologyDrugDate extends BaseEntity {

    /** Лекарственный препарат*/
    @Comment("Лекарственный препарат")
    @ManyToOne(fetch = FetchType.LAZY)
    public OncologyDrug getOncologyDrug() {return theOncologyDrug;}
    public void setOncologyDrug(OncologyDrug aOncologyDrug) {theOncologyDrug = aOncologyDrug;}
    /** Онкологический случай */
    private OncologyDrug theOncologyDrug ;

    /** Дата введения */
    @Comment("Дата введения")
    public Date getDate() {return theDate;}
    public void setDate(Date aDate) {theDate = aDate;}
    /** Дата введения */
    private Date theDate ;



}
