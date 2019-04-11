package ru.ecom.expert2.domain;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.oncological.ejb.domain.voc.VocOncologyN020;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.List;

@Entity
/** Использование лекарственного препарата в онкологическом случае*/
public class E2CancerDrug extends BaseEntity {

    /** Онкологический случай */
    @Comment("Онкологический случай")
    @ManyToOne
    public E2CancerEntry getCancerEntry() {return theCancerEntry;}
    public void setCancerEntry(E2CancerEntry aCancerEntry) {theCancerEntry = aCancerEntry;}
    /** Онкологический случай */
    private E2CancerEntry theCancerEntry ;

    /** Дата введения препарата */
    @Comment("Дата введения препарата")
    @OneToMany
    public List<E2CancerDrugDate> getDates() {return theDates;}
    public void setDates(List<E2CancerDrugDate> aDates) {theDates= aDates;}
    /** Дата введения препарата */
    private List<E2CancerDrugDate> theDates ;

    /** Лекарственный препарат */
    @Comment("Лекарственный препарат")
    @OneToOne
    public VocOncologyN020 getDrug() {return theDrug;}
    public void setDrug(VocOncologyN020 aDrug) {theDrug = aDrug;}
    /** Лекарственный препарат */
    private VocOncologyN020 theDrug ;

    public E2CancerDrug() {}
    public E2CancerDrug(E2CancerEntry aCancerEntry) {theCancerEntry = aCancerEntry;}
}
