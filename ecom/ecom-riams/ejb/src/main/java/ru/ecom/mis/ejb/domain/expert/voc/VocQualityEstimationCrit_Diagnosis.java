package ru.ecom.mis.ejb.domain.expert.voc;

import lombok.Getter;
import lombok.Setter;
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
@Getter
@Setter
public class VocQualityEstimationCrit_Diagnosis extends BaseEntity {
    /** Критерий оценки качества */
    @Comment("Критерий оценки качества")
    @OneToOne
    public VocQualityEstimationCrit getVQECrit() {
        return VQECrit;
    }

    /** Диагноз */
    @Comment("Диагноз")
    @OneToOne
    public VocIdc10 getVocIdc10() {
        return vocIdc10;
    }

    /** Критерий оценки качества */
    private VocQualityEstimationCrit VQECrit;

    /** Диагноз */
    private VocIdc10 vocIdc10;

    /** Относится ли к акушерскому обсервационному отделению? */
    private Boolean isObserv;

    /** Сопутствующий? */
    private Boolean isConcomitant;
}