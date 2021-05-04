package ru.ecom.oncological.ejb.domain;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.oncological.ejb.domain.voc.VocOncologyN020;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
/** Использование лекарственного препарата в онкологическом случае*/
public class OncologyDrug extends BaseEntity {

    /** Онкологический случай */
    @Comment("Онкологический случай")
    @ManyToOne(fetch = FetchType.LAZY)
    public OncologyCase getOncologyCase() {return oncologyCase;}
    /** Онкологический случай */
    private OncologyCase oncologyCase ;

    /** Дата введения препарата */
    @Comment("Дата введения препарата")
    @OneToMany(mappedBy = "oncologyDrug", cascade = CascadeType.ALL)
    public List<OncologyDrugDate> getDates() {return dates;}
    /** Дата введения препарата */
    private List<OncologyDrugDate> dates ;

    /** Лекарственный препарат */
    @Comment("Лекарственный препарат")
    @OneToOne
    public VocOncologyN020 getDrug() {return drug;}
    /** Лекарственный препарат */
    private VocOncologyN020 drug ;
}
