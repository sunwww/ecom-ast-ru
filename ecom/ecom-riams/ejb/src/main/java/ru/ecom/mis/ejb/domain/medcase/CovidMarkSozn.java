package ru.ecom.mis.ejb.domain.medcase;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.mis.ejb.domain.medcase.voc.covidMarcVocs.VocSozn;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;


/**
 * Нарушение сознания в оценке тяжести ковида
 * @author milamesher
 */
@Entity
@Table(schema="SQLUser")
public class CovidMarkSozn extends BaseEntity {
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
    public VocSozn getSozn() {return theSozn;}
    public void setSozn(VocSozn aSozn) {theSozn = aSozn;}
    private VocSozn theSozn ;
}