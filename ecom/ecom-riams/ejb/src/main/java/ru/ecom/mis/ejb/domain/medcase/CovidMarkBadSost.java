package ru.ecom.mis.ejb.domain.medcase;

import lombok.Getter;
import lombok.Setter;
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
@Getter
@Setter
public class CovidMarkBadSost extends BaseEntity {
    @Comment("Форма оценки сознания")
    @ManyToOne(fetch = FetchType.LAZY)
    public CovidMark getCovidMark() {
        return covidMark;
    }
    private CovidMark covidMark;

    /** Нарушение сознания в оценке ковида */
    @Comment("Нарушение сознания в оценке ковида")
    @OneToOne
    public VocBadSost getBadSost() {return badSost;}
    private VocBadSost badSost ;
}