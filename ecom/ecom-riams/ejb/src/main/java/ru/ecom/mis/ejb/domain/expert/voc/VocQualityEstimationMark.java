package ru.ecom.mis.ejb.domain.expert.voc;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Справочник баллов критериев оценки качества
 */
@Comment("Справочник баллов критериев оценки качества")
@Entity
@Table(schema = "SQLUser")
@AIndexes({
        @AIndex(properties = "criterion")
        , @AIndex(properties = {"criterion", "mark"})
})
@Getter
@Setter
public class VocQualityEstimationMark extends VocBaseEntity {

    /**
     * Не учитывать
     */
    private Boolean isIgnore;


    /**
     * Полное название
     */
    private String fullname;
    /**
     * Оценочный балл
     */
    private Double mark;

    /**
     * Критерий оценки качества
     */
    @Comment("Критерий оценки качества")
    @OneToOne
    public VocQualityEstimationCrit getCriterion() {
        return criterion;
    }

    /**
     * Критерий оценки качества
     */
    private VocQualityEstimationCrit criterion;

    /**
     * Обязательно указывать примечание
     */
    private Boolean isNeedComment;
}
