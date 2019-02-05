package ru.ecom.expert2.domain;

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
    @Comment("Диагноз")
    @OneToOne
    public VocIdc10 getMkb() {return theMkb;}
    public void setMkb(VocIdc10 aMkb) {theMkb = aMkb;}
    /** Диагноз */
    private VocIdc10 theMkb ;
    /** Тип регистрации */
    @Comment("Тип регистрации")
    @OneToOne
    public VocDiagnosisRegistrationType getRegistrationType() {return theRegistrationType;}
    public void setRegistrationType(VocDiagnosisRegistrationType aRegistrationType) {theRegistrationType = aRegistrationType;}
    /** Тип регистрации */
    private VocDiagnosisRegistrationType theRegistrationType ;

    /** Приоритет */
    @Comment("Приоритет")
    @OneToOne
    public VocPriorityDiagnosis getPriority() {return thePriority;}
    public void setPriority(VocPriorityDiagnosis aPriority) {thePriority = aPriority;}
    /** Приоритет */
    private VocPriorityDiagnosis thePriority ;

    /** Доп. код МКБ */
    @Comment("Доп. код МКБ")
    public String getDopMkb() {return theDopMkb;}
    public void setDopMkb(String aDopMkb) {theDopMkb = aDopMkb;}
    /** Доп. код МКБ */
    private String theDopMkb ;

    /** Справочник характеров заболевания */
    @Comment("Справочник характеров заболевания")
    @OneToOne
    public VocE2FondV027 getVocIllnessPrimary() {return theVocIllnessPrimary;}
    public void setVocIllnessPrimary(VocE2FondV027 aVocIllnessPrimary) {theVocIllnessPrimary = aVocIllnessPrimary;}
    /** Справочник характеров заболевания */
    private VocE2FondV027 theVocIllnessPrimary ;

    /** Характер заболевания */
    @Comment("Характер заболевания")
    public String getIllnessPrimary() {return theIllnessPrimary;}
    public void setIllnessPrimary(String aIllnessPrimary) {theIllnessPrimary = aIllnessPrimary;}
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
