package ru.ecom.expert2.domain;

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
public class E2EntryError extends BaseEntity {

    /** Заполнение с ошибкой */
    @Comment("Заполнение с ошибкой")
    @OneToOne
    public E2ListEntry getListEntry() {return theListEntry;}
    public void setListEntry(E2ListEntry aListEntry) {theListEntry = aListEntry;}
    /** Заполнение с ошибкой */
    private E2ListEntry theListEntry ;

    /** Случай */
    @Comment("Случай")
    @ManyToOne
    public E2Entry getEntry() {return theEntry;}
    public void setEntry(E2Entry aEntry) {theEntry = aEntry;}
    /** Случай */
    private E2Entry theEntry ;

    /** Удаленная запись */
    @Comment("Удаленная запись")
    public Boolean getIsDeleted() {return theIsDeleted;}
    public void setIsDeleted(Boolean aIsDeleted) {theIsDeleted = aIsDeleted;}
    /** Удаленная запись */
    private Boolean theIsDeleted ;

    @PrePersist
    void onPrePesist(){
        if (theIsDeleted==null) {theIsDeleted=false;}
    }

    /** Код ошибки */
    @Comment("Код ошибки")
    public String getErrorCode() {return theErrorCode;}
    public void setErrorCode(String aErrorCode) {theErrorCode = aErrorCode;}
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
    @Comment("Примечание")
    public String getComment() {return theComment;}
    public void setComment(String aComment) {theComment = aComment;}
    /** Примечание */
    private String theComment ;
}