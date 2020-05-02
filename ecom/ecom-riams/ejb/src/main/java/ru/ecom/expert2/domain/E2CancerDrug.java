package ru.ecom.expert2.domain;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.oncological.ejb.domain.voc.VocOncologyN020;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
/** Использование лекарственного препарата в онкологическом случае*/
public class E2CancerDrug extends BaseEntity {

    /** Онкологический случай */
    @Comment("Онкологический случай")
    @ManyToOne
    public E2CancerEntry getCancerEntry() {return theCancerEntry;}
    public void setCancerEntry(E2CancerEntry aCancerEntry) {theCancerEntry = aCancerEntry;}
    private E2CancerEntry theCancerEntry ;

    /** Дата введения препарата */
    @Comment("Дата введения препарата")
    @OneToMany(mappedBy = "drug", cascade = CascadeType.ALL)
    public List<E2CancerDrugDate> getDates() {return theDates;}
    public void setDates(List<E2CancerDrugDate> aDates) {theDates= aDates;}
    private List<E2CancerDrugDate> theDates ;

    /** Лекарственный препарат */
    @Comment("Лекарственный препарат")
    @OneToOne
    public VocOncologyN020 getDrug() {return theDrug;}
    public void setDrug(VocOncologyN020 aDrug) {theDrug = aDrug;}
    private VocOncologyN020 theDrug ;

    public E2CancerDrug() {}
    public E2CancerDrug(E2CancerEntry aCancerEntry) {theCancerEntry = aCancerEntry;}
    public E2CancerDrug(E2CancerDrug aDrug, E2CancerEntry aCancerEntry) {
        theCancerEntry = aCancerEntry;
        theDrug = aDrug.theDrug;
        List<E2CancerDrugDate> drugDates = new ArrayList<>();
        if (getDates()!=null) {
            for (E2CancerDrugDate drugDate : getDates()) {
                E2CancerDrugDate cancerDrugDate = new E2CancerDrugDate(this);
                cancerDrugDate.setDate(drugDate.getDate());
                drugDates.add(cancerDrugDate);
            }
        }
        setDates(drugDates);
    }
}
