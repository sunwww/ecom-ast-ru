package ru.ecom.expert2.domain;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.oncological.ejb.domain.voc.VocOncologyN020;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
/** Использование лекарственного препарата в онкологическом случае*/
public class E2CancerDrug extends BaseEntity {

    /** Онкологический случай */
    @Comment("Онкологический случай")
    @ManyToOne
    public E2CancerEntry getCancerEntry() {return cancerEntry;}
    private E2CancerEntry cancerEntry;

    /** Дата введения препарата */
    @Comment("Дата введения препарата")
    @OneToMany(mappedBy = "drug", cascade = CascadeType.ALL)
    public List<E2CancerDrugDate> getDates() {return dates;}
    private List<E2CancerDrugDate> dates;

    /** Лекарственный препарат */
    @Comment("Лекарственный препарат")
    @OneToOne
    public VocOncologyN020 getDrug() {return drug;}
    private VocOncologyN020 drug;

    public E2CancerDrug() {}
    public E2CancerDrug(E2CancerEntry aCancerEntry) {
        cancerEntry = aCancerEntry;}
    public E2CancerDrug(E2CancerDrug aDrug, E2CancerEntry aCancerEntry) {
        cancerEntry = aCancerEntry;
        drug = aDrug.drug;
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
