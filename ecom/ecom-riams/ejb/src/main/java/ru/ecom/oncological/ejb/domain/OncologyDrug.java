package ru.ecom.oncological.ejb.domain;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.oncological.ejb.domain.voc.VocOncologyN020;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;
import java.util.List;

@Entity
/** Использование лекарственного препарата в онкологическом случае*/
public class OncologyDrug extends BaseEntity {

    /** Онкологический случай */
    @Comment("Онкологический случай")
    @ManyToOne(fetch = FetchType.LAZY)
    public OncologyCase getOncologyCase() {return theOncologyCase;}
    public void setOncologyCase(OncologyCase aOncologyCase) {theOncologyCase = aOncologyCase;}
    /** Онкологический случай */
    private OncologyCase theOncologyCase ;

    /** Дата введения препарата */
    @Comment("Дата введения препарата")
    @OneToMany(mappedBy = "oncologyDrug", cascade = CascadeType.ALL)
    public List<OncologyDrugDate> getDates() {return theDates;}
    public void setDates(List<OncologyDrugDate> aDates) {theDates = aDates;}
    /** Дата введения препарата */
    private List<OncologyDrugDate> theDates ;

    /** Лекарственный препарат */
    @Comment("Лекарственный препарат")
    @OneToOne
    public VocOncologyN020 getDrug() {return theDrug;}
    public void setDrug(VocOncologyN020 aDrug) {theDrug = aDrug;}
    /** Лекарственный препарат */
    private VocOncologyN020 theDrug ;
}
