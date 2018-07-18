package ru.ecom.oncological.ejb.domain;

import org.hibernate.annotations.Entity;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.oncological.ejb.domain.voc.VocContraindicationAndRejection_N001;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.OneToOne;
import javax.persistence.Table;
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
    private VocContraindicationAndRejection_N001 contraindicationAndRejection;
    /** Дата регистрации противопоказания или отказа*/
    private Date date;


    @Comment("Случай онкологического лечения")
    @OneToOne
    public OncologyCase getOncologyCase() {
        return oncologyCase;
    }
    public void setOncologyCase(OncologyCase oncologyCase) {
        this.oncologyCase = oncologyCase;
    }

    @Comment("Код противопоказания")
    @OneToOne
    public VocContraindicationAndRejection_N001 getContraindicationAndRejection() {
        return contraindicationAndRejection;
    }
    public void setContraindicationAndRejection(VocContraindicationAndRejection_N001 contraindicationAndRejection) {
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
