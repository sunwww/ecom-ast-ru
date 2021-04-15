package ru.ecom.expert2.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.expert2.domain.voc.federal.VocE2FondV027;
import ru.ecom.expomc.ejb.domain.med.VocIdc10;
import ru.ecom.mis.ejb.domain.medcase.voc.VocDiagnosisRegistrationType;
import ru.ecom.mis.ejb.domain.medcase.voc.VocPriorityDiagnosis;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 * Список диагнозов по записи
 */
@Entity
@Getter
@Setter
@Accessors(prefix = "the")
@AIndexes({
        @AIndex(properties = {"entry"})
})
public class EntryDiagnosis extends BaseEntity {

    /** Запись */
    @Comment("Запись")
    @ManyToOne
    public E2Entry getEntry() {return theEntry;}
    public void setEntry(E2Entry aEntry) {theEntry = aEntry;}
    /** Запись */
    private E2Entry theEntry ;

    /** Диагноз */
    @OneToOne
    private VocIdc10 theMkb ;

    /** Тип регистрации */
    @OneToOne
    private VocDiagnosisRegistrationType theRegistrationType ;

    /** Приоритет */
    @OneToOne
    private VocPriorityDiagnosis thePriority ;

    /** Доп. код МКБ */
    private String theDopMkb ;

    /** Справочник характеров заболевания */
    @OneToOne
    private VocE2FondV027 theVocIllnessPrimary ;

    /** Характер заболевания */
    private String theIllnessPrimary ;


    public EntryDiagnosis(E2Entry aEntry, VocIdc10 aMkb, VocDiagnosisRegistrationType aRegType, VocPriorityDiagnosis aPriority, String aDopMkb
            , String aIllnessPrimary) {
        theEntry=aEntry; theMkb=aMkb;theRegistrationType=aRegType;thePriority=aPriority;theDopMkb=aDopMkb;theIllnessPrimary=aIllnessPrimary;
    }
    public EntryDiagnosis(E2Entry aEntry, VocIdc10 aMkb, VocDiagnosisRegistrationType aRegType, VocPriorityDiagnosis aPriority, String aDopMkb
            , VocE2FondV027 aIllnessPrimary) {
        theEntry=aEntry; theMkb=aMkb;theRegistrationType=aRegType;thePriority=aPriority;theDopMkb=aDopMkb;theVocIllnessPrimary=aIllnessPrimary;
    }
    public EntryDiagnosis (E2Entry aEntry, EntryDiagnosis aEntryDiagnosis) {
        theEntry=aEntry; theMkb=aEntryDiagnosis.getMkb();theRegistrationType=aEntryDiagnosis.getRegistrationType();
        thePriority=aEntryDiagnosis.getPriority();theDopMkb=aEntryDiagnosis.getDopMkb();theIllnessPrimary=aEntryDiagnosis.getIllnessPrimary();
        theVocIllnessPrimary = aEntryDiagnosis.getVocIllnessPrimary();
    }
    public EntryDiagnosis() {}
}
