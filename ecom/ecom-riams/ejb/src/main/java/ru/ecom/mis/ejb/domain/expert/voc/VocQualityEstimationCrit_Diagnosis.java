package ru.ecom.mis.ejb.domain.expert.voc;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.expomc.ejb.domain.med.VocIdc10;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Справочник соответствия критерия оценки качества и диагноза
 */
@Comment("Справочник соответствия критерия оценки качества и диагноза")
@Entity
@Table(schema="SQLUser")
@AIndexes({
        @AIndex(properties="VQECrit")
        , @AIndex(properties="vocIdc10")
})
public class VocQualityEstimationCrit_Diagnosis extends BaseEntity {
    /** Критерий оценки качества */
    @Comment("Критерий оценки качества")
    @OneToOne
    public VocQualityEstimationCrit getVQECrit() {
        return VQECrit;
    }
    public void setVQECrit(VocQualityEstimationCrit VQECrit) {
        this.VQECrit = VQECrit;
    }

    /** Диагноз */
    @Comment("Диагноз")
    @OneToOne
    public VocIdc10 getVocIdc10() {
        return vocIdc10;
    }
    public void setVocIdc10(VocIdc10 vocIdc10) {
        this.vocIdc10 = vocIdc10;
    }

    /** Критерий оценки качества */
    private VocQualityEstimationCrit VQECrit;

    /** Диагноз */
    private VocIdc10 vocIdc10;

    /** Относится ли к акушерскому обсервационному отделению? */
    private Boolean theIsObserv;
    public Boolean getIsObserv() {
        return theIsObserv;
    }
    public void setIsObserv(Boolean aIsObserv) {
        this.theIsObserv = aIsObserv;
    }

    /** Сопутствующий? */
    private Boolean theIsConcomitant;
    public Boolean getIsConcomitant() {
        return theIsConcomitant;
    }
    public void setIsConcomitant(Boolean aIsConcomitant) {
        this.theIsConcomitant = aIsConcomitant;
    }

}