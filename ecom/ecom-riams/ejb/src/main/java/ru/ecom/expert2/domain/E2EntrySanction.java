package ru.ecom.expert2.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.expert2.domain.voc.VocE2Sanction;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/** Санкции к случаю*/
@Entity
@Getter
@Setter
@Accessors(prefix = "the")
@AIndexes({
        @AIndex(properties= {"entry"}),
        @AIndex(properties= {"dopCode"})

})
public class E2EntrySanction extends BaseEntity {
    /** Запись */
    @ManyToOne
    private E2Entry theEntry ;

    /** Санкция */
    @OneToOne
    private VocE2Sanction theSanction ;

    /** Доп. код */
    private String theDopCode ;

    public E2EntrySanction(){}
    public E2EntrySanction (E2Entry aEntry, VocE2Sanction aSanction, String aDopCode, Boolean aIsMainDefect) {
        theEntry=aEntry;theSanction=aSanction;theDopCode=aDopCode;theIsMainDefect=aIsMainDefect;
    }
    public E2EntrySanction (E2Entry aEntry, VocE2Sanction aSanction, String aDopCode, Boolean aIsMainDefect, String aComment) {
        theEntry=aEntry;theSanction=aSanction;theDopCode=aDopCode;theIsMainDefect=aIsMainDefect;theComment=aComment;
    }

    /** Примечание */
    private String theComment ;

    /** Главыный дефект случая */
    private Boolean theIsMainDefect ;

}
