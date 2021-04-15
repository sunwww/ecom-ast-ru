package ru.ecom.expert2.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.oncological.ejb.domain.voc.VocOncologyN020;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Accessors(prefix = "the")
/** Использование лекарственного препарата в онкологическом случае*/
public class E2CancerDrug extends BaseEntity {

    /** Онкологический случай */
    @ManyToOne
    private E2CancerEntry theCancerEntry ;

    /** Дата введения препарата */
    @Comment("Дата введения препарата")
    @OneToMany(mappedBy = "drug", cascade = CascadeType.ALL)
    public List<E2CancerDrugDate> getDates() {return theDates;}
    private List<E2CancerDrugDate> theDates ;

    /** Лекарственный препарат */
    @OneToOne
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
