package ru.ecom.expert2.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;

@Entity
@AIndexes({
        @AIndex(properties= {"listEntry"})
        , @AIndex(properties= {"errorCode"})
})
@Getter
@Setter
@Accessors(prefix = "the")
public class E2EntryError extends BaseEntity {

    /** Заполнение с ошибкой */
    @OneToOne
    private E2ListEntry theListEntry ;

    /** Случай */
    @ManyToOne
    private E2Entry theEntry ;

    /** Удаленная запись */
    private Boolean theIsDeleted ;

    @PrePersist
    void onPrePesist(){
        if (theIsDeleted==null) {theIsDeleted=false;}
    }

    /** Код ошибки */
    private String theErrorCode ;
    public E2EntryError(){}
    public E2EntryError(E2Entry aEntry, String aCode) {
        this.theEntry=aEntry;
        this.theErrorCode=aCode;
        this.theListEntry=aEntry.getListEntry();
    }
    public E2EntryError(E2Entry aEntry, String aCode, String aComment) {
        this.theEntry=aEntry;
        this.theErrorCode=aCode;
        this.theListEntry=aEntry.getListEntry();
        theComment = aComment;
    }

    /** Примечание */
    private String theComment ;
}