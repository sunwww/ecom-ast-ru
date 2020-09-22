package ru.ecom.mis.ejb.domain.medcase;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.mis.ejb.domain.medcase.voc.covidMarcVocs.VocBadSost;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;


/**
 * Нарушение сознания в оценке тяжести ковида
 * @author milamesher
 */
@Entity
@Table(schema="SQLUser")
public class CovidMarkBadSost extends BaseEntity {
    @Comment("Форма оценки сознания")
    @ManyToOne(fetch = FetchType.LAZY)
    public CovidMark getCovidMark() {
        return covidMark;
    }
    public void setCovidMark(CovidMark covidMark) {
        this.covidMark = covidMark;
    }
    /**Форма оценки сознания */
    private CovidMark covidMark;

    /** Нарушение сознания в оценке ковида */
    @Comment("Нарушение сознания в оценке ковида")
    @OneToOne
    public VocBadSost getBadSost() {return theBadSost;}
    public void setBadSost(VocBadSost aBadSost) {theBadSost = aBadSost;}
    private VocBadSost theBadSost ;
}