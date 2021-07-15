package ru.ecom.expert2.domain;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.expert2.domain.voc.VocE2Sanction;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 * Санкции к случаю
 */
@Entity
@AIndexes({
        @AIndex(properties = {"entry"}),
        @AIndex(properties = {"dopCode"}),
        @AIndex(properties = {"entry"})

})
@Getter
@Setter
public class E2EntrySanction extends BaseEntity {
    /**
     * Запись
     */
    @Comment("Запись")
    @ManyToOne
    public E2Entry getEntry() {
        return entry;
    }

    private E2Entry entry;

    /**
     * Санкция
     */
    @Comment("Санкция")
    @OneToOne
    public VocE2Sanction getSanction() {
        return sanction;
    }

    private VocE2Sanction sanction;

    /**
     * Доп. код
     */
    private String dopCode;

    public E2EntrySanction() {
    }

    public E2EntrySanction(E2Entry aEntry, VocE2Sanction aSanction, String aDopCode, Boolean aIsMainDefect, String aComment) {
        entry = aEntry;
        sanction = aSanction;
        dopCode = aDopCode;
        isMainDefect = aIsMainDefect;
        comment = aComment;
    }

    /**
     * Примечание
     */
    private String comment;

    /**
     * Главыный дефект случая
     */
    private Boolean isMainDefect;

}
