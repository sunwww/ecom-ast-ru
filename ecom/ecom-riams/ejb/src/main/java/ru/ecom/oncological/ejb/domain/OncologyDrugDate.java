package ru.ecom.oncological.ejb.domain;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.sql.Date;

@Entity
@Getter
@Setter
/** Использование лекарственного препарата в онкологическом случае*/
public class OncologyDrugDate extends BaseEntity {

    /** Лекарственный препарат*/
    @Comment("Лекарственный препарат")
    @ManyToOne(fetch = FetchType.LAZY)
    public OncologyDrug getOncologyDrug() {return oncologyDrug;}
    /** Онкологический случай */
    private OncologyDrug oncologyDrug ;

    /** Дата введения */
    private Date date ;
}
