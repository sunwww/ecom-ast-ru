package ru.ecom.expert2.domain;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.expert2.domain.voc.federal.VocE2FondV027;
import ru.ecom.expomc.ejb.domain.med.VocIdc10;
import ru.ecom.mis.ejb.domain.medcase.voc.VocDiagnosisRegistrationType;
import ru.ecom.mis.ejb.domain.medcase.voc.VocPriorityDiagnosis;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.util.Objects;

/**
 * Список диагнозов по записи
 */
@Entity
@AIndexes({
        @AIndex(properties = {"entry"})
})
@Getter
@Setter
public class EntryDiagnosis extends BaseEntity {

    /**
     * Запись
     */
    @Comment("Запись")
    @ManyToOne
    public E2Entry getEntry() {
        return entry;
    }

    private E2Entry entry;
    private Long entryId;

    @Column(name = "entry_id", insertable = false, updatable = false)
    public Long getEntryId() {
        return entryId;
    }

    /**
     * Диагноз
     */
    @Comment("Диагноз")
    @OneToOne
    public VocIdc10 getMkb() {
        return mkb;
    }

    private VocIdc10 mkb;

    /**
     * Тип регистрации
     */
    @Comment("Тип регистрации")
    @OneToOne
    public VocDiagnosisRegistrationType getRegistrationType() {return registrationType;}
    private VocDiagnosisRegistrationType registrationType;

    /** Приоритет */
    @Comment("Приоритет")
    @OneToOne
    public VocPriorityDiagnosis getPriority() {return priority;}
    private VocPriorityDiagnosis priority;

    /** Доп. код МКБ */
    @Comment("Доп. код МКБ")
    public String getDopMkb() {return dopMkb;}
    private String dopMkb;

    /** Справочник характеров заболевания */
    @Comment("Справочник характеров заболевания")
    @OneToOne
    public VocE2FondV027 getVocIllnessPrimary() {return vocIllnessPrimary;}
    private VocE2FondV027 vocIllnessPrimary;

    /** Характер заболевания */
    private String illnessPrimary;


    public EntryDiagnosis(E2Entry aEntry, VocIdc10 aMkb, VocDiagnosisRegistrationType aRegType, VocPriorityDiagnosis aPriority, String aDopMkb
            , String aIllnessPrimary) {
        entry =aEntry; mkb =aMkb;
        registrationType =aRegType;
        priority =aPriority;
        dopMkb =aDopMkb;
        illnessPrimary =aIllnessPrimary;
    }
    public EntryDiagnosis(E2Entry aEntry, VocIdc10 aMkb, VocDiagnosisRegistrationType aRegType, VocPriorityDiagnosis aPriority, String aDopMkb
            , VocE2FondV027 aIllnessPrimary) {
        entry =aEntry; mkb =aMkb;
        registrationType =aRegType;
        priority =aPriority;
        dopMkb =aDopMkb;
        vocIllnessPrimary =aIllnessPrimary;
    }
    public EntryDiagnosis (E2Entry aEntry, EntryDiagnosis aEntryDiagnosis) {
        entry =aEntry; mkb =aEntryDiagnosis.getMkb();
        registrationType =aEntryDiagnosis.getRegistrationType();
        priority =aEntryDiagnosis.getPriority();
        dopMkb =aEntryDiagnosis.getDopMkb();
        illnessPrimary =aEntryDiagnosis.getIllnessPrimary();
        vocIllnessPrimary = aEntryDiagnosis.getVocIllnessPrimary();
    }
    public EntryDiagnosis() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        EntryDiagnosis that = (EntryDiagnosis) o;
        return Objects.equals(entry, that.entry) && Objects.equals(mkb, that.mkb) && Objects.equals(registrationType, that.registrationType) && Objects.equals(priority, that.priority) && Objects.equals(dopMkb, that.dopMkb) && Objects.equals(vocIllnessPrimary, that.vocIllnessPrimary) && Objects.equals(illnessPrimary, that.illnessPrimary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), entry, mkb, registrationType, priority, dopMkb, vocIllnessPrimary, illnessPrimary);
    }
}
