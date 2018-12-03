package ru.ecom.oncological.ejb.domain;


import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.oncological.ejb.domain.voc.VocOncologyN001;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;
import java.sql.Date;

/**
 * Сведения об имеющихся противопоказаниях и отказах
 * @author rkurbanov
 */
@Entity
@Table(schema="SQLUser")
public class OncologyContra extends BaseEntity {

    /**Случай онкологического лечения */
    private OncologyCase oncologyCase;
    /** Код противопоказания */
    private VocOncologyN001 contraindicationAndRejection;
    /** Дата регистрации противопоказания или отказа*/
    private Date date;


    @Comment("Случай онкологического лечения")
    @ManyToOne(fetch = FetchType.LAZY)
    public OncologyCase getOncologyCase() {
        return oncologyCase;
    }
    public void setOncologyCase(OncologyCase oncologyCase) {
        this.oncologyCase = oncologyCase;
    }

    @Comment("Код противопоказания")
    @OneToOne
    public VocOncologyN001 getContraindicationAndRejection() {
        return contraindicationAndRejection;
    }
    public void setContraindicationAndRejection(VocOncologyN001 contraindicationAndRejection) {
        this.contraindicationAndRejection = contraindicationAndRejection;
    }

    @Comment("Дата регистрации противопоказания или отказа")
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
}
